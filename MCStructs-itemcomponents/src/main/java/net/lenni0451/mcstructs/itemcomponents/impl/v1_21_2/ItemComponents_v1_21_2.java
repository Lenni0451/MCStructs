package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.RegistryVerifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.ItemComponents_v1_21;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.ArrayList;
import java.util.List;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2.*;
import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2.Food.*;

public class ItemComponents_v1_21_2 extends ItemComponents_v1_21 {

    private final TypeSerializers_v1_21_2 typeSerializers = new TypeSerializers_v1_21_2(this, TextComponentCodec.V1_21_2);

    public final ItemComponent<PotionContents> POTION_CONTENTS = this.register("potion_contents", Codec.oneOf(
            MapCodecMerger.codec(
                    Codec.STRING_IDENTIFIER.verified(this.registryVerifier.potion).mapCodec(PotionContents.POTION).optional().defaulted(null), PotionContents::getPotion,
                    Codec.INTEGER.mapCodec(PotionContents.CUSTOM_COLOR).optional().defaulted(null), PotionContents::getCustomColor,
                    this.typeSerializers.statusEffect().listOf().mapCodec(PotionContents.CUSTOM_EFFECTS).optional().defaulted(List::isEmpty, ArrayList::new), PotionContents::getCustomEffects,
                    Codec.STRING.mapCodec(PotionContents.CUSTOM_NAME).optional().defaulted(null), PotionContents::getCustomName,
                    PotionContents::new
            ),
            Codec.STRING_IDENTIFIER.verified(this.registryVerifier.potion).map(PotionContents::getPotion, id -> new PotionContents(id, null, new ArrayList<>(), null))
    ));
    public final ItemComponent<Identifier> ITEM_MODEL = this.register("item_model", Codec.STRING_IDENTIFIER);
    public final ItemComponent<Food> FOOD = this.register("food", MapCodecMerger.codec(
            Codec.minInt(0).mapCodec(NUTRITION).required(), Food::getNutrition,
            Codec.FLOAT.mapCodec(SATURATION).required(), Food::getSaturation,
            Codec.BOOLEAN.mapCodec(CAN_ALWAYS_EAT).optional().defaulted(false), Food::isCanAlwaysEat,
            Food::new
    ));
    public final ItemComponent<Consumable> CONSUMABLE = this.register("consumable", MapCodecMerger.codec(
            Codec.minFloat(0).mapCodec(Consumable.CONSUME_SECONDS).optional().defaulted(1.6F), Consumable::getConsumeSeconds,
            Codec.named(Consumable.ItemUseAnimation.values()).mapCodec(Consumable.ANIMATION).optional().defaulted(Consumable.ItemUseAnimation.EAT), Consumable::getAnimation,
            this.typeSerializers.soundEvent().mapCodec(Consumable.SOUND).optional().defaulted(Either.left(Identifier.of("entity.generic.eat"))), Consumable::getSound,
            Codec.BOOLEAN.mapCodec(Consumable.HAS_CONSUME_PARTICLES).optional().defaulted(true), Consumable::isHasConsumeParticles,
            this.typeSerializers.consumeEffect().listOf().mapCodec(Consumable.ON_CONSUME_EFFECTS).optional().defaulted(List::isEmpty, ArrayList::new), Consumable::getOnConsumeEffects,
            Consumable::new
    ));
    public final ItemComponent<Types_v1_20_5.ItemStack> USE_REMAINDER = this.register("use_remainder", this.typeSerializers.itemStack());
    public final ItemComponent<UseCooldown> USE_COOLDOWN = this.register("use_cooldown", MapCodecMerger.codec(
            Codec.minExclusiveFloat(0).mapCodec(UseCooldown.SECONDS).required(), UseCooldown::getSeconds,
            Codec.STRING_IDENTIFIER.mapCodec(UseCooldown.COOLDOWN_GROUP).optional().defaulted(null), UseCooldown::getCooldownGroup,
            UseCooldown::new
    ));
    public final ItemComponent<DamageResistant> DAMAGE_RESISTANT = this.register("damage_resistant", MapCodecMerger.codec(
            this.typeSerializers.tag(this.registryVerifier.damageTypeTag).mapCodec(DamageResistant.TYPES).required(), DamageResistant::getTypes,
            DamageResistant::new
    ));
    public final ItemComponent<Enchantable> ENCHANTABLE = this.register("enchantable", MapCodecMerger.codec(
            Codec.minInt(1).mapCodec(Enchantable.VALUE).required(), Enchantable::getValue,
            Enchantable::new
    ));
    public final ItemComponent<Equippable> EQUIPPABLE = this.register("equippable", MapCodecMerger.codec(
            Codec.named(EquipmentSlot.values()).mapCodec(Equippable.SLOT).required(), Equippable::getSlot,
            this.typeSerializers.soundEvent().mapCodec(Equippable.EQUIP_SOUND).optional().defaulted(Either.left(Identifier.of("item.armor.equip_generic"))), Equippable::getEquipSound,
            Codec.STRING_IDENTIFIER.mapCodec(Equippable.MODEL).optional().defaulted(null), Equippable::getModel,
            Codec.STRING_IDENTIFIER.mapCodec(Equippable.CAMERA_OVERLAY).optional().defaulted(null), Equippable::getCameraOverlay,
            this.typeSerializers.tagEntryList(this.registryVerifier.entityTypeTag, this.registryVerifier.entityType).mapCodec(Equippable.ALLOWED_ENTITIES).optional().defaulted(null), Equippable::getAllowedEntities,
            Codec.BOOLEAN.mapCodec(Equippable.DISPENSABLE).optional().defaulted(true), Equippable::isDispensable,
            Codec.BOOLEAN.mapCodec(Equippable.SWAPPABLE).optional().defaulted(true), Equippable::isSwappable,
            Codec.BOOLEAN.mapCodec(Equippable.DAMAGE_ON_HURT).optional().defaulted(true), Equippable::isDamageOnHurt,
            Equippable::new
    ));
    public final ItemComponent<Repairable> REPAIRABLE = this.register("repairable", MapCodecMerger.codec(
            this.typeSerializers.tagEntryList(this.registryVerifier.itemTag, this.registryVerifier.item).mapCodec(Repairable.ITEMS).required(), Repairable::getItems,
            Repairable::new
    ));
    public final ItemComponent<Boolean> GLIDER = this.register("glider", Codec.UNIT);
    public final ItemComponent<Identifier> TOOLTIP_STYLE = this.register("tooltip_style", Codec.STRING_IDENTIFIER);
    public final ItemComponent<DeathProtection> DEATH_PROTECTION = this.register("death_protection", MapCodecMerger.codec(
            this.typeSerializers.consumeEffect().listOf().mapCodec(DeathProtection.DEATH_EFFECTS).optional().defaulted(List::isEmpty, ArrayList::new), DeathProtection::getDeathEffects,
            DeathProtection::new
    ));
    public final ItemComponent<ItemPredicate> LOCK = this.register("lock", this.typeSerializers.itemPredicate());
    private ItemComponent<?> FIRE_RESISTANT;


    public ItemComponents_v1_21_2() {
    }

    public ItemComponents_v1_21_2(final RegistryVerifier registryVerifier) {
        super(registryVerifier);
    }

    {
        this.unregister("fire_resistant");
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "custom_name", "item_name", "item_model", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "hide_additional_tooltip", "hide_tooltip", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "consumable", "use_remainder", "use_cooldown", "damage_resistant", "tool", "enchantable", "equippable", "repairable", "glider", "tooltip_style", "death_protection", "stored_enchantments", "dyed_color", "map_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "ominous_bottle_amplifier", "jukebox_playable", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "lock", "container_loot");
    }

}
