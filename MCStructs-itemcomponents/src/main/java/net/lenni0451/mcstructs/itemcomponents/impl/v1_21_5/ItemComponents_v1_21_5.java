package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.RegistryVerifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4.ItemComponents_v1_21_4;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4.TypeSerializers_v1_21_4;
import net.lenni0451.mcstructs.networkcodec.NetType;
import net.lenni0451.mcstructs.networkcodec.RecordNetType;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;
import net.lenni0451.mcstructs.text.serializer.v1_21_5.TextCodecs_v1_21_5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5.Types_v1_21_5.*;

public class ItemComponents_v1_21_5 extends ItemComponents_v1_21_4 {

    private final TypeSerializers_v1_21_4 typeSerializers = new TypeSerializers_v1_21_4(this, TextComponentCodec.V1_21_5);

    public final ItemComponent<TextComponent> CUSTOM_NAME = this.register("custom_name", this.typeSerializers.getTextComponentCodec().getTextCodec());
    public final ItemComponent<TextComponent> ITEM_NAME = this.register("item_name", this.typeSerializers.getTextComponentCodec().getTextCodec());
    public final ItemComponent<List<TextComponent>> LORE = this.register("lore", this.typeSerializers.getTextComponentCodec().getTextCodec().listOf(256));
    public final ItemComponent<Weapon> WEAPON = this.register("weapon", MapCodecMerger.codec(
            Codec.minInt(0).mapCodec(Weapon.ITEM_DAMAGE_PER_ATTACK).optional().defaulted(1), Weapon::getItemDamagePerAttack,
            Codec.minFloat(0).mapCodec(Weapon.DISABLE_BLOCKING_FOR_SECONDS).optional().defaulted(0F), Weapon::getDisableBlockingForSeconds,
            Weapon::new
    ), RecordNetType.of(NetType.VAR_INT, Weapon::getItemDamagePerAttack, NetType.FLOAT, Weapon::getDisableBlockingForSeconds, Weapon::new));
    public final ItemComponent<Types_v1_20_5.WrittenBook> WRITTEN_BOOK_CONTENT = this.register("written_book_content", MapCodecMerger.codec(
            this.typeSerializers.rawFilteredPair(Codec.sizedString(0, 32)).mapCodec(Types_v1_20_5.WrittenBook.TITLE).required(), Types_v1_20_5.WrittenBook::getTitle,
            Codec.STRING.mapCodec(Types_v1_20_5.WrittenBook.AUTHOR).required(), Types_v1_20_5.WrittenBook::getAuthor,
            Codec.rangedInt(0, 3).mapCodec(Types_v1_20_5.WrittenBook.GENERATION).optional().defaulted(0), Types_v1_20_5.WrittenBook::getGeneration,
            this.typeSerializers.rawFilteredPair(this.typeSerializers.getTextComponentCodec().getTextCodec()).listOf().mapCodec(Types_v1_20_5.WrittenBook.PAGES).optional().defaulted(List::isEmpty, ArrayList::new), Types_v1_20_5.WrittenBook::getPages,
            Codec.BOOLEAN.mapCodec(Types_v1_20_5.WrittenBook.RESOLVED).optional().defaulted(false), Types_v1_20_5.WrittenBook::isResolved,
            Types_v1_20_5.WrittenBook::new
    ));
    public final ItemComponent<Float> POTION_DURATION_SCALE = this.register("potion_duration_scale", Codec.minFloat(0), NetType.FLOAT);
    public final ItemComponent<ToolComponent> TOOL = this.register("tool", MapCodecMerger.codec(
            MapCodecMerger.codec(
                    this.typeSerializers.tagEntryList(this.registryVerifier.blockTag, this.registryVerifier.block).mapCodec(Types_v1_20_5.ToolComponent.Rule.BLOCKS).required(), Types_v1_20_5.ToolComponent.Rule::getBlocks,
                    Codec.minExclusiveFloat(0).mapCodec(Types_v1_20_5.ToolComponent.Rule.SPEED).optional().defaulted(null), Types_v1_20_5.ToolComponent.Rule::getSpeed,
                    Codec.BOOLEAN.mapCodec(Types_v1_20_5.ToolComponent.Rule.CORRECT_FOR_DROPS).optional().defaulted(null), Types_v1_20_5.ToolComponent.Rule::getCorrectForDrops,
                    Types_v1_20_5.ToolComponent.Rule::new
            ).listOf().mapCodec(ToolComponent.RULES).required(), ToolComponent::getRules,
            Codec.FLOAT.mapCodec(ToolComponent.DEFAULT_MINING_SPEED).optional().defaulted(1F), ToolComponent::getDefaultMiningSpeed,
            Codec.minInt(0).mapCodec(ToolComponent.DAMAGE_PER_BLOCK).optional().defaulted(1), ToolComponent::getDamagePerBlock,
            Codec.BOOLEAN.mapCodec(ToolComponent.CAN_DESTROY_BLOCKS_IN_CREATIVE).optional().defaulted(true), ToolComponent::isCanDestroyBlocksInCreative,
            ToolComponent::new
    ));
    public final ItemComponent<VillagerVariant> VILLAGER_VARIANT = this.register("villager/variant", Codec.identified(VillagerVariant.values()));
    public final ItemComponent<Identifier> WOLF_VARIANT = this.register("wolf/variant", Codec.STRING_IDENTIFIER.verified(this.registryVerifier.wolfVariant), NetType.IDENTIFIER);
    public final ItemComponent<Identifier> WOLF_SOUND_VARIANT = this.register("wolf/sound_variant", Codec.STRING_IDENTIFIER.verified(this.registryVerifier.wolfSoundVariant), NetType.IDENTIFIER);
    public final ItemComponent<Types_v1_20_5.DyeColor> WOLF_COLLAR = this.register("wolf/collar", Codec.named(Types_v1_20_5.DyeColor.values()), Types_v1_20_5.DyeColor.STREAM_CODEC);
    public final ItemComponent<FoxVariant> FOX_VARIANT = this.register("fox/variant", Codec.named(FoxVariant.values()));
    public final ItemComponent<SalmonSize> SALMON_SIZE = this.register("salmon/size", Codec.named(SalmonSize.values()));
    public final ItemComponent<ParrotVariant> PARROT_VARIANT = this.register("parrot/variant", Codec.named(ParrotVariant.values()));
    public final ItemComponent<TropicalFishPattern> TROPICAL_FISH_PATTERN = this.register("tropical_fish/pattern", Codec.named(TropicalFishPattern.values()));
    public final ItemComponent<Types_v1_20_5.DyeColor> TROPICAL_FISH_BASE_COLOR = this.register("tropical_fish/base_color", Codec.named(Types_v1_20_5.DyeColor.values()), Types_v1_20_5.DyeColor.STREAM_CODEC);
    public final ItemComponent<Types_v1_20_5.DyeColor> TROPICAL_FISH_PATTERN_COLOR = this.register("tropical_fish/pattern_color", Codec.named(Types_v1_20_5.DyeColor.values()), Types_v1_20_5.DyeColor.STREAM_CODEC);
    public final ItemComponent<MooshroomVariant> MOOSHROOM_VARIANT = this.register("mooshroom/variant", Codec.named(MooshroomVariant.values()));
    public final ItemComponent<RabbitVariant> RABBIT_VARIANT = this.register("rabbit/variant", Codec.named(RabbitVariant.values()));
    public final ItemComponent<Identifier> PIG_VARIANT = this.register("pig/variant", Codec.STRING_IDENTIFIER.verified(this.registryVerifier.pigVariant), NetType.IDENTIFIER);
    public final ItemComponent<Identifier> COW_VARIANT = this.register("cow/variant", Codec.STRING_IDENTIFIER.verified(this.registryVerifier.cowVariant), NetType.IDENTIFIER);
    public final ItemComponent<Identifier> CHICKEN_VARIANT = this.register("chicken/variant", Codec.STRING_IDENTIFIER.verified(this.registryVerifier.chickenVariant), NetType.IDENTIFIER);
    public final ItemComponent<Identifier> FROG_VARIANT = this.register("frog/variant", Codec.STRING_IDENTIFIER.verified(this.registryVerifier.frogVariant), NetType.IDENTIFIER);
    public final ItemComponent<HorseVariant> HORSE_VARIANT = this.register("horse/variant", Codec.named(HorseVariant.values()));
    public final ItemComponent<Either<Identifier, PaintingVariant>> PAINTING_VARIANT = this.register("painting/variant", this.typeSerializers.registryEntry(
            this.registryVerifier.paintingVariant,
            MapCodecMerger.codec(
                    Codec.rangedInt(1, 16).mapCodec(PaintingVariant.WIDTH).required(), PaintingVariant::getWidth,
                    Codec.rangedInt(1, 16).mapCodec(PaintingVariant.HEIGHT).required(), PaintingVariant::getHeight,
                    Codec.STRING_IDENTIFIER.mapCodec(PaintingVariant.ASSET_ID).required(), PaintingVariant::getAssetId,
                    TextCodecs_v1_21_5.TEXT.mapCodec(PaintingVariant.TITLE).required(), PaintingVariant::getTitle,
                    TextCodecs_v1_21_5.TEXT.mapCodec(PaintingVariant.AUTHOR).required(), PaintingVariant::getAuthor,
                    PaintingVariant::new
            )
    ));
    public final ItemComponent<LlamaVariant> LLAMA_VARIANT = this.register("llama/variant", Codec.named(LlamaVariant.values()));
    public final ItemComponent<AxolotlVariant> AXOLOTL_VARIANT = this.register("axolotl/variant", Codec.named(AxolotlVariant.values()));
    public final ItemComponent<Identifier> CAT_VARIANT = this.register("cat/variant", Codec.STRING_IDENTIFIER.verified(this.registryVerifier.catVariant), NetType.IDENTIFIER);
    public final ItemComponent<Types_v1_20_5.DyeColor> CAT_COLLAR = this.register("cat/collar", Codec.named(Types_v1_20_5.DyeColor.values()), Types_v1_20_5.DyeColor.STREAM_CODEC);
    public final ItemComponent<Types_v1_20_5.DyeColor> SHEEP_COLOR = this.register("sheep/color", Codec.named(Types_v1_20_5.DyeColor.values()), Types_v1_20_5.DyeColor.STREAM_CODEC);
    public final ItemComponent<Types_v1_20_5.DyeColor> SHULKER_COLOR = this.register("shulker/color", Codec.named(Types_v1_20_5.DyeColor.values()), Types_v1_20_5.DyeColor.STREAM_CODEC);
    public final ItemComponent<BlocksAttacks> BLOCKS_ATTACKS = this.register("blocks_attacks", MapCodecMerger.codec(
            Codec.minFloat(0).mapCodec(BlocksAttacks.BLOCK_DELAY_SECONDS).optional().defaulted(0F), BlocksAttacks::getBlockDelaySeconds,
            Codec.minFloat(0).mapCodec(BlocksAttacks.DISABLE_COOLDOWN_SCALE).optional().defaulted(0F), BlocksAttacks::getDisableCooldownScale,
            MapCodecMerger.codec(
                    Codec.minFloat(1).mapCodec(BlocksAttacks.DamageReduction.HORIZONTAL_BLOCKING_ANGLE).optional().defaulted(90F), BlocksAttacks.DamageReduction::getHorizontalBlockingAngle,
                    this.typeSerializers.tagEntryList(this.registryVerifier.damageTypeTag, this.registryVerifier.damageType).mapCodec(BlocksAttacks.DamageReduction.TYPE).optional().defaulted(null), BlocksAttacks.DamageReduction::getType,
                    Codec.FLOAT.mapCodec(BlocksAttacks.DamageReduction.BASE).required(), BlocksAttacks.DamageReduction::getBase,
                    Codec.FLOAT.mapCodec(BlocksAttacks.DamageReduction.FACTOR).required(), BlocksAttacks.DamageReduction::getFactor,
                    BlocksAttacks.DamageReduction::new
            ).listOf().mapCodec(BlocksAttacks.DAMAGE_REDUCTIONS).optional().defaulted(objects -> objects.size() == 1 && objects.get(0).getHorizontalBlockingAngle() == 90 && objects.get(0).getType() == null && objects.get(0).getBase() == 0 && objects.get(0).getFactor() == 1, () -> Collections.singletonList(new BlocksAttacks.DamageReduction(90, null, 0, 1))), BlocksAttacks::getDamageReductions,
            MapCodecMerger.codec(
                    Codec.minFloat(0).mapCodec(BlocksAttacks.ItemDamageFunction.THRESHOLD).required(), BlocksAttacks.ItemDamageFunction::getThreshold,
                    Codec.FLOAT.mapCodec(BlocksAttacks.ItemDamageFunction.BASE).required(), BlocksAttacks.ItemDamageFunction::getBase,
                    Codec.FLOAT.mapCodec(BlocksAttacks.ItemDamageFunction.FACTOR).required(), BlocksAttacks.ItemDamageFunction::getFactor,
                    BlocksAttacks.ItemDamageFunction::new
            ).mapCodec(BlocksAttacks.ITEM_DAMAGE).optional().defaulted(itemDamageFunction -> itemDamageFunction.getThreshold() == 1 && itemDamageFunction.getBase() == 0 && itemDamageFunction.getFactor() == 1, () -> new BlocksAttacks.ItemDamageFunction(1, 0, 1)), BlocksAttacks::getItemDamage,
            this.typeSerializers.tag(this.registryVerifier.damageTypeTag).mapCodec(BlocksAttacks.BYPASSED_BY).optional().defaulted(null), BlocksAttacks::getBypassedBy,
            this.typeSerializers.soundEvent().mapCodec(BlocksAttacks.BLOCK_SOUND).optional().defaulted(null), BlocksAttacks::getBlockSound,
            this.typeSerializers.soundEvent().mapCodec(BlocksAttacks.DISABLED_SOUND).optional().defaulted(null), BlocksAttacks::getDisabledSound,
            BlocksAttacks::new
    ));
    public final ItemComponent<Either<Identifier, Types_v1_20_5.SoundEvent>> BREAK_SOUND = this.register("break_sound", this.typeSerializers.soundEvent());
    public final ItemComponent<Identifier> PROVIDES_BANNER_PATTERNS = this.register("provides_banner_patterns", this.typeSerializers.tag(this.registryVerifier.bannerPatternTag), NetType.IDENTIFIER);
    public final ItemComponent<Identifier> PROVIDES_TRIM_MATERIAL = this.register("provides_trim_material", this.typeSerializers.tag(this.registryVerifier.armorTrimMaterialTag), NetType.IDENTIFIER);
    public final ItemComponent<TooltipDisplay> TOOLTIP_DISPLAY = this.register("tooltip_display", MapCodecMerger.codec(
            Codec.BOOLEAN.mapCodec(TooltipDisplay.HIDE_TOOLTIP).optional().defaulted(false), TooltipDisplay::isHideTooltip,
            this.getComponentCodec().listOf().mapCodec(TooltipDisplay.HIDDEN_COMPONENTS).optional().defaulted(List::isEmpty, ArrayList::new), TooltipDisplay::getHiddenComponents,
            TooltipDisplay::new
    ));
    public final ItemComponent<AttributeModifiers> ATTRIBUTE_MODIFIERS = this.register("attribute_modifiers", this.typeSerializers.attributeModifier_v1_21().listOf().map(AttributeModifiers::getModifiers, AttributeModifiers::new));
    public final ItemComponent<Integer> DYED_COLOR = this.register("dyed_color", this.typeSerializers.rgbColor(), NetType.INTEGER);
    public final ItemComponent<List<Types_v1_20_5.BlockPredicate>> CAN_PLACE_ON = this.register("can_place_on", this.typeSerializers.blockPredicate().compactListOf());
    public final ItemComponent<List<Types_v1_20_5.BlockPredicate>> CAN_BREAK = this.copy("can_break", this.CAN_PLACE_ON);
    public final ItemComponent<Map<Identifier, Integer>> ENCHANTMENTS = this.register("enchantments", this.typeSerializers.enchantmentLevels(), NetType.map(NetType.IDENTIFIER, NetType.VAR_INT));
    public final ItemComponent<Map<Identifier, Integer>> STORED_ENCHANTMENTS = this.copy("stored_enchantments", this.ENCHANTMENTS);
    public final ItemComponent<Either<Identifier, Types_v1_21.JukeboxPlayable.JukeboxSong>> JUKEBOX_PLAYABLE = this.register("jukebox_playable", this.typeSerializers.registryEntry(this.registryVerifier.jukeboxSong, MapCodecMerger.codec(
            this.typeSerializers.soundEvent().mapCodec(Types_v1_21.JukeboxPlayable.JukeboxSong.SOUND_EVENT).required(), Types_v1_21.JukeboxPlayable.JukeboxSong::getSoundEvent,
            this.typeSerializers.rawTextComponent().mapCodec(Types_v1_21.JukeboxPlayable.JukeboxSong.DESCRIPTION).required(), Types_v1_21.JukeboxPlayable.JukeboxSong::getDescription,
            Codec.minExclusiveFloat(0).mapCodec(Types_v1_21.JukeboxPlayable.JukeboxSong.LENGTH_IN_SECONDS).required(), Types_v1_21.JukeboxPlayable.JukeboxSong::getLengthInSeconds,
            Codec.rangedInt(0, 15).mapCodec(Types_v1_21.JukeboxPlayable.JukeboxSong.COMPARATOR_OUTPUT).required(), Types_v1_21.JukeboxPlayable.JukeboxSong::getComparatorOutput,
            Types_v1_21.JukeboxPlayable.JukeboxSong::new
    )));
    public final ItemComponent<ArmorTrim> TRIM = this.register("trim", MapCodecMerger.codec(
            this.typeSerializers.armorTrimMaterial().mapCodec(ArmorTrim.MATERIAL).required(), ArmorTrim::getMaterial,
            this.typeSerializers.armorTrimPattern().mapCodec(ArmorTrim.PATTERN).required(), ArmorTrim::getPattern,
            ArmorTrim::new
    ));
    public final ItemComponent<Boolean> UNBREAKABLE = this.register("unbreakable", Codec.UNIT, NetType.UNIT);
    private ItemComponent<?> HIDE_ADDITIONAL_TOOLTIP;
    private ItemComponent<?> HIDE_TOOLTIP;

    public ItemComponents_v1_21_5() {
    }

    public ItemComponents_v1_21_5(final RegistryVerifier registryVerifier) {
        super(registryVerifier);
    }

    {
        this.unregister("hide_additional_tooltip");
        this.unregister("hide_tooltip");
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "custom_name", "item_name", "item_model", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "tooltip_display", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "consumable", "use_remainder", "use_cooldown", "damage_resistant", "tool", "weapon", "enchantable", "equippable", "repairable", "glider", "tooltip_style", "death_protection", "blocks_attacks", "stored_enchantments", "dyed_color", "map_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "potion_duration_scale", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "provides_trim_material", "ominous_bottle_amplifier", "jukebox_playable", "provides_banner_patterns", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "lock", "container_loot", "break_sound", "villager/variant", "wolf/variant", "wolf/sound_variant", "wolf/collar", "fox/variant", "salmon/size", "parrot/variant", "tropical_fish/pattern", "tropical_fish/base_color", "tropical_fish/pattern_color", "mooshroom/variant", "rabbit/variant", "pig/variant", "cow/variant", "chicken/variant", "frog/variant", "horse/variant", "painting/variant", "llama/variant", "axolotl/variant", "cat/variant", "cat/collar", "sheep/color", "shulker/color");
    }

}
