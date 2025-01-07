package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.NbtNumber;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.exceptions.UnknownTagTypeException;
import net.lenni0451.mcstructs.nbt.utils.ObjectTagConverter;

import javax.annotation.Nullable;
import java.util.*;

public class CompoundTag implements NbtTag, Iterable<Map.Entry<String, NbtTag>> {

    private Map<String, NbtTag> value;

    /**
     * Create an empty compound tag.
     */
    public CompoundTag() {
        this.value = new HashMap<>();
    }

    /**
     * Create a compound tag from a map.
     *
     * @param value The map
     */
    public CompoundTag(final Map<String, NbtTag> value) {
        this.value = value;
    }

    /**
     * @return The map value
     */
    public Map<String, NbtTag> getValue() {
        return this.value;
    }

    /**
     * Set the map value.
     *
     * @param value The new value
     * @return This tag
     */
    public CompoundTag setValue(final Map<String, NbtTag> value) {
        this.value = value;
        return this;
    }

    /**
     * @return The size of the map
     */
    public int size() {
        return this.value.size();
    }

    /**
     * @return If the map is empty
     */
    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    /**
     * Get if the map contains a tag with the given name.
     *
     * @param key The key
     * @return If the map contains the given key
     */
    public boolean contains(final String key) {
        return this.value.containsKey(key);
    }

    /**
     * Get if the map contains a tag with the given name and type.<br>
     * If the type is a {@link NbtNumber} any number type will be accepted.
     *
     * @param key  The key
     * @param type The type
     * @return If the map contains the given key and type
     */
    public boolean contains(final String key, final NbtType type) {
        NbtTag tag = this.get(key);
        if (tag == null) return false;
        if (type.isNumber() && tag.getNbtType().isNumber()) return true;
        return tag.getNbtType().equals(type);
    }

    /**
     * Get if the map contains a tag with the given name and exact type.
     *
     * @param key  The key
     * @param type The type
     * @return If the map contains the given key and type
     */
    public boolean containsExact(final String key, final NbtType type) {
        NbtTag tag = this.get(key);
        if (tag == null) return false;
        return tag.getNbtType().equals(type);
    }

    /**
     * Get a tag from the map.
     *
     * @param key The key
     * @param <T> The tag type
     * @return The tag or null if the map does not contain the given key
     */
    @Nullable
    public <T extends NbtTag> T get(final String key) {
        return (T) this.value.get(key);
    }

    /**
     * Get a tag from the map or the default value.
     *
     * @param key The key
     * @param def The default value
     * @param <T> The tag type
     * @return The tag or the default value if the map does not contain the given key
     */
    public <T extends NbtTag> T get(final String key, final T def) {
        T t = this.get(key);
        if (t == null) return def;
        return t;
    }

    /**
     * Add a tag to the map.
     *
     * @param key The key
     * @param tag The tag
     * @return This tag
     */
    public CompoundTag add(final String key, final NbtTag tag) {
        this.value.put(key, tag);
        return this;
    }

    /**
     * Add a tag from its raw value to the map.
     *
     * @param key The key
     * @param o   The raw value
     * @return This tag
     * @throws UnknownTagTypeException If the raw value can not be converted to a tag
     */
    public CompoundTag add(final String key, final Object o) {
        return this.add(key, ObjectTagConverter.convert(o));
    }

    /**
     * Add all tags from the given compound tag to the map.
     *
     * @param tag The compound tag
     * @return This tag
     */
    public CompoundTag addAll(final CompoundTag tag) {
        this.value.putAll(tag.getValue());
        return this;
    }

    /**
     * Remove a tag from the map.
     *
     * @param key The key
     * @return The removed tag or null if the map does not contain the given key
     */
    @Nullable
    public NbtTag remove(final String key) {
        return this.value.remove(key);
    }

    /**
     * Get a boolean from the map.<br>
     * Booleans are stored as bytes with 0 being false and everything else being true.
     *
     * @param key The key
     * @return The boolean or false if the map does not contain the given key or the tag is not a number
     */
    public boolean getBoolean(final String key) {
        if (this.contains(key, NbtType.BYTE)) return this.<NbtNumber>get(key).byteValue() != 0;
        return false;
    }

