package net.lenni0451.mcstructs.nbt.snbt;

import net.lenni0451.mcstructs.nbt.INbtTag;

public interface ISNbtParser<T extends INbtTag> {

    T parse(final String s) throws SNbtParseException;

}
