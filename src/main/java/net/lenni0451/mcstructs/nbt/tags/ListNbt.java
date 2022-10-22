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

}
