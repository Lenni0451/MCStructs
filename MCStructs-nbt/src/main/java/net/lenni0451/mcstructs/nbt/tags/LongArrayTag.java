package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;

import java.util.Arrays;
import java.util.Iterator;

public class LongArrayTag implements INbtTag, Iterable<Long> {

    private long[] value;

    /**
     * Create an empty long array tag.
     */
    public LongArrayTag() {
        this(new long[0]);
    }

    /**
     * Create a long array tag from a {@link LongTag} {@link ListTag}.
     *
     * @param list The list tag
     */
    public LongArrayTag(final ListTag<LongTag> list) {
        this.value = new long[list.size()];
        for (int i = 0; i < list.size(); i++) this.value[i] = list.get(i).getValue();
    }

    /**
     * Create a long array tag from a long array.
     *
     * @param value The long array
     */
    public LongArrayTag(final long[] value) {
        this.value = value;
    }

    /**
     * @return The long array value
     */
    public long[] getValue() {
        return this.value;
    }

    /**
     * Set the long array value.
     *
     * @param value The new value
     */
    public void setValue(long[] value) {
        this.value = value;
    }

    /**
     * Get a long from the array.
     *
     * @param index The index
     * @return The long
     */
    public long get(final int index) {
        return this.value[index];
    }

    /**
     * Set a long in the array.
     *
     * @param index The index
     * @param l     The new value
     */
    public void set(final int index, final long l) {
        this.value[index] = l;
    }

    /**
     * Add a long to the array.<br>
     * This will create a new array and copy the old values into it.
     *
     * @param l The long
     */
    public void add(final long l) {
        long[] newValue = new long[this.value.length + 1];
        System.arraycopy(this.value, 0, newValue, 0, this.value.length);
        newValue[this.value.length] = l;
        this.value = newValue;
    }

    /**
     * @return The length of the array
     */
    public int getLength() {
        return this.value.length;
    }

    /**
     * @return If the array is empty
     */
    public boolean isEmpty() {
        return this.value.length == 0;
    }

    @Override
    public NbtType getNbtType() {
        return NbtType.LONG_ARRAY;
    }

    @Override
    public INbtTag copy() {
        return new LongArrayTag(this.value.clone());
    }

    @Override
    public Iterator<Long> iterator() {
        return new Iterator<Long>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < LongArrayTag.this.value.length;
            }

            @Override
            public Long next() {
                return LongArrayTag.this.value[this.index++];
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongArrayTag that = (LongArrayTag) o;
        return Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    @Override
    public String toString() {
        return "long[" + this.value.length + "](" + Arrays.toString(this.value) + ")";
    }

}
