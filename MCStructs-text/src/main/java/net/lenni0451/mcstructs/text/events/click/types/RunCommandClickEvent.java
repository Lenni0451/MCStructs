package net.lenni0451.mcstructs.text.events.click.types;

import lombok.EqualsAndHashCode;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;

@EqualsAndHashCode
public class RunCommandClickEvent extends ClickEvent {

    private String command;

    public RunCommandClickEvent(final String command) {
        super(ClickEventAction.RUN_COMMAND);
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("command", this.command)
                .toString();
    }

}
