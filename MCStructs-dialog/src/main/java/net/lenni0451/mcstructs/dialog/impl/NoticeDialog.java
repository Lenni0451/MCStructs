package net.lenni0451.mcstructs.dialog.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.lenni0451.mcstructs.dialog.ClickAction;
import net.lenni0451.mcstructs.dialog.Dialog;
import net.lenni0451.mcstructs.dialog.DialogType;
import net.lenni0451.mcstructs.dialog.body.DialogBody;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NoticeDialog extends Dialog {

    public static ClickAction defaultAction() {
        return new ClickAction(TextComponent.translation("gui.ok"), null, 150, null);
    }


    private ClickAction action;

    public NoticeDialog(final TextComponent title, final boolean canCloseWithEscape, final List<DialogBody> body, final ClickAction action) {
        this(title, null, canCloseWithEscape, body, action);
    }

    public NoticeDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final List<DialogBody> body, final ClickAction action) {
        super(DialogType.NOTICE, title, externalTitle, canCloseWithEscape, body);
        this.action = action;
    }

}
