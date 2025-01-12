package net.lenni0451.mcstructs.text.serializer.legacy;

import net.lenni0451.mcstructs.snbt.SNbt;

import java.util.function.Predicate;

public class EventSerializer<R, T extends R, A extends Enum<A>, IO> {

    private final Predicate<R> classMatcher;
    private final IOFunction<T, IO> serializer;
    private final A action;
    private final IOFunction<IO, T> deserializer;

    protected EventSerializer(final Predicate<R> classMatcher, final IOFunction<T, IO> serializer, final A action, final IOFunction<IO, T> deserializer) {
        this.classMatcher = classMatcher;
        this.serializer = serializer;
        this.action = action;
        this.deserializer = deserializer;
    }

    public A getAction() {
        return this.action;
    }

    public boolean matches(final R rawType) {
        return this.classMatcher.test(rawType);
    }

    public IO serialize(final SNbt<?> sNbt, final T value) throws Throwable {
        return this.serializer.apply(sNbt, value);
    }

    public boolean matches(final A action) {
        return this.action == action;
    }

    public T deserialize(final SNbt<?> sNbt, final IO value) throws Throwable {
        return this.deserializer.apply(sNbt, value);
    }


    @FunctionalInterface
    protected interface IOFunction<I, O> {
        O apply(final SNbt<?> sNbt, final I value) throws Throwable;
    }

    @FunctionalInterface
    protected interface BasicIOFunction<I, O> extends IOFunction<I, O> {
        O apply(final I value) throws Throwable;

        default O apply(final SNbt<?> sNbt, final I value) throws Throwable {
            return this.apply(value);
        }
    }

}
