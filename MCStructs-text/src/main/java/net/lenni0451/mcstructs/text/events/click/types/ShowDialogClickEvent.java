package net.lenni0451.mcstructs.text.events.click.types;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.lenni0451.mcstructs.converter.SerializedData;
import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ShowDialogClickEvent extends ClickEvent {

    private SerializedData<?> dialogData;

    public ShowDialogClickEvent(final SerializedData<?> dialogData) {
        super(ClickEventAction.SHOW_DIALOG);
        this.dialogData = dialogData;
    }

    @Override
    public String toString() {
        return ToString.of(this)
                .add("action", this.action)
                .add("dialogData", this.dialogData)
                .toString();
    }

}
