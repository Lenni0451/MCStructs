package net.lenni0451.mcstructs.nbt.snbt.impl.v1_14;

import net.lenni0451.mcstructs.nbt.snbt.impl.v1_13.SNbtParser_v1_13;

public class SNbtParser_v1_14 extends SNbtParser_v1_13 {

    @Override
    protected boolean isQuote(char c) {
        return c == '"' || c == '\'';
    }

}
