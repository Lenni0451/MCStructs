package net.lenni0451.mcstructs.dialog.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.lenni0451.mcstructs.dialog.Dialog;
import net.lenni0451.mcstructs.dialog.DialogType;
import net.lenni0451.mcstructs.dialog.body.DialogBody;
import net.lenni0451.mcstructs.dialog.types.ButtonListDialog;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;

import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DialogListDialog extends ButtonListDialog {

    private List<Dialog> dialogs;
    @Nullable
    private ClickEvent onCancel;
    private int buttonWidth;

    public DialogListDialog(final TextComponent title, final boolean canCloseWithEscape, final List<DialogBody> body, final List<Dialog> dialogs, final int columns, final int buttonWidth) {
        this(title, null, canCloseWithEscape, body, dialogs, null, columns, buttonWidth);
    }

    public DialogListDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final List<DialogBody> body, final List<Dialog> dialogs, final int columns, final int buttonWidth) {
        this(title, externalTitle, canCloseWithEscape, body, dialogs, null, columns, buttonWidth);
    }

    public DialogListDialog(final TextComponent title, final boolean canCloseWithEscape, final List<DialogBody> body, final List<Dialog> dialogs, @Nullable final ClickEvent onCancel, final int columns, final int buttonWidth) {
        this(title, null, canCloseWithEscape, body, dialogs, onCancel, columns, buttonWidth);
    }

    public DialogListDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final List<DialogBody> body, final List<Dialog> dialogs, @Nullable final ClickEvent onCancel, final int columns, final int buttonWidth) {
        super(DialogType.DIALOG_LIST, title, externalTitle, canCloseWithEscape, body, columns);
        this.dialogs = dialogs;
        this.onCancel = onCancel;
        this.buttonWidth = buttonWidth;
    }

}
