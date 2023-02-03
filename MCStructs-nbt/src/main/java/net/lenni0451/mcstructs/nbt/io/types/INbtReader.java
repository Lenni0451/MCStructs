package net.lenni0451.mcstructs.nbt.io.types;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.io.NbtHeader;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.tags.*;

import java.io.DataInput;
import java.io.IOException;

public interface INbtReader {

    NbtHeader readHeader(final DataInput in, final NbtReadTracker readTracker) throws IOException;

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

    ByteTag readByte(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    ShortTag readShort(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    IntTag readInt(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    LongTag readLong(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    FloatTag readFloat(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    DoubleTag readDouble(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    ByteArrayTag readByteArray(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    StringTag readString(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    ListTag<?> readList(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    CompoundTag readCompound(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    IntArrayTag readIntArray(final DataInput in, final NbtReadTracker readTracker) throws IOException;

    LongArrayTag readLongArray(final DataInput in, final NbtReadTracker readTracker) throws IOException;

}
