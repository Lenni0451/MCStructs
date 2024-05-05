package net.lenni0451.mcstructs.converter.codec;

import com.sun.istack.internal.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.lenni0451.mcstructs.converter.Result;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecursiveCodecTest {

    private static final Codec<Impl> IMPL = Codec.recursive(thiz -> MapCodec.of(
            Codec.STRING.mapCodec("name"), Impl::getName,
            thiz.mapCodec("hidden").optionalDefault(() -> null), Impl::getHidden,
            Impl::new
    ));

    @Test
    void test() {
        CompoundTag tag = new CompoundTag();
        tag.add("name", "Test");
        tag.add("hidden", new CompoundTag().addString("name", "Hidden"));

        Result<Impl> result = IMPL.deserialize(NbtConverter_v1_20_3.INSTANCE, tag);
        assertTrue(result.isSuccessful());
        assertEquals(new Impl("Test", new Impl("Hidden", null)), result.get());
    }


    @Getter
    @EqualsAndHashCode
    private static class Impl {
        private final String name;
        @Nullable
        private final Impl hidden;

        public Impl(final String name, @Nullable final Impl hidden) {
            this.name = name;
            this.hidden = hidden;
        }
    }

}
