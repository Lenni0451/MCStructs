package net.lenni0451.mcstructs.nbt;

import net.lenni0451.mcstructs.nbt.tags.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public enum NbtType {

    END(0, null, () -> null, null),
    BYTE(1, ByteTag.class, ByteTag::new, byte.class),
    SHORT(2, ShortTag.class, ShortTag::new, short.class),
    INT(3, IntTag.class, IntTag::new, int.class),
    LONG(4, LongTag.class, LongTag::new, long.class),
    FLOAT(5, FloatTag.class, FloatTag::new, float.class),
    DOUBLE(6, DoubleTag.class, DoubleTag::new, double.class),
    BYTE_ARRAY(7, ByteArrayTag.class, ByteArrayTag::new, byte[].class),
    STRING(8, StringTag.class, StringTag::new, String.class),
    LIST(9, ListTag.class, ListTag::new, List.class),
    COMPOUND(10, CompoundTag.class, CompoundTag::new, Map.class),
    INT_ARRAY(11, IntArrayTag.class, IntArrayTag::new, int[].class),
    LONG_ARRAY(12, LongArrayTag.class, LongArrayTag::new, long[].class);

    public static NbtType byName(final String name) {
        for (NbtType type : NbtType.values()) {
            if (type.name().equals(name)) return type;
        }
        return null;
    }

    public static NbtType byId(final int id) {
        for (NbtType type : NbtType.values()) {
            if (type.getId() == id) return type;
        }
        return null;
    }

    public static NbtType byClass(final Class<? extends INbtTag> tagClass) {
        for (NbtType type : NbtType.values()) {
            if (Objects.equals(type.getTagClass(), tagClass)) return type;
        }
        return null;
    }

    public static NbtType byDataType(final Class<?> dataType) {
        for (NbtType type : NbtType.values()) {
            if (Objects.equals(type.getDataType(), dataType)) return type;
        }
        return null;
    }


    private final int id;
    private final Class<? extends INbtTag> tagClass;
    private final Supplier<?> tagSupplier;
    private final Class<?> dataType;

    NbtType(final int id, final Class<? extends INbtTag> tagClass, final Supplier<?> tagSupplier, final Class<?> dataType) {
        this.id = id;
        this.tagClass = tagClass;
        this.tagSupplier = tagSupplier;
        this.dataType = dataType;
    }

    public int getId() {
        return this.id;
    }

    public Class<? extends INbtTag> getTagClass() {
        return this.tagClass;
    }

    public INbtTag newInstance() {
        if (this.tagClass == null) throw new IllegalStateException("Unable to allocate instance of END tag");
        return (INbtTag) this.tagSupplier.get();
    }

    public Class<?> getDataType() {
        return this.dataType;
    }

    public boolean isNumber() {
        if (this.dataType == null) return false;
        return byte.class.equals(this.dataType) || short.class.equals(this.dataType) || int.class.equals(this.dataType) || long.class.equals(this.dataType) || float.class.equals(this.dataType) || double.class.equals(this.dataType);
    }

}
