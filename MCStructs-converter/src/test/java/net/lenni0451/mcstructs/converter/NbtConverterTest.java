package net.lenni0451.mcstructs.converter;

import net.lenni0451.mcstructs.converter.impl.NbtConverter_v1_21_5;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NbtConverterTest {

    private static final DataConverter<NbtTag> CONVERTER = new NbtConverter_v1_21_5();

    @Test
    void testMarkers() {
        ListTag<CompoundTag> markers = new ListTag<CompoundTag>()
                .add(new CompoundTag().addInt("", 12))
                .add(new CompoundTag().addBoolean("", true))
                .add(new CompoundTag().addString("test", "string"));

        List<NbtTag> tags = CONVERTER.asList(markers).get();
        assertEquals(3, tags.size());
        assertEquals(new IntTag(12), tags.get(0));
        assertEquals(new ByteTag(true), tags.get(1));
        assertEquals(new CompoundTag().addString("test", "string"), tags.get(2));
    }

    @Test
    void testNumberArrays() {
        byte[] byteArray = new byte[]{0, 1, 2, 3, 4, 5};
        int[] intArray = new int[]{0, 1, 2, 3, 4, 5};
        long[] longArray = new long[]{0, 1, 2, 3, 4, 5};

        NbtTag[] input = new NbtTag[]{
                new ByteArrayTag(byteArray),
                new IntArrayTag(intArray),
                new LongArrayTag(longArray),
                new ByteArrayTag(byteArray).toListTag(),
                new IntArrayTag(intArray).toListTag(),
                new LongArrayTag(longArray).toListTag(),
                this.mark(new ByteArrayTag(byteArray).toListTag()),
                this.mark(new IntArrayTag(intArray).toListTag()),
                this.mark(new LongArrayTag(longArray).toListTag()),
        };
        for (NbtTag tag : input) {
            assertArrayEquals(byteArray, CONVERTER.asByteArray(tag).get());
            assertArrayEquals(intArray, CONVERTER.asIntArray(tag).get());
            assertArrayEquals(longArray, CONVERTER.asLongArray(tag).get());
        }
    }

    @Test
    void testToMarker() {
        NbtTag list = CONVERTER.emptyList();
        list = CONVERTER.mergeList(list, new IntTag(12)).get();
        list = CONVERTER.mergeList(list, new ByteTag(true)).get();
        list = CONVERTER.mergeList(list, new CompoundTag().addString("test", "string")).get();

        ListTag<CompoundTag> markers = new ListTag<CompoundTag>()
                .add(new CompoundTag().addInt("", 12))
                .add(new CompoundTag().addBoolean("", true))
                .add(new CompoundTag().addString("test", "string"));
        assertEquals(markers, list);
    }

    @Test
    void listMerge() {
        NbtTag byteList = CONVERTER.emptyList();
        for (int i = 0; i < 5; i++) byteList = CONVERTER.mergeList(byteList, new ByteTag((byte) i)).get();
        assertArrayEquals(new byte[]{0, 1, 2, 3, 4}, CONVERTER.asByteArray(byteList).get());

        NbtTag intList = CONVERTER.emptyList();
        for (int i = 0; i < 5; i++) intList = CONVERTER.mergeList(intList, new IntTag(i)).get();
        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, CONVERTER.asIntArray(intList).get());

        NbtTag longList = CONVERTER.emptyList();
        for (int i = 0; i < 5; i++) longList = CONVERTER.mergeList(longList, new LongTag(i)).get();
        assertArrayEquals(new long[]{0, 1, 2, 3, 4}, CONVERTER.asLongArray(longList).get());
    }

    private ListTag<CompoundTag> mark(final ListTag<?> list) {
        ListTag<CompoundTag> out = new ListTag<>();
        for (NbtTag tag : list) {
            if (tag instanceof CompoundTag) out.add(tag.asCompoundTag());
            else out.add(new CompoundTag().add("", tag));
        }
        return out;
    }

}
