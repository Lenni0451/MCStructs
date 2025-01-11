package net.lenni0451.mcstructs.text.serializer.v1_7;

import com.google.gson.*;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.TextFormatting;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;
import net.lenni0451.mcstructs.text.events.click.types.LegacyClickEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.LegacyHoverEvent;

import java.lang.reflect.Type;
import java.net.URI;

public class StyleDeserializer_v1_7 implements JsonDeserializer<Style> {

    private final SNbt<?> sNbt;

    public StyleDeserializer_v1_7(final SNbt<?> sNbt) {
        this.sNbt = sNbt;
    }

    @Override
    public Style deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            JsonObject rawStyle = json.getAsJsonObject();
            Style style = new Style();

            if (rawStyle.has("bold")) style.setBold(rawStyle.get("bold").getAsBoolean());
            if (rawStyle.has("italic")) style.setItalic(rawStyle.get("italic").getAsBoolean());
            if (rawStyle.has("underlined")) style.setUnderlined(rawStyle.get("underlined").getAsBoolean());
            if (rawStyle.has("strikethrough")) style.setStrikethrough(rawStyle.get("strikethrough").getAsBoolean());
            if (rawStyle.has("obfuscated")) style.setObfuscated(rawStyle.get("obfuscated").getAsBoolean());
            if (rawStyle.has("color")) style.setFormatting(TextFormatting.getByName(rawStyle.get("color").getAsString()));
            if (rawStyle.has("clickEvent")) {
                JsonObject rawClickEvent = rawStyle.getAsJsonObject("clickEvent");
                if (rawClickEvent != null) {
                    JsonPrimitive rawAction = rawClickEvent.getAsJsonPrimitive("action");
                    JsonPrimitive rawValue = rawClickEvent.getAsJsonPrimitive("value");

                    ClickEventAction action = null;
                    String value = null;
                    if (rawAction != null) action = ClickEventAction.byName(rawAction.getAsString());
                    if (rawValue != null) value = rawValue.getAsString();

                    if (action != null && value != null && action.isUserDefinable()) {
                        style.setClickEvent(this.deserializeClickEvent(action, value));
                    }
                }
            }
            if (rawStyle.has("hoverEvent")) {
                JsonObject rawHoverEvent = rawStyle.getAsJsonObject("hoverEvent");
                if (rawHoverEvent != null) {
                    JsonPrimitive rawAction = rawHoverEvent.getAsJsonPrimitive("action");

                    HoverEventAction action = null;
                    TextComponent value = context.deserialize(rawHoverEvent.get("value"), TextComponent.class);
                    if (rawAction != null) action = HoverEventAction.byName(rawAction.getAsString());

                    if (action != null && value != null && action.isUserDefinable()) {
                        style.setHoverEvent(this.deserializeHoverEvent(action, value));
                    }
                }
            }
            return style;
        }
        return null;
    }

    private ClickEvent deserializeClickEvent(final ClickEventAction action, final String value) {
        switch (action) {
            case OPEN_URL:
                try {
                    return ClickEvent.openURL(new URI(value));
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
            default:
                throw new IllegalArgumentException("Unknown click event action: " + action.getName());
        }
    }

    private HoverEvent deserializeHoverEvent(final HoverEventAction action, final TextComponent value) {
        switch (action) {
            case SHOW_TEXT:
                return HoverEvent.text(value);
            case SHOW_ACHIEVEMENT:
                return HoverEvent.achievement(value.asUnformattedString());
            case SHOW_ITEM:
                try {
                    NbtTag tag = this.sNbt.deserialize(value.asUnformattedString());
                    if (tag.isCompoundTag()) {
                        CompoundTag compoundTag = tag.asCompoundTag();
                        short itemId = compoundTag.getShort("id");
                        byte itemCount = compoundTag.getByte("Count");
                        short itemDamage = compoundTag.getShort("Damage");
                        if (itemDamage < 0) itemDamage = 0;
                        CompoundTag itemTag = compoundTag.getCompound("tag", null);

                        return new LegacyHoverEvent(action, new LegacyHoverEvent.LegacyIntItemData(itemId, itemCount, itemDamage, itemTag));
                    }
                } catch (Throwable ignored) {
                }
                return new LegacyHoverEvent(action, new LegacyHoverEvent.LegacyInvalidData(value));
            default:
                throw new IllegalArgumentException("Unknown hover event action: " + action.getName());
        }
    }

}
