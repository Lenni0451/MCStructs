package net.lenni0451.mcstructs.dialog.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.lenni0451.mcstructs.dialog.*;
import net.lenni0451.mcstructs.dialog.body.DialogBody;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ConfirmationDialog extends Dialog {

    private ActionButton yesButton;
    private ActionButton noButton;

    public ConfirmationDialog(final TextComponent title, final boolean canCloseWithEscape, final boolean pause, final AfterAction afterAction, final List<DialogBody> body, final List<Input> inputs,
                              final ActionButton yesButton, final ActionButton noButton) {
        this(title, null, canCloseWithEscape, pause, afterAction, body, inputs, yesButton, noButton);
    }

    public ConfirmationDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final boolean pause, final AfterAction afterAction, final List<DialogBody> body, final List<Input> inputs,
                              final ActionButton yesButton, final ActionButton noButton) {
        super(DialogType.CONFIRMATION, title, externalTitle, canCloseWithEscape, pause, afterAction, body, inputs);
        this.yesButton = yesButton;
        this.noButton = noButton;
    }

}
