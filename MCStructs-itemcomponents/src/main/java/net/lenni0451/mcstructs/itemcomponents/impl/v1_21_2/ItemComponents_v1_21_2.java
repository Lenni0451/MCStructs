package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.MapCodec;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.RegistryVerifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.ItemComponents_v1_21;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.TypeSerializers_v1_21;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_1_21_2.Enchantable;
import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_1_21_2.Repairable;

public class ItemComponents_v1_21_2 extends ItemComponents_v1_21 {

    private final TypeSerializers_v1_21 typeSerializers = new TypeSerializers_v1_21(this);

    public final ItemComponent<Enchantable> ENCHANTABLE = this.register("enchantable", MapCodec.of(
            Codec.minInt(1).mapCodec(Enchantable.VALUE), Enchantable::getValue,
            Enchantable::new
    ));
    public final ItemComponent<Repairable> REPAIRABLE = this.register("repairable", MapCodec.of(
            this.typeSerializers.tagEntryList(this.registryVerifier.itemTag, this.registryVerifier.item).mapCodec(Repairable.ITEMS), Repairable::getItems,
            Repairable::new
    ));


    public ItemComponents_v1_21_2() {
    }

    public ItemComponents_v1_21_2(final RegistryVerifier registryVerifier) {
        super(registryVerifier);
    }

    {
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "custom_name", "item_name", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "hide_additional_tooltip", "hide_tooltip", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "fire_resistant", "tool", "enchantable", "repairable", "stored_enchantments", "dyed_color", "map_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "ominous_bottle_amplifier", "jukebox_playable", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "lock", "container_loot");
    }

}
