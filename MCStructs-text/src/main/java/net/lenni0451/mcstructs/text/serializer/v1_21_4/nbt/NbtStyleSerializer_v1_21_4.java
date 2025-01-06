package net.lenni0451.mcstructs.text.serializer.v1_21_4.nbt;

import net.lenni0451.mcstructs.nbt.NbtNumber;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.Style;
import net.lenni0451.mcstructs.text.events.hover.HoverEvent;
import net.lenni0451.mcstructs.text.serializer.ITypedSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt.NbtStyleSerializer_v1_20_3;

import java.util.List;
import java.util.function.Function;

public class NbtStyleSerializer_v1_21_4 extends NbtStyleSerializer_v1_20_3 {

    public NbtStyleSerializer_v1_21_4(final Function<NbtStyleSerializer_v1_20_3, ITypedSerializer<NbtTag, HoverEvent>> hoverEventSerializer) {
        super(hoverEventSerializer);
    }

    @Override
    public NbtTag serialize(Style object) {
        CompoundTag out = super.serialize(object).asCompoundTag();
        if (object.getShadowColor() != null) out.addInt("shadow_color", object.getShadowColor());
        return out;
    }

    @Override
    public Style deserialize(NbtTag object) {
        Style style = super.deserialize(object);
        CompoundTag tag = object.asCompoundTag();
        if (tag.contains("shadow_color")) style.setShadowColor(this.getShadowColor(tag.get("shadow_color")));
        return style;
    }

    protected int getShadowColor(final NbtTag tag) {
        if (tag instanceof NbtNumber) {
            return tag.asNumberTag().intValue();
        } else {
            List<Number> numbers = this.asNumberStream(tag);
            if (numbers.size() != 4) throw new IllegalArgumentException("Expected list with 4 values for 'shadow_color' tag");
            //For some reason the stream is in RGBA order
            int r = (int) Math.floor(numbers.get(0).floatValue() * 255);
            int g = (int) Math.floor(numbers.get(1).floatValue() * 255);
            int b = (int) Math.floor(numbers.get(2).floatValue() * 255);
            int a = (int) Math.floor(numbers.get(3).floatValue() * 255);
            return (a << 24) | (r << 16) | (g << 8) | b;
        }
    }

}
