package net.lenni0451.mcstructs.text.events.click.types;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;

@EqualsAndHashCode
public class OpenFileClickEvent extends ClickEvent {

    private String path;

    public OpenFileClickEvent(final String path) {
        super(ClickEventAction.OPEN_FILE);
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("path", this.path)
                .toString();
    }

}
