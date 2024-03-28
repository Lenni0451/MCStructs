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
     * Create a byte tag with the given boolean value.<br>
     * {@code true} {@literal ->} {@code 1}<br>
     * {@code false} {@literal ->} {@code 0}
     *
     * @param value The value
     */
    public ByteTag(final boolean value) {
        this((byte) (value ? 1 : 0));
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
     * @return This tag
     */
    public ByteTag setValue(final byte value) {
        this.value = value;
        return this;
    }

    /**
     * Set the value to the given boolean value.<br>
     * {@code true} {@literal ->} {@code 1}<br>
     * {@code false} {@literal ->} {@code 0}
     *
     * @param value The new value
     */
    public void setValue(final boolean value) {
        this.value = (byte) (value ? 1 : 0);
    }

    /**
     * Get the value as a boolean.<br>
     * {@code 0} {@literal ->} {@code false}<br>
     * {@code != 0} {@literal ->} {@code true}
     *
     * @return The value as a boolean
     */
    public boolean booleanValue() {
        return this.value != 0;
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
        return this.value == byteTag.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }

    @Override
    public String toString() {
        return "byte(" + this.value + ")";
    }

}
