package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.ATextComponent;

import java.util.Objects;

public class StringComponent extends ATextComponent {

    private String text;

    public StringComponent() {
        this("");
    }

    public StringComponent(final String text) {
        this.text = text;
    }

    /**
     * @return The text of this component
     */
    public String getText() {
        return this.text;
    }

    /**
     * Set the text of this component.
     *
     * @param text The text
     * @return This component
     */
    public StringComponent setText(final String text) {
        this.text = text;
        return this;
    }

    @Override
    public String asSingleString() {
        return this.text;
    }

    @Override
    public ATextComponent copy() {
        return this.putMetaCopy(this.shallowCopy());
    }

    @Override
    public ATextComponent shallowCopy() {
        return new StringComponent(this.text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringComponent that = (StringComponent) o;
        return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSiblings(), this.getStyle(), this.text);
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty())
                .add("style", this.getStyle(), style -> !style.isEmpty())
                .add("text", this.text)
                .toString();
    }

}
