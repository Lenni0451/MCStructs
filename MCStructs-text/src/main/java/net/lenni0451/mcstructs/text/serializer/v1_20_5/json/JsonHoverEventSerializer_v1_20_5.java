package net.lenni0451.mcstructs.text.serializer.v1_20_5.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.EntityHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.json.JsonHoverEventSerializer_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.TextComponentCodec_v1_20_5;

import java.util.UUID;

public class JsonHoverEventSerializer_v1_20_5 extends JsonHoverEventSerializer_v1_20_3 {

    private static final String ACTION = "action";
    private static final String CONTENTS = "contents";
    private static final String VALUE = "value";

    private final TextComponentCodec_v1_20_5 codec;
    private final ITypedSerializer<JsonElement, TextComponent> textSerializer;
    private final SNbt<CompoundTag> sNbt;

    public JsonHoverEventSerializer_v1_20_5(final TextComponentCodec_v1_20_5 codec, final ITypedSerializer<JsonElement, TextComponent> textSerializer, final SNbt<CompoundTag> sNbt) {
        super(codec, textSerializer, sNbt);
        this.codec = codec;
        this.textSerializer = textSerializer;
        this.sNbt = sNbt;
    }

    @Override
    public JsonElement serialize(HoverEvent object) {
        JsonObject out = new JsonObject();
        out.addProperty(ACTION, object.getAction().getName());
        if (object instanceof TextHoverEvent) {
            TextHoverEvent textHoverEvent = (TextHoverEvent) object;
            out.add(CONTENTS, this.textSerializer.serialize(textHoverEvent.getText()));
        } else if (object instanceof ItemHoverEvent) {
            ItemHoverEvent itemHoverEvent = (ItemHoverEvent) object;
            JsonObject contents = new JsonObject();
            contents.addProperty("id", itemHoverEvent.asModernHolder().getId().get());
            if (itemHoverEvent.asModernHolder().getCount() != 1) contents.addProperty("count", itemHoverEvent.asModernHolder().getCount());
            if (itemHoverEvent.asModernHolder().getTag() != null) contents.add("components", this.codec.convertItemComponents(itemHoverEvent.asModernHolder().getTag()));
            out.add(CONTENTS, contents);
        } else if (object instanceof EntityHoverEvent) {
            EntityHoverEvent entityHoverEvent = (EntityHoverEvent) object;
            JsonObject contents = new JsonObject();
            contents.addProperty("type", entityHoverEvent.asModernHolder().getType().get());
            JsonArray id = new JsonArray();
            id.add((int) (entityHoverEvent.asModernHolder().getUuid().getMostSignificantBits() >> 32));
            id.add((int) (entityHoverEvent.asModernHolder().getUuid().getMostSignificantBits() & 0xFFFF_FFFFL));
            id.add((int) (entityHoverEvent.asModernHolder().getUuid().getLeastSignificantBits() >> 32));
            id.add((int) (entityHoverEvent.asModernHolder().getUuid().getLeastSignificantBits() & 0xFFFF_FFFFL));
            contents.add("id", id);
            if (entityHoverEvent.asModernHolder().getName() != null) contents.add("name", this.textSerializer.serialize(entityHoverEvent.asModernHolder().getName()));
            out.add(CONTENTS, contents);
        } else {
            throw new IllegalArgumentException("Unknown hover event type: " + object.getClass().getName());
        }
        return out;
    }

    @Override
    public HoverEvent deserialize(JsonElement object) {
        if (!object.isJsonObject()) throw new IllegalArgumentException("Element must be a json object");
        JsonObject obj = object.getAsJsonObject();

        HoverEventAction action = HoverEventAction.byName(requiredString(obj, ACTION), false);
        if (action == null) throw new IllegalArgumentException("Unknown hover event action: " + obj.get(ACTION).getAsString());
        if (!action.isUserDefinable()) throw new IllegalArgumentException("Hover event action is not user definable: " + action);

        if (obj.has(CONTENTS)) {
            switch (action) {
                case SHOW_TEXT:
                    return new TextHoverEvent(this.textSerializer.deserialize(obj.get(CONTENTS)));
                case SHOW_ITEM:
                    //If the item is not valid or air an exception will be thrown
                    if (obj.has(CONTENTS) && isString(obj.get(CONTENTS))) {
                        Identifier id = Identifier.of(obj.get(CONTENTS).getAsString());
                        this.verifyItem(id);
                        return new ItemHoverEvent(id, 1, null);
                    } else if (obj.has(CONTENTS) && isObject(obj.get(CONTENTS))) {
                        JsonObject contents = obj.getAsJsonObject(CONTENTS);
                        Identifier id = Identifier.of(requiredString(contents, "id"));
                        this.verifyItem(id);
                        Integer count = optionalInt(contents, "count");
                        JsonObject components = optionalObject(contents, "components");
                        return new ItemHoverEvent(
                                id,
                                count == null ? 1 : count,
                                components == null ? null : this.codec.convertItemComponents(components)
                        );
                    } else {
                        throw new IllegalArgumentException("Expected string or json array for '" + CONTENTS + "' tag");
                    }
                case SHOW_ENTITY:
                    //If the entity is not valid an exception will be thrown
                    JsonObject contents = requiredObject(obj, CONTENTS);
                    Identifier type = Identifier.of(requiredString(contents, "type"));
                    this.codec.verifyEntity(type);
                    UUID id = this.getUUID(contents.get("id"));
                    TextComponent name;
                    if (contents.has("name")) {
                        try {
                            name = this.textSerializer.deserialize(contents.get("name"));
                        } catch (Throwable t) {
                            name = null;
                        }
                    } else {
                        name = null;
                    }
                    return new EntityHoverEvent(type, id, name);
                default:
                    throw new IllegalArgumentException("Unknown hover event action: " + action);
            }
        } else if (obj.has(VALUE)) {
            TextComponent value = this.textSerializer.deserialize(obj.get(VALUE));
            try {
                switch (action) {
                    case SHOW_TEXT:
                        return new TextHoverEvent(value);
                    case SHOW_ITEM:
                        CompoundTag parsed = this.sNbt.deserialize(value.asUnformattedString());
                        Identifier id = Identifier.of(requiredString(parsed, "id"));
                        this.verifyItem(id);
                        Integer count = optionalInt(parsed, "count");
                        CompoundTag components = optionalCompound(parsed, "components");
                        if (components != null) this.codec.verifyItemComponents(components);
                        return new ItemHoverEvent(
                                id,
                                count == null ? 1 : count,
                                components
                        );
                    case SHOW_ENTITY:
                        parsed = this.sNbt.deserialize(value.asUnformattedString());
                        TextComponent name = this.codec.deserializeJson(parsed.getString("name"));
                        Identifier type = Identifier.of(parsed.getString("type"));
                        UUID uuid = UUID.fromString(parsed.getString("id"));
                        return new EntityHoverEvent(type, uuid, name);
                    default:
                        throw new IllegalArgumentException("Unknown hover event action: " + action);
                }
            } catch (Throwable t) {
                this.sneak(t);
            }
        }

        throw new IllegalArgumentException("Missing '" + CONTENTS + "' or '" + VALUE + "' tag");
    }

    protected void verifyItem(final Identifier id) {
        this.codec.verifyItem(id);
        if (id.equals(Identifier.of("minecraft:air"))) throw new IllegalArgumentException("Item hover component id is 'minecraft:air'");
    }

}
