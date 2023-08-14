package net.lenni0451.mcstructs.text.utils;

import net.lenni0451.mcstructs.core.TextFormatting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

public class LegacyStringUtils {

    /**
     * Get the legacy style of a string at the given position.<br>
     * If the position is negative the style will be empty.<br>
     * If the position is greater than the length of the string the last style will be returned.<br>
     * Minecraft 1.13+ ignores unknown formatting codes. Earlier versions will handle them like {@link TextFormatting#WHITE}.
     *
     * @param s            The string to get the style from
     * @param position     The position to get the style at
     * @param unknownWhite Handle unknown formatting codes as reset
     * @return The style at the given position
     */
    public static LegacyStyle getStyleAt(final String s, final int position, final boolean unknownWhite) {
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
     * Get the legacy style of a string at the given position.<br>
     * If the position is negative the style will be empty.<br>
     * If the position is greater than the length of the string the last style will be returned.<br>
     * The {@code formattingResolver} should return a formatting for the given char or {@code null} if the previous formatting should be kept.<br>
     * When returning a color the previous formattings like {@code bold, italic, etc.} will be reset.
     *
     * @param s                  The string to get the style from
     * @param position           The position to get the style at
     * @param formattingResolver The function that resolves the formatting for the given char
     * @return The style at the given position
     */
    public static LegacyStyle getStyleAt(final String s, final int position, final Function<Character, TextFormatting> formattingResolver) {
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
     * Split a string by a given split string and keep the legacy style of the previous part.<br>
     * Minecraft 1.13+ ignores unknown formatting codes. Earlier versions will handle them like {@link TextFormatting#RESET}.
     *
     * @param s            The string to split
     * @param split        The split string
     * @param unknownWhite Handle unknown formatting codes as reset
     * @return The split string
     */
    public static String[] split(final String s, final String split, final boolean unknownWhite) {
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
     * Split a string by a given split string and keep the legacy style of the previous part.<br>
     * The {@code formattingResolver} should return a formatting for the given char or {@code null} if the previous formatting should be kept.<br>
     * When returning a color the previous formattings like {@code bold, italic, etc.} will be reset.
     *
     * @param s                  The string to split
     * @param split              The split string
     * @param formattingResolver The function that resolves the formatting for the given char
     * @return The split string
     */
    public static String[] split(final String s, final String split, final Function<Character, TextFormatting> formattingResolver) {
        String[] parts = s.split(Pattern.quote(split));
        for (int i = 1; i < parts.length; i++) {
            String prev = parts[i - 1];
            LegacyStyle style = getStyleAt(prev, prev.length(), formattingResolver);
            parts[i] = style.toLegacy() + parts[i];
        }
        return parts;
    }


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
        public String toString() {
            return "LegacyStyle{" +
                    "color=" + this.color +
                    ", styles=" + this.styles +
                    '}';
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
    }

}
