package net.lenni0451.mcstructs.text.serializer.v1_16;

import com.google.gson.*;
import net.lenni0451.mcstructs.general.Identifier;
import net.lenni0451.mcstructs.nbt.NbtRegistry;
import net.lenni0451.mcstructs.nbt.snbt.SNbtParseException;
import net.lenni0451.mcstructs.nbt.snbt.SNbtParser;
import net.lenni0451.mcstructs.nbt.tags.CompoundNbt;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.events.hover.AHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.EntityHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import net.lenni0451.mcstructs.text.serializer.TextComponentJsonUtils;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

import java.lang.reflect.Type;
import java.util.UUID;

import static net.lenni0451.mcstructs.text.serializer.TextComponentJsonUtils.getString;

public class HoverEventDeserializer_v1_16 implements JsonDeserializer<AHoverEvent> {

    @Override
    public AHoverEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) return null;
        JsonObject rawHoverEvent = json.getAsJsonObject();
        if (rawHoverEvent == null) return null;

        String rawAction = getString(rawHoverEvent, "action", null);
        if (rawAction == null) return null;
        HoverEventAction action = HoverEventAction.getByName(rawAction);
        if (action == null) return null;
        JsonElement rawContents = rawHoverEvent.get("contents");
        if (rawContents != null) return this.deserialize(action, rawContents);
        ATextComponent text = TextComponentSerializer.V1_16.deserialize(rawHoverEvent.get("value"));
        if (text == null) return null;
        return this.deserializeLegacy(action, text);
    }

    private AHoverEvent deserialize(final HoverEventAction action, final JsonElement contents) {
        switch (action) {
            case SHOW_TEXT:
                return new TextHoverEvent(action, TextComponentSerializer.V1_16.deserialize(contents));
            case SHOW_ITEM:
                if (contents.isJsonPrimitive()) return new ItemHoverEvent(action, Identifier.of(contents.getAsString()), 1, null);
                JsonObject rawItem = TextComponentJsonUtils.getJsonObject(contents, "item");
                Identifier item = Identifier.of(getString(rawItem, "id"));
                int count = TextComponentJsonUtils.getInt(rawItem, "count", 1);
                if (rawItem.has("tag")) {
                    String rawTag = getString(rawItem, "tag");
                    return new ItemHoverEvent(action, item, count, SNbtParser.V1_14.tryParse(rawTag));
                }
                return new ItemHoverEvent(action, item, count, null);
            case SHOW_ENTITY:
                if (!contents.isJsonObject()) return null;
                JsonObject rawEntity = contents.getAsJsonObject();
                Identifier entityType = Identifier.of(getString(rawEntity, "type"));
                UUID uuid = UUID.fromString(getString(rawEntity, "id"));
                ATextComponent name = TextComponentSerializer.V1_16.deserialize(rawEntity.get("name"));
                return new EntityHoverEvent(action, entityType, uuid, name);
            default:
                return null;
        }
    }

    private AHoverEvent deserializeLegacy(final HoverEventAction action, final ATextComponent text) {
        switch (action) {
            case SHOW_TEXT:
                return new TextHoverEvent(action, text);
            case SHOW_ITEM:
                CompoundNbt rawTag = SNbtParser.V1_14.tryParse(text.asString());
                if (rawTag == null) return null;
                Identifier id = Identifier.of(rawTag.getString("id"));
                int count = rawTag.getByte("count");
                CompoundNbt tag = null;
                if (rawTag.contains("tag", NbtRegistry.COMPOUND_NBT)) tag = rawTag.getCompound("tag");
                return new ItemHoverEvent(action, id, count, tag);
            case SHOW_ENTITY:
                try {
                    CompoundNbt rawEntity = SNbtParser.V1_14.parse(text.asString());
                    ATextComponent name = TextComponentSerializer.V1_16.deserialize(rawEntity.getString("name"));
                    Identifier entityType = Identifier.of(rawEntity.getString("type"));
                    UUID uuid = UUID.fromString(rawEntity.getString("id"));
                    return new EntityHoverEvent(action, entityType, uuid, name);
                } catch (SNbtParseException | JsonSyntaxException ignored) {
                    return null;
                }
            default:
                return null;
        }
    }

}