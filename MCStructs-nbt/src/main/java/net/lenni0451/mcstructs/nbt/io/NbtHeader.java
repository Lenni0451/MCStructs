package net.lenni0451.mcstructs.nbt.io;

import net.lenni0451.mcstructs.nbt.NbtType;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * The header of a Nbt tag.<br>
 * This is only used for reading/writing.
 */
public class NbtHeader {

    /**
     * The header of an {@link NbtType#END} tag.
     */
    public static NbtHeader END = new NbtHeader(NbtType.END, null);


    private final NbtType type;
    private final String name;

    public NbtHeader(@Nonnull final NbtType type, final String name) {
        Objects.requireNonNull(type);

        this.type = type;
        this.name = name;
    }

    /**
     * @return The type of the tag
     */
    public NbtType getType() {
        return this.type;
    }

    /**
     * @return The name of the tag
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return If the tag is an {@link NbtType#END} tag
     */
    public boolean isEnd() {
        return NbtType.END.equals(this.type);
    }

}
