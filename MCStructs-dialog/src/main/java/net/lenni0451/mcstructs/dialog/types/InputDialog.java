package net.lenni0451.mcstructs.dialog.types;

import lombok.Value;
import net.lenni0451.mcstructs.dialog.Dialog;
import net.lenni0451.mcstructs.dialog.DialogType;
import net.lenni0451.mcstructs.dialog.input.DialogInput;
import net.lenni0451.mcstructs.dialog.submit.DialogSubmit;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;

public class InputDialog extends Dialog {

    public InputDialog(final DialogType type, final TextComponent title, final @Nullable TextComponent externalTitle, final boolean canCloseWithEscape) {
        super(type, title, externalTitle, canCloseWithEscape);
    }


    @Value
    public static class Input {
        private final String key;
        private final DialogInput control;
    }

    @Value
    public static class SubmitAction {
        private final String id;
        private final TextComponent label;
        @Nullable
        private final TextComponent tooltip;
        private final int width;
        private final DialogSubmit method;
    }

}
