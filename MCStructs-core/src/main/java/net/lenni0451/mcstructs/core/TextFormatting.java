package net.lenni0451.mcstructs.core;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Text formattings are used to style texts in minecraft.<br>
 * Starting with minecraft 1.16 rgb colors have been added which also marked the end of just using § as a formatting character.
 */
public class TextFormatting {

    /**
     * All text formattings and colors.
     */
    public static final Map<String, TextFormatting> ALL = new LinkedHashMap<>();
    /**
     * All basic text colors.
     */
    public static final Map<String, TextFormatting> COLORS = new LinkedHashMap<>();
    /**
     * All text style formattings.
     */
    public static final Map<String, TextFormatting> FORMATTINGS = new LinkedHashMap<>();
    /**
     * The color char used for formatting.<br>
     * Using it has been deprecated since 1.16.
     */
    public static final char COLOR_CHAR = '\u00A7';
    public static final TextFormatting BLACK = new TextFormatting("black", '0', 0x00_00_00);
    public static final TextFormatting DARK_BLUE = new TextFormatting("dark_blue", '1', 0x00_00_AA);
    public static final TextFormatting DARK_GREEN = new TextFormatting("dark_green", '2', 0x00_AA_00);
    public static final TextFormatting DARK_AQUA = new TextFormatting("dark_aqua", '3', 0x00_AA_AA);
    public static final TextFormatting DARK_RED = new TextFormatting("dark_red", '4', 0xAA_00_00);
    public static final TextFormatting DARK_PURPLE = new TextFormatting("dark_purple", '5', 0xAA_00_AA);
    public static final TextFormatting GOLD = new TextFormatting("gold", '6', 0xFF_AA_00);
    public static final TextFormatting GRAY = new TextFormatting("gray", '7', 0xAA_AA_AA);
    public static final TextFormatting DARK_GRAY = new TextFormatting("dark_gray", '8', 0x55_55_55);
    public static final TextFormatting BLUE = new TextFormatting("blue", '9', 0x55_55_FF);
    public static final TextFormatting GREEN = new TextFormatting("green", 'a', 0x55_FF_55);
    public static final TextFormatting AQUA = new TextFormatting("aqua", 'b', 0x55_FF_FF);
    public static final TextFormatting RED = new TextFormatting("red", 'c', 0xFF_55_55);
    public static final TextFormatting LIGHT_PURPLE = new TextFormatting("light_purple", 'd', 0xFF_55_FF);
    public static final TextFormatting YELLOW = new TextFormatting("yellow", 'e', 0xFF_FF_55);
    public static final TextFormatting WHITE = new TextFormatting("white", 'f', 0xFF_FF_FF);
    public static final TextFormatting OBFUSCATED = new TextFormatting("obfuscated", 'k');
    public static final TextFormatting BOLD = new TextFormatting("bold", 'l');
    public static final TextFormatting STRIKETHROUGH = new TextFormatting("strikethrough", 'm');
    public static final TextFormatting UNDERLINE = new TextFormatting("underline", 'n');
    public static final TextFormatting ITALIC = new TextFormatting("italic", 'o');
    public static final TextFormatting RESET = new TextFormatting("reset", 'r');

    /**
     * Get a formatting by ordinal.<br>
     * <br>
     * Example:<br>
     * <code>TextFormatting.getByOrdinal(0)</code> -{@literal >} {@link #BLACK}
     *
     * @param ordinal The ordinal of the formatting
     * @return The formatting or null if not found
     */
    @Nullable
    public static TextFormatting getByOrdinal(final int ordinal) {
        return ALL.values().stream().filter(formatting -> formatting.ordinal == ordinal).findFirst().orElse(null);
    }

    /**
     * Get a formatting by name.<br>
     * The name is case-insensitive.<br>
     * <br>
     * Example:<br>
     * <code>TextFormatting.getByName("RED")</code> -{@literal >} {@link #RED}
     *
     * @param name The name of the formatting
     * @return The formatting or null if not found
     */
    @Nullable
    public static TextFormatting getByName(final String name) {
        return ALL.get(name.toLowerCase());
    }

    /**
     * Get a formatting by code.<br>
     * <br>
     * Example:<br>
     * <code>TextFormatting.getByCode('c')</code> -{@literal >} {@link #RED}
     *
     * @param code The code of the formatting
     * @return The formatting or null if not found
     */
    @Nullable
    public static TextFormatting getByCode(final char code) {
        for (TextFormatting formatting : ALL.values()) {
            if (formatting.getCode() == code) return formatting;
        }
        return null;
    }

