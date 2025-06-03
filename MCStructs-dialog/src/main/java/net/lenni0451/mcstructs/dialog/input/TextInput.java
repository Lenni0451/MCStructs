package net.lenni0451.mcstructs.dialog.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;

@Data
@AllArgsConstructor
public class TextInput implements DialogInput {

    private final InputType type = InputType.TEXT;
    private int width = 200;
    private TextComponent label;
    private boolean labelVisible = true;
    private String initial = "";
    private int maxLength = 32;
    @Nullable
    private MultilineOptions multiline = null;

    public TextInput(final TextComponent label) {
        this.label = label;
    }


    @Value
    public static class MultilineOptions {
        @Nullable
        private final Integer maxLines;
        @Nullable
        private final Integer height;
    }

}
