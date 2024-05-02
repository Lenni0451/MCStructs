package net.lenni0451.mcstructs.converter;

import javax.annotation.Nullable;
import java.util.*;

public interface DataConverter<T> {

    <N> N convert(final DataConverter<N> converter, @Nullable final T element);

    default <N> N convertList(final DataConverter<N> converter, final T list) {
        List<T> in = this.asList(list).orElse(new ArrayList<>());
        List<N> out = new ArrayList<>();
        for (T element : in) out.add(this.convert(converter, element));
        return converter.createList(out);
    }

    default <N> N convertMap(final DataConverter<N> converter, final T map) {
        Map<T, T> in = this.asMap(map).orElse(new HashMap<>());
        Map<N, N> out = new HashMap<>();
        for (Map.Entry<T, T> entry : in.entrySet()) out.put(this.convert(converter, entry.getKey()), this.convert(converter, entry.getValue()));
        return converter.createMap(out);
    }

    T createBoolean(final boolean value);

    ConverterResult<Boolean> asBoolean(final T element);

    T createNumber(final Number number);

    ConverterResult<Number> asNumber(final T element);

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

    ConverterResult<String> asString(final T element);

    T createList(final List<T> values);

    default T emptyList() {
        return this.createList(Collections.emptyList());
    }

    ConverterResult<T> mergeList(@Nullable final T list, final List<T> values);

    default ConverterResult<T> mergeList(@Nullable final T list, final T... values) {
        return this.mergeList(list, Arrays.asList(values));
    }

    ConverterResult<List<T>> asList(final T element);

    T createMap(final Map<T, T> values);

    default T emptyMap() {
        return this.createMap(Collections.emptyMap());
    }

    ConverterResult<T> mergeMap(@Nullable final T map, final Map<T, T> values);

    default ConverterResult<T> mergeMap(@Nullable final T map, final T key, final T value) {
        return this.mergeMap(map, Collections.singletonMap(key, value));
    }

    default ConverterResult<T> mergeMap(@Nullable final T map, final T... keyValue) {
        if (keyValue.length % 2 != 0) return ConverterResult.error("Key-Value pairs must be even");
        Map<T, T> mapValues = new HashMap<>();
        for (int i = 0; i < keyValue.length; i += 2) mapValues.put(keyValue[i], keyValue[i + 1]);
        return this.mergeMap(map, mapValues);
    }

    ConverterResult<Map<T, T>> asMap(final T element);

    boolean put(final T map, final String key, final T value);

    T createByteArray(final byte[] value);

    ConverterResult<byte[]> asByteArray(final T element);

    T createIntArray(final int[] value);

    ConverterResult<int[]> asIntArray(final T element);

    T createLongArray(final long[] value);

    ConverterResult<long[]> asLongArray(final T element);

}
