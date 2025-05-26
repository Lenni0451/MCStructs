package net.lenni0451.mcstructs.dialog.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.lenni0451.mcstructs.text.TextComponent;

@Data
@AllArgsConstructor
public class TextInput implements DialogInput {

    private final InputType type = InputType.TEXT;
    private int width = 200;
    private TextComponent label;
    private boolean labelVisible = true;
    private String initial = "";

    public TextInput(final TextComponent label) {
        this.label = label;
    }

}
