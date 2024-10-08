package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.DynamicMapCodec;
import net.lenni0451.mcstructs.converter.codec.Either;
import net.lenni0451.mcstructs.converter.codec.MapCodec;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.TypeSerializers_v1_21;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21;

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

    public TypeSerializers_v1_21_2(final ItemComponentRegistry registry) {
        super(registry);
    }

    public Codec<MinMaxInt> minMaxInt() {
        return this.init(MIN_MAX_INT, () -> Codec.either(
                MapCodec.of(
                        Codec.INTEGER.mapCodec(MinMaxInt.MIN).optionalDefault(() -> null), MinMaxInt::getMin,
                        Codec.INTEGER.mapCodec(MinMaxInt.MAX).optionalDefault(() -> null), MinMaxInt::getMax,
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
                MapCodec.of(
                        Codec.DOUBLE.mapCodec(MinMaxDouble.MIN).optionalDefault(() -> null), MinMaxDouble::getMin,
                        Codec.DOUBLE.mapCodec(MinMaxDouble.MAX).optionalDefault(() -> null), MinMaxDouble::getMax,
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
        return this.init(ENCHANTMENT_PREDICATE, () -> MapCodec.of(
                this.tagEntryList(this.registry.getRegistryVerifier().enchantmentTag, this.registry.getRegistryVerifier().enchantment).mapCodec(EnchantmentPredicate.ENCHANTMENTS).optionalDefault(() -> null), EnchantmentPredicate::getEnchantments,
                this.minMaxInt().mapCodec(EnchantmentPredicate.LEVELS).optionalDefault(() -> null), EnchantmentPredicate::getLevels,
                EnchantmentPredicate::new
        ));
    }

    public <T> Codec<CollectionPredicate<T>> collectionPredicate(final Codec<T> codec) {
        return MapCodec.of(
                codec.listOf().mapCodec(CollectionPredicate.CONTAINS).optionalDefault(() -> null), CollectionPredicate::getContains,
                MapCodec.<T, MinMaxInt, CollectionPredicate.CountPredicate<T>>of(
                        codec.mapCodec(CollectionPredicate.CountPredicate.TEST).optionalDefault(() -> null), CollectionPredicate.CountPredicate::getTest,
                        this.minMaxInt().mapCodec(CollectionPredicate.CountPredicate.COUNT).optionalDefault(() -> null), CollectionPredicate.CountPredicate::getCount,
                        CollectionPredicate.CountPredicate::new
                ).listOf().mapCodec(CollectionPredicate.COUNT).optionalDefault(() -> null), CollectionPredicate::getCount,
                this.minMaxInt().mapCodec(CollectionPredicate.SIZE).optionalDefault(() -> null), CollectionPredicate::getSize,
                CollectionPredicate::new
        );
    }

    public Codec<ItemPredicate> itemPredicate() {
        return this.init(ITEM_PREDICATE, () -> MapCodec.of(
                this.tagEntryList(this.registry.getRegistryVerifier().itemTag, this.registry.getRegistryVerifier().item).mapCodec(ItemPredicate.ITEMS).optionalDefault(() -> null), ItemPredicate::getItems,
                this.minMaxInt().mapCodec(ItemPredicate.COUNT).optionalDefault(() -> null), ItemPredicate::getCount,
                cast(new DynamicMapCodec<>(this.registry.getComponentCodec(), ItemComponent::getCodec).mapCodec(ItemPredicate.COMPONENTS).optionalDefault(() -> null)), ItemPredicate::getComponents,
                this.itemSubPredicate().mapCodec(ItemPredicate.PREDICATES).optionalDefault(() -> null), ItemPredicate::getPredicates,
                ItemPredicate::new
        ));
    }

    public Codec<ConsumeEffect> consumeEffect() {
        return this.init(CONSUME_EFFECT, () -> {
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
        });
    }

    public Codec<Map<ItemSubPredicate.Type, ItemSubPredicate>> itemSubPredicate() {
        return this.init(ITEM_SUB_PREDICATE, () -> {
            Map<ItemSubPredicate.Type, Codec<? extends ItemSubPredicate>> codecs = new EnumMap<>(ItemSubPredicate.Type.class);
            codecs.put(ItemSubPredicate.Type.DAMAGE, MapCodec.of(
                    this.minMaxInt().mapCodec(ItemSubPredicate.Damage.DURABILITY).optionalDefault(MinMaxInt::new), ItemSubPredicate.Damage::getDurability,
                    this.minMaxInt().mapCodec(ItemSubPredicate.Damage.DAMAGE).optionalDefault(MinMaxInt::new), ItemSubPredicate.Damage::getDamage,
                    ItemSubPredicate.Damage::new
            ));
            codecs.put(ItemSubPredicate.Type.ENCHANTMENTS, this.enchantmentPredicate().listOf().map(ItemSubPredicate.Enchantments::getEnchantments, ItemSubPredicate.Enchantments::new));
            codecs.put(ItemSubPredicate.Type.STORED_ENCHANTMENTS, this.enchantmentPredicate().listOf().map(ItemSubPredicate.StoredEnchantments::getEnchantments, ItemSubPredicate.StoredEnchantments::new));
            codecs.put(ItemSubPredicate.Type.POTION_CONTENTS, this.tagEntryList(this.registry.getRegistryVerifier().potionTag, this.registry.getRegistryVerifier().potion).map(ItemSubPredicate.PotionContents::getPotion, ItemSubPredicate.PotionContents::new));
            codecs.put(ItemSubPredicate.Type.CUSTOM_DATA, this.stringOrRawCompoundTag().map(ItemSubPredicate.CustomData::getData, ItemSubPredicate.CustomData::new));
            codecs.put(ItemSubPredicate.Type.CONTAINER, MapCodec.of(
                    this.collectionPredicate(this.itemPredicate()).mapCodec(ItemSubPredicate.Container.ITEMS).optionalDefault(() -> null), ItemSubPredicate.Container::getItems,
                    ItemSubPredicate.Container::new
            ));
            codecs.put(ItemSubPredicate.Type.BUNDLE_CONTENTS, MapCodec.of(
                    this.collectionPredicate(this.itemPredicate()).mapCodec(ItemSubPredicate.BundleContents.ITEMS).optionalDefault(() -> null), ItemSubPredicate.BundleContents::getItems,
                    ItemSubPredicate.BundleContents::new
            ));
            codecs.put(ItemSubPredicate.Type.FIREWORK_EXPLOSION, MapCodec.of(
                    Codec.named(Types_v1_20_5.FireworkExplosions.ExplosionShape.values()).mapCodec(ItemSubPredicate.FireworkExplosion.SHAPE).optionalDefault(() -> null), ItemSubPredicate.FireworkExplosion::getShape,
                    Codec.BOOLEAN.mapCodec(ItemSubPredicate.FireworkExplosion.HAS_TWINKLE).optionalDefault(() -> null), ItemSubPredicate.FireworkExplosion::isHasTwinkle,
                    Codec.BOOLEAN.mapCodec(ItemSubPredicate.FireworkExplosion.HAS_TRAIL).optionalDefault(() -> null), ItemSubPredicate.FireworkExplosion::isHasTrail,
                    ItemSubPredicate.FireworkExplosion::new
            ));
            codecs.put(ItemSubPredicate.Type.FIREWORKS, MapCodec.of(
                    this.collectionPredicate((Codec<ItemSubPredicate.FireworkExplosion>) codecs.get(ItemSubPredicate.Type.FIREWORK_EXPLOSION)).mapCodec(ItemSubPredicate.Fireworks.EXPLOSIONS).optionalDefault(() -> null), ItemSubPredicate.Fireworks::getExplosions,
                    this.minMaxInt().mapCodec(ItemSubPredicate.Fireworks.FLIGHT_DURATION).optionalDefault(() -> null), ItemSubPredicate.Fireworks::getFlightDuration,
                    ItemSubPredicate.Fireworks::new
            ));
            codecs.put(ItemSubPredicate.Type.WRITABLE_BOOK_CONTENT, MapCodec.of(
                    this.collectionPredicate(Codec.STRING.map(ItemSubPredicate.WritableBookContent.PageContent::getPage, ItemSubPredicate.WritableBookContent.PageContent::new)).mapCodec(ItemSubPredicate.WritableBookContent.PAGES).optionalDefault(() -> null), ItemSubPredicate.WritableBookContent::getPages,
                    ItemSubPredicate.WritableBookContent::new
            ));
            codecs.put(ItemSubPredicate.Type.WRITTEN_BOOK_CONTENT, MapCodec.of(
                    this.collectionPredicate(Codec.STRING.map(ItemSubPredicate.WrittenBookContent.PageContent::getPage, ItemSubPredicate.WrittenBookContent.PageContent::new)).mapCodec(ItemSubPredicate.WrittenBookContent.PAGES).optionalDefault(() -> null), ItemSubPredicate.WrittenBookContent::getPages,
                    Codec.STRING.mapCodec(ItemSubPredicate.WrittenBookContent.AUTHOR).optionalDefault(() -> null), ItemSubPredicate.WrittenBookContent::getAuthor,
                    Codec.STRING.mapCodec(ItemSubPredicate.WrittenBookContent.TITLE).optionalDefault(() -> null), ItemSubPredicate.WrittenBookContent::getTitle,
                    this.minMaxInt().mapCodec(ItemSubPredicate.WrittenBookContent.GENERATION).optionalDefault(MinMaxInt::new), ItemSubPredicate.WrittenBookContent::getGeneration,
                    Codec.BOOLEAN.mapCodec(ItemSubPredicate.WrittenBookContent.RESOLVED).optionalDefault(() -> null), ItemSubPredicate.WrittenBookContent::isResolved,
                    ItemSubPredicate.WrittenBookContent::new
            ));
            codecs.put(ItemSubPredicate.Type.ATTRIBUTE_MODIFIERS, MapCodec.of(
                    this.collectionPredicate(MapCodec.of(
                            this.tagEntryList(this.registry.getRegistryVerifier().attributeModifierTag, this.registry.getRegistryVerifier().attributeModifier).mapCodec(ItemSubPredicate.AttributeModifiers.ModifierPredicate.ATTRIBUTE).optionalDefault(() -> null), ItemSubPredicate.AttributeModifiers.ModifierPredicate::getAttribute,
                            Codec.STRING_IDENTIFIER.mapCodec(ItemSubPredicate.AttributeModifiers.ModifierPredicate.ID).optionalDefault(() -> null), ItemSubPredicate.AttributeModifiers.ModifierPredicate::getId,
                            this.minMaxDouble().mapCodec(ItemSubPredicate.AttributeModifiers.ModifierPredicate.AMOUNT).optionalDefault(MinMaxDouble::new), ItemSubPredicate.AttributeModifiers.ModifierPredicate::getAmount,
                            Codec.named(Types_v1_21.AttributeModifier.EntityAttribute.Operation.values()).mapCodec(ItemSubPredicate.AttributeModifiers.ModifierPredicate.OPERATION).optionalDefault(() -> null), ItemSubPredicate.AttributeModifiers.ModifierPredicate::getOperation,
                            Codec.named(Types_v1_20_5.AttributeModifier.Slot.values()).mapCodec(ItemSubPredicate.AttributeModifiers.ModifierPredicate.SLOT).optionalDefault(() -> null), ItemSubPredicate.AttributeModifiers.ModifierPredicate::getSlot,
                            ItemSubPredicate.AttributeModifiers.ModifierPredicate::new
                    )).mapCodec(ItemSubPredicate.AttributeModifiers.MODIFIERS).optionalDefault(() -> null), ItemSubPredicate.AttributeModifiers::getModifiers,
                    ItemSubPredicate.AttributeModifiers::new
            ));
            codecs.put(ItemSubPredicate.Type.TRIM, MapCodec.of(
                    this.tagEntryList(this.registry.getRegistryVerifier().armorTrimMaterialTag, this.registry.getRegistryVerifier().armorTrimMaterial).mapCodec(ItemSubPredicate.Trim.MATERIAL).optionalDefault(() -> null), ItemSubPredicate.Trim::getMaterial,
                    this.tagEntryList(this.registry.getRegistryVerifier().armorTrimPatternTag, this.registry.getRegistryVerifier().armorTrimPattern).mapCodec(ItemSubPredicate.Trim.PATTERN).optionalDefault(() -> null), ItemSubPredicate.Trim::getPattern,
                    ItemSubPredicate.Trim::new
            ));
            codecs.put(ItemSubPredicate.Type.JUKEBOX_PLAYABLE, MapCodec.of(
                    this.tagEntryList(this.registry.getRegistryVerifier().jukeboxSongTag, this.registry.getRegistryVerifier().jukeboxSong).mapCodec(ItemSubPredicate.JukeboxPlayable.SONG).optionalDefault(() -> null), ItemSubPredicate.JukeboxPlayable::getSong,
                    ItemSubPredicate.JukeboxPlayable::new
            ));
            return new DynamicMapCodec<>(
                    Codec.identified(ItemSubPredicate.Type.values()),
                    type -> (Codec<ItemSubPredicate>) codecs.get(type)
            );
        });
    }

    private static <T> T cast(final Object obj) {
        return (T) obj;
    }

}
