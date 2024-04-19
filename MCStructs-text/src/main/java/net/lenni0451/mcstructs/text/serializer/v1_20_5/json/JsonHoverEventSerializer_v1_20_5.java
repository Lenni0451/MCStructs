package net.lenni0451.mcstructs.text.serializer.v1_20_5.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.events.hover.AHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.EntityHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.TextComponentCodec_v1_20_5;

import java.util.UUID;

import static net.lenni0451.mcstructs.text.utils.CodecUtils.*;

public class JsonHoverEventSerializer_v1_20_5 implements ITypedSerializer<JsonElement, AHoverEvent> {

    private static final String ACTION = "action";
    private static final String CONTENTS = "contents";
    private static final String VALUE = "value";

    private final TextComponentCodec_v1_20_5 codec;
    private final ITypedSerializer<JsonElement, ATextComponent> textSerializer;
    private final SNbtSerializer<CompoundTag> sNbtSerializer;

    public JsonHoverEventSerializer_v1_20_5(final TextComponentCodec_v1_20_5 codec, final ITypedSerializer<JsonElement, ATextComponent> textSerializer, final SNbtSerializer<CompoundTag> sNbtSerializer) {
        this.codec = codec;
        this.textSerializer = textSerializer;
        this.sNbtSerializer = sNbtSerializer;
    }

    @Override
    public JsonElement serialize(AHoverEvent object) {
        JsonObject out = new JsonObject();
        out.addProperty(ACTION, object.getAction().getName());
        if (object instanceof TextHoverEvent) {
            TextHoverEvent textHoverEvent = (TextHoverEvent) object;
            out.add(CONTENTS, this.textSerializer.serialize(textHoverEvent.getText()));
        } else if (object instanceof ItemHoverEvent) {
            ItemHoverEvent itemHoverEvent = (ItemHoverEvent) object;
            JsonObject contents = new JsonObject();
            contents.addProperty("id", itemHoverEvent.getItem().get());
            if (itemHoverEvent.getCount() != 1) contents.addProperty("count", itemHoverEvent.getCount());
            if (itemHoverEvent.getNbt() != null) contents.add("components", this.codec.convertItemComponents(itemHoverEvent.getNbt()));
            out.add(CONTENTS, contents);
        } else if (object instanceof EntityHoverEvent) {
            EntityHoverEvent entityHoverEvent = (EntityHoverEvent) object;
            JsonObject contents = new JsonObject();
            contents.addProperty("type", entityHoverEvent.getEntityType().get());
            JsonArray id = new JsonArray();
            id.add((int) (entityHoverEvent.getUuid().getMostSignificantBits() >> 32));
            id.add((int) (entityHoverEvent.getUuid().getMostSignificantBits() & 0xFFFF_FFFFL));
            id.add((int) (entityHoverEvent.getUuid().getLeastSignificantBits() >> 32));
            id.add((int) (entityHoverEvent.getUuid().getLeastSignificantBits() & 0xFFFF_FFFFL));
            contents.add("id", id);
            if (entityHoverEvent.getName() != null) contents.add("name", this.textSerializer.serialize(entityHoverEvent.getName()));
            out.add(CONTENTS, contents);
        } else {
            throw new IllegalArgumentException("Unknown hover event type: " + object.getClass().getName());
        }
        return out;
    }

