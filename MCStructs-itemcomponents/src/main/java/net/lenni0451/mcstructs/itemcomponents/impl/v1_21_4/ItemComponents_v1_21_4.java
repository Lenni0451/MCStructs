package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.MapCodec;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.RegistryVerifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.ItemComponents_v1_21_2;

import java.util.ArrayList;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4.Types_v1_21_4.CustomModelData;

public class ItemComponents_v1_21_4 extends ItemComponents_v1_21_2 {

    private final TypeSerializers_v1_21_4 typeSerializers = new TypeSerializers_v1_21_4(this);

    public final ItemComponent<CustomModelData> CUSTOM_MODEL_DATA = this.register("custom_model_data", MapCodec.of(
            Codec.FLOAT.listOf().mapCodec(CustomModelData.FLOATS).optionalDefault(ArrayList::new), CustomModelData::getFloats,
            Codec.BOOLEAN.listOf().mapCodec(CustomModelData.FLAGS).optionalDefault(ArrayList::new), CustomModelData::getFlags,
            Codec.STRING.listOf().mapCodec(CustomModelData.STRINGS).optionalDefault(ArrayList::new), CustomModelData::getStrings,
            this.typeSerializers.rgbColor().listOf().mapCodec(CustomModelData.COLORS).optionalDefault(ArrayList::new), CustomModelData::getColors,
            CustomModelData::new
    ));


    public ItemComponents_v1_21_4() {
    }

    public ItemComponents_v1_21_4(final RegistryVerifier registryVerifier) {
        super(registryVerifier);
    }

    {
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "custom_name", "item_name", "item_model", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "hide_additional_tooltip", "hide_tooltip", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "consumable", "use_remainder", "use_cooldown", "damage_resistant", "tool", "enchantable", "equippable", "repairable", "glider", "tooltip_style", "death_protection", "stored_enchantments", "dyed_color", "map_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "ominous_bottle_amplifier", "jukebox_playable", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "lock", "container_loot");
    }

}
