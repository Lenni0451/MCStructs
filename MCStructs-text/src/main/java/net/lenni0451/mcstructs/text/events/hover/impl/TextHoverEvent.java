package net.lenni0451.mcstructs.text.events.hover.impl;

import net.lenni0451.mcstructs.core.utils.ToString;
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

    private ATextComponent text;

    public TextHoverEvent(final HoverEventAction action, final ATextComponent text) {
        super(action);

        this.text = text;
    }

    /**
     * Set the action of the hover event.<br>
     * Other actions than {@link HoverEventAction#SHOW_TEXT} will result in legacy hover events.
     *
     * @param action The new action
     * @return This instance for chaining
     */
    public TextHoverEvent setAction(final HoverEventAction action) {
        this.action = action;
        return this;
    }

    /**
     * @return The text of this hover event
     */
    public ATextComponent getText() {
        return this.text;
    }

    /**
     * Set the text of this hover event.
     *
     * @param text The new text
     * @return This instance for chaining
     */
    public TextHoverEvent setText(final ATextComponent text) {
        this.text = text;
        return this;
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
        return ToString.of(this)
                .add("text", this.text)
                .toString();
    }

}
