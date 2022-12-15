package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.*;
import net.lenni0451.mcstructs.nbt.exceptions.UnknownTagTypeException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

public class CompoundTag implements INbtTag {

    private Map<String, INbtTag> value;

    public CompoundTag() {
        this.value = new HashMap<>();
    }

    public CompoundTag(final Map<String, INbtTag> value) {
        this.value = value;
    }

    public Map<String, INbtTag> getValue() {
        return this.value;
    }

    public void setValue(final Map<String, INbtTag> value) {
        this.value = value;
    }

    public int size() {
        return this.value.size();
    }

    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    public boolean contains(final String key) {
        return this.value.containsKey(key);
    }

    public boolean contains(final String key, final NbtType type) {
        INbtTag tag = this.get(key);
        if (tag == null) return false;
        if (type.isNumber() && tag.getNbtType().isNumber()) return true;
        return tag.getNbtType().equals(type);
    }

    public boolean containsExact(final String key, final NbtType type) {
        INbtTag tag = this.get(key);
        if (tag == null) return false;
        return tag.getNbtType().equals(type);
    }

    public <T extends INbtTag> T get(final String key) {
        return (T) this.value.get(key);
    }

    public void add(final String key, final INbtTag tag) {
        this.value.put(key, tag);
    }

    public void add(final String key, final Object o) {
        this.add(key, this.wrap(o));
    }

    public INbtTag remove(final String key) {
        return this.value.remove(key);
    }

    public byte getByte(final String key) {
        if (this.contains(key, NbtType.BYTE)) return this.<INbtNumber>get(key).byteValue();
        return 0;
    }

    public void addByte(final String key, final byte b) {
        this.add(key, new ByteTag(b));
    }

    public short getShort(final String key) {
        if (this.contains(key, NbtType.SHORT)) return this.<INbtNumber>get(key).shortValue();
        return 0;
    }

    public void addShort(final String key, final short s) {
        this.add(key, new ShortTag(s));
    }

    public int getInt(final String key) {
        if (this.contains(key, NbtType.INT)) return this.<INbtNumber>get(key).intValue();
        return 0;
    }

    public void addInt(final String key, final int i) {
        this.add(key, new IntTag(i));
    }

    public long getLong(final String key) {
        if (this.contains(key, NbtType.LONG)) return this.<INbtNumber>get(key).longValue();
        return 0;
    }

    public void addLong(final String key, final long l) {
        this.add(key, new LongTag(l));
    }

    public float getFloat(final String key) {
        if (this.contains(key, NbtType.FLOAT)) return this.<INbtNumber>get(key).floatValue();
        return 0;
    }

    public void addFloat(final String key, final float f) {
        this.add(key, new FloatTag(f));
    }

    public double getDouble(final String key) {
        if (this.contains(key, NbtType.DOUBLE)) return this.<INbtNumber>get(key).doubleValue();
        return 0;
    }

    public void addDouble(final String key, final double d) {
        this.add(key, new DoubleTag(d));
    }

    public byte[] getByteArray(final String key) {
        if (this.contains(key, NbtType.BYTE_ARRAY)) return this.<ByteArrayTag>get(key).getValue();
        return new byte[0];
    }

    public void addByteArray(final String key, final byte[] bytes) {
        this.add(key, new ByteArrayTag(bytes));
    }

    public String getString(final String key) {
        if (this.contains(key, NbtType.STRING)) return this.<StringTag>get(key).getValue();
        return "";
    }

    public void addString(final String key, final String s) {
        this.add(key, new StringTag(s));
    }

    public <T extends INbtTag> ListTag<T> getList(final String key) {
        if (this.contains(key, NbtType.LIST)) return this.get(key);
        return new ListTag<>();
    }

    public <T extends INbtTag> ListTag<T> getList(final String key, final NbtType type) {
        if (this.contains(key, NbtType.LIST)) {
            ListTag<T> list = this.get(key);
            if (!list.canAdd(type)) return new ListTag<>(type);
            else return list;
        }
        return new ListTag<>();
    }

    public void addList(final String key, final ListTag<?> list) {
        this.add(key, list);
    }

    public <T extends INbtTag> void addList(final String key, final T... items) {
        if (items.length == 0) {
            this.add(key, new ListTag<>());
        } else {
            List<INbtTag> list = new ArrayList<>();
            Collections.addAll(list, items);
            this.add(key, new ListTag<>(list));
        }
    }

    public void addList(final String key, final Object... items) {
        if (items.length == 0) {
            this.add(key, new ListTag<>());
        } else {
            List<INbtTag> list = new ArrayList<>();
            for (Object item : items) list.add(this.wrap(item));
            this.add(key, new ListTag<>(list));
        }
    }

    public CompoundTag getCompound(final String key) {
        if (this.contains(key, NbtType.COMPOUND)) return this.get(key);
        return new CompoundTag();
    }

    public void addCompound(final String key, final CompoundTag compound) {
        this.add(key, compound);
    }

    public int[] getIntArray(final String key) {
        if (this.contains(key, NbtType.INT_ARRAY)) return this.<IntArrayTag>get(key).getValue();
        return new int[0];
    }

    public void addIntArray(final String key, final int[] ints) {
        this.add(key, new IntArrayTag(ints));
    }

    public long[] getLongArray(final String key) {
        if (this.contains(key, NbtType.LONG_ARRAY)) return this.<LongArrayTag>get(key).getValue();
        return new long[0];
    }

    public void addLongArray(final String key, final long[] longs) {
        this.add(key, new LongArrayTag(longs));
    }

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
