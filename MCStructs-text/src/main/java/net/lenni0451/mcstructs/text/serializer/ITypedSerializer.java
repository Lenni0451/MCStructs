package net.lenni0451.mcstructs.text.serializer;

public interface ITypedSerializer<T, O> {

    T serialize(final O object);

    O deserialize(final T object);

}
