package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.NbtNumber;
import net.lenni0451.mcstructs.nbt.NbtType;

import java.util.Objects;

public class FloatTag implements NbtNumber {

    private float value;

    /**
     * Create a float tag with the value 0.
     */
    public FloatTag() {
        this(0);
    }

    /**
     * Create a float tag with the given value.
     *
     * @param value The value
     */
    public FloatTag(final float value) {
        this.value = value;
    }

    /**
     * @return The float value
     */
    public float getValue() {
        return this.value;
    }

    /**
     * Set the float value.
     *
     * @param value The new value
     * @return This tag
     */
    public FloatTag setValue(final float value) {
        this.value = value;
        return this;
    }

    @Override
    public byte byteValue() {
        return (byte) (((int) Math.floor(this.value)) & 0xFF);
    }

    @Override
    public short shortValue() {
        return (short) (((int) Math.floor(this.value)) & 0xFFFF);
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
        return NbtType.FLOAT;
    }

    @Override
    public FloatTag copy() {
        return new FloatTag(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloatTag floatTag = (FloatTag) o;
        return Float.compare(floatTag.value, this.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }

    @Override
    public String toString() {
        return "float(" + this.value + ")";
    }

}
