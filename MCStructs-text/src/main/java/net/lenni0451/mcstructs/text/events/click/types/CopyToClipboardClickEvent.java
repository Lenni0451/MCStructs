package net.lenni0451.mcstructs.text.events.click.types;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;

@EqualsAndHashCode
public class CopyToClipboardClickEvent extends ClickEvent {

    private String value;

    public CopyToClipboardClickEvent(final String value) {
        super(ClickEventAction.COPY_TO_CLIPBOARD);
        this.value = value;
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
                .add("value", this.value)
                .toString();
    }

}