    /**
     * Get a boolean from the map or the default value.<br>
     * Booleans are stored as bytes with 0 being false and everything else being true.
     *
     * @param key The key
     * @param def The default value
     * @return The boolean or the default value if the map does not contain the given key or the tag is not a number
     */
    public boolean getBoolean(final String key, final boolean def) {
        if (this.contains(key, NbtType.BYTE)) return this.<NbtNumber>get(key).byteValue() != 0;
        return def;
    }

    /**
     * Add a boolean to the map.<br>
     * Booleans are stored as bytes with 0 being false and everything else being true.
     *
     * @param key The key
     * @param b   The boolean
     * @return This tag
     */
    public CompoundTag addBoolean(final String key, final boolean b) {
        return this.add(key, new ByteTag((byte) (b ? 1 : 0)));
    }

    /**
     * Get a byte from the map.
     *
     * @param key The key
     * @return The byte or 0 if the map does not contain the given key or the tag is not a number
     */
    public byte getByte(final String key) {
        if (this.contains(key, NbtType.BYTE)) return this.<NbtNumber>get(key).byteValue();
        return 0;
    }

    /**
     * Get a byte from the map or the default value.
     *
     * @param key The key
     * @param def The default value
     * @return The byte or the default value if the map does not contain the given key or the tag is not a number
     */
    public byte getByte(final String key, final byte def) {
        if (this.contains(key, NbtType.BYTE)) return this.<NbtNumber>get(key).byteValue();
        return def;
    }

    /**
     * Add a byte to the map.
     *
     * @param key The key
     * @param b   The byte
     * @return This tag
     */
    public CompoundTag addByte(final String key, final byte b) {
        return this.add(key, new ByteTag(b));
    }

    /**
     * Get a short from the map.
     *
     * @param key The key
     * @return The short or 0 if the map does not contain the given key or the tag is not a number
     */
    public short getShort(final String key) {
        if (this.contains(key, NbtType.SHORT)) return this.<NbtNumber>get(key).shortValue();
        return 0;
    }

    /**
     * Get a short from the map or the default value.
     *
     * @param key The key
     * @param def The default value
     * @return The short or the default value if the map does not contain the given key or the tag is not a number
     */
    public short getShort(final String key, final short def) {
        if (this.contains(key, NbtType.SHORT)) return this.<NbtNumber>get(key).shortValue();
        return def;
    }

    /**
     * Add a short to the map.
     *
     * @param key The key
     * @param s   The short
     * @return This tag
     */
    public CompoundTag addShort(final String key, final short s) {
        return this.add(key, new ShortTag(s));
    }

    /**
     * Get an int from the map.
     *
     * @param key The key
     * @return The int or 0 if the map does not contain the given key or the tag is not a number
     */
    public int getInt(final String key) {
        if (this.contains(key, NbtType.INT)) return this.<NbtNumber>get(key).intValue();
        return 0;
    }

    /**
     * Get an int from the map or the default value.
     *
     * @param key The key
     * @param def The default value
     * @return The int or the default value if the map does not contain the given key or the tag is not a number
     */
    public int getInt(final String key, final int def) {
        if (this.contains(key, NbtType.INT)) return this.<NbtNumber>get(key).intValue();
        return def;
    }

    /**
     * Add an int to the map.
     *
     * @param key The key
     * @param i   The int
     * @return This tag
     */
    public CompoundTag addInt(final String key, final int i) {
        return this.add(key, new IntTag(i));
    }

    /**
     * Get a long from the map.
     *
     * @param key The key
     * @return The long or 0 if the map does not contain the given key or the tag is not a number
     */
    public long getLong(final String key) {
        if (this.contains(key, NbtType.LONG)) return this.<NbtNumber>get(key).longValue();
        return 0;
    }

    /**
     * Get a long from the map or the default value.
     *
     * @param key The key
     * @param def The default value
     * @return The long or the default value if the map does not contain the given key or the tag is not a number
     */
    public long getLong(final String key, final long def) {
        if (this.contains(key, NbtType.LONG)) return this.<NbtNumber>get(key).longValue();
        return def;
    }

