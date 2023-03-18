package net.lenni0451.mcstructs.nbt.tags;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;

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
     * @return This tag
     */
    public StringTag setValue(final String value) {
        this.value = value;
        return this;
    }

    @Override
    public NbtType getNbtType() {
        return NbtType.STRING;
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
