package net.lenni0451.mcstructs.converter.mapcodec.impl;

import lombok.AllArgsConstructor;
import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.converter.model.Result;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FieldMapCodec<T> implements MapCodec<T> {

    public static <T> Builder.Stage1<T> builder(final Codec<T> codec, final String fieldName) {
        return new Builder.Stage1<>(codec, fieldName);
    }


    private final Codec<T> codec;
    private final String fieldName;
    private final boolean optional;
    private final boolean lenient;

    public FieldMapCodec(final Codec<T> codec, final String fieldName, final boolean optional, final boolean lenient) {
        if (lenient && !optional) throw new IllegalArgumentException("Lenient can only be true if optional is true");

        this.codec = codec;
        this.fieldName = fieldName;
        this.optional = optional;
        this.lenient = lenient;
    }

    @Override
    public <S> Result<Map<S, S>> serialize(DataConverter<S> converter, Map<S, S> map, T element) {
        if (element == null) return Result.success(map);
        Result<S> result = this.codec.serialize(converter, element);
        if (result.isError()) return result.mapError();
        map.put(converter.createString(this.fieldName), result.get());
        return Result.success(map);
    }

    @Override
    public <S> Result<T> deserialize(DataConverter<S> converter, Map<S, S> map) {
        S value = map.get(converter.createString(this.fieldName));
        if (value == null) {
            if (this.optional) return Result.success(null);
            else return Result.error("Key not found in map: " + this.fieldName);
        }
        Result<T> deserialized = this.codec.deserialize(converter, value);
        if (deserialized.isError() && this.lenient) return Result.success(null);
        return deserialized;
    }


    @AllArgsConstructor
    public static abstract class Builder<T> {
        protected final Codec<T> codec;
        protected final String fieldName;

        protected abstract MapCodec<T> build();

        public static class Stage1<T> extends Builder<T> {
            public Stage1(final Codec<T> codec, final String fieldName) {
                super(codec, fieldName);
            }

            public Stage2<T> optional() {
                return new Stage2<>(this.codec, this.fieldName);
            }

            public MapCodec<T> required() {
                return this.build();
            }

            @Override
            protected MapCodec<T> build() {
                return new FieldMapCodec<>(this.codec, this.fieldName, false, false);
            }
        }

        public static abstract class OptionalStage<T> extends Builder<T> {
            public OptionalStage(final Codec<T> codec, final String fieldName) {
                super(codec, fieldName);
            }

            public MapCodec<T> defaulted(final T defaultValue) {
                return this.build().defaulted(defaultValue);
            }

            public MapCodec<T> defaulted(final Predicate<T> isDefault, final Supplier<T> defaultSupplier) {
                return this.build().defaulted(isDefault, defaultSupplier);
            }

            public MapCodec<T> elseGet(final Supplier<T> defaultSupplier) {
                return this.build().elseGet(defaultSupplier);
            }
        }

        public static class Stage2<T> extends OptionalStage<T> {
            public Stage2(final Codec<T> codec, final String fieldName) {
                super(codec, fieldName);
            }

            public Stage3<T> lenient() {
                return new Stage3<>(this.codec, this.fieldName);
            }

            @Override
            protected MapCodec<T> build() {
                return new FieldMapCodec<>(this.codec, this.fieldName, true, false);
            }
        }

        public static class Stage3<T> extends OptionalStage<T> {
            public Stage3(final Codec<T> codec, final String fieldName) {
                super(codec, fieldName);
            }

            @Override
            protected MapCodec<T> build() {
                return new FieldMapCodec<>(this.codec, this.fieldName, true, true);
            }
        }
    }

}
