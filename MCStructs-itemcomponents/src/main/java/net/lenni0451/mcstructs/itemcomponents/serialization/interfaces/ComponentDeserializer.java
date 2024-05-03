package net.lenni0451.mcstructs.itemcomponents.serialization.interfaces;

import net.lenni0451.mcstructs.converter.DataConverter;

@FunctionalInterface
public interface ComponentDeserializer<I> {

    <T> I deserialize(final DataConverter<T> converter, final T data);

}
