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
     * Get this tag as a byte tag.
     *
     * @return This tag as a byte tag
     * @throws ClassCastException If this tag is not a byte tag
     */
    default ByteTag asByteTag() {
        return (ByteTag) this;
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
     * Get this tag as an int tag.
     *
     * @return This tag as a int tag
     * @throws ClassCastException If this tag is not an int tag
     */
    default IntTag asIntTag() {
        return (IntTag) this;
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
     * Get this tag as a float tag.
     *
     * @return This tag as a float tag
     * @throws ClassCastException If this tag is not a float tag
     */
    default FloatTag asFloatTag() {
        return (FloatTag) this;
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
     * Get this tag as a byte array tag.
     *
     * @return This tag as a byte array tag
     * @throws ClassCastException If this tag is not a byte array tag
     */
    default ByteArrayTag asByteArrayTag() {
        return (ByteArrayTag) this;
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
     * Get this tag as a compound tag.
     *
     * @return This tag as a compound tag
     * @throws ClassCastException If this tag is not a compound tag
     */
    default CompoundTag asCompoundTag() {
        return (CompoundTag) this;
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
     * Get this tag as a long array tag.
     *
     * @return This tag as a long array tag
     * @throws ClassCastException If this tag is not a long array tag
     */
    default LongArrayTag asLongArrayTag() {
        return (LongArrayTag) this;
    }

    boolean equals(final Object o);

    int hashCode();

    String toString();

}
