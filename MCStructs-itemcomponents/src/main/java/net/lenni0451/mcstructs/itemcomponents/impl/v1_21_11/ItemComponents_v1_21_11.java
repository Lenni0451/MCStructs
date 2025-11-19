package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.Registries;
import net.lenni0451.mcstructs.itemcomponents.impl.Verifiers;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11.Types_v1_21_11.*;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_9.ItemComponents_v1_21_9;
import net.lenni0451.mcstructs.registry.EitherEntry;
import net.lenni0451.mcstructs.registry.RegistryEntry;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.ArrayList;
import java.util.List;

public class ItemComponents_v1_21_11 extends ItemComponents_v1_21_9 {

    private final TypeSerializers_v1_21_11 typeSerializers = new TypeSerializers_v1_21_11(this, TextComponentCodec.V1_21_9);

    public final ItemComponent<UseEffects> USE_EFFECTS = this.register("use_effects", MapCodecMerger.codec(
            Codec.BOOLEAN.mapCodec(UseEffects.CAN_SPRINT).optional().defaulted(false), UseEffects::isCanSprint,
            Codec.BOOLEAN.mapCodec(UseEffects.INTERACT_VIBRATIONS).optional().defaulted(true), UseEffects::isInteractVibrations,
            Codec.FLOAT.mapCodec(UseEffects.SPEED_MULTIPLIER).optional().defaulted(0.2F), UseEffects::getSpeedMultiplier,
            UseEffects::new
    ));
    public final ItemComponent<Float> MINIMUM_ATTACK_CHARGE = this.register("minimum_attack_charge", Codec.rangedFloat(0, 1));
    public final ItemComponent<EitherEntry<DamageType>> DAMAGE_TYPE = this.register("damage_type", EitherEntry.codec(this.getRegistries().damageType, MapCodecMerger.codec(
            Codec.STRING.mapCodec(DamageType.MESSAGE_ID).required(), DamageType::getMessageId,
            Codec.named(DamageScaling.values()).mapCodec(DamageType.SCALING).required(), DamageType::getScaling,
            Codec.FLOAT.mapCodec(DamageType.EXHAUSTION).required(), DamageType::getExhaustion,
            Codec.named(DamageEffects.values()).mapCodec(DamageType.EFFECTS).optional().defaulted(DamageEffects.HURT), DamageType::getEffects,
            Codec.named(DeathMessageType.values()).mapCodec(DamageType.DEATH_MESSAGE_TYPE).optional().defaulted(DeathMessageType.DEFAULT), DamageType::getDeathMessageType,
            DamageType::new
    )));
    public final ItemComponent<KineticWeapon> KINETIC_WEAPON = this.register("kinetic_weapon", MapCodecMerger.codec(
            Codec.minInt(0).mapCodec(KineticWeapon.CONTACT_COOLDOWN_TICKS).optional().defaulted(10), KineticWeapon::getContactCooldownTicks,
            Codec.minInt(0).mapCodec(KineticWeapon.DELAY_TICKS).optional().defaulted(0), KineticWeapon::getDelayTicks,
            this.typeSerializers.kineticWeaponCondition().mapCodec(KineticWeapon.DISMOUNT_CONDITIONS).optional().defaulted(null), KineticWeapon::getDismountConditions,
            this.typeSerializers.kineticWeaponCondition().mapCodec(KineticWeapon.KNOCKBACK_CONDITIONS).optional().defaulted(null), KineticWeapon::getKnockbackConditions,
            this.typeSerializers.kineticWeaponCondition().mapCodec(KineticWeapon.DAMAGE_CONDITIONS).optional().defaulted(null), KineticWeapon::getDamageConditions,
            Codec.FLOAT.mapCodec(KineticWeapon.FORWARD_MOVEMENT).optional().defaulted(0F), KineticWeapon::getForwardMovement,
            Codec.FLOAT.mapCodec(KineticWeapon.DAMAGE_MULTIPLIER).optional().defaulted(1F), KineticWeapon::getDamageMultiplier,
            this.typeSerializers.soundEvent().mapCodec(KineticWeapon.SOUND).optional().defaulted(null), KineticWeapon::getSound,
            this.typeSerializers.soundEvent().mapCodec(KineticWeapon.HIT_SOUND).optional().defaulted(null), KineticWeapon::getHitSound,
            KineticWeapon::new
    ));
    public final ItemComponent<PiercingWeapon> PIERCING_WEAPON = this.register("piercing_weapon", MapCodecMerger.codec(
            Codec.BOOLEAN.mapCodec(PiercingWeapon.DEALS_KNOCKBACK).optional().defaulted(true), PiercingWeapon::isDealsKnockback,
            Codec.BOOLEAN.mapCodec(PiercingWeapon.DISMOUNTS).optional().defaulted(false), PiercingWeapon::isDismounts,
            this.typeSerializers.soundEvent().mapCodec(PiercingWeapon.SOUND).optional().defaulted(null), PiercingWeapon::getSound,
            this.typeSerializers.soundEvent().mapCodec(PiercingWeapon.HIT_SOUND).optional().defaulted(null), PiercingWeapon::getHitSound,
            PiercingWeapon::new
    ));
    public final ItemComponent<SwingAnimation> SWING_ANIMATION = this.register("swing_animation", MapCodecMerger.codec(
            Codec.named(SwingAnimationType.values()).mapCodec(SwingAnimation.TYPE).optional().defaulted(SwingAnimationType.WHACK), SwingAnimation::getType,
            Codec.minInt(1).mapCodec(SwingAnimation.DURATION).optional().defaulted(6), SwingAnimation::getDuration,
            SwingAnimation::new
    ));
    public final ItemComponent<Consumable> CONSUMABLE = this.register("consumable", MapCodecMerger.codec(
            Codec.minFloat(0).mapCodec(Consumable.CONSUME_SECONDS).optional().defaulted(1.6F), Consumable::getConsumeSeconds,
            Codec.named(Consumable.ItemUseAnimation.values()).mapCodec(Consumable.ANIMATION).optional().defaulted(Consumable.ItemUseAnimation.EAT), Consumable::getAnimation,
            this.typeSerializers.soundEvent().mapCodec(Consumable.SOUND).optional().defaulted(this.registries.sound.getLeftEntry(Identifier.of("entity.generic.eat"))), Consumable::getSound,
            Codec.BOOLEAN.mapCodec(Consumable.HAS_CONSUME_PARTICLES).optional().defaulted(true), Consumable::isHasConsumeParticles,
            this.typeSerializers.consumeEffect().listOf().mapCodec(Consumable.ON_CONSUME_EFFECTS).optional().defaulted(List::isEmpty, ArrayList::new), Consumable::getOnConsumeEffects,
            Consumable::new
    ));
    public final ItemComponent<AttackRange> ATTACK_RANGE = this.register("attack_range", MapCodecMerger.codec(
            Codec.rangedFloat(0, 64).mapCodec(AttackRange.MIN_REACH).optional().defaulted(0F), AttackRange::getMinReach,
            Codec.rangedFloat(0, 64).mapCodec(AttackRange.MAX_REACH).optional().defaulted(3F), AttackRange::getMaxReach,
            Codec.rangedFloat(0, 1).mapCodec(AttackRange.HITBOX_MARGIN).optional().defaulted(0.3F), AttackRange::getHitboxMargin,
            Codec.rangedFloat(0, 2).mapCodec(AttackRange.MOB_FACTOR).optional().defaulted(1F), AttackRange::getMobFactor,
            AttackRange::new
    ));
    public final ItemComponent<RegistryEntry> ZOMBIE_NAUTILUS_VARIANT = this.register("zombie_nautilus/variant", this.registries.zombieNautilusVariant.entryCodec());