    /**
     * Add a long to the map.
     *
     * @param key The key
     * @param l   The long
     * @return This tag
     */
    public CompoundTag addLong(final String key, final long l) {
        return this.add(key, new LongTag(l));
    }

    /**
     * Get a float from the map.
     *
     * @param key The key
     * @return The float or 0 if the map does not contain the given key or the tag is not a number
     */
    public float getFloat(final String key) {
        if (this.contains(key, NbtType.FLOAT)) return this.<NbtNumber>get(key).floatValue();
        return 0;
    }

    /**
     * Get a float from the map or the default value.
     *
     * @param key The key
     * @param def The default value
     * @return The float or the default value if the map does not contain the given key or the tag is not a number
     */
    public float getFloat(final String key, final float def) {
        if (this.contains(key, NbtType.FLOAT)) return this.<NbtNumber>get(key).floatValue();
        return def;
    }

    /**
     * Add a float to the map.
     *
     * @param key The key
     * @param f   The float
     * @return This tag
     */
    public CompoundTag addFloat(final String key, final float f) {
        return this.add(key, new FloatTag(f));
    }

    /**
     * Get a double from the map.
     *
     * @param key The key
     * @return The double or 0 if the map does not contain the given key or the tag is not a number
     */
    public double getDouble(final String key) {
        if (this.contains(key, NbtType.DOUBLE)) return this.<NbtNumber>get(key).doubleValue();
        return 0;
    }

    /**
     * Get a double from the map or the default value.
     *
     * @param key The key
     * @param def The default value
     * @return The double or the default value if the map does not contain the given key or the tag is not a number
     */
    public double getDouble(final String key, final double def) {
        if (this.contains(key, NbtType.DOUBLE)) return this.<NbtNumber>get(key).doubleValue();
        return def;
    }

    /**
     * Add a double to the map.
     *
     * @param key The key
     * @param d   The double
     * @return This tag
     */
    public CompoundTag addDouble(final String key, final double d) {
        return this.add(key, new DoubleTag(d));
    }

    /**
     * Get a byte array from the map.
     *
     * @param key The key
     * @return The byte array or <code>new byte[0]</code> if the map does not contain the given key or the tag is not a byte array
     */
    public byte[] getByteArray(final String key) {
        if (this.contains(key, NbtType.BYTE_ARRAY)) return this.<ByteArrayTag>get(key).getValue();
        return new byte[0];
    }

    /**
     * Get a byte array from the map or the default value.
     *
     * @param key The key
     * @param def The default value
     * @return The byte array or the default value if the map does not contain the given key or the tag is not a byte array
     */
    public byte[] getByteArray(final String key, final byte[] def) {
        if (this.contains(key, NbtType.BYTE_ARRAY)) return this.<ByteArrayTag>get(key).getValue();
        return def;
    }

    /**
     * Add a byte array to the map.
     *
     * @param key   The key
     * @param bytes The byte array
     * @return This tag
     */
    public CompoundTag addByteArray(final String key, final byte... bytes) {
        return this.add(key, new ByteArrayTag(bytes));
    }

    /**
     * Get a string from the map.
     *
     * @param key The key
     * @return The string or <code>""</code> if the map does not contain the given key or the tag is not a string
     */
    public String getString(final String key) {
        if (this.contains(key, NbtType.STRING)) return this.<StringTag>get(key).getValue();
        return "";
    }

    /**
     * Get a string from the map or the default value.
     *
     * @param key The key
     * @param def The default value
     * @return The string or the default value if the map does not contain the given key or the tag is not a string
     */
    public String getString(final String key, final String def) {
        if (this.contains(key, NbtType.STRING)) return this.<StringTag>get(key).getValue();
        return def;
    }

    /**
     * Add a string to the map.
     *
     * @param key The key
     * @param s   The string
     * @return This tag
     */
    public CompoundTag addString(final String key, final String s) {
        return this.add(key, new StringTag(s));
    }

