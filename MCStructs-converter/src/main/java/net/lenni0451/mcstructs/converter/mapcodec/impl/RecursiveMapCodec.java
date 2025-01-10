package net.lenni0451.mcstructs.converter.mapcodec.impl;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.converter.model.Result;

import java.util.Map;
import java.util.function.Function;

public class RecursiveMapCodec<T> implements MapCodec<T> {

    private final MapCodec<T> codec;

    public RecursiveMapCodec(final Function<Codec<T>, MapCodec<T>> creator) {
        this.codec = creator.apply(this.asCodec());
    }

    @Override
    public <S> Result<Map<S, S>> serialize(DataConverter<S> converter, Map<S, S> map, T element) {
        return this.codec.serialize(converter, map, element);
    }

    @Override
    public <S> Result<T> deserialize(DataConverter<S> converter, Map<S, S> map) {
        return this.codec.deserialize(converter, map);
    }

}
