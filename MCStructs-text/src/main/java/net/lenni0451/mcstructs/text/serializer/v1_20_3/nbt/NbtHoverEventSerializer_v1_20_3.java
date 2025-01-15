package net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.IntArrayTag;
import net.lenni0451.mcstructs.nbt.tags.ListTag;
import net.lenni0451.mcstructs.nbt.tags.StringTag;
import net.lenni0451.mcstructs.snbt.SNbt;
import net.lenni0451.mcstructs.snbt.exceptions.SNbtSerializeException;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.EntityHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.CodecUtils_v1_20_3;

import java.util.List;
import java.util.UUID;

public class NbtHoverEventSerializer_v1_20_3 implements ITypedSerializer<NbtTag, HoverEvent>, CodecUtils_v1_20_3 {

    private static final String ACTION = "action";
    private static final String CONTENTS = "contents";
    private static final String VALUE = "value";

    private final TextComponentCodec codec;
    private final ITypedSerializer<NbtTag, TextComponent> textSerializer;
    private final SNbt<CompoundTag> sNbt;

    public NbtHoverEventSerializer_v1_20_3(final TextComponentCodec codec, final ITypedSerializer<NbtTag, TextComponent> textSerializer, final SNbt<CompoundTag> sNbt) {
        this.codec = codec;
        this.textSerializer = textSerializer;
        this.sNbt = sNbt;
    }

    @Override
    public NbtTag serialize(HoverEvent object) {
        CompoundTag out = new CompoundTag();
        out.addString(ACTION, object.getAction().getName());
        if (object instanceof TextHoverEvent) {
            TextHoverEvent textHoverEvent = (TextHoverEvent) object;
            out.add(CONTENTS, this.textSerializer.serialize(textHoverEvent.getText()));
        } else if (object instanceof ItemHoverEvent) {
            ItemHoverEvent itemHoverEvent = (ItemHoverEvent) object;
            CompoundTag contents = new CompoundTag();
            contents.addString("id", itemHoverEvent.asModernHolder().getId().get());
            if (itemHoverEvent.asModernHolder().getCount() != 1) contents.addInt("count", itemHoverEvent.asModernHolder().getCount());
            if (itemHoverEvent.asModernHolder().getTag() != null) {
                try {
                    contents.addString("tag", this.sNbt.serialize(itemHoverEvent.asModernHolder().getTag()));
                } catch (SNbtSerializeException e) {
                    throw new IllegalStateException("Failed to serialize nbt", e);
                }
            }
            out.add(CONTENTS, contents);
        } else if (object instanceof EntityHoverEvent) {
            EntityHoverEvent entityHoverEvent = (EntityHoverEvent) object;
            CompoundTag contents = new CompoundTag();
            contents.addString("type", entityHoverEvent.asModernHolder().getType().get());
            contents.add("id", new IntArrayTag(new int[]{
                    (int) (entityHoverEvent.asModernHolder().getUuid().getMostSignificantBits() >> 32),
                    (int) (entityHoverEvent.asModernHolder().getUuid().getMostSignificantBits() & 0xFFFF_FFFFL),
                    (int) (entityHoverEvent.asModernHolder().getUuid().getLeastSignificantBits() >> 32),
                    (int) (entityHoverEvent.asModernHolder().getUuid().getLeastSignificantBits() & 0xFFFF_FFFFL)
            }));
            if (entityHoverEvent.asModernHolder().getName() != null) contents.add("name", this.textSerializer.serialize(entityHoverEvent.asModernHolder().getName()));
            out.add(CONTENTS, contents);
        } else {
            throw new IllegalArgumentException("Unknown hover event type: " + object.getClass().getName());
        }
        return out;
    }

