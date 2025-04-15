package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.DynamicMapCodec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.TypeSerializers_v1_21;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21;
import net.lenni0451.mcstructs.itemcomponents.registry.TagEntryList;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2.*;

public class TypeSerializers_v1_21_2 extends TypeSerializers_v1_21 {

    protected static final String MIN_MAX_INT = "min_max_int";
    protected static final String MIN_MAX_DOUBLE = "min_max_double";
    protected static final String ENCHANTMENT_PREDICATE = "enchantment_predicate";
    protected static final String ITEM_PREDICATE = "item_predicate";
    protected static final String CONSUME_EFFECT = "consume_effect";
    protected static final String ITEM_SUB_PREDICATE = "item_sub_predicate";

    public TypeSerializers_v1_21_2(final ItemComponentRegistry registry, final TextComponentCodec textComponentCodec) {
        super(registry, textComponentCodec);
    }

    public Codec<MinMaxInt> minMaxInt() {
        return this.init(MIN_MAX_INT, () -> Codec.either(
                MapCodecMerger.codec(
                        Codec.INTEGER.mapCodec(MinMaxInt.MIN).optional().defaulted(null), MinMaxInt::getMin,
                        Codec.INTEGER.mapCodec(MinMaxInt.MAX).optional().defaulted(null), MinMaxInt::getMax,
                        MinMaxInt::new
                ),
                Codec.INTEGER
        ).map(minMaxInt -> {
            if (minMaxInt.getMin() != null && minMaxInt.getMin().equals(minMaxInt.getMax())) {
                return Either.right(minMaxInt.getMin());
            } else {
                return Either.left(minMaxInt);
            }
        }, minMaxIntIntegerEither -> minMaxIntIntegerEither.xmap(Function.identity(), value -> new MinMaxInt(value, value))));
    }

    public Codec<MinMaxDouble> minMaxDouble() {
        return this.init(MIN_MAX_DOUBLE, () -> Codec.either(
                MapCodecMerger.codec(
                        Codec.DOUBLE.mapCodec(MinMaxDouble.MIN).optional().defaulted(null), MinMaxDouble::getMin,
                        Codec.DOUBLE.mapCodec(MinMaxDouble.MAX).optional().defaulted(null), MinMaxDouble::getMax,
                        MinMaxDouble::new
                ),
                Codec.DOUBLE
        ).map(minMaxInt -> {
            if (minMaxInt.getMin() != null && minMaxInt.getMin().equals(minMaxInt.getMax())) {
                return Either.right(minMaxInt.getMin());
            } else {
                return Either.left(minMaxInt);
            }
        }, minMaxIntIntegerEither -> minMaxIntIntegerEither.xmap(Function.identity(), value -> new MinMaxDouble(value, value))));
    }

    public Codec<EnchantmentPredicate> enchantmentPredicate() {
        return this.init(ENCHANTMENT_PREDICATE, () -> MapCodecMerger.codec(
                TagEntryList.codec(this.registry.getRegistries().enchantment, false).mapCodec(EnchantmentPredicate.ENCHANTMENTS).optional().defaulted(null), EnchantmentPredicate::getEnchantments,
                this.minMaxInt().mapCodec(EnchantmentPredicate.LEVELS).optional().defaulted(null), EnchantmentPredicate::getLevels,
                EnchantmentPredicate::new
        ));
    }

    public <T> Codec<CollectionPredicate<T>> collectionPredicate(final Codec<T> codec) {
        return MapCodecMerger.codec(
                codec.listOf().mapCodec(CollectionPredicate.CONTAINS).optional().defaulted(null), CollectionPredicate::getContains,
                MapCodecMerger.<T, MinMaxInt, CollectionPredicate.CountPredicate<T>>codec(
                        codec.mapCodec(CollectionPredicate.CountPredicate.TEST).optional().defaulted(null), CollectionPredicate.CountPredicate::getTest,
                        this.minMaxInt().mapCodec(CollectionPredicate.CountPredicate.COUNT).optional().defaulted(null), CollectionPredicate.CountPredicate::getCount,
                        CollectionPredicate.CountPredicate::new
                ).listOf().mapCodec(CollectionPredicate.COUNT).optional().defaulted(null), CollectionPredicate::getCount,
                this.minMaxInt().mapCodec(CollectionPredicate.SIZE).optional().defaulted(null), CollectionPredicate::getSize,
                CollectionPredicate::new
        );
    }

