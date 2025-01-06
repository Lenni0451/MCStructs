package net.lenni0451.mcstructs.text.serializer.v1_20_5.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.json.JsonStyleSerializer_v1_20_3;

import javax.annotation.Nullable;
import java.util.function.Function;

public class JsonStyleSerializer_v1_20_5 extends JsonStyleSerializer_v1_20_3 {

    public JsonStyleSerializer_v1_20_5(final Function<JsonStyleSerializer_v1_20_3, ITypedSerializer<JsonElement, HoverEvent>> hoverEventSerializer) {
        super(hoverEventSerializer);
    }

    @Override
    public boolean isNumber(@Nullable JsonElement element) {
        return element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber();
    }

    @Override
    public boolean requiredBoolean(JsonObject obj, String name) {
        if (!obj.has(name)) throw new IllegalArgumentException("Missing boolean for '" + name + "' tag");
        JsonElement element = obj.get(name);
        if (!element.isJsonPrimitive()) throw new IllegalArgumentException("Expected boolean for '" + name + "' tag");

        JsonPrimitive primitive = element.getAsJsonPrimitive();
        if (primitive.isBoolean()) return primitive.getAsBoolean();
        else throw new IllegalArgumentException("Expected boolean for '" + name + "' tag");
    }

    @Override
    public int requiredInt(JsonObject obj, String name) {
        if (!obj.has(name)) throw new IllegalArgumentException("Missing int for '" + name + "' tag");
        JsonElement element = obj.get(name);
        if (!element.isJsonPrimitive()) throw new IllegalArgumentException("Expected int for '" + name + "' tag");

        JsonPrimitive primitive = element.getAsJsonPrimitive();
        if (primitive.isNumber()) return primitive.getAsInt();
        else throw new IllegalArgumentException("Expected int for '" + name + "' tag");
    }

}
