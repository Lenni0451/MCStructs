package net.lenni0451.mcstructs.text.events.hover;

import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

/**
 * The abstract class for hover events.<br>
 * Until minecraft 1.16 hover events always used the {@link net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent} implementation.
 */
public abstract class AHoverEvent {

    private final HoverEventAction action;

    public AHoverEvent(final HoverEventAction action) {
        this.action = action;
    }

    public HoverEventAction getAction() {
        return this.action;
    }

    /**
     * Converts this hover event to a legacy text hover event.
     *
     * @return The converted hover event
     */
    public abstract TextHoverEvent toLegacy(final TextComponentSerializer textComponentSerializer, final SNbtSerializer<?> sNbtSerializer);

    @Override
    public abstract boolean equals(final Object o);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

}
