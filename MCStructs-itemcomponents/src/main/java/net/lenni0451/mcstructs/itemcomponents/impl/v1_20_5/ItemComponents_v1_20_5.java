package net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.ConstructorCodec;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.RegistryVerifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.ATextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5.*;

public class ItemComponents_v1_20_5 extends ItemComponentRegistry {

    private final TypeSerializers_v1_20_5 typeSerializers = new TypeSerializers_v1_20_5(this);

    public final ItemComponent<CompoundTag> CUSTOM_DATA = register("custom_data", this.typeSerializers.COMPOUND_TAG);
    public final ItemComponent<Integer> MAX_STACK_SIZE = register("max_stack_size", Codec.rangedInt(1, 99));
    public final ItemComponent<Integer> MAX_DAMAGE = register("max_damage", Codec.minInt(1));
    public final ItemComponent<Integer> DAMAGE = register("damage", Codec.minInt(0));
    public final ItemComponent<Unbreakable> UNBREAKABLE = register("unbreakable", ConstructorCodec.of(
            Codec.BOOLEAN.mapCodec(Unbreakable.SHOW_IN_TOOLTIP).optionalDefault(() -> true), Unbreakable::isShowInTooltip,
            Unbreakable::new
    ));
    public final ItemComponent<ATextComponent> CUSTOM_NAME = register("custom_name", this.typeSerializers.TEXT_COMPONENT);
    public final ItemComponent<ATextComponent> ITEM_NAME = register("item_name", this.typeSerializers.TEXT_COMPONENT);
    public final ItemComponent<List<ATextComponent>> LORE = register("lore", this.typeSerializers.TEXT_COMPONENT.listOf(256));
    public final ItemComponent<Rarity> RARITY = this.register("rarity", Codec.named(Rarity.values()));
    public final ItemComponent<Enchantments> ENCHANTMENTS = register("enchantments", Codec.oneOf(
            ConstructorCodec.of(
                    this.typeSerializers.ENCHANTMENT_LEVELS.mapCodec(Enchantments.LEVELS), Enchantments::getEnchantments,
                    Codec.BOOLEAN.mapCodec(Enchantments.SHOW_IN_TOOLTIP).optionalDefault(() -> true), Enchantments::isShowInTooltip,
                    Enchantments::new
            ),
            this.typeSerializers.ENCHANTMENT_LEVELS.map(Enchantments::getEnchantments, map -> new Enchantments(map, true))
    ));
    //TODO: can_place_on
    //TODO: can_break
    //TODO: attribute_modifiers
    public final ItemComponent<Integer> CUSTOM_MODEL_DATA = register("custom_model_data", Codec.INTEGER);
    public final ItemComponent<Boolean> HIDE_ADDITIONAL_TOOLTIP = register("hide_additional_tooltip", Codec.UNIT);
    public final ItemComponent<Boolean> HIDE_TOOLTIP = register("hide_tooltip", Codec.UNIT);
    public final ItemComponent<Integer> REPAIR_COST = register("repair_cost", Codec.minInt(0));
    public final ItemComponent<Boolean> CREATIVE_SLOT_LOCK = registerNonSerializable("creative_slot_lock"); //No json/nbt serialization
    public final ItemComponent<Boolean> ENCHANTMENT_GLINT_OVERRIDE = register("enchantment_glint_override", Codec.BOOLEAN);
    public final ItemComponent<Boolean> INTANGIBLE_PROJECTILE = register("intangible_projectile", Codec.UNIT);
    //TODO: food
    public final ItemComponent<Boolean> FIRE_RESISTANT = register("fire_resistant", Codec.UNIT);
    //TODO: tool
    public final ItemComponent<Enchantments> STORED_ENCHANTMENTS = copy("stored_enchantments", this.ENCHANTMENTS);
    public final ItemComponent<DyedColor> DYED_COLOR = register("dyed_color", ConstructorCodec.of(
            Codec.INTEGER.mapCodec(DyedColor.RGB), DyedColor::getRgb,
            Codec.BOOLEAN.mapCodec(DyedColor.SHOW_IN_TOOLTIP).optionalDefault(() -> true), DyedColor::isShowInTooltip,
            DyedColor::new
    ));
    public final ItemComponent<Integer> MAP_COLOR = register("map_color", Codec.INTEGER);
    public final ItemComponent<Integer> MAP_ID = register("map_id", Codec.INTEGER);
    public final ItemComponent<Map<String, MapDecoration>> MAP_DECORATIONS = register("map_decorations", Codec.mapOf(Codec.STRING, ConstructorCodec.of(
            Codec.STRING_IDENTIFIER.verified(this.getRegistryVerifier().mapDecorationType).mapCodec(MapDecoration.TYPE), MapDecoration::getType,
            Codec.DOUBLE.mapCodec(MapDecoration.X), MapDecoration::getX,
            Codec.DOUBLE.mapCodec(MapDecoration.Z), MapDecoration::getZ,
            Codec.FLOAT.mapCodec(MapDecoration.ROTATION), MapDecoration::getRotation,
            MapDecoration::new
    )));
    public final ItemComponent<Integer> MAP_POST_PROCESSING = registerNonSerializable("map_post_processing");
    public final ItemComponent<List<ItemStack>> CHARGED_PROJECTILES = register("charged_projectiles", this.typeSerializers.ITEM_STACK.listOf());
    public final ItemComponent<List<ItemStack>> BUNDLE_CONTENTS = register("bundle_contents", this.typeSerializers.ITEM_STACK.listOf());
    //TODO: potion_contents
    //TODO: suspicious_stew_effects
    public final ItemComponent<WritableBook> WRITABLE_BOOK_CONTENT = register("writable_book_content", ConstructorCodec.of(
            this.typeSerializers.WRITABLE_BOOK_PAGE.listOf().mapCodec(WritableBook.PAGES).defaulted(ArrayList::new, List::isEmpty), WritableBook::getPages,
            WritableBook::new
    ));
    //TODO: written_book_content
    //TODO: trim
    //TODO: debug_stick_state
    //TODO: entity_data
    //TODO: bucket_entity_data
    //TODO: block_entity_data
    //TODO: instrument
    public final ItemComponent<Integer> OMINOUS_BOTTLE_AMPLIFIER = register("ominous_bottle_amplifier", Codec.rangedInt(1, 4));
    public final ItemComponent<List<Identifier>> RECIPES = register("recipes", Codec.STRING_IDENTIFIER.listOf());
    //TODO: lodestone_tracker
    public final ItemComponent<FireworkExplosions> FIREWORK_EXPLOSION = this.register("firework_explosion", ConstructorCodec.of(
            Codec.named(FireworkExplosions.ExplosionShape.values()).mapCodec(FireworkExplosions.SHAPE), FireworkExplosions::getShape,
            Codec.INT_ARRAY.mapCodec(FireworkExplosions.COLORS).defaulted(() -> new int[0], array -> array.length == 0), FireworkExplosions::getColors,
            Codec.INT_ARRAY.mapCodec(FireworkExplosions.FADE_COLORS).defaulted(() -> new int[0], array -> array.length == 0), FireworkExplosions::getFadeColors,
            Codec.BOOLEAN.mapCodec(FireworkExplosions.HAS_TRAIL).optionalDefault(() -> false), FireworkExplosions::isHasTrail,
            Codec.BOOLEAN.mapCodec(FireworkExplosions.HAS_TWINKLE).optionalDefault(() -> false), FireworkExplosions::isHasTwinkle,
            FireworkExplosions::new
    ));
    //TODO: fireworks
    //TODO: profile
    public final ItemComponent<Identifier> NOTE_BLOCK_SOUND = this.register("note_block_sound", Codec.STRING_IDENTIFIER);
    public final ItemComponent<List<BannerPattern>> BANNER_PATTERNS = this.register("banner_patterns", ConstructorCodec.of(
            this.typeSerializers.DYE_COLOR.mapCodec(BannerPattern.COLOR), BannerPattern::getColor,
            this.typeSerializers.BANNER_PATTERN_PATTERN.mapCodec(BannerPattern.PATTERN), BannerPattern::getPattern,
            BannerPattern::new
    ).listOf());
    public final ItemComponent<DyeColor> BASE_COLOR = this.register("base_color", this.typeSerializers.DYE_COLOR);
    public final ItemComponent<List<Identifier>> POT_DECORATIONS = register("pot_decorations", Codec.STRING_IDENTIFIER.verified(this.registryVerifier.item).listOf(4));
    public final ItemComponent<List<ContainerSlot>> CONTAINER = register("container", this.typeSerializers.CONTAINER_SLOT.listOf(256));
    public final ItemComponent<Map<String, String>> BLOCK_STATE = register("block_state", Codec.mapOf(Codec.STRING, Codec.STRING));
    public final ItemComponent<List<BeeData>> BEES = register("bees", ConstructorCodec.of(
            this.typeSerializers.COMPOUND_TAG.mapCodec(BeeData.ENTITY_DATA).defaulted(CompoundTag::new, CompoundTag::isEmpty), BeeData::getEntityData,
            Codec.INTEGER.mapCodec(BeeData.TICKS_IN_HIVE), BeeData::getTicksInHive,
            Codec.INTEGER.mapCodec(BeeData.MIN_TICKS_IN_HIVE), BeeData::getMinTicksInHive,
            BeeData::new
    ).listOf());
    public final ItemComponent<String> LOCK = register("lock", Codec.STRING);
    public final ItemComponent<ContainerLoot> CONTAINER_LOOT = register("container_loot", ConstructorCodec.of(
            Codec.STRING_IDENTIFIER.mapCodec(ContainerLoot.LOOT_TABLE), ContainerLoot::getLootTable,
            Codec.LONG.mapCodec(ContainerLoot.SEED).optionalDefault(() -> 0L), ContainerLoot::getSeed,
            ContainerLoot::new
    ));