    @Override
    public AHoverEvent deserialize(JsonElement object) {
        if (!object.isJsonObject()) throw new IllegalArgumentException("Element must be a json object");
        JsonObject obj = object.getAsJsonObject();

        HoverEventAction action = HoverEventAction.getByName(requiredString(obj, ACTION), false);
        if (action == null) throw new IllegalArgumentException("Unknown hover event action: " + obj.get(ACTION).getAsString());
        if (!action.isUserDefinable()) throw new IllegalArgumentException("Hover event action is not user definable: " + action);

        if (obj.has(CONTENTS)) {
            switch (action) {
                case SHOW_TEXT:
                    return new TextHoverEvent(action, this.textSerializer.deserialize(obj.get(CONTENTS)));
                case SHOW_ITEM:
                    //If the item is not valid or air an exception will be thrown
                    if (obj.has(CONTENTS) && isString(obj.get(CONTENTS))) {
                        Identifier id = Identifier.of(obj.get(CONTENTS).getAsString());
                        this.verifyItem(id);
                        return new ItemHoverEvent(action, id, 1, null);
                    } else if (obj.has(CONTENTS) && isObject(obj.get(CONTENTS))) {
                        JsonObject contents = obj.getAsJsonObject(CONTENTS);
                        Identifier id = Identifier.of(requiredString(contents, "id"));
                        this.verifyItem(id);
                        Integer count = optionalInt(contents, "count");
                        JsonObject components = optionalObject(contents, "components");
                        return new ItemHoverEvent(
                                action,
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
                    ATextComponent name;
                    if (contents.has("name")) {
                        try {
                            name = this.textSerializer.deserialize(contents.get("name"));
                        } catch (Throwable t) {
                            name = null;
                        }
                    } else {
                        name = null;
                    }
                    return new EntityHoverEvent(action, type, id, name);

                default:
                    throw new IllegalArgumentException("Unknown hover event action: " + action);
            }
        } else if (obj.has(VALUE)) {
            ATextComponent value = this.textSerializer.deserialize(obj.get(VALUE));
            try {
                switch (action) {
                    case SHOW_TEXT:
                        return new TextHoverEvent(action, value);
                    case SHOW_ITEM:
                        CompoundTag parsed = this.sNbtSerializer.deserialize(value.asUnformattedString());
                        Identifier id = Identifier.of(requiredString(parsed, "id"));
                        this.verifyItem(id);
                        Integer count = optionalInt(parsed, "count");
                        CompoundTag components = optionalCompound(parsed, "components");
                        if (components != null) this.codec.verifyItemComponents(components);
                        return new ItemHoverEvent(
                                action,
                                id,
                                count == null ? 1 : count,
                                components
                        );
                    case SHOW_ENTITY:
                        parsed = this.sNbtSerializer.deserialize(value.asUnformattedString());
                        ATextComponent name = this.codec.deserializeJson(parsed.getString("name"));
                        Identifier type = Identifier.of(parsed.getString("type"));
                        UUID uuid = UUID.fromString(parsed.getString("id"));
                        return new EntityHoverEvent(action, type, uuid, name);

                    default:
                        throw new IllegalArgumentException("Unknown hover event action: " + action);
                }
            } catch (Throwable t) {
                this.sneak(t);
            }
        }

        throw new IllegalArgumentException("Missing '" + CONTENTS + "' or '" + VALUE + "' tag");
    }

    private void verifyItem(final Identifier id) {
        this.codec.verifyItem(id);
        if (id.equals(Identifier.of("minecraft:air"))) throw new IllegalArgumentException("Item hover component id is 'minecraft:air'");
    }

    private <T extends Throwable> void sneak(final Throwable t) throws T {
        throw (T) t;
    }

    private UUID getUUID(final JsonElement element) {
        if (element == null || (!element.isJsonArray() && (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()))) {
            throw new IllegalArgumentException("Expected json array or string for 'id' tag");
        }
        if (element.isJsonPrimitive()) {
            return UUID.fromString(element.getAsString());
        } else {
            JsonArray array = element.getAsJsonArray();
            if (array.size() != 4) throw new IllegalArgumentException("Expected json array with 4 elements for 'id' tag");
            int[] ints = new int[4];
            for (int i = 0; i < ints.length; i++) {
                JsonElement e = array.get(i);
                if (!e.isJsonPrimitive()) throw new IllegalArgumentException("Expected json primitive for array element " + i + " of 'id' tag");
                JsonPrimitive primitive = e.getAsJsonPrimitive();
                if (primitive.isNumber()) ints[i] = primitive.getAsInt();
                else if (primitive.isBoolean()) ints[i] = primitive.getAsBoolean() ? 1 : 0;
                else throw new IllegalArgumentException("Expected int for array element " + i + " of 'id' tag");
            }
            return new UUID((long) ints[0] << 32 | (long) ints[1] & 0xFFFF_FFFFL, (long) ints[2] << 32 | (long) ints[3] & 0xFFFF_FFFFL);
        }
    }

}
