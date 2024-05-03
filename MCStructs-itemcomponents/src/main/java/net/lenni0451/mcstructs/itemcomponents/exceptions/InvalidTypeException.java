package net.lenni0451.mcstructs.itemcomponents.exceptions;

import java.util.Arrays;

public class InvalidTypeException extends RuntimeException {

    public static InvalidTypeException of(final Object type, final Class<?>... expected) {
        return new InvalidTypeException(type, Arrays.stream(expected).map(Class::getSimpleName).toArray(String[]::new));
    }

    public static InvalidTypeException of(final Object type, final String... expected) {
        return new InvalidTypeException(type, expected);
    }


    private InvalidTypeException(final Object type, final String... expected) {
        super("Expected " + String.join("/", expected) + " but got " + (type == null ? "null" : type.getClass().getSimpleName()));
    }

    public InvalidTypeException with(final Throwable cause) {
        this.initCause(cause);
        return this;
    }

}
