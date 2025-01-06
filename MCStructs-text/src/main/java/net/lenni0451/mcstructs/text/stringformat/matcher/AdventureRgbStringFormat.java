package net.lenni0451.mcstructs.text.stringformat.matcher;

import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.stringformat.StringFormat;
import net.lenni0451.mcstructs.text.stringformat.TextStringReader;

import javax.annotation.Nullable;

/**
 * A {@link StringFormat} implementation for the Adventure RGB format.<br>
 * Example: {@code §#FF0000}
 */
public class AdventureRgbStringFormat extends StringFormat {

    public AdventureRgbStringFormat(final char colorChar) {
        super(colorChar);
    }

    @Override
    public boolean matches(TextStringReader reader) {
        return reader.canRead(8) && reader.read() == this.colorChar && reader.peek() == '#';
    }

    @Nullable
    @Override
    public TextFormatting read(TextStringReader reader) {
        try {
            return new TextFormatting(Integer.parseInt(reader.skip(2).read(6), 16));
        } catch (Throwable t) {
            return null;
        }
    }

    @Override
    public boolean canWrite(TextFormatting formatting) {
        return formatting.isRGBColor();
    }

    @Override
    public void write(StringBuilder builder, TextFormatting formatting) {
        builder.append(this.colorChar).append("#").append(String.format("%06X", formatting.getRgbValue()));
    }

}
