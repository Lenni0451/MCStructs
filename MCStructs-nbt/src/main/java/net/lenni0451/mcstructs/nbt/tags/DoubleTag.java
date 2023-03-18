package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtNumber;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;

import java.util.Objects;

public class DoubleTag implements INbtNumber {

    private double value;

    /**
     * Create a double tag with the value 0.
     */
    public DoubleTag() {
        this(0);
    }

    /**
     * Create a double tag with the given value.
     *
     * @param value The value
     */
    public DoubleTag(final double value) {
        this.value = value;
    }

    /**
     * @return The double value
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Set the double value.
     *
     * @param value The new value
     * @return This tag
     */
    public DoubleTag setValue(final double value) {
        this.value = value;
        return this;
    }

    @Override
    public byte byteValue() {
        return (byte) (((int) Math.floor(this.value)) & 0xFF);
    }

    @Override
    public short shortValue() {
        return (byte) (((int) Math.floor(this.value)) & 0xFFFF);
    }

    @Override
    public int intValue() {
        return (int) Math.floor(this.value);
    }

    @Override
    public long longValue() {
        return (long) Math.floor(this.value);
    }

    @Override
    public float floatValue() {
        return (float) this.value;
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
        return NbtType.DOUBLE;
    }

    @Override
    public INbtTag copy() {
        return new DoubleTag(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleTag doubleTag = (DoubleTag) o;
        return Double.compare(doubleTag.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "double(" + this.value + ")";
    }

}
