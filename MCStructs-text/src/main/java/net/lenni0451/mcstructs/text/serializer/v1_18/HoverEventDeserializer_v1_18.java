package net.lenni0451.mcstructs.text.serializer.v1_18;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.EntityHoverEvent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_16.HoverEventDeserializer_v1_16;

import java.util.UUID;

public class HoverEventDeserializer_v1_18 extends HoverEventDeserializer_v1_16 {

    public HoverEventDeserializer_v1_18(final TextComponentSerializer textComponentSerializer, final SNbt<?> sNbt) {
        super(textComponentSerializer, sNbt);
    }

    protected HoverEvent deserializeLegacy(final HoverEventAction action, final TextComponent text) {
        if (action == HoverEventAction.SHOW_ENTITY) {
            try {
                CompoundTag rawEntity = (CompoundTag) this.sNbt.deserialize(text.asUnformattedString());
                TextComponent name = this.textComponentSerializer.deserialize(rawEntity.getString("name"));
                Identifier entityType = Identifier.of(rawEntity.getString("type"));
                UUID uuid = UUID.fromString(rawEntity.getString("id"));
                return new EntityHoverEvent(entityType, uuid, name);
            } catch (Exception ignored) {
                return null;
            }
        }
        return super.deserializeLegacy(action, text);
    }

}
