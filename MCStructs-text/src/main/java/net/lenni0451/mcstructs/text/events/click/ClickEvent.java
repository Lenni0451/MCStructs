package net.lenni0451.mcstructs.text.events.click;

import java.util.Objects;

/**
 * The implementation for click events.
 */
public class ClickEvent {

    private ClickEventAction action;
    private String value;

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
     * Set the action of this click event.
     *
     * @param action The new action
     * @return This instance for chaining
     */
    public ClickEvent setAction(final ClickEventAction action) {
        this.action = action;
        return this;
    }

    /**
     * @return The value of this click event
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set the value of this click event.
     *
     * @param value The new value
     * @return This instance for chaining
     */
    public ClickEvent setValue(final String value) {
        this.value = value;
        return this;
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
