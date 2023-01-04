package net.lenni0451.mcstructs.snbt.exceptions;

import net.lenni0451.mcstructs.nbt.NbtType;

/**
 * This exception is thrown when the SNbt serializer encounters an invalid Nbt tag.
 */
public class SNbtSerializeException extends Exception {

    public SNbtSerializeException(final NbtType type) {
        super("Unable to serialize nbt type " + type.name());
    }

}
