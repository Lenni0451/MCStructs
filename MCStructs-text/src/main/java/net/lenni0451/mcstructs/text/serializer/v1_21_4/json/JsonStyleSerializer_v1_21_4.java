package net.lenni0451.mcstructs.text.serializer.v1_21_4.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.json.JsonStyleSerializer_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.json.JsonStyleSerializer_v1_20_5;

import java.util.List;
import java.util.function.Function;

public class JsonStyleSerializer_v1_21_4 extends JsonStyleSerializer_v1_20_5 {

    public JsonStyleSerializer_v1_21_4(final Function<JsonStyleSerializer_v1_20_3, ITypedSerializer<JsonElement, HoverEvent>> hoverEventSerializer) {
        super(hoverEventSerializer);
    }

    @Override
    public JsonElement serialize(Style object) {
        JsonObject out = super.serialize(object).getAsJsonObject();
        if (object.getShadowColor() != null) out.addProperty("shadow_color", object.getShadowColor());
        return out;
    }

    @Override
    public Style deserialize(JsonElement object) {
        Style style = super.deserialize(object);
        JsonObject obj = object.getAsJsonObject();
        if (obj.has("shadow_color")) {
            Integer shadowColor = this.optionalInt(obj, "shadow_color");
            if (shadowColor == null) {
                List<Number> numbers = this.asNumberStream(obj.get("shadow_color"));
                if (numbers.size() != 4) throw new IllegalArgumentException("Expected list with 4 values for 'shadow_color' tag");
                //For some reason the stream is in RGBA order
                int r = (int) Math.floor(numbers.get(0).floatValue() * 255);
                int g = (int) Math.floor(numbers.get(1).floatValue() * 255);
                int b = (int) Math.floor(numbers.get(2).floatValue() * 255);
                int a = (int) Math.floor(numbers.get(3).floatValue() * 255);
                shadowColor = (a << 24) | (r << 16) | (g << 8) | b;
            }
            style.setShadowColor(shadowColor);
        }
        return style;
    }

}
