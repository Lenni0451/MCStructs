package net.lenni0451.mcstructs.dialog.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.lenni0451.mcstructs.dialog.*;
import net.lenni0451.mcstructs.dialog.body.DialogBody;
import net.lenni0451.mcstructs.dialog.types.ButtonListDialog;
import net.lenni0451.mcstructs.registry.TypedTagEntryList;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DialogListDialog extends ButtonListDialog {

    private TypedTagEntryList<Dialog> dialogs;
    @Nullable
    private ActionButton exitAction;
    private int buttonWidth;

    public DialogListDialog(final TextComponent title, final boolean canCloseWithEscape, final boolean pause, final AfterAction action, final List<DialogBody> body, final List<Input> inputs,
                            final TypedTagEntryList<Dialog> dialogs, final int columns, final int buttonWidth) {
        this(title, null, canCloseWithEscape, pause, action, body, inputs, dialogs, null, columns, buttonWidth);
    }

    public DialogListDialog(final TextComponent title, final boolean canCloseWithEscape, final boolean pause, final AfterAction action, final List<DialogBody> body, final List<Input> inputs,
                            final TypedTagEntryList<Dialog> dialogs, @Nullable final ActionButton exitAction, final int columns, final int buttonWidth) {
        this(title, null, canCloseWithEscape, pause, action, body, inputs, dialogs, exitAction, columns, buttonWidth);
    }

    public DialogListDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final boolean pause, final AfterAction action, final List<DialogBody> body, final List<Input> inputs,
                            final TypedTagEntryList<Dialog> dialogs, final int columns, final int buttonWidth) {
        this(title, externalTitle, canCloseWithEscape, pause, action, body, inputs, dialogs, null, columns, buttonWidth);
    }

    public DialogListDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final boolean pause, final AfterAction action, final List<DialogBody> body, final List<Input> inputs,
                            final TypedTagEntryList<Dialog> dialogs, @Nullable final ActionButton exitAction, final int columns, final int buttonWidth) {
        super(DialogType.DIALOG_LIST, title, externalTitle, canCloseWithEscape, pause, action, body, inputs, columns);
        this.dialogs = dialogs;
        this.exitAction = exitAction;
        this.buttonWidth = buttonWidth;
    }

}
