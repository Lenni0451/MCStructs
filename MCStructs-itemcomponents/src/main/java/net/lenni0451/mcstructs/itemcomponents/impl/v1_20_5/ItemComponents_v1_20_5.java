package net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.Registries;
import net.lenni0451.mcstructs.itemcomponents.impl.Verifiers;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.registry.EitherEntry;
import net.lenni0451.mcstructs.registry.RegistryEntry;
import net.lenni0451.mcstructs.registry.TagEntryList;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5.*;

public class ItemComponents_v1_20_5 extends ItemComponentRegistry {

    private final TypeSerializers_v1_20_5 typeSerializers = new TypeSerializers_v1_20_5(this, TextComponentCodec.V1_20_5);

    public final ItemComponent<CompoundTag> CUSTOM_DATA = this.register("custom_data", this.typeSerializers.customData());
    public final ItemComponent<Integer> MAX_STACK_SIZE = this.register("max_stack_size", Codec.rangedInt(1, 99));
    public final ItemComponent<Integer> MAX_DAMAGE = this.register("max_damage", Codec.minInt(1));
    public final ItemComponent<Integer> DAMAGE = this.register("damage", Codec.minInt(0));
    public final ItemComponent<Unbreakable> UNBREAKABLE = this.register("unbreakable", MapCodecMerger.codec(
            Codec.BOOLEAN.mapCodec(Unbreakable.SHOW_IN_TOOLTIP).optional().defaulted(true), Unbreakable::isShowInTooltip,
            Unbreakable::new
    ));
    public final ItemComponent<TextComponent> CUSTOM_NAME = this.register("custom_name", this.typeSerializers.textComponent(Integer.MAX_VALUE));
    public final ItemComponent<TextComponent> ITEM_NAME = this.register("item_name", this.typeSerializers.textComponent(Integer.MAX_VALUE));
    public final ItemComponent<List<TextComponent>> LORE = this.register("lore", this.typeSerializers.textComponent(Integer.MAX_VALUE).listOf(256));
    public final ItemComponent<Rarity> RARITY = this.register("rarity", Codec.named(Rarity.values()));
    public final ItemComponent<Enchantments> ENCHANTMENTS = this.register("enchantments", Codec.oneOf(
            MapCodecMerger.codec(
                    this.typeSerializers.enchantmentLevels().mapCodec(Enchantments.LEVELS).required(), Enchantments::getEnchantments,
                    Codec.BOOLEAN.mapCodec(Enchantments.SHOW_IN_TOOLTIP).optional().defaulted(true), Enchantments::isShowInTooltip,
                    Enchantments::new
            ),
            this.typeSerializers.enchantmentLevels().map(Enchantments::getEnchantments, map -> new Enchantments(map, true))
    ));
    public final ItemComponent<BlockPredicatesChecker> CAN_PLACE_ON = this.register("can_place_on", Codec.oneOf(
            MapCodecMerger.codec(
                    this.typeSerializers.blockPredicate().listOf(1, Integer.MAX_VALUE).mapCodec(BlockPredicatesChecker.PREDICATES).required(), BlockPredicatesChecker::getPredicates,
                    Codec.BOOLEAN.mapCodec(BlockPredicatesChecker.SHOW_IN_TOOLTIP).optional().defaulted(true), BlockPredicatesChecker::isShowInTooltip,
                    BlockPredicatesChecker::new
            ),
            this.typeSerializers.blockPredicate().flatMap(predicates -> Result.error("Can't encode single block predicate"), predicate -> {
                BlockPredicatesChecker checker = new BlockPredicatesChecker();
                List<BlockPredicate> predicates = new ArrayList<>();
                predicates.add(predicate);
                checker.setPredicates(predicates);
                return Result.success(checker);
            })
    ));
    public final ItemComponent<BlockPredicatesChecker> CAN_BREAK = this.copy("can_break", this.CAN_PLACE_ON);
    public final ItemComponent<AttributeModifiers> ATTRIBUTE_MODIFIERS = this.register("attribute_modifiers", Codec.oneOf(
            MapCodecMerger.codec(
                    this.typeSerializers.attributeModifier().listOf().mapCodec(AttributeModifiers.MODIFIERS).required(), AttributeModifiers::getModifiers,
                    Codec.BOOLEAN.mapCodec(AttributeModifiers.SHOW_IN_TOOLTIP).optional().defaulted(true), AttributeModifiers::isShowInTooltip,
                    AttributeModifiers::new
            ),
            this.typeSerializers.attributeModifier().flatMap(modifiers -> Result.error("Can't encode single attribute modifier"), modifier -> {
                AttributeModifiers attributeModifiers = new AttributeModifiers();
                List<AttributeModifier> list = new ArrayList<>();
                list.add(modifier);
                attributeModifiers.setModifiers(list);
                return Result.success(attributeModifiers);
            })
    ));
    public final ItemComponent<Integer> CUSTOM_MODEL_DATA = this.register("custom_model_data", Codec.INTEGER);
    public final ItemComponent<Boolean> HIDE_ADDITIONAL_TOOLTIP = this.register("hide_additional_tooltip", Codec.UNIT);
    public final ItemComponent<Boolean> HIDE_TOOLTIP = this.register("hide_tooltip", Codec.UNIT);
    public final ItemComponent<Integer> REPAIR_COST = this.register("repair_cost", Codec.minInt(0));
    public final ItemComponent<Boolean> CREATIVE_SLOT_LOCK = this.registerNonSerializable("creative_slot_lock"); //No json/nbt serialization
    public final ItemComponent<Boolean> ENCHANTMENT_GLINT_OVERRIDE = this.register("enchantment_glint_override", Codec.BOOLEAN);
    public final ItemComponent<Boolean> INTANGIBLE_PROJECTILE = this.register("intangible_projectile", Codec.UNIT);
    public final ItemComponent<Food> FOOD = this.register("food", MapCodecMerger.codec(
            Codec.minInt(0).mapCodec(Food.NUTRITION).required(), Food::getNutrition,
            Codec.FLOAT.mapCodec(Food.SATURATION).required(), Food::getSaturation,
            Codec.BOOLEAN.mapCodec(Food.CAN_ALWAYS_EAT).optional().defaulted(false), Food::isCanAlwaysEat,
            Codec.minExclusiveFloat(0).mapCodec(Food.EAT_SECONDS).optional().defaulted(1.6F), Food::getEatSeconds,
            MapCodecMerger.codec(
                    this.typeSerializers.statusEffect().mapCodec(Food.Effect.EFFECT).required(), Food.Effect::getEffect,
                    Codec.rangedFloat(0, 1).mapCodec(Food.Effect.PROBABILITY).optional().defaulted(1F), Food.Effect::getProbability,
                    Food.Effect::new
            ).listOf().mapCodec(Food.EFFECTS).optional().defaulted(List::isEmpty, ArrayList::new), Food::getEffects,
            Food::new
    ));
    public final ItemComponent<Boolean> FIRE_RESISTANT = this.register("fire_resistant", Codec.UNIT);
    public final ItemComponent<ToolComponent> TOOL = this.register("tool", MapCodecMerger.codec(
            MapCodecMerger.codec(
                    TagEntryList.codec(this.registries.block, false).mapCodec(ToolComponent.Rule.BLOCKS).required(), ToolComponent.Rule::getBlocks,
                    Codec.minExclusiveFloat(0).mapCodec(ToolComponent.Rule.SPEED).optional().defaulted(null), ToolComponent.Rule::getSpeed,
                    Codec.BOOLEAN.mapCodec(ToolComponent.Rule.CORRECT_FOR_DROPS).optional().defaulted(null), ToolComponent.Rule::getCorrectForDrops,
                    ToolComponent.Rule::new
            ).listOf().mapCodec(ToolComponent.RULES).required(), ToolComponent::getRules,
            Codec.FLOAT.mapCodec(ToolComponent.DEFAULT_MINING_SPEED).optional().defaulted(1F), ToolComponent::getDefaultMiningSpeed,
            Codec.minInt(0).mapCodec(ToolComponent.DAMAGE_PER_BLOCK).optional().defaulted(1), ToolComponent::getDamagePerBlock,
            ToolComponent::new
    ));
    public final ItemComponent<Enchantments> STORED_ENCHANTMENTS = copy("stored_enchantments", this.ENCHANTMENTS);
    public final ItemComponent<DyedColor> DYED_COLOR = this.register("dyed_color", MapCodecMerger.codec(
            Codec.INTEGER.mapCodec(DyedColor.RGB).required(), DyedColor::getRgb,
            Codec.BOOLEAN.mapCodec(DyedColor.SHOW_IN_TOOLTIP).optional().defaulted(true), DyedColor::isShowInTooltip,
            DyedColor::new
    ));
    public final ItemComponent<Integer> MAP_COLOR = this.register("map_color", Codec.INTEGER);
    public final ItemComponent<Integer> MAP_ID = this.register("map_id", Codec.INTEGER);
    public final ItemComponent<Map<String, MapDecoration>> MAP_DECORATIONS = this.register("map_decorations", Codec.mapOf(Codec.STRING, MapCodecMerger.codec(
            this.registries.mapDecorationType.entryCodec().mapCodec(MapDecoration.TYPE).required(), MapDecoration::getType,
            Codec.DOUBLE.mapCodec(MapDecoration.X).required(), MapDecoration::getX,
            Codec.DOUBLE.mapCodec(MapDecoration.Z).required(), MapDecoration::getZ,
            Codec.FLOAT.mapCodec(MapDecoration.ROTATION).required(), MapDecoration::getRotation,
            MapDecoration::new
    )));
    public final ItemComponent<MapPostProcessing> MAP_POST_PROCESSING = this.registerNonSerializable("map_post_processing");
    public final ItemComponent<List<ItemStack>> CHARGED_PROJECTILES = this.register("charged_projectiles", this.typeSerializers.itemStack().listOf());
    public final ItemComponent<List<ItemStack>> BUNDLE_CONTENTS = this.register("bundle_contents", this.typeSerializers.itemStack().listOf());
    public final ItemComponent<PotionContents> POTION_CONTENTS = this.register("potion_contents", Codec.oneOf(
            MapCodecMerger.codec(
                    this.registries.potion.entryCodec().mapCodec(PotionContents.POTION).optional().defaulted(null), PotionContents::getPotion,
                    Codec.INTEGER.mapCodec(PotionContents.CUSTOM_COLOR).optional().defaulted(null), PotionContents::getCustomColor,
                    this.typeSerializers.statusEffect().listOf().mapCodec(PotionContents.CUSTOM_EFFECTS).optional().defaulted(List::isEmpty, ArrayList::new), PotionContents::getCustomEffects,
                    PotionContents::new
            ),
            this.registries.potion.entryCodec().map(PotionContents::getPotion, id -> new PotionContents(id, null, new ArrayList<>()))
    ));
    public final ItemComponent<List<SuspiciousStewEffect>> SUSPICIOUS_STEW_EFFECTS = this.register("suspicious_stew_effects", MapCodecMerger.codec(
            this.registries.statusEffect.entryCodec().mapCodec(SuspiciousStewEffect.ID).required(), SuspiciousStewEffect::getId,
            Codec.INTEGER.mapCodec(SuspiciousStewEffect.DURATION).optional().lenient().defaulted(0), SuspiciousStewEffect::getDuration,
            SuspiciousStewEffect::new
    ).listOf());
    public final ItemComponent<WritableBook> WRITABLE_BOOK_CONTENT = this.register("writable_book_content", MapCodecMerger.codec(
            this.typeSerializers.rawFilteredPair(Codec.sizedString(0, 1024)).listOf().mapCodec(WritableBook.PAGES).optional().defaulted(List::isEmpty, ArrayList::new), WritableBook::getPages,
            WritableBook::new
    ));
    public final ItemComponent<WrittenBook> WRITTEN_BOOK_CONTENT = this.register("written_book_content", MapCodecMerger.codec(
            this.typeSerializers.rawFilteredPair(Codec.sizedString(0, 32)).mapCodec(WrittenBook.TITLE).required(), WrittenBook::getTitle,
            Codec.STRING.mapCodec(WrittenBook.AUTHOR).required(), WrittenBook::getAuthor,
            Codec.rangedInt(0, 3).mapCodec(WrittenBook.GENERATION).optional().defaulted(0), WrittenBook::getGeneration,
            this.typeSerializers.rawFilteredPair(this.typeSerializers.textComponent(32767)).listOf().mapCodec(WrittenBook.PAGES).optional().defaulted(List::isEmpty, ArrayList::new), WrittenBook::getPages,
            Codec.BOOLEAN.mapCodec(WrittenBook.RESOLVED).optional().defaulted(false), WrittenBook::isResolved,
            WrittenBook::new
    ));
    public final ItemComponent<ArmorTrim> TRIM = this.register("trim", MapCodecMerger.codec(
            this.typeSerializers.armorTrimMaterial().mapCodec(ArmorTrim.MATERIAL).required(), ArmorTrim::getMaterial,
            this.typeSerializers.armorTrimPattern().mapCodec(ArmorTrim.PATTERN).required(), ArmorTrim::getPattern,
            Codec.BOOLEAN.mapCodec(ArmorTrim.SHOW_IN_TOOLTIP).optional().defaulted(true), ArmorTrim::isShowInTooltip,
            ArmorTrim::new
    ));
    public final ItemComponent<Map<RegistryEntry, String>> DEBUG_STICK_STATE = this.register("debug_stick_state", Codec.mapOf(
            this.registries.block.entryCodec(),
            block -> Codec.STRING.verified(value -> {
                if (this.verifiers.verifyBlockState(block, value)) return Result.success(null);
                return Result.error("Invalid " + block + " block state value: " + value);
            })
    ));
    public final ItemComponent<CompoundTag> ENTITY_DATA = this.register("entity_data", this.typeSerializers.customData().verified(tag -> {
        if (!tag.contains("id", NbtType.STRING)) return Result.error("Entity data tag does not contain an id");
        return Result.success(null);
    }));
    public final ItemComponent<CompoundTag> BUCKET_ENTITY_DATA = this.register("bucket_entity_data", this.typeSerializers.customData());
    public final ItemComponent<CompoundTag> BLOCK_ENTITY_DATA = this.register("block_entity_data", this.typeSerializers.customData().verified(tag -> {
        if (!tag.contains("id", NbtType.STRING)) return Result.error("Block entity data tag does not contain an id");
        return Result.success(null);
    }));
    public final ItemComponent<EitherEntry<Instrument>> INSTRUMENT = this.register("instrument", EitherEntry.codec(
            this.registries.instrument,
            MapCodecMerger.codec(
                    this.typeSerializers.soundEvent().mapCodec(Instrument.SOUND_EVENT).required(), Instrument::getSoundEvent,
                    Codec.minInt(1).mapCodec(Instrument.USE_DURATION).required(), Instrument::getUseDuration,
                    Codec.minExclusiveFloat(0).mapCodec(Instrument.RANGE).required(), Instrument::getRange,
                    Instrument::new
            )
    ));
    public final ItemComponent<Integer> OMINOUS_BOTTLE_AMPLIFIER = this.register("ominous_bottle_amplifier", Codec.rangedInt(1, 4));
    public final ItemComponent<List<Identifier>> RECIPES = this.register("recipes", Codec.STRING_IDENTIFIER.listOf());
    public final ItemComponent<LodestoneTracker> LODESTONE_TRACKER = this.register("lodestone_tracker", MapCodecMerger.codec(
            MapCodecMerger.codec(
                    this.registries.dimension.entryCodec().mapCodec(LodestoneTracker.GlobalPos.DIMENSION).required(), LodestoneTracker.GlobalPos::getDimension,
                    this.typeSerializers.blockPos().mapCodec(LodestoneTracker.GlobalPos.POS).required(), LodestoneTracker.GlobalPos::getPos,
                    LodestoneTracker.GlobalPos::new
            ).mapCodec(LodestoneTracker.TARGET).optional().defaulted(null), LodestoneTracker::getTarget,
            Codec.BOOLEAN.mapCodec(LodestoneTracker.TRACKED).optional().defaulted(true), LodestoneTracker::isTracked,
            LodestoneTracker::new
    ));
    public final ItemComponent<FireworkExplosions> FIREWORK_EXPLOSION = this.register("firework_explosion", MapCodecMerger.codec(
            Codec.named(FireworkExplosions.ExplosionShape.values()).mapCodec(FireworkExplosions.SHAPE).required(), FireworkExplosions::getShape,
            Codec.INTEGER.listOf().mapCodec(FireworkExplosions.COLORS).optional().defaulted(List::isEmpty, ArrayList::new), FireworkExplosions::getColors,
            Codec.INTEGER.listOf().mapCodec(FireworkExplosions.FADE_COLORS).optional().defaulted(List::isEmpty, ArrayList::new), FireworkExplosions::getFadeColors,
            Codec.BOOLEAN.mapCodec(FireworkExplosions.HAS_TRAIL).optional().defaulted(false), FireworkExplosions::isHasTrail,
            Codec.BOOLEAN.mapCodec(FireworkExplosions.HAS_TWINKLE).optional().defaulted(false), FireworkExplosions::isHasTwinkle,
            FireworkExplosions::new
    ));
    public final ItemComponent<Fireworks> FIREWORKS = this.register("fireworks", MapCodecMerger.codec(
            Codec.UNSIGNED_BYTE.mapCodec(Fireworks.FLIGHT_DURATION).optional().defaulted(0), Fireworks::getFlightDuration,
            this.FIREWORK_EXPLOSION.getCodec().listOf().mapCodec(Fireworks.EXPLOSIONS).optional().defaulted(List::isEmpty, ArrayList::new), Fireworks::getExplosions,
            Fireworks::new
    ));
    public final ItemComponent<GameProfile> PROFILE = this.register("profile", Codec.oneOf(
            MapCodecMerger.codec(
                    this.typeSerializers.playerName().mapCodec(GameProfile.NAME).optional().defaulted(null), GameProfile::getName,
                    Codec.INT_ARRAY_UUID.mapCodec(GameProfile.ID).optional().defaulted(null), GameProfile::getUuid,
                    Codec.oneOf(
                            Codec.mapOf(Codec.STRING, Codec.STRING.listOf()).flatMap(properties -> Result.error("Can't serialize properties to String/String map"), map -> {
                                Map<String, List<GameProfile.Property>> properties = new HashMap<>();
                                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                                    for (String val : entry.getValue()) {
                                        properties.computeIfAbsent(entry.getKey(), key -> new ArrayList<>()).add(new GameProfile.Property(entry.getKey(), val, null));
                                    }
                                }
                                return Result.success(properties);
                            }),
                            MapCodecMerger.codec(
                                    Codec.STRING.mapCodec(GameProfile.Property.NAME).required(), GameProfile.Property::getName,
                                    Codec.STRING.mapCodec(GameProfile.Property.VALUE).required(), GameProfile.Property::getValue,
                                    Codec.STRING.mapCodec(GameProfile.Property.SIGNATURE).optional().lenient().defaulted(null), GameProfile.Property::getSignature,
                                    GameProfile.Property::new
                            ).listOf().map(properties -> {
                                List<GameProfile.Property> list = new ArrayList<>();
                                for (Map.Entry<String, List<GameProfile.Property>> entry : properties.entrySet()) list.addAll(entry.getValue());
                                return list;
                            }, list -> {
                                Map<String, List<GameProfile.Property>> properties = new HashMap<>();
                                for (GameProfile.Property property : list) properties.computeIfAbsent(property.getName(), key -> new ArrayList<>()).add(property);
                                return properties;
                            })
                    ).mapCodec(GameProfile.PROPERTIES).optional().defaulted(Map::isEmpty, HashMap::new), GameProfile::getProperties,
                    GameProfile::new
            ),
            this.typeSerializers.playerName().map(GameProfile::getName, name -> new GameProfile(name, null, new HashMap<>()))
    ));
    public final ItemComponent<Identifier> NOTE_BLOCK_SOUND = this.register("note_block_sound", Codec.STRING_IDENTIFIER);
    public final ItemComponent<List<BannerPattern>> BANNER_PATTERNS = this.register("banner_patterns", MapCodecMerger.codec(
            EitherEntry.codec(
                    this.registries.bannerPattern,
                    MapCodecMerger.codec(
                            Codec.STRING_IDENTIFIER.mapCodec(BannerPattern.Pattern.ASSET_ID).required(), BannerPattern.Pattern::getAssetId,
                            Codec.STRING.mapCodec(BannerPattern.Pattern.TRANSLATION_KEY).required(), BannerPattern.Pattern::getTranslationKey,
                            BannerPattern.Pattern::new
                    )
            ).mapCodec(BannerPattern.PATTERN).required(), BannerPattern::getPattern,
            this.typeSerializers.dyeColor().mapCodec(BannerPattern.COLOR).required(), BannerPattern::getColor,
            BannerPattern::new
    ).listOf());
    public final ItemComponent<DyeColor> BASE_COLOR = this.register("base_color", this.typeSerializers.dyeColor());
    public final ItemComponent<List<RegistryEntry>> POT_DECORATIONS = this.register("pot_decorations", this.registries.item.entryCodec().listOf(4));
    public final ItemComponent<List<ContainerSlot>> CONTAINER = this.register("container", MapCodecMerger.codec(
            Codec.rangedInt(0, 255).mapCodec(ContainerSlot.SLOT).required(), ContainerSlot::getSlot,
            this.typeSerializers.itemStack().mapCodec(ContainerSlot.ITEM).required(), ContainerSlot::getItem,
            ContainerSlot::new
    ).listOf(256));
    public final ItemComponent<Map<String, String>> BLOCK_STATE = this.register("block_state", Codec.mapOf(Codec.STRING, Codec.STRING));
    public final ItemComponent<List<BeeData>> BEES = this.register("bees", MapCodecMerger.codec(
            this.typeSerializers.customData().mapCodec(BeeData.ENTITY_DATA).optional().defaulted(CompoundTag::isEmpty, CompoundTag::new), BeeData::getEntityData,
            Codec.INTEGER.mapCodec(BeeData.TICKS_IN_HIVE).required(), BeeData::getTicksInHive,
            Codec.INTEGER.mapCodec(BeeData.MIN_TICKS_IN_HIVE).required(), BeeData::getMinTicksInHive,
            BeeData::new
    ).listOf());
    public final ItemComponent<String> LOCK = this.register("lock", Codec.STRING);
    public final ItemComponent<ContainerLoot> CONTAINER_LOOT = this.register("container_loot", MapCodecMerger.codec(
            Codec.STRING_IDENTIFIER.mapCodec(ContainerLoot.LOOT_TABLE).required(), ContainerLoot::getLootTable,
            Codec.LONG.mapCodec(ContainerLoot.SEED).optional().defaulted(0L), ContainerLoot::getSeed,
            ContainerLoot::new
    ));


    public ItemComponents_v1_20_5() {
    }

    public ItemComponents_v1_20_5(final Registries registryVerifier, final Verifiers verifiers) {
        super(registryVerifier, verifiers);
    }

    {
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "custom_name", "item_name", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "hide_additional_tooltip", "hide_tooltip", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "fire_resistant", "tool", "stored_enchantments", "dyed_color", "map_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "ominous_bottle_amplifier", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "lock", "container_loot");
    }

    @Override
    public ItemComponentMap getItemDefaults() {
        return new ItemComponentMap(this)
                .set(this.MAX_STACK_SIZE, 64)
                .set(this.LORE, new ArrayList<>())
                .set(this.ENCHANTMENTS, new Enchantments(new HashMap<>(), true))
                .set(this.REPAIR_COST, 0)
                .set(this.ATTRIBUTE_MODIFIERS, new AttributeModifiers(new ArrayList<>(), true))
                .set(this.RARITY, Rarity.COMMON);
    }

    @Override
    public <D> D mapTo(DataConverter<D> converter, ItemComponentMap map) {
        Map<D, D> out = new HashMap<>();
        for (Map.Entry<ItemComponent<?>, ?> entry : map.getValues().entrySet()) {
            ItemComponent<Object> component = (ItemComponent<Object>) entry.getKey();
            Object value = entry.getValue();

            String name = component.getName().get();
            out.put(converter.createString(name), component.serialize(converter, value));
        }
        for (ItemComponent<?> component : map.getMarkedForRemoval()) {
            String name = "!" + component.getName().get();
            out.put(converter.createString(name), converter.emptyMap());
        }
        return converter.createUnsafeMap(out);
    }

    @Override
    public <D> ItemComponentMap mapFrom(DataConverter<D> converter, D data) {
        Map<String, D> map = converter.asStringTypeMap(data).get();
        ItemComponentMap out = new ItemComponentMap(this);
        for (Map.Entry<String, D> entry : map.entrySet()) {
            String name = entry.getKey();
            boolean forRemoval = name.startsWith("!");
            if (forRemoval) name = name.substring(1);
            Identifier id = Identifier.of(name);
            ItemComponent<Object> component = this.getComponent(id);
            if (component == null) throw new IllegalArgumentException("Unknown item component: " + id);
            if (forRemoval) out.markForRemoval(component);
            else out.set(component, component.deserialize(converter, entry.getValue()));
        }
        return out;
    }

}
