package net.lenni0451.mcstructs.text.serializer.legacy;

import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.AchievementHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.EntityHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;

import java.util.function.Predicate;

public class HoverEventSerializer<T extends HoverEvent> extends EventSerializer<HoverEvent, T, HoverEventAction, TextComponent> {

    public static final HoverEventSerializer<TextHoverEvent> TEXT = createBasic(
            TextHoverEvent.class::isInstance,
            TextHoverEvent::getText,
            HoverEventAction.SHOW_TEXT,
            TextHoverEvent::new
    );
    public static final HoverEventSerializer<AchievementHoverEvent> ACHIEVEMENT = createBasic(
            AchievementHoverEvent.class::isInstance,
            hoverEvent -> new StringComponent(hoverEvent.getStatistic()),
            HoverEventAction.SHOW_ACHIEVEMENT,
            statistic -> new AchievementHoverEvent(statistic.asUnformattedString())
    );
    public static final HoverEventSerializer<ItemHoverEvent> LEGACY_RAW_ITEM = createBasic(
            hoverEvent -> hoverEvent instanceof ItemHoverEvent && ((ItemHoverEvent) hoverEvent).isLegacyRaw(),
            hoverEvent -> {
                ItemHoverEvent.LegacyRawHolder itemData = (ItemHoverEvent.LegacyRawHolder) hoverEvent.getData();
                return new StringComponent(itemData.getData());
            },
            HoverEventAction.SHOW_ITEM,
            value -> new ItemHoverEvent(value.asUnformattedString())
    );
    public static final HoverEventSerializer<ItemHoverEvent> LEGACY_INT_ITEM = createSNbt(
            hoverEvent -> hoverEvent instanceof ItemHoverEvent && ((ItemHoverEvent) hoverEvent).isLegacyShort(),
            (sNbt, hoverEvent) -> {
                ItemHoverEvent.LegacyShortHolder itemData = (ItemHoverEvent.LegacyShortHolder) hoverEvent.getData();
                CompoundTag itemTag = new CompoundTag();
                itemTag.addShort("id", itemData.getId());
                itemTag.addByte("Count", itemData.getCount());
                itemTag.addShort("Damage", itemData.getDamage());
                if (itemData.getTag() != null) itemTag.addCompound("tag", itemData.getTag());
                return new StringComponent(sNbt.trySerialize(itemTag));
            },
            HoverEventAction.SHOW_ITEM,
            (sNbt, value) -> {
                NbtTag tag = sNbt.deserialize(value.asUnformattedString());
                if (tag.isCompoundTag()) {
                    CompoundTag compoundTag = tag.asCompoundTag();
                    short itemId = compoundTag.getShort("id");
                    byte itemCount = compoundTag.getByte("Count");
                    short itemDamage = compoundTag.getShort("Damage");
                    if (itemDamage < 0) itemDamage = 0;
                    CompoundTag itemTag = compoundTag.getCompound("tag", null);

                    return new ItemHoverEvent(itemId, itemCount, itemDamage, itemTag);
                }
                throw new UnsupportedOperationException();
            }
    );
    public static final HoverEventSerializer<ItemHoverEvent> LEGACY_STRING_ITEM = createSNbt(
            hoverEvent -> hoverEvent instanceof ItemHoverEvent && ((ItemHoverEvent) hoverEvent).isLegacyString(),
            (sNbt, hoverEvent) -> {
                ItemHoverEvent.LegacyStringHolder itemData = (ItemHoverEvent.LegacyStringHolder) hoverEvent.getData();
                CompoundTag itemTag = new CompoundTag();
                itemTag.addString("id", itemData.getId());
                itemTag.addByte("Count", itemData.getCount());
                itemTag.addShort("Damage", itemData.getDamage());
                if (itemData.getTag() != null) itemTag.addCompound("tag", itemData.getTag());
                return new StringComponent(sNbt.trySerialize(itemTag));
            },
            HoverEventAction.SHOW_ITEM,
            (sNbt, value) -> {
                NbtTag tag = sNbt.deserialize(value.asUnformattedString());
                if (tag.isCompoundTag()) {
                    CompoundTag compoundTag = tag.asCompoundTag();
                    String itemId = compoundTag.getString("id");
                    byte itemCount = compoundTag.getByte("Count");
                    short itemDamage = compoundTag.getShort("Damage");
                    if (itemDamage < 0) itemDamage = 0;
                    CompoundTag itemTag = compoundTag.getCompound("tag", null);

                    return new ItemHoverEvent(itemId, itemCount, itemDamage, itemTag);
                }
                throw new UnsupportedOperationException();
            }
    );
    public static final HoverEventSerializer<ItemHoverEvent> LEGACY_INT_OR_STRING_ITEM = createSNbt(
            hoverEvent -> hoverEvent instanceof ItemHoverEvent && (((ItemHoverEvent) hoverEvent).isLegacyShort() || ((ItemHoverEvent) hoverEvent).isLegacyString()),
            (sNbt, hoverEvent) -> {
                ItemHoverEvent.DataHolder legacyData = hoverEvent.getData();
                if (legacyData instanceof ItemHoverEvent.LegacyShortHolder) {
                    return LEGACY_INT_ITEM.serialize(sNbt, hoverEvent);
                } else if (legacyData instanceof ItemHoverEvent.LegacyStringHolder) {
                    return LEGACY_STRING_ITEM.serialize(sNbt, hoverEvent);
                }
                throw new UnsupportedOperationException();
            },
            HoverEventAction.SHOW_ITEM,
            (sNbt, value) -> {
                NbtTag tag = sNbt.deserialize(value.asUnformattedString());
                if (tag.isCompoundTag()) {
                    CompoundTag compoundTag = tag.asCompoundTag();
                    if (compoundTag.contains("id", NbtType.STRING)) {
                        return LEGACY_STRING_ITEM.deserialize(sNbt, value);
                    } else {
                        return LEGACY_INT_ITEM.deserialize(sNbt, value);
                    }
                }
                throw new UnsupportedOperationException();
            }
    );
    public static final HoverEventSerializer<EntityHoverEvent> LEGACY_RAW_ENTITY = createBasic(
            hoverEvent -> hoverEvent instanceof EntityHoverEvent && ((EntityHoverEvent) hoverEvent).getData() instanceof EntityHoverEvent.LegacyRawHolder,
            hoverEvent -> ((EntityHoverEvent.LegacyRawHolder) hoverEvent.getData()).getData(),
            HoverEventAction.SHOW_ENTITY,
            EntityHoverEvent::new
    );
    public static final HoverEventSerializer<EntityHoverEvent> LEGACY_ENTITY = createSNbt(
            hoverEvent -> hoverEvent instanceof EntityHoverEvent && ((EntityHoverEvent) hoverEvent).getData() instanceof EntityHoverEvent.LegacyHolder,
            (sNbt, hoverEvent) -> {
                EntityHoverEvent.LegacyHolder entityData = (EntityHoverEvent.LegacyHolder) hoverEvent.getData();
                CompoundTag entityTag = new CompoundTag();
                entityTag.addString("name", entityData.getName());
                if (entityData.getType() != null) entityTag.addString("type", entityData.getType());
                entityTag.addString("id", entityData.getId());
                return new StringComponent(sNbt.trySerialize(entityTag));
            },
            HoverEventAction.SHOW_ENTITY,
            (sNbt, value) -> {
                try {
                    NbtTag tag = sNbt.deserialize(value.asUnformattedString());
                    if (tag.isCompoundTag()) {
                        CompoundTag compoundTag = tag.asCompoundTag();
                        String entityName = compoundTag.getString("name");
                        String entityType = compoundTag.getString("type", null);
                        String entityId = compoundTag.getString("id");

                        return new EntityHoverEvent(entityType, entityId, entityName);
                    }
                } catch (Throwable ignored) {
                }
                throw new UnsupportedOperationException();
            }
    );

    private static <T extends HoverEvent> HoverEventSerializer<T> createBasic(final Predicate<HoverEvent> classMatcher, final BasicIOFunction<T, TextComponent> serializer, final HoverEventAction action, final BasicIOFunction<TextComponent, T> deserializer) {
        return new HoverEventSerializer<>(classMatcher, serializer, action, deserializer);
    }

    private static <T extends HoverEvent> HoverEventSerializer<T> createSNbt(final Predicate<HoverEvent> classMatcher, final IOFunction<T, TextComponent> serializer, final HoverEventAction action, final IOFunction<TextComponent, T> deserializer) {
        return new HoverEventSerializer<>(classMatcher, serializer, action, deserializer);
    }

    protected HoverEventSerializer(final Predicate<HoverEvent> classMatcher, final IOFunction<T, TextComponent> serializer, final HoverEventAction action, final IOFunction<TextComponent, T> deserializer) {
        super(classMatcher, serializer, action, deserializer);
    }

}
