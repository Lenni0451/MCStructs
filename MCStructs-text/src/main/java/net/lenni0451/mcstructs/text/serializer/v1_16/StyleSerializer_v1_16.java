package net.lenni0451.mcstructs.text.serializer.v1_16;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.types.*;

import java.lang.reflect.Type;

public class StyleSerializer_v1_16 implements JsonSerializer<Style> {

    @Override
    public JsonElement serialize(Style src, Type typeOfSrc, JsonSerializationContext context) {
        if (src.isEmpty()) return null;
        JsonObject serializedStyle = new JsonObject();

        if (src.getBold() != null) serializedStyle.addProperty("bold", src.isBold());
        if (src.getItalic() != null) serializedStyle.addProperty("italic", src.isItalic());
        if (src.getUnderlined() != null) serializedStyle.addProperty("underlined", src.isUnderlined());
        if (src.getStrikethrough() != null) serializedStyle.addProperty("strikethrough", src.isStrikethrough());
        if (src.getObfuscated() != null) serializedStyle.addProperty("obfuscated", src.isObfuscated());
        if (src.getColor() != null) serializedStyle.addProperty("color", src.getColor().serialize());
        if (src.getInsertion() != null) serializedStyle.add("insertion", context.serialize(src.getInsertion()));
        if (src.getClickEvent() != null) {
            JsonObject clickEvent = new JsonObject();
            this.serializeClickEvent(clickEvent, src.getClickEvent());
            serializedStyle.add("clickEvent", clickEvent);
        }
        if (src.getHoverEvent() != null) serializedStyle.add("hoverEvent", context.serialize(src.getHoverEvent()));
        if (src.getFont() != null) serializedStyle.addProperty("font", src.getFont().get());

        return serializedStyle;
    }

    private void serializeClickEvent(final JsonObject json, final ClickEvent clickEvent) {
        json.addProperty("action", clickEvent.getAction().getName());
        if (clickEvent instanceof LegacyClickEvent) {
            json.addProperty("value", ((LegacyClickEvent) clickEvent).getValue());
        } else if (clickEvent instanceof OpenURLClickEvent) {
            json.addProperty("value", ((OpenURLClickEvent) clickEvent).getUrl().toString());
        } else if (clickEvent instanceof OpenFileClickEvent) {
            json.addProperty("value", ((OpenFileClickEvent) clickEvent).getPath());
        } else if (clickEvent instanceof RunCommandClickEvent) {
            json.addProperty("value", ((RunCommandClickEvent) clickEvent).getCommand());
        } else if (clickEvent instanceof SuggestCommandClickEvent) {
            json.addProperty("value", ((SuggestCommandClickEvent) clickEvent).getCommand());
        } else if (clickEvent instanceof ChangePageClickEvent) {
            json.addProperty("value", String.valueOf(((ChangePageClickEvent) clickEvent).getPage()));
        } else if (clickEvent instanceof CopyToClipboardClickEvent) {
            json.addProperty("value", ((CopyToClipboardClickEvent) clickEvent).getValue());
        } else {
            throw new IllegalArgumentException("Unknown click event type: " + clickEvent.getClass().getName());
        }
    }

}
