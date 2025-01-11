package net.lenni0451.mcstructs.text.serializer.v1_7;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.types.*;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.AchievementHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.LegacyHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;

import java.lang.reflect.Type;

public class StyleSerializer_v1_7 implements JsonSerializer<Style> {

    private final SNbt<?> sNbt;

    public StyleSerializer_v1_7(final SNbt<?> sNbt) {
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
        if (src.getClickEvent() != null) {
            JsonObject clickEvent = new JsonObject();
            clickEvent.addProperty("action", src.getClickEvent().getAction().getName());
            clickEvent.addProperty("value", this.serializeClickEvent(src.getClickEvent()));
            serializedStyle.add("clickEvent", clickEvent);
        }
        if (src.getHoverEvent() != null) {
            JsonObject hoverEvent = new JsonObject();
            hoverEvent.addProperty("action", src.getHoverEvent().getAction().getName());
            hoverEvent.add("value", context.serialize(this.serializeHoverEvent(src.getHoverEvent())));
            serializedStyle.add("hoverEvent", hoverEvent);
        }

        return serializedStyle;
    }

    private String serializeClickEvent(final ClickEvent clickEvent) {
        if (clickEvent instanceof LegacyClickEvent) {
            return ((LegacyClickEvent) clickEvent).getValue();
        } else if (clickEvent instanceof OpenURLClickEvent) {
            return ((OpenURLClickEvent) clickEvent).getUrl().toString();
        } else if (clickEvent instanceof OpenFileClickEvent) {
            return ((OpenFileClickEvent) clickEvent).getPath();
        } else if (clickEvent instanceof RunCommandClickEvent) {
            return ((RunCommandClickEvent) clickEvent).getCommand();
        } else if (clickEvent instanceof SuggestCommandClickEvent) {
            return ((SuggestCommandClickEvent) clickEvent).getCommand();
        } else if (clickEvent instanceof ChangePageClickEvent) {
            return String.valueOf(((ChangePageClickEvent) clickEvent).getPage());
        }
        throw new IllegalArgumentException("Unknown click event type: " + clickEvent.getClass().getName());
    }

    private TextComponent serializeHoverEvent(final HoverEvent hoverEvent) {
        if (hoverEvent instanceof TextHoverEvent) {
            return ((TextHoverEvent) hoverEvent).getText();
        } else if (hoverEvent instanceof AchievementHoverEvent) {
            return new StringComponent(((AchievementHoverEvent) hoverEvent).getStatistic());
        } else if (hoverEvent instanceof LegacyHoverEvent) {
            LegacyHoverEvent legacyHoverEvent = (LegacyHoverEvent) hoverEvent;
            if (legacyHoverEvent.getData() instanceof LegacyHoverEvent.LegacyInvalidData) {
                return ((LegacyHoverEvent.LegacyInvalidData) legacyHoverEvent.getData()).getRaw();
            } else if (legacyHoverEvent.getData() instanceof LegacyHoverEvent.LegacyIntItemData) {
                LegacyHoverEvent.LegacyIntItemData itemData = (LegacyHoverEvent.LegacyIntItemData) legacyHoverEvent.getData();
                CompoundTag itemTag = new CompoundTag();
                itemTag.addShort("id", itemData.getId());
                itemTag.addByte("Count", itemData.getCount());
                itemTag.addShort("Damage", itemData.getDamage());
                if (itemData.getTag() != null) itemTag.addCompound("tag", itemData.getTag());
                return new StringComponent(this.sNbt.trySerialize(itemTag));
            }
        }
        throw new IllegalArgumentException("Unknown hover event type: " + hoverEvent.getClass().getName());
    }

}
