package net.lenni0451.mcstructs.converter.impl.v1_20_5;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.lenni0451.mcstructs.converter.ConverterResult;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.JsonConverter_v1_20_3;

public class JsonConverter_v1_20_5 extends JsonConverter_v1_20_3 {

    public static final JsonConverter_v1_20_5 INSTANCE = new JsonConverter_v1_20_5();

    @Override
    public ConverterResult<Boolean> asBoolean(JsonElement element) {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isBoolean()) return ConverterResult.success(primitive.getAsBoolean());
        }
        return ConverterResult.unexpected(element, "boolean");
    }

    @Override
    public ConverterResult<Number> asNumber(JsonElement element) {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isNumber()) return ConverterResult.success(primitive.getAsNumber());
        }
        return ConverterResult.unexpected(element, "number");
    }

}
