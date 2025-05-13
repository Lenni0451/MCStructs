package net.lenni0451.mcstructs.itemcomponents.impl.v1_22;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5.TypeSerializers_v1_21_5;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.EnumMap;
import java.util.Map;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_22.Types_v1_22.AttributeModifier;

public class TypeSerializers_v1_22 extends TypeSerializers_v1_21_5 {

    public TypeSerializers_v1_22(final ItemComponentRegistry registry, final TextComponentCodec textComponentCodec) {
        super(registry, textComponentCodec);
    }

    @Override
    @Deprecated
    public Codec<Types_v1_21.AttributeModifier> attributeModifier_v1_21() {
        throw new UnsupportedOperationException();
    }

    public Codec<AttributeModifier> attributeModifier_v1_22() {
        return this.init(ATTRIBUTE_MODIFIER, () -> {
            Map<AttributeModifier.Display.Type, MapCodec<? extends AttributeModifier.Display>> codecs = new EnumMap<>(AttributeModifier.Display.Type.class);
            codecs.put(AttributeModifier.Display.Type.DEFAULT, MapCodec.unit(AttributeModifier.Display.Default::new));
            return MapCodecMerger.codec(
                    this.registry.getRegistries().attributeModifier.entryCodec().mapCodec(AttributeModifier.TYPE).required(), AttributeModifier::getType,
                    MapCodecMerger.mapCodec(
                            Codec.STRING_IDENTIFIER.mapCodec(Types_v1_21.AttributeModifier.EntityAttribute.ID).required(), Types_v1_21.AttributeModifier.EntityAttribute::getId,
                            Codec.DOUBLE.mapCodec(Types_v1_21.AttributeModifier.EntityAttribute.AMOUNT).required(), Types_v1_21.AttributeModifier.EntityAttribute::getAmount,
                            Codec.named(Types_v1_21.AttributeModifier.EntityAttribute.Operation.values()).mapCodec(Types_v1_21.AttributeModifier.EntityAttribute.OPERATION).required(), Types_v1_21.AttributeModifier.EntityAttribute::getOperation,
                            Types_v1_21.AttributeModifier.EntityAttribute::new
                    ), AttributeModifier::getModifier,
                    Codec.named(Types_v1_20_5.AttributeModifier.Slot.values()).mapCodec(Types_v1_21.AttributeModifier.SLOT).optional().defaulted(Types_v1_20_5.AttributeModifier.Slot.ANY), AttributeModifier::getSlot,
                    Codec.named(AttributeModifier.Display.Type.values()).<AttributeModifier.Display>typed("type", AttributeModifier.Display::getType, codecs::get).mapCodec(AttributeModifier.DISPLAY).optional().defaulted(AttributeModifier.Display.class::isInstance, () -> new AttributeModifier.Display.Default()), AttributeModifier::getDisplay,
                    AttributeModifier::new
            );
        });
    }

}
