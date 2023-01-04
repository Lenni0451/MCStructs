package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.NbtType;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class StringTag implements INbtTag {

    private String value;

    /**
     * Create a string tag with the value "".
     */
    public StringTag() {
        this("");
    }

    /**
     * Create a string tag with the given value.
     *
     * @param value The value
     */
    public StringTag(final String value) {
        this.value = value;
    }

    /**
     * @return The string value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set the string value.
     *
     * @param value The new value
     */
    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public NbtType getNbtType() {
        return NbtType.STRING;
    }

    @Override
    public void read(DataInput in, NbtReadTracker readTracker) throws IOException {
        readTracker.read(36);
        this.value = in.readUTF();
        readTracker.read(2 * this.value.length());
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.value);
    }

    @Override
    public INbtTag copy() {
        return new StringTag(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringTag stringTag = (StringTag) o;
        return Objects.equals(value, stringTag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "String(" + this.value + ")";
    }

}
