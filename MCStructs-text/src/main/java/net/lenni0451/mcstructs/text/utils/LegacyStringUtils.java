package net.lenni0451.mcstructs.text.utils;

import net.lenni0451.mcstructs.core.utils.ToString;
import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.stringformat.StringFormat;
import net.lenni0451.mcstructs.text.stringformat.handling.ColorHandling;
import net.lenni0451.mcstructs.text.stringformat.handling.DeserializerUnknownHandling;
import net.lenni0451.mcstructs.text.stringformat.handling.SerializerUnknownHandling;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Replaced by {@link StringFormat}.
 */
@Deprecated //for removal
public class LegacyStringUtils {

    /**
     * Replaced by {@link StringFormat#styleAt(String, int, ColorHandling, DeserializerUnknownHandling)}.
     */
    @Deprecated
    public static LegacyStyle getStyleAt(final String s, final int position, final boolean unknownWhite) {
        //return StringFormat.vanilla().styleAt(s, position, ColorHandling.RESET, unknownWhite ? DeserializerUnknownHandling.WHITE : DeserializerUnknownHandling.IGNORE);
        return getStyleAt(s, position, c -> {
            TextFormatting formatting = TextFormatting.getByCode(c);
            if (formatting == null) {
                if (unknownWhite) return TextFormatting.WHITE;
                else return null;
            }
            return formatting;
        });
    }

    /**
     * Replaced by {@link StringFormat#styleAt(String, int, ColorHandling, DeserializerUnknownHandling)}.
     */
    @Deprecated
    public static LegacyStyle getStyleAt(final String s, final int position, final Function<Character, TextFormatting> formattingResolver) {
        //return StringFormat.vanilla().styleAt(s, position, ColorHandling.RESET, (resolved, currentText) -> formattingResolver.apply(resolved.raw().charAt(0)));
        char[] chars = s.toCharArray();
        LegacyStyle legacyStyle = new LegacyStyle();

        for (int i = 0; i < Math.min(chars.length, position); i++) {
            char c = chars[i];
            if (c == TextFormatting.COLOR_CHAR) {
                if (i + 1 < chars.length) {
                    char code = chars[++i];
                    TextFormatting formatting = formattingResolver.apply(code);
                    if (formatting == null) continue;

                    if (TextFormatting.RESET.equals(formatting)) {
                        legacyStyle.setColor(null);
                        legacyStyle.getStyles().clear();
                    } else if (formatting.isColor()) {
                        legacyStyle.setColor(formatting);
                        legacyStyle.getStyles().clear();
                    } else {
                        legacyStyle.getStyles().add(formatting);
                    }
                }
            }
        }
        return legacyStyle;
    }

    /**
     * Replaced by {@link StringFormat#split(String, String, ColorHandling, SerializerUnknownHandling, DeserializerUnknownHandling)}.
     */
    @Deprecated
    public static String[] split(final String s, final String split, final boolean unknownWhite) {
//        return StringFormat.vanilla().split(s, split, ColorHandling.RESET, SerializerUnknownHandling.THROW, unknownWhite ? DeserializerUnknownHandling.WHITE : DeserializerUnknownHandling.IGNORE);
        return split(s, split, (c) -> {
            TextFormatting formatting = TextFormatting.getByCode(c);
            if (formatting == null) {
                if (unknownWhite) return TextFormatting.WHITE;
                else return null;
            }
            return formatting;
        });
    }

    /**
     * Replaced by {@link StringFormat#split(String, String, ColorHandling, SerializerUnknownHandling, DeserializerUnknownHandling)}.
     */
    @Deprecated
    public static String[] split(final String s, final String split, final Function<Character, TextFormatting> formattingResolver) {
//        return StringFormat.vanilla().split(s, split, ColorHandling.RESET, SerializerUnknownHandling.THROW, (resolved, currentText) -> formattingResolver.apply(resolved.raw().charAt(0)));
        String[] parts = s.split(Pattern.quote(split));
        for (int i = 1; i < parts.length; i++) {
            String prev = parts[i - 1];
            LegacyStyle style = getStyleAt(prev, prev.length(), formattingResolver);
            parts[i] = style.toLegacy() + parts[i];
        }
        return parts;
    }


    @Deprecated
    public static class LegacyStyle {
        private TextFormatting color = null;
        private final Set<TextFormatting> styles = new HashSet<>();

        private LegacyStyle() {
        }

        public void setColor(@Nullable final TextFormatting color) {
            this.color = color;
        }

        @Nullable
        public TextFormatting getColor() {
            return this.color;
        }

        @Nonnull
        public Set<TextFormatting> getStyles() {
            return this.styles;
        }

        public String toLegacy() {
            StringBuilder out = new StringBuilder();
            if (this.color != null) out.append(this.color.toLegacy());
            for (TextFormatting style : this.styles) out.append(style.toLegacy());
            return out.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LegacyStyle that = (LegacyStyle) o;
            return Objects.equals(this.color, that.color) && Objects.equals(this.styles, that.styles);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.color, this.styles);
        }

        @Override
        public String toString() {
            return ToString.of(this)
                    .add("color", this.color)
                    .add("styles", this.styles)
                    .toString();
        }
    }

}
