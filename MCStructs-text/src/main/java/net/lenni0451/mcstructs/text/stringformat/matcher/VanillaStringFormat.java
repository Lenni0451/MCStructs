package net.lenni0451.mcstructs.text.stringformat.matcher;

import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.stringformat.StringFormat;
import net.lenni0451.mcstructs.text.stringformat.TextStringReader;

import javax.annotation.Nullable;

/**
 * A {@link StringFormat} implementation for the vanilla format.<br>
 * Example: {@code §a}
 */
public class VanillaStringFormat extends StringFormat {

    private final boolean downsampleRgbColors;

    public VanillaStringFormat(final char colorChar, final boolean downsampleRgbColors) {
        super(colorChar);
        this.downsampleRgbColors = downsampleRgbColors;
    }

    @Override
    public boolean matches(TextStringReader reader) {
        return reader.canRead(2) && reader.read() == this.colorChar && this.getByCode(reader.peek()) != null;
    }

    @Override
    @Nullable
    public TextFormatting read(TextStringReader reader) {
        reader.skip();
        return this.getByCode(reader.read());
    }

    @Override
    public boolean canWrite(TextFormatting formatting) {
        if (this.downsampleRgbColors) return true;
        return !formatting.isRGBColor();
    }

    @Override
    public void write(StringBuilder builder, TextFormatting formatting) {
        if (this.downsampleRgbColors && formatting.isRGBColor()) formatting = TextFormatting.getClosestFormattingColor(formatting.getRgbValue());
        builder.append(this.colorChar).append(formatting.getCode());
    }

    @Nullable
    protected TextFormatting getByCode(final char c) {
        return TextFormatting.getByCode(c);
    }

}
