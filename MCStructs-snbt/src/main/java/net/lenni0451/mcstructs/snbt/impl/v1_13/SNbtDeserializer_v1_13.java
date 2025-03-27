package net.lenni0451.mcstructs.snbt.impl.v1_13;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtDeserializeException;
import net.lenni0451.mcstructs.snbt.impl.v1_12.SNbtDeserializer_v1_12;
import net.lenni0451.mcstructs.snbt.impl.v1_12.StringReader_v1_12;

public class SNbtDeserializer_v1_13 extends SNbtDeserializer_v1_12 {

    @Override
    protected NbtTag readListOrArray(final StringReader_v1_12 reader) throws SNbtDeserializeException {
        if (reader.canRead(3) && !this.isQuote(reader.charAt(1)) && reader.charAt(2) == ';') return this.readArray(reader);
        else return this.readList(reader);
    }

}
