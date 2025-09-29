package net.lenni0451.mcstructs.converter.mapcodec;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.ThrowingFunction;
import net.lenni0451.mcstructs.converter.mapcodec.impl.FieldMapCodec;
import net.lenni0451.mcstructs.converter.mapcodec.impl.RecursiveMapCodec;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.converter.model.Result;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface MapCodec<T> extends MapSerializer<T>, MapDeserializer<T> {

    MapCodec<Boolean> UNIT = unit(() -> true);

    static <N> MapCodec<N> unit(final Supplier<N> supplier) {
        return new MapCodec<N>() {
            @Override
            public <S> Result<Map<S, S>> serialize(DataConverter<S> converter, Map<S, S> map, N element) {
                return Result.success(map);
            }

            @Override
            public <S> Result<N> deserialize(DataConverter<S> converter, Map<S, S> map) {
                return Result.success(supplier.get());
            }
        };
    }

    static <N> MapCodec<N> failing(final String error) {
        return new MapCodec<N>() {
            @Override
            public <S> Result<Map<S, S>> serialize(DataConverter<S> converter, Map<S, S> map, N element) {
                return Result.error(error);
            }

            @Override
            public <S> Result<N> deserialize(DataConverter<S> converter, Map<S, S> map) {
                return Result.error(error);
            }
        };
    }

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

    static <T> MapCodec<T> lazyInit(final Supplier<MapCodec<T>> supplier) {
        return new MapCodec<T>() {
            private MapCodec<T> codec;

            @Override
            public <S> Result<Map<S, S>> serialize(DataConverter<S> converter, Map<S, S> map, T element) {
                if (this.codec == null) this.codec = supplier.get();
                return this.codec.serialize(converter, map, element);
            }

            @Override
            public <S> Result<T> deserialize(DataConverter<S> converter, Map<S, S> map) {
                if (this.codec == null) this.codec = supplier.get();
                return this.codec.deserialize(converter, map);
            }
        };
    }

    static <L, R> MapCodec<Either<L, R>> either(final MapCodec<L> leftCodec, final MapCodec<R> rightCodec) {
        return new MapCodec<Either<L, R>>() {
            @Override
            public <S> Result<Map<S, S>> serialize(DataConverter<S> converter, Map<S, S> map, Either<L, R> element) {
                return element.xmap(l -> leftCodec.serialize(converter, map, l), r -> rightCodec.serialize(converter, map, r));
            }

            @Override
            public <S> Result<Either<L, R>> deserialize(DataConverter<S> converter, Map<S, S> map) {
                Result<L> left = leftCodec.deserialize(converter, map);
                if (left.isSuccessful()) return left.map(Either::left);
                Result<R> right = rightCodec.deserialize(converter, map);
                if (right.isSuccessful()) return right.map(Either::right);
                return Result.mergeErrors("Failed to deserialize as either left or right", Arrays.asList(left, right));
            }
        };
    }


    default FieldMapCodec.Builder.Stage1<T> field(final String fieldName) {
        return this.asCodec().mapCodec(fieldName);
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

    default <N> MapCodec<N> mapThrowing(final ThrowingFunction<N, T> serializer, final ThrowingFunction<T, N> deserializer) {
        return new MapCodec<N>() {
            @Override
            public <S> Result<Map<S, S>> serialize(DataConverter<S> converter, Map<S, S> map, N element) {
                try {
                    return MapCodec.this.serialize(converter, map, serializer.apply(element));
                } catch (Throwable t) {
                    return Result.error(t);
                }
            }

            @Override
            public <S> Result<N> deserialize(DataConverter<S> converter, Map<S, S> map) {
                return MapCodec.this.deserialize(converter, map).mapResult(t -> {
                    try {
                        return Result.success(deserializer.apply(t));
                    } catch (Throwable t2) {
                        return Result.error(t2);
                    }
                });
            }
        };
    }

    default MapCodec<T> defaulted(final T defaultValue) {
        return this.map(
                value -> Objects.equals(value, defaultValue) ? null : value,
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
