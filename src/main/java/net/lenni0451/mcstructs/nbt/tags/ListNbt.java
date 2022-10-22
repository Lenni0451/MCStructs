package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.NbtRegistry;
import net.lenni0451.mcstructs.nbt.exceptions.NbtReadException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListNbt<T extends INbtTag> implements INbtTag {

    private Class<T> type;
    private List<T> value;

    public ListNbt() {
        this(null, new ArrayList<>());
    }

    public ListNbt(final Class<T> type) {
        this(type, new ArrayList<>());
    }

    public ListNbt(final List<T> list) {
        if (!list.isEmpty()) {
            this.type = (Class<T>) list.get(0).getClass();
            if (list.stream().anyMatch(tag -> !tag.getClass().equals(this.type))) throw new IllegalArgumentException("Tried to create list with multiple nbt types");
        }
        this.value = list;
    }

    public ListNbt(final Class<T> type, final List<T> value) {
        this.type = type;
        this.value = value;
    }

    public Class<T> getType() {
        return this.type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public List<T> getValue() {
        return this.value;
    }

    public void setValue(List<T> value) {
        this.value = value;
    }

    public T get(final int index) {
        return this.value.get(index);
    }

    public void add(final T tag) {
        this.check(tag);
        this.value.add(tag);
    }

    public void set(final int index, final T tag) {
        this.check(tag);
        this.value.set(index, tag);
    }

    public void remove(final T tag) {
        this.check(tag);
        this.value.remove(tag);
    }

    public boolean canAdd(final INbtTag tag) {
        if (this.type == null || this.value.isEmpty()) return true;
        return this.type.equals(tag.getClass());
    }

    public boolean canAdd(final int type) {
        if (this.type == null || this.value.isEmpty()) return true;
        return NbtRegistry.getTagId(this.type) == type;
    }

    public boolean canAdd(final Class<? extends INbtTag> type) {
        if (this.type == null || this.value.isEmpty()) return true;
        return this.type.equals(type);
    }

    public boolean trim() {
        if (this.value.isEmpty()) return true;
        if (CompoundNbt.class.equals(this.type)) this.value.forEach(tag -> ((CompoundNbt) tag).trim());
        else if (ListNbt.class.equals(this.type)) this.value.forEach(tag -> ((ListNbt<?>) tag).trim());
        return false;
    }

    @Override
    public int getId() {
        return NbtRegistry.LIST_NBT;
    }

    @Override
    public void read(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(296);
        int typeId = in.readByte();
        int count = in.readInt();
        if (typeId == NbtRegistry.END_NBT && count > 0) throw new NbtReadException("ListNbt with type EndNbt and count > 0");
        readTracker.read(32 * count);
        this.type = (Class<T>) NbtRegistry.getTagClass(typeId);
        this.value = new ArrayList<>(Math.min(count, 512));
        for (int i = 0; i < count; i++) {
            T tag = NbtRegistry.newInstance(this.type);
            readTracker.pushDepth();
            tag.read(in, readTracker);
            readTracker.popDepth();
            this.value.add(tag);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeByte(NbtRegistry.getTagId(this.type));
        out.writeInt(this.value.size());
        for (T tag : this.value) tag.write(out);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("[");
        for (T tag : this.value) out.append(tag).append(",");
        if (!this.value.isEmpty()) out.deleteCharAt(out.length() - 1);
        return out.append("]").toString();
    }


    private void check(final T tag) {
        if (this.type == null || this.value.isEmpty()) {
            this.type = (Class<T>) tag.getClass();
            this.value.clear();
        } else if (!this.type.equals(tag.getClass())) {
            throw new IllegalArgumentException("Can't add " + tag.getClass().getSimpleName() + " to a " + this.type.getSimpleName() + " list");
        }
    }

}