    /**
     * Get a list from the map.
     *
     * @param key The key
     * @param <T> The type of the list
     * @return The list or <code>new ListTag{@literal <}{@literal >}()</code> if the map does not contain the given key or the tag is not a list
     */
    public <T extends NbtTag> ListTag<T> getList(final String key) {
        if (this.contains(key, NbtType.LIST)) return this.get(key);
        return new ListTag<>();
    }

    /**
     * Get a list from the map or the default value.
     *
     * @param key The key
     * @param def The default value
     * @param <T> The type of the list
     * @return The list or the default value if the map does not contain the given key or the tag is not a list
     */
    public <T extends NbtTag> ListTag<T> getList(final String key, final ListTag<T> def) {
        if (this.contains(key, NbtType.LIST)) return this.get(key);
        return def;
    }

    /**
     * Get a list from the map with the given type.<br>
     * If the list does not contain the given type, an empty list is returned.
     *
     * @param key  The key
     * @param type The type
     * @param <T>  The type of the list
     * @return The list or <code>new ListTag{@literal <}{@literal >}()</code> if the map does not contain the given key or the tag is not a list
     */
    public <T extends NbtTag> ListTag<T> getList(final String key, final NbtType type) {
        if (this.contains(key, NbtType.LIST)) {
            ListTag<T> list = this.get(key);
            if (!list.canAdd(type)) return new ListTag<>(type);
            else return list;
        }
        return new ListTag<>(type);
    }

    /**
     * Get a list from the map with the given type or the default value.<br>
     * If the list does not contain the given type, the default value is returned.
     *
     * @param key  The key
     * @param type The type
     * @param def  The default value
     * @param <T>  The type of the list
     * @return The list or the default value if the map does not contain the given key or the tag is not a list
     */
    public <T extends NbtTag> ListTag<T> getList(final String key, final NbtType type, final ListTag<T> def) {
        if (this.contains(key, NbtType.LIST)) {
            ListTag<T> list = this.get(key);
            if (!list.canAdd(type)) return def;
            else return list;
        }
        return def;
    }

    /**
     * Add a list to the map.
     *
     * @param key  The key
     * @param list The list
     * @return This tag
     */
    public CompoundTag addList(final String key, final ListTag<?> list) {
        return this.add(key, list);
    }

    /**
     * Add a list to the map.
     *
     * @param key   The key
     * @param items The items
     * @param <T>   The type of the items
     * @throws IllegalArgumentException If the type of the items is mixed
     */
    public <T extends NbtTag> void addList(final String key, final T... items) {
        if (items.length == 0) {
            this.add(key, new ListTag<>());
        } else {
            List<NbtTag> list = new ArrayList<>();
            Collections.addAll(list, items);
            this.add(key, new ListTag<>(list));
        }
    }

    /**
     * Add a list to the map.
     *
     * @param key   The key
     * @param items The raw items
     * @return This tag
     * @throws UnknownTagTypeException  If the type of the items is unknown
     * @throws IllegalArgumentException If the type of the items is mixed
     */
    public CompoundTag addList(final String key, final Object... items) {
        if (items.length == 0) {
            this.add(key, new ListTag<>());
        } else {
            List<NbtTag> list = new ArrayList<>();
            for (Object item : items) list.add(ObjectTagConverter.convert(item));
            this.add(key, new ListTag<>(list));
        }
        return this;
    }

    /**
     * Get a compound from the map.
     *
     * @param key The key
     * @return The compound or <code>new CompoundTag()</code> if the map does not contain the given key or the tag is not a compound
     */
    public CompoundTag getCompound(final String key) {
        if (this.contains(key, NbtType.COMPOUND)) return this.get(key);
        return new CompoundTag();
    }

    /**
     * Get a compound from the map or the default value.
     *
     * @param key The key
     * @param def The default value
     * @return The compound or the default value if the map does not contain the given key or the tag is not a compound
     */
    public CompoundTag getCompound(final String key, final CompoundTag def) {
        if (this.contains(key, NbtType.COMPOUND)) return this.get(key);
        return def;
    }

    /**
     * Add a compound to the map.
     *
     * @param key      The key
     * @param compound The compound
     * @return This tag
     */
    public CompoundTag addCompound(final String key, final CompoundTag compound) {
        return this.add(key, compound);
    }

