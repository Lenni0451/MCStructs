package net.lenni0451.mcstructs.converter.impl;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Result;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class DelegatingConverter<T> implements DataConverter<T> {

    private final DataConverter<T> delegate;

    public DelegatingConverter(final DataConverter<T> delegate) {
        this.delegate = delegate;
    }

    public DataConverter<T> getDelegate() {
        return this.delegate;
    }

    @Override
    public <N> N convertTo(DataConverter<N> converter, @Nullable T element) {
        return this.delegate.convertTo(converter, element);
    }

    @Override
    public <N> T convertFrom(DataConverter<N> converter, @Nullable N element) {
        return this.delegate.convertFrom(converter, element);
    }

    @Override
    public <N> N convertList(DataConverter<N> to, T list) {
        return this.delegate.convertList(to, list);
    }

    @Override
    public <N> N convertMap(DataConverter<N> to, T map) {
        return this.delegate.convertMap(to, map);
    }

    @Override
    public T createBoolean(boolean value) {
        return this.delegate.createBoolean(value);
    }

    @Override
    public Result<Boolean> asBoolean(T element) {
        return this.delegate.asBoolean(element);
    }

    @Override
    public T createNumber(Number number) {
        return this.delegate.createNumber(number);
    }

    @Override
    public Result<Number> asNumber(T element) {
        return this.delegate.asNumber(element);
    }

    @Override
    public T createByte(byte value) {
        return this.delegate.createByte(value);
    }

    @Override
    public T createShort(short value) {
        return this.delegate.createShort(value);
    }

    @Override
    public T createInt(int value) {
        return this.delegate.createInt(value);
    }

    @Override
    public T createLong(long value) {
        return this.delegate.createLong(value);
    }

    @Override
    public T createFloat(float value) {
        return this.delegate.createFloat(value);
    }

    @Override
    public T createDouble(double value) {
        return this.delegate.createDouble(value);
    }

    @Override
    public T createString(String value) {
        return this.delegate.createString(value);
    }

    @Override
    public Result<String> asString(T element) {
        return this.delegate.asString(element);
    }

    @Override
    public T createList(List<T> values) {
        return this.delegate.createList(values);
    }

    @Override
    public T emptyList() {
        return this.delegate.emptyList();
    }

    @Override
    public Result<T> mergeList(@Nullable T list, List<T> values) {
        return this.delegate.mergeList(list, values);
    }

    @Override
    @SafeVarargs
    public final Result<T> mergeList(@Nullable T list, T... values) {
        return this.delegate.mergeList(list, values);
    }

    @Override
    public Result<List<T>> asList(T element) {
        return this.delegate.asList(element);
    }

    @Override
    public T createUnsafeMap(Map<T, T> values) {
        return this.delegate.createUnsafeMap(values);
    }

    @Override
    public T emptyMap() {
        return this.delegate.emptyMap();
    }

    @Override
    public Result<T> createMergedMap(Map<T, T> values) {
        return this.delegate.createMergedMap(values);
    }

    @Override
    public Result<T> mergeMap(@Nullable T map, Map<T, T> values) {
        return this.delegate.mergeMap(map, values);
    }

    @Override
    public Result<T> mergeMap(@Nullable T map, T key, T value) {
        return this.delegate.mergeMap(map, key, value);
    }

    @Override
    @SafeVarargs
    public final Result<T> mergeMap(@Nullable T map, T... keyValue) {
        return this.delegate.mergeMap(map, keyValue);
    }

    @Override
    public Result<Map<T, T>> asMap(T element) {
        return this.delegate.asMap(element);
    }

    @Override
    public Result<Map<String, T>> asStringTypeMap(T element) {
        return this.delegate.asStringTypeMap(element);
    }

    @Override
    public boolean put(T map, String key, T value) {
        return this.delegate.put(map, key, value);
    }

    @Override
    public T createByteArray(byte[] value) {
        return this.delegate.createByteArray(value);
    }

    @Override
    public Result<byte[]> asByteArray(T element) {
        return this.delegate.asByteArray(element);
    }

    @Override
    public T createIntArray(int[] value) {
        return this.delegate.createIntArray(value);
    }

    @Override
    public Result<int[]> asIntArray(T element) {
        return this.delegate.asIntArray(element);
    }

    @Override
    public T createLongArray(long[] value) {
        return this.delegate.createLongArray(value);
    }

    @Override
    public Result<long[]> asLongArray(T element) {
        return this.delegate.asLongArray(element);
    }

    @Override
    public Codec<T> toCodec() {
        return this.delegate.toCodec();
    }

}
