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

    private static final List<NbtTag> tags = new ArrayList<>();
    private static final Map<NbtType, Consumer<NbtTag>> asFunctions = new EnumMap<>(NbtType.class);

    static {
        tags.add(new ByteTag());
        tags.add(new ShortTag());
        tags.add(new IntTag());
        tags.add(new LongTag());
        tags.add(new FloatTag());
        tags.add(new DoubleTag());
        tags.add(new ByteArrayTag());
        tags.add(new StringTag());
        tags.add(new ListTag<>());
        tags.add(new CompoundTag());
        tags.add(new IntArrayTag());
        tags.add(new LongArrayTag());

        asFunctions.put(NbtType.BYTE, NbtTag::asByteTag);
        asFunctions.put(NbtType.SHORT, NbtTag::asShortTag);
        asFunctions.put(NbtType.INT, NbtTag::asIntTag);
        asFunctions.put(NbtType.LONG, NbtTag::asLongTag);
        asFunctions.put(NbtType.FLOAT, NbtTag::asFloatTag);
        asFunctions.put(NbtType.DOUBLE, NbtTag::asDoubleTag);
        asFunctions.put(NbtType.BYTE_ARRAY, NbtTag::asByteArrayTag);
        asFunctions.put(NbtType.STRING, NbtTag::asStringTag);
        asFunctions.put(NbtType.LIST, NbtTag::asListTag);
        asFunctions.put(NbtType.COMPOUND, NbtTag::asCompoundTag);
        asFunctions.put(NbtType.INT_ARRAY, NbtTag::asIntArrayTag);
        asFunctions.put(NbtType.LONG_ARRAY, NbtTag::asLongArrayTag);
    }

    @Test
    void testAsFunctions() {
        for (NbtTag tag : tags) {
            for (Map.Entry<NbtType, Consumer<NbtTag>> entry : asFunctions.entrySet()) {
                if (entry.getKey().equals(tag.getNbtType())) assertDoesNotThrow(() -> entry.getValue().accept(tag));
                else assertThrows(ClassCastException.class, () -> entry.getValue().accept(tag));
            }
        }
    }

}
