package net.lenni0451.mcstructs.snbt.impl.v1_21_5;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.ListTag;
import net.lenni0451.mcstructs.nbt.utils.NbtCodecUtils;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtDeserializeException;
import net.lenni0451.mcstructs.snbt.impl.v1_12.StringReader_v1_12;
import net.lenni0451.mcstructs.snbt.impl.v1_14.SNbtDeserializer_v1_14;

import java.util.ArrayList;
import java.util.List;

public class SNbtDeserializer_v1_21_5 extends SNbtDeserializer_v1_14 {

    @Override
    protected ListTag<NbtTag> readList(StringReader_v1_12 reader) throws SNbtDeserializeException {
        reader.jumpTo('[');
        reader.skipWhitespaces();
        if (!reader.canRead()) throw this.makeException(reader, "Expected value");
        List<NbtTag> list = new ArrayList<>();
        while (reader.peek() != ']') {
            list.add(this.readValue(reader));
            if (!this.hasNextValue(reader)) break;
            if (!reader.canRead()) throw this.makeException(reader, "Expected value");
        }
        reader.jumpTo(']');
        return NbtCodecUtils.wrapIfRequired(list);
    }

}