    public ItemComponents_v1_20_5() {
    }

    public ItemComponents_v1_20_5(final RegistryVerifier registryVerifier) {
        super(registryVerifier);
    }

    @Override
    public <D> D mapTo(DataConverter<D> converter, ItemComponentMap map) {
        D out = converter.emptyMap();
        for (Map.Entry<ItemComponent<?>, Optional<?>> entry : map.getRaw().entrySet()) {
            ItemComponent<Object> component = (ItemComponent<Object>) entry.getKey();
            Optional<?> value = entry.getValue();

            String name = component.getName().get();
            if (value.isPresent()) converter.put(out, name, component.serialize(converter, value.get()));
            else converter.put(out, "!" + name, converter.emptyMap());
        }
        return out;
    }

    @Override
    public <D> ItemComponentMap mapFrom(DataConverter<D> converter, D data) {
        Map<String, D> map = converter.asStringTypeMap(data).getOrThrow();
        ItemComponentMap out = new ItemComponentMap(this);
        for (Map.Entry<String, D> entry : map.entrySet()) {
            String name = entry.getKey();
            boolean forRemoval = name.startsWith("!");
            if (forRemoval) name = name.substring(1);
            Identifier id = Identifier.of(name);
            ItemComponent<Object> component = this.getComponent(id);
            if (component == null) throw new IllegalArgumentException("Unknown item component: " + id);
            if (forRemoval) out.markForRemoval(component);
            else out.set(component, component.deserialize(converter, entry.getValue()));
        }
        return out;
    }

}
