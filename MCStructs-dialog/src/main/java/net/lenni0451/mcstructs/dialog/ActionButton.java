package net.lenni0451.mcstructs.dialog;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lenni0451.mcstructs.dialog.action.DialogAction;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;

@Data
@AllArgsConstructor
public class ActionButton {

    private TextComponent label;
    @Nullable
    private TextComponent tooltip;
    private int width;
    @Nullable
    private DialogAction action;

    public ActionButton(final TextComponent label, final int width) {
        this.label = label;
        this.tooltip = null;
        this.width = width;
    }

    public ActionButton(final TextComponent label, final int width, @Nullable final DialogAction action) {
        this.label = label;
        this.tooltip = null;
        this.width = width;
        this.action = action;
    }

    public ActionButton(final TextComponent label, @Nullable TextComponent tooltip, final int width) {
        this.label = label;
        this.tooltip = tooltip;
        this.width = width;
        this.action = null;
    }

}
