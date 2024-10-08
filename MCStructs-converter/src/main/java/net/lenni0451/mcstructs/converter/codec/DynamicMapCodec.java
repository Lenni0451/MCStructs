package net.lenni0451.mcstructs.converter.codec;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DynamicMapCodec<K, V> implements Codec<Map<K, V>> {

    private final Codec<K> keyCodec;
    private final Function<K, Codec<V>> valueCodecMapper;

    public DynamicMapCodec(final Codec<K> keyCodec, final Function<K, Codec<V>> valueCodecMapper) {
        this.keyCodec = keyCodec;
        this.valueCodecMapper = valueCodecMapper;
    }

    @Override
    public <S> Result<Map<K, V>> deserialize(DataConverter<S> converter, S data) {
        Result<Map<S, S>> rawMap = converter.asMap(data);
        if (rawMap.isError()) return rawMap.mapError();
        Map<S, S> map = rawMap.get();
        Map<K, V> out = new HashMap<>();
        for (Map.Entry<S, S> entry : map.entrySet()) {
            Result<K> key = this.keyCodec.deserialize(converter, entry.getKey());
            if (key.isError()) return key.mapError();
            Codec<V> valueCodec = this.valueCodecMapper.apply(key.get());
            Result<V> value = valueCodec.deserialize(converter, entry.getValue());
            if (value.isError()) return value.mapError();
            out.put(key.get(), value.get());
        }
        return Result.success(out);
    }

    @Override
    public <S> Result<S> serialize(DataConverter<S> converter, Map<K, V> element) {
        Map<S, S> out = new HashMap<>();
        for (Map.Entry<K, V> entry : element.entrySet()) {
            Result<S> key = this.keyCodec.serialize(converter, entry.getKey());
            if (key.isError()) return key.mapError();
            Codec<V> valueCodec = this.valueCodecMapper.apply(entry.getKey());
            Result<S> value = valueCodec.serialize(converter, entry.getValue());
            if (value.isError()) return value.mapError();
            out.put(key.get(), value.get());
        }
        return converter.createMergedMap(out);
    }

}
