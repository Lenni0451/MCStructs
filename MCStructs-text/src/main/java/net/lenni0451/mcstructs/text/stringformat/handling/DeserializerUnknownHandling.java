package net.lenni0451.mcstructs.text.stringformat.handling;

import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.stringformat.ResolvedFormatting;
import net.lenni0451.mcstructs.text.stringformat.StringFormat;

/**
 * Option to modify the behavior of how unknown formatting codes are handled in a {@link StringFormat} during deserialization.
 *
 * @see StringFormat#fromString(String, ColorHandling, DeserializerUnknownHandling)
 */
@FunctionalInterface
public interface DeserializerUnknownHandling {

    /**
     * Treat unknown formatting codes as white.<br>
     * This is the default behavior for Minecraft {@code <=} 1.12.2.
     */
    DeserializerUnknownHandling WHITE = (resolved, currentText) -> TextFormatting.WHITE;
    /**
     * Ignore unknown formatting codes.<br>
     * This is the default behavior for Minecraft 1.13+.
     */
    DeserializerUnknownHandling IGNORE = (resolved, currentText) -> null;
    /**
     * Treat unknown formatting codes as reset.
     */
    DeserializerUnknownHandling RESET = (resolved, currentText) -> TextFormatting.RESET;
    /**
     * Treat unknown formatting codes as text.
     */
    DeserializerUnknownHandling TEXT = (resolved, currentText) -> {
        currentText.append(resolved.raw());
        return null;
    };
    /**
     * Throw an exception when an unknown formatting code is found.
     */
    DeserializerUnknownHandling THROW = (resolved, currentText) -> {
        throw new IllegalArgumentException("Unknown formatting code: " + resolved.raw());
    };


    /**
     * Handle unknown formatting codes during deserialization.<br>
     * Return null to ignore the formatting code.
     *
     * @param resolved    The resolved formatting
     * @param currentText The output string builder
     * @return The text formatting to use
     */
    TextFormatting handle(final ResolvedFormatting resolved, final StringBuilder currentText);

}
