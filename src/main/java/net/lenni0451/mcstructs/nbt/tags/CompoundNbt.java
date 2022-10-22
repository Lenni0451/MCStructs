package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CompoundNbt implements INbtTag {

    private Map<String, INbtTag> value;

    public CompoundNbt(final Map<String, INbtTag> value) {
        this.value = value;
    }

    public Map<String, INbtTag> getValue() {
        return this.value;
    }

    public void setValue(final Map<String, INbtTag> value) {
        this.value = value;
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
    public String toString() {
        StringBuilder out = new StringBuilder("{");
        for (Map.Entry<String, INbtTag> entry : this.value.entrySet()) out.append(this.escapeName(entry.getKey())).append(":").append(entry.getValue()).append(",");
        if (!this.value.isEmpty()) out.deleteCharAt(out.length() - 1);
        return out.append("}").toString();
    }

    private String escapeName(String name) {
        if (name.matches("[a-zA-Z0-9._+-]+")) return name;
        else return new StringNbt(name).toString();
    }

}
