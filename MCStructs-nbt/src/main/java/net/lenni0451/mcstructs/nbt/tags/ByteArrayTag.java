package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.NbtType;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class ByteArrayTag implements INbtTag, Iterable<Byte> {

    private byte[] value;

    /**
     * Create an empty byte array tag.
     */
    public ByteArrayTag() {
        this(new byte[0]);
    }

    /**
     * Create a byte array tag from a {@link ByteTag} {@link ListTag}.
     *
     * @param list The list tag
     */
    public ByteArrayTag(final ListTag<ByteTag> list) {
        this.value = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) this.value[i] = list.get(i).getValue();
    }

    /**
     * Create a byte array tag from a byte array.
     *
     * @param value The byte array
     */
    public ByteArrayTag(final byte[] value) {
        this.value = value;
    }

    /**
     * @return The byte array value
     */
    public byte[] getValue() {
        return this.value;
    }

    /**
     * Set the byte array value.
     *
     * @param value The new value
     */
    public void setValue(final byte[] value) {
        this.value = value;
    }

    /**
     * Get a byte from the array.
     *
     * @param index The index
     * @return The byte
     * @throws ArrayIndexOutOfBoundsException If the index is out of bounds
     */
    public byte get(final int index) {
        return this.value[index];
    }

    /**
     * Set a byte in the array.
     *
     * @param index The index
     * @param b     The new value
     * @throws ArrayIndexOutOfBoundsException If the index is out of bounds
     */
    public void set(final int index, final byte b) {
        this.value[index] = b;
    }

    /**
     * Add a byte to the array.<br>
     * This will create a new array and copy the old one into it.
     *
     * @param b The byte
     */
    public void add(final byte b) {
        byte[] newValue = new byte[this.value.length + 1];
        System.arraycopy(this.value, 0, newValue, 0, this.value.length);
        newValue[this.value.length] = b;
        this.value = newValue;
    }

    /**
     * @return The length of the array
     */
    public int getLength() {
        return this.value.length;
    }

    /**
     * @return If the byte array is empty
     */
    public boolean isEmpty() {
        return this.value.length == 0;
    }

    @Override
    public NbtType getNbtType() {
        return NbtType.BYTE_ARRAY;
    }

    @Override
    public void read(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(24);
        int length = in.readInt();
        readTracker.read(length);
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
    public INbtTag copy() {
        return new ByteArrayTag(this.value.clone());
    }

    @Override
    public Iterator<Byte> iterator() {
        return new Iterator<Byte>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < ByteArrayTag.this.value.length;
            }

            @Override
            public Byte next() {
                return ByteArrayTag.this.value[this.index++];
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteArrayTag that = (ByteArrayTag) o;
        return Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    @Override
    public String toString() {
        return "byte[" + this.value.length + "](" + Arrays.toString(this.value) + ")";
    }

}
