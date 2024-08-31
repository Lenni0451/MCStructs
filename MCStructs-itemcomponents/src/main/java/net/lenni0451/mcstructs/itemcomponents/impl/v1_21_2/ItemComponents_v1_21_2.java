package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.Either;
import net.lenni0451.mcstructs.converter.codec.MapCodec;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.RegistryVerifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.ItemComponents_v1_21;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.TypeSerializers_v1_21;

import java.util.ArrayList;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_1_21_2.*;
import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_1_21_2.Food.*;

public class ItemComponents_v1_21_2 extends ItemComponents_v1_21 {

    private final TypeSerializers_v1_21 typeSerializers = new TypeSerializers_v1_21(this);

    public final ItemComponent<Food> FOOD = this.register("food", MapCodec.of(
            Codec.minInt(0).mapCodec(NUTRITION), Food::getNutrition,
            Codec.FLOAT.mapCodec(SATURATION), Food::getSaturation,
            Codec.BOOLEAN.mapCodec(CAN_ALWAYS_EAT).optionalDefault(() -> false), Food::isCanAlwaysEat,
            Food::new
    ));
    public final ItemComponent<Consumable> CONSUMABLE = this.register("consumable", MapCodec.of(
            Codec.minFloat(0).mapCodec(Consumable.CONSUME_SECONDS).optionalDefault(() -> 1.6F), Consumable::getConsumeSeconds,
            Codec.named(Consumable.ItemUseAnimation.values()).mapCodec(Consumable.ANIMATION).optionalDefault(() -> Consumable.ItemUseAnimation.EAT), Consumable::getAnimation,
            this.typeSerializers.soundEvent().mapCodec(Consumable.SOUND).optionalDefault(() -> Either.left(Identifier.of("entity.generic.eat"))), Consumable::getSound,
            Codec.BOOLEAN.mapCodec(Consumable.HAS_CONSUME_PARTICLES).optionalDefault(() -> true), Consumable::isHasConsumeParticles,
            Codec.STRING_IDENTIFIER.verified(this.registryVerifier.consumeEffect).listOf().mapCodec(Consumable.ON_CONSUME_EFFECTS).optionalDefault(ArrayList::new), Consumable::getOnConsumeEffects,
            Consumable::new
    ));
    public final ItemComponent<Types_v1_20_5.ItemStack> USE_REMAINDER = this.register("use_remainder", this.typeSerializers.itemStack());
    public final ItemComponent<UseCooldown> USE_COOLDOWN = this.register("use_cooldown", MapCodec.of(
            Codec.minExclusiveFloat(0).mapCodec(UseCooldown.SECONDS), UseCooldown::getSeconds,
            Codec.STRING_IDENTIFIER.mapCodec(UseCooldown.COOLDOWN_GROUP).optionalDefault(() -> null), UseCooldown::getCooldownGroup,
            UseCooldown::new
    ));
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
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "custom_name", "item_name", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "hide_additional_tooltip", "hide_tooltip", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "consumable", "use_remainder", "use_cooldown", "fire_resistant", "tool", "enchantable", "repairable", "stored_enchantments", "dyed_color", "map_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "ominous_bottle_amplifier", "jukebox_playable", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "lock", "container_loot");
    }

}