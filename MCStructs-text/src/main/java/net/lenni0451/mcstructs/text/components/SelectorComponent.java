package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.ATextComponent;

import javax.annotation.Nullable;
import java.util.Objects;

public class SelectorComponent extends ATextComponent {

    private String selector;
    @Nullable
    private ATextComponent separator;

    public SelectorComponent(final String selector) {
        this(selector, null);
    }

    public SelectorComponent(final String selector, @Nullable final ATextComponent separator) {
        this.selector = selector;
        this.separator = separator;
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
    public SelectorComponent setSelector(final String selector) {
        this.selector = selector;
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
    public SelectorComponent setSeparator(@Nullable final ATextComponent separator) {
        this.separator = separator;
        return this;
    }

    @Override
    public String asSingleString() {
        return this.selector;
    }

    @Override
    public ATextComponent copy() {
        return this.putMetaCopy(this.shallowCopy());
    }

    @Override
    public ATextComponent shallowCopy() {
        if (this.separator == null) return new SelectorComponent(this.selector, null).setStyle(this.getStyle().copy());
        else return new SelectorComponent(this.selector, this.separator.copy()).setStyle(this.getStyle().copy());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectorComponent that = (SelectorComponent) o;
        return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.selector, that.selector) && Objects.equals(this.separator, that.separator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSiblings(), this.getStyle(), this.selector, this.separator);
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty())
                .add("style", this.getStyle(), style -> !style.isEmpty())
                .add("selector", this.selector)
                .add("separator", this.separator, Objects::nonNull)
                .toString();
    }

}
