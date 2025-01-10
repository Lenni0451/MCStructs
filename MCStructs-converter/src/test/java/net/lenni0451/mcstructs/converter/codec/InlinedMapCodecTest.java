package net.lenni0451.mcstructs.converter.codec;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InlinedMapCodecTest {

    private static final Codec<C1> codec = MapCodecMerger.codec(
            Codec.STRING.mapCodec("s1").required(), C1::getS1,
            MapCodec.recursive(c -> MapCodecMerger.mapCodec(
                    Codec.STRING.mapCodec("s2").required(), C2::getS,
                    c.mapCodec("extra").optional().defaulted(null), C2::getExtra,
                    C2::new
            )), C1::asC2,
            C1::new
    );

    @Test
    void test() {
        CompoundTag tag = new CompoundTag()
                .addString("s1", "Test1")
                .addString("s2", "Test2")
                .add("extra", new CompoundTag()
                        .addString("s2", "Test3")
                        .add("extra", new CompoundTag()
                                .addString("s2", "Test4")));

        assertEquals(new C1("Test1", new C2("Test2", new C2("Test3", new C2("Test4", null)))), codec.deserialize(NbtConverter_v1_20_3.INSTANCE, tag).get());
    }

    @Test
    void testFail() {
        CompoundTag tag = new CompoundTag()
                .addString("s1", "Test1");

        assertThrows(IllegalStateException.class, () -> codec.deserialize(NbtConverter_v1_20_3.INSTANCE, tag).get());
    }


    @Getter
    @ToString
    @EqualsAndHashCode
    private static class C1 {
        private final String s1;
        private final String s2;
        @Nullable
        private final C1 extra;

        public C1(final String s1, final C2 extra) {
            this.s1 = s1;
            this.s2 = extra.getS();
            if (extra.getExtra() != null) this.extra = new C1(s1, extra.getExtra());
            else this.extra = null;
        }

        public C2 asC2() {
            return new C2(this.s2, this.extra == null ? null : this.extra.asC2());
        }
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    private static class C2 {
        private final String s;
        @Nullable
        private final C2 extra;
    }

}
