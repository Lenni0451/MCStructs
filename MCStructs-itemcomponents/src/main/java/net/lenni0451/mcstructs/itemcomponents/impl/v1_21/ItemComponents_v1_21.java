package net.lenni0451.mcstructs.itemcomponents.impl.v1_21;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.impl.RegistryVerifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.ItemComponents_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21.AttributeModifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21.AttributeModifiers;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21.Food;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21.JukeboxPlayable;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemComponents_v1_21 extends ItemComponents_v1_20_5 {

    private final TypeSerializers_v1_21 typeSerializers = new TypeSerializers_v1_21(this, TextComponentCodec.V1_20_5);

    public final ItemComponent<Food> FOOD = this.register("food", MapCodecMerger.codec(
            Codec.minInt(0).mapCodec(Food.NUTRITION).required(), Food::getNutrition,
            Codec.FLOAT.mapCodec(Food.SATURATION).required(), Food::getSaturation,
            Codec.BOOLEAN.mapCodec(Food.CAN_ALWAYS_EAT).optional().defaulted(false), Food::isCanAlwaysEat,
            Codec.minExclusiveFloat(0).mapCodec(Food.EAT_SECONDS).optional().defaulted(1.6F), Food::getEatSeconds,
            this.typeSerializers.itemStack().mapCodec(Food.USING_CONVERTS_TO).optional().defaulted(null), Food::getUsingConvertsTo,
            MapCodecMerger.codec(
                    this.typeSerializers.statusEffect().mapCodec(Types_v1_20_5.Food.Effect.EFFECT).required(), Types_v1_20_5.Food.Effect::getEffect,
                    Codec.rangedFloat(0, 1).mapCodec(Types_v1_20_5.Food.Effect.PROBABILITY).optional().defaulted(1F), Types_v1_20_5.Food.Effect::getProbability,
                    Types_v1_20_5.Food.Effect::new
            ).listOf().mapCodec(Food.EFFECTS).optional().defaulted(List::isEmpty, ArrayList::new), Food::getEffects,
            Food::new
    ));
    public final ItemComponent<JukeboxPlayable> JUKEBOX_PLAYABLE = this.register("jukebox_playable", MapCodecMerger.codec(
            this.typeSerializers.registryEntry(this.registryVerifier.jukeboxSong, MapCodecMerger.codec(
                    this.typeSerializers.soundEvent().mapCodec(JukeboxPlayable.JukeboxSong.SOUND_EVENT).required(), JukeboxPlayable.JukeboxSong::getSoundEvent,
                    this.typeSerializers.rawTextComponent().mapCodec(JukeboxPlayable.JukeboxSong.DESCRIPTION).required(), JukeboxPlayable.JukeboxSong::getDescription,
                    Codec.minExclusiveFloat(0).mapCodec(JukeboxPlayable.JukeboxSong.LENGTH_IN_SECONDS).required(), JukeboxPlayable.JukeboxSong::getLengthInSeconds,
                    Codec.rangedInt(0, 15).mapCodec(JukeboxPlayable.JukeboxSong.COMPARATOR_OUTPUT).required(), JukeboxPlayable.JukeboxSong::getComparatorOutput,
                    JukeboxPlayable.JukeboxSong::new
            )).mapCodec(JukeboxPlayable.SONG).required(), JukeboxPlayable::getSong,
            Codec.BOOLEAN.mapCodec(JukeboxPlayable.SHOW_IN_TOOLTIP).optional().defaulted(true), JukeboxPlayable::isShowInTooltip,
            JukeboxPlayable::new
    ));
    public final ItemComponent<AttributeModifiers> ATTRIBUTE_MODIFIERS = this.register("attribute_modifiers", Codec.oneOf(
            MapCodecMerger.codec(
                    this.typeSerializers.attributeModifier_v1_21().listOf().mapCodec(AttributeModifiers.MODIFIERS).required(), AttributeModifiers::getModifiers,
                    Codec.BOOLEAN.mapCodec(AttributeModifiers.SHOW_IN_TOOLTIP).optional().defaulted(true), AttributeModifiers::isShowInTooltip,
                    AttributeModifiers::new
            ),
            this.typeSerializers.attributeModifier_v1_21().flatMap(modifiers -> Result.error("Can't encode single attribute modifier"), modifier -> {
                AttributeModifiers attributeModifiers = new AttributeModifiers();
                List<AttributeModifier> list = new ArrayList<>();
                list.add(modifier);
                attributeModifiers.setModifiers(list);
                return Result.success(attributeModifiers);
            })
    ));


    public ItemComponents_v1_21() {
    }

    public ItemComponents_v1_21(final RegistryVerifier registryVerifier) {
        super(registryVerifier);
    }

    {
        this.sort("custom_data", "max_stack_size", "max_damage", "damage", "unbreakable", "custom_name", "item_name", "lore", "rarity", "enchantments", "can_place_on", "can_break", "attribute_modifiers", "custom_model_data", "hide_additional_tooltip", "hide_tooltip", "repair_cost", "creative_slot_lock", "enchantment_glint_override", "intangible_projectile", "food", "fire_resistant", "tool", "stored_enchantments", "dyed_color", "map_color", "map_id", "map_decorations", "map_post_processing", "charged_projectiles", "bundle_contents", "potion_contents", "suspicious_stew_effects", "writable_book_content", "written_book_content", "trim", "debug_stick_state", "entity_data", "bucket_entity_data", "block_entity_data", "instrument", "ominous_bottle_amplifier", "jukebox_playable", "recipes", "lodestone_tracker", "firework_explosion", "fireworks", "profile", "note_block_sound", "banner_patterns", "base_color", "pot_decorations", "container", "block_state", "bees", "lock", "container_loot");
    }

    @Override
    public ItemComponentMap getItemDefaults() {
        return new ItemComponentMap(this)
                .set(this.MAX_STACK_SIZE, 64)
                .set(this.LORE, new ArrayList<>())
                .set(this.ENCHANTMENTS, new Types_v1_20_5.Enchantments(new HashMap<>(), true))
                .set(this.REPAIR_COST, 0)
                .set(this.ATTRIBUTE_MODIFIERS, new AttributeModifiers(new ArrayList<>(), true))
                .set(this.RARITY, Types_v1_20_5.Rarity.COMMON);
    }

}
