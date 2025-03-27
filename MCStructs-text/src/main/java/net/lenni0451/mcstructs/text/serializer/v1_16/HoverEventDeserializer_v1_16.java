package net.lenni0451.mcstructs.text.serializer.v1_16;

import com.google.gson.*;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtDeserializeException;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.EntityHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;
import net.lenni0451.mcstructs.text.utils.JsonUtils;

import java.lang.reflect.Type;
import java.util.UUID;

import static net.lenni0451.mcstructs.text.utils.JsonUtils.getString;

public class HoverEventDeserializer_v1_16 implements JsonDeserializer<HoverEvent> {

    protected final TextComponentSerializer textComponentSerializer;
    protected final SNbt<?> sNbt;

    public HoverEventDeserializer_v1_16(final TextComponentSerializer textComponentSerializer, final SNbt<?> sNbt) {
        this.textComponentSerializer = textComponentSerializer;
        this.sNbt = sNbt;
    }

    @Override
    public HoverEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) return null;
        JsonObject rawHoverEvent = json.getAsJsonObject();
        if (rawHoverEvent == null) return null;

        String rawAction = getString(rawHoverEvent, "action", null);
        if (rawAction == null) return null;
        HoverEventAction action = HoverEventAction.byName(rawAction);
        if (action == null) return null;
        JsonElement rawContents = rawHoverEvent.get("contents");
        if (rawContents != null) return this.deserialize(action, rawContents);
        TextComponent text = this.textComponentSerializer.deserialize(rawHoverEvent.get("value"));
        if (text == null) return null;
        return this.deserializeLegacy(action, text);
    }

    protected HoverEvent deserialize(final HoverEventAction action, final JsonElement contents) {
        switch (action) {
            case SHOW_TEXT:
                return new TextHoverEvent(this.textComponentSerializer.deserialize(contents));
            case SHOW_ITEM:
                if (contents.isJsonPrimitive()) return new ItemHoverEvent(Identifier.of(contents.getAsString()), 1, null);
                JsonObject rawItem = JsonUtils.getJsonObject(contents, "item");
                Identifier item = Identifier.of(getString(rawItem, "id"));
                int count = JsonUtils.getInt(rawItem, "count", 1);
                if (rawItem.has("tag")) {
                    String rawTag = getString(rawItem, "tag");
                    return new ItemHoverEvent(item, count, (CompoundTag) this.sNbt.tryDeserialize(rawTag));
                }
                return new ItemHoverEvent(item, count, null);
            case SHOW_ENTITY:
                if (!contents.isJsonObject()) return null;
                JsonObject rawEntity = contents.getAsJsonObject();
                Identifier entityType = Identifier.of(getString(rawEntity, "type"));
                UUID uuid = UUID.fromString(getString(rawEntity, "id"));
                TextComponent name = this.textComponentSerializer.deserialize(rawEntity.get("name"));
                return new EntityHoverEvent(entityType, uuid, name);
            default:
                return null;
        }
    }

    protected HoverEvent deserializeLegacy(final HoverEventAction action, final TextComponent text) {
        switch (action) {
            case SHOW_TEXT:
                return new TextHoverEvent(text);
            case SHOW_ITEM:
                CompoundTag rawTag = (CompoundTag) this.sNbt.tryDeserialize(text.asUnformattedString());
                if (rawTag == null) return null;
                Identifier id = Identifier.of(rawTag.getString("id"));
                int count = rawTag.getByte("count");
                CompoundTag tag = null;
                if (rawTag.contains("tag", NbtType.COMPOUND)) tag = rawTag.getCompound("tag");
                return new ItemHoverEvent(id, count, tag);
            case SHOW_ENTITY:
                try {
                    CompoundTag rawEntity = (CompoundTag) this.sNbt.deserialize(text.asUnformattedString());
                    TextComponent name = this.textComponentSerializer.deserialize(rawEntity.getString("name"));
                    Identifier entityType = Identifier.of(rawEntity.getString("type"));
                    UUID uuid = UUID.fromString(rawEntity.getString("id"));
                    return new EntityHoverEvent(entityType, uuid, name);
                } catch (SNbtDeserializeException | JsonSyntaxException ignored) {
                    return null;
                }
            default:
                return null;
        }
    }

}