    /**
     * Get an int array from the map.
     *
     * @param key The key
     * @return The int array or <code>new int[0]</code> if the map does not contain the given key or the tag is not an int array
     */
    public int[] getIntArray(final String key) {
        if (this.contains(key, NbtType.INT_ARRAY)) return this.<IntArrayTag>get(key).getValue();
        return new int[0];
    }

    /**
     * Get an int array from the map or the default value.
     *
     * @param key The key
     * @param def The default value
     * @return The int array or the default value if the map does not contain the given key or the tag is not an int array
     */
    public int[] getIntArray(final String key, final int[] def) {
        if (this.contains(key, NbtType.INT_ARRAY)) return this.<IntArrayTag>get(key).getValue();
        return def;
    }

    /**
     * Add an int array to the map.
     *
     * @param key  The key
     * @param ints The int array
     * @return This tag
     */
    public CompoundTag addIntArray(final String key, final int... ints) {
        return this.add(key, new IntArrayTag(ints));
    }

    /**
     * Get a long array from the map.
     *
     * @param key The key
     * @return The long array or <code>new long[0]</code> if the map does not contain the given key or the tag is not a long array
     */
    public long[] getLongArray(final String key) {
        if (this.contains(key, NbtType.LONG_ARRAY)) return this.<LongArrayTag>get(key).getValue();
        return new long[0];
    }

    /**
     * Get a long array from the map or the default value.
     *
     * @param key The key
     * @param def The default value
     * @return The long array or the default value if the map does not contain the given key or the tag is not a long array
     */
    public long[] getLongArray(final String key, final long[] def) {
        if (this.contains(key, NbtType.LONG_ARRAY)) return this.<LongArrayTag>get(key).getValue();
        return def;
    }

    /**
     * Add a long array to the map.
     *
     * @param key   The key
     * @param longs The long array
     * @return This tag
     */
    public CompoundTag addLongArray(final String key, final long... longs) {
        return this.add(key, new LongArrayTag(longs));
    }

    /**
     * Trim the map by removing empty tags.
     *
     * @return If the trimmed map is empty
     */
    public boolean trim() {
        if (this.value.isEmpty()) return true;
        this.value.entrySet().removeIf(entry -> {
            NbtTag tag = entry.getValue();
            if (tag instanceof ByteTag) return ((ByteTag) tag).getValue() == 0;
            else if (tag instanceof ShortTag) return ((ShortTag) tag).getValue() == 0;
            else if (tag instanceof IntTag) return ((IntTag) tag).getValue() == 0;
            else if (tag instanceof LongTag) return ((LongTag) tag).getValue() == 0;
            else if (tag instanceof FloatTag) return ((FloatTag) tag).getValue() == 0;
            else if (tag instanceof DoubleTag) return ((DoubleTag) tag).getValue() == 0;
            else if (tag instanceof ByteArrayTag) return ((ByteArrayTag) tag).isEmpty();
            else if (tag instanceof StringTag) return ((StringTag) tag).getValue().isEmpty();
            else if (tag instanceof ListTag) return ((ListTag<?>) tag).trim();
            else if (tag instanceof CompoundTag) return ((CompoundTag) tag).trim();
            else if (tag instanceof IntArrayTag) return ((IntArrayTag) tag).isEmpty();
            else if (tag instanceof LongArrayTag) return ((LongArrayTag) tag).isEmpty();
            return false;
        });
        return this.value.isEmpty();
    }

    @Override
    public NbtType getNbtType() {
        return NbtType.COMPOUND;
    }

    @Override
    public CompoundTag copy() {
        Map<String, NbtTag> value = new HashMap<>();
        for (Map.Entry<String, NbtTag> entry : this.value.entrySet()) value.put(entry.getKey(), entry.getValue().copy());
        return new CompoundTag(value);
    }

    @Override
    public Iterator<Map.Entry<String, NbtTag>> iterator() {
        return this.value.entrySet().iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompoundTag that = (CompoundTag) o;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }

    @Override
    public String toString() {
        return "Compound[" + this.value.size() + "]" + this.value;
    }

}
