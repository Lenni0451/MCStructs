package net.lenni0451.mcstructs.itemcomponents.serialization;

@FunctionalInterface
public interface Verifier<T> {

    boolean verify(final T t);

    default void check(final String type, final T t) {
        if (!this.verify(t)) throw new IllegalArgumentException("Invalid " + type + " value: " + t);
    }

}