    public ItemComponents_v1_21_11() {
    }

    public ItemComponents_v1_21_11(final Registries registries, final Verifiers verifiers) {
        super(registries, verifiers);
    }

    {
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "use_effects", "custom_name", "minimum_attack_charge", "damage_type", "item_name", "item_model", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "tooltip_display", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "consumable", "use_remainder", "use_cooldown", "damage_resistant", "tool", "weapon", "attack_range", "enchantable", "equippable", "repairable", "glider", "tooltip_style", "death_protection", "blocks_attacks", "piercing_weapon", "kinetic_weapon", "swing_animation", "stored_enchantments", "dyed_color", "map_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "potion_duration_scale", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "provides_trim_material", "ominous_bottle_amplifier", "jukebox_playable", "provides_banner_patterns", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "lock", "container_loot", "break_sound", "villager/variant", "wolf/variant", "wolf/sound_variant", "wolf/collar", "fox/variant", "salmon/size", "parrot/variant", "tropical_fish/pattern", "tropical_fish/base_color", "tropical_fish/pattern_color", "mooshroom/variant", "rabbit/variant", "pig/variant", "cow/variant", "chicken/variant", "zombie_nautilus/variant", "frog/variant", "horse/variant", "painting/variant", "llama/variant", "axolotl/variant", "cat/variant", "cat/collar", "sheep/color", "shulker/color");
    }

}
