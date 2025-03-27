package net.lenni0451.mcstructs.text.stringformat.matcher;

import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.stringformat.StringFormat;
import net.lenni0451.mcstructs.text.stringformat.TextStringReader;

import javax.annotation.Nullable;

/**
 * A {@link StringFormat} implementation that delegates to multiple other {@link StringFormat} implementations.<br>
 * The first {@link StringFormat} that matches will be used.
 */
public class PrioritizingStringFormat extends StringFormat {

    private final StringFormat[] matchers;

    public PrioritizingStringFormat(final StringFormat... matchers) {
        super('\0');
        this.matchers = matchers;
    }

    @Override
    public boolean canRead(TextStringReader reader) {
        for (StringFormat matcher : this.matchers) {
            if (matcher.canRead(reader)) return true;
        }
        return false;
    }

    @Override
    public boolean matches(TextStringReader reader) {
        reader.mark();
        for (StringFormat matcher : this.matchers) {
            if (matcher.canRead(reader) && matcher.matches(reader)) return true;
            reader.reset();
        }
        return false;
    }

    @Nullable
    @Override
    public TextFormatting read(TextStringReader reader) {
        reader.mark();
        for (StringFormat matcher : this.matchers) {
            if (matcher.canRead(reader) && matcher.matches(reader)) {
                reader.reset();
                return matcher.read(reader);
            }
            reader.reset();
        }
        return null;
    }

    @Override
    public boolean canWrite(TextFormatting formatting) {
        for (StringFormat matcher : this.matchers) {
            if (matcher.canWrite(formatting)) return true;
        }
        return false;
    }

    @Override
    public void write(StringBuilder builder, TextFormatting formatting) {
        for (StringFormat matcher : this.matchers) {
            if (matcher.canWrite(formatting)) {
                matcher.write(builder, formatting);
                return;
            }
        }
    }

}
