package net.lenni0451.mcstructs.dialog.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.lenni0451.mcstructs.dialog.ActionButton;
import net.lenni0451.mcstructs.dialog.AfterAction;
import net.lenni0451.mcstructs.dialog.DialogType;
import net.lenni0451.mcstructs.dialog.Input;
import net.lenni0451.mcstructs.dialog.body.DialogBody;
import net.lenni0451.mcstructs.dialog.types.ButtonListDialog;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MultiActionDialog extends ButtonListDialog {

    private List<ActionButton> actions;
    @Nullable
    private ActionButton exitAction;

    public MultiActionDialog(final TextComponent title, final boolean canCloseWithEscape, final boolean pause, final AfterAction action, final List<DialogBody> body, final List<Input> inputs,
                             final List<ActionButton> actions, final int columns) {
        this(title, null, canCloseWithEscape, pause, action, body, inputs, actions, null, columns);
    }

    public MultiActionDialog(final TextComponent title, final boolean canCloseWithEscape, final boolean pause, final AfterAction action, final List<DialogBody> body, final List<Input> inputs,
                             final List<ActionButton> actions, @Nullable final ActionButton exitAction, final int columns) {
        this(title, null, canCloseWithEscape, pause, action, body, inputs, actions, exitAction, columns);
    }

    public MultiActionDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final boolean pause, final AfterAction action, final List<DialogBody> body, final List<Input> inputs,
                             final List<ActionButton> actions, final int columns) {
        this(title, externalTitle, canCloseWithEscape, pause, action, body, inputs, actions, null, columns);
    }

    public MultiActionDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final boolean pause, final AfterAction action, final List<DialogBody> body, final List<Input> inputs,
                             final List<ActionButton> actions, @Nullable final ActionButton exitAction, final int columns) {
        super(DialogType.MULTI_ACTION, title, externalTitle, canCloseWithEscape, pause, action, body, inputs, columns);
        this.actions = actions;
        this.exitAction = exitAction;
    }

}
