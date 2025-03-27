package net.lenni0451.mcstructs.text.serializer.v1_16;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.EntityHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

import java.lang.reflect.Type;

public class HoverEventSerializer_v1_16 implements JsonSerializer<HoverEvent> {

    private final TextComponentSerializer textComponentSerializer;
    private final SNbt<?> sNbt;

    public HoverEventSerializer_v1_16(final TextComponentSerializer textComponentSerializer, final SNbt<?> sNbt) {
        this.textComponentSerializer = textComponentSerializer;
        this.sNbt = sNbt;
    }

    @Override
    public JsonElement serialize(HoverEvent src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject serializedHoverEvent = new JsonObject();

        serializedHoverEvent.addProperty("action", src.getAction().getName());
        if (src instanceof TextHoverEvent) {
            TextHoverEvent textHoverEvent = (TextHoverEvent) src;
            serializedHoverEvent.add("contents", this.textComponentSerializer.serializeJson(textHoverEvent.getText()));
        } else if (src instanceof ItemHoverEvent) {
            ItemHoverEvent itemHoverEvent = (ItemHoverEvent) src;
            JsonObject serializedItem = new JsonObject();
            serializedItem.addProperty("id", itemHoverEvent.asModern().getId().get());
            if (itemHoverEvent.asModern().getCount() != 1) serializedItem.addProperty("count", itemHoverEvent.asModern().getCount());
            if (itemHoverEvent.asModern().getTag() != null) serializedItem.addProperty("tag", this.sNbt.trySerialize(itemHoverEvent.asModern().getTag()));
            serializedHoverEvent.add("contents", serializedItem);
        } else if (src instanceof EntityHoverEvent) {
            EntityHoverEvent entityHoverEvent = (EntityHoverEvent) src;
            JsonObject serializedEntity = new JsonObject();
            serializedEntity.addProperty("type", entityHoverEvent.asModern().getType().get());
            serializedEntity.addProperty("id", entityHoverEvent.asModern().getUuid().toString());
            if (entityHoverEvent.asModern().getName() != null) serializedEntity.add("name", this.textComponentSerializer.serializeJson(entityHoverEvent.asModern().getName()));
            serializedHoverEvent.add("contents", serializedEntity);
        }

        return serializedHoverEvent;
    }

}
