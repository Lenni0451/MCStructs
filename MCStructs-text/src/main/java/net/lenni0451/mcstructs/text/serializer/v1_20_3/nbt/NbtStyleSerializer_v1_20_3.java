package net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;
import net.lenni0451.mcstructs.text.events.hover.AHoverEvent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;
import net.lenni0451.mcstructs.text.serializer.subtypes.IStyleSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.CodecUtils_v1_20_3;

import java.util.function.Function;

public class NbtStyleSerializer_v1_20_3 implements IStyleSerializer<INbtTag>, CodecUtils_v1_20_3 {

    private final ITypedSerializer<INbtTag, AHoverEvent> hoverEventSerializer;

    public NbtStyleSerializer_v1_20_3(final Function<NbtStyleSerializer_v1_20_3, ITypedSerializer<INbtTag, AHoverEvent>> hoverEventSerializer) {
        this.hoverEventSerializer = hoverEventSerializer.apply(this);
    }

    @Override
    public INbtTag serialize(Style object) {
        CompoundTag out = new CompoundTag();
        if (object.getColor() != null) out.addString("color", object.getColor().serialize());
        if (object.getBold() != null) out.addBoolean("bold", object.isBold());
        if (object.getItalic() != null) out.addBoolean("italic", object.isItalic());
        if (object.getUnderlined() != null) out.addBoolean("underlined", object.isUnderlined());
        if (object.getStrikethrough() != null) out.addBoolean("strikethrough", object.isStrikethrough());
        if (object.getObfuscated() != null) out.addBoolean("obfuscated", object.isObfuscated());
        if (object.getClickEvent() != null) {
            CompoundTag clickEvent = new CompoundTag();
            clickEvent.addString("action", object.getClickEvent().getAction().getName());
            clickEvent.addString("value", object.getClickEvent().getValue());
            out.add("clickEvent", clickEvent);
        }
        if (object.getHoverEvent() != null) out.add("hoverEvent", this.hoverEventSerializer.serialize(object.getHoverEvent()));
        if (object.getInsertion() != null) out.addString("insertion", object.getInsertion());
        if (object.getFont() != null) out.addString("font", object.getFont().get());
        return out;
    }

    @Override
    public Style deserialize(INbtTag object) {
        if (!object.isCompoundTag()) throw new IllegalArgumentException("Nbt tag is not a compound tag");
        CompoundTag tag = object.asCompoundTag();

        Style style = new Style();
        if (tag.contains("color")) {
            String color = requiredString(tag, "color");
            TextFormatting formatting = TextFormatting.parse(color);
            if (formatting == null) throw new IllegalArgumentException("Unknown color: " + color);
            if (formatting.isRGBColor() && (formatting.getRgbValue() < 0 || formatting.getRgbValue() > 0xFFFFFF)) {
                throw new IllegalArgumentException("Out of bounds RGB color: " + formatting.getRgbValue());
            }
            style.setFormatting(formatting);
        }
        style.setBold(optionalBoolean(tag, "bold"));
        style.setItalic(optionalBoolean(tag, "italic"));
        style.setUnderlined(optionalBoolean(tag, "underlined"));
        style.setStrikethrough(optionalBoolean(tag, "strikethrough"));
        style.setObfuscated(optionalBoolean(tag, "obfuscated"));
        if (tag.contains("clickEvent")) {
            CompoundTag clickEvent = requiredCompound(tag, "clickEvent");
            ClickEventAction action = ClickEventAction.getByName(requiredString(clickEvent, "action"), false);
            if (action == null || ClickEventAction.TWITCH_USER_INFO.equals(action)) {
                throw new IllegalArgumentException("Unknown click event action: " + clickEvent.getString("action"));
            }
            if (!action.isUserDefinable()) throw new IllegalArgumentException("Click event action is not user definable: " + action);
            style.setClickEvent(new ClickEvent(action, requiredString(clickEvent, "value")));
        }
        if (tag.contains("hoverEvent")) style.setHoverEvent(this.hoverEventSerializer.deserialize(requiredCompound(tag, "hoverEvent")));
        style.setInsertion(optionalString(tag, "insertion"));
        if (tag.contains("font")) style.setFont(Identifier.of(requiredString(tag, "font")));
        return style;
    }

}
