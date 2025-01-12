package net.lenni0451.mcstructs.text.serializer.v1_16;

import com.google.gson.*;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;
import net.lenni0451.mcstructs.text.events.click.types.LegacyClickEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;

import java.lang.reflect.Type;
import java.net.URI;

import static net.lenni0451.mcstructs.text.utils.JsonUtils.getJsonObject;
import static net.lenni0451.mcstructs.text.utils.JsonUtils.getString;

public class StyleDeserializer_v1_16 implements JsonDeserializer<Style> {

    @Override
    public Style deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) return null;
        JsonObject rawStyle = json.getAsJsonObject();
        if (rawStyle == null) return null;
        Style style = new Style();

        if (rawStyle.has("bold")) style.setBold(rawStyle.get("bold").getAsBoolean());
        if (rawStyle.has("italic")) style.setItalic(rawStyle.get("italic").getAsBoolean());
        if (rawStyle.has("underlined")) style.setUnderlined(rawStyle.get("underlined").getAsBoolean());
        if (rawStyle.has("strikethrough")) style.setStrikethrough(rawStyle.get("strikethrough").getAsBoolean());
        if (rawStyle.has("obfuscated")) style.setObfuscated(rawStyle.get("obfuscated").getAsBoolean());
        if (rawStyle.has("color")) style.setFormatting(TextFormatting.parse(getString(rawStyle, "color")));
        if (rawStyle.has("insertion")) style.setInsertion(getString(rawStyle, "insertion", null));
        if (rawStyle.has("clickEvent")) {
            JsonObject rawClickEvent = getJsonObject(rawStyle, "clickEvent");
            String rawAction = getString(rawClickEvent, "action");

            ClickEventAction action = null;
            String value = getString(rawClickEvent, "value");
            if (rawAction != null) action = ClickEventAction.byName(rawAction);

            if (action != null && value != null && action.isUserDefinable()) {
                style.setClickEvent(this.deserializeClickEvent(action, value));
            }
        }
        if (rawStyle.has("hoverEvent")) {
            JsonObject rawHoverEvent = getJsonObject(rawStyle, "hoverEvent");
            HoverEvent hoverEvent = context.deserialize(rawHoverEvent, HoverEvent.class);
            if (hoverEvent != null && hoverEvent.getAction().isUserDefinable()) {
                style.setHoverEvent(hoverEvent);
            }
        }
        if (rawStyle.has("font")) {
            String font = getString(rawStyle, "font");
            try {
                style.setFont(Identifier.of(font));
            } catch (Throwable t) {
                throw new JsonSyntaxException("Invalid font name: " + font);
            }
        }
        return style;
    }

    private ClickEvent deserializeClickEvent(final ClickEventAction action, final String value) {
        switch (action) {
            case OPEN_URL:
                try {
                    return ClickEvent.openUrl(new URI(value));
                } catch (Throwable t) {
                    return new LegacyClickEvent(action, new LegacyClickEvent.LegacyUrlData(value));
                }
            case OPEN_FILE:
                return ClickEvent.openFile(value);
            case RUN_COMMAND:
                return ClickEvent.runCommand(value);
            case SUGGEST_COMMAND:
                return ClickEvent.suggestCommand(value);
            case CHANGE_PAGE:
                try {
                    return ClickEvent.changePage(Integer.parseInt(value));
                } catch (Throwable t) {
                    return new LegacyClickEvent(action, new LegacyClickEvent.LegacyPageData(value));
                }
            case COPY_TO_CLIPBOARD:
                return ClickEvent.copyToClipboard(value);
            default:
                throw new IllegalArgumentException("Unknown click event action: " + action.getName());
        }
    }

}
