package net.lenni0451.mcstructs.itemcomponents.impl.v26_3;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.impl.Registries;
import net.lenni0451.mcstructs.itemcomponents.impl.Verifiers;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11.Types_v1_21_11.Consumable;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11.Types_v1_21_11.SwingAnimation;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_2.ItemComponents_v26_2;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_3.Types_v26_3.*;
import net.lenni0451.mcstructs.registry.Holder;
import net.lenni0451.mcstructs.registry.RegistryEntry;
import net.lenni0451.mcstructs.registry.TagEntryList;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemComponents_v26_3 extends ItemComponents_v26_2 {

    private final TypeSerializers_v26_3 typeSerializers = new TypeSerializers_v26_3(this, TextComponentCodec.V26_2);


    public final ItemComponent<SwingAnimation> ATTACK_ANIMATION = this.register("attack_animation", this.typeSerializers.swingAnimation());
    public final ItemComponent<SwingAnimation> INTERACT_ANIMATION = this.register("interact_animation", this.typeSerializers.swingAnimation());
    public final ItemComponent<RegistryEntry> BLOCK_TRANSFORMER = this.register("block_transformer", this.registries.blockTransformer.entryCodec());
    public final ItemComponent<VillagerFood> VILLAGER_FOOD = this.register("villager_food", MapCodecMerger.codec(
            Codec.minInt(1).mapCodec(VillagerFood.NUTRITION).required(), VillagerFood::getNutrition,
            VillagerFood::new
    ));
    public final ItemComponent<Compostable> COMPOSTABLE = this.register("compostable", MapCodecMerger.codec(
            this.typeSerializers.resolvableInt().mapCodec(Compostable.LAYERS).required(), Compostable::getLayers,
            Compostable::new
    ));
    public final ItemComponent<CookingFuel> COOKING_FUEL = this.register("cooking_fuel", MapCodecMerger.codec(
            this.typeSerializers.resolvableInt().mapCodec(CookingFuel.BURN_TIME).required(), CookingFuel::getBurnTime,
            this.typeSerializers.resolvableFloat().mapCodec(CookingFuel.SPEED_MULTIPLIER).required(), CookingFuel::getSpeedMultiplier,
            CookingFuel::new
    ));
    public final ItemComponent<BrewingFuel> BREWING_FUEL = this.register("brewing_fuel", MapCodecMerger.codec(
            this.typeSerializers.resolvableInt().mapCodec(BrewingFuel.USES).required(), BrewingFuel::getUses,
            this.typeSerializers.resolvableFloat().mapCodec(BrewingFuel.SPEED_MULTIPLIER).required(), BrewingFuel::getSpeedMultiplier,
            BrewingFuel::new
    ));
    public final ItemComponent<MobVisibility> MOB_VISIBILITY = this.register("mob_visibility", MapCodecMerger.codec(
            TagEntryList.codec(this.registries.entityType, false).mapCodec(MobVisibility.TARGETING_ENTITY_TYPES).required(), MobVisibility::getTargetingEntityTypes,
            Codec.rangedFloat(0F, 10F).mapCodec(MobVisibility.VISIBILITY).required(), MobVisibility::getVisibility,
            MobVisibility::new
    ));
    public final ItemComponent<RegistryEntry> PROVIDES_POTTERY_PATTERN = this.register("provides_pottery_pattern", this.registries.decoratedPotPattern.entryCodec());
    public final ItemComponent<SignText> SIGN_TEXT_FRONT = this.register("sign_text_front", this.typeSerializers.signText());
    public final ItemComponent<SignText> SIGN_TEXT_BACK = this.register("sign_text_back", this.typeSerializers.signText());
    public final ItemComponent<Boolean> WAXED = this.register("waxed", Codec.UNIT);
    public final ItemComponent<Types_v1_20_5.DyeColor> CUSHION_COLOR = this.register("cushion/color", Codec.named(Types_v1_20_5.DyeColor.values()));
    public final ItemComponent<PotDecorations> POT_DECORATIONS = this.register("pot_decorations", MapCodecMerger.codec(
            this.typeSerializers.itemStackTemplate().mapCodec(PotDecorations.BACK).optional().defaulted(null), PotDecorations::getBack,
            this.typeSerializers.itemStackTemplate().mapCodec(PotDecorations.LEFT).optional().defaulted(null), PotDecorations::getLeft,
            this.typeSerializers.itemStackTemplate().mapCodec(PotDecorations.RIGHT).optional().defaulted(null), PotDecorations::getRight,
            this.typeSerializers.itemStackTemplate().mapCodec(PotDecorations.FRONT).optional().defaulted(null), PotDecorations::getFront,
            PotDecorations::new
    ));
    public final ItemComponent<Consumable> CONSUMABLE = this.register("consumable", MapCodecMerger.codec(
            Codec.minFloat(0).mapCodec(Consumable.CONSUME_SECONDS).optional().defaulted(1.6F), Consumable::getConsumeSeconds,
            Codec.named(Consumable.ItemUseAnimation.values()).mapCodec(Consumable.ANIMATION).optional().defaulted(Consumable.ItemUseAnimation.EAT), Consumable::getAnimation,
            this.typeSerializers.soundEvent().mapCodec(Consumable.SOUND).optional().defaulted(this.registries.sound.getHolder(Identifier.of("entity.generic.eat"))), Consumable::getSound,
            Codec.BOOLEAN.mapCodec(Consumable.HAS_CONSUME_PARTICLES).optional().defaulted(true), Consumable::isHasConsumeParticles,
            this.typeSerializers.consumeEffect().listOf().mapCodec(Consumable.ON_CONSUME_EFFECTS).optional().defaulted(List::isEmpty, ArrayList::new), Consumable::getOnConsumeEffects,
            Consumable::new
    ));
    public final ItemComponent<Holder<Instrument>> INSTRUMENT = this.register("instrument", Holder.fileCodec(
            this.registries.instrument,
            MapCodecMerger.codec(
                    this.typeSerializers.soundEvent().mapCodec(Instrument.SOUND_EVENT).required(), Instrument::getSoundEvent,
                    Codec.minFloat(0).mapCodec(Instrument.USE_DURATION).required(), Instrument::getUseDuration,
                    Codec.minExclusiveFloat(0).mapCodec(Instrument.RANGE).required(), Instrument::getRange,
                    Codec.minInt(0).mapCodec(Instrument.DURABILITY_DAMAGE).optional().defaulted(0), Instrument::getDurabilityDamage,
                    this.typeSerializers.getTextComponentCodec().getTextCodec().mapCodec(Instrument.DESCRIPTION).required(), Instrument::getDescription,
                    Instrument::new
            )
    ));
    public final ItemComponent<Holder<ArmorTrimMaterial>> PROVIDES_TRIM_MATERIAL = this.register("provides_trim_material", this.typeSerializers.armorTrimMaterial_v26_3());
    public final ItemComponent<ArmorTrim> TRIM = this.register("trim", MapCodecMerger.codec(
            this.typeSerializers.armorTrimMaterial_v26_3().mapCodec(ArmorTrim.MATERIAL).required(), ArmorTrim::getMaterial,
            this.typeSerializers.armorTrimPattern_v1_21_5().mapCodec(ArmorTrim.PATTERN).required(), ArmorTrim::getPattern,
            ArmorTrim::new
    ));
    private ItemComponent<?> SWING_ANIMATION;
    private ItemComponent<?> MAP_COLOR;

    public ItemComponents_v26_3() {
    }

    public ItemComponents_v26_3(final Registries registries, final Verifiers verifiers) {
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
        this.unregister("swing_animation");
        this.unregister("map_color");
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "use_effects", "custom_name", "minimum_attack_charge", "damage_type", "item_name", "item_model", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "tooltip_display", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "consumable", "use_remainder", "use_cooldown", "damage_resistant", "tool", "weapon", "attack_range", "enchantable", "equippable", "repairable", "glider", "tooltip_style", "death_protection", "blocks_attacks", "piercing_weapon", "kinetic_weapon", "attack_animation", "interact_animation", "additional_trade_cost", "block_transformer", "villager_food", "stored_enchantments", "dye", "dyed_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "potion_duration_scale", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "provides_trim_material", "ominous_bottle_amplifier", "jukebox_playable", "provides_banner_patterns", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "sulfur_cube_content", "lock", "container_loot", "break_sound", "compostable", "cooking_fuel", "brewing_fuel", "mob_visibility", "villager/variant", "wolf/variant", "wolf/sound_variant", "wolf/collar", "fox/variant", "salmon/size", "parrot/variant", "tropical_fish/pattern", "tropical_fish/base_color", "tropical_fish/pattern_color", "mooshroom/variant", "rabbit/variant", "pig/variant", "pig/sound_variant", "cow/variant", "cow/sound_variant", "chicken/variant", "chicken/sound_variant", "zombie_nautilus/variant", "frog/variant", "horse/variant", "painting/variant", "llama/variant", "axolotl/variant", "cat/variant", "cat/sound_variant", "cat/collar", "sheep/color", "shulker/color", "provides_pottery_pattern", "sign_text_front", "sign_text_back", "waxed", "cushion/color");
    }

}
