package net.lenni0451.mcstructs.nbt.exceptions;

/**
 * This exception is thrown when a tag type is unknown
 */
public class UnknownTagTypeException extends IllegalStateException {

    public UnknownTagTypeException(final int typeId) {
        super("Unknown tag type id " + typeId);
    }

    public UnknownTagTypeException(final Class<?> typeClass) {
        super("Unknown tag type class " + typeClass.getName());
    }

}
