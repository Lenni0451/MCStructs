package net.lenni0451.mcstructs.text.stringformat.matcher;

import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.stringformat.StringFormat;
import net.lenni0451.mcstructs.text.stringformat.TextStringReader;

import javax.annotation.Nullable;

/**
 * A {@link StringFormat} implementation for the vanilla format without actually reading or writing anything.<br>
 * It is used to filter out invalid vanilla formatting codes.
 */
public class VoidVanillaStringFormat extends StringFormat {

    public VoidVanillaStringFormat(final char colorChar) {
        super(colorChar);
    }

    @Override
    public boolean matches(TextStringReader reader) {
        return reader.canRead() && reader.peek() == this.colorChar;
    }

    @Nullable
    @Override
    public TextFormatting read(TextStringReader reader) {
        reader.skip();
        if (reader.canRead()) reader.skip();
        return null;
    }

    @Override
    public boolean canWrite(TextFormatting formatting) {
        return true;
    }

    @Override
    public void write(StringBuilder builder, TextFormatting formatting) {
    }

}
