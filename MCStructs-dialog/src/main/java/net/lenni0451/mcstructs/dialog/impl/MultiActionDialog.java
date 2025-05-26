package net.lenni0451.mcstructs.dialog.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.lenni0451.mcstructs.dialog.ClickAction;
import net.lenni0451.mcstructs.dialog.DialogType;
import net.lenni0451.mcstructs.dialog.types.ButtonListDialog;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;

import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MultiActionDialog extends ButtonListDialog {

    private List<ClickAction> actions;
    @Nullable
    private ClickEvent onCancel;

    public MultiActionDialog(final TextComponent title, final boolean canCloseWithEscape, final List<ClickAction> actions, final int columns) {
        this(title, null, canCloseWithEscape, actions, null, columns);
    }

    public MultiActionDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final List<ClickAction> actions, final int columns) {
        this(title, externalTitle, canCloseWithEscape, actions, null, columns);
    }

    public MultiActionDialog(final TextComponent title, final boolean canCloseWithEscape, final List<ClickAction> actions, @Nullable final ClickEvent onCancel, final int columns) {
        this(title, null, canCloseWithEscape, actions, onCancel, columns);
    }

    public MultiActionDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final List<ClickAction> actions, @Nullable final ClickEvent onCancel, final int columns) {
        super(DialogType.MULTI_ACTION, title, externalTitle, canCloseWithEscape, columns);
        this.actions = actions;
        this.onCancel = onCancel;
    }

}
