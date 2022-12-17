package net.lenni0451.mcstructs.snbt.impl.v1_14;

import net.lenni0451.mcstructs.snbt.impl.v1_12.StringReader_v1_12;

public class StringReader_v1_14 extends StringReader_v1_12 {

    public StringReader_v1_14(final String s) {
        super(s);
    }

    @Override
    protected boolean isQuote(char c) {
        return c == '"' || c == '\'';
    }

}
