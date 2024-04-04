package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.text.ATextComponent;

import javax.annotation.Nullable;

public abstract class NbtComponent extends ATextComponent {

    private String component;
    private boolean resolve;
    private ATextComponent separator;

    public NbtComponent(final String component, final boolean resolve) {
        this(component, resolve, null);
    }

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
     * Set the component of this component.
     *
     * @param component The component
     * @return This component
     */
    public NbtComponent setComponent(final String component) {
        this.component = component;
        return this;
    }

    /**
     * @return Whether this component should be resolved
     */
    public boolean isResolve() {
        return this.resolve;
    }

    /**
     * Set whether this component should be resolved.
     *
     * @param resolve Whether this component should be resolved
     * @return This component
     */
    public NbtComponent setResolve(final boolean resolve) {
        this.resolve = resolve;
        return this;
    }

    /**
     * @return The separator of this component
     */
    @Nullable
    public ATextComponent getSeparator() {
        return this.separator;
    }

    /**
     * Set the separator of this component.
     *
     * @param separator The separator
     * @return This component
     */
    public NbtComponent setSeparator(final ATextComponent separator) {
        this.separator = separator;
        return this;
    }

    @Override
    public String asSingleString() {
        return "";
    }

}
