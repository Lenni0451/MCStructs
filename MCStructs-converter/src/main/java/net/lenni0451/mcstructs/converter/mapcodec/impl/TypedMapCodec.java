package net.lenni0451.mcstructs.converter.mapcodec.impl;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.converter.model.Result;

import java.util.Map;
import java.util.function.Function;

public class TypedMapCodec<K, V> implements MapCodec<V> {

    private final String typeKey;
    private final Codec<K> keyCodec;
    private final Function<V, K> typeMapper;
    private final Function<K, MapCodec<? extends V>> continuation;

    public TypedMapCodec(final String typeKey, final Codec<K> keyCodec, final Function<V, K> typeMapper, final Function<K, MapCodec<? extends V>> continuation) {
        this.typeKey = typeKey;
        this.keyCodec = keyCodec;
        this.typeMapper = typeMapper;
        this.continuation = continuation;
    }

    @Override
    public <S> Result<Map<S, S>> serialize(DataConverter<S> converter, Map<S, S> map, V element) {
        K key = this.typeMapper.apply(element);
        if (key == null) return Result.error("No key found for element: " + element);
        MapCodec<V> codec = (MapCodec<V>) this.continuation.apply(key);
        if (codec == null) return Result.error("No codec found for key '" + key + "'");
        Result<Map<S, S>> result = codec.serialize(converter, map, element);
        if (result.isError()) return result.mapError();
        map = result.get();
        Result<S> serializedKey = this.keyCodec.serialize(converter, key);
        if (serializedKey.isError()) return serializedKey.mapError();
        map.put(converter.createString(this.typeKey), serializedKey.get());
        return Result.success(map);
    }

    @Override
    public <S> Result<V> deserialize(DataConverter<S> converter, Map<S, S> map) {
        S rawKey = map.get(converter.createString(this.typeKey));
        if (rawKey == null) return Result.error("Input does not contain key '" + this.typeKey + "': " + map);
        Result<K> key = this.keyCodec.deserialize(converter, rawKey);
        if (key.isError()) return key.mapError();
        MapCodec<? extends V> codec = this.continuation.apply(key.get());
        if (codec == null) return Result.error("No codec found for key '" + key.get() + "'");
        return (Result<V>) codec.deserialize(converter, map);
    }

}
