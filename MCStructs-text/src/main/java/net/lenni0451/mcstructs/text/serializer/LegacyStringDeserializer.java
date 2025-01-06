package net.lenni0451.mcstructs.text.serializer;

import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.components.StringComponent;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Deserialize a legacy formatted string to an {@link TextComponent}.
 */
public class LegacyStringDeserializer {

    /**
     * Parse a string with legacy formatting codes into an {@link TextComponent}.<br>
     * Minecraft 1.13+ ignores unknown formatting codes. Earlier versions will handle them like {@link TextFormatting#WHITE}.
     *
     * @param s            The string to parse
     * @param unknownWhite Handle unknown formatting codes as reset
     * @return The parsed string
     */
    public static TextComponent parse(final String s, final boolean unknownWhite) {
        return parse(s, TextFormatting.COLOR_CHAR, unknownWhite);
    }

    /**
     * Parse a string with legacy formatting codes into an {@link TextComponent}.<br>
     * Minecraft 1.13+ ignores unknown formatting codes. Earlier versions will handle them like {@link TextFormatting#WHITE}.
     *
     * @param s            The string to parse
     * @param colorChar    The color char to use (e.g. {@link TextFormatting#COLOR_CHAR})
     * @param unknownWhite Handle unknown formatting codes as reset
     * @return The parsed string
     */
    public static TextComponent parse(final String s, final char colorChar, final boolean unknownWhite) {
        return parse(s, colorChar, c -> {
            TextFormatting formatting = TextFormatting.getByCode(c);
            if (formatting == null) {
                if (unknownWhite) return TextFormatting.WHITE;
                else return null;
            }
            return formatting;
        });
    }

    /**
     * Parse a string with legacy formatting codes into an {@link TextComponent}.<br>
     * The {@code formattingResolver} should return a formatting for the given char or {@code null} if the previous formatting should be kept.<br>
     * When returning a color the previous formattings like {@code bold, italic, etc.} will be reset.
     *
     * @param s                  The string to parse
     * @param colorChar          The color char to use (e.g. {@link TextFormatting#COLOR_CHAR})
     * @param formattingResolver The function that resolves the formatting for the given char
     * @return The parsed string
     */
    public static TextComponent parse(final String s, final char colorChar, final Function<Character, TextFormatting> formattingResolver) {
        return parse(s, colorChar, Style::new, formattingResolver);
    }

    /**
     * Parse a string with legacy formatting codes into an {@link TextComponent}.<br>
     * The {@code formattingResolver} should return a formatting for the given char or {@code null} if the previous formatting should be kept.<br>
     * When returning a color the previous formattings like {@code bold, italic, etc.} will be reset.
     *
     * @param s                  The string to parse
     * @param colorChar          The color char to use (e.g. {@link TextFormatting#COLOR_CHAR})
     * @param styleSupplier      The supplier for the default style (not copied)
     * @param formattingResolver The function that resolves the formatting for the given char
     * @return The parsed string
     */
    public static TextComponent parse(final String s, final char colorChar, final Supplier<Style> styleSupplier, final Function<Character, TextFormatting> formattingResolver) {
        char[] chars = s.toCharArray();
        Style style = styleSupplier.get();
        StringBuilder currentPart = new StringBuilder();
        TextComponent out = new StringComponent("");

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
                        if (formatting.isColor() || TextFormatting.RESET.equals(formatting)) style = styleSupplier.get();
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
