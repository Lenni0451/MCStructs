package net.lenni0451.mcstructs.converter.mapcodec.impl;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.converter.model.Result;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FuzzyMapCodec<T> implements MapCodec<T> {

    private final List<MapCodec<? extends T>> codecs;
    private final Function<T, MapCodec<? extends T>> selector;

    public FuzzyMapCodec(final List<MapCodec<? extends T>> codecs, final Function<T, MapCodec<? extends T>> selector) {
        this.codecs = codecs;
        this.selector = selector;
    }

    @Override
    public <S> Result<Map<S, S>> serialize(DataConverter<S> converter, Map<S, S> map, T element) {
        MapCodec<T> codec = (MapCodec<T>) this.selector.apply(element);
        return codec.serialize(converter, map, element);
    }

    @Override
    public <S> Result<T> deserialize(DataConverter<S> converter, Map<S, S> map) {
        for (MapCodec<? extends T> codec : this.codecs) {
            Result<? extends T> result = codec.deserialize(converter, map);
            if (result.isSuccessful()) {
                return Result.success(result.get());
            }
        }
        return Result.error("No codec could deserialize the given map");
    }

}
