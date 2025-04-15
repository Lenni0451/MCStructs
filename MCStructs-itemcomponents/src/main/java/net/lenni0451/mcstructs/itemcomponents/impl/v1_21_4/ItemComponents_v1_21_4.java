package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.Registries;
import net.lenni0451.mcstructs.itemcomponents.impl.Verifiers;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.ItemComponents_v1_21_2;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2;
import net.lenni0451.mcstructs.itemcomponents.registry.EitherEntry;
import net.lenni0451.mcstructs.itemcomponents.registry.TagEntryList;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.ArrayList;
import java.util.List;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4.Types_v1_21_4.CustomModelData;

public class ItemComponents_v1_21_4 extends ItemComponents_v1_21_2 {

    private final TypeSerializers_v1_21_4 typeSerializers = new TypeSerializers_v1_21_4(this, TextComponentCodec.V1_21_4);

    public final ItemComponent<CustomModelData> CUSTOM_MODEL_DATA = this.register("custom_model_data", MapCodecMerger.codec(
            Codec.FLOAT.listOf().mapCodec(CustomModelData.FLOATS).optional().defaulted(List::isEmpty, ArrayList::new), CustomModelData::getFloats,
            Codec.BOOLEAN.listOf().mapCodec(CustomModelData.FLAGS).optional().defaulted(List::isEmpty, ArrayList::new), CustomModelData::getFlags,
            Codec.STRING.listOf().mapCodec(CustomModelData.STRINGS).optional().defaulted(List::isEmpty, ArrayList::new), CustomModelData::getStrings,
            this.typeSerializers.rgbColor().listOf().mapCodec(CustomModelData.COLORS).optional().defaulted(List::isEmpty, ArrayList::new), CustomModelData::getColors,
            CustomModelData::new
    ));
    public final ItemComponent<Types_v1_21_2.Equippable> EQUIPPABLE = this.register("equippable", MapCodecMerger.codec(
            Codec.named(Types_v1_21_2.EquipmentSlot.values()).mapCodec(Types_v1_21_2.Equippable.SLOT).required(), Types_v1_21_2.Equippable::getSlot,
            this.typeSerializers.soundEvent().mapCodec(Types_v1_21_2.Equippable.EQUIP_SOUND).optional().defaulted(new EitherEntry<>(this.registries.sound.getEntry(Identifier.of("item.armor.equip_generic")))), Types_v1_21_2.Equippable::getEquipSound,
            Codec.STRING_IDENTIFIER.mapCodec("asset_id").optional().defaulted(null), Types_v1_21_2.Equippable::getModel,
            Codec.STRING_IDENTIFIER.mapCodec(Types_v1_21_2.Equippable.CAMERA_OVERLAY).optional().defaulted(null), Types_v1_21_2.Equippable::getCameraOverlay,
            TagEntryList.codec(this.registries.entityType, false).mapCodec(Types_v1_21_2.Equippable.ALLOWED_ENTITIES).optional().defaulted(null), Types_v1_21_2.Equippable::getAllowedEntities,
            Codec.BOOLEAN.mapCodec(Types_v1_21_2.Equippable.DISPENSABLE).optional().defaulted(true), Types_v1_21_2.Equippable::isDispensable,
            Codec.BOOLEAN.mapCodec(Types_v1_21_2.Equippable.SWAPPABLE).optional().defaulted(true), Types_v1_21_2.Equippable::isSwappable,
            Codec.BOOLEAN.mapCodec(Types_v1_21_2.Equippable.DAMAGE_ON_HURT).optional().defaulted(true), Types_v1_21_2.Equippable::isDamageOnHurt,
            Types_v1_21_2.Equippable::new
    ));


    public ItemComponents_v1_21_4() {
    }

    public ItemComponents_v1_21_4(final Registries registries, final Verifiers verifiers) {
        super(registries, verifiers);
    }

    {
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "custom_name", "item_name", "item_model", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "hide_additional_tooltip", "hide_tooltip", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "consumable", "use_remainder", "use_cooldown", "damage_resistant", "tool", "enchantable", "equippable", "repairable", "glider", "tooltip_style", "death_protection", "stored_enchantments", "dyed_color", "map_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "ominous_bottle_amplifier", "jukebox_playable", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "lock", "container_loot");
    }

}
