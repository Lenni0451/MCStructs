package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtNumber;
import net.lenni0451.mcstructs.nbt.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.NbtRegistry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteNbt implements INbtNumber {

    private byte value;

    public ByteNbt(final byte value) {
        this.value = value;
    }

    public byte getValue() {
        return this.value;
    }

    public void setValue(final byte value) {
        this.value = value;
    }

    @Override
    public byte byteValue() {
        return this.value;
    }

    @Override
    public short shortValue() {
        return this.value;
    }

    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    @Override
    public Number numberValue() {
        return this.value;
    }

    @Override
    public int getId() {
        return NbtRegistry.BYTE_NBT;
    }

    @Override
    public void read(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(72);
        this.value = in.readByte();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeByte(this.value);
    }

    @Override
    public String toString() {
        return this.value + "b";
    }

}