    @Override
    public HoverEvent deserialize(NbtTag object) {
        if (!object.isCompoundTag()) throw new IllegalArgumentException("Nbt tag is not a compound tag");
        CompoundTag tag = object.asCompoundTag();

        HoverEventAction action = HoverEventAction.byName(requiredString(tag, ACTION), false);
        if (action == null) throw new IllegalArgumentException("Unknown hover event action: " + tag.getString(ACTION));
        if (!action.isUserDefinable()) throw new IllegalArgumentException("Hover event action is not user definable: " + action);

        if (tag.contains(CONTENTS)) {
            switch (action) {
                case SHOW_TEXT:
                    return new TextHoverEvent(this.textSerializer.deserialize(tag.get(CONTENTS)));
                case SHOW_ITEM:
                    //The item id does not have to be a valid item. Minecraft defaults to air if the item is invalid
                    if (tag.contains(CONTENTS, NbtType.STRING)) {
                        return new ItemHoverEvent(Identifier.of(tag.getString(CONTENTS)), 1, null);
                    } else if (tag.contains(CONTENTS, NbtType.COMPOUND)) {
                        CompoundTag contents = tag.getCompound(CONTENTS);
                        String id = requiredString(contents, "id");
                        Integer count = optionalInt(contents, "count");
                        String itemTag = optionalString(contents, "tag");
                        try {
                            return new ItemHoverEvent(
                                    Identifier.of(id),
                                    count == null ? 1 : count,
                                    itemTag == null ? null : this.sNbt.deserialize(itemTag)
                            );
                        } catch (Throwable t) {
                            this.sneak(t);
                        }
                    } else {
                        throw new IllegalArgumentException("Expected string or compound tag for '" + CONTENTS + "' tag");
                    }
                case SHOW_ENTITY:
                    CompoundTag contents = requiredCompound(tag, CONTENTS);
                    Identifier type = Identifier.of(requiredString(contents, "type"));
                    UUID id = this.getUUID(contents.get("id"));
                    TextComponent name = contents.contains("name") ? this.textSerializer.deserialize(contents.get("name")) : null;
                    return new EntityHoverEvent(type, id, name);
                default:
                    throw new IllegalArgumentException("Unknown hover event action: " + action);
            }
        } else if (tag.contains(VALUE)) {
            TextComponent value = this.textSerializer.deserialize(tag.get(VALUE));
            try {
                switch (action) {
                    case SHOW_TEXT:
                        return new TextHoverEvent(value);
                    case SHOW_ITEM:
                        CompoundTag parsed = this.sNbt.deserialize(value.asUnformattedString());
                        Identifier id = Identifier.of(parsed.getString("id"));
                        int count = parsed.getByte("Count");
                        CompoundTag itemTag = parsed.getCompound("tag", null);
                        return new ItemHoverEvent(id, count, itemTag);
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

    protected <T extends Throwable> void sneak(final Throwable t) throws T {
        throw (T) t;
    }

    protected UUID getUUID(final NbtTag tag) {
        if (!(tag instanceof IntArrayTag) && !(tag instanceof ListTag) && !(tag instanceof StringTag)) {
            throw new IllegalArgumentException("Expected int array, list or string tag for 'id' tag");
        }
        int[] value;
        if (tag instanceof StringTag) {
            return UUID.fromString(tag.asStringTag().getValue());
        } else if (tag instanceof IntArrayTag) {
            value = tag.asIntArrayTag().getValue();
            if (value.length != 4) throw new IllegalArgumentException("Expected int array with 4 values for 'id' tag");
        } else {
            ListTag<?> list = tag.asListTag();
            if (list.size() != 4) throw new IllegalArgumentException("Expected list with 4 values for 'id' tag");
            if (!list.getType().isNumber()) throw new IllegalArgumentException("Expected list with number values for 'id' tag");
            List<NbtTag> values = unwrapMarkers(list);
            value = new int[4];
            for (int i = 0; i < 4; i++) {
                value[i] = values.get(i).asNumberTag().intValue();
            }
        }
        return new UUID((long) value[0] << 32 | (long) value[1] & 0xFFFF_FFFFL, (long) value[2] << 32 | (long) value[3] & 0xFFFF_FFFFL);
    }

}
