package net.lenni0451.mcstructs.text.utils;

import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.components.StringComponent;

/**
 * Utility class to color strings with a gradient.<br>
 * RGB Colors are used which only work in Minecraft 1.16+.<br>
 * If you want to translate the colors to the old format use {@link TextUtils#replaceRGBColors(ATextComponent)}, but keep in mind that many details will be lost.
 */
public class TextColorUtils {

    /**
     * Color a string with a gradient of {@link TextFormatting}s.<br>
     * The text color is interpolated between the given colors.<br>
     * If no colors are given a new {@link StringComponent} is returned.<br>
     * If one color is given the text will only be colored with that color.
     *
     * @param s      The string to color
     * @param colors The colors to use
     * @return The colored string
     */
    public static ATextComponent gradient(final String s, final TextFormatting... colors) {
        if (colors.length == 0) return new StringComponent(s);
        else if (colors.length == 1) return new StringComponent(s).setStyle(new Style().setFormatting(colors[0]));

        ATextComponent out = new StringComponent("");
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) fractions[i] = (float) i / (float) (colors.length - 1);
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            float progress = (float) i / (float) (chars.length - 1);
            int fromI = 0;
            int toI = 0;
            for (int j = 0; j < colors.length - 1; j++) {
                if (progress >= fractions[j] && progress <= fractions[j + 1]) {
                    fromI = j;
                    toI = j + 1;
                    break;
                }
            }
            float ratio = (progress - fractions[fromI]) / (fractions[toI] - fractions[fromI]);
            int rgb = interpolate(colors[fromI], colors[toI], ratio);
            out.append(new StringComponent(String.valueOf(chars[i])).setStyle(new Style().setColor(rgb)));
        }
        return out;
    }

    /**
     * Color a string with a rainbow gradient.<br>
     * The text color is interpolated between the given colors.
     *
     * @param s The string to color
     * @return The colored string
     */
    public static ATextComponent rainbow(final String s) {
        return gradient(
                s,
                new TextFormatting(0xFF0000),
                new TextFormatting(0xFFFF00),
                new TextFormatting(0x00FF00),
                new TextFormatting(0x00FFFF),
                new TextFormatting(0x0000FF),
                new TextFormatting(0xFF00FF),
                new TextFormatting(0xFF0000)
        );
    }

    private static int interpolate(final TextFormatting from, final TextFormatting to, final float ratio) {
        int ar = (from.getRgbValue() >> 16) & 0xFF;
        int ag = (from.getRgbValue() >> 8) & 0xFF;
        int ab = from.getRgbValue() & 0xFF;

        int br = (to.getRgbValue() >> 16) & 0xFF;
        int bg = (to.getRgbValue() >> 8) & 0xFF;
        int bb = to.getRgbValue() & 0xFF;

        int r = (int) (ar + (br - ar) * ratio);
        int g = (int) (ag + (bg - ag) * ratio);
        int b = (int) (ab + (bb - ab) * ratio);

        return r << 16 | g << 8 | b;
    }

}
