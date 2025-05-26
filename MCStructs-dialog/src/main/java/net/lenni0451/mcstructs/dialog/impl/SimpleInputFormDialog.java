package net.lenni0451.mcstructs.dialog.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.lenni0451.mcstructs.dialog.DialogType;
import net.lenni0451.mcstructs.dialog.types.InputDialog;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SimpleInputFormDialog extends InputDialog {

    private List<Input> inputs;
    private SubmitAction action;

    public SimpleInputFormDialog(final TextComponent title, final boolean canCloseWithEscape, final List<Input> inputs, final SubmitAction action) {
        this(title, null, canCloseWithEscape, inputs, action);
    }

    public SimpleInputFormDialog(final TextComponent title, @Nullable final TextComponent externalTitle, final boolean canCloseWithEscape, final List<Input> inputs, final SubmitAction action) {
        super(DialogType.SIMPLE_INPUT_FORM, title, externalTitle, canCloseWithEscape);
        this.inputs = inputs;
        this.action = action;
    }

}
