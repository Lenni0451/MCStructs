package net.lenni0451.mcstructs.dialog.types;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.lenni0451.mcstructs.dialog.AfterAction;
import net.lenni0451.mcstructs.dialog.Dialog;
import net.lenni0451.mcstructs.dialog.DialogType;
import net.lenni0451.mcstructs.dialog.Input;
import net.lenni0451.mcstructs.dialog.body.DialogBody;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class ButtonListDialog extends Dialog {

    private int columns;

    public ButtonListDialog(final DialogType type, final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final boolean pause, final AfterAction action, final List<DialogBody> body, final List<Input> inputs, final int columns) {
        super(type, title, externalTitle, canCloseWithEscape, pause, action, body, inputs);
        this.columns = columns;
    }

}
