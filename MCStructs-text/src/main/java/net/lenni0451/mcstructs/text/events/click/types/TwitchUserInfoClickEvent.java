package net.lenni0451.mcstructs.text.events.click.types;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;

@Getter
@Setter
@EqualsAndHashCode
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
                .add("user", this.user)
                .toString();
    }

}
