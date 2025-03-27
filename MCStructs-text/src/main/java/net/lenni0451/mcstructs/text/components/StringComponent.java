package net.lenni0451.mcstructs.text.components;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.TextComponent;

@EqualsAndHashCode(callSuper = true)
public class StringComponent extends TextComponent {

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
    public TextComponent copy() {
        return this.copyMetaTo(this.shallowCopy());
    }

    @Override
    public TextComponent shallowCopy() {
        return new StringComponent(this.text).setStyle(this.getStyle().copy());
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
