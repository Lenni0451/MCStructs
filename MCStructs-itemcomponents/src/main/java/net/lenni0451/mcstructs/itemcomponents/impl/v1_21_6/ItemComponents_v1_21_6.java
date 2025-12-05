package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_6;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.Registries;
import net.lenni0451.mcstructs.itemcomponents.impl.Verifiers;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5.ItemComponents_v1_21_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_6.Types_v1_21_6.AttributeModifier;
import net.lenni0451.mcstructs.registry.Holder;
import net.lenni0451.mcstructs.registry.TagEntryList;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.List;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_6.Types_v1_21_6.Equippable;

public class ItemComponents_v1_21_6 extends ItemComponents_v1_21_5 {

    private final TypeSerializers_v1_21_6 typeSerializers = new TypeSerializers_v1_21_6(this, TextComponentCodec.V1_21_6);

    public final ItemComponent<List<AttributeModifier>> ATTRIBUTE_MODIFIERS = this.register("attribute_modifiers", this.typeSerializers.attributeModifier_v1_21_6().listOf());
    public final ItemComponent<Equippable> EQUIPPABLE = this.register("equippable", MapCodecMerger.codec(
            Codec.named(Types_v1_21_2.EquipmentSlot.values()).mapCodec(Equippable.SLOT).required(), Equippable::getSlot,
            this.typeSerializers.soundEvent().mapCodec(Equippable.EQUIP_SOUND).optional().defaulted(new Holder<>(this.registries.sound.getEntry(Identifier.of("item.armor.equip_generic")))), Equippable::getEquipSound,
            Codec.STRING_IDENTIFIER.mapCodec("asset_id").optional().defaulted(null), Equippable::getAssetId,
            Codec.STRING_IDENTIFIER.mapCodec(Equippable.CAMERA_OVERLAY).optional().defaulted(null), Equippable::getCameraOverlay,
            TagEntryList.codec(this.registries.entityType, false).mapCodec(Equippable.ALLOWED_ENTITIES).optional().defaulted(null), Equippable::getAllowedEntities,
            Codec.BOOLEAN.mapCodec(Equippable.DISPENSABLE).optional().defaulted(true), Equippable::isDispensable,
            Codec.BOOLEAN.mapCodec(Equippable.SWAPPABLE).optional().defaulted(true), Equippable::isSwappable,
            Codec.BOOLEAN.mapCodec(Equippable.DAMAGE_ON_HURT).optional().defaulted(true), Equippable::isDamageOnHurt,
            Codec.BOOLEAN.mapCodec(Equippable.EQUIP_ON_INTERACT).optional().defaulted(false), Equippable::isEquipOnInteract,
            Codec.BOOLEAN.mapCodec(Equippable.CAN_BE_SHEARED).optional().defaulted(false), Equippable::isCanBeSheared,
            this.typeSerializers.soundEvent().mapCodec(Equippable.SHEARING_SOUND).optional().defaulted(new Holder<>(this.registries.sound.getEntry(Identifier.of("item.shears.snip")))), Equippable::getEquipSound,
            Equippable::new
    ));


    public ItemComponents_v1_21_6() {
    }

    public ItemComponents_v1_21_6(final Registries registries, final Verifiers verifiers) {
        super(registries, verifiers);
    }

    {
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "custom_name", "item_name", "item_model", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "tooltip_display", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "consumable", "use_remainder", "use_cooldown", "damage_resistant", "tool", "weapon", "enchantable", "equippable", "repairable", "glider", "tooltip_style", "death_protection", "blocks_attacks", "stored_enchantments", "dyed_color", "map_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "potion_duration_scale", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "provides_trim_material", "ominous_bottle_amplifier", "jukebox_playable", "provides_banner_patterns", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "lock", "container_loot", "break_sound", "villager/variant", "wolf/variant", "wolf/sound_variant", "wolf/collar", "fox/variant", "salmon/size", "parrot/variant", "tropical_fish/pattern", "tropical_fish/base_color", "tropical_fish/pattern_color", "mooshroom/variant", "rabbit/variant", "pig/variant", "cow/variant", "chicken/variant", "frog/variant", "horse/variant", "painting/variant", "llama/variant", "axolotl/variant", "cat/variant", "cat/collar", "sheep/color", "shulker/color");
    }

}
