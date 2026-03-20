package net.lenni0451.mcstructs.itemcomponents.impl.v26_1;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.Registries;
import net.lenni0451.mcstructs.itemcomponents.impl.Verifiers;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11.ItemComponents_v1_21_11;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5.Types_v1_21_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_1.Types_v26_1.BlocksAttacks;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_1.Types_v26_1.DamageResistant;
import net.lenni0451.mcstructs.registry.RegistryEntry;
import net.lenni0451.mcstructs.registry.TagEntryList;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.Collections;

public class ItemComponents_v26_1 extends ItemComponents_v1_21_11 {

    private final TypeSerializers_v26_1 typeSerializers = new TypeSerializers_v26_1(this, TextComponentCodec.V26_1);


    public final ItemComponent<Integer> ADDITIONAL_TRADE_COST = this.register("additional_trade_cost", Codec.INTEGER);
    public final ItemComponent<Types_v1_20_5.DyeColor> DYE = this.register("dye", Codec.named(Types_v1_20_5.DyeColor.values()));
    public final ItemComponent<TagEntryList> PROVIDES_BANNER_PATTERNS = this.register("provides_banner_patterns", TagEntryList.codec(this.registries.bannerPattern, false));
    public final ItemComponent<BlocksAttacks> BLOCKS_ATTACKS = this.register("blocks_attacks", MapCodecMerger.codec(
            Codec.minFloat(0).mapCodec(BlocksAttacks.BLOCK_DELAY_SECONDS).optional().defaulted(0F), BlocksAttacks::getBlockDelaySeconds,
            Codec.minFloat(0).mapCodec(BlocksAttacks.DISABLE_COOLDOWN_SCALE).optional().defaulted(0F), BlocksAttacks::getDisableCooldownScale,
            MapCodecMerger.codec(
                    Codec.minFloat(1).mapCodec(Types_v1_21_5.BlocksAttacks.DamageReduction.HORIZONTAL_BLOCKING_ANGLE).optional().defaulted(90F), Types_v1_21_5.BlocksAttacks.DamageReduction::getHorizontalBlockingAngle,
                    TagEntryList.codec(this.registries.damageType, false).mapCodec(Types_v1_21_5.BlocksAttacks.DamageReduction.TYPE).optional().defaulted(null), Types_v1_21_5.BlocksAttacks.DamageReduction::getType,
                    Codec.FLOAT.mapCodec(Types_v1_21_5.BlocksAttacks.DamageReduction.BASE).required(), Types_v1_21_5.BlocksAttacks.DamageReduction::getBase,
                    Codec.FLOAT.mapCodec(Types_v1_21_5.BlocksAttacks.DamageReduction.FACTOR).required(), Types_v1_21_5.BlocksAttacks.DamageReduction::getFactor,
                    Types_v1_21_5.BlocksAttacks.DamageReduction::new
            ).listOf().mapCodec(BlocksAttacks.DAMAGE_REDUCTIONS).optional().defaulted(objects -> objects.size() == 1 && objects.get(0).getHorizontalBlockingAngle() == 90 && objects.get(0).getType() == null && objects.get(0).getBase() == 0 && objects.get(0).getFactor() == 1, () -> Collections.singletonList(new Types_v1_21_5.BlocksAttacks.DamageReduction(90, null, 0, 1))), BlocksAttacks::getDamageReductions,
            MapCodecMerger.codec(
                    Codec.minFloat(0).mapCodec(Types_v1_21_5.BlocksAttacks.ItemDamageFunction.THRESHOLD).required(), Types_v1_21_5.BlocksAttacks.ItemDamageFunction::getThreshold,
                    Codec.FLOAT.mapCodec(Types_v1_21_5.BlocksAttacks.ItemDamageFunction.BASE).required(), Types_v1_21_5.BlocksAttacks.ItemDamageFunction::getBase,
                    Codec.FLOAT.mapCodec(Types_v1_21_5.BlocksAttacks.ItemDamageFunction.FACTOR).required(), Types_v1_21_5.BlocksAttacks.ItemDamageFunction::getFactor,
                    Types_v1_21_5.BlocksAttacks.ItemDamageFunction::new
            ).mapCodec(BlocksAttacks.ITEM_DAMAGE).optional().defaulted(itemDamageFunction -> itemDamageFunction.getThreshold() == 1 && itemDamageFunction.getBase() == 0 && itemDamageFunction.getFactor() == 1, () -> new Types_v1_21_5.BlocksAttacks.ItemDamageFunction(1, 0, 1)), BlocksAttacks::getItemDamage,
            TagEntryList.codec(this.registries.damageType, false).mapCodec(BlocksAttacks.BYPASSED_BY).optional().defaulted(null), BlocksAttacks::getBypassedBy,
            this.typeSerializers.soundEvent().mapCodec(BlocksAttacks.BLOCK_SOUND).optional().defaulted(null), BlocksAttacks::getBlockSound,
            this.typeSerializers.soundEvent().mapCodec(BlocksAttacks.DISABLED_SOUND).optional().defaulted(null), BlocksAttacks::getDisabledSound,
            BlocksAttacks::new
    ));
    public final ItemComponent<DamageResistant> DAMAGE_RESISTANT = this.register("damage_resistant", MapCodecMerger.codec(
            TagEntryList.codec(this.registries.damageType, false).mapCodec(DamageResistant.TYPES).required(), DamageResistant::getTypes,
            DamageResistant::new
    ));
    public final ItemComponent<RegistryEntry> PIG_SOUND_VARIANT = this.register("pig/sound_variant", this.registries.pigSoundVariant.entryCodec());
    public final ItemComponent<RegistryEntry> COW_SOUND_VARIANT = this.register("cow/sound_variant", this.registries.cowSoundVariant.entryCodec());
    public final ItemComponent<RegistryEntry> CHICKEN_SOUND_VARIANT = this.register("chicken/sound_variant", this.registries.chickenSoundVariant.entryCodec());
    public final ItemComponent<RegistryEntry> CAT_SOUND_VARIANT = this.register("cat/sound_variant", this.registries.catSoundVariant.entryCodec());

    public ItemComponents_v26_1() {
    }

    public ItemComponents_v26_1(final Registries registries, final Verifiers verifiers) {
        super(registries, verifiers);
    }

    {
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "use_effects", "custom_name", "minimum_attack_charge", "damage_type", "item_name", "item_model", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "tooltip_display", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "consumable", "use_remainder", "use_cooldown", "damage_resistant", "tool", "weapon", "attack_range", "enchantable", "equippable", "repairable", "glider", "tooltip_style", "death_protection", "blocks_attacks", "piercing_weapon", "kinetic_weapon", "swing_animation", "additional_trade_cost", "stored_enchantments", "dye", "dyed_color", "map_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "potion_duration_scale", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "provides_trim_material", "ominous_bottle_amplifier", "jukebox_playable", "provides_banner_patterns", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "lock", "container_loot", "break_sound", "villager/variant", "wolf/variant", "wolf/sound_variant", "wolf/collar", "fox/variant", "salmon/size", "parrot/variant", "tropical_fish/pattern", "tropical_fish/base_color", "tropical_fish/pattern_color", "mooshroom/variant", "rabbit/variant", "pig/variant", "pig/sound_variant", "cow/variant", "cow/sound_variant", "chicken/variant", "chicken/sound_variant", "zombie_nautilus/variant", "frog/variant", "horse/variant", "painting/variant", "llama/variant", "axolotl/variant", "cat/variant", "cat/sound_variant", "cat/collar", "sheep/color", "shulker/color");
    }

}
