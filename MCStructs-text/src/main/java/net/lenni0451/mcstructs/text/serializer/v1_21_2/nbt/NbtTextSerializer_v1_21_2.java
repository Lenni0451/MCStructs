package net.lenni0451.mcstructs.text.serializer.v1_21_2.nbt;

import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.components.SelectorComponent;
import net.lenni0451.mcstructs.text.serializer.subtypes.IStyleSerializer;
import net.lenni0451.mcstructs.text.serializer.v1_20_3.nbt.NbtTextSerializer_v1_20_3;
import net.lenni0451.mcstructs.text.serializer.v1_21_2.TextComponentCodec_v1_21_2;

import java.util.function.Function;

public class NbtTextSerializer_v1_21_2 extends NbtTextSerializer_v1_20_3 {

    private final TextComponentCodec_v1_21_2 codec;

    public NbtTextSerializer_v1_21_2(final TextComponentCodec_v1_21_2 codec, final Function<NbtTextSerializer_v1_20_3, IStyleSerializer<INbtTag>> styleSerializer) {
        super(styleSerializer);
        this.codec = codec;
    }

    @Override
    public ATextComponent deserialize(INbtTag object) {
        ATextComponent textComponent = super.deserialize(object);
        if (textComponent instanceof SelectorComponent) {
            SelectorComponent selectorComponent = (SelectorComponent) textComponent;
            this.codec.verifyEntitySelector(selectorComponent.getSelector());
        }
        return textComponent;
    }

}
