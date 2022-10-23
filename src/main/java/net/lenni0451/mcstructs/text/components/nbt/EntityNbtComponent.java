package net.lenni0451.mcstructs.text.components.nbt;

import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.NbtComponent;

public class EntityNbtComponent extends NbtComponent {

    private final String selector;

    public EntityNbtComponent(final String component, final boolean resolve, final String selector) {
        super(component, resolve);
        this.selector = selector;
    }

    public String getSelector() {
        return this.selector;
    }

    @Override
    public ATextComponent copy() {
        return new EntityNbtComponent(this.getComponent(), this.isResolve(), this.selector);
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
