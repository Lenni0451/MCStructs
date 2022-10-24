package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

public class CompoundNbt implements INbtTag {

    private Map<String, INbtTag> value;

    public CompoundNbt() {
        this.value = new HashMap<>();
    }

    public CompoundNbt(final Map<String, INbtTag> value) {
        this.value = value;
    }

    public Map<String, INbtTag> getValue() {
        return this.value;
    }

    public void setValue(final Map<String, INbtTag> value) {
        this.value = value;
    }

    public boolean contains(final String key) {
        return this.value.containsKey(key);
    }

    public boolean contains(final String key, final int type) {
        INbtTag tag = this.get(key);
        if (tag == null) return false;
        if (NbtRegistry.isNumber(type) && NbtRegistry.isNumber(tag.getId())) return true;
        return tag.getId() == type;
    }

    public boolean containsExact(final String key, final int type) {
        INbtTag tag = this.get(key);
        if (tag == null) return false;
        return tag.getId() == type;
    }

    public <T extends INbtTag> T get(final String key) {
        return (T) this.value.get(key);
    }

    public void add(final String key, final INbtTag tag) {
        this.value.put(key, tag);
    }

    public void add(final String key, final Object o) {
        this.add(key, NbtRegistry.wrap(o));
    }

    public byte getByte(final String key) {
        if (this.contains(key, NbtRegistry.BYTE_NBT)) return this.<INbtNumber>get(key).byteValue();
        return 0;
    }

    public void addByte(final String key, final byte b) {
        this.add(key, new ByteNbt(b));
    }

    public short getShort(final String key) {
        if (this.contains(key, NbtRegistry.SHORT_NBT)) return this.<INbtNumber>get(key).shortValue();
        return 0;
    }

    public void addShort(final String key, final short s) {
        this.add(key, new ShortNbt(s));
    }

    public int getInt(final String key) {
        if (this.contains(key, NbtRegistry.INT_NBT)) return this.<INbtNumber>get(key).intValue();
        return 0;
    }

    public void addInt(final String key, final int i) {
        this.add(key, new IntNbt(i));
    }

    public long getLong(final String key) {
        if (this.contains(key, NbtRegistry.LONG_NBT)) return this.<INbtNumber>get(key).longValue();
        return 0;
    }

    public void addLong(final String key, final long l) {
        this.add(key, new LongNbt(l));
    }

    public float getFloat(final String key) {
        if (this.contains(key, NbtRegistry.FLOAT_NBT)) return this.<INbtNumber>get(key).floatValue();
        return 0;
    }

    public void addFloat(final String key, final float f) {
        this.add(key, new FloatNbt(f));
    }

    public double getDouble(final String key) {
        if (this.contains(key, NbtRegistry.DOUBLE_NBT)) return this.<INbtNumber>get(key).doubleValue();
        return 0;
    }

    public void addDouble(final String key, final double d) {
        this.add(key, new DoubleNbt(d));
    }

    public byte[] getByteArray(final String key) {
        if (this.contains(key, NbtRegistry.BYTE_ARRAY_NBT)) return this.<ByteArrayNbt>get(key).getValue();
        return new byte[0];
    }

    public void addByteArray(final String key, final byte[] bytes) {
        this.add(key, new ByteArrayNbt(bytes));
    }

    public String getString(final String key) {
        if (this.contains(key, NbtRegistry.STRING_NBT)) return this.<StringNbt>get(key).getValue();
        return "";
    }

    public void addString(final String key, final String s) {
        this.add(key, new StringNbt(s));
    }

    public ListNbt<?> getList(final String key) {
        if (this.contains(key, NbtRegistry.LIST_NBT)) return this.get(key);
        return new ListNbt<>();
    }

    public ListNbt<?> getList(final String key, final int type) {
        if (this.contains(key, NbtRegistry.LIST_NBT)) {
            ListNbt<?> list = this.get(key);
            if (!list.canAdd(type)) return new ListNbt<>(NbtRegistry.getTagClass(type));
            else return list;
        }
        return new ListNbt<>();
    }

    public <T extends INbtTag> ListNbt<T> getList(final String key, final Class<T> type) {
        if (this.contains(key, NbtRegistry.LIST_NBT)) {
            ListNbt<?> list = this.get(key);
            if (!list.canAdd(type)) return new ListNbt<>(type);
            else return (ListNbt<T>) list;
        }
        return new ListNbt<>();
    }

    public void addList(final String key, final ListNbt<?> list) {
        this.add(key, list);
    }

