package net.lenni0451.mcstructs.nbt.io;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;

/**
 * A class containing a nbt tag and its name.
 */
public class NamedTag {

    private final String name;
    private final NbtType type;
    private final NbtTag tag;

    public NamedTag(final String name, final NbtType type, final NbtTag tag) {
        this.name = name;
        this.type = type;
        this.tag = tag;
    }

    /**
     * @return The name of the tag
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return The type of the tag
     */
    public NbtType getType() {
        return this.type;
    }

    /**
     * Get the tag.
     *
     * @return The tag
     */
    public NbtTag getTag() {
        return this.tag;
    }

}
