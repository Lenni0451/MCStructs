package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_9;

import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.Registries;
import net.lenni0451.mcstructs.itemcomponents.impl.Verifiers;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_6.ItemComponents_v1_21_6;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

public class ItemComponents_v1_21_9 extends ItemComponents_v1_21_6 {

    private final TypeSerializers_v1_21_9 typeSerializers = new TypeSerializers_v1_21_9(this, TextComponentCodec.V1_21_9);

    public final ItemComponent<Types_v1_20_5.GameProfile> ATTRIBUTE_MODIFIERS = this.register("profile", this.typeSerializers.gameProfile());


    public ItemComponents_v1_21_9() {
    }

    public ItemComponents_v1_21_9(final Registries registries, final Verifiers verifiers) {
        super(registries, verifiers);
    }

    {
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "custom_name", "item_name", "item_model", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "tooltip_display", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "consumable", "use_remainder", "use_cooldown", "damage_resistant", "tool", "weapon", "enchantable", "equippable", "repairable", "glider", "tooltip_style", "death_protection", "blocks_attacks", "stored_enchantments", "dyed_color", "map_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "potion_duration_scale", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "provides_trim_material", "ominous_bottle_amplifier", "jukebox_playable", "provides_banner_patterns", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "lock", "container_loot", "break_sound", "villager/variant", "wolf/variant", "wolf/sound_variant", "wolf/collar", "fox/variant", "salmon/size", "parrot/variant", "tropical_fish/pattern", "tropical_fish/base_color", "tropical_fish/pattern_color", "mooshroom/variant", "rabbit/variant", "pig/variant", "cow/variant", "chicken/variant", "frog/variant", "horse/variant", "painting/variant", "llama/variant", "axolotl/variant", "cat/variant", "cat/collar", "sheep/color", "shulker/color");
    }

}
