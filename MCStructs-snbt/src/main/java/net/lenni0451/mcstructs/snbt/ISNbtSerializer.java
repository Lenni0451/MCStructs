package net.lenni0451.mcstructs.snbt;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtSerializeException;

public interface ISNbtSerializer {

    String serialize(final INbtTag tag) throws SNbtSerializeException;

}
