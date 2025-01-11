package net.lenni0451.mcstructs.text.events.click.types;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;

@EqualsAndHashCode
public class LegacyClickEvent extends ClickEvent {

    private String value;

    public LegacyClickEvent(final ClickEventAction action, final String value) {
        super(action);
        this.value = value;
    }

    public ClickEvent setAction(final ClickEventAction action) {
        this.action = action;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("action", this.action)
                .add("value", this.value)
                .toString();
    }

}
