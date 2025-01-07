package net.lenni0451.mcstructs.nbt.io.impl;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.io.NbtHeader;
import net.lenni0451.mcstructs.nbt.tags.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.DataOutput;
import java.io.IOException;

@ParametersAreNonnullByDefault
public interface NbtWriter {

    /**
     * Write a Nbt type.
     *
     * @param out  The data output
     * @param type The type
     * @throws IOException If an I/O error occurs
     */
    void writeType(final DataOutput out, final NbtType type) throws IOException;

    /**
     * Write the header of a Nbt tag.
     *
     * @param out    The data output
     * @param header The header
     * @throws IOException If an I/O error occurs
     */
    void writeHeader(final DataOutput out, final NbtHeader header) throws IOException;

    /**
     * Write a generic Nbt tag.<br>
     * Automatically calls the correct method for the tag type.
     *
     * @param out The data output
     * @param tag The tag
     * @throws IOException If an I/O error occurs
     */
    default void write(final DataOutput out, final NbtTag tag) throws IOException {
        if (tag instanceof ByteTag) this.writeByte(out, (ByteTag) tag);
        else if (tag instanceof ShortTag) this.writeShort(out, (ShortTag) tag);
        else if (tag instanceof IntTag) this.writeInt(out, (IntTag) tag);
        else if (tag instanceof LongTag) this.writeLong(out, (LongTag) tag);
        else if (tag instanceof FloatTag) this.writeFloat(out, (FloatTag) tag);
        else if (tag instanceof DoubleTag) this.writeDouble(out, (DoubleTag) tag);
        else if (tag instanceof ByteArrayTag) this.writeByteArray(out, (ByteArrayTag) tag);
        else if (tag instanceof StringTag) this.writeString(out, (StringTag) tag);
        else if (tag instanceof ListTag) this.writeList(out, (ListTag<?>) tag);
        else if (tag instanceof CompoundTag) this.writeCompound(out, (CompoundTag) tag);
        else if (tag instanceof IntArrayTag) this.writeIntArray(out, (IntArrayTag) tag);
        else if (tag instanceof LongArrayTag) this.writeLongArray(out, (LongArrayTag) tag);
        else throw new IllegalArgumentException("Unknown tag type: " + tag.getClass().getName());
    }

    /**
     * Write a byte tag.
     *
     * @param out The data output
     * @param tag The byte tag
     * @throws IOException If an I/O error occurs
     */
    default void writeByte(final DataOutput out, final ByteTag tag) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support byte tags");
    }

    /**
     * Write a short tag.
     *
     * @param out The data output
     * @param tag The short tag
     * @throws IOException If an I/O error occurs
     */
    default void writeShort(final DataOutput out, final ShortTag tag) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support short tags");
    }

    /**
     * Write an int tag.
     *
     * @param out   The data output
     * @param value The int tag
     * @throws IOException If an I/O error occurs
     */
    default void writeInt(final DataOutput out, final IntTag value) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support int tags");
    }

    /**
     * Write a long tag.
     *
     * @param out   The data output
     * @param value The long tag
     * @throws IOException If an I/O error occurs
     */
    default void writeLong(final DataOutput out, final LongTag value) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support long tags");
    }

    /**
     * Write a float tag.
     *
     * @param out   The data output
     * @param value The float tag
     * @throws IOException If an I/O error occurs
     */
    default void writeFloat(final DataOutput out, final FloatTag value) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support float tags");
    }

    /**
     * Write a double tag.
     *
     * @param out   The data output
     * @param value The double tag
     * @throws IOException If an I/O error occurs
     */
    default void writeDouble(final DataOutput out, final DoubleTag value) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support double tags");
    }

    /**
     * Write a byte array tag.
     *
     * @param out   The data output
     * @param value The byte array tag
     * @throws IOException If an I/O error occurs
     */
    default void writeByteArray(final DataOutput out, final ByteArrayTag value) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support byte array tags");
    }

    /**
     * Write a string tag.
     *
     * @param out   The data output
     * @param value The string tag
     * @throws IOException If an I/O error occurs
     */
    default void writeString(final DataOutput out, final StringTag value) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support string tags");
    }

    /**
     * Write a list tag.
     *
     * @param out   The data output
     * @param value The list tag
     * @throws IOException If an I/O error occurs
     */
    default void writeList(final DataOutput out, final ListTag<?> value) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support list tags");
    }

    /**
     * Write a compound tag.
     *
     * @param out   The data output
     * @param value The compound tag
     * @throws IOException If an I/O error occurs
     */
    default void writeCompound(final DataOutput out, final CompoundTag value) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support compound tags");
    }

    /**
     * Write an int array tag.
     *
     * @param out   The data output
     * @param value The int array tag
     * @throws IOException If an I/O error occurs
     */
    default void writeIntArray(final DataOutput out, final IntArrayTag value) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support int array tags");
    }

    /**
     * Write a long array tag.<br>
     * Long array tags have been added in 1.12.
     *
     * @param out   The data output
     * @param value The long array tag
     * @throws IOException If an I/O error occurs
     */
    default void writeLongArray(final DataOutput out, final LongArrayTag value) throws IOException {
        throw new UnsupportedOperationException("This Nbt version does not support long array tags");
    }

}
