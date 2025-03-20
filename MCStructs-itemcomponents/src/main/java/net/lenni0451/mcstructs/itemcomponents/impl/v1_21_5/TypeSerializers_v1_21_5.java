package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4.TypeSerializers_v1_21_4;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.Map;

public class TypeSerializers_v1_21_5 extends TypeSerializers_v1_21_4 {

    public TypeSerializers_v1_21_5(final ItemComponentRegistry registry, final TextComponentCodec textComponentCodec) {
        super(registry, textComponentCodec);
    }

    @Override
    public Codec<TextComponent> textComponent(int maxLength) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Codec<Map<Identifier, Integer>> enchantmentLevels() {
        return this.init(ENCHANTMENT_LEVELS, () -> Codec.mapOf(Codec.STRING_IDENTIFIER.verified(registry.getRegistryVerifier().enchantment), Codec.rangedInt(1, 255)));
    }

}