    /**
     * Get a formatting by name or a rgb color when the name starts with #.<br>
     * The name is case-insensitive.<br>
     * <br>
     * Example:<br>
     * <code>TextFormatting.parse("RED")</code> -{@literal >} {@link #RED}
     * <code>TextFormatting.parse("#FF0000")</code> -{@literal >} rgb(255, 0, 0)
     *
     * @param s The name of the formatting or a rgb color
     * @return The formatting or null if not found
     */
    @Nullable
    public static TextFormatting parse(final String s) {
        if (s.startsWith("#")) {
            try {
                return new TextFormatting(Integer.parseInt(s.substring(1), 16));
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            return getByName(s);
        }
    }

    /**
     * Get the closest formatting color to the given rgb color.
     *
     * @param rgb The rgb color
     * @return The closest formatting color
     */
    public static TextFormatting getClosestFormattingColor(final int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        TextFormatting closest = null;
        int closestDistance = Integer.MAX_VALUE;
        for (TextFormatting color : COLORS.values()) {
            int colorR = (color.getRgbValue() >> 16) & 0xFF;
            int colorG = (color.getRgbValue() >> 8) & 0xFF;
            int colorB = color.getRgbValue() & 0xFF;

            int distance = (r - colorR) * (r - colorR) + (g - colorG) * (g - colorG) + (b - colorB) * (b - colorB);
            if (distance < closestDistance) {
                closest = color;
                closestDistance = distance;
            }
        }
        return closest;
    }


    private final Type type;
    private final int ordinal;
    private final String name;
    private final char code;
    private final int rgbValue;

    private TextFormatting(final String name, final char code, final int rgbValue) {
        this.type = Type.COLOR;
        this.ordinal = ALL.size();
        this.name = name;
        this.code = code;
        this.rgbValue = rgbValue;

        ALL.put(name, this);
        COLORS.put(name, this);
    }

    private TextFormatting(final String name, final char code) {
        this.type = Type.FORMATTING;
        this.ordinal = ALL.size();
        this.name = name;
        this.code = code;
        this.rgbValue = -1;

        ALL.put(name, this);
        FORMATTINGS.put(name, this);
    }

    /**
     * Create a new rgb color.<br>
     * Alpha values are stripped.
     *
     * @param rgbValue The rgb value
     */
    public TextFormatting(final int rgbValue) {
        this.type = Type.RGB;
        this.ordinal = -1;
        this.name = "RGB_COLOR";
        this.code = '\0';
        this.rgbValue = rgbValue & 0xFF_FF_FF;
    }

    /**
     * @return If the formatting is a formatting color or rgb color
     */
    public boolean isColor() {
        return Type.COLOR.equals(this.type) || Type.RGB.equals(this.type);
    }

    /**
     * @return If the formatting is a formatting color (e.g. {@link #RED})
     */
    public boolean isFormattingColor() {
        return Type.COLOR.equals(this.type);
    }

    /**
     * @return If the formatting is a rgb color
     */
    public boolean isRGBColor() {
        return Type.RGB.equals(this.type);
    }

    /**
     * @return If the formatting is a formatting (e.g. {@link #OBFUSCATED}, {@link #BOLD}, ...)
     */
    public boolean isFormatting() {
        return Type.FORMATTING.equals(this.type);
    }

    /**
     * Get the ordinal of the formatting.<br>
     * The ordinal of rgb colors is -1.
     *
     * @return The ordinal
     */
    public int getOrdinal() {
        return this.ordinal;
    }

    /**
     * @return The name of the formatting (e.g. "RED")
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return The code of the formatting (e.g. 'c')
     */
    public char getCode() {
        return this.code;
    }

    /**
     * @return The rgb value of the formatting or -1 if it's not a color
     */
    public int getRgbValue() {
        return this.rgbValue;
    }

    /**
     * Get the legacy code of the formatting.<br>
     * The legacy code of rgb colors is an empty string.
     *
     * @return The legacy code (e.g. "§c")
     */
    public String toLegacy() {
        return String.valueOf(COLOR_CHAR) + this.code;
    }

    /**
     * @return The lowercase name of the formatting or the rgb value as a hex string if it's a rgb color
     */
    public String serialize() {
        if (Type.RGB.equals(this.type)) return "#" + String.format("%06X", this.rgbValue);
        else return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextFormatting that = (TextFormatting) o;
        return this.code == that.code && this.rgbValue == that.rgbValue && this.type == that.type && Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.name, this.code, this.rgbValue);
    }

    @Override
    public String toString() {
        return "TextFormatting{type=" + this.type + ", name='" + this.name + '\'' + ", code=" + this.code + ", rgbValue=" + this.rgbValue + "}";
    }


    private enum Type {
        COLOR, FORMATTING, RGB
    }

}
