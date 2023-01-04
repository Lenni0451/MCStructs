package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.NbtType;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class IntArrayTag implements INbtTag, Iterable<Integer> {

    private int[] value;

    /**
     * Create an empty int array tag.
     */
    public IntArrayTag() {
        this(new int[0]);
    }

    /**
     * Create an int array tag from a {@link IntTag} {@link ListTag}.
     *
     * @param list The list tag
     */
    public IntArrayTag(final ListTag<IntTag> list) {
        this.value = new int[list.size()];
        for (int i = 0; i < list.size(); i++) this.value[i] = list.get(i).getValue();
    }

    /**
     * Create an int array tag from an int array.
     *
     * @param value The int array
     */
    public IntArrayTag(final int[] value) {
        this.value = value;
    }

    /**
     * @return The int array value
     */
    public int[] getValue() {
        return this.value;
    }

    /**
     * Set the int array value.
     *
     * @param value The new value
     */
    public void setValue(final int[] value) {
        this.value = value;
    }

    /**
     * Get an int from the array.
     *
     * @param index The index
     * @return The int
     */
    public int get(final int index) {
        return this.value[index];
    }

    /**
     * Set an int in the array.
     *
     * @param index The index
     * @param i     The new value
     */
    public void set(final int index, final int i) {
        this.value[index] = i;
    }

    /**
     * Add an int to the array.<br>
     * This will create a new array and copy the old values into it.
     *
     * @param i The int
     */
    public void add(final int i) {
        int[] newValue = new int[this.value.length + 1];
        System.arraycopy(this.value, 0, newValue, 0, this.value.length);
        newValue[this.value.length] = i;
        this.value = newValue;
    }

    /**
     * @return The length of the array
     */
    public int getLength() {
        return this.value.length;
    }

    /**
     * @return If the int array is empty
     */
    public boolean isEmpty() {
        return this.value.length == 0;
    }

    @Override
    public NbtType getNbtType() {
        return NbtType.INT_ARRAY;
    }

    @Override
    public void read(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(24);
        int length = in.readInt();
        readTracker.read(4 * length);
        this.value = new int[length];
        for (int i = 0; i < this.value.length; i++) this.value[i] = in.readInt();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.value.length);
        for (int i : this.value) out.writeInt(i);
    }

    @Override
    public INbtTag copy() {
        return new IntArrayTag(this.value.clone());
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < IntArrayTag.this.value.length;
            }

            @Override
            public Integer next() {
                return IntArrayTag.this.value[this.index++];
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntArrayTag that = (IntArrayTag) o;
        return Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    @Override
    public String toString() {
        return "int[" + this.value.length + "](" + Arrays.toString(this.value) + ")";
    }

}
