package net.lenni0451.mcstructs.text.serializer;

import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.components.StringComponent;

import java.util.function.Function;

/**
 * Deserialize a legacy formatted string to an {@link ATextComponent}.
 */
public class LegacyStringDeserializer {

    /**
     * Parse a string with legacy formatting codes into an {@link ATextComponent}.<br>
     * Minecraft 1.13+ ignores unknown formatting codes. Earlier versions will handle them like {@link TextFormatting#RESET}.
     *
     * @param s            The string to parse
     * @param unknownReset Handle unknown formatting codes as reset
     * @return The parsed string
     */
    public static ATextComponent parse(final String s, final boolean unknownReset) {
        return parse(s, TextFormatting.COLOR_CHAR, unknownReset);
    }

    /**
     * Parse a string with legacy formatting codes into an {@link ATextComponent}.<br>
     * Minecraft 1.13+ ignores unknown formatting codes. Earlier versions will handle them like {@link TextFormatting#RESET}.
     *
     * @param s            The string to parse
     * @param colorChar    The color char to use (e.g. {@link TextFormatting#COLOR_CHAR})
     * @param unknownReset Handle unknown formatting codes as reset
     * @return The parsed string
     */
    public static ATextComponent parse(final String s, final char colorChar, final boolean unknownReset) {
        return parse(s, colorChar, c -> {
            TextFormatting formatting = TextFormatting.getByCode(c);
            if (formatting == null) {
                if (unknownReset) return TextFormatting.RESET;
                else return null;
            }
            return formatting;
        });
    }

    /**
     * Parse a string with legacy formatting codes into an {@link ATextComponent}.<br>
     * The {@code formattingResolver} should return a formatting for the given char or {@code null} if the previous formatting should be kept.<br>
     * When returning a color the previous formattings like {@code bold, italic, etc.} will be reset.
     *
     * @param s                  The string to parse
     * @param colorChar          The color char to use (e.g. {@link TextFormatting#COLOR_CHAR})
     * @param formattingResolver The function that resolves the formatting for the given char
     * @return The parsed string
     */
    public static ATextComponent parse(final String s, final char colorChar, final Function<Character, TextFormatting> formattingResolver) {
        char[] chars = s.toCharArray();
        Style style = new Style();
        StringBuilder currentPart = new StringBuilder();
        ATextComponent out = new StringComponent("");

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == colorChar) {
                if (i + 1 < chars.length) {
                    char format = chars[++i];
                    TextFormatting formatting = formattingResolver.apply(format);
                    if (formatting == null) continue;

                    if (currentPart.length() != 0) {
                        out.append(new StringComponent(currentPart.toString()).setStyle(style.copy()));
                        currentPart = new StringBuilder();
                        if (formatting.isColor() || TextFormatting.RESET.equals(formatting)) style = new Style();
                    }
                    style.setFormatting(formatting);
                }
            } else {
                currentPart.append(c);
            }
        }
        if (currentPart.length() != 0) out.append(new StringComponent(currentPart.toString()).setStyle(style));
        if (out.getSiblings().size() == 1) return out.getSiblings().get(0);
        return out;
    }

}
