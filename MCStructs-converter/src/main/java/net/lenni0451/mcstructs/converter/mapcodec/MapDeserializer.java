package net.lenni0451.mcstructs.converter.mapcodec;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.model.Result;

import java.util.Map;

@FunctionalInterface
public interface MapDeserializer<T> {

    <S> Result<T> deserialize(final DataConverter<S> converter, final Map<S, S> map);

}
