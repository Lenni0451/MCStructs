package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ListTag<T extends NbtTag> implements NbtTag, Iterable<T> {

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
     * @return The type of the list. <b><u>Can</u> be null if the list is empty</b>.
     */
    @Nullable
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
     * @return This tag
     */
    public ListTag<T> setValue(final List<T> value) {
        this.value = value;
        return this;
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
     * @return This tag
     * @throws IllegalArgumentException If the tag is not of the same type as the list
     */
    public ListTag<T> add(final T tag) {
        this.check(tag);
        this.value.add(tag);
        return this;
    }

    /**
     * Set a tag in the list.
     *
     * @param index The index
     * @param tag   The tag
     * @return This tag
     * @throws IndexOutOfBoundsException If the index is out of bounds
     * @throws IllegalArgumentException  If the tag is not of the same type as the list
     */
    public ListTag<T> set(final int index, final T tag) {
        this.check(tag);
        this.value.set(index, tag);
        return this;
    }

    /**
     * Remove a tag from the list.
     *
     * @param tag The tag
     * @return This tag
     * @throws IllegalArgumentException If the tag is not of the same type as the list
     */
    public ListTag<T> remove(final T tag) {
        this.check(tag);
        this.value.remove(tag);
        return this;
    }

    /**
     * Check if the tag can be added to the list.
     *
     * @param tag The tag
     * @return True if the tag can be added
     */
    public boolean canAdd(final NbtTag tag) {
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
    public ListTag copy() {
        List<NbtTag> value = new ArrayList<>();
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
        if (!Objects.equals(this.type, listTag.type)) {
            if (Objects.isNull(this.type) && Objects.equals(NbtType.END, listTag.type)) return true;
            if (Objects.isNull(listTag.type) && Objects.equals(NbtType.END, this.type)) return true;
            return false;
        }
        return Objects.equals(this.value, listTag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.value);
    }

    @Override
    public String toString() {
        String list = this.value.toString();
        list = list.substring(1, list.length() - 1);
        return "List[" + this.value.size() + "](" + list + ")";
    }

}
