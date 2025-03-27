package net.lenni0451.mcstructs.snbt.impl;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtSerializeException;

/**
 * The base SNbt serializer interface.
 */
public interface SNbtSerializer {

    String serialize(final NbtTag tag) throws SNbtSerializeException;

}
