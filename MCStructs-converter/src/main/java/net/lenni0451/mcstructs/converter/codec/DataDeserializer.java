package net.lenni0451.mcstructs.converter.codec;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.Result;

public interface DataDeserializer<T> {

    <S> Result<T> deserialize(final DataConverter<S> converter, final S data);

}
