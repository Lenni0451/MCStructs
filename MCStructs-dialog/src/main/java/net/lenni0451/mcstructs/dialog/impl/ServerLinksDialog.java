package net.lenni0451.mcstructs.dialog.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
public class ServerLinksDialog extends ButtonListDialog {

    @Nullable
    private ClickEvent onCancel;
    private int buttonWidth;

    public ServerLinksDialog(final TextComponent title, final boolean canCloseWithEscape, final List<DialogBody> body, final int columns, final int buttonWidth) {
        this(title, null, canCloseWithEscape, body, null, columns, buttonWidth);
    }

    public ServerLinksDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final List<DialogBody> body, final int columns, final int buttonWidth) {
        this(title, externalTitle, canCloseWithEscape, body, null, columns, buttonWidth);
    }

    public ServerLinksDialog(final TextComponent title, final boolean canCloseWithEscape, final List<DialogBody> body, @Nullable final ClickEvent onCancel, final int columns, final int buttonWidth) {
        this(title, null, canCloseWithEscape, body, onCancel, columns, buttonWidth);
    }

    public ServerLinksDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final List<DialogBody> body, @Nullable final ClickEvent onCancel, final int columns, final int buttonWidth) {
        super(DialogType.SERVER_LINKS, title, externalTitle, canCloseWithEscape, body, columns);
        this.onCancel = onCancel;
        this.buttonWidth = buttonWidth;
    }

}
