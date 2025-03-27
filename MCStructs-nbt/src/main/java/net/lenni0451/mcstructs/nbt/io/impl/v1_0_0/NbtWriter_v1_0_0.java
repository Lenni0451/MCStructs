package net.lenni0451.mcstructs.nbt.io.impl.v1_0_0;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.io.NbtHeader;
import net.lenni0451.mcstructs.nbt.io.impl.NbtWriter;
import net.lenni0451.mcstructs.nbt.tags.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

/**
 * The Nbt writer implementation for the Java Edition Nbt format.
 */
@ParametersAreNonnullByDefault
public class NbtWriter_v1_0_0 implements NbtWriter {

    @Override
    public void writeType(DataOutput out, NbtType type) throws IOException {
        out.writeByte(type.getId());
    }

    @Override
    public void writeHeader(DataOutput out, NbtHeader header) throws IOException {
        if (header.isEnd()) {
            this.writeType(out, NbtType.END);
        } else {
            this.writeType(out, header.getType());
            out.writeUTF(header.getName());
        }
    }

    @Override
    public void writeByte(DataOutput out, ByteTag tag) throws IOException {
        out.writeByte(tag.getValue());
    }

    @Override
    public void writeShort(DataOutput out, ShortTag tag) throws IOException {
        out.writeShort(tag.getValue());
    }

    @Override
    public void writeInt(DataOutput out, IntTag value) throws IOException {
        out.writeInt(value.getValue());
    }

    @Override
    public void writeLong(DataOutput out, LongTag value) throws IOException {
        out.writeLong(value.getValue());
    }

    @Override
    public void writeFloat(DataOutput out, FloatTag value) throws IOException {
        out.writeFloat(value.getValue());
    }

    @Override
    public void writeDouble(DataOutput out, DoubleTag value) throws IOException {
        out.writeDouble(value.getValue());
    }

    @Override
    public void writeByteArray(DataOutput out, ByteArrayTag value) throws IOException {
        out.writeInt(value.getLength());
        out.write(value.getValue());
    }

    @Override
    public void writeString(DataOutput out, StringTag value) throws IOException {
        out.writeUTF(value.getValue());
    }

    @Override
    public void writeList(DataOutput out, ListTag<?> value) throws IOException {
        if (value.getType() == null) out.writeByte(NbtType.END.getId());
        else out.writeByte(value.getType().getId());
        out.writeInt(value.size());
        for (NbtTag tag : value.getValue()) this.write(out, tag);
    }

    @Override
    public void writeCompound(DataOutput out, CompoundTag value) throws IOException {
        for (Map.Entry<String, NbtTag> entry : value.getValue().entrySet()) {
            this.writeHeader(out, new NbtHeader(entry.getValue().getNbtType(), entry.getKey()));
            this.write(out, entry.getValue());
        }
        this.writeHeader(out, NbtHeader.END);
    }

}
