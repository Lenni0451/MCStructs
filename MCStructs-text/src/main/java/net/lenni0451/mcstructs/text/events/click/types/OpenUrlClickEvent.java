package net.lenni0451.mcstructs.text.events.click.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;

import java.net.URI;

@EqualsAndHashCode(callSuper = false)
public class OpenUrlClickEvent extends ClickEvent {

    private UrlHolder url;

    public OpenUrlClickEvent(final String url) {
        super(ClickEventAction.OPEN_URL);
        try {
            this.url = new UriHolder(URI.create(url));
        } catch (Throwable t) {
            this.url = new StringHolder(url);
        }
    }

    public OpenUrlClickEvent(final URI url) {
        super(ClickEventAction.OPEN_URL);
        this.url = new UriHolder(url);
    }

    public UrlHolder getHolder() {
        return this.url;
    }

    public OpenUrlClickEvent setHolder(final UrlHolder holder) {
        this.url = holder;
        return this;
    }

    public String asString() {
        if (this.url instanceof StringHolder) {
            return ((StringHolder) this.url).getUrl();
        } else {
            return ((UriHolder) this.url).getUri().toString();
        }
    }

    public URI asUri() throws IllegalArgumentException {
        if (this.url instanceof StringHolder) {
            return URI.create(((StringHolder) this.url).getUrl());
        } else {
            return ((UriHolder) this.url).getUri();
        }
    }

    public OpenUrlClickEvent setUrl(final String url) {
        this.url = new StringHolder(url);
        return this;
    }

    public OpenUrlClickEvent setUrl(final URI url) {
        this.url = new UriHolder(url);
        return this;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("action", this.action)
                .add("url", this.url)
                .toString();
    }


    public interface UrlHolder {
    }

    @Data
    @AllArgsConstructor
    public static class StringHolder implements UrlHolder {
        private final String url;

        @Override
        public String toString() {
            return this.url;
        }
    }

    @Data
    @AllArgsConstructor
    public static class UriHolder implements UrlHolder {
        private final URI uri;

        @Override
        public String toString() {
            return this.uri.toString();
        }
    }

}
