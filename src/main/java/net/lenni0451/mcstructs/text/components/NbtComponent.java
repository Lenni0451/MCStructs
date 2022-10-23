package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.text.ATextComponent;

public abstract class NbtComponent extends ATextComponent {

    private final String component;
    private final boolean resolve;

    public NbtComponent(final String component, final boolean resolve) {
        this.component = component;
        this.resolve = resolve;

    }

    public String getComponent() {
        return this.component;
    }

    public boolean isResolve() {
        return this.resolve;
    }

}