    public Codec<ItemPredicate> itemPredicate() {
        return this.init(ITEM_PREDICATE, () -> MapCodecMerger.codec(
                TagEntryList.codec(this.registry.getRegistries().item, false).mapCodec(ItemPredicate.ITEMS).optional().defaulted(null), ItemPredicate::getItems,
                this.minMaxInt().mapCodec(ItemPredicate.COUNT).optional().defaulted(null), ItemPredicate::getCount,
                cast(new DynamicMapCodec<>(this.registry.getComponentCodec(), ItemComponent::getCodec).mapCodec(ItemPredicate.COMPONENTS).optional().defaulted(null)), ItemPredicate::getComponents,
                this.itemSubPredicate().mapCodec(ItemPredicate.PREDICATES).optional().defaulted(null), ItemPredicate::getPredicates,
                ItemPredicate::new
        ));
    }

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
                    Codec.minExclusiveFloat(0).mapCodec(ConsumeEffect.TeleportRandomly.DIAMETER).optional().defaulted(16F), ConsumeEffect.TeleportRandomly::getDiameter,
                    ConsumeEffect.TeleportRandomly::new
            ));
            codecs.put(ConsumeEffect.Type.PLAY_SOUND, MapCodecMerger.mapCodec(
                    this.soundEvent().mapCodec(ConsumeEffect.PlaySound.SOUND).required(), ConsumeEffect.PlaySound::getSound,
                    ConsumeEffect.PlaySound::new
            ));
            return Codec.identified(ConsumeEffect.Type.values()).typed(ConsumeEffect::getType, codecs::get);
        });
    }

    public Codec<Map<ItemSubPredicate.Type, ItemSubPredicate>> itemSubPredicate() {
        return this.init(ITEM_SUB_PREDICATE, () -> {
            Map<ItemSubPredicate.Type, Codec<? extends ItemSubPredicate>> codecs = new EnumMap<>(ItemSubPredicate.Type.class);
            codecs.put(ItemSubPredicate.Type.DAMAGE, MapCodecMerger.codec(
                    this.minMaxInt().mapCodec(ItemSubPredicate.Damage.DURABILITY).optional().defaulted(MinMaxInt::isEmpty, MinMaxInt::new), ItemSubPredicate.Damage::getDurability,
                    this.minMaxInt().mapCodec(ItemSubPredicate.Damage.DAMAGE).optional().defaulted(MinMaxInt::isEmpty, MinMaxInt::new), ItemSubPredicate.Damage::getDamage,
                    ItemSubPredicate.Damage::new
            ));
            codecs.put(ItemSubPredicate.Type.ENCHANTMENTS, this.enchantmentPredicate().listOf().map(ItemSubPredicate.Enchantments::getEnchantments, ItemSubPredicate.Enchantments::new));
            codecs.put(ItemSubPredicate.Type.STORED_ENCHANTMENTS, this.enchantmentPredicate().listOf().map(ItemSubPredicate.StoredEnchantments::getEnchantments, ItemSubPredicate.StoredEnchantments::new));
            codecs.put(ItemSubPredicate.Type.POTION_CONTENTS, TagEntryList.codec(this.registry.getRegistries().potion, false).map(ItemSubPredicate.PotionContents::getPotion, ItemSubPredicate.PotionContents::new));
            codecs.put(ItemSubPredicate.Type.CUSTOM_DATA, this.stringOrRawCompoundTag().map(ItemSubPredicate.CustomData::getData, ItemSubPredicate.CustomData::new));
            codecs.put(ItemSubPredicate.Type.CONTAINER, MapCodecMerger.codec(
                    this.collectionPredicate(this.itemPredicate()).mapCodec(ItemSubPredicate.Container.ITEMS).optional().defaulted(null), ItemSubPredicate.Container::getItems,
                    ItemSubPredicate.Container::new
            ));
            codecs.put(ItemSubPredicate.Type.BUNDLE_CONTENTS, MapCodecMerger.codec(
                    this.collectionPredicate(this.itemPredicate()).mapCodec(ItemSubPredicate.BundleContents.ITEMS).optional().defaulted(null), ItemSubPredicate.BundleContents::getItems,
                    ItemSubPredicate.BundleContents::new
            ));
            codecs.put(ItemSubPredicate.Type.FIREWORK_EXPLOSION, MapCodecMerger.codec(
                    Codec.named(Types_v1_20_5.FireworkExplosions.ExplosionShape.values()).mapCodec(ItemSubPredicate.FireworkExplosion.SHAPE).optional().defaulted(null), ItemSubPredicate.FireworkExplosion::getShape,
                    Codec.BOOLEAN.mapCodec(ItemSubPredicate.FireworkExplosion.HAS_TWINKLE).optional().defaulted(null), ItemSubPredicate.FireworkExplosion::isHasTwinkle,
                    Codec.BOOLEAN.mapCodec(ItemSubPredicate.FireworkExplosion.HAS_TRAIL).optional().defaulted(null), ItemSubPredicate.FireworkExplosion::isHasTrail,
                    ItemSubPredicate.FireworkExplosion::new
            ));
            codecs.put(ItemSubPredicate.Type.FIREWORKS, MapCodecMerger.codec(
                    this.collectionPredicate((Codec<ItemSubPredicate.FireworkExplosion>) codecs.get(ItemSubPredicate.Type.FIREWORK_EXPLOSION)).mapCodec(ItemSubPredicate.Fireworks.EXPLOSIONS).optional().defaulted(null), ItemSubPredicate.Fireworks::getExplosions,
                    this.minMaxInt().mapCodec(ItemSubPredicate.Fireworks.FLIGHT_DURATION).optional().defaulted(null), ItemSubPredicate.Fireworks::getFlightDuration,
                    ItemSubPredicate.Fireworks::new
            ));
            codecs.put(ItemSubPredicate.Type.WRITABLE_BOOK_CONTENT, MapCodecMerger.codec(
                    this.collectionPredicate(Codec.STRING.map(ItemSubPredicate.WritableBookContent.PageContent::getPage, ItemSubPredicate.WritableBookContent.PageContent::new)).mapCodec(ItemSubPredicate.WritableBookContent.PAGES).optional().defaulted(null), ItemSubPredicate.WritableBookContent::getPages,
                    ItemSubPredicate.WritableBookContent::new
            ));
            codecs.put(ItemSubPredicate.Type.WRITTEN_BOOK_CONTENT, MapCodecMerger.codec(
                    this.collectionPredicate(Codec.STRING.map(ItemSubPredicate.WrittenBookContent.PageContent::getPage, ItemSubPredicate.WrittenBookContent.PageContent::new)).mapCodec(ItemSubPredicate.WrittenBookContent.PAGES).optional().defaulted(null), ItemSubPredicate.WrittenBookContent::getPages,
                    Codec.STRING.mapCodec(ItemSubPredicate.WrittenBookContent.AUTHOR).optional().defaulted(null), ItemSubPredicate.WrittenBookContent::getAuthor,
                    Codec.STRING.mapCodec(ItemSubPredicate.WrittenBookContent.TITLE).optional().defaulted(null), ItemSubPredicate.WrittenBookContent::getTitle,
                    this.minMaxInt().mapCodec(ItemSubPredicate.WrittenBookContent.GENERATION).optional().defaulted(MinMaxInt::isEmpty, MinMaxInt::new), ItemSubPredicate.WrittenBookContent::getGeneration,
                    Codec.BOOLEAN.mapCodec(ItemSubPredicate.WrittenBookContent.RESOLVED).optional().defaulted(null), ItemSubPredicate.WrittenBookContent::isResolved,
                    ItemSubPredicate.WrittenBookContent::new
            ));
            codecs.put(ItemSubPredicate.Type.ATTRIBUTE_MODIFIERS, MapCodecMerger.codec(
                    this.collectionPredicate(MapCodecMerger.codec(
                            TagEntryList.codec(this.registry.getRegistries().attributeModifier, false).mapCodec(ItemSubPredicate.AttributeModifiers.ModifierPredicate.ATTRIBUTE).optional().defaulted(null), ItemSubPredicate.AttributeModifiers.ModifierPredicate::getAttribute,
                            Codec.STRING_IDENTIFIER.mapCodec(ItemSubPredicate.AttributeModifiers.ModifierPredicate.ID).optional().defaulted(null), ItemSubPredicate.AttributeModifiers.ModifierPredicate::getId,
                            this.minMaxDouble().mapCodec(ItemSubPredicate.AttributeModifiers.ModifierPredicate.AMOUNT).optional().defaulted(MinMaxDouble::isEmpty, MinMaxDouble::new), ItemSubPredicate.AttributeModifiers.ModifierPredicate::getAmount,
                            Codec.named(Types_v1_21.AttributeModifier.EntityAttribute.Operation.values()).mapCodec(ItemSubPredicate.AttributeModifiers.ModifierPredicate.OPERATION).optional().defaulted(null), ItemSubPredicate.AttributeModifiers.ModifierPredicate::getOperation,
                            Codec.named(Types_v1_20_5.AttributeModifier.Slot.values()).mapCodec(ItemSubPredicate.AttributeModifiers.ModifierPredicate.SLOT).optional().defaulted(null), ItemSubPredicate.AttributeModifiers.ModifierPredicate::getSlot,
                            ItemSubPredicate.AttributeModifiers.ModifierPredicate::new
                    )).mapCodec(ItemSubPredicate.AttributeModifiers.MODIFIERS).optional().defaulted(null), ItemSubPredicate.AttributeModifiers::getModifiers,
                    ItemSubPredicate.AttributeModifiers::new
            ));
            codecs.put(ItemSubPredicate.Type.TRIM, MapCodecMerger.codec(
                    TagEntryList.codec(this.registry.getRegistries().armorTrimMaterial, false).mapCodec(ItemSubPredicate.Trim.MATERIAL).optional().defaulted(null), ItemSubPredicate.Trim::getMaterial,
                    TagEntryList.codec(this.registry.getRegistries().armorTrimPattern, false).mapCodec(ItemSubPredicate.Trim.PATTERN).optional().defaulted(null), ItemSubPredicate.Trim::getPattern,
                    ItemSubPredicate.Trim::new
            ));
            codecs.put(ItemSubPredicate.Type.JUKEBOX_PLAYABLE, MapCodecMerger.codec(
                    TagEntryList.codec(this.registry.getRegistries().jukeboxSong, false).mapCodec(ItemSubPredicate.JukeboxPlayable.SONG).optional().defaulted(null), ItemSubPredicate.JukeboxPlayable::getSong,
                    ItemSubPredicate.JukeboxPlayable::new
            ));
            return new DynamicMapCodec<>(Codec.identified(ItemSubPredicate.Type.values()), codecs::get);
        });
    }

    private static <T> T cast(final Object obj) {
        return (T) obj;
    }

}
