package net.lenni0451.mcstructs.converter.codec;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ConstructorCodec {

    public static <T1, O> Codec<O> of(
            final MapCodec<?, T1> type1, final Function<O, T1> value1,
            final I1<T1, O> generator
    ) {
        return new Codec<O>() {
            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, O element) {
                Map<S, S> map = new HashMap<>();
                Result<S> result = type1.serialize(converter, map, value1.apply(element));
                if (result.isError()) return result.mapError();
                return Result.success(converter.createMap(map));
            }

            @Override
            public <S> Result<O> deserialize(DataConverter<S> converter, S data) {
                Result<Map<S, S>> mapResult = converter.asMap(data);
                if (mapResult.isError()) return mapResult.mapError();

                Map<S, S> map = mapResult.get();
                Result<T1> value1Result = type1.deserialize(converter, map);
                if (value1Result.isError()) return value1Result.mapError();
                return Result.success(generator.generate(value1Result.get()));
            }
        };
    }

    public static <T1, T2, O> Codec<O> of(
            final MapCodec<?, T1> type1, final Function<O, T1> value1,
            final MapCodec<?, T2> type2, final Function<O, T2> value2,
            final I2<T1, T2, O> generator
    ) {
        return new Codec<O>() {
            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, O element) {
                Map<S, S> map = new HashMap<>();
                Result<S> result1 = type1.serialize(converter, map, value1.apply(element));
                if (result1.isError()) return result1.mapError();
                Result<S> result2 = type2.serialize(converter, map, value2.apply(element));
                if (result2.isError()) return result2.mapError();
                return Result.success(converter.createMap(map));
            }

            @Override
            public <S> Result<O> deserialize(DataConverter<S> converter, S data) {
                Result<Map<S, S>> mapResult = converter.asMap(data);
                if (mapResult.isError()) return mapResult.mapError();

                Map<S, S> map = mapResult.get();
                Result<T1> value1Result = type1.deserialize(converter, map);
                if (value1Result.isError()) return value1Result.mapError();
                Result<T2> value2Result = type2.deserialize(converter, map);
                if (value2Result.isError()) return value2Result.mapError();
                return Result.success(generator.generate(value1Result.get(), value2Result.get()));
            }
        };
    }

    public static <T1, T2, T3, O> Codec<O> of(
            final MapCodec<?, T1> type1, final Function<O, T1> value1,
            final MapCodec<?, T2> type2, final Function<O, T2> value2,
            final MapCodec<?, T3> type3, final Function<O, T3> value3,
            final I3<T1, T2, T3, O> generator
    ) {
        return new Codec<O>() {
            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, O element) {
                Map<S, S> map = new HashMap<>();
                Result<S> result1 = type1.serialize(converter, map, value1.apply(element));
                if (result1.isError()) return result1.mapError();
                Result<S> result2 = type2.serialize(converter, map, value2.apply(element));
                if (result2.isError()) return result2.mapError();
                Result<S> result3 = type3.serialize(converter, map, value3.apply(element));
                if (result3.isError()) return result3.mapError();
                return Result.success(converter.createMap(map));
            }

            @Override
            public <S> Result<O> deserialize(DataConverter<S> converter, S data) {
                Result<Map<S, S>> mapResult = converter.asMap(data);
                if (mapResult.isError()) return mapResult.mapError();

                Map<S, S> map = mapResult.get();
                Result<T1> value1Result = type1.deserialize(converter, map);
                if (value1Result.isError()) return value1Result.mapError();
                Result<T2> value2Result = type2.deserialize(converter, map);
                if (value2Result.isError()) return value2Result.mapError();
                Result<T3> value3Result = type3.deserialize(converter, map);
                if (value3Result.isError()) return value3Result.mapError();
                return Result.success(generator.generate(value1Result.get(), value2Result.get(), value3Result.get()));
            }
        };
    }

    public static <T1, T2, T3, T4, O> Codec<O> of(
            final MapCodec<?, T1> type1, final Function<O, T1> value1,
            final MapCodec<?, T2> type2, final Function<O, T2> value2,
            final MapCodec<?, T3> type3, final Function<O, T3> value3,
            final MapCodec<?, T4> type4, final Function<O, T4> value4,
            final I4<T1, T2, T3, T4, O> generator
    ) {
        return new Codec<O>() {
            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, O element) {
                Map<S, S> map = new HashMap<>();
                Result<S> result1 = type1.serialize(converter, map, value1.apply(element));
                if (result1.isError()) return result1.mapError();
                Result<S> result2 = type2.serialize(converter, map, value2.apply(element));
                if (result2.isError()) return result2.mapError();
                Result<S> result3 = type3.serialize(converter, map, value3.apply(element));
                if (result3.isError()) return result3.mapError();
                Result<S> result4 = type4.serialize(converter, map, value4.apply(element));
                if (result4.isError()) return result4.mapError();
                return Result.success(converter.createMap(map));
            }

            @Override
            public <S> Result<O> deserialize(DataConverter<S> converter, S data) {
                Result<Map<S, S>> mapResult = converter.asMap(data);
                if (mapResult.isError()) return mapResult.mapError();

                Map<S, S> map = mapResult.get();
                Result<T1> value1Result = type1.deserialize(converter, map);
                if (value1Result.isError()) return value1Result.mapError();
                Result<T2> value2Result = type2.deserialize(converter, map);
                if (value2Result.isError()) return value2Result.mapError();
                Result<T3> value3Result = type3.deserialize(converter, map);
                if (value3Result.isError()) return value3Result.mapError();
                Result<T4> value4Result = type4.deserialize(converter, map);
                if (value4Result.isError()) return value4Result.mapError();
                return Result.success(generator.generate(value1Result.get(), value2Result.get(), value3Result.get(), value4Result.get()));
            }
        };
    }

    public static <T1, T2, T3, T4, T5, O> Codec<O> of(
            final MapCodec<?, T1> type1, final Function<O, T1> value1,
            final MapCodec<?, T2> type2, final Function<O, T2> value2,
            final MapCodec<?, T3> type3, final Function<O, T3> value3,
            final MapCodec<?, T4> type4, final Function<O, T4> value4,
            final MapCodec<?, T5> type5, final Function<O, T5> value5,
            final I5<T1, T2, T3, T4, T5, O> generator
    ) {
        return new Codec<O>() {
            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, O element) {
                Map<S, S> map = new HashMap<>();
                Result<S> result1 = type1.serialize(converter, map, value1.apply(element));
                if (result1.isError()) return result1.mapError();
                Result<S> result2 = type2.serialize(converter, map, value2.apply(element));
                if (result2.isError()) return result2.mapError();
                Result<S> result3 = type3.serialize(converter, map, value3.apply(element));
                if (result3.isError()) return result3.mapError();
                Result<S> result4 = type4.serialize(converter, map, value4.apply(element));
                if (result4.isError()) return result4.mapError();
                Result<S> result5 = type5.serialize(converter, map, value5.apply(element));
                if (result5.isError()) return result5.mapError();
                return Result.success(converter.createMap(map));
            }

            @Override
            public <S> Result<O> deserialize(DataConverter<S> converter, S data) {
                Result<Map<S, S>> mapResult = converter.asMap(data);
                if (mapResult.isError()) return mapResult.mapError();

                Map<S, S> map = mapResult.get();
                Result<T1> value1Result = type1.deserialize(converter, map);
                if (value1Result.isError()) return value1Result.mapError();
                Result<T2> value2Result = type2.deserialize(converter, map);
                if (value2Result.isError()) return value2Result.mapError();
                Result<T3> value3Result = type3.deserialize(converter, map);
                if (value3Result.isError()) return value3Result.mapError();
                Result<T4> value4Result = type4.deserialize(converter, map);
                if (value4Result.isError()) return value4Result.mapError();
                Result<T5> value5Result = type5.deserialize(converter, map);
                if (value5Result.isError()) return value5Result.mapError();
                return Result.success(generator.generate(value1Result.get(), value2Result.get(), value3Result.get(), value4Result.get(), value5Result.get()));
            }
        };
    }


    @FunctionalInterface
    public interface I1<T1, O> {
        O generate(T1 t1);
    }

    @FunctionalInterface
    public interface I2<T1, T2, O> {
        O generate(T1 t1, T2 t2);
    }

    @FunctionalInterface
    public interface I3<T1, T2, T3, O> {
        O generate(T1 t1, T2 t2, T3 t3);
    }

    @FunctionalInterface
    public interface I4<T1, T2, T3, T4, O> {
        O generate(T1 t1, T2 t2, T3 t3, T4 t4);
    }

    @FunctionalInterface
    public interface I5<T1, T2, T3, T4, T5, O> {
        O generate(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
    }

}
