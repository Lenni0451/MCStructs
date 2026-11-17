package net.lenni0451.mcstructs.itemcomponents.impl.v26_4;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.Registries;
import net.lenni0451.mcstructs.itemcomponents.impl.Verifiers;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11.Types_v1_21_11.Consumable;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_6.Types_v1_21_6.Equippable;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_3.ItemComponents_v26_3;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_4.Types_v26_4.DispenseBehavior;
import net.lenni0451.mcstructs.registry.Holder;
import net.lenni0451.mcstructs.registry.TagEntryList;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.ArrayList;
import java.util.List;

public class ItemComponents_v26_4 extends ItemComponents_v26_3 {

    private final TypeSerializers_v26_4 typeSerializers = new TypeSerializers_v26_4(this, TextComponentCodec.V26_2);


    public final ItemComponent<Consumable> CONSUMABLE = this.register("consumable", MapCodecMerger.codec(
            Codec.minFloat(0).mapCodec(Consumable.CONSUME_SECONDS).optional().defaulted(1.6F), Consumable::getConsumeSeconds,
            Codec.named(Consumable.ItemUseAnimation.values()).mapCodec(Consumable.ANIMATION).optional().defaulted(Consumable.ItemUseAnimation.EAT), Consumable::getAnimation,
            this.typeSerializers.soundEvent().mapCodec(Consumable.SOUND).optional().defaulted(null), Consumable::getSound,
            Codec.BOOLEAN.mapCodec(Consumable.HAS_CONSUME_PARTICLES).optional().defaulted(true), Consumable::isHasConsumeParticles,
            this.typeSerializers.consumeEffect().listOf().mapCodec(Consumable.ON_CONSUME_EFFECTS).optional().defaulted(List::isEmpty, ArrayList::new), Consumable::getOnConsumeEffects,
            Consumable::new
    ));
    public final ItemComponent<Equippable> EQUIPPABLE = this.register("equippable", MapCodecMerger.codec(
            Codec.named(Types_v1_21_2.EquipmentSlot.values()).mapCodec(Equippable.SLOT).required(), Equippable::getSlot,
            this.typeSerializers.soundEvent().mapCodec(Equippable.EQUIP_SOUND).optional().defaulted(null), Equippable::getEquipSound,
            Codec.STRING_IDENTIFIER.mapCodec(Equippable.ASSET_ID).optional().defaulted(null), Equippable::getAssetId,
            Codec.STRING_IDENTIFIER.mapCodec(Equippable.CAMERA_OVERLAY).optional().defaulted(null), Equippable::getCameraOverlay,
            TagEntryList.codec(this.registries.entityType, false).mapCodec(Equippable.ALLOWED_ENTITIES).optional().defaulted(null), Equippable::getAllowedEntities,
            Codec.BOOLEAN.mapCodec(Equippable.DISPENSABLE).optional().defaulted(true), Equippable::isDispensable,
            Codec.BOOLEAN.mapCodec(Equippable.SWAPPABLE).optional().defaulted(true), Equippable::isSwappable,
            Codec.BOOLEAN.mapCodec(Equippable.DAMAGE_ON_HURT).optional().defaulted(true), Equippable::isDamageOnHurt,
            Codec.BOOLEAN.mapCodec(Equippable.EQUIP_ON_INTERACT).optional().defaulted(false), Equippable::isEquipOnInteract,
            Codec.BOOLEAN.mapCodec(Equippable.CAN_BE_SHEARED).optional().defaulted(false), Equippable::isCanBeSheared,
            this.typeSerializers.soundEvent().mapCodec(Equippable.SHEARING_SOUND).optional().defaulted(null), Equippable::getShearingSound,
            Equippable::new
    ));
    public final ItemComponent<Holder<List<DispenseBehavior>>> DISPENSE_BEHAVIOR = this.register("dispense_behavior", this.typeSerializers.dispenseBehaviors());

    public ItemComponents_v26_4() {
    }

    public ItemComponents_v26_4(final Registries registries, final Verifiers verifiers) {
        super(registries, verifiers);
    }

    {
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "use_effects", "custom_name", "minimum_attack_charge", "damage_type", "item_name", "item_model", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "tooltip_display", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "consumable", "use_remainder", "use_cooldown", "damage_resistant", "tool", "weapon", "attack_range", "enchantable", "equippable", "repairable", "glider", "tooltip_style", "death_protection", "blocks_attacks", "piercing_weapon", "kinetic_weapon", "attack_animation", "interact_animation", "additional_trade_cost", "block_transformer", "villager_food", "dispense_behavior", "stored_enchantments", "dye", "dyed_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "potion_duration_scale", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "provides_trim_material", "ominous_bottle_amplifier", "jukebox_playable", "provides_banner_patterns", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "sulfur_cube_content", "lock", "container_loot", "break_sound", "compostable", "cooking_fuel", "brewing_fuel", "mob_visibility", "villager/variant", "wolf/variant", "wolf/sound_variant", "wolf/collar", "fox/variant", "salmon/size", "parrot/variant", "tropical_fish/pattern", "tropical_fish/base_color", "tropical_fish/pattern_color", "mooshroom/variant", "rabbit/variant", "pig/variant", "pig/sound_variant", "cow/variant", "cow/sound_variant", "chicken/variant", "chicken/sound_variant", "zombie_nautilus/variant", "frog/variant", "horse/variant", "painting/variant", "llama/variant", "axolotl/variant", "cat/variant", "cat/sound_variant", "cat/collar", "sheep/color", "shulker/color", "provides_pottery_pattern", "sign_text_front", "sign_text_back", "waxed", "cushion/color");
    }

}
