package net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.Result;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.Either;
import net.lenni0451.mcstructs.converter.codec.MapCodec;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.RegistryVerifier;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.ATextComponent;

import java.util.*;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5.*;

public class ItemComponents_v1_20_5 extends ItemComponentRegistry {

    private final TypeSerializers_v1_20_5 typeSerializers = new TypeSerializers_v1_20_5(this);

    public final ItemComponent<CompoundTag> CUSTOM_DATA = this.register("custom_data", this.typeSerializers.customData());
    public final ItemComponent<Integer> MAX_STACK_SIZE = this.register("max_stack_size", Codec.rangedInt(1, 99));
    public final ItemComponent<Integer> MAX_DAMAGE = this.register("max_damage", Codec.minInt(1));
    public final ItemComponent<Integer> DAMAGE = this.register("damage", Codec.minInt(0));
    public final ItemComponent<Unbreakable> UNBREAKABLE = this.register("unbreakable", MapCodec.of(
            Codec.BOOLEAN.mapCodec(Unbreakable.SHOW_IN_TOOLTIP).optionalDefault(() -> true), Unbreakable::isShowInTooltip,
            Unbreakable::new
    ));
    public final ItemComponent<ATextComponent> CUSTOM_NAME = this.register("custom_name", this.typeSerializers.textComponent(Integer.MAX_VALUE));
    public final ItemComponent<ATextComponent> ITEM_NAME = this.register("item_name", this.typeSerializers.textComponent(Integer.MAX_VALUE));
    public final ItemComponent<List<ATextComponent>> LORE = this.register("lore", this.typeSerializers.textComponent(Integer.MAX_VALUE).listOf(256));
    public final ItemComponent<Rarity> RARITY = this.register("rarity", Codec.named(Rarity.values()));
    public final ItemComponent<Enchantments> ENCHANTMENTS = this.register("enchantments", Codec.oneOf(
            MapCodec.of(
                    this.typeSerializers.enchantmentLevels().mapCodec(Enchantments.LEVELS), Enchantments::getEnchantments,
                    Codec.BOOLEAN.mapCodec(Enchantments.SHOW_IN_TOOLTIP).optionalDefault(() -> true), Enchantments::isShowInTooltip,
                    Enchantments::new
            ),
            this.typeSerializers.enchantmentLevels().map(Enchantments::getEnchantments, map -> new Enchantments(map, true))
    ));
    public final ItemComponent<BlockPredicatesChecker> CAN_PLACE_ON = this.register("can_place_on", Codec.oneOf(
            MapCodec.of(
                    this.typeSerializers.blockPredicate().listOf(1, Integer.MAX_VALUE).mapCodec(BlockPredicatesChecker.PREDICATES), BlockPredicatesChecker::getPredicates,
                    Codec.BOOLEAN.mapCodec(BlockPredicatesChecker.SHOW_IN_TOOLTIP).optionalDefault(() -> true), BlockPredicatesChecker::isShowInTooltip,
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
            MapCodec.of(
                    this.typeSerializers.attributeModifier().listOf().mapCodec(AttributeModifiers.MODIFIERS), AttributeModifiers::getModifiers,
                    Codec.BOOLEAN.mapCodec(AttributeModifiers.SHOW_IN_TOOLTIP).optionalDefault(() -> true), AttributeModifiers::isShowInTooltip,
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
    public final ItemComponent<Food> FOOD = this.register("food", MapCodec.of(
            Codec.minInt(0).mapCodec(Food.NUTRITION), Food::getNutrition,
            Codec.FLOAT.mapCodec(Food.SATURATION), Food::getSaturation,
            Codec.BOOLEAN.mapCodec(Food.CAN_ALWAYS_EAT).optionalDefault(() -> false), Food::isCanAlwaysEat,
            Codec.minExclusiveFloat(0).mapCodec(Food.EAT_SECONDS).optionalDefault(() -> 1.6F), Food::getEatSeconds,
            MapCodec.of(
                    this.typeSerializers.statusEffect().mapCodec(Food.Effect.EFFECT), Food.Effect::getEffect,
                    Codec.rangedFloat(0, 1).mapCodec(Food.Effect.PROBABILITY).optionalDefault(() -> 1F), Food.Effect::getProbability,
                    Food.Effect::new
            ).listOf().mapCodec(Food.EFFECTS).defaulted(ArrayList::new, List::isEmpty), Food::getEffects,
            Food::new
    ));
    public final ItemComponent<Boolean> FIRE_RESISTANT = this.register("fire_resistant", Codec.UNIT);
    public final ItemComponent<ToolComponent> TOOL = this.register("tool", MapCodec.of(
            MapCodec.of(
                    Codec.STRING_IDENTIFIER.verified(this.registryVerifier.block).optionalListOf().mapCodec(ToolComponent.Rule.BLOCKS), ToolComponent.Rule::getBlocks,
                    Codec.minExclusiveFloat(0).mapCodec(ToolComponent.Rule.SPEED).optionalDefault(() -> null), ToolComponent.Rule::getSpeed,
                    Codec.BOOLEAN.mapCodec(ToolComponent.Rule.CORRECT_FOR_DROPS).optionalDefault(() -> null), ToolComponent.Rule::getCorrectForDrops,
                    ToolComponent.Rule::new
            ).listOf().mapCodec(ToolComponent.RULES), ToolComponent::getRules,
            Codec.FLOAT.mapCodec(ToolComponent.DEFAULT_MINING_SPEED).optionalDefault(() -> 1F), ToolComponent::getDefaultMiningSpeed,
            Codec.minInt(0).mapCodec(ToolComponent.DAMAGE_PER_BLOCK).optionalDefault(() -> 1), ToolComponent::getDamagePerBlock,
            ToolComponent::new
    ));
    public final ItemComponent<Enchantments> STORED_ENCHANTMENTS = copy("stored_enchantments", this.ENCHANTMENTS);
    public final ItemComponent<DyedColor> DYED_COLOR = this.register("dyed_color", MapCodec.of(
            Codec.INTEGER.mapCodec(DyedColor.RGB), DyedColor::getRgb,
            Codec.BOOLEAN.mapCodec(DyedColor.SHOW_IN_TOOLTIP).optionalDefault(() -> true), DyedColor::isShowInTooltip,
            DyedColor::new
    ));
    public final ItemComponent<Integer> MAP_COLOR = this.register("map_color", Codec.INTEGER);
    public final ItemComponent<Integer> MAP_ID = this.register("map_id", Codec.INTEGER);
    public final ItemComponent<Map<String, MapDecoration>> MAP_DECORATIONS = this.register("map_decorations", Codec.mapOf(Codec.STRING, MapCodec.of(
            Codec.STRING_IDENTIFIER.verified(this.getRegistryVerifier().mapDecorationType).mapCodec(MapDecoration.TYPE), MapDecoration::getType,
            Codec.DOUBLE.mapCodec(MapDecoration.X), MapDecoration::getX,
            Codec.DOUBLE.mapCodec(MapDecoration.Z), MapDecoration::getZ,
            Codec.FLOAT.mapCodec(MapDecoration.ROTATION), MapDecoration::getRotation,
            MapDecoration::new
    )));
    public final ItemComponent<MapPostProcessing> MAP_POST_PROCESSING = this.registerNonSerializable("map_post_processing");
    public final ItemComponent<List<ItemStack>> CHARGED_PROJECTILES = this.register("charged_projectiles", this.typeSerializers.itemStack().listOf());
    public final ItemComponent<List<ItemStack>> BUNDLE_CONTENTS = this.register("bundle_contents", this.typeSerializers.itemStack().listOf());
    public final ItemComponent<PotionContents> POTION_CONTENTS = this.register("potion_contents", Codec.oneOf(
            MapCodec.of(
                    Codec.STRING_IDENTIFIER.verified(this.registryVerifier.potion).mapCodec(PotionContents.POTION).optionalDefault(() -> null), PotionContents::getPotion,
                    Codec.INTEGER.mapCodec(PotionContents.CUSTOM_COLOR).optionalDefault(() -> null), PotionContents::getCustomColor,
                    this.typeSerializers.statusEffect().listOf().mapCodec(PotionContents.CUSTOM_EFFECTS).defaulted(ArrayList::new, List::isEmpty), PotionContents::getCustomEffects,
                    PotionContents::new
            ),
            Codec.STRING_IDENTIFIER.verified(this.registryVerifier.potion).map(PotionContents::getPotion, id -> new PotionContents(id, null, new ArrayList<>()))
    ));
    public final ItemComponent<List<SuspiciousStewEffect>> SUSPICIOUS_STEW_EFFECTS = this.register("suspicious_stew_effects", MapCodec.of(
            Codec.STRING_IDENTIFIER.verified(this.registryVerifier.statusEffect).mapCodec(SuspiciousStewEffect.ID), SuspiciousStewEffect::getId,
            Codec.INTEGER.mapCodec(SuspiciousStewEffect.DURATION).lenient().optionalDefault(() -> 0), SuspiciousStewEffect::getDuration,
            SuspiciousStewEffect::new
    ).listOf());
    public final ItemComponent<WritableBook> WRITABLE_BOOK_CONTENT = this.register("writable_book_content", MapCodec.of(
            this.typeSerializers.rawFilteredPair(Codec.sizedString(0, 1024)).listOf().mapCodec(WritableBook.PAGES).defaulted(ArrayList::new, List::isEmpty), WritableBook::getPages,
            WritableBook::new
    ));
    public final ItemComponent<WrittenBook> WRITTEN_BOOK_CONTENT = this.register("written_book_content", MapCodec.of(
            this.typeSerializers.rawFilteredPair(Codec.sizedString(0, 32)).mapCodec(WrittenBook.TITLE), WrittenBook::getTitle,
            Codec.STRING.mapCodec(WrittenBook.AUTHOR), WrittenBook::getAuthor,
            Codec.rangedInt(0, 3).mapCodec(WrittenBook.GENERATION).optionalDefault(() -> 0), WrittenBook::getGeneration,
            this.typeSerializers.rawFilteredPair(this.typeSerializers.textComponent(32767)).listOf().mapCodec(WrittenBook.PAGES).defaulted(ArrayList::new, List::isEmpty), WrittenBook::getPages,
            Codec.BOOLEAN.mapCodec(WrittenBook.RESOLVED).optionalDefault(() -> false), WrittenBook::isResolved,
            WrittenBook::new
    ));
    public final ItemComponent<ArmorTrim> TRIM = this.register("trim", MapCodec.of(
            this.typeSerializers.armorTrimMaterial().mapCodec(ArmorTrim.MATERIAL), ArmorTrim::getMaterial,
            this.typeSerializers.armorTrimPattern().mapCodec(ArmorTrim.PATTERN), ArmorTrim::getPattern,
            Codec.BOOLEAN.mapCodec(ArmorTrim.SHOW_IN_TOOLTIP).optionalDefault(() -> true), ArmorTrim::isShowInTooltip,
            ArmorTrim::new
    ));
    public final ItemComponent<Map<Identifier, String>> DEBUG_STICK_STATE = this.register("debug_stick_state", Codec.mapOf(
            Codec.STRING_IDENTIFIER.verified(this.registryVerifier.block),
            block -> Codec.STRING.verified(value -> {
                if (this.registryVerifier.verifyBlockState(block, value)) return Result.success(null);
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
    public final ItemComponent<Either<Identifier, Instrument>> INSTRUMENT = this.register("instrument", this.typeSerializers.registryEntry(
            this.registryVerifier.instrument,
            MapCodec.of(
                    this.typeSerializers.soundEvent().mapCodec(Instrument.SOUND_EVENT), Instrument::getSoundEvent,
                    Codec.minInt(1).mapCodec(Instrument.USE_DURATION), Instrument::getUseDuration,
                    Codec.minExclusiveFloat(0).mapCodec(Instrument.RANGE), Instrument::getRange,
                    Instrument::new
            )
    ));
    public final ItemComponent<Integer> OMINOUS_BOTTLE_AMPLIFIER = this.register("ominous_bottle_amplifier", Codec.rangedInt(1, 4));
    public final ItemComponent<List<Identifier>> RECIPES = this.register("recipes", Codec.STRING_IDENTIFIER.listOf());
    public final ItemComponent<LodestoneTracker> LODESTONE_TRACKER = this.register("lodestone_tracker", MapCodec.of(
            MapCodec.of(
                    Codec.STRING_IDENTIFIER.mapCodec(LodestoneTracker.GlobalPos.DIMENSION), LodestoneTracker.GlobalPos::getDimension,
                    this.typeSerializers.blockPos().mapCodec(LodestoneTracker.GlobalPos.POS), LodestoneTracker.GlobalPos::getPos,
                    LodestoneTracker.GlobalPos::new
            ).mapCodec(LodestoneTracker.TARGET).optionalDefault(() -> null), LodestoneTracker::getTarget,
            Codec.BOOLEAN.mapCodec(LodestoneTracker.TRACKED).optionalDefault(() -> true), LodestoneTracker::isTracked,
            LodestoneTracker::new
    ));
    public final ItemComponent<FireworkExplosions> FIREWORK_EXPLOSION = this.register("firework_explosion", MapCodec.of(
            Codec.named(FireworkExplosions.ExplosionShape.values()).mapCodec(FireworkExplosions.SHAPE), FireworkExplosions::getShape,
            Codec.INT_ARRAY.mapCodec(FireworkExplosions.COLORS).defaulted(() -> new int[0], array -> array.length == 0), FireworkExplosions::getColors,
            Codec.INT_ARRAY.mapCodec(FireworkExplosions.FADE_COLORS).defaulted(() -> new int[0], array -> array.length == 0), FireworkExplosions::getFadeColors,
            Codec.BOOLEAN.mapCodec(FireworkExplosions.HAS_TRAIL).optionalDefault(() -> false), FireworkExplosions::isHasTrail,
            Codec.BOOLEAN.mapCodec(FireworkExplosions.HAS_TWINKLE).optionalDefault(() -> false), FireworkExplosions::isHasTwinkle,
            FireworkExplosions::new
    ));
    public final ItemComponent<Fireworks> FIREWORKS = this.register("fireworks", MapCodec.of(
            Codec.UNSIGNED_BYTE.mapCodec(Fireworks.FLIGHT_DURATION).optionalDefault(() -> 0), Fireworks::getFlightDuration,
            this.FIREWORK_EXPLOSION.getCodec().listOf().mapCodec(Fireworks.EXPLOSIONS).defaulted(ArrayList::new, List::isEmpty), Fireworks::getExplosions,
            Fireworks::new
    ));
    public final ItemComponent<GameProfile> PROFILE = this.register("profile", Codec.oneOf(
            MapCodec.of(
                    this.typeSerializers.playerName().mapCodec(GameProfile.NAME).optionalDefault(() -> null), GameProfile::getName,
                    Codec.INT_ARRAY_UUID.mapCodec(GameProfile.ID).optionalDefault(() -> null), GameProfile::getUuid,
                    Codec.oneOf(
                            Codec.mapOf(Codec.STRING, Codec.STRING.listOf()).map(properties -> {
                                Map<String, List<String>> map = new HashMap<>();
                                for (Map.Entry<String, List<GameProfile.Property>> entry : properties.entrySet()) {
                                    for (GameProfile.Property property : entry.getValue()) {
                                        map.computeIfAbsent(entry.getKey(), key -> new ArrayList<>()).add(property.getValue());
                                    }
                                }
                                return map;
                            }, map -> {
                                Map<String, List<GameProfile.Property>> properties = new HashMap<>();
                                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                                    for (String val : entry.getValue()) {
                                        properties.computeIfAbsent(entry.getKey(), key -> new ArrayList<>()).add(new GameProfile.Property(entry.getKey(), val, null));
                                    }
                                }
                                return properties;
                            }),
                            MapCodec.of(
                                    Codec.STRING.mapCodec(GameProfile.Property.NAME), GameProfile.Property::getName,
                                    Codec.STRING.mapCodec(GameProfile.Property.VALUE), GameProfile.Property::getValue,
                                    Codec.STRING.mapCodec(GameProfile.Property.SIGNATURE).lenient().optionalDefault(() -> null), GameProfile.Property::getSignature,
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
                    ).mapCodec(GameProfile.PROPERTIES).defaulted(HashMap::new, Map::isEmpty), GameProfile::getProperties,
                    GameProfile::new
            ),
            this.typeSerializers.playerName().map(GameProfile::getName, name -> new GameProfile(name, null, new HashMap<>()))
    ));
    public final ItemComponent<Identifier> NOTE_BLOCK_SOUND = this.register("note_block_sound", Codec.STRING_IDENTIFIER);
    public final ItemComponent<List<BannerPattern>> BANNER_PATTERNS = this.register("banner_patterns", MapCodec.of(
            this.typeSerializers.dyeColor().mapCodec(BannerPattern.COLOR), BannerPattern::getColor,
            this.typeSerializers.registryEntry(
                    this.getRegistryVerifier().bannerPattern,
                    MapCodec.of(
                            Codec.STRING_IDENTIFIER.mapCodec(BannerPattern.Pattern.ASSET_ID), BannerPattern.Pattern::getAssetId,
                            Codec.STRING.mapCodec(BannerPattern.Pattern.TRANSLATION_KEY), BannerPattern.Pattern::getTranslationKey,
                            BannerPattern.Pattern::new
                    )
            ).mapCodec(BannerPattern.PATTERN), BannerPattern::getPattern,
            BannerPattern::new
    ).listOf());
    public final ItemComponent<DyeColor> BASE_COLOR = this.register("base_color", this.typeSerializers.dyeColor());
    public final ItemComponent<List<Identifier>> POT_DECORATIONS = this.register("pot_decorations", Codec.STRING_IDENTIFIER.verified(this.registryVerifier.item).listOf(4));
    public final ItemComponent<List<ContainerSlot>> CONTAINER = this.register("container", MapCodec.of(
            Codec.rangedInt(0, 255).mapCodec(ContainerSlot.SLOT), ContainerSlot::getSlot,
            this.typeSerializers.itemStack().mapCodec(ContainerSlot.ITEM), ContainerSlot::getItem,
            ContainerSlot::new
    ).listOf(256));
    public final ItemComponent<Map<String, String>> BLOCK_STATE = this.register("block_state", Codec.mapOf(Codec.STRING, Codec.STRING));
    public final ItemComponent<List<BeeData>> BEES = this.register("bees", MapCodec.of(
            this.typeSerializers.customData().mapCodec(BeeData.ENTITY_DATA).defaulted(CompoundTag::new, CompoundTag::isEmpty), BeeData::getEntityData,
            Codec.INTEGER.mapCodec(BeeData.TICKS_IN_HIVE), BeeData::getTicksInHive,
            Codec.INTEGER.mapCodec(BeeData.MIN_TICKS_IN_HIVE), BeeData::getMinTicksInHive,
            BeeData::new
    ).listOf());
    public final ItemComponent<String> LOCK = this.register("lock", Codec.STRING);
    public final ItemComponent<ContainerLoot> CONTAINER_LOOT = this.register("container_loot", MapCodec.of(
            Codec.STRING_IDENTIFIER.mapCodec(ContainerLoot.LOOT_TABLE), ContainerLoot::getLootTable,
            Codec.LONG.mapCodec(ContainerLoot.SEED).optionalDefault(() -> 0L), ContainerLoot::getSeed,
            ContainerLoot::new
    ));


    public ItemComponents_v1_20_5() {
    }

    public ItemComponents_v1_20_5(final RegistryVerifier registryVerifier) {
        super(registryVerifier);
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
        D out = converter.emptyMap();
        for (Map.Entry<ItemComponent<?>, Optional<?>> entry : map.getRaw().entrySet()) {
            ItemComponent<Object> component = (ItemComponent<Object>) entry.getKey();
            Optional<?> value = entry.getValue();

            String name = component.getName().get();
            if (value.isPresent()) converter.put(out, name, component.serialize(converter, value.get()));
            else converter.put(out, "!" + name, converter.emptyMap());
        }
        return out;
    }

    @Override
    public <D> ItemComponentMap mapFrom(DataConverter<D> converter, D data) {
        Map<String, D> map = converter.asStringTypeMap(data).getOrThrow();
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
