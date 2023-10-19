package net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.events.click.ClickEvent;
import net.lenni0451.mcstructs.text.events.click.ClickEventAction;
import net.lenni0451.mcstructs.text.events.hover.AHoverEvent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;

public class NbtStyleSerializer_v1_20_3 implements ITypedSerializer<INbtTag, Style> {

    private final ITypedSerializer<INbtTag, ATextComponent> textSerializer;
    private final ITypedSerializer<INbtTag, AHoverEvent> hoverEventSerializer;

    public NbtStyleSerializer_v1_20_3(final ITypedSerializer<INbtTag, ATextComponent> textSerializer) {
        this.textSerializer = textSerializer;
        this.hoverEventSerializer = new NbtHoverEventSerializer_v1_20_3(textSerializer);
    }

    @Override
    public INbtTag serialize(Style object) {
        return null;
    }

    @Override
    public Style deserialize(INbtTag object) {
        if (!object.isCompoundTag()) throw new IllegalArgumentException("Nbt tag is not a compound tag");
        CompoundTag tag = object.asCompoundTag();

        Style style = new Style();
        if (tag.contains("color")) {
            if (!tag.contains("color", NbtType.STRING)) throw new IllegalArgumentException("Expected string tag for 'color' tag");
            TextFormatting formatting = TextFormatting.parse(tag.getString("color"));
            if (formatting == null) throw new IllegalArgumentException("Unknown color: " + tag.getString("color"));
            if (formatting.isRGBColor() && (formatting.getRgbValue() < 0 || formatting.getRgbValue() > 0xFFFFFF)) {
                throw new IllegalArgumentException("Out of bounds RGB color: " + formatting.getRgbValue());
            }
        }
        style.setBold(this.optionalBoolean(tag, "bold"));
        style.setItalic(this.optionalBoolean(tag, "italic"));
        style.setUnderlined(this.optionalBoolean(tag, "underlined"));
        style.setStrikethrough(this.optionalBoolean(tag, "strikethrough"));
        style.setObfuscated(this.optionalBoolean(tag, "obfuscated"));
        if (tag.contains("clickEvent")) {
            if (!tag.contains("clickEvent", NbtType.COMPOUND)) throw new IllegalArgumentException("Expected compound tag for 'clickEvent' tag");
            CompoundTag clickEvent = tag.getCompound("clickEvent");
            if (!clickEvent.contains("action", NbtType.STRING)) throw new IllegalArgumentException("Expected string tag for 'action' tag");
            ClickEventAction action = ClickEventAction.getByName(clickEvent.getString("action"), false);
            if (action == null || ClickEventAction.TWITCH_USER_INFO.equals(action)) {
                throw new IllegalArgumentException("Unknown click event action: " + clickEvent.getString("action"));
            }
            if (!action.isUserDefinable()) throw new IllegalArgumentException("Click event action is not user definable: " + action);
            if (!clickEvent.contains("value", NbtType.STRING)) throw new IllegalArgumentException("Expected string tag for 'value' tag");
            style.setClickEvent(new ClickEvent(action, clickEvent.getString("value")));
        }
        if (tag.contains("hoverEvent")) {
            if (!tag.contains("hoverEvent", NbtType.COMPOUND)) throw new IllegalArgumentException("Expected compound tag for 'hoverEvent' tag");
            style.setHoverEvent(this.hoverEventSerializer.deserialize(tag.getCompound("hoverEvent")));
        }
        if (tag.contains("insertion")) {
            if (!tag.contains("insertion", NbtType.STRING)) throw new IllegalArgumentException("Expected string tag for 'insertion' tag");
            style.setInsertion(tag.getString("insertion"));
        }
        if (tag.contains("font")) {
            if (!tag.contains("font", NbtType.STRING)) throw new IllegalArgumentException("Expected string tag for 'font' tag");
            style.setFont(Identifier.of(tag.getString("font")));
        }
        return style;
    }

    private Boolean optionalBoolean(final CompoundTag tag, final String name) {
        if (tag.contains(name)) {
            if (!tag.contains(name, NbtType.BYTE)) throw new IllegalArgumentException("Expected byte tag for '" + name + "' tag");
            return tag.getByte(name) != 0;
        }
        return null;
    }

}
