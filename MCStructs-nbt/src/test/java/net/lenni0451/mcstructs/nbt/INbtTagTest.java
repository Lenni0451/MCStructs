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

class INbtTagTest {

    private static final List<INbtTag> tags = new ArrayList<>();
    private static final Map<NbtType, Consumer<INbtTag>> asFunctions = new EnumMap<>(NbtType.class);

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

        asFunctions.put(NbtType.BYTE, INbtTag::asByteTag);
        asFunctions.put(NbtType.SHORT, INbtTag::asShortTag);
        asFunctions.put(NbtType.INT, INbtTag::asIntTag);
        asFunctions.put(NbtType.LONG, INbtTag::asLongTag);
        asFunctions.put(NbtType.FLOAT, INbtTag::asFloatTag);
        asFunctions.put(NbtType.DOUBLE, INbtTag::asDoubleTag);
        asFunctions.put(NbtType.BYTE_ARRAY, INbtTag::asByteArrayTag);
        asFunctions.put(NbtType.STRING, INbtTag::asStringTag);
        asFunctions.put(NbtType.LIST, INbtTag::asListTag);
        asFunctions.put(NbtType.COMPOUND, INbtTag::asCompoundTag);
        asFunctions.put(NbtType.INT_ARRAY, INbtTag::asIntArrayTag);
        asFunctions.put(NbtType.LONG_ARRAY, INbtTag::asLongArrayTag);
    }

    @Test
    void testAsFunctions() {
        for (INbtTag tag : tags) {
            for (Map.Entry<NbtType, Consumer<INbtTag>> entry : asFunctions.entrySet()) {
                if (entry.getKey().equals(tag.getNbtType())) assertDoesNotThrow(() -> entry.getValue().accept(tag));
                else assertThrows(ClassCastException.class, () -> entry.getValue().accept(tag));
            }
        }
    }

}
