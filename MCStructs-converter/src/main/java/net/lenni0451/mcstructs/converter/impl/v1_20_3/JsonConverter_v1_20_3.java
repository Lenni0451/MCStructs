package net.lenni0451.mcstructs.converter.impl.v1_20_3;

import com.google.gson.*;
import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.model.Result;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

public class JsonConverter_v1_20_3 implements DataConverter<JsonElement> {

    public static final JsonConverter_v1_20_3 INSTANCE = new JsonConverter_v1_20_3();

    @Override
    public <N> N convertTo(DataConverter<N> to, @Nullable JsonElement element) {
        if (to == this) return (N) element;
        if (element == null || element instanceof JsonNull) return null;
        if (element instanceof JsonObject) return this.convertMap(to, element);
        if (element instanceof JsonArray) return this.convertList(to, element);

        JsonPrimitive primitive = element.getAsJsonPrimitive();
        if (primitive.isString()) return to.createString(primitive.getAsString());
        if (primitive.isBoolean()) return to.createBoolean(primitive.getAsBoolean());

        BigDecimal number = primitive.getAsBigDecimal();
        try {
            long l = number.longValueExact();
            if ((byte) l == l) return to.createByte((byte) l);
            if ((short) l == l) return to.createShort((short) l);
            if ((int) l == l) return to.createInt((int) l);
            return to.createLong(l);
        } catch (ArithmeticException e) {
            double d = number.doubleValue();
            if ((float) d == d) return to.createFloat((float) d);
            return to.createDouble(d);
        }
    }

    @Override
    public JsonElement createBoolean(boolean value) {
        return new JsonPrimitive(value);
    }

    @Override
    public Result<Boolean> asBoolean(JsonElement element) {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isBoolean()) return Result.success(primitive.getAsBoolean());
            else if (primitive.isNumber()) return Result.success(primitive.getAsNumber().byteValue() != 0);
        }
        return Result.unexpected(element, "boolean");
    }

    @Override
    public JsonElement createNumber(Number number) {
        return new JsonPrimitive(number);
    }

    @Override
    public Result<Number> asNumber(JsonElement element) {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isBoolean()) return Result.success(primitive.getAsBoolean() ? 1 : 0);
            else if (primitive.isNumber()) return Result.success(primitive.getAsNumber());
        }
        return Result.unexpected(element, "number");
    }

    @Override
    public JsonElement createString(String value) {
        return new JsonPrimitive(value);
    }

    @Override
    public Result<String> asString(JsonElement element) {
        if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) return Result.unexpected(element, "string");
        return Result.success(element.getAsJsonPrimitive().getAsString());
    }

    @Override
    public Result<JsonElement> mergeList(@Nullable JsonElement list, List<JsonElement> values) {
        if (list == null) list = new JsonArray();
        if (!list.isJsonArray()) return Result.unexpected(list, JsonArray.class);

        JsonArray jsonArray = list.getAsJsonArray();
        for (JsonElement value : values) jsonArray.add(value);
        return Result.success(jsonArray);
    }

    @Override
    public Result<List<JsonElement>> asList(JsonElement element) {
        if (!element.isJsonArray()) return Result.unexpected(element, JsonArray.class);
        return Result.success(element.getAsJsonArray().asList());
    }

    @Override
    public JsonElement createUnsafeMap(Map<JsonElement, JsonElement> values) {
        JsonObject object = new JsonObject();
        values.forEach((key, value) -> object.add(key.getAsString(), value));
        return object;
    }

    @Override
    public Result<JsonElement> mergeMap(@Nullable JsonElement map, Map<JsonElement, JsonElement> values) {
        if (map == null) map = new JsonObject();
        if (!map.isJsonObject()) return Result.unexpected(map, JsonObject.class);
        JsonObject jsonObject = map.getAsJsonObject();
        for (Map.Entry<JsonElement, JsonElement> entry : values.entrySet()) {
            if (!entry.getKey().isJsonPrimitive() || !entry.getKey().getAsJsonPrimitive().isString()) return Result.unexpected(entry.getKey(), "string");
            jsonObject.add(entry.getKey().getAsString(), entry.getValue());
        }
        return Result.success(jsonObject);
    }

    @Override
    public Result<Map<JsonElement, JsonElement>> asMap(JsonElement element) {
        if (!element.isJsonObject()) return Result.unexpected(element, JsonObject.class);
        Map<JsonElement, JsonElement> map = new HashMap<>();
        element.getAsJsonObject().entrySet().forEach(entry -> map.put(this.createString(entry.getKey()), entry.getValue()));
        return Result.success(map);
    }

    @Override
    public Result<Map<String, JsonElement>> asStringTypeMap(JsonElement element) {
        if (!element.isJsonObject()) return Result.unexpected(element, JsonObject.class);
        Map<String, JsonElement> map = new HashMap<>();
        element.getAsJsonObject().entrySet().forEach(entry -> map.put(entry.getKey(), entry.getValue()));
        return Result.success(map);
    }

    @Override
    public boolean put(JsonElement map, String key, JsonElement value) {
        if (!map.isJsonObject()) return false;
        map.getAsJsonObject().add(key, value);
        return true;
    }

    @Override
    public JsonElement createByteArray(byte[] value) {
        JsonArray jsonArray = new JsonArray();
        for (byte b : value) jsonArray.add(b);
        return jsonArray;
    }

    @Override
    public Result<byte[]> asByteArray(JsonElement element) {
        if (!element.isJsonArray()) return Result.unexpected(element, JsonArray.class);
        JsonArray jsonArray = element.getAsJsonArray();
        if (StreamSupport.stream(jsonArray.spliterator(), false).anyMatch(e -> e.isJsonPrimitive() && e.getAsJsonPrimitive().isNumber())) {
            byte[] bytes = new byte[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) bytes[i] = jsonArray.get(i).getAsByte();
            return Result.success(bytes);
        } else {
            return Result.unexpected(element, byte[].class);
        }
    }

    @Override
    public JsonElement createIntArray(int[] value) {
        JsonArray jsonArray = new JsonArray();
        for (int i : value) jsonArray.add(i);
        return jsonArray;
    }

    @Override
    public Result<int[]> asIntArray(JsonElement element) {
        if (!element.isJsonArray()) return Result.unexpected(element, JsonArray.class);
        JsonArray jsonArray = element.getAsJsonArray();
        if (StreamSupport.stream(jsonArray.spliterator(), false).anyMatch(e -> e.isJsonPrimitive() && e.getAsJsonPrimitive().isNumber())) {
            int[] ints = new int[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) ints[i] = jsonArray.get(i).getAsInt();
            return Result.success(ints);
        } else {
            return Result.unexpected(element, int[].class);
        }
    }

    @Override
    public JsonElement createLongArray(long[] value) {
        JsonArray jsonArray = new JsonArray();
        for (long l : value) jsonArray.add(l);
        return jsonArray;
    }

    @Override
    public Result<long[]> asLongArray(JsonElement element) {
        if (!element.isJsonArray()) return Result.unexpected(element, JsonArray.class);
        JsonArray jsonArray = element.getAsJsonArray();
        if (StreamSupport.stream(jsonArray.spliterator(), false).anyMatch(e -> e.isJsonPrimitive() && e.getAsJsonPrimitive().isNumber())) {
            long[] longs = new long[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) longs[i] = jsonArray.get(i).getAsLong();
            return Result.success(longs);
        } else {
            return Result.unexpected(element, long[].class);
        }
    }

}
