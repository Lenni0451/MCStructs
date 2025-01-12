package net.lenni0451.mcstructs.text.events.click.types;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;

import java.net.URI;

@EqualsAndHashCode(callSuper = false)
public class OpenUrlClickEvent extends ClickEvent {

    private URI url;

    public OpenUrlClickEvent(final URI url) {
        super(ClickEventAction.OPEN_URL);
        this.url = url;
    }

    public URI getUrl() {
        return this.url;
    }

    public void setUrl(final URI url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("action", this.action)
                .add("url", this.url)
                .toString();
    }

}
