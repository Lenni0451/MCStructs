package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ListTag<T extends INbtTag> implements INbtTag, Iterable<T> {

    private NbtType type;
    private List<T> value;

    /**
     * Create an empty list tag.
     */
    public ListTag() {
        this(null, new ArrayList<>());
    }

    /**
     * Create a list tag with the given type.
     *
     * @param type The type
     */
    public ListTag(final NbtType type) {
        this(type, new ArrayList<>());
    }

    /**
     * Create a list tag from a {@link List}.
     *
     * @param list The list
     * @throws IllegalArgumentException If the list contains mixed types
     */
    public ListTag(final List<T> list) {
        if (!list.isEmpty()) {
            this.type = NbtType.byClass(list.get(0).getClass());
            if (list.stream().anyMatch(tag -> !tag.getNbtType().equals(this.type))) throw new IllegalArgumentException("Tried to create list with multiple Nbt types");
        }
        this.value = list;
    }

    /**
     * Create a list tag from a type and a {@link List}.
     *
     * @param type  The type
     * @param value The list
     */
    public ListTag(final NbtType type, final List<T> value) {
        this.type = type;
        this.value = value;
    }

    /**
     * @return The type of the list
     */
    public NbtType getType() {
        return this.type;
    }

    /**
     * @return The list value
     */
    public List<T> getValue() {
        return this.value;
    }

    /**
     * Set the list value.
     *
     * @param value The new value
     */
    public void setValue(final List<T> value) {
        this.value = value;
    }

    /**
     * Get a tag from the list.
     *
     * @param index The index
     * @return The tag
     */
    public T get(final int index) {
        return this.value.get(index);
    }

    /**
     * Add a tag to the list.
     *
     * @param tag The tag
     * @throws IllegalArgumentException If the tag is not of the same type as the list
     */
    public void add(final T tag) {
        this.check(tag);
        this.value.add(tag);
    }

    /**
     * Set a tag in the list.
     *
     * @param index The index
     * @param tag   The tag
     * @throws IndexOutOfBoundsException If the index is out of bounds
     * @throws IllegalArgumentException  If the tag is not of the same type as the list
     */
    public void set(final int index, final T tag) {
        this.check(tag);
        this.value.set(index, tag);
    }

    /**
     * Remove a tag from the list.
     *
     * @param tag The tag
     * @throws IllegalArgumentException If the tag is not of the same type as the list
     */
    public void remove(final T tag) {
        this.check(tag);
        this.value.remove(tag);
    }

    /**
     * Check if the tag can be added to the list.
     *
     * @param tag The tag
     * @return True if the tag can be added
     */
    public boolean canAdd(final INbtTag tag) {
        if (this.type == null || this.value.isEmpty()) return true;
        return this.type.equals(tag.getNbtType());
    }

    /**
     * Check if the type can be added to the list.
     *
     * @param type The type
     * @return True if the type can be added
     */
    public boolean canAdd(final NbtType type) {
        if (this.type == null || this.value.isEmpty()) return true;
        return this.type.equals(type);
    }

    /**
     * Trim the list by removing empty tags.
     *
     * @return If the trimmed list is empty
     */
    public boolean trim() {
        if (this.value.isEmpty()) return true;
        if (NbtType.COMPOUND.equals(this.type)) this.value.forEach(tag -> ((CompoundTag) tag).trim());
        else if (NbtType.LIST.equals(this.type)) this.value.forEach(tag -> ((ListTag<?>) tag).trim());
        return false;
    }

    /**
     * @return The size of the list
     */
    public int size() {
        return this.value.size();
    }

    /**
     * @return If the list is empty
     */
    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    private void check(final T tag) {
        if (this.type == null || this.value.isEmpty()) {
            this.type = tag.getNbtType();
            this.value.clear();
        } else if (!this.type.equals(tag.getNbtType())) {
            throw new IllegalArgumentException("Can't add " + tag.getClass().getSimpleName() + " to a " + this.type.name() + " list");
        }
    }

    @Override
    public NbtType getNbtType() {
        return NbtType.LIST;
    }

    @Override
    public INbtTag copy() {
        List<INbtTag> value = new ArrayList<>();
        for (T val : this.value) value.add(val.copy());
        return new ListTag<>(value);
    }

    @Override
    public Iterator<T> iterator() {
        return this.value.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListTag<?> listTag = (ListTag<?>) o;
        if (!Objects.equals(type, listTag.type)) {
            if (Objects.isNull(type) && Objects.equals(NbtType.END, listTag.type)) return true;
            if (Objects.isNull(listTag.type) && Objects.equals(NbtType.END, type)) return true;
            return false;
        }
        return Objects.equals(value, listTag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        String list = this.value.toString();
        list = list.substring(1, list.length() - 1);
        return "List[" + this.value.size() + "](" + list + ")";
    }

}
