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
public class NoticeDialog extends Dialog {

    public static ActionButton defaultAction() {
        return new ActionButton(TextComponent.translation("gui.ok"), null, 150, null);
    }

    public static boolean isDefaultAction(final ActionButton action) {
        return action != null && action.getLabel().equals(TextComponent.translation("gui.ok")) && action.getTooltip() == null && action.getWidth() == 150 && action.getAction() == null;
    }


    private ActionButton action;

    public NoticeDialog(final TextComponent title, final boolean canCloseWithEscape, final boolean pause, final AfterAction afterAction, final List<DialogBody> body, final List<Input> inputs,
                        final ActionButton action) {
        this(title, null, canCloseWithEscape, pause, afterAction, body, inputs, action);
    }

    public NoticeDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final boolean pause, final AfterAction afterAction, final List<DialogBody> body, final List<Input> inputs,
                        final ActionButton action) {
        super(DialogType.NOTICE, title, externalTitle, canCloseWithEscape, pause, afterAction, body, inputs);
        this.action = action;
    }

}
