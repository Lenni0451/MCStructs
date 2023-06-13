package net.lenni0451.mcstructs.text.utils;

import net.lenni0451.mcstructs.core.TextFormatting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class LegacyStringUtils {

    /**
     * Get the legacy style of a string at the given position.<br>
     * If the position is negative the style will be empty.<br>
     * If the position is greater than the length of the string the last style will be returned.<br>
     * Minecraft 1.13+ ignores unknown formatting codes. Earlier versions will handle them like {@link TextFormatting#RESET}.
     *
     * @param s            The string to get the style from
     * @param position     The position to get the style at
     * @param unknownReset Handle unknown formatting codes as reset
     * @return The style at the given position
     */
    public static LegacyStyle getStyleAt(final String s, final int position, final boolean unknownReset) {
        char[] chars = s.toCharArray();
        LegacyStyle legacyStyle = new LegacyStyle();

        for (int i = 0; i < Math.min(chars.length, position); i++) {
            char c = chars[i];
            if (c == TextFormatting.COLOR_CHAR) {
                if (i + 1 < chars.length) {
                    char code = chars[++i];
                    TextFormatting formatting = TextFormatting.getByCode(code);
                    if (formatting != null) {
                        if (formatting.isColor()) {
                            legacyStyle.setColor(formatting);
                            legacyStyle.getStyles().clear();
                        } else {
                            legacyStyle.getStyles().add(formatting);
                        }
                    } else if (unknownReset) {
                        legacyStyle.setColor(null);
                        legacyStyle.getStyles().clear();
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
     * @param unknownReset Handle unknown formatting codes as reset
     * @return The split string
     */
    public static String[] split(final String s, final String split, final boolean unknownReset) {
        String[] parts = s.split(Pattern.quote(split));
        for (int i = 1; i < parts.length; i++) {
            String prev = parts[i - 1];
            LegacyStyle style = getStyleAt(prev, prev.length(), unknownReset);
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
                    "color=" + color +
                    ", styles=" + styles +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LegacyStyle that = (LegacyStyle) o;
            return Objects.equals(color, that.color) && Objects.equals(styles, that.styles);
        }

        @Override
        public int hashCode() {
            return Objects.hash(color, styles);
        }
    }

}
