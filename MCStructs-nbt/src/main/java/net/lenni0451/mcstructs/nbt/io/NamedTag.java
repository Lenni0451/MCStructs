package net.lenni0451.mcstructs.nbt.io;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;

/**
 * A class containing a nbt tag and its name.
 */
public class NamedTag {

    private final String name;
    private final NbtType type;
    private final INbtTag tag;

    public NamedTag(final String name, final NbtType type, final INbtTag tag) {
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
    public INbtTag getTag() {
        return this.tag;
    }

}
