package net.lenni0451.mcstructs.text.serializer.v1_9;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.LegacyHoverEvent;
import net.lenni0451.mcstructs.text.serializer.v1_8.StyleDeserializer_v1_8;

public class StyleDeserializer_v1_9 extends StyleDeserializer_v1_8 {

    public StyleDeserializer_v1_9(final SNbt<?> sNbt) {
        super(sNbt);
    }

    protected HoverEvent deserializeHoverEvent(final HoverEventAction action, final TextComponent value) {
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
                        String itemId = compoundTag.getString("id");
                        byte itemCount = compoundTag.getByte("Count");
                        short itemDamage = compoundTag.getShort("Damage");
                        if (itemDamage < 0) itemDamage = 0;
                        CompoundTag itemTag = compoundTag.getCompound("tag", null);

                        return new LegacyHoverEvent(action, new LegacyHoverEvent.LegacyStringItemData(itemId, itemCount, itemDamage, itemTag));
                    }
                } catch (Throwable ignored) {
                }
                return new LegacyHoverEvent(action, new LegacyHoverEvent.LegacyInvalidData(value));
            case SHOW_ENTITY:
                try {
                    NbtTag tag = this.sNbt.deserialize(value.asUnformattedString());
                    if (tag.isCompoundTag()) {
                        CompoundTag compoundTag = tag.asCompoundTag();
                        String entityName = compoundTag.getString("name");
                        String entityType = compoundTag.getString("type", null);
                        String entityId = compoundTag.getString("id");

                        return new LegacyHoverEvent(action, new LegacyHoverEvent.LegacyEntityData(entityName, entityType, entityId));
                    }
                } catch (Throwable ignored) {
                }
                return new LegacyHoverEvent(action, new LegacyHoverEvent.LegacyInvalidData(value));
            default:
                throw new IllegalArgumentException("Unknown hover event action: " + action.getName());
        }
    }

}
