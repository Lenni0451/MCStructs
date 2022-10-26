package net.lenni0451.mcstructs.text.components;

import net.lenni0451.mcstructs.text.ATextComponent;

import java.util.Objects;

public class StringComponent extends ATextComponent {

    private final String text;

    public StringComponent(final String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String asString() {
        return this.text;
    }

    @Override
    public ATextComponent copy() {
        return this.putMetaCopy(new StringComponent(this.text));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringComponent that = (StringComponent) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "StringComponent{text='" + text + "', siblings=" + this.getSiblings() + ", style=" + this.getStyle() + "}";
    }

}
