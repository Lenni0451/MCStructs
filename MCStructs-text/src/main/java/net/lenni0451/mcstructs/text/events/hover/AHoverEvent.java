package net.lenni0451.mcstructs.text.events.hover;

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

    @Override
    public abstract boolean equals(final Object o);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

}
