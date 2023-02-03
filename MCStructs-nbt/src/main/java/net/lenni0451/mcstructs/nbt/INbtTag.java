package net.lenni0451.mcstructs.nbt;

/**
 * The base interface for all Nbt tags.
 */
public interface INbtTag {

    /**
     * @return The type of this tag
     */
    NbtType getNbtType();

    /**
     * @return A copy of this tag and its value
     */
    INbtTag copy();

    boolean equals(final Object o);

    int hashCode();

    String toString();

}
