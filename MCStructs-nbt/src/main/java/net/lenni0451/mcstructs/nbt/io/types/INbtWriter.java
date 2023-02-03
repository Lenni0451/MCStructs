package net.lenni0451.mcstructs.nbt.io.types;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.io.NbtHeader;
import net.lenni0451.mcstructs.nbt.tags.*;

import java.io.DataOutput;
import java.io.IOException;

public interface INbtWriter {

    void writeHeader(final DataOutput out, final NbtHeader header) throws IOException;

    default void write(final DataOutput out, final INbtTag tag) throws IOException {
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

    void writeByte(final DataOutput out, final ByteTag tag) throws IOException;

    void writeShort(final DataOutput out, final ShortTag tag) throws IOException;

    void writeInt(final DataOutput out, final IntTag value) throws IOException;

    void writeLong(final DataOutput out, final LongTag value) throws IOException;

    void writeFloat(final DataOutput out, final FloatTag value) throws IOException;

    void writeDouble(final DataOutput out, final DoubleTag value) throws IOException;

    void writeByteArray(final DataOutput out, final ByteArrayTag value) throws IOException;

    void writeString(final DataOutput out, final StringTag value) throws IOException;

    void writeList(final DataOutput out, final ListTag<?> value) throws IOException;

    void writeCompound(final DataOutput out, final CompoundTag value) throws IOException;

    void writeIntArray(final DataOutput out, final IntArrayTag value) throws IOException;

    void writeLongArray(final DataOutput out, final LongArrayTag value) throws IOException;

}
