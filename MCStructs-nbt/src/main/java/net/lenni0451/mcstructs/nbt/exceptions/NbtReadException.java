package net.lenni0451.mcstructs.nbt.exceptions;

import java.io.IOException;

/**
 * This exception is thrown when the Nbt reader encounters an invalid Nbt tag.
 */
public class NbtReadException extends IOException {

    public NbtReadException(final String message) {
        super(message);
    }

}
