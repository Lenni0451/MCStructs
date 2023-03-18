package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.text.ATextComponent;

import javax.annotation.Nullable;
import java.util.Objects;

public class SelectorComponent extends ATextComponent {

    private final String selector;
    private final ATextComponent separator;

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
     * @return The separator of this component
     */
    @Nullable
    public ATextComponent getSeparator() {
        return this.separator;
    }

    @Override
    public String asSingleString() {
        return this.selector;
    }

    @Override
    public ATextComponent copy() {
        if (this.separator == null) return this.putMetaCopy(new SelectorComponent(this.selector, null));
        else return this.putMetaCopy(new SelectorComponent(this.selector, this.separator.copy()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectorComponent that = (SelectorComponent) o;
        return Objects.equals(getSiblings(), that.getSiblings()) && Objects.equals(getStyle(), that.getStyle()) && Objects.equals(selector, that.selector) && Objects.equals(separator, that.separator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSiblings(), getStyle(), selector, separator);
    }

    @Override
    public String toString() {
        return "SelectorComponent{" +
                "siblings=" + getSiblings() +
                ", style=" + getStyle() +
                ", selector='" + selector + '\'' +
                ", separator=" + separator +
                '}';
    }

}
