package net.lenni0451.mcstructs.core;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Text formattings are used to style texts in minecraft.<br>
 * Starting with minecraft 1.16 rgb colors have been added which also marked the end of just using § as a formatting character.
 */
public class TextFormatting {

    /**
     * All text formattings and colors.
     */
    public static final List<TextFormatting> ALL = new ArrayList<>();
    /**
     * All basic text colors.
     */
    public static final List<TextFormatting> COLORS = new ArrayList<>();
    /**
     * All text style formattings.
     */
    public static final List<TextFormatting> FORMATTINGS = new ArrayList<>();
    /**
     * The color char used for formatting.<br>
     * Using it has been deprecated since 1.16.
     */
    public static final char COLOR_CHAR = '\u00A7';
    public static final TextFormatting BLACK = new TextFormatting("BLACK", '0', 0x00_00_00);
    public static final TextFormatting DARK_BLUE = new TextFormatting("DARK_BLUE", '1', 0x00_00_AA);
    public static final TextFormatting DARK_GREEN = new TextFormatting("DARK_GREEN", '2', 0x00_AA_00);
    public static final TextFormatting DARK_AQUA = new TextFormatting("DARK_AQUA", '3', 0x00_AA_AA);
    public static final TextFormatting DARK_RED = new TextFormatting("DARK_RED", '4', 0xAA_00_00);
    public static final TextFormatting DARK_PURPLE = new TextFormatting("DARK_PURPLE", '5', 0xAA_00_AA);
    public static final TextFormatting GOLD = new TextFormatting("GOLD", '6', 0xFF_AA_00);
    public static final TextFormatting GRAY = new TextFormatting("GRAY", '7', 0xAA_AA_AA);
    public static final TextFormatting DARK_GRAY = new TextFormatting("DARK_GRAY", '8', 0x55_55_55);
    public static final TextFormatting BLUE = new TextFormatting("BLUE", '9', 0x55_55_FF);
    public static final TextFormatting GREEN = new TextFormatting("GREEN", 'a', 0x55_FF_55);
    public static final TextFormatting AQUA = new TextFormatting("AQUA", 'b', 0x55_FF_FF);
    public static final TextFormatting RED = new TextFormatting("RED", 'c', 0xFF_55_55);
    public static final TextFormatting LIGHT_PURPLE = new TextFormatting("LIGHT_PURPLE", 'd', 0xFF_55_FF);
    public static final TextFormatting YELLOW = new TextFormatting("YELLOW", 'e', 0xFF_FF_55);
    public static final TextFormatting WHITE = new TextFormatting("WHITE", 'f', 0xFF_FF_FF);
    public static final TextFormatting OBFUSCATED = new TextFormatting("OBFUSCATED", 'k');
    public static final TextFormatting BOLD = new TextFormatting("BOLD", 'l');
    public static final TextFormatting STRIKETHROUGH = new TextFormatting("STRIKETHROUGH", 'm');
    public static final TextFormatting UNDERLINE = new TextFormatting("UNDERLINE", 'n');
    public static final TextFormatting ITALIC = new TextFormatting("ITALIC", 'o');
    public static final TextFormatting RESET = new TextFormatting("RESET", 'r', -1);

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
        for (TextFormatting formatting : ALL) {
            if (formatting.getName().equalsIgnoreCase(name)) return formatting;
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
        if (s.startsWith("#")) return new TextFormatting(Integer.parseInt(s.substring(1), 16));
        else return getByName(s);
    }


    private final Type type;
    private final String name;
    private final char code;
    private final int rgbValue;

    private TextFormatting(final String name, final char code, final int rgbValue) {
        this.type = Type.COLOR;
        this.name = name;
        this.code = code;
        this.rgbValue = rgbValue;

        ALL.add(this);
        COLORS.add(this);
    }

    private TextFormatting(final String name, final char code) {
        this.type = Type.FORMATTING;
        this.name = name;
        this.code = code;
        this.rgbValue = -1;

        ALL.add(this);
        FORMATTINGS.add(this);
    }

    /**
     * Create a new rgb color.<br>
     * Alpha values are stripped.
     *
     * @param rgbValue The rgb value
     */
    public TextFormatting(final int rgbValue) {
        this.type = Type.RGB;
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
     * @return The lowercase name of the formatting or the rgb value as a hex string if it's a rgb color
     */
    public String serialize() {
        if (Type.RGB.equals(this.type)) return "#" + String.format("%06X", this.rgbValue);
        else return this.name.toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextFormatting that = (TextFormatting) o;
        return code == that.code && rgbValue == that.rgbValue && type == that.type && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, code, rgbValue);
    }

    @Override
    public String toString() {
        return "TextFormatting{type=" + type + ", name='" + name + '\'' + ", code=" + code + ", rgbValue=" + rgbValue + "}";
    }


    private enum Type {
        COLOR, FORMATTING, RGB
    }

}
