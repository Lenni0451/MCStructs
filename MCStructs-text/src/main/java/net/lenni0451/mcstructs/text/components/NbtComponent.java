package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.text.ATextComponent;

import javax.annotation.Nullable;

public abstract class NbtComponent extends ATextComponent {

    private final String component;
    private final boolean resolve;
    private final ATextComponent separator;

    public NbtComponent(final String component, final boolean resolve, @Nullable final ATextComponent separator) {
        this.component = component;
        this.resolve = resolve;
        this.separator = separator;
    }

    /**
     * @return The component of this component
     */
    public String getComponent() {
        return this.component;
    }

    /**
     * @return Whether this component should be resolved
     */
    public boolean isResolve() {
        return this.resolve;
    }

    /**
     * @return The separator of this component
     */
    @Nullable
    public ATextComponent getSeparator() {
        return this.separator;
    }

    @Override
    public String asSingleString() {
        return "";
    }

}
