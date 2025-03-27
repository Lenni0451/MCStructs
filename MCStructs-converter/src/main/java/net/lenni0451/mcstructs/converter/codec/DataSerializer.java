package net.lenni0451.mcstructs.converter.codec;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.model.Result;

@FunctionalInterface
public interface DataSerializer<T> {

    <S> Result<S> serialize(final DataConverter<S> converter, final T element);

}
