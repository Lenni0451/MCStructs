package net.lenni0451.mcstructs.nbt;

import net.lenni0451.mcstructs.core.ICopyable;
import net.lenni0451.mcstructs.nbt.tags.*;

/**
 * The base interface for all Nbt tags.
 */
public interface INbtTag extends ICopyable<INbtTag> {

    /**
     * @return The type of this tag
     */
    NbtType getNbtType();

    @Override
    INbtTag copy();

    /**
     * @return If this tag is a byte tag
     */
    default boolean isByteTag() {
        return this instanceof ByteTag;
    }

    /**
     * Get this tag as a byte tag.
     *
     * @return This tag as a byte tag
     * @throws ClassCastException If this tag is not a byte tag
     */
    default ByteTag asByteTag() {
        return (ByteTag) this;
    }

    /**
     * @return If this tag is a short tag
     */
    default boolean isShortTag() {
        return this instanceof ShortTag;
    }

    /**
     * Get this tag as a short tag.
     *
     * @return This tag as a short tag
     * @throws ClassCastException If this tag is not a short tag
     */
    default ShortTag asShortTag() {
        return (ShortTag) this;
    }

    /**
     * @return If this tag is an int tag
     */
    default boolean isIntTag() {
        return this instanceof IntTag;
    }

    /**
     * Get this tag as an int tag.
     *
     * @return This tag as a int tag
     * @throws ClassCastException If this tag is not an int tag
     */
    default IntTag asIntTag() {
        return (IntTag) this;
    }

    /**
     * @return If this tag is a long tag
     */
    default boolean isLongTag() {
        return this instanceof LongTag;
    }

    /**
     * Get this tag as a long tag.
     *
     * @return This tag as a long tag
     * @throws ClassCastException If this tag is not a long tag
     */
    default LongTag asLongTag() {
        return (LongTag) this;
    }

    /**
     * @return If this tag is a float tag
     */
    default boolean isFloatTag() {
        return this instanceof FloatTag;
    }

    /**
     * Get this tag as a float tag.
     *
     * @return This tag as a float tag
     * @throws ClassCastException If this tag is not a float tag
     */
    default FloatTag asFloatTag() {
        return (FloatTag) this;
    }

    /**
     * @return If this tag is a double tag
     */
    default boolean isDoubleTag() {
        return this instanceof DoubleTag;
    }

    /**
     * Get this tag as a double tag.
     *
     * @return This tag as a double tag
     * @throws ClassCastException If this tag is not a double tag
     */
    default DoubleTag asDoubleTag() {
        return (DoubleTag) this;
    }

    /**
     * @return If this tag is a byte array tag
     */
    default boolean isByteArrayTag() {
        return this instanceof ByteArrayTag;
    }

    /**
     * Get this tag as a byte array tag.
     *
     * @return This tag as a byte array tag
     * @throws ClassCastException If this tag is not a byte array tag
     */
    default ByteArrayTag asByteArrayTag() {
        return (ByteArrayTag) this;
    }

    /**
     * @return If this tag is a string tag
     */
    default boolean isStringTag() {
        return this instanceof StringTag;
    }

    /**
     * Get this tag as a string tag.
     *
     * @return This tag as a string tag
     * @throws ClassCastException If this tag is not a string tag
     */
    default StringTag asStringTag() {
        return (StringTag) this;
    }

    /**
     * @return If this tag is a list tag
     */
    default boolean isListTag() {
        return this instanceof ListTag;
    }

    /**
     * Get this tag as a list tag.
     *
     * @param <T> The type of the list tag
     * @return This tag as a list tag
     * @throws ClassCastException If this tag is not a list tag
     */
    default <T extends INbtTag> ListTag<T> asListTag() {
        return (ListTag<T>) this;
    }

    /**
     * @return If this tag is a compound tag
     */
    default boolean isCompoundTag() {
        return this instanceof CompoundTag;
    }

    /**
     * Get this tag as a compound tag.
     *
     * @return This tag as a compound tag
     * @throws ClassCastException If this tag is not a compound tag
     */
    default CompoundTag asCompoundTag() {
        return (CompoundTag) this;
    }

    /**
     * @return If this tag is an int array tag
     */
    default boolean isIntArrayTag() {
        return this instanceof IntArrayTag;
    }

    /**
     * Get this tag as an int array tag.
     *
     * @return This tag as an int array tag
     * @throws ClassCastException If this tag is not an int array tag
     */
    default IntArrayTag asIntArrayTag() {
        return (IntArrayTag) this;
    }

    /**
     * @return If this tag is a long array tag
     */
    default boolean isLongArrayTag() {
        return this instanceof LongArrayTag;
    }

    /**
     * Get this tag as a long array tag.
     *
     * @return This tag as a long array tag
     * @throws ClassCastException If this tag is not a long array tag
     */
    default LongArrayTag asLongArrayTag() {
        return (LongArrayTag) this;
    }

    /**
     * @return If this tag is a number tag
     */
    default boolean isNumberTag() {
        return this instanceof INbtNumber;
    }

    /**
     * Get this tag as a number tag.
     *
     * @return This tag as a number tag
     * @throws ClassCastException If this tag is not a number tag
     */
    default INbtNumber asNumberTag() {
        return (INbtNumber) this;
    }

    /**
     * @return If this tag is an array tag
     */
    default boolean isArrayTag() {
        return this instanceof INbtArray;
    }

    /**
     * Get this tag as an array tag.
     *
     * @return This tag as an array tag
     * @throws ClassCastException If this tag is not an array tag
     */
    default INbtArray<?, ?, ?, ?> asArrayTag() {
        return (INbtArray<?, ?, ?, ?>) this;
    }

    boolean equals(final Object o);

    int hashCode();

    String toString();

}
