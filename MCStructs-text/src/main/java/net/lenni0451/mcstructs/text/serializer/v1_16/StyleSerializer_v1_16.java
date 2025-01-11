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
            clickEvent.addProperty("action", src.getClickEvent().getAction().getName());
            clickEvent.addProperty("value", this.serializeClickEvent(src.getClickEvent()));
            serializedStyle.add("clickEvent", clickEvent);
        }
        if (src.getHoverEvent() != null) serializedStyle.add("hoverEvent", context.serialize(src.getHoverEvent()));
        if (src.getFont() != null) serializedStyle.addProperty("font", src.getFont().get());

        return serializedStyle;
    }

    private String serializeClickEvent(final ClickEvent clickEvent) {
        if (clickEvent instanceof OpenURLClickEvent) {
            return ((OpenURLClickEvent) clickEvent).getUrl().toString();
        } else if (clickEvent instanceof OpenFileClickEvent) {
            return ((OpenFileClickEvent) clickEvent).getPath();
        } else if (clickEvent instanceof RunCommandClickEvent) {
            return ((RunCommandClickEvent) clickEvent).getCommand();
        } else if (clickEvent instanceof SuggestCommandClickEvent) {
            return ((SuggestCommandClickEvent) clickEvent).getCommand();
        } else if (clickEvent instanceof ChangePageClickEvent) {
            return String.valueOf(((ChangePageClickEvent) clickEvent).getPage());
        } else if (clickEvent instanceof CopyToClipboardClickEvent) {
            return ((CopyToClipboardClickEvent) clickEvent).getValue();
        } else if (clickEvent instanceof LegacyClickEvent) {
            LegacyClickEvent legacyClickEvent = (LegacyClickEvent) clickEvent;
            if (legacyClickEvent.getData() instanceof LegacyClickEvent.LegacyUrlData) {
                LegacyClickEvent.LegacyUrlData urlData = (LegacyClickEvent.LegacyUrlData) legacyClickEvent.getData();
                return urlData.getUrl();
            } else if (legacyClickEvent.getData() instanceof LegacyClickEvent.LegacyPageData) {
                LegacyClickEvent.LegacyPageData pageData = (LegacyClickEvent.LegacyPageData) legacyClickEvent.getData();
                return pageData.getPage();
            }
        }
        throw new IllegalArgumentException("Unknown click event type: " + clickEvent.getClass().getName());
    }

}
