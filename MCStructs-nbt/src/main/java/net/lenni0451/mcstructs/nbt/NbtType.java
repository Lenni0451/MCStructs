package net.lenni0451.mcstructs.nbt;

import net.lenni0451.mcstructs.nbt.tags.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * The list of Nbt types.
 */
public enum NbtType {

    /**
     * The end tag is only used for reading/writing and can not be instantiated.<br>
     * It should <b>not</b> be used for anything else.
     */
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
    /**
     * The long array tag has been added in minecraft 1.12.
     */
    LONG_ARRAY(12, LongArrayTag.class, LongArrayTag::new, long[].class);

    /**
     * Get a type by its name.
     *
     * @param name The name of the type
     * @return The type or null if not found
     */
    @Nullable
    public static NbtType byName(final String name) {
        for (NbtType type : NbtType.values()) {
            if (type.name().equals(name)) return type;
        }
        return null;
    }

    /**
     * Get a type by its id.
     *
     * @param id The id of the type
     * @return The type or null if not found
     */
    @Nullable
    public static NbtType byId(final int id) {
        for (NbtType type : NbtType.values()) {
            if (type.getId() == id) return type;
        }
        return null;
    }

    /**
     * Get a type by its class.
     *
     * @param tagClass The class of the type
     * @return The type or null if not found
     */
    @Nullable
    public static NbtType byClass(final Class<? extends NbtTag> tagClass) {
        for (NbtType type : NbtType.values()) {
            if (Objects.equals(type.getTagClass(), tagClass)) return type;
        }
        return null;
    }

    /**
     * Get a type by its primitive class.
     *
     * @param dataType The primitive class of the type
     * @return The type or null if not found
     */
    @Nullable
    public static NbtType byDataType(final Class<?> dataType) {
        for (NbtType type : NbtType.values()) {
            if (Objects.equals(type.getDataType(), dataType)) return type;
        }
        return null;
    }


    private final int id;
    private final Class<? extends NbtTag> tagClass;
    private final Supplier<?> tagSupplier;
    private final Class<?> dataType;

    NbtType(final int id, final Class<? extends NbtTag> tagClass, final Supplier<?> tagSupplier, final Class<?> dataType) {
        this.id = id;
        this.tagClass = tagClass;
        this.tagSupplier = tagSupplier;
        this.dataType = dataType;
    }

    /**
     * @return The id of the type
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return The class of the type
     */
    public Class<? extends NbtTag> getTagClass() {
        return this.tagClass;
    }

    /**
     * @return A new instance of the tag
     * @throws IllegalStateException If the type is {@link #END}
     */
    public NbtTag newInstance() {
        if (this.tagClass == null) throw new IllegalStateException("Unable to create a new instance of END tag");
        return (NbtTag) this.tagSupplier.get();
    }

    /**
     * @return The primitive class of the type
     */
    public Class<?> getDataType() {
        return this.dataType;
    }

    /**
     * @return If the type is a number
     */
    public boolean isNumber() {
        if (this.dataType == null) return false;
        return byte.class.equals(this.dataType) || short.class.equals(this.dataType) || int.class.equals(this.dataType) || long.class.equals(this.dataType) || float.class.equals(this.dataType) || double.class.equals(this.dataType);
    }

}
