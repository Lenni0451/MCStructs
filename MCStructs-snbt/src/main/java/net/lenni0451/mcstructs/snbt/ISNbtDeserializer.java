package net.lenni0451.mcstructs.snbt;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtDeserializeException;

public interface ISNbtDeserializer<T extends INbtTag> {

    T deserialize(final String s) throws SNbtDeserializeException;

}
