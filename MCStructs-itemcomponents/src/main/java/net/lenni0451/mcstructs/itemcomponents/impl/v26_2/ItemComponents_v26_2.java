package net.lenni0451.mcstructs.itemcomponents.impl.v26_2;

import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.impl.Registries;
import net.lenni0451.mcstructs.itemcomponents.impl.Verifiers;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_1.ItemComponents_v26_1;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_2.Types_v26_2.SulfurCubeContent;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static net.lenni0451.mcstructs.itemcomponents.impl.v26_2.Types_v26_2.ItemStackTemplate;

public class ItemComponents_v26_2 extends ItemComponents_v26_1 {

    private final TypeSerializers_v26_2 typeSerializers = new TypeSerializers_v26_2(this, TextComponentCodec.V26_2);


    public final ItemComponent<List<ItemStackTemplate>> CHARGED_PROJECTILES = this.register("charged_projectiles", this.typeSerializers.itemStackTemplate().listOf(1024));
    public final ItemComponent<SulfurCubeContent> SULFUR_CUBE_CONTENT = this.register("sulfur_cube_content", this.typeSerializers.itemStackTemplate().map(SulfurCubeContent::getAbsorbedBlockItemStack, SulfurCubeContent::new));

    public ItemComponents_v26_2() {
    }

    public ItemComponents_v26_2(final Registries registries, final Verifiers verifiers) {
        super(registries, verifiers);
    }

    @Override
    public ItemComponentMap getItemDefaults() {
        return new ItemComponentMap(this)
                .set(this.MAX_STACK_SIZE, 64)
                .set(this.LORE, new ArrayList<>())
                .set(this.ENCHANTMENTS, new HashMap<>())
                .set(this.REPAIR_COST, 0)
                .set(this.ATTRIBUTE_MODIFIERS, new ArrayList<>())
                .set(this.RARITY, Types_v1_20_5.Rarity.COMMON);
    }

    {
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "use_effects", "custom_name", "minimum_attack_charge", "damage_type", "item_name", "item_model", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "tooltip_display", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "consumable", "use_remainder", "use_cooldown", "damage_resistant", "tool", "weapon", "attack_range", "enchantable", "equippable", "repairable", "glider", "tooltip_style", "death_protection", "blocks_attacks", "piercing_weapon", "kinetic_weapon", "swing_animation", "additional_trade_cost", "stored_enchantments", "dye", "dyed_color", "map_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "potion_duration_scale", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "provides_trim_material", "ominous_bottle_amplifier", "jukebox_playable", "provides_banner_patterns", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "sulfur_cube_content", "lock", "container_loot", "break_sound", "villager/variant", "wolf/variant", "wolf/sound_variant", "wolf/collar", "fox/variant", "salmon/size", "parrot/variant", "tropical_fish/pattern", "tropical_fish/base_color", "tropical_fish/pattern_color", "mooshroom/variant", "rabbit/variant", "pig/variant", "pig/sound_variant", "cow/variant", "cow/sound_variant", "chicken/variant", "chicken/sound_variant", "zombie_nautilus/variant", "frog/variant", "horse/variant", "painting/variant", "llama/variant", "axolotl/variant", "cat/variant", "cat/sound_variant", "cat/collar", "sheep/color", "shulker/color");
    }

}
