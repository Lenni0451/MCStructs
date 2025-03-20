package net.lenni0451.mcstructs.nbt.io.impl.v1_0_0;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.exceptions.NbtReadException;
import net.lenni0451.mcstructs.nbt.io.NbtHeader;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.io.impl.NbtReader;
import net.lenni0451.mcstructs.nbt.tags.*;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Nbt reader implementation for the Java Edition Nbt format.
 */
@ParametersAreNonnullByDefault
public class NbtReader_v1_0_0 implements NbtReader {

    @Nonnull
    @Override
    public NbtType readType(DataInput in, NbtReadTracker readTracker) throws IOException {
        byte id = in.readByte();
        NbtType type = NbtType.byId(id);
        if (type == null) throw new IOException("Unknown Nbt type: " + id);
        return type;
    }

    @Nonnull
    @Override
    public NbtHeader readHeader(DataInput in, NbtReadTracker readTracker) throws IOException {
        NbtType type = this.readType(in, readTracker);
        if (NbtType.END.equals(type)) return NbtHeader.END;
        return new NbtHeader(type, in.readUTF());
    }

    @Nonnull
    @Override
    public ByteTag readByte(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(9);
        return new ByteTag(in.readByte());
    }

    @Nonnull
    @Override
    public ShortTag readShort(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(10);
        return new ShortTag(in.readShort());
    }

    @Nonnull
    @Override
    public IntTag readInt(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(12);
        return new IntTag(in.readInt());
    }

    @Nonnull
    @Override
    public LongTag readLong(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(16);
        return new LongTag(in.readLong());
    }

    @Nonnull
    @Override
    public FloatTag readFloat(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(12);
        return new FloatTag(in.readFloat());
    }

    @Nonnull
    @Override
    public DoubleTag readDouble(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(16);
        return new DoubleTag(in.readDouble());
    }

    @Nonnull
    @Override
    public ByteArrayTag readByteArray(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(24);
        int length = in.readInt();
        readTracker.read(length, 1);
        byte[] value = new byte[length];
        in.readFully(value);
        return new ByteArrayTag(value);
    }

    @Nonnull
    @Override
    public StringTag readString(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(36);
        String value = in.readUTF();
        readTracker.read(value.length(), 2);
        return new StringTag(value);
    }

    @Nonnull
    @Override
    public ListTag<?> readList(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(37);
        int typeId = in.readByte();
        int count = in.readInt();
        if (typeId == NbtType.END.getId() && count > 0) throw new NbtReadException("ListNbt with type END and count > 0");
        readTracker.read(count, 4);
        NbtType type = NbtType.byId(typeId);
        List<NbtTag> value = new ArrayList<>(Math.min(count, 512));
        for (int i = 0; i < count; i++) {
            readTracker.pushDepth();
            NbtTag tag = this.read(type, in, readTracker);
            readTracker.popDepth();
            value.add(tag);
        }
        return new ListTag<>(type, value);
    }

    @Nonnull
    @Override
    public CompoundTag readCompound(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(48);
        Map<String, NbtTag> value = new HashMap<>();
        while (true) {
            NbtHeader header = this.readHeader(in, readTracker);
            if (header.isEnd()) break;
            readTracker.read(28);
            readTracker.read(header.getName().length(), 2);

            readTracker.pushDepth();
            NbtTag tag = this.read(header.getType(), in, readTracker);
            readTracker.popDepth();
            if (value.put(header.getName(), tag) != null) readTracker.read(36);
        }
        return new CompoundTag(value);
    }

}
