package net.lenni0451.mcstructs.converter.codec.map;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.impl.FieldMapCodec;
import net.lenni0451.mcstructs.converter.codec.map.impl.RecursiveMapCodec;
import net.lenni0451.mcstructs.converter.model.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface MapCodec<T> extends MapSerializer<T>, MapDeserializer<T> {

    static <N> MapCodec<N> requiredKey(final Codec<N> codec, final String fieldName) {
        return new FieldMapCodec<>(codec, fieldName, false, false);
    }

    static <N> MapCodec<N> optionalKey(final Codec<N> codec, final String fieldName) {
        return new FieldMapCodec<>(codec, fieldName, true, false);
    }

    static <N> MapCodec<N> optionalLenientKey(final Codec<N> codec, final String fieldName) {
        return new FieldMapCodec<>(codec, fieldName, true, true);
    }

    static <T> MapCodec<T> recursive(final Function<Codec<T>, MapCodec<T>> creator) {
        return new RecursiveMapCodec<>(creator);
    }


    default <N> MapCodec<N> map(final Function<N, T> serializer, final Function<T, N> deserializer) {
        return new MapCodec<N>() {
            @Override
            public <S> Result<Map<S, S>> serialize(DataConverter<S> converter, Map<S, S> map, N element) {
                return MapCodec.this.serialize(converter, map, serializer.apply(element));
            }

            @Override
            public <S> Result<N> deserialize(DataConverter<S> converter, Map<S, S> map) {
                return MapCodec.this.deserialize(converter, map).map(deserializer);
            }
        };
    }

    default MapCodec<T> defaulted(final T defaultValue) {
        return this.map(
                value -> Objects.equals(value, defaultValue) ? defaultValue : value,
                value -> value == null ? defaultValue : value
        );
    }

    default MapCodec<T> defaulted(final Predicate<T> isDefault, final Supplier<T> defaultValue) {
        return this.map(
                value -> isDefault.test(value) ? null : value,
                value -> value == null ? defaultValue.get() : value
        );
    }

    default MapCodec<T> elseGet(final Supplier<T> defaultSupplier) {
        return this.map(
                value -> value,
                value -> value == null ? defaultSupplier.get() : value
        );
    }

    default Codec<T> asCodec() {
        return new Codec<T>() {
            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, T element) {
                Map<S, S> map = new HashMap<>();
                Result<Map<S, S>> result = MapCodec.this.serialize(converter, map, element);
                return result.mapResult(converter::createMergedMap);
            }

            @Override
            public <S> Result<T> deserialize(DataConverter<S> converter, S data) {
                Result<Map<S, S>> map = converter.asMap(data);
                if (map.isError()) return map.mapError();
                return MapCodec.this.deserialize(converter, map.get());
            }
        };
    }

}
