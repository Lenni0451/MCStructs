package net.lenni0451.mcstructs.itemcomponents.impl.v26_3;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11.Types_v1_21_11.SwingAnimation;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11.Types_v1_21_11.SwingAnimationType;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2.ConsumeEffect;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_2.TypeSerializers_v26_2;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_3.Types_v26_3.*;
import net.lenni0451.mcstructs.registry.Holder;
import net.lenni0451.mcstructs.registry.ResourceKey;
import net.lenni0451.mcstructs.registry.TagEntryList;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.EnumMap;
import java.util.Map;

public class TypeSerializers_v26_3 extends TypeSerializers_v26_2 {

    protected static final String RESOLVABLE_INT = "resolvable_int";
    protected static final String RESOLVABLE_FLOAT = "resolvable_float";
    protected static final String SIGN_TEXT = "sign_text";
    protected static final String SWING_ANIMATION = "swing_animation";

    public TypeSerializers_v26_3(final ItemComponentRegistry registry, final TextComponentCodec textComponentCodec) {
        super(registry, textComponentCodec);
    }

    public Codec<Holder<ArmorTrimMaterial>> armorTrimMaterial_v26_3() {
        return this.init(ARMOR_TRIM_MATERIAL, () -> Holder.fileCodec(
                this.registry.getRegistries().armorTrimMaterial,
                MapCodecMerger.codec(
                        Codec.STRING_IDENTIFIER.mapCodec(ArmorTrimMaterial.PALETTE_ID).required(), ArmorTrimMaterial::getPaletteId,
                        this.textComponentCodec.getTextCodec().mapCodec(ArmorTrimMaterial.DESCRIPTION).required(), ArmorTrimMaterial::getDescription,
                        ArmorTrimMaterial::new
                )
        ));
    }

    @Override
    public Codec<ConsumeEffect> consumeEffect() {
        return this.init(CONSUME_EFFECT, () -> {
            Map<ConsumeEffect.Type, MapCodec<? extends ConsumeEffect>> codecs = new EnumMap<>(ConsumeEffect.Type.class);
            codecs.put(ConsumeEffect.Type.APPLY_EFFECTS, MapCodecMerger.mapCodec(
                    this.statusEffect().listOf().mapCodec(ConsumeEffect.ApplyEffects.EFFECTS).required(), ConsumeEffect.ApplyEffects::getEffects,
                    Codec.rangedFloat(0, 1).mapCodec(ConsumeEffect.ApplyEffects.PROBABILITY).optional().defaulted(1F), ConsumeEffect.ApplyEffects::getProbability,
                    ConsumeEffect.ApplyEffects::new
            ));
            codecs.put(ConsumeEffect.Type.REMOVE_EFFECTS, MapCodecMerger.mapCodec(
                    TagEntryList.codec(this.registry.getRegistries().statusEffect, false).mapCodec(ConsumeEffect.RemoveEffects.EFFECTS).required(), ConsumeEffect.RemoveEffects::getEffects,
                    ConsumeEffect.RemoveEffects::new
            ));
            codecs.put(ConsumeEffect.Type.CLEAR_ALL_EFFECTS, MapCodec.unit(ConsumeEffect.ClearAllEffects::new));
            codecs.put(ConsumeEffect.Type.TELEPORT_RANDOMLY, MapCodecMerger.mapCodec(
                    Codec.minExclusiveFloat(0).mapCodec(TeleportRandomly.DIAMETER).optional().defaulted(16F), TeleportRandomly::getDiameter,
                    Codec.BOOLEAN.mapCodec(TeleportRandomly.DIRECTIONAL_PARTICLES).optional().defaulted(true), TeleportRandomly::isDirectionalParticles,
                    TeleportRandomly::new
            ));
            codecs.put(ConsumeEffect.Type.PLAY_SOUND, MapCodecMerger.mapCodec(
                    this.soundEvent().mapCodec(ConsumeEffect.PlaySound.SOUND).required(), ConsumeEffect.PlaySound::getSound,
                    ConsumeEffect.PlaySound::new
            ));
            return Codec.identified(ConsumeEffect.Type.values()).typed(ConsumeEffect::getType, codecs::get);
        });
    }

    public Codec<ResolvableInt> resolvableInt() {
        return this.init(RESOLVABLE_INT, () -> Codec.either(Codec.INTEGER, ResourceKey.codec(registry.getRegistries().contextIntProvider)).map(
                resolvableInt -> resolvableInt.isConstant() ? Either.left(resolvableInt.getValue()) : Either.right(resolvableInt.getKey()),
                either -> either.xmap(ResolvableInt::new, ResolvableInt::new)
        ));
    }

    public Codec<ResolvableFloat> resolvableFloat() {
        return this.init(RESOLVABLE_FLOAT, () -> Codec.either(Codec.FLOAT, ResourceKey.codec(registry.getRegistries().contextFloatProvider)).map(
                resolvableFloat -> resolvableFloat.isConstant() ? Either.left(resolvableFloat.getValue()) : Either.right(resolvableFloat.getKey()),
                either -> either.xmap(ResolvableFloat::new, ResolvableFloat::new)
        ));
    }

    public Codec<SignText> signText() {
        return this.init(SIGN_TEXT, () -> MapCodecMerger.codec(
                this.textComponentCodec.getTextCodec().listOf(4, 4).mapCodec(SignText.MESSAGES).required(), SignText::getMessages,
                this.textComponentCodec.getTextCodec().listOf(4, 4).mapCodec(SignText.FILTERED_MESSAGES).optional().defaulted(null), SignText::getFilteredMessagesForSerialization,
                Codec.named(Types_v1_20_5.DyeColor.values()).mapCodec(SignText.COLOR).optional().elseGet(() -> Types_v1_20_5.DyeColor.BLACK), SignText::getColor,
                Codec.BOOLEAN.mapCodec(SignText.HAS_GLOWING_TEXT).optional().elseGet(() -> false), SignText::isHasGlowingText,
                SignText::new
        ));
    }

    public Codec<SwingAnimation> swingAnimation() {
        return this.init(SWING_ANIMATION, () -> MapCodecMerger.codec(
                Codec.named(SwingAnimationType.values()).mapCodec(SwingAnimation.TYPE).optional().defaulted(SwingAnimationType.WHACK), SwingAnimation::getType,
                Codec.minInt(0).mapCodec(SwingAnimation.DURATION).optional().defaulted(6), SwingAnimation::getDuration,
                SwingAnimation::new
        ));
    }

}
