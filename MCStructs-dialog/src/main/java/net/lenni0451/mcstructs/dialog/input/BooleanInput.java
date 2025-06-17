package net.lenni0451.mcstructs.dialog.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lenni0451.mcstructs.text.TextComponent;

@Data
@AllArgsConstructor
public class BooleanInput implements DialogInput {

    private final InputType type = InputType.BOOLEAN;
    private TextComponent label;
    private boolean initial = false;
    private String onTrue = "true";
    private String onFalse = "false";

    public BooleanInput(final TextComponent label) {
        this.label = label;
    }

}
