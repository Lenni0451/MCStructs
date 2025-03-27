package net.lenni0451.mcstructs.text.stringformat.matcher;

import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.stringformat.StringFormat;
import net.lenni0451.mcstructs.text.stringformat.TextStringReader;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link StringFormat} implementation for the ANSI format.<br>
 * Generally used in terminals and consoles.
 */
public class AnsiStringFormat extends StringFormat {

    private static final String PREFIX = "\033[";
    private static final String TRUE_COLOR_PREFIX = "38;2;";
    private static final String SUFFIX = "m";
    private static final Map<TextFormatting, String> FORMATTING_TO_ANSI = init(new HashMap<>(), map -> {
        map.put(TextFormatting.BLACK, "30");
        map.put(TextFormatting.DARK_BLUE, "34");
        map.put(TextFormatting.DARK_GREEN, "32");
        map.put(TextFormatting.DARK_AQUA, "36");
        map.put(TextFormatting.DARK_RED, "31");
        map.put(TextFormatting.DARK_PURPLE, "35");
        map.put(TextFormatting.GOLD, "33");
        map.put(TextFormatting.GRAY, "37");
        map.put(TextFormatting.DARK_GRAY, "90");
        map.put(TextFormatting.BLUE, "94");
        map.put(TextFormatting.GREEN, "92");
        map.put(TextFormatting.AQUA, "96");
        map.put(TextFormatting.RED, "91");
        map.put(TextFormatting.LIGHT_PURPLE, "95");
        map.put(TextFormatting.YELLOW, "93");
        map.put(TextFormatting.WHITE, "97");
//        map.put(TextFormatting.OBFUSCATED, ""); //Not supported
        map.put(TextFormatting.BOLD, "1");
        map.put(TextFormatting.STRIKETHROUGH, "9");
        map.put(TextFormatting.UNDERLINE, "4");
        map.put(TextFormatting.ITALIC, "3");
        map.put(TextFormatting.RESET, "0");
    });
    private static final Map<String, TextFormatting> ANSI_TO_FORMATTING = init(new HashMap<>(), map -> {
        map.put("30", TextFormatting.BLACK);
        map.put("34", TextFormatting.DARK_BLUE);
        map.put("32", TextFormatting.DARK_GREEN);
        map.put("36", TextFormatting.DARK_AQUA);
        map.put("31", TextFormatting.DARK_RED);
        map.put("35", TextFormatting.DARK_PURPLE);
        map.put("33", TextFormatting.GOLD);
        map.put("37", TextFormatting.GRAY);
        map.put("90", TextFormatting.DARK_GRAY);
        map.put("94", TextFormatting.BLUE);
        map.put("92", TextFormatting.GREEN);
        map.put("96", TextFormatting.AQUA);
        map.put("91", TextFormatting.RED);
        map.put("95", TextFormatting.LIGHT_PURPLE);
        map.put("93", TextFormatting.YELLOW);
        map.put("97", TextFormatting.WHITE);
//        map.put("", TextFormatting.OBFUSCATED); //Not supported
        map.put("1", TextFormatting.BOLD);
        map.put("9", TextFormatting.STRIKETHROUGH);
        map.put("4", TextFormatting.UNDERLINE);
        map.put("3", TextFormatting.ITALIC);
        map.put("0", TextFormatting.RESET);
    });

    private final boolean trueColor;

    public AnsiStringFormat(final boolean trueColor) {
        super('\033');
        this.trueColor = trueColor;
    }

    @Override
    public boolean matches(TextStringReader reader) {
        if (reader.canRead(PREFIX.length()) && reader.read(PREFIX.length()).equals(PREFIX)) {
            if (reader.canRead(2) && ANSI_TO_FORMATTING.containsKey(reader.peek(1)) && reader.peekAt(1) == 'm') {
                return true;
            }
            if (reader.canRead(3) && ANSI_TO_FORMATTING.containsKey(reader.peek(2)) && reader.peekAt(2) == 'm') {
                return true;
            }
            if (this.trueColor && reader.canRead(TRUE_COLOR_PREFIX.length()) && reader.peek(TRUE_COLOR_PREFIX.length()).equals(TRUE_COLOR_PREFIX)) {
                return reader.readUntil('m').length() <= 11 && reader.canRead() && reader.peek() == 'm';
            }
        }
        return false;
    }

    @Nullable
    @Override
    public TextFormatting read(TextStringReader reader) {
        reader.skip(PREFIX.length());
        if (this.trueColor && reader.canRead(TRUE_COLOR_PREFIX.length()) && reader.peek(TRUE_COLOR_PREFIX.length()).equals(TRUE_COLOR_PREFIX)) {
            reader.skip(TRUE_COLOR_PREFIX.length());
            String rgb = reader.read(reader.peekAt(1) == ';' ? 1 : 2);
            reader.skip();
            String[] split = rgb.split(";");
            if (split.length != 3) return null;
            try {
                int red = Integer.parseInt(split[0]);
                int green = Integer.parseInt(split[1]);
                int blue = Integer.parseInt(split[2]);
                return new TextFormatting(red << 16 | green << 8 | blue);
            } catch (Throwable t) {
                return null;
            }
        } else {
            String code = reader.read(reader.peekAt(1) == 'm' ? 1 : 2);
            reader.skip();
            return ANSI_TO_FORMATTING.get(code);
        }
    }

    @Override
    public boolean canWrite(TextFormatting formatting) {
        return this.trueColor || FORMATTING_TO_ANSI.containsKey(formatting);
    }

    @Override
    public void write(StringBuilder builder, TextFormatting formatting) {
        if (formatting.isRGBColor()) {
            int red = formatting.getRgbValue() >> 16 & 0xFF;
            int green = formatting.getRgbValue() >> 8 & 0xFF;
            int blue = formatting.getRgbValue() & 0xFF;
            builder.append(PREFIX).append(TRUE_COLOR_PREFIX).append(red).append(';').append(green).append(';').append(blue).append(SUFFIX);
        } else {
            builder.append(PREFIX).append(FORMATTING_TO_ANSI.get(formatting)).append(SUFFIX);
        }
    }

    @Override
    public boolean shouldResetAtEnd() {
        return true;
    }

}
