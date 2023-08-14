package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtNumber;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;

import java.util.Objects;

public class LongTag implements INbtNumber {

    private long value;

    /**
     * Create a long tag with the value 0.
     */
    public LongTag() {
        this(0);
    }

    /**
     * Create a long tag with the given value.
     *
     * @param value The value
     */
    public LongTag(final long value) {
        this.value = value;
    }

    /**
     * @return The long value
     */
    public long getValue() {
        return this.value;
    }

    /**
     * Set the long value.
     *
     * @param value The new value
     * @return This tag
     */
    public LongTag setValue(final long value) {
        this.value = value;
        return this;
    }

    @Override
    public byte byteValue() {
        return (byte) (this.value & 0xFF);
    }

    @Override
    public short shortValue() {
        return (byte) (this.value & 0xFFFF);
    }

    @Override
    public int intValue() {
        return (int) this.value;
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
        return NbtType.LONG;
    }

    @Override
    public INbtTag copy() {
        return new LongTag(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongTag longTag = (LongTag) o;
        return this.value == longTag.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }

    @Override
    public String toString() {
        return "long(" + this.value + ")";
    }

}
