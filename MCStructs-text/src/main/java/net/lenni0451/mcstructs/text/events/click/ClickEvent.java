package net.lenni0451.mcstructs.text.events.click;

import java.util.Objects;

/**
 * The implementation for click events.
 */
public class ClickEvent {

    private final ClickEventAction action;
    private final String value;

    public ClickEvent(final ClickEventAction action, final String value) {
        this.action = action;
        this.value = value;
    }

    /**
     * @return The action of this click event
     */
    public ClickEventAction getAction() {
        return this.action;
    }

    /**
     * @return The value of this click event
     */
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClickEvent that = (ClickEvent) o;
        return this.action == that.action && Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.action, this.value);
    }

    @Override
    public String toString() {
        return "ClickEvent{action=" + this.action + ", value='" + this.value + "'}";
    }

}
