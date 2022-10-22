package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.NbtRegistry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteArrayNbt implements INbtTag {

    private byte[] value;

    public ByteArrayNbt(final byte[] value) {
        this.value = value;
    }

    public byte[] getValue() {
        return this.value;
    }

    public void setValue(final byte[] value) {
        this.value = value;
    }

    public byte get(final int index) {
        return this.value[index];
    }

    public void set(final int index, final byte b) {
        this.value[index] = b;
    }

    public void add(final byte b) {
        byte[] newValue = new byte[this.value.length + 1];
        System.arraycopy(this.value, 0, newValue, 0, this.value.length);
        newValue[this.value.length] = b;
        this.value = newValue;
    }

    @Override
    public int getId() {
        return NbtRegistry.BYTE_ARRAY_NBT;
    }

    @Override
    public void read(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(192);
        int length = in.readInt();
        readTracker.read(length * 8);
        byte[] value = new byte[length];
        in.readFully(value);
        this.value = value;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.value.length);
        out.write(this.value);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("[B;");
        for (int i = 0; i < this.value.length; i++) out.append(i).append(",");
        if (this.value.length > 0) out.deleteCharAt(out.length() - 1);
        return out.append("]").toString();
    }

}
