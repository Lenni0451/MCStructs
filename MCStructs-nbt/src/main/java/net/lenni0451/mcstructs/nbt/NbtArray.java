package net.lenni0451.mcstructs.nbt;

import net.lenni0451.mcstructs.nbt.tags.ListTag;

/**
 * An interface for all Nbt array tags.
 *
 * @param <TYPE> The nbt type of the tag
 * @param <PRIMITIVE_TAG> The nbt type of the array elements
 * @param <ARRAY> The array type
 * @param <PRIMITIVE> The primitive type of the array
 */
public interface NbtArray<TYPE extends NbtArray<TYPE, PRIMITIVE_TAG, ARRAY, PRIMITIVE>, PRIMITIVE_TAG extends NbtTag, ARRAY, PRIMITIVE> extends Iterable<PRIMITIVE> {

    /**
     * @return The array value
     */
    ARRAY getValue();

    /**
     * Set the array value.
     *
     * @param value The new value
     * @return This tag
     */
    TYPE setValue(final ARRAY value);

    /**
     * @return The length of the array
     */
    int getLength();

    /**
     * @return If the array is empty
     */
    boolean isEmpty();

    /**
     * @return The array as a {@link ListTag}
     */
    ListTag<PRIMITIVE_TAG> toListTag();

}
