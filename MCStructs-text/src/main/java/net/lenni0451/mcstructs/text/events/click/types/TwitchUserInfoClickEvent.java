package net.lenni0451.mcstructs.text.events.click.types;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;

@EqualsAndHashCode(callSuper = false)
public class TwitchUserInfoClickEvent extends ClickEvent {

    private String user;

    public TwitchUserInfoClickEvent(final String user) {
        super(ClickEventAction.TWITCH_USER_INFO);
        this.user = user;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("action", this.action)
                .add("user", this.user)
                .toString();
    }

}
