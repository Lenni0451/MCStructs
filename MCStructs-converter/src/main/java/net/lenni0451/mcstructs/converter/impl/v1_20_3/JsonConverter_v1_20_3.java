package net.lenni0451.mcstructs.converter.impl.v1_20_3;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.lenni0451.mcstructs.converter.ConverterResult;
import net.lenni0451.mcstructs.converter.DataConverter;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

public class JsonConverter_v1_20_3 implements DataConverter<JsonElement> {

    @Override
    public JsonElement createBoolean(boolean value) {
        return new JsonPrimitive(value);
    }

    @Override
    public ConverterResult<Boolean> asBoolean(JsonElement element) {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isBoolean()) return ConverterResult.success(primitive.getAsBoolean());
            else if (primitive.isNumber()) return ConverterResult.success(primitive.getAsNumber().byteValue() != 0);
        }
        return ConverterResult.unexpected(element, "boolean");
    }

    @Override
    public JsonElement createNumber(Number number) {
        return new JsonPrimitive(number);
    }

    @Override
    public ConverterResult<Number> asNumber(JsonElement element) {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isBoolean()) return ConverterResult.success(primitive.getAsBoolean() ? 1 : 0);
            else if (primitive.isNumber()) return ConverterResult.success(primitive.getAsNumber());
        }
        return ConverterResult.unexpected(element, "number");
    }

    @Override
    public JsonElement createString(String value) {
        return new JsonPrimitive(value);
    }

    @Override
    public ConverterResult<String> asString(JsonElement element) {
        if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) return ConverterResult.unexpected(element, "string");
        return ConverterResult.success(element.getAsJsonPrimitive().getAsString());
    }

    @Override
    public JsonElement createList(List<JsonElement> values) {
        return this.mergeList(new JsonArray(), values).get();
    }

    @Override
    public ConverterResult<JsonElement> mergeList(@Nullable JsonElement list, List<JsonElement> values) {
        if (list == null) list = new JsonArray();
        if (!list.isJsonArray()) return ConverterResult.unexpected(list, JsonArray.class);

        JsonArray jsonArray = list.getAsJsonArray();
        for (JsonElement value : values) jsonArray.add(value);
        return ConverterResult.success(jsonArray);
    }

    @Override
    public ConverterResult<List<JsonElement>> asList(JsonElement element) {
        if (!element.isJsonArray()) return ConverterResult.unexpected(element, JsonArray.class);
        return ConverterResult.success(element.getAsJsonArray().asList());
    }

    @Override
    public JsonElement createMap(Map<JsonElement, JsonElement> values) {
        JsonObject object = new JsonObject();
        values.forEach((key, value) -> object.add(key.getAsString(), value));
        return object;
    }

    @Override
    public ConverterResult<JsonElement> mergeMap(@Nullable JsonElement map, Map<JsonElement, JsonElement> values) {
        if (map == null) map = new JsonObject();
        if (!map.isJsonObject()) return ConverterResult.unexpected(map, JsonObject.class);
        JsonObject jsonObject = map.getAsJsonObject();
        for (Map.Entry<JsonElement, JsonElement> entry : values.entrySet()) {
            if (!entry.getKey().isJsonPrimitive() || !entry.getKey().getAsJsonPrimitive().isString()) return ConverterResult.unexpected(entry.getKey(), "string");
            jsonObject.add(entry.getKey().getAsString(), entry.getValue());
        }
        return ConverterResult.success(jsonObject);
    }

    @Override
    public ConverterResult<Map<JsonElement, JsonElement>> asMap(JsonElement element) {
        if (!element.isJsonObject()) return ConverterResult.unexpected(element, JsonObject.class);
        Map<JsonElement, JsonElement> map = new HashMap<>();
        element.getAsJsonObject().entrySet().forEach(entry -> map.put(this.createString(entry.getKey()), entry.getValue()));
        return ConverterResult.success(map);
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
    public ConverterResult<byte[]> asByteArray(JsonElement element) {
        if (!element.isJsonArray()) return ConverterResult.unexpected(element, JsonArray.class);
        JsonArray jsonArray = element.getAsJsonArray();
        if (StreamSupport.stream(jsonArray.spliterator(), false).anyMatch(e -> e.isJsonPrimitive() && e.getAsJsonPrimitive().isNumber())) {
            byte[] bytes = new byte[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) bytes[i] = jsonArray.get(i).getAsByte();
            return ConverterResult.success(bytes);
        } else {
            return ConverterResult.unexpected(element, byte[].class);
        }
    }

    @Override
    public JsonElement createIntArray(int[] value) {
        JsonArray jsonArray = new JsonArray();
        for (int i : value) jsonArray.add(i);
        return jsonArray;
    }

    @Override
    public ConverterResult<int[]> asIntArray(JsonElement element) {
        if (!element.isJsonArray()) return ConverterResult.unexpected(element, JsonArray.class);
        JsonArray jsonArray = element.getAsJsonArray();
        if (StreamSupport.stream(jsonArray.spliterator(), false).anyMatch(e -> e.isJsonPrimitive() && e.getAsJsonPrimitive().isNumber())) {
            int[] ints = new int[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) ints[i] = jsonArray.get(i).getAsInt();
            return ConverterResult.success(ints);
        } else {
            return ConverterResult.unexpected(element, int[].class);
        }
    }

    @Override
    public JsonElement createLongArray(long[] value) {
        JsonArray jsonArray = new JsonArray();
        for (long l : value) jsonArray.add(l);
        return jsonArray;
    }

    @Override
    public ConverterResult<long[]> asLongArray(JsonElement element) {
        if (!element.isJsonArray()) return ConverterResult.unexpected(element, JsonArray.class);
        JsonArray jsonArray = element.getAsJsonArray();
        if (StreamSupport.stream(jsonArray.spliterator(), false).anyMatch(e -> e.isJsonPrimitive() && e.getAsJsonPrimitive().isNumber())) {
            long[] longs = new long[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) longs[i] = jsonArray.get(i).getAsLong();
            return ConverterResult.success(longs);
        } else {
            return ConverterResult.unexpected(element, long[].class);
        }
    }

}
