package net.lenni0451.mcstructs.nbt.io.impl;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.io.NbtHeader;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.tags.*;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.DataInput;
import java.io.IOException;

@ParametersAreNonnullByDefault
public interface NbtReader {

    /**
     * Read a Nbt type.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The type
     * @throws IOException If an I/O error occurs
     */
    @Nonnull
    NbtType readType(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    /**
     * Read the header of a Nbt tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The header
     * @throws IOException If an I/O error occurs
     */
    @Nonnull
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
    @Nonnull
    default NbtTag read(final NbtType type, final DataInput in, final NbtReadTracker readTracker) throws IOException {
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
    @Nonnull
    default ByteTag readByte(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support byte tags");
    }

    /**
     * Read a short tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    @Nonnull
    default ShortTag readShort(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support short tags");
    }

    /**
     * Read a int tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    @Nonnull
    default IntTag readInt(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support int tags");
    }

    /**
     * Read a long tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    @Nonnull
    default LongTag readLong(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support long tags");
    }

    /**
     * Read a float tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    @Nonnull
    default FloatTag readFloat(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support float tags");
    }

    /**
     * Read a double tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    @Nonnull
    default DoubleTag readDouble(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support double tags");
    }

    /**
     * Read a byte array tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    @Nonnull
    default ByteArrayTag readByteArray(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support byte array tags");
    }

    /**
     * Read a string tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    @Nonnull
    default StringTag readString(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support string tags");
    }

    /**
     * Read a list tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    @Nonnull
    default ListTag<?> readList(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support list tags");
    }

    /**
     * Read a compound tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    @Nonnull
    default CompoundTag readCompound(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support compound tags");
    }

    /**
     * Read a int array tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    @Nonnull
    default IntArrayTag readIntArray(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support int array tags");
    }

    /**
     * Read a long array tag.
     *
     * @param in          The data input
     * @param readTracker The read tracker
     * @return The tag
     * @throws IOException If an I/O error occurs
     */
    @Nonnull
    default LongArrayTag readLongArray(final DataInput in, final NbtReadTracker readTracker) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support long array tags");
    }

}
