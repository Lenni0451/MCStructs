package net.lenni0451.mcstructs.nbt.snbt.impl.v1_13;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.snbt.SNbtParseException;
import net.lenni0451.mcstructs.nbt.snbt.impl.v1_12.SNbtParser_v1_12;
import net.lenni0451.mcstructs.nbt.snbt.impl.v1_12.StringReader_v1_14;

public class SNbtParser_v1_13 extends SNbtParser_v1_12 {

    @Override
    protected INbtTag readListOrArray(final StringReader_v1_14 reader) throws SNbtParseException {
        if (reader.canRead(3) && !this.isQuote(reader.charAt(1)) && reader.charAt(2) == ';') return this.readArray(reader);
        else return this.readList(reader);
    }

}
