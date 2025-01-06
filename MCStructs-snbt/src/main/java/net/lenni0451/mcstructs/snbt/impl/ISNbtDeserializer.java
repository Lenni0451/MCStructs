package net.lenni0451.mcstructs.snbt.impl;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtDeserializeException;

/**
 * The base SNbt deserializer interface.
 *
 * @param <T> The output type of the deserializer
 */
public interface ISNbtDeserializer<T extends NbtTag> {

    T deserialize(final String s) throws SNbtDeserializeException;

    NbtTag deserializeValue(final String s) throws SNbtDeserializeException;

}
