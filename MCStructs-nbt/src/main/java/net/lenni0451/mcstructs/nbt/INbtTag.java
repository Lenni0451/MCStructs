package net.lenni0451.mcstructs.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * The base interface for all Nbt tags.
 */
public interface INbtTag {

    /**
     * @return The type of this tag
     */
    NbtType getNbtType();

    /**
     * Read the tag value from a given {@link DataInput}.
     *
     * @param in          The input to read from
     * @param readTracker The read tracker
     * @throws IOException If an I/O error occurs
     */
    void read(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    /**
     * Write the tag value to a given {@link DataOutput}.
     *
     * @param out The output to write to
     * @throws IOException If an I/O error occurs
     */
    void write(final DataOutput out) throws IOException;

    /**
     * @return A copy of this tag and its value
     */
    INbtTag copy();

    boolean equals(final Object o);

    int hashCode();

    String toString();

}
