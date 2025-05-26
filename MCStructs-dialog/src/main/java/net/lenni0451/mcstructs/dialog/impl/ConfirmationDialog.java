package net.lenni0451.mcstructs.dialog.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.lenni0451.mcstructs.dialog.ClickAction;
import net.lenni0451.mcstructs.dialog.Dialog;
import net.lenni0451.mcstructs.dialog.DialogType;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ConfirmationDialog extends Dialog {

    private ClickAction yesButton;
    private ClickAction noButton;

    public ConfirmationDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final ClickAction yesButton, final ClickAction noButton) {
        super(DialogType.CONFIRMATION, title, externalTitle, canCloseWithEscape);
    }

}
