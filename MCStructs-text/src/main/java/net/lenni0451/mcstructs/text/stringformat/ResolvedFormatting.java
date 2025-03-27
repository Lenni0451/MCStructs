package net.lenni0451.mcstructs.text.stringformat;

import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.stringformat.matcher.VoidVanillaStringFormat;

import javax.annotation.Nullable;

/**
 * Represents a resolved formatting code.<br>
 * It is the result of {@link StringFormat#resolve(TextStringReader)} and contains the raw input and the resolved {@link TextFormatting} object.<br>
 * The {@link TextFormatting} object can be null if the input was invalid.
 *
 * @see VoidVanillaStringFormat
 */
public class ResolvedFormatting {

    private final String raw;
    @Nullable
    private final TextFormatting formatting;

    public ResolvedFormatting(final String raw, @Nullable final TextFormatting formatting) {
        this.raw = raw;
        this.formatting = formatting;
    }

    /**
     * @return The raw input that was resolved
     */
    public String raw() {
        return this.raw;
    }

    /**
     * @return The resolved formatting or null if the input was invalid
     */
    @Nullable
    public TextFormatting get() {
        return this.formatting;
    }

}
