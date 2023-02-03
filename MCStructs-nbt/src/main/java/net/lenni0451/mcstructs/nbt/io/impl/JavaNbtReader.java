package net.lenni0451.mcstructs.nbt.io.impl;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.exceptions.NbtReadException;
import net.lenni0451.mcstructs.nbt.io.NbtHeader;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.io.types.INbtReader;
import net.lenni0451.mcstructs.nbt.tags.*;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Nbt reader implementation for the Java Edition Nbt format.
 */
public class JavaNbtReader implements INbtReader {

    @Override
    public NbtHeader readHeader(DataInput in, NbtReadTracker readTracker) throws IOException {
        byte type = in.readByte();
        if (NbtType.END.getId() == type) return NbtHeader.END;
        return new NbtHeader(NbtType.byId(type), in.readUTF());
    }

    @Override
    public ByteTag readByte(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(9);
        return new ByteTag(in.readByte());
    }

    @Override
    public ShortTag readShort(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(10);
        return new ShortTag(in.readShort());
    }

    @Override
    public IntTag readInt(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(12);
        return new IntTag(in.readInt());
    }

    @Override
    public LongTag readLong(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(16);
        return new LongTag(in.readLong());
    }

    @Override
    public FloatTag readFloat(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(12);
        return new FloatTag(in.readFloat());
    }

    @Override
    public DoubleTag readDouble(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(16);
        return new DoubleTag(in.readDouble());
    }

    @Override
    public ByteArrayTag readByteArray(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(24);
        int length = in.readInt();
        readTracker.read(length);
        byte[] value = new byte[length];
        in.readFully(value);
        return new ByteArrayTag(value);
    }

    @Override
    public StringTag readString(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(36);
        String value = in.readUTF();
        readTracker.read(2 * value.length());
        return new StringTag(value);
    }

    @Override
    public ListTag<?> readList(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(37);
        int typeId = in.readByte();
        int count = in.readInt();
        if (typeId == NbtType.END.getId() && count > 0) throw new NbtReadException("ListNbt with type END and count > 0");
        readTracker.read(4 * count);
        NbtType type = NbtType.byId(typeId);
        List<INbtTag> value = new ArrayList<>(Math.min(count, 512));
        for (int i = 0; i < count; i++) {
            readTracker.pushDepth();
            INbtTag tag = this.read(type, in, readTracker);
            readTracker.popDepth();
            value.add(tag);
        }
        return new ListTag<>(type, value);
    }

    @Override
    public CompoundTag readCompound(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(48);
        Map<String, INbtTag> value = new HashMap<>();
        while (true) {
            NbtHeader header = this.readHeader(in, readTracker);
            if (header.isEnd()) break;
            readTracker.read(28 + 2 * header.getName().length());

            readTracker.pushDepth();
            INbtTag tag = this.read(header.getType(), in, readTracker);
            readTracker.popDepth();
            if (value.put(header.getName(), tag) != null) readTracker.read(36);
        }
        return new CompoundTag(value);
    }

    @Override
    public IntArrayTag readIntArray(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(24);
        int length = in.readInt();
        readTracker.read(4 * length);
        int[] value = new int[length];
        for (int i = 0; i < value.length; i++) value[i] = in.readInt();
        return new IntArrayTag(value);
    }

    @Override
    public LongArrayTag readLongArray(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(24);
        int length = in.readInt();
        readTracker.read(8 * length);
        long[] value = new long[length];
        for (int i = 0; i < value.length; i++) value[i] = in.readLong();
        return new LongArrayTag(value);
    }

}
