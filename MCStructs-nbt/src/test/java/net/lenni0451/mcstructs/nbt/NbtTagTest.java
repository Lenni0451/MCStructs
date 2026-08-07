package net.lenni0451.mcstructs.nbt;

import net.lenni0451.mcstructs.nbt.tags.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NbtTagTest {

    private static final List<NbtTag> TAGS = new ArrayList<>();
    private static final Map<NbtType, Consumer<NbtTag>> AS_FUNCTIONS = new EnumMap<>(NbtType.class);

    static {
        TAGS.add(new ByteTag());
        TAGS.add(new ShortTag());
        TAGS.add(new IntTag());
        TAGS.add(new LongTag());
        TAGS.add(new FloatTag());
        TAGS.add(new DoubleTag());
        TAGS.add(new ByteArrayTag());
        TAGS.add(new StringTag());
        TAGS.add(new ListTag<>());
        TAGS.add(new CompoundTag());
        TAGS.add(new IntArrayTag());
        TAGS.add(new LongArrayTag());

        AS_FUNCTIONS.put(NbtType.BYTE, NbtTag::asByteTag);
        AS_FUNCTIONS.put(NbtType.SHORT, NbtTag::asShortTag);
        AS_FUNCTIONS.put(NbtType.INT, NbtTag::asIntTag);
        AS_FUNCTIONS.put(NbtType.LONG, NbtTag::asLongTag);
        AS_FUNCTIONS.put(NbtType.FLOAT, NbtTag::asFloatTag);
        AS_FUNCTIONS.put(NbtType.DOUBLE, NbtTag::asDoubleTag);
        AS_FUNCTIONS.put(NbtType.BYTE_ARRAY, NbtTag::asByteArrayTag);
        AS_FUNCTIONS.put(NbtType.STRING, NbtTag::asStringTag);
        AS_FUNCTIONS.put(NbtType.LIST, NbtTag::asListTag);
        AS_FUNCTIONS.put(NbtType.COMPOUND, NbtTag::asCompoundTag);
        AS_FUNCTIONS.put(NbtType.INT_ARRAY, NbtTag::asIntArrayTag);
        AS_FUNCTIONS.put(NbtType.LONG_ARRAY, NbtTag::asLongArrayTag);
    }

    @Test
    void testAsFunctions() {
        for (NbtTag tag : TAGS) {
            for (Map.Entry<NbtType, Consumer<NbtTag>> entry : AS_FUNCTIONS.entrySet()) {
                if (entry.getKey().equals(tag.getNbtType())) assertDoesNotThrow(() -> entry.getValue().accept(tag));
                else assertThrows(ClassCastException.class, () -> entry.getValue().accept(tag));
            }
        }
    }

}
