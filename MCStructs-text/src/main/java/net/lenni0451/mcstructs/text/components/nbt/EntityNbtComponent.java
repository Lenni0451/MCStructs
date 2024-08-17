package net.lenni0451.mcstructs.text.components.nbt;

import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.NbtComponent;

import java.util.Objects;

public class EntityNbtComponent extends NbtComponent {

    private String selector;

    public EntityNbtComponent(final String component, final boolean resolve, final String selector) {
        super(component, resolve);
        this.selector = selector;
    }

    public EntityNbtComponent(final String component, final boolean resolve, final ATextComponent separator, final String selector) {
        super(component, resolve, separator);
        this.selector = selector;
    }

    /**
     * @return The selector of this component
     */
    public String getSelector() {
        return this.selector;
    }

    /**
     * Set the selector of this component.
     *
     * @param selector The selector
     * @return This component
     */
    public EntityNbtComponent setSelector(final String selector) {
        this.selector = selector;
        return this;
    }

    @Override
    public ATextComponent copy() {
        return this.putMetaCopy(this.shallowCopy());
    }

    @Override
    public ATextComponent shallowCopy() {
        if (this.getSeparator() == null) return new EntityNbtComponent(this.getComponent(), this.isResolve(), null, this.selector);
        else return new EntityNbtComponent(this.getComponent(), this.isResolve(), this.getSeparator(), this.selector);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityNbtComponent that = (EntityNbtComponent) o;
        return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.selector, that.selector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSiblings(), this.getStyle(), this.selector);
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty())
                .add("style", this.getStyle(), style -> !style.isEmpty())
                .add("selector", this.selector)
                .toString();
    }

}
