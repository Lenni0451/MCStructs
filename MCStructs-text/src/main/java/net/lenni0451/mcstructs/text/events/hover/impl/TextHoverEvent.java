package net.lenni0451.mcstructs.text.events.hover.impl;

import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.events.hover.AHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

import java.util.Objects;

/**
 * The implementation for text hover events.
 */
public class TextHoverEvent extends AHoverEvent {

    private final ATextComponent text;

    public TextHoverEvent(final HoverEventAction action, final ATextComponent text) {
        super(action);

        this.text = text;
    }

    /**
     * @return The text of this hover event
     */
    public ATextComponent getText() {
        return this.text;
    }

    @Override
    public TextHoverEvent toLegacy(TextComponentSerializer textComponentSerializer, SNbtSerializer<?> sNbtSerializer) {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextHoverEvent that = (TextHoverEvent) o;
        return Objects.equals(this.text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.text);
    }

    @Override
    public String toString() {
        return "TextHoverEvent{" +
                "text=" + this.text +
                '}';
    }

}
