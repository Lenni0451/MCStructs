package net.lenni0451.mcstructs.text.serializer.v1_9;

import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.AchievementHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.LegacyHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import net.lenni0451.mcstructs.text.serializer.v1_8.StyleSerializer_v1_8;

public class StyleSerializer_v1_9 extends StyleSerializer_v1_8 {

    public StyleSerializer_v1_9(final SNbt<?> sNbt) {
        super(sNbt);
    }

    protected TextComponent serializeHoverEvent(final HoverEvent hoverEvent) {
        if (hoverEvent instanceof TextHoverEvent) {
            return ((TextHoverEvent) hoverEvent).getText();
        } else if (hoverEvent instanceof AchievementHoverEvent) {
            return new StringComponent(((AchievementHoverEvent) hoverEvent).getStatistic());
        } else if (hoverEvent instanceof LegacyHoverEvent) {
            LegacyHoverEvent legacyHoverEvent = (LegacyHoverEvent) hoverEvent;
            LegacyHoverEvent.LegacyData legacyData = legacyHoverEvent.getData();
            if (legacyData instanceof LegacyHoverEvent.LegacyInvalidData) {
                return ((LegacyHoverEvent.LegacyInvalidData) legacyData).getRaw();
            } else if (legacyData instanceof LegacyHoverEvent.LegacyStringItemData) {
                LegacyHoverEvent.LegacyStringItemData itemData = (LegacyHoverEvent.LegacyStringItemData) legacyData;
                CompoundTag itemTag = new CompoundTag();
                itemTag.addString("id", itemData.getId());
                itemTag.addByte("Count", itemData.getCount());
                itemTag.addShort("Damage", itemData.getDamage());
                if (itemData.getTag() != null) itemTag.addCompound("tag", itemData.getTag());
                return new StringComponent(this.sNbt.trySerialize(itemTag));
            } else if (legacyData instanceof LegacyHoverEvent.LegacyEntityData) {
                LegacyHoverEvent.LegacyEntityData entityData = (LegacyHoverEvent.LegacyEntityData) legacyData;
                CompoundTag entityTag = new CompoundTag();
                entityTag.addString("name", entityData.getName());
                if (entityData.getType() != null) entityTag.addString("type", entityData.getType());
                entityTag.addString("id", entityData.getId());
                return new StringComponent(this.sNbt.trySerialize(entityTag));
            }
        }
        throw new IllegalArgumentException("Unknown hover event type: " + hoverEvent.getClass().getName());
    }

}
