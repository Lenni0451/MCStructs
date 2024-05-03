package net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.RegistryVerifier;
import net.lenni0451.mcstructs.itemcomponents.serialization.BaseTypes;
import net.lenni0451.mcstructs.itemcomponents.serialization.interfaces.MergedComponentSerializer;
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
    public final ItemComponent<Integer> MAX_STACK_SIZE = register("max_stack_size", BaseTypes.INTEGER, i -> {
        if (i < 1) throw new IllegalArgumentException("Max stack size must be at least 1");
        if (i > 99) throw new IllegalArgumentException("Max stack size must be at most 99");
    });
    public final ItemComponent<Integer> MAX_DAMAGE = register("max_damage", BaseTypes.INTEGER, i -> {
        if (i < 1) throw new IllegalArgumentException("Max damage must be at least 1");
    });
    public final ItemComponent<Integer> DAMAGE = register("damage", BaseTypes.INTEGER, i -> {
        if (i < 0) throw new IllegalArgumentException("Damage must be at least 0");
    });
    public final ItemComponent<Unbreakable> UNBREAKABLE = register("unbreakable", new MergedComponentSerializer<Unbreakable>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, Unbreakable component) {
            T map = converter.emptyMap();
            if (!component.isShowInTooltip()) converter.put(map, Unbreakable.SHOW_IN_TOOLTIP, BaseTypes.BOOLEAN.serialize(converter, false));
            return map;
        }

        @Override
        public <T> Unbreakable deserialize(DataConverter<T> converter, T data) {
            Map<String, T> map = BaseTypes.asStringTypeMap(converter, data);
            return new Unbreakable(
                    BaseTypes.BOOLEAN.fromMap(converter, map, Unbreakable.SHOW_IN_TOOLTIP, true)
            );
        }
    });
    public final ItemComponent<ATextComponent> CUSTOM_NAME = register("custom_name", this.typeSerializers.TEXT_COMPONENT);
    public final ItemComponent<ATextComponent> ITEM_NAME = register("item_name", this.typeSerializers.TEXT_COMPONENT);
    public final ItemComponent<List<ATextComponent>> LORE = register("lore", this.typeSerializers.TEXT_COMPONENT.listOf(256));
    public final ItemComponent<Rarity> RARITY = this.register("rarity", BaseTypes.named(Rarity.values()));
    public final ItemComponent<Enchantments> ENCHANTMENTS = register("enchantments", BaseTypes.oneOf(
            new MergedComponentSerializer<Enchantments>() {
                @Override
                public <T> T serialize(DataConverter<T> converter, Enchantments component) {
                    T map = converter.emptyMap();
                    converter.put(map, Enchantments.LEVELS, ItemComponents_v1_20_5.this.typeSerializers.ENCHANTMENT_LEVELS.serialize(converter, component.getEnchantments()));
                    if (!component.isShowInTooltip()) converter.put(map, Enchantments.SHOW_IN_TOOLTIP, BaseTypes.BOOLEAN.serialize(converter, false));
                    return map;
                }

                @Override
                public <T> Enchantments deserialize(DataConverter<T> converter, T data) {
                    Map<String, T> map = BaseTypes.asStringTypeMap(converter, data);
                    return new Enchantments(
                            ItemComponents_v1_20_5.this.typeSerializers.ENCHANTMENT_LEVELS.fromMap(converter, map, Enchantments.LEVELS),
                            BaseTypes.BOOLEAN.fromMap(converter, map, Enchantments.SHOW_IN_TOOLTIP, true)
                    );
                }
            },
            this.typeSerializers.ENCHANTMENT_LEVELS.map(Enchantments::getEnchantments, map -> new Enchantments(map, true))
    ));
    //TODO: can_place_on
    //TODO: can_break
    //TODO: attribute_modifiers
    public final ItemComponent<Integer> CUSTOM_MODEL_DATA = register("custom_model_data", BaseTypes.INTEGER);
    public final ItemComponent<Boolean> HIDE_ADDITIONAL_TOOLTIP = register("hide_additional_tooltip", BaseTypes.UNIT);
    public final ItemComponent<Boolean> HIDE_TOOLTIP = register("hide_tooltip", BaseTypes.UNIT);
    public final ItemComponent<Integer> REPAIR_COST = register("repair_cost", BaseTypes.INTEGER, i -> {
        if (i < 0) throw new IllegalArgumentException("Repair cost must be at least 0");
    });
    public final ItemComponent<Boolean> CREATIVE_SLOT_LOCK = registerNonSerializable("creative_slot_lock"); //No json/nbt serialization
    public final ItemComponent<Boolean> ENCHANTMENT_GLINT_OVERRIDE = register("enchantment_glint_override", BaseTypes.BOOLEAN);
    public final ItemComponent<Boolean> INTANGIBLE_PROJECTILE = register("intangible_projectile", BaseTypes.UNIT);
    //TODO: food
    public final ItemComponent<Boolean> FIRE_RESISTANT = register("fire_resistant", BaseTypes.UNIT);
    //TODO: tool
    public final ItemComponent<Enchantments> STORED_ENCHANTMENTS = copy("stored_enchantments", this.ENCHANTMENTS);
    public final ItemComponent<DyedColor> DYED_COLOR = register("dyed_color", new MergedComponentSerializer<DyedColor>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, DyedColor component) {
            T map = converter.emptyMap();
            converter.put(map, DyedColor.RGB, BaseTypes.INTEGER.serialize(converter, component.getRgb()));
            if (!component.isShowInTooltip()) converter.put(map, DyedColor.SHOW_IN_TOOLTIP, BaseTypes.BOOLEAN.serialize(converter, false));
            return map;
        }

        @Override
        public <T> DyedColor deserialize(DataConverter<T> converter, T data) {
            Map<String, T> map = BaseTypes.asStringTypeMap(converter, data);
            return new DyedColor(
                    BaseTypes.INTEGER.fromMap(converter, map, DyedColor.RGB),
                    BaseTypes.BOOLEAN.fromMap(converter, map, DyedColor.SHOW_IN_TOOLTIP, true)
            );
        }
    });
    public final ItemComponent<Integer> MAP_COLOR = register("map_color", BaseTypes.INTEGER);
    public final ItemComponent<Integer> MAP_ID = register("map_id", BaseTypes.INTEGER);
    public final ItemComponent<Map<String, MapDecoration>> MAP_DECORATIONS = register("map_decorations", new MergedComponentSerializer<MapDecoration>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, MapDecoration component) {
            T map = converter.emptyMap();
            converter.put(map, MapDecoration.TYPE, BaseTypes.IDENTIFIER.serialize(converter, component.getType()));
            converter.put(map, MapDecoration.X, BaseTypes.DOUBLE.serialize(converter, component.getX()));
            converter.put(map, MapDecoration.Z, BaseTypes.DOUBLE.serialize(converter, component.getZ()));
            converter.put(map, MapDecoration.ROTATION, BaseTypes.FLOAT.serialize(converter, component.getRotation()));
            return map;
        }

        @Override
        public <T> MapDecoration deserialize(DataConverter<T> converter, T data) {
            Map<String, T> map = BaseTypes.asStringTypeMap(converter, data);
            return new MapDecoration(
                    BaseTypes.IDENTIFIER.fromMap(converter, map, MapDecoration.TYPE),
                    BaseTypes.DOUBLE.fromMap(converter, map, MapDecoration.X),
                    BaseTypes.DOUBLE.fromMap(converter, map, MapDecoration.Z),
                    BaseTypes.FLOAT.fromMap(converter, map, MapDecoration.ROTATION)
            );
        }
    }.withVerifier(this.registryVerifier.mapDecorationType.map(MapDecoration::getType)).stringMapOf());
    public final ItemComponent<Integer> MAP_POST_PROCESSING = registerNonSerializable("map_post_processing");
    public final ItemComponent<List<ItemStack>> CHARGED_PROJECTILES = register("charged_projectiles", this.typeSerializers.ITEM_STACK.listOf());
    public final ItemComponent<List<ItemStack>> BUNDLE_CONTENTS = register("bundle_contents", this.typeSerializers.ITEM_STACK.listOf());
    //TODO: potion_contents
    //TODO: suspicious_stew_effects
    public final ItemComponent<WritableBook> WRITABLE_BOOK_CONTENT = register("writable_book_content", new MergedComponentSerializer<WritableBook>() {
        private final MergedComponentSerializer<List<WritableBook.Page>> pageList = ItemComponents_v1_20_5.this.typeSerializers.WRITABLE_BOOK_PAGE.listOf();

        @Override
        public <T> T serialize(DataConverter<T> converter, WritableBook component) {
            T map = converter.emptyMap();
            if (component.getPages().isEmpty()) return map;

            converter.put(map, WritableBook.PAGES, this.pageList.serialize(converter, component.getPages()));
            return map;
        }

        @Override
        public <T> WritableBook deserialize(DataConverter<T> converter, T data) {
            Map<String, T> map = BaseTypes.asStringTypeMap(converter, data);
            return new WritableBook(
                    this.pageList.fromMap(converter, map, WritableBook.PAGES, new ArrayList<>())
            );
        }
    });
    //TODO: written_book_content
    //TODO: trim
    //TODO: debug_stick_state
    //TODO: entity_data
    //TODO: bucket_entity_data
    //TODO: block_entity_data
    //TODO: instrument
    public final ItemComponent<Integer> OMINOUS_BOTTLE_AMPLIFIER = register("ominous_bottle_amplifier", BaseTypes.INTEGER, amplifier -> {
        if (amplifier < 0) throw new IllegalArgumentException("Ominous bottle amplifier must be at least 0");
        if (amplifier > 4) throw new IllegalArgumentException("Ominous bottle amplifier must be at most 4");
    });
    public final ItemComponent<List<Identifier>> RECIPES = register("recipes", BaseTypes.IDENTIFIER.listOf());
    //TODO: lodestone_tracker
    public final ItemComponent<FireworkExplosions> FIREWORK_EXPLOSION = this.register("firework_explosion", new MergedComponentSerializer<FireworkExplosions>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, FireworkExplosions component) {
            T map = converter.emptyMap();
            converter.put(map, FireworkExplosions.SHAPE, ItemComponents_v1_20_5.this.typeSerializers.FIREWORK_EXPLOSIONS_EXPLOSION_SHAPE.serialize(converter, component.getShape()));
            if (component.getColors().length != 0) converter.put(map, FireworkExplosions.COLORS, BaseTypes.INT_ARRAY.serialize(converter, component.getColors()));
            if (component.getFadeColors().length != 0) converter.put(map, FireworkExplosions.FADE_COLORS, BaseTypes.INT_ARRAY.serialize(converter, component.getFadeColors()));
            if (component.isHasTrail()) converter.put(map, FireworkExplosions.HAS_TRAIL, BaseTypes.BOOLEAN.serialize(converter, true));
            if (component.isHasTwinkle()) converter.put(map, FireworkExplosions.HAS_TWINKLE, BaseTypes.BOOLEAN.serialize(converter, true));
            return map;
        }

        @Override
        public <T> FireworkExplosions deserialize(DataConverter<T> converter, T data) {
            Map<String, T> map = BaseTypes.asStringTypeMap(converter, data);
            return new FireworkExplosions(
                    ItemComponents_v1_20_5.this.typeSerializers.FIREWORK_EXPLOSIONS_EXPLOSION_SHAPE.fromMap(converter, map, FireworkExplosions.SHAPE),
                    BaseTypes.INT_ARRAY.fromMap(converter, map, FireworkExplosions.COLORS, new int[0]),
                    BaseTypes.INT_ARRAY.fromMap(converter, map, FireworkExplosions.FADE_COLORS, new int[0]),
                    BaseTypes.BOOLEAN.fromMap(converter, map, FireworkExplosions.HAS_TRAIL, false),
                    BaseTypes.BOOLEAN.fromMap(converter, map, FireworkExplosions.HAS_TWINKLE, false)
            );
        }
    });
    //TODO: fireworks
    //TODO: profile
    public final ItemComponent<Identifier> NOTE_BLOCK_SOUND = this.register("note_block_sound", BaseTypes.IDENTIFIER);
    public final ItemComponent<List<BannerPattern>> BANNER_PATTERNS = this.register("banner_patterns", new MergedComponentSerializer<BannerPattern>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, BannerPattern component) {
            T map = converter.emptyMap();
            converter.put(map, BannerPattern.COLOR, ItemComponents_v1_20_5.this.typeSerializers.DYE_COLOR.serialize(converter, component.getColor()));
            converter.put(map, BannerPattern.PATTERN, ItemComponents_v1_20_5.this.typeSerializers.BANNER_PATTERN_PATTERN.serialize(converter, component.getPattern()));
            return map;
        }

        @Override
        public <T> BannerPattern deserialize(DataConverter<T> converter, T data) {
            Map<String, T> map = BaseTypes.asStringTypeMap(converter, data);
            return new BannerPattern(
                    ItemComponents_v1_20_5.this.typeSerializers.BANNER_PATTERN_PATTERN.fromMap(converter, map, BannerPattern.PATTERN),
                    ItemComponents_v1_20_5.this.typeSerializers.DYE_COLOR.fromMap(converter, map, BannerPattern.COLOR)
            );
        }
    }.listOf());
    public final ItemComponent<DyeColor> BASE_COLOR = this.register("base_color", this.typeSerializers.DYE_COLOR);
    public final ItemComponent<List<Identifier>> POT_DECORATIONS = register("pot_decorations", BaseTypes.IDENTIFIER.withVerifier(this.registryVerifier.item).listOf(), potDecorations -> {
        if (potDecorations.size() > 4) throw new IllegalArgumentException("Pot decorations can only have at most 4 elements");
    });
    public final ItemComponent<List<ContainerSlot>> CONTAINER = register("container", this.typeSerializers.CONTAINER_SLOT.listOf(256));
    public final ItemComponent<Map<String, String>> BLOCK_STATE = register("block_state", BaseTypes.STRING.stringMapOf());
    public final ItemComponent<List<BeeData>> BEES = register("bees", new MergedComponentSerializer<BeeData>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, BeeData component) {
            T map = converter.emptyMap();
            if (!component.getEntityData().isEmpty()) {
                converter.put(map, BeeData.ENTITY_DATA, ItemComponents_v1_20_5.this.typeSerializers.COMPOUND_TAG.serialize(converter, component.getEntityData()));
            }
            converter.put(map, BeeData.TICKS_IN_HIVE, BaseTypes.INTEGER.serialize(converter, component.getTicksInHive()));
            converter.put(map, BeeData.MIN_TICKS_IN_HIVE, BaseTypes.INTEGER.serialize(converter, component.getMinTicksInHive()));
            return map;
        }

        @Override
        public <T> BeeData deserialize(DataConverter<T> converter, T data) {
            Map<String, T> map = BaseTypes.asStringTypeMap(converter, data);
            return new BeeData(
                    ItemComponents_v1_20_5.this.typeSerializers.COMPOUND_TAG.fromMap(converter, map, BeeData.ENTITY_DATA, new CompoundTag()),
                    BaseTypes.INTEGER.fromMap(converter, map, BeeData.TICKS_IN_HIVE),
                    BaseTypes.INTEGER.fromMap(converter, map, BeeData.MIN_TICKS_IN_HIVE)
            );
        }
    }.listOf());
    public final ItemComponent<String> LOCK = register("lock", BaseTypes.STRING);
    public final ItemComponent<ContainerLoot> CONTAINER_LOOT = register("container_loot", new MergedComponentSerializer<ContainerLoot>() {
        @Override
        public <T> T serialize(DataConverter<T> converter, ContainerLoot component) {
            T map = converter.emptyMap();
            converter.put(map, ContainerLoot.LOOT_TABLE, BaseTypes.IDENTIFIER.serialize(converter, component.getLootTable()));
            if (component.getSeed() != 0) converter.put(map, ContainerLoot.SEED, BaseTypes.LONG.serialize(converter, component.getSeed()));
            return map;
        }

        @Override
        public <T> ContainerLoot deserialize(DataConverter<T> converter, T data) {
            Map<String, T> map = BaseTypes.asStringTypeMap(converter, data);
            return new ContainerLoot(
                    BaseTypes.IDENTIFIER.fromMap(converter, map, ContainerLoot.LOOT_TABLE),
                    BaseTypes.LONG.fromMap(converter, map, ContainerLoot.SEED, 0L)
            );
        }
    });


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
