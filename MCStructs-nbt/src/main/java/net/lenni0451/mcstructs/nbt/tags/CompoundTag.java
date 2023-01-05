package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.*;
import net.lenni0451.mcstructs.nbt.exceptions.UnknownTagTypeException;

import javax.annotation.Nullable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

public class CompoundTag implements INbtTag, Iterable<Map.Entry<String, INbtTag>> {

    private Map<String, INbtTag> value;

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
    public CompoundTag(final Map<String, INbtTag> value) {
        this.value = value;
    }

    /**
     * @return The map value
     */
    public Map<String, INbtTag> getValue() {
        return this.value;
    }

    /**
     * Set the map value.
     *
     * @param value The new value
     */
    public void setValue(final Map<String, INbtTag> value) {
        this.value = value;
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
     * If the type is a {@link INbtNumber} any number type will be accepted.
     *
     * @param key  The key
     * @param type The type
     * @return If the map contains the given key and type
     */
    public boolean contains(final String key, final NbtType type) {
        INbtTag tag = this.get(key);
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
        INbtTag tag = this.get(key);
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
    public <T extends INbtTag> T get(final String key) {
        return (T) this.value.get(key);
    }

    /**
     * Add a tag to the map.
     *
     * @param key The key
     * @param tag The tag
     */
    public void add(final String key, final INbtTag tag) {
        this.value.put(key, tag);
    }

    /**
     * Add a tag from its raw value to the map.
     *
     * @param key The key
     * @param o   The raw value
     * @throws UnknownTagTypeException If the raw value can not be converted to a tag
     */
    public void add(final String key, final Object o) {
        this.add(key, this.wrap(o));
    }

    /**
     * Remove a tag from the map.
     *
     * @param key The key
     * @return The removed tag or null if the map does not contain the given key
     */
    @Nullable
    public INbtTag remove(final String key) {
        return this.value.remove(key);
    }

    /**
     * Get a byte from the map.
     *
     * @param key The key
     * @return The byte or 0 if the map does not contain the given key or the tag is not a number
     */
    public byte getByte(final String key) {
        if (this.contains(key, NbtType.BYTE)) return this.<INbtNumber>get(key).byteValue();
        return 0;
    }

    /**
     * Add a byte to the map.
     *
     * @param key The key
     * @param b   The byte
     */
    public void addByte(final String key, final byte b) {
        this.add(key, new ByteTag(b));
    }

    /**
     * Get a short from the map.
     *
     * @param key The key
     * @return The short or 0 if the map does not contain the given key or the tag is not a number
     */
    public short getShort(final String key) {
        if (this.contains(key, NbtType.SHORT)) return this.<INbtNumber>get(key).shortValue();
        return 0;
    }

    /**
     * Add a short to the map.
     *
     * @param key The key
     * @param s   The short
     */
    public void addShort(final String key, final short s) {
        this.add(key, new ShortTag(s));
    }

    /**
     * Get an int from the map.
     *
     * @param key The key
     * @return The int or 0 if the map does not contain the given key or the tag is not a number
     */
    public int getInt(final String key) {
        if (this.contains(key, NbtType.INT)) return this.<INbtNumber>get(key).intValue();
        return 0;
    }

    /**
     * Add an int to the map.
     *
     * @param key The key
     * @param i   The int
     */
    public void addInt(final String key, final int i) {
        this.add(key, new IntTag(i));
    }

    /**
     * Get a long from the map.
     *
     * @param key The key
     * @return The long or 0 if the map does not contain the given key or the tag is not a number
     */
    public long getLong(final String key) {
        if (this.contains(key, NbtType.LONG)) return this.<INbtNumber>get(key).longValue();
        return 0;
    }

    /**
     * Add a long to the map.
     *
     * @param key The key
     * @param l   The long
     */
    public void addLong(final String key, final long l) {
        this.add(key, new LongTag(l));
    }

    /**
     * Get a float from the map.
     *
     * @param key The key
     * @return The float or 0 if the map does not contain the given key or the tag is not a number
     */
    public float getFloat(final String key) {
        if (this.contains(key, NbtType.FLOAT)) return this.<INbtNumber>get(key).floatValue();
        return 0;
    }

    /**
     * Add a float to the map.
     *
     * @param key The key
     * @param f   The float
     */
    public void addFloat(final String key, final float f) {
        this.add(key, new FloatTag(f));
    }

    /**
     * Get a double from the map.
     *
     * @param key The key
     * @return The double or 0 if the map does not contain the given key or the tag is not a number
     */
    public double getDouble(final String key) {
        if (this.contains(key, NbtType.DOUBLE)) return this.<INbtNumber>get(key).doubleValue();
        return 0;
    }

    /**
     * Add a double to the map.
     *
     * @param key The key
     * @param d   The double
     */
    public void addDouble(final String key, final double d) {
        this.add(key, new DoubleTag(d));
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
     * Add a byte array to the map.
     *
     * @param key   The key
     * @param bytes The byte array
     */
    public void addByteArray(final String key, final byte... bytes) {
        this.add(key, new ByteArrayTag(bytes));
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
     * Add a string to the map.
     *
     * @param key The key
     * @param s   The string
     */
    public void addString(final String key, final String s) {
        this.add(key, new StringTag(s));
    }

    /**
     * Get a list from the map.
     *
     * @param key The key
     * @param <T> The type of the list
     * @return The list or <code>new ListTag{@literal <}{@literal >}()</code> if the map does not contain the given key or the tag is not a list
     */
    public <T extends INbtTag> ListTag<T> getList(final String key) {
        if (this.contains(key, NbtType.LIST)) return this.get(key);
        return new ListTag<>();
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
    public <T extends INbtTag> ListTag<T> getList(final String key, final NbtType type) {
        if (this.contains(key, NbtType.LIST)) {
            ListTag<T> list = this.get(key);
            if (!list.canAdd(type)) return new ListTag<>(type);
            else return list;
        }
        return new ListTag<>();
    }

    /**
     * Add a list to the map.
     *
     * @param key  The key
     * @param list The list
     */
    public void addList(final String key, final ListTag<?> list) {
        this.add(key, list);
    }

    /**
     * Add a list to the map.
     *
     * @param key   The key
     * @param items The items
     * @param <T>   The type of the items
     * @throws IllegalArgumentException If the type of the items is mixed
     */
    public <T extends INbtTag> void addList(final String key, final T... items) {
        if (items.length == 0) {
            this.add(key, new ListTag<>());
        } else {
            List<INbtTag> list = new ArrayList<>();
            Collections.addAll(list, items);
            this.add(key, new ListTag<>(list));
        }
    }

    /**
     * Add a list to the map.
     *
     * @param key   The key
     * @param items The raw items
     * @throws UnknownTagTypeException  If the type of the items is unknown
     * @throws IllegalArgumentException If the type of the items is mixed
     */
    public void addList(final String key, final Object... items) {
        if (items.length == 0) {
            this.add(key, new ListTag<>());
        } else {
            List<INbtTag> list = new ArrayList<>();
            for (Object item : items) list.add(this.wrap(item));
            this.add(key, new ListTag<>(list));
        }
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
     * Add a compound to the map.
     *
     * @param key      The key
     * @param compound The compound
     */
    public void addCompound(final String key, final CompoundTag compound) {
        this.add(key, compound);
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
     * Add an int array to the map.
     *
     * @param key  The key
     * @param ints The int array
     */
    public void addIntArray(final String key, final int... ints) {
        this.add(key, new IntArrayTag(ints));
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
     * Add a long array to the map.
     *
     * @param key   The key
     * @param longs The long array
     */
    public void addLongArray(final String key, final long... longs) {
        this.add(key, new LongArrayTag(longs));
    }

    /**
     * Trim the map by removing empty tags.
     *
     * @return If the trimmed map is empty
     */
    public boolean trim() {
        if (this.value.isEmpty()) return true;
        this.value.entrySet().removeIf(entry -> {
            INbtTag tag = entry.getValue();
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

    private INbtTag wrap(final Object o) {
        if (o == null) return null;
        if (o instanceof INbtTag) return (INbtTag) o;
        if (o instanceof Byte) return new ByteTag((Byte) o);
        else if (o instanceof Short) return new ShortTag((Short) o);
        else if (o instanceof Integer) return new IntTag((Integer) o);
        else if (o instanceof Long) return new LongTag((Long) o);
        else if (o instanceof Float) return new FloatTag((Float) o);
        else if (o instanceof Double) return new DoubleTag((Double) o);
        else if (o instanceof byte[]) return new ByteArrayTag((byte[]) o);
        else if (o instanceof String) return new StringTag((String) o);
        else if (o instanceof int[]) return new IntArrayTag((int[]) o);
        else if (o instanceof long[]) return new LongArrayTag((long[]) o);
        throw new UnknownTagTypeException(o.getClass());
    }

    @Override
    public NbtType getNbtType() {
        return NbtType.COMPOUND;
    }

    @Override
    public void read(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(48);
        this.value = new HashMap<>();
        while (true) {
            NbtHeader header = NbtIO.readNbtHeader(in, readTracker);
            if (header.isEnd()) break;
            readTracker.read(28 + 2 * header.getName().length());

            INbtTag tag = header.getType().newInstance();
            readTracker.pushDepth();
            tag.read(in, readTracker);
            readTracker.popDepth();
            if (this.value.put(header.getName(), tag) != null) readTracker.read(36);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        for (Map.Entry<String, INbtTag> entry : this.value.entrySet()) {
            NbtIO.writeNbtHeader(out, new NbtHeader(entry.getValue().getNbtType(), entry.getKey()));
            entry.getValue().write(out);
        }
        NbtIO.writeNbtHeader(out, NbtHeader.END);
    }

    @Override
    public INbtTag copy() {
        Map<String, INbtTag> value = new HashMap<>();
        for (Map.Entry<String, INbtTag> entry : this.value.entrySet()) value.put(entry.getKey(), entry.getValue().copy());
        return new CompoundTag(value);
    }

    @Override
    public Iterator<Map.Entry<String, INbtTag>> iterator() {
        return this.value.entrySet().iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompoundTag that = (CompoundTag) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Compound[" + this.value.size() + "]" + this.value;
    }

}
