package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.NbtArray;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;

import java.util.Arrays;
import java.util.Iterator;

public class LongArrayTag implements NbtTag, NbtArray<LongArrayTag, LongTag, long[], Long> {

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

    @Override
    public long[] getValue() {
        return this.value;
    }

    @Override
    public LongArrayTag setValue(long[] value) {
        this.value = value;
        return this;
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
     * @return This tag
     */
    public LongArrayTag set(final int index, final long l) {
        this.value[index] = l;
        return this;
    }

    /**
     * Add a long to the array.<br>
     * This will create a new array and copy the old values into it.
     *
     * @param l The long
     * @return This tag
     */
    public LongArrayTag add(final long l) {
        long[] newValue = new long[this.value.length + 1];
        System.arraycopy(this.value, 0, newValue, 0, this.value.length);
        newValue[this.value.length] = l;
        this.value = newValue;
        return this;
    }

    @Override
    public int getLength() {
        return this.value.length;
    }

    @Override
    public boolean isEmpty() {
        return this.value.length == 0;
    }

    @Override
    public ListTag<LongTag> toListTag() {
        ListTag<LongTag> listTag = new ListTag<>();
        for (long l : this.value) listTag.add(new LongTag(l));
        return listTag;
    }

    @Override
    public NbtType getNbtType() {
        return NbtType.LONG_ARRAY;
    }

    @Override
    public LongArrayTag copy() {
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
        return Arrays.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override
    public String toString() {
        String s = Arrays.toString(this.value);
        return "long[" + this.value.length + "](" + s.substring(1, s.length() - 1) + ")";
    }

}
