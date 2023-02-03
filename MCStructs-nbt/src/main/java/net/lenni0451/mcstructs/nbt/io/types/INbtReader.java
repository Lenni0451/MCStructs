package net.lenni0451.mcstructs.nbt.io.types;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.io.NbtHeader;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.tags.*;

import java.io.DataInput;
import java.io.IOException;

public interface INbtReader {

    /**
     * Read the header of a Nbt tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The header
     * @throws IOException If an I/O error occurs
     */
    NbtHeader readHeader(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    /**
     * Read a Nbt tag by its type.<br>
     * Automatically calls the correct method for the tag type.
     *
     * @param type        The tag type
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    default INbtTag read(final NbtType type, final DataInput in, final NbtReadTracker readTracker) throws IOException {
        switch (type) {
            case BYTE:
                return this.readByte(in, readTracker);
            case SHORT:
                return this.readShort(in, readTracker);
            case INT:
                return this.readInt(in, readTracker);
            case LONG:
                return this.readLong(in, readTracker);
            case FLOAT:
                return this.readFloat(in, readTracker);
            case DOUBLE:
                return this.readDouble(in, readTracker);
            case BYTE_ARRAY:
                return this.readByteArray(in, readTracker);
            case STRING:
                return this.readString(in, readTracker);
            case LIST:
                return this.readList(in, readTracker);
            case COMPOUND:
                return this.readCompound(in, readTracker);
            case INT_ARRAY:
                return this.readIntArray(in, readTracker);
            case LONG_ARRAY:
                return this.readLongArray(in, readTracker);
            default:
                throw new IOException("Unknown tag type: " + type);
        }
    }

    /**
     * Read a byte tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    ByteTag readByte(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    /**
     * Read a short tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    ShortTag readShort(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    /**
     * Read a int tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    IntTag readInt(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    /**
     * Read a long tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    LongTag readLong(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    /**
     * Read a float tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    FloatTag readFloat(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    /**
     * Read a double tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    DoubleTag readDouble(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    /**
     * Read a byte array tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    ByteArrayTag readByteArray(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    /**
     * Read a string tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    StringTag readString(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    /**
     * Read a list tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    ListTag<?> readList(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    /**
     * Read a compound tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    CompoundTag readCompound(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    /**
     * Read a int array tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    IntArrayTag readIntArray(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    /**
     * Read a long array tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    LongArrayTag readLongArray(final DataInput in, final NbtReadTracker readTracker) throws IOException;

}
