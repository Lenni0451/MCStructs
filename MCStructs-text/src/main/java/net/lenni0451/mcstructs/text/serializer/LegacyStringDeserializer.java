package net.lenni0451.mcstructs.text.serializer;

import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.stringformat.StringFormat;
import net.lenni0451.mcstructs.text.stringformat.handling.ColorHandling;
import net.lenni0451.mcstructs.text.stringformat.handling.DeserializerUnknownHandling;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Replaced by {@link StringFormat}.
 */
@Deprecated //for removal
public class LegacyStringDeserializer {

    /**
     * Replaced by {@link StringFormat#fromString(String, ColorHandling, DeserializerUnknownHandling)}.
     */
    @Deprecated
    public static TextComponent parse(final String s, final boolean unknownWhite) {
        //return StringFormat.vanilla().fromString(s, ColorHandling.RESET, unknownWhite ? DeserializerUnknownHandling.WHITE : DeserializerUnknownHandling.IGNORE);
        return parse(s, TextFormatting.COLOR_CHAR, unknownWhite);
    }

    /**
     * Replaced by {@link StringFormat#fromString(String, ColorHandling, DeserializerUnknownHandling)}.
     */
    @Deprecated
    public static TextComponent parse(final String s, final char colorChar, final boolean unknownWhite) {
        //return StringFormat.vanilla(colorChar).fromString(s, ColorHandling.RESET, unknownWhite ? DeserializerUnknownHandling.WHITE : DeserializerUnknownHandling.IGNORE);
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
     * Replaced by {@link StringFormat#fromString(String, ColorHandling, DeserializerUnknownHandling)}.
     */
    @Deprecated
    public static TextComponent parse(final String s, final char colorChar, final Function<Character, TextFormatting> formattingResolver) {
        //return StringFormat.vanilla(colorChar).fromString(s, ColorHandling.RESET, (resolved, currentText) -> formattingResolver.apply(resolved.raw().charAt(0)));
        return parse(s, colorChar, Style::new, formattingResolver);
    }

    /**
     * Replaced by {@link StringFormat#fromString(String, ColorHandling, DeserializerUnknownHandling)}.<br>
     * Currently has no replacement for the styleSupplier. This method will only be removed once a replacement with a styleSupplier is available in StringFormat.
     */
    @Deprecated
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
