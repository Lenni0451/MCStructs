package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtNumber;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;

import java.util.Objects;

public class ByteTag implements INbtNumber {

    private byte value;

    /**
     * Create a byte tag with the value 0.
     */
    public ByteTag() {
        this((byte) 0);
    }

    /**
     * Create a byte tag with the given value.
     *
     * @param value The value
     */
    public ByteTag(final byte value) {
        this.value = value;
    }

    /**
     * @return The byte value
     */
    public byte getValue() {
        return this.value;
    }

    /**
     * Set the byte value.
     *
     * @param value The new value
     */
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
    public NbtType getNbtType() {
        return NbtType.BYTE;
    }

    @Override
    public INbtTag copy() {
        return new ByteTag(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteTag byteTag = (ByteTag) o;
        return value == byteTag.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "byte(" + this.value + ")";
    }

}
