package net.lenni0451.mcstructs.text.serializer.v1_20_3.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;
import net.lenni0451.mcstructs.text.serializer.subtypes.IStyleSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.CodecUtils_v1_20_3;

import java.util.function.Function;

public class JsonStyleSerializer_v1_20_3 implements IStyleSerializer<JsonElement>, CodecUtils_v1_20_3 {

    private final ITypedSerializer<JsonElement, HoverEvent> hoverEventSerializer;

    public JsonStyleSerializer_v1_20_3(final Function<JsonStyleSerializer_v1_20_3, ITypedSerializer<JsonElement, HoverEvent>> hoverEventSerializer) {
        this.hoverEventSerializer = hoverEventSerializer.apply(this);
    }

    @Override
    public JsonElement serialize(Style object) {
        JsonObject out = new JsonObject();
        if (object.getColor() != null) out.addProperty("color", object.getColor().serialize());
        if (object.getBold() != null) out.addProperty("bold", object.isBold());
        if (object.getItalic() != null) out.addProperty("italic", object.isItalic());
        if (object.getUnderlined() != null) out.addProperty("underlined", object.isUnderlined());
        if (object.getStrikethrough() != null) out.addProperty("strikethrough", object.isStrikethrough());
        if (object.getObfuscated() != null) out.addProperty("obfuscated", object.isObfuscated());
        if (object.getClickEvent() != null) {
            JsonObject clickEvent = new JsonObject();
            clickEvent.addProperty("action", object.getClickEvent().getAction().getName());
            clickEvent.addProperty("value", object.getClickEvent().getValue());
            out.add("clickEvent", clickEvent);
        }
        if (object.getHoverEvent() != null) out.add("hoverEvent", this.hoverEventSerializer.serialize(object.getHoverEvent()));
        if (object.getInsertion() != null) out.addProperty("insertion", object.getInsertion());
        if (object.getFont() != null) out.addProperty("font", object.getFont().get());
        return out;
    }

    @Override
    public Style deserialize(JsonElement object) {
        if (!object.isJsonObject()) throw new IllegalArgumentException("Json element is not a json object");
        JsonObject obj = object.getAsJsonObject();

        Style style = new Style();
        if (obj.has("color")) {
            String color = requiredString(obj, "color");
            TextFormatting formatting = TextFormatting.parse(color);
            if (formatting == null) throw new IllegalArgumentException("Unknown color: " + color);
            if (formatting.isRGBColor() && (formatting.getRgbValue() < 0 || formatting.getRgbValue() > 0xFFFFFF)) {
                throw new IllegalArgumentException("Out of bounds RGB color: " + formatting.getRgbValue());
            }
            style.setFormatting(formatting);
        }
        style.setBold(optionalBoolean(obj, "bold"));
        style.setItalic(optionalBoolean(obj, "italic"));
        style.setUnderlined(optionalBoolean(obj, "underlined"));
        style.setStrikethrough(optionalBoolean(obj, "strikethrough"));
        style.setObfuscated(optionalBoolean(obj, "obfuscated"));
        if (obj.has("clickEvent")) {
            JsonObject clickEvent = requiredObject(obj, "clickEvent");
            ClickEventAction action = ClickEventAction.byName(requiredString(clickEvent, "action"), false);
            if (action == null || ClickEventAction.TWITCH_USER_INFO.equals(action)) {
                throw new IllegalArgumentException("Unknown click event action: " + clickEvent.get("action").getAsString());
            }
            if (!action.isUserDefinable()) throw new IllegalArgumentException("Click event action is not user definable: " + action);
            style.setClickEvent(new ClickEvent(action, requiredString(clickEvent, "value")));
        }
        if (obj.has("hoverEvent")) style.setHoverEvent(this.hoverEventSerializer.deserialize(requiredObject(obj, "hoverEvent")));
        style.setInsertion(optionalString(obj, "insertion"));
        if (obj.has("font")) style.setFont(Identifier.of(requiredString(obj, "font")));
        return style;
    }

}
