package net.lenni0451.mcstructs.dialog.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;
import java.util.List;

@Data
@AllArgsConstructor
public class SingleOptionInput implements DialogInput {

    private final InputType type = InputType.SINGLE_OPTION;
    private int width = 200;
    private List<Entry> options;
    private TextComponent label;
    private boolean labelVisible = true;

    public SingleOptionInput(final List<Entry> options, final TextComponent label) {
        this.options = options;
        this.label = label;
    }


    @Value
    @AllArgsConstructor
    public static class Entry {
        private final String id;
        @Nullable
        private final TextComponent display;
        private final boolean initial;

        public Entry(final String id, final boolean initial) {
            this.id = id;
            this.display = null;
            this.initial = initial;
        }
    }

}
