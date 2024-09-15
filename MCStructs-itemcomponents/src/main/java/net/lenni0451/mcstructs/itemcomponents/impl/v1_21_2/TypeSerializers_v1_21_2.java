package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.MapCodec;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.TypeSerializers_v1_21;

import java.util.EnumMap;
import java.util.Map;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2.ConsumeEffect;

public class TypeSerializers_v1_21_2 extends TypeSerializers_v1_21 {

    public TypeSerializers_v1_21_2(final ItemComponentRegistry registry) {
        super(registry);
    }

    public Codec<ConsumeEffect> consumeEffect() {
        Map<ConsumeEffect.Type, Codec<? extends ConsumeEffect>> codecs = new EnumMap<>(ConsumeEffect.Type.class);
        codecs.put(ConsumeEffect.Type.APPLY_EFFECTS, MapCodec.of(
                this.statusEffect().listOf().mapCodec(ConsumeEffect.ApplyEffects.EFFECTS), ConsumeEffect.ApplyEffects::getEffects,
                Codec.rangedFloat(0, 1).mapCodec(ConsumeEffect.ApplyEffects.PROBABILITY).optionalDefault(() -> 1F), ConsumeEffect.ApplyEffects::getProbability,
                ConsumeEffect.ApplyEffects::new
        ));
        codecs.put(ConsumeEffect.Type.REMOVE_EFFECTS, MapCodec.of(
                this.tagEntryList(this.registry.getRegistryVerifier().statusEffectTag, this.registry.getRegistryVerifier().statusEffect).mapCodec(ConsumeEffect.RemoveEffects.EFFECTS), ConsumeEffect.RemoveEffects::getEffects,
                ConsumeEffect.RemoveEffects::new
        ));
        codecs.put(ConsumeEffect.Type.CLEAR_ALL_EFFECTS, Codec.unit(ConsumeEffect.ClearAllEffects::new));
        codecs.put(ConsumeEffect.Type.TELEPORT_RANDOMLY, MapCodec.of(
                Codec.minExclusiveFloat(0).mapCodec(ConsumeEffect.TeleportRandomly.DIAMETER).optionalDefault(() -> 16F), ConsumeEffect.TeleportRandomly::getDiameter,
                ConsumeEffect.TeleportRandomly::new
        ));
        codecs.put(ConsumeEffect.Type.PLAY_SOUND, MapCodec.of(
                this.soundEvent().mapCodec(ConsumeEffect.PlaySound.SOUND), ConsumeEffect.PlaySound::getSound,
                ConsumeEffect.PlaySound::new
        ));
        return Codec.identified(ConsumeEffect.Type.values()).typed(ConsumeEffect::getType, type -> (Codec<ConsumeEffect>) codecs.get(type));
    }

}
