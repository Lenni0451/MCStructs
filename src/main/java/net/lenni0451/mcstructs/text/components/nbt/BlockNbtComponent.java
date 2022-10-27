package net.lenni0451.mcstructs.text.components.nbt;

import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.NbtComponent;

public class BlockNbtComponent extends NbtComponent {

    private final String pos;

    public BlockNbtComponent(final String rawComponent, final boolean resolve, final ATextComponent separator, final String pos) {
        super(rawComponent, resolve, separator);
        this.pos = pos;
    }

    public String getPos() {
        return this.pos;
    }

    @Override
    public ATextComponent copy() {
        if (this.getSeparator() == null) return new BlockNbtComponent(this.getComponent(), this.isResolve(), this.getSeparator(), this.pos);
        else return new BlockNbtComponent(this.getComponent(), this.isResolve(), this.getSeparator().copy(), this.pos);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }

}
