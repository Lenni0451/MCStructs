package net.lenni0451.mcstructs.dialog.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;

@Data
@AllArgsConstructor
public class NumberRangeInput implements DialogInput {

    private final InputType type = InputType.NUMBER_RANGE;
    private int width = 200;
    private TextComponent label;
    private String labelFormat = "options.generic_value";
    private Range range;

    public NumberRangeInput(final TextComponent label, final Range range) {
        this.label = label;
        this.range = range;
    }


    @Value
    @AllArgsConstructor
    public static class Range {
        private final double start;
        private final double end;
        @Nullable
        private final Double initial;
        private final int steps;

        public Range(final double start, final double end, final int steps) {
            this.start = start;
            this.end = end;
            this.initial = null;
            this.steps = steps;
        }
    }

}
