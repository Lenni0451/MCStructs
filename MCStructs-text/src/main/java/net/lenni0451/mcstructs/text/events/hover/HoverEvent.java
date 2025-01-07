package net.lenni0451.mcstructs.text.events.hover;

import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

/**
 * The abstract class for hover events.<br>
 * Until minecraft 1.16 hover events always used the {@link TextHoverEvent} implementation.
 */
public abstract class HoverEvent {

    protected HoverEventAction action;

    public HoverEvent(final HoverEventAction action) {
        this.action = action;
    }

    /**
     * @return The action of this hover event
     */
    public HoverEventAction getAction() {
        return this.action;
    }

    /**
     * Converts this hover event to a legacy text hover event.
     *
     * @param textComponentSerializer The serializer to use for text components
     * @param sNbt          The serializer to use for nbt tags
     * @return The converted hover event
     */
    @Deprecated //TODO: Remove
    public abstract TextHoverEvent toLegacy(final TextComponentSerializer textComponentSerializer, final SNbt<?> sNbt);

    @Override
    public abstract boolean equals(final Object o);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

}
