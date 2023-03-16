package net.lenni0451.mcstructs.text.serializer;

import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.components.StringComponent;

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
        StringReader reader = new StringReader(s);
        Style style = new Style();
        StringBuilder currentPart = new StringBuilder();
        ATextComponent out = new StringComponent("");

        while (reader.hasNext()) {
            char c = reader.read();
            if (c == colorChar) {
                if (reader.hasNext()) {
                    char format = reader.read();
                    TextFormatting formatting = TextFormatting.getByCode(format);
                    if (formatting == null && !unknownReset) continue;

                    if (currentPart.length() != 0) {
                        out.append(new StringComponent(currentPart.toString()).setStyle(style.copy()));
                        currentPart = new StringBuilder();
                        if (formatting == null || formatting.isColor()) style = new Style();
                    }
                    if (formatting != null) style.setFormatting(formatting);
                }
            } else {
                currentPart.append(c);
            }
        }
        if (currentPart.length() != 0) out.append(new StringComponent(currentPart.toString()).setStyle(style));
        if (out.getSiblings().size() == 1) return out.getSiblings().get(0);
        return out;
    }


    private static class StringReader {
        private final String s;
        private int cursor = 0;

        private StringReader(final String s) {
            this.s = s;
        }

        private char read() {
            return this.s.charAt(this.cursor++);
        }

        private boolean hasNext() {
            return this.cursor < this.s.length();
        }
    }

}
