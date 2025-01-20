package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.RegistryVerifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4.ItemComponents_v1_21_4;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4.TypeSerializers_v1_21_4;
import net.lenni0451.mcstructs.text.serializer.v1_21_5.TextCodecs_v1_21_5;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5.Types_v1_21_5.*;

public class ItemComponents_v1_21_5 extends ItemComponents_v1_21_4 {

    private final TypeSerializers_v1_21_4 typeSerializers = new TypeSerializers_v1_21_4(this);

    public final ItemComponent<Weapon> WEAPON = this.register("weapon", MapCodecMerger.codec(
            Codec.minInt(0).mapCodec(Weapon.ITEM_DAMAGE_PER_ATTACK).optional().defaulted(1), Weapon::getItemDamagePerAttack,
            Codec.BOOLEAN.mapCodec(Weapon.CAN_DISABLE_BLOCKING).optional().defaulted(false), Weapon::isCanDisableBlocking,
            Weapon::new
    ));
    public final ItemComponent<Float> POTION_DURATION_SCALE = this.register("potion_duration_scale", Codec.minFloat(0));
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
    public final ItemComponent<VillagerVariant> VILLAGER_VARIANT = this.register("villager/variant", Codec.named(VillagerVariant.values()));
    public final ItemComponent<Either<Identifier, WolfVariant>> WOLF_VARIANT = this.register("wolf/variant", this.typeSerializers.registryEntry(
            this.registryVerifier.wolfVariant,
            MapCodecMerger.codec(
                    Codec.STRING_IDENTIFIER.mapCodec(WolfVariant.WILD_TEXTURE).required(), WolfVariant::getWildTexture,
                    Codec.STRING_IDENTIFIER.mapCodec(WolfVariant.TAME_TEXTURE).required(), WolfVariant::getTameTexture,
                    Codec.STRING_IDENTIFIER.mapCodec(WolfVariant.ANGRY_TEXTURE).required(), WolfVariant::getAngryTexture,
                    this.typeSerializers.tagEntryList(this.registryVerifier.biomeTag, this.registryVerifier.biome).mapCodec(WolfVariant.BIOMES).required(), WolfVariant::getBiomes,
                    WolfVariant::new
            )
    ));
    public final ItemComponent<Types_v1_20_5.DyeColor> WOLF_COLLAR = this.register("wolf/collar", Codec.named(Types_v1_20_5.DyeColor.values()));
    public final ItemComponent<FoxVariant> FOX_VARIANT = this.register("fox/variant", Codec.named(FoxVariant.values()));
    public final ItemComponent<SalmonSize> SALMON_SIZE = this.register("salmon/size", Codec.named(SalmonSize.values()));
    public final ItemComponent<ParrotVariant> PARROT_VARIANT = this.register("parrot/variant", Codec.named(ParrotVariant.values()));
    public final ItemComponent<TropicalFishPattern> TROPICAL_FISH_PATTERN = this.register("tropical_fish/pattern", Codec.named(TropicalFishPattern.values()));
    public final ItemComponent<Types_v1_20_5.DyeColor> TROPICAL_FISH_BASE_COLOR = this.register("tropical_fish/base_color", Codec.named(Types_v1_20_5.DyeColor.values()));
    public final ItemComponent<Types_v1_20_5.DyeColor> TROPICAL_FISH_PATTERN_COLOR = this.register("tropical_fish/pattern_color", Codec.named(Types_v1_20_5.DyeColor.values()));
    public final ItemComponent<MooshroomVariant> MOOSHROOM_VARIANT = this.register("mooshroom/variant", Codec.named(MooshroomVariant.values()));
    public final ItemComponent<RabbitVariant> RABBIT_VARIANT = this.register("rabbit/variant", Codec.named(RabbitVariant.values()));
    public final ItemComponent<Either<Identifier, PigVariant>> PIG_VARIANT = this.register("pig/variant", this.typeSerializers.registryEntry(
            this.registryVerifier.pigVariant,
            MapCodecMerger.codec(
                    Codec.named(PigVariant.ModelType.values()).mapCodec(PigVariant.MODEL).optional().defaulted(PigVariant.ModelType.NORMAL), PigVariant::getModel,
                    Codec.STRING_IDENTIFIER.mapCodec(PigVariant.TEXTURE).required(), PigVariant::getTexture,
                    this.typeSerializers.tagEntryList(this.registryVerifier.biomeTag, this.registryVerifier.biome).mapCodec(PigVariant.BIOMES).optional().defaulted(null), PigVariant::getBiomes,
                    PigVariant::new
            )
    ));
    public final ItemComponent<Identifier> FROG_VARIANT = this.register("frog/variant", Codec.STRING_IDENTIFIER.verified(this.registryVerifier.frogVariant));
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
    public final ItemComponent<Identifier> CAT_VARIANT = this.register("cat/variant", Codec.STRING_IDENTIFIER.verified(this.registryVerifier.catVariant));
    public final ItemComponent<Types_v1_20_5.DyeColor> CAT_COLLAR = this.register("cat/collar", Codec.named(Types_v1_20_5.DyeColor.values()));
    public final ItemComponent<Types_v1_20_5.DyeColor> SHEEP_COLOR = this.register("sheep/color", Codec.named(Types_v1_20_5.DyeColor.values()));
    public final ItemComponent<Types_v1_20_5.DyeColor> SHULKER_COLOR = this.register("shulker/color", Codec.named(Types_v1_20_5.DyeColor.values()));


    public ItemComponents_v1_21_5() {
    }

    public ItemComponents_v1_21_5(final RegistryVerifier registryVerifier) {
        super(registryVerifier);
    }

    {
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "custom_name", "item_name", "item_model", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "hide_additional_tooltip", "hide_tooltip", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "consumable", "use_remainder", "use_cooldown", "damage_resistant", "tool", "weapon", "enchantable", "equippable", "repairable", "glider", "tooltip_style", "death_protection", "stored_enchantments", "dyed_color", "map_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "potion_duration_scale", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "ominous_bottle_amplifier", "jukebox_playable", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "lock", "container_loot", "villager/variant", "wolf/variant", "wolf/collar", "fox/variant", "salmon/size", "parrot/variant", "tropical_fish/pattern", "tropical_fish/base_color", "tropical_fish/pattern_color", "mooshroom/variant", "rabbit/variant", "pig/variant", "frog/variant", "horse/variant", "painting/variant", "llama/variant", "axolotl/variant", "cat/variant", "cat/collar", "sheep/color", "shulker/color");
    }

}
