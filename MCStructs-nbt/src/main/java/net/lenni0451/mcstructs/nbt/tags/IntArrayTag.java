package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.NbtArray;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;

import java.util.Arrays;
import java.util.Iterator;

public class IntArrayTag implements NbtTag, NbtArray<IntArrayTag, IntTag, int[], Integer> {

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

    @Override
    public int[] getValue() {
        return this.value;
    }

    @Override
    public IntArrayTag setValue(final int[] value) {
        this.value = value;
        return this;
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
     * @return This tag
     */
    public IntArrayTag set(final int index, final int i) {
        this.value[index] = i;
        return this;
    }

    /**
     * Add an int to the array.<br>
     * This will create a new array and copy the old values into it.
     *
     * @param i The int
     * @return This tag
     */
    public IntArrayTag add(final int i) {
        int[] newValue = new int[this.value.length + 1];
        System.arraycopy(this.value, 0, newValue, 0, this.value.length);
        newValue[this.value.length] = i;
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
    public ListTag<IntTag> toListTag() {
        ListTag<IntTag> listTag = new ListTag<>();
        for (int i : this.value) listTag.add(new IntTag(i));
        return listTag;
    }

    @Override
    public NbtType getNbtType() {
        return NbtType.INT_ARRAY;
    }

    @Override
    public IntArrayTag copy() {
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
        return Arrays.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override
    public String toString() {
        return "int[" + this.value.length + "](" + Arrays.toString(this.value) + ")";
    }

}
