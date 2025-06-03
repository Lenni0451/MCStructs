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
public class ServerLinksDialog extends ButtonListDialog {

    @Nullable
    private ActionButton exitAction;
    private int buttonWidth;

    public ServerLinksDialog(final TextComponent title, final boolean canCloseWithEscape, final boolean pause, final AfterAction action, final List<DialogBody> body, final List<Input> inputs,
                             final int columns, final int buttonWidth) {
        this(title, null, canCloseWithEscape, pause, action, body, inputs, null, columns, buttonWidth);
    }

    public ServerLinksDialog(final TextComponent title, final boolean canCloseWithEscape, final boolean pause, final AfterAction action, final List<DialogBody> body, final List<Input> inputs,
                             @Nullable final ActionButton exitAction, final int columns, final int buttonWidth) {
        this(title, null, canCloseWithEscape, pause, action, body, inputs, exitAction, columns, buttonWidth);
    }

    public ServerLinksDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final boolean pause, final AfterAction action, final List<DialogBody> body, final List<Input> inputs,
                             final int columns, final int buttonWidth) {
        this(title, externalTitle, canCloseWithEscape, pause, action, body, inputs, null, columns, buttonWidth);
    }

    public ServerLinksDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final boolean pause, final AfterAction action, final List<DialogBody> body, final List<Input> inputs,
                             @Nullable final ActionButton exitAction, final int columns, final int buttonWidth) {
        super(DialogType.SERVER_LINKS, title, externalTitle, canCloseWithEscape, pause, action, body, inputs, columns);
        this.exitAction = exitAction;
        this.buttonWidth = buttonWidth;
    }

}