    public void addList(final String key, final INbtTag... items) {
        if (items.length == 0) {
            this.add(key, new ListNbt<>());
        } else {
            List<INbtTag> list = new ArrayList<>();
            Collections.addAll(list, items);
            this.add(key, new ListNbt<>(list));
        }
    }

    public void addList(final String key, final Object... items) {
        if (items.length == 0) {
            this.add(key, new ListNbt<>());
        } else {
            List<INbtTag> list = new ArrayList<>();
            for (Object item : items) list.add(NbtRegistry.wrap(item));
            this.add(key, new ListNbt<>(list));
        }
    }

    public CompoundNbt getCompound(final String key) {
        if (this.contains(key, NbtRegistry.COMPOUND_NBT)) return this.get(key);
        return new CompoundNbt();
    }

    public void addCompound(final String key, final CompoundNbt compound) {
        this.add(key, compound);
    }

    public int[] getIntArray(final String key) {
        if (this.contains(key, NbtRegistry.INT_ARRAY_NBT)) return this.<IntArrayNbt>get(key).getValue();
        return new int[0];
    }

    public void addIntArray(final String key, final int[] ints) {
        this.add(key, new IntArrayNbt(ints));
    }

    public long[] getLongArray(final String key) {
        if (this.contains(key, NbtRegistry.LONG_ARRAY_NBT)) return this.<LongArrayNbt>get(key).getValue();
        return new long[0];
    }

    public void addLongArray(final String key, final long[] longs) {
        this.add(key, new LongArrayNbt(longs));
    }

    public boolean trim() {
        if (this.value.isEmpty()) return true;
        this.value.entrySet().removeIf(entry -> {
            INbtTag tag = entry.getValue();
            if (tag instanceof ByteNbt) return ((ByteNbt) tag).getValue() == 0;
            else if (tag instanceof ShortNbt) return ((ShortNbt) tag).getValue() == 0;
            else if (tag instanceof IntNbt) return ((IntNbt) tag).getValue() == 0;
            else if (tag instanceof LongNbt) return ((LongNbt) tag).getValue() == 0;
            else if (tag instanceof FloatNbt) return ((FloatNbt) tag).getValue() == 0;
            else if (tag instanceof DoubleNbt) return ((DoubleNbt) tag).getValue() == 0;
            else if (tag instanceof ByteArrayNbt) return ((ByteArrayNbt) tag).isEmpty();
            else if (tag instanceof StringNbt) return ((StringNbt) tag).getValue().isEmpty();
            else if (tag instanceof ListNbt) return ((ListNbt<?>) tag).trim();
            else if (tag instanceof CompoundNbt) return ((CompoundNbt) tag).trim();
            else if (tag instanceof IntArrayNbt) return ((IntArrayNbt) tag).isEmpty();
            else if (tag instanceof LongArrayNbt) return ((LongArrayNbt) tag).isEmpty();
            return false;
        });
        return this.value.isEmpty();
    }

    private String escapeName(String name) {
        if (name.matches("[a-zA-Z0-9._+-]+")) return name;
        else return new StringNbt(name).toString();
    }

    @Override
    public int getId() {
        return NbtRegistry.COMPOUND_NBT;
    }

    @Override
    public void read(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(384);
        this.value = new HashMap<>();
        while (true) {
            NbtHeader<?> header = NbtIO.readNbtHeader(in, readTracker);
            if (header.isEnd()) break;
            readTracker.read(224 + 16 * header.getName().length());

            INbtTag tag = NbtRegistry.newInstance(header.getType());
            readTracker.pushDepth();
            tag.read(in, readTracker);
            readTracker.popDepth();
            if (this.value.put(header.getName(), tag) != null) readTracker.read(288);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        for (Map.Entry<String, INbtTag> entry : this.value.entrySet()) {
            NbtIO.writeNbtHeader(out, new NbtHeader<>(entry.getValue().getClass(), entry.getKey()));
            entry.getValue().write(out);
        }
        NbtIO.writeNbtHeader(out, NbtHeader.END);
    }

    @Override
    public INbtTag copy() {
        Map<String, INbtTag> value = new HashMap<>();
        for (Map.Entry<String, INbtTag> entry : this.value.entrySet()) value.put(entry.getKey(), entry.getValue().copy());
        return new CompoundNbt(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompoundNbt that = (CompoundNbt) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("{");
        for (Map.Entry<String, INbtTag> entry : this.value.entrySet()) out.append(this.escapeName(entry.getKey())).append(":").append(entry.getValue()).append(",");
        if (!this.value.isEmpty()) out.deleteCharAt(out.length() - 1);
        return out.append("}").toString();
    }

}
