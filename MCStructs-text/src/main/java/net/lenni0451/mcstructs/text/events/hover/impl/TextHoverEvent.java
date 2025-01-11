package net.lenni0451.mcstructs.text.events.hover.impl;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;

/**
 * The implementation for text hover events.
 */
@EqualsAndHashCode(callSuper = false)
public class TextHoverEvent extends HoverEvent {

    private TextComponent text;

    public TextHoverEvent(final TextComponent text) {
        super(HoverEventAction.SHOW_TEXT);
        this.text = text;
    }

    /**
     * @return The text of this hover event
     */
    public TextComponent getText() {
        return this.text;
    }

    /**
     * Set the text of this hover event.
     *
     * @param text The new text
     * @return This instance for chaining
     */
    public TextHoverEvent setText(final TextComponent text) {
        this.text = text;
        return this;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("action", this.action)
                .add("text", this.text)
                .toString();
    }

}
