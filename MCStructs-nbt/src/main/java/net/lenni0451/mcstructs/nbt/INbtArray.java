package net.lenni0451.mcstructs.nbt;

import net.lenni0451.mcstructs.nbt.tags.ListTag;

/**
 * An interface for all Nbt array tags.
 *
 * @param <T> The nbt type of the tag
 * @param <N> The nbt type of the array elements
 * @param <A> The array type
 * @param <P> The primitive type of the array
 */
public interface INbtArray<T extends INbtArray<T, N, A, P>, N extends INbtTag, A, P> extends Iterable<P> {

    /**
     * @return The array value
     */
    A getValue();

    /**
     * Set the array value.
     *
     * @param value The new value
     * @return This tag
     */
    T setValue(final A value);

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
    ListTag<N> toListTag();

}
