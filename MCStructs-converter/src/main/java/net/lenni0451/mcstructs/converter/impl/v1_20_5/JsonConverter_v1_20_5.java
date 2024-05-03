package net.lenni0451.mcstructs.converter.impl.v1_20_5;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.lenni0451.mcstructs.converter.Result;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.JsonConverter_v1_20_3;

public class JsonConverter_v1_20_5 extends JsonConverter_v1_20_3 {

    public static final JsonConverter_v1_20_5 INSTANCE = new JsonConverter_v1_20_5();

    @Override
    public Result<Boolean> asBoolean(JsonElement element) {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isBoolean()) return Result.success(primitive.getAsBoolean());
        }
        return Result.unexpected(element, "boolean");
    }

    @Override
    public Result<Number> asNumber(JsonElement element) {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isNumber()) return Result.success(primitive.getAsNumber());
        }
        return Result.unexpected(element, "number");
    }

}
