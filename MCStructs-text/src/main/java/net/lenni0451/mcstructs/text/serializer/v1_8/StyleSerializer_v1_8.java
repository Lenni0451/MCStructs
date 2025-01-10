package net.lenni0451.mcstructs.text.serializer.v1_8;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.types.*;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

import java.lang.reflect.Type;

public class StyleSerializer_v1_8 implements JsonSerializer<Style> {

    private final TextComponentSerializer textComponentSerializer;
    private final SNbt<?> sNbt;

    public StyleSerializer_v1_8(final TextComponentSerializer textComponentSerializer, final SNbt<?> sNbt) {
        this.textComponentSerializer = textComponentSerializer;
        this.sNbt = sNbt;
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
            this.serializeClickEvent(clickEvent, src.getClickEvent());
            serializedStyle.add("clickEvent", clickEvent);
        }
        if (src.getHoverEvent() != null) {
            JsonObject hoverEvent = new JsonObject();
            hoverEvent.addProperty("action", src.getHoverEvent().getAction().getName());
            hoverEvent.add("value", context.serialize(src.getHoverEvent().toLegacy(this.textComponentSerializer, this.sNbt).getText()));
            serializedStyle.add("hoverEvent", hoverEvent);
        }

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
        } else if (clickEvent instanceof TwitchUserInfoClickEvent) {
            json.addProperty("value", ((TwitchUserInfoClickEvent) clickEvent).getUser());
        } else if (clickEvent instanceof SuggestCommandClickEvent) {
            json.addProperty("value", ((SuggestCommandClickEvent) clickEvent).getCommand());
        } else if (clickEvent instanceof ChangePageClickEvent) {
            json.addProperty("value", String.valueOf(((ChangePageClickEvent) clickEvent).getPage()));
        } else {
            throw new IllegalArgumentException("Unknown click event type: " + clickEvent.getClass().getName());
        }
    }

}
