package net.lenni0451.mcstructs.converter.codec;

@FunctionalInterface
public interface ThrowingFunction<T, R> {

    R apply(T t) throws Throwable;

}
