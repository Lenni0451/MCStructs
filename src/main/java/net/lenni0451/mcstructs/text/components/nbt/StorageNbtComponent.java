package net.lenni0451.mcstructs.text.components.nbt;

import net.lenni0451.mcstructs.general.Identifier;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.NbtComponent;

public class StorageNbtComponent extends NbtComponent {

    private final Identifier id;

    public StorageNbtComponent(final String component, final boolean resolve, final Identifier id) {
        super(component, resolve);
        this.id = id;
    }

    public Identifier getId() {
        return this.id;
    }

    @Override
    public ATextComponent copy() {
        return new StorageNbtComponent(this.getComponent(), this.isResolve(), this.id);
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
