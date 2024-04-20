package net.lenni0451.mcstructs.text.serializer.v1_20_5.nbt;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.IntArrayTag;
import net.lenni0451.mcstructs.snbt.SNbtSerializer;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.events.hover.AHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.HoverEventAction;
import net.lenni0451.mcstructs.text.events.hover.impl.EntityHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.ItemHoverEvent;
import net.lenni0451.mcstructs.text.events.hover.impl.TextHoverEvent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt.NbtHoverEventSerializer_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_20_5.TextComponentCodec_v1_20_5;

import java.util.UUID;

public class NbtHoverEventSerializer_v1_20_5 extends NbtHoverEventSerializer_v1_20_3 {

    private static final String ACTION = "action";
    private static final String CONTENTS = "contents";
    private static final String VALUE = "value";

    private final TextComponentCodec_v1_20_5 codec;
    private final ITypedSerializer<INbtTag, ATextComponent> textSerializer;
    private final SNbtSerializer<CompoundTag> sNbtSerializer;

    public NbtHoverEventSerializer_v1_20_5(final TextComponentCodec_v1_20_5 codec, final ITypedSerializer<INbtTag, ATextComponent> textSerializer, final SNbtSerializer<CompoundTag> sNbtSerializer) {
        super(codec, textSerializer, sNbtSerializer);
        this.codec = codec;
        this.textSerializer = textSerializer;
        this.sNbtSerializer = sNbtSerializer;
    }

    @Override
    public INbtTag serialize(AHoverEvent object) {
        CompoundTag out = new CompoundTag();
        out.addString(ACTION, object.getAction().getName());
        if (object instanceof TextHoverEvent) {
            TextHoverEvent textHoverEvent = (TextHoverEvent) object;
            out.add(CONTENTS, this.textSerializer.serialize(textHoverEvent.getText()));
        } else if (object instanceof ItemHoverEvent) {
            ItemHoverEvent itemHoverEvent = (ItemHoverEvent) object;
            CompoundTag contents = new CompoundTag();
            contents.addString("id", itemHoverEvent.getItem().get());
            if (itemHoverEvent.getCount() != 1) contents.addInt("count", itemHoverEvent.getCount());
            if (itemHoverEvent.getNbt() != null) contents.add("components", itemHoverEvent.getNbt());
            out.add(CONTENTS, contents);
        } else if (object instanceof EntityHoverEvent) {
            EntityHoverEvent entityHoverEvent = (EntityHoverEvent) object;
            CompoundTag contents = new CompoundTag();
            contents.addString("type", entityHoverEvent.getEntityType().get());
            contents.add("id", new IntArrayTag(new int[]{
                    (int) (entityHoverEvent.getUuid().getMostSignificantBits() >> 32),
                    (int) (entityHoverEvent.getUuid().getMostSignificantBits() & 0xFFFF_FFFFL),
                    (int) (entityHoverEvent.getUuid().getLeastSignificantBits() >> 32),
                    (int) (entityHoverEvent.getUuid().getLeastSignificantBits() & 0xFFFF_FFFFL)
            }));
            if (entityHoverEvent.getName() != null) contents.add("name", this.textSerializer.serialize(entityHoverEvent.getName()));
            out.add(CONTENTS, contents);
        } else {
            throw new IllegalArgumentException("Unknown hover event type: " + object.getClass().getName());
        }
        return out;
    }

    @Override
    public AHoverEvent deserialize(INbtTag object) {
        if (!object.isCompoundTag()) throw new IllegalArgumentException("Nbt tag is not a compound tag");
        CompoundTag tag = object.asCompoundTag();

        HoverEventAction action = HoverEventAction.getByName(requiredString(tag, ACTION), false);
        if (action == null) throw new IllegalArgumentException("Unknown hover event action: " + tag.getString(ACTION));
        if (!action.isUserDefinable()) throw new IllegalArgumentException("Hover event action is not user definable: " + action);

        if (tag.contains(CONTENTS)) {
            switch (action) {
                case SHOW_TEXT:
                    return new TextHoverEvent(action, this.textSerializer.deserialize(tag.get(CONTENTS)));
                case SHOW_ITEM:
                    //If the item is not valid or air an exception will be thrown
                    if (tag.contains(CONTENTS, NbtType.STRING)) {
                        Identifier id = Identifier.of(tag.getString(CONTENTS));
                        this.verifyItem(id);
                        return new ItemHoverEvent(action, id, 1, null);
                    } else if (tag.contains(CONTENTS, NbtType.COMPOUND)) {
                        return this.parseItemHoverEvent(action, tag.getCompound(CONTENTS));
                    } else {
                        throw new IllegalArgumentException("Expected string or compound tag for '" + CONTENTS + "' tag");
                    }
                case SHOW_ENTITY:
                    //If the entity is not valid an exception will be thrown
                    CompoundTag contents = requiredCompound(tag, CONTENTS);
                    Identifier type = Identifier.of(requiredString(contents, "type"));
                    this.codec.verifyEntity(type);
                    UUID id = this.getUUID(contents.get("id"));
                    ATextComponent name;
                    if (contents.contains("name")) {
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
        } else if (tag.contains(VALUE)) {
            ATextComponent value = this.textSerializer.deserialize(tag.get(VALUE));
            try {
                switch (action) {
                    case SHOW_TEXT:
                        return new TextHoverEvent(action, value);
                    case SHOW_ITEM:
                        return this.parseItemHoverEvent(action, this.sNbtSerializer.deserialize(value.asUnformattedString()));
                    case SHOW_ENTITY:
                        CompoundTag parsed = this.sNbtSerializer.deserialize(value.asUnformattedString());
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

    protected ItemHoverEvent parseItemHoverEvent(final HoverEventAction action, final CompoundTag tag) {
        Identifier id = Identifier.of(requiredString(tag, "id"));
        this.verifyItem(id);
        Integer count = optionalInt(tag, "count");
        CompoundTag components = optionalCompound(tag, "components");
        if (components != null) this.codec.verifyItemComponents(components);
        return new ItemHoverEvent(
                action,
                id,
                count == null ? 1 : count,
                components
        );
    }

    protected void verifyItem(final Identifier id) {
        this.codec.verifyItem(id);
        if (id.equals(Identifier.of("minecraft:air"))) throw new IllegalArgumentException("Item hover component id is 'minecraft:air'");
    }

}
