package net.lenni0451.mcstructs.converter.impl.v1_20_3;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.model.Result;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaConverter_v1_20_3 implements DataConverter<Object> {

    public static final JavaConverter_v1_20_3 INSTANCE = new JavaConverter_v1_20_3();

    @Override
    public <N> N convertTo(DataConverter<N> converter, @Nullable Object element) {
        if (element == null) return null;
        else if (element instanceof Boolean) return converter.createBoolean((Boolean) element);
        else if (element instanceof Byte) return converter.createByte((Byte) element);
        else if (element instanceof Short) return converter.createShort((Short) element);
        else if (element instanceof Integer) return converter.createInt((Integer) element);
        else if (element instanceof Long) return converter.createLong((Long) element);
        else if (element instanceof Float) return converter.createFloat((Float) element);
        else if (element instanceof Double) return converter.createDouble((Double) element);
        else if (element instanceof Number) return converter.createNumber((Number) element);
        else if (element instanceof String) return converter.createString((String) element);
        else if (element instanceof byte[]) return converter.createByteArray((byte[]) element);
        else if (element instanceof int[]) return converter.createIntArray((int[]) element);
        else if (element instanceof long[]) return converter.createLongArray((long[]) element);
        else if (element instanceof List) return this.convertList(converter, element);
        else if (element instanceof Map) return this.convertMap(converter, element);
        throw new IllegalArgumentException("Unknown element type: " + element.getClass().getName());
    }

    @Override
    public Object createBoolean(boolean value) {
        return value;
    }

    @Override
    public Result<Boolean> asBoolean(Object element) {
        if (element instanceof Boolean) return Result.success((Boolean) element);
        return Result.unexpected(element, Boolean.class);
    }

    @Override
    public Object createNumber(Number number) {
        return number;
    }

    @Override
    public Result<Number> asNumber(Object element) {
        if (element instanceof Number) return Result.success((Number) element);
        return Result.unexpected(element, Number.class);
    }

    @Override
    public Object createString(String value) {
        return value;
    }

    @Override
    public Result<String> asString(Object element) {
        if (element instanceof String) return Result.success((String) element);
        return Result.unexpected(element, String.class);
    }

    @Override
    public Result<Object> mergeList(@Nullable Object list, List<Object> values) {
        if (list == null) return Result.success(values);
        if (!(list instanceof List)) return Result.unexpected(list, List.class);
        List<?> input = (List<?>) list;
        if (input.isEmpty()) return Result.success(values);
        List<Object> result = new ArrayList<>();
        result.addAll(input);
        result.addAll(values);
        return Result.success(result);
    }

    @Override
    public Result<List<Object>> asList(Object element) {
        if (element instanceof List) return Result.success((List<Object>) element);
        return Result.unexpected(element, List.class);
    }

    @Override
    public Object createUnsafeMap(Map<Object, Object> values) {
        return values;
    }

    @Override
    public Result<Object> mergeMap(@Nullable Object map, Map<Object, Object> values) {
        if (map == null) return Result.success(values);
        if (!(map instanceof Map)) return Result.unexpected(map, Map.class);
        Map<Object, Object> input = (Map<Object, Object>) map;
        if (input.isEmpty()) return Result.success(values);
        Map<Object, Object> result = new HashMap<>();
        result.putAll(input);
        result.putAll(values);
        return Result.success(result);
    }

    @Override
    public Result<Map<Object, Object>> asMap(Object element) {
        if (element instanceof Map) return Result.success((Map<Object, Object>) element);
        return Result.unexpected(element, Map.class);
    }

    @Override
    public Result<Map<String, Object>> asStringTypeMap(Object element) {
        if (!(element instanceof Map)) return Result.unexpected(element, Map.class);
        Map<?, ?> input = (Map<?, ?>) element;
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            if (!(entry.getKey() instanceof String)) return Result.unexpected(entry.getKey(), String.class);
            result.put((String) entry.getKey(), entry.getValue());
        }
        return Result.success(result);
    }

    @Override
    public boolean put(Object map, String key, Object value) {
        if (!(map instanceof Map)) return false;
        ((Map<Object, Object>) map).put(key, value);
        return true;
    }

    @Override
    public Object createByteArray(byte[] value) {
        return value;
    }

    @Override
    public Result<byte[]> asByteArray(Object element) {
        if (element instanceof byte[]) return Result.success((byte[]) element);
        return Result.unexpected(element, byte[].class);
    }

    @Override
    public Object createIntArray(int[] value) {
        return value;
    }

    @Override
    public Result<int[]> asIntArray(Object element) {
        if (element instanceof int[]) return Result.success((int[]) element);
        return Result.unexpected(element, int[].class);
    }

    @Override
    public Object createLongArray(long[] value) {
        return value;
    }

    @Override
    public Result<long[]> asLongArray(Object element) {
        if (element instanceof long[]) return Result.success((long[]) element);
        return Result.unexpected(element, long[].class);
    }

}
