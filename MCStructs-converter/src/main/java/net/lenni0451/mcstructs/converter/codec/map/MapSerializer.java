package net.lenni0451.mcstructs.converter.codec.map;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.model.Result;

import java.util.Map;

@FunctionalInterface
public interface MapSerializer<T> {

    <S> Result<Map<S, S>> serialize(final DataConverter<S> converter, final Map<S, S> map, final T element);

}
