package net.lenni0451.mcstructs.text.serializer.v1_8;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.text.Style;

import java.lang.reflect.Type;

public class StyleSerializer_v1_8 extends EventSerializers_v1_8 implements JsonSerializer<Style> {

    public StyleSerializer_v1_8(final SNbt<?> sNbt) {
        super(sNbt);
    }

    @Override
    public JsonElement serialize(Style src, Type typeOfSrc, JsonSerializationContext context) {
        if (src.isEmpty()) return null;
        JsonObject serializedStyle = new JsonObject();

        if (src.getBold() != null) serializedStyle.addProperty("bold", src.isBold());
        if (src.getItalic() != null) serializedStyle.addProperty("italic", src.isItalic());
        if (src.getUnderlined() != null) serializedStyle.addProperty("underlined", src.isUnderlined());
        if (src.getStrikethrough() != null) serializedStyle.addProperty("strikethrough", src.isStrikethrough());
        if (src.getObfuscated() != null) serializedStyle.addProperty("obfuscated", src.isObfuscated());
        if (src.getColor() != null && !src.getColor().isRGBColor()) serializedStyle.addProperty("color", src.getColor().serialize());
        if (src.getInsertion() != null) serializedStyle.add("insertion", context.serialize(src.getInsertion()));
        if (src.getClickEvent() != null) {
            JsonObject clickEvent = new JsonObject();
            clickEvent.addProperty("action", src.getClickEvent().getAction().getName());
            clickEvent.addProperty("value", this.clickEventSerializer.serialize(src.getClickEvent()));
            serializedStyle.add("clickEvent", clickEvent);
        }
        if (src.getHoverEvent() != null) {
            JsonObject hoverEvent = new JsonObject();
            hoverEvent.addProperty("action", src.getHoverEvent().getAction().getName());
            hoverEvent.add("value", context.serialize(this.hoverEventSerializer.serialize(src.getHoverEvent())));
            serializedStyle.add("hoverEvent", hoverEvent);
        }

        return serializedStyle;
    }

}
