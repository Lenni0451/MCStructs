package net.lenni0451.mcstructs.converter;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Result;

import javax.annotation.Nullable;
import java.util.*;

public interface DataConverter<T> {

    <N> N convertTo(final DataConverter<N> converter, @Nullable final T element);

    default <N> T convertFrom(final DataConverter<N> converter, @Nullable final N element) {
        return converter.convertTo(this, element);
    }

    default <N> N convertList(final DataConverter<N> to, final T list) {
        List<T> in = this.asList(list).orElse(new ArrayList<>());
        List<N> out = new ArrayList<>();
        for (T element : in) out.add(this.convertTo(to, element));
        return to.createList(out);
    }

    default <N> N convertMap(final DataConverter<N> to, final T map) {
        Map<T, T> in = this.asMap(map).orElse(new HashMap<>());
        Map<N, N> out = new HashMap<>();
        for (Map.Entry<T, T> entry : in.entrySet()) out.put(this.convertTo(to, entry.getKey()), this.convertTo(to, entry.getValue()));
        return to.createUnsafeMap(out);
    }

    T createBoolean(final boolean value);

    Result<Boolean> asBoolean(final T element);

    T createNumber(final Number number);

    Result<Number> asNumber(final T element);

    default T createByte(final byte value) {
        return this.createNumber(value);
    }

    default T createShort(final short value) {
        return this.createNumber(value);
    }

    default T createInt(final int value) {
        return this.createNumber(value);
    }

    default T createLong(final long value) {
        return this.createNumber(value);
    }

    default T createFloat(final float value) {
        return this.createNumber(value);
    }

    default T createDouble(final double value) {
        return this.createNumber(value);
    }

    T createString(final String value);

    Result<String> asString(final T element);

    default T createList(final List<T> values) {
        return this.mergeList(null, values).get();
    }

    default T emptyList() {
        return this.createList(Collections.emptyList());
    }

    Result<T> mergeList(@Nullable final T list, final List<T> values);

    default Result<T> mergeList(@Nullable final T list, final T... values) {
        return this.mergeList(list, Arrays.asList(values));
    }

    Result<List<T>> asList(final T element);

    T createUnsafeMap(final Map<T, T> values);

    default T emptyMap() {
        return this.createUnsafeMap(Collections.emptyMap());
    }

    default Result<T> createMergedMap(final Map<T, T> values) {
        T map = this.emptyMap();
        for (Map.Entry<T, T> entry : values.entrySet()) {
            Result<T> mergedMap = this.mergeMap(map, entry.getKey(), entry.getValue());
            if (mergedMap.isError()) return mergedMap;
            map = mergedMap.get();
        }
        return Result.success(map);
    }

    Result<T> mergeMap(@Nullable final T map, final Map<T, T> values);

    default Result<T> mergeMap(@Nullable final T map, final T key, final T value) {
        return this.mergeMap(map, Collections.singletonMap(key, value));
    }

    default Result<T> mergeMap(@Nullable final T map, final T... keyValue) {
        if (keyValue.length % 2 != 0) return Result.error("Key-Value pairs must be even");
        Map<T, T> mapValues = new HashMap<>();
        for (int i = 0; i < keyValue.length; i += 2) mapValues.put(keyValue[i], keyValue[i + 1]);
        return this.mergeMap(map, mapValues);
    }

    Result<Map<T, T>> asMap(final T element);

    Result<Map<String, T>> asStringTypeMap(final T element);

    boolean put(final T map, final String key, final T value);

    T createByteArray(final byte[] value);

    Result<byte[]> asByteArray(final T element);

    T createIntArray(final int[] value);

    Result<int[]> asIntArray(final T element);

    T createLongArray(final long[] value);

    Result<long[]> asLongArray(final T element);

    default Codec<T> toCodec() {
        return new Codec<T>() {
            @Override
            public <S> Result<T> deserialize(DataConverter<S> converter, S data) {
                try {
                    return Result.success(converter.convertTo(DataConverter.this, data));
                } catch (Throwable t) {
                    return Result.error(t);
                }
            }

            @Override
            public <S> Result<S> serialize(DataConverter<S> converter, T element) {
                try {
                    return Result.success(converter.convertFrom(DataConverter.this, element));
                } catch (Throwable t) {
                    return Result.error(t);
                }
            }
        };
    }

}
