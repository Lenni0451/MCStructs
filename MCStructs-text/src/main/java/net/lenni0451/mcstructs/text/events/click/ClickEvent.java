package net.lenni0451.mcstructs.text.events.click;

import net.lenni0451.mcstructs.text.events.click.types.*;

import java.net.URI;

/**
 * The abstract class for click events.
 */
public abstract class ClickEvent {

    public static OpenURLClickEvent openURL(final URI url) {
        return new OpenURLClickEvent(url);
    }

    public static OpenFileClickEvent openFile(final String path) {
        return new OpenFileClickEvent(path);
    }

    public static RunCommandClickEvent runCommand(final String command) {
        return new RunCommandClickEvent(command);
    }

    public static TwitchUserInfoClickEvent twitchUserInfo(final String user) {
        return new TwitchUserInfoClickEvent(user);
    }

    public static SuggestCommandClickEvent suggestCommand(final String command) {
        return new SuggestCommandClickEvent(command);
    }

    public static ChangePageClickEvent changePage(final int page) {
        return new ChangePageClickEvent(page);
    }

    public static CopyToClipboardClickEvent copyToClipboard(final String value) {
        return new CopyToClipboardClickEvent(value);
    }


    protected ClickEventAction action;

    public ClickEvent(final ClickEventAction action) {
        this.action = action;
    }

    /**
     * @return The action of this click event
     */
    public ClickEventAction getAction() {
        return this.action;
    }

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

}
