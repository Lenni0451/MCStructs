package net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.serialization.TypeValidator;
import net.lenni0451.mcstructs.itemcomponents.serialization.Verifier;
import net.lenni0451.mcstructs.nbt.INbtNumber;
import net.lenni0451.mcstructs.nbt.INbtTag;
import net.lenni0451.mcstructs.nbt.tags.*;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;
import net.lenni0451.mcstructs.text.utils.JsonNbtConverter;

import java.util.*;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.TypeSerializers_v1_20_5.ItemStackSerializer;
import static net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5.*;
import static net.lenni0451.mcstructs.itemcomponents.serialization.TypeValidator.*;

public class ItemComponents_v1_20_5 extends ItemComponentRegistry {

    public final ItemComponent<CompoundTag> CUSTOM_DATA = register("custom_data", JsonNbtConverter::toJson, element -> JsonNbtConverter.toNbt(element).asCompoundTag(), tag -> tag, INbtTag::asCompoundTag);
    public final ItemComponent<Integer> MAX_STACK_SIZE = register("max_stack_size", JsonPrimitive::new, TypeValidator::requireInt, IntTag::new, TypeValidator::requireInt, i -> {
        if (i < 1) throw new IllegalArgumentException("Max stack size must be at least 1");
        if (i > 99) throw new IllegalArgumentException("Max stack size must be at most 99");
    });
    public final ItemComponent<Integer> MAX_DAMAGE = register("max_damage", JsonPrimitive::new, TypeValidator::requireInt, IntTag::new, TypeValidator::requireInt, i -> {
        if (i < 1) throw new IllegalArgumentException("Max damage must be at least 1");
    });
    public final ItemComponent<Integer> DAMAGE = register("damage", JsonPrimitive::new, TypeValidator::requireInt, IntTag::new, TypeValidator::requireInt, i -> {
        if (i < 0) throw new IllegalArgumentException("Damage must be at least 0");
    });
    public final ItemComponent<Unbreakable> UNBREAKABLE = register("unbreakable", unbreakable -> {
        JsonObject object = new JsonObject();
        if (!unbreakable.isShowInTooltip()) object.addProperty(Unbreakable.SHOW_IN_TOOLTIP, false);
        return object;
    }, element -> {
        JsonObject object = requireJsonObject(element);
        Unbreakable unbreakable = new Unbreakable();
        if (object.has(Unbreakable.SHOW_IN_TOOLTIP)) unbreakable.setShowInTooltip(requireBoolean(object.get(Unbreakable.SHOW_IN_TOOLTIP)));
        return unbreakable;
    }, unbreakable -> {
        CompoundTag compound = new CompoundTag();
        if (!unbreakable.isShowInTooltip()) compound.add(Unbreakable.SHOW_IN_TOOLTIP, false);
        return compound;
    }, tag -> {
        CompoundTag compound = requireCompoundTag(tag);
        Unbreakable unbreakable = new Unbreakable();
        if (compound.contains(Unbreakable.SHOW_IN_TOOLTIP)) unbreakable.setShowInTooltip(requireBoolean(compound.<INbtNumber>get(Unbreakable.SHOW_IN_TOOLTIP)));
        return unbreakable;
    });
    public final ItemComponent<ATextComponent> CUSTOM_NAME = register("custom_name", text -> new JsonPrimitive(TextComponentCodec.V1_20_5.serializeJsonString(text)), element -> TextComponentCodec.V1_20_5.deserializeJson(requireString(element)), text -> new StringTag(TextComponentCodec.V1_20_5.serializeJsonString(text)), tag -> TextComponentCodec.V1_20_5.deserializeJson(requireString(tag)));
    public final ItemComponent<ATextComponent> ITEM_NAME = copy("item_name", this.CUSTOM_NAME);
    public final ItemComponent<Lore> LORE = register("lore", lore -> {
        JsonArray array = new JsonArray();
        lore.getLines().forEach(line -> array.add(TextComponentCodec.V1_20_5.serializeJsonString(line)));
        return array;
    }, element -> {
        JsonArray array = requireJsonArray(element);
        List<ATextComponent> lines = new ArrayList<>();
        array.forEach(line -> lines.add(TextComponentCodec.V1_20_5.deserializeJson(requireString(line))));
        return new Lore(lines);
    }, lore -> {
        ListTag<StringTag> list = new ListTag<>();
        lore.getLines().forEach(line -> list.add(new StringTag(TextComponentCodec.V1_20_5.serializeJsonString(line))));
        return list;
    }, tag -> {
        ListTag<StringTag> list = requireListTag(tag);
        List<ATextComponent> lines = new ArrayList<>();
        list.getValue().forEach(line -> lines.add(TextComponentCodec.V1_20_5.deserializeJson(requireString(line))));
        return new Lore(lines);
    });
    public final ItemComponent<Rarity> RARITY = this.register("rarity", rarity -> new JsonPrimitive(rarity.getName()), element -> Rarity.byName(requireString(element)), rarity -> new StringTag(rarity.getName()), tag -> Rarity.byName(requireString(tag)));
    public final ItemComponent<Enchantments> ENCHANTMENTS = register("enchantments", enchantments -> {
        JsonObject object = new JsonObject();
        if (!enchantments.isShowInTooltip()) object.addProperty(Enchantments.SHOW_IN_TOOLTIP, false);
        JsonObject levels = new JsonObject();
        enchantments.getEnchantments().forEach((enchantment, level) -> levels.addProperty(enchantment.get(), level));
        object.add(Enchantments.LEVELS, levels);
        return object;
    }, element -> {
        Enchantments enchantments = new Enchantments();
        JsonObject object = requireJsonObject(element);
        if (object.has("levels")) {
            JsonObject levels = requireJsonObject(object.get("levels"));
            levels.entrySet().forEach(entry -> enchantments.addEnchantment(Identifier.of(entry.getKey()), requireInt(entry.getValue())));
            if (object.has(Enchantments.SHOW_IN_TOOLTIP)) enchantments.setShowInTooltip(requireBoolean(object.get(Enchantments.SHOW_IN_TOOLTIP)));
        } else {
            object.entrySet().forEach(entry -> enchantments.addEnchantment(Identifier.of(entry.getKey()), requireInt(entry.getValue())));
        }
        return enchantments;
    }, enchantments -> {
        CompoundTag compound = new CompoundTag();
        if (!enchantments.isShowInTooltip()) compound.add(Enchantments.SHOW_IN_TOOLTIP, false);
        CompoundTag levels = new CompoundTag();
        enchantments.getEnchantments().forEach((enchantment, level) -> levels.add(enchantment.get(), level));
        compound.add(Enchantments.LEVELS, levels);
        return compound;
    }, tag -> {
        Enchantments enchantments = new Enchantments();
        CompoundTag compound = requireCompoundTag(tag);
        if (compound.contains(Enchantments.LEVELS)) {
            CompoundTag levels = requireCompoundTag(compound.get(Enchantments.LEVELS));
            levels.forEach(entry -> enchantments.addEnchantment(Identifier.of(entry.getKey()), requireInt(entry.getValue())));
            if (compound.contains(Enchantments.SHOW_IN_TOOLTIP)) enchantments.setShowInTooltip(requireBoolean(compound.<INbtTag>get(Enchantments.SHOW_IN_TOOLTIP)));
        } else {
            compound.forEach(entry -> enchantments.addEnchantment(Identifier.of(entry.getKey()), requireInt(entry.getValue())));
        }
        return enchantments;
    }, enchantments -> {
        for (Map.Entry<Identifier, Integer> entry : enchantments.getEnchantments().entrySet()) {
            this.enchantmentVerifier.check("enchantment", entry.getKey());
            if (entry.getValue() < 0) throw new IllegalArgumentException("Enchantment level must be at least 0");
            if (entry.getValue() > 255) throw new IllegalArgumentException("Enchantment level must be at most 255");
        }
    });
    //TODO: can_place_on
    //TODO: can_break
    //TODO: attribute_modifiers
    public final ItemComponent<Integer> CUSTOM_MODEL_DATA = register("custom_model_data", JsonPrimitive::new, TypeValidator::requireInt, IntTag::new, TypeValidator::requireInt);
    public final ItemComponent<Boolean> HIDE_ADDITIONAL_TOOLTIP = register("hide_additional_tooltip", b -> new JsonObject(), element -> {
        requireJsonObject(element);
        return true;
    }, b -> new CompoundTag(), tag -> {
        requireCompoundTag(tag);
        return true;
    });
    public final ItemComponent<Boolean> HIDE_TOOLTIP = copy("hide_tooltip", this.HIDE_ADDITIONAL_TOOLTIP);
    public final ItemComponent<Integer> REPAIR_COST = register("repair_cost", JsonPrimitive::new, TypeValidator::requireInt, IntTag::new, TypeValidator::requireInt, i -> {
        if (i < 0) throw new IllegalArgumentException("Repair cost must be at least 0");
    });
    public final ItemComponent<Boolean> CREATIVE_SLOT_LOCK = registerNonSerializable("creative_slot_lock"); //No json/nbt serialization
    public final ItemComponent<Boolean> ENCHANTMENT_GLINT_OVERRIDE = register("enchantment_glint_override", JsonPrimitive::new, TypeValidator::requireBoolean, ByteTag::new, TypeValidator::requireBoolean);
    public final ItemComponent<Boolean> INTANGIBLE_PROJECTILE = copy("intangible_projectile", this.HIDE_ADDITIONAL_TOOLTIP);
    //TODO: food
    public final ItemComponent<Boolean> FIRE_RESISTANT = copy("fire_resistant", this.HIDE_ADDITIONAL_TOOLTIP);
    //TODO: tool
    public final ItemComponent<Enchantments> STORED_ENCHANTMENTS = copy("stored_enchantments", this.ENCHANTMENTS);
    public final ItemComponent<DyedColor> DYED_COLOR = register("dyed_color", dyedColor -> {
        JsonObject object = new JsonObject();
        object.addProperty(DyedColor.RGB, dyedColor.getRgb());
        if (!dyedColor.isShowInTooltip()) object.addProperty(DyedColor.SHOW_IN_TOOLTIP, false);
        return object;
    }, element -> {
        JsonObject object = requireJsonObject(element);
        DyedColor dyedColor = new DyedColor();
        dyedColor.setRgb(requireInt(object.get(DyedColor.RGB)));
        if (object.has(DyedColor.SHOW_IN_TOOLTIP)) dyedColor.setShowInTooltip(requireBoolean(object.get(DyedColor.SHOW_IN_TOOLTIP)));
        return dyedColor;
    }, dyed_color -> {
        CompoundTag compound = new CompoundTag();
        compound.add(DyedColor.RGB, dyed_color.getRgb());
        if (!dyed_color.isShowInTooltip()) compound.add(DyedColor.SHOW_IN_TOOLTIP, false);
        return compound;
    }, tag -> {
        CompoundTag compound = requireCompoundTag(tag);
        DyedColor dyedColor = new DyedColor();
        dyedColor.setRgb(requireInt(compound.<INbtTag>get(DyedColor.RGB)));
        if (compound.contains(DyedColor.SHOW_IN_TOOLTIP)) dyedColor.setShowInTooltip(requireBoolean(compound.<INbtTag>get(DyedColor.SHOW_IN_TOOLTIP)));
        return dyedColor;
    });
    public final ItemComponent<Integer> MAP_COLOR = register("map_color", JsonPrimitive::new, TypeValidator::requireInt, IntTag::new, TypeValidator::requireInt);
    public final ItemComponent<Integer> MAP_ID = register("map_id", JsonPrimitive::new, TypeValidator::requireInt, IntTag::new, TypeValidator::requireInt);
    public final ItemComponent<Map<String, MapDecoration>> MAP_DECORATIONS = register("map_decorations", map -> {
        JsonObject object = new JsonObject();
        for (Map.Entry<String, MapDecoration> entry : map.entrySet()) {
            JsonObject decoration = new JsonObject();
            decoration.addProperty(MapDecoration.TYPE, entry.getValue().getType().get());
            decoration.addProperty(MapDecoration.X, entry.getValue().getX());
            decoration.addProperty(MapDecoration.Z, entry.getValue().getZ());
            decoration.addProperty(MapDecoration.ROTATION, entry.getValue().getRotation());
            object.add(entry.getKey(), decoration);
        }
        return object;
    }, element -> {
        JsonObject object = requireJsonObject(element);
        Map<String, MapDecoration> mapDecorations = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            JsonObject decoration = requireJsonObject(entry.getValue());
            mapDecorations.put(entry.getKey(), new MapDecoration(
                    Identifier.of(requireString(decoration.get(MapDecoration.TYPE))),
                    requireDouble(decoration.get(MapDecoration.X)),
                    requireDouble(decoration.get(MapDecoration.Z)),
                    requireFloat(decoration.get(MapDecoration.ROTATION))
            ));
        }
        return mapDecorations;
    }, map -> {
        CompoundTag compound = new CompoundTag();
        for (Map.Entry<String, MapDecoration> entry : map.entrySet()) {
            CompoundTag decoration = new CompoundTag()
                    .add(MapDecoration.TYPE, new StringTag(entry.getValue().getType().get()))
                    .add(MapDecoration.X, new DoubleTag(entry.getValue().getX()))
                    .add(MapDecoration.Z, new DoubleTag(entry.getValue().getZ()))
                    .add(MapDecoration.ROTATION, new FloatTag(entry.getValue().getRotation()));
            compound.add(entry.getKey(), decoration);
        }
        return compound;
    }, tag -> {
        CompoundTag compound = requireCompoundTag(tag);
        Map<String, MapDecoration> mapDecorations = new HashMap<>();
        for (Map.Entry<String, INbtTag> entry : compound) {
            CompoundTag decoration = requireCompoundTag(entry.getValue());
            mapDecorations.put(entry.getKey(), new MapDecoration(
                    Identifier.of(requireString(decoration.<INbtTag>get(MapDecoration.TYPE))),
                    requireDouble(decoration.<INbtTag>get(MapDecoration.X)),
                    requireDouble(decoration.<INbtTag>get(MapDecoration.Z)),
                    requireFloat(decoration.<INbtTag>get(MapDecoration.ROTATION))
            ));
        }
        return mapDecorations;
    }, map -> {
        for (MapDecoration decoration : map.values()) this.mapDecorationTypeVerifier.check("map decoration type", decoration.getType());
    });
    public final ItemComponent<Integer> MAP_POST_PROCESSING = registerNonSerializable("map_post_processing");
    public final ItemComponent<List<ItemStack>> CHARGED_PROJECTILES = register("charged_projectiles", list -> {
        JsonArray array = new JsonArray();
        for (ItemStack itemStack : list) array.add(ItemStackSerializer.toJson(this, itemStack));
        return array;
    }, element -> {
        JsonArray array = requireJsonArray(element);
        List<ItemStack> list = new ArrayList<>();
        for (JsonElement arrayElement : array) list.add(ItemStackSerializer.fromJson(this, this.itemVerifier, arrayElement));
        return list;
    }, list -> {
        ListTag<CompoundTag> listTag = new ListTag<>();
        for (ItemStack itemStack : list) listTag.add(ItemStackSerializer.toNbt(this, itemStack));
        return listTag;
    }, tag -> {
        ListTag<?> listTag = requireListTag(tag);
        List<ItemStack> list = new ArrayList<>();
        for (INbtTag element : listTag.getValue()) list.add(ItemStackSerializer.fromNbt(this, this.itemVerifier, element));
        return list;
    });
    public final ItemComponent<List<ItemStack>> BUNDLE_CONTENTS = copy("bundle_contents", this.CHARGED_PROJECTILES);
    //TODO: potion_contents
    //TODO: suspicious_stew_effects
    //TODO: writable_book_content
    //TODO: written_book_content
    //TODO: trim
    //TODO: debug_stick_state
    //TODO: entity_data
    //TODO: bucket_entity_data
    //TODO: block_entity_data
    //TODO: instrument
    //TODO: ominous_bottle_amplifier
    //TODO: recipes
    //TODO: lodestone_tracker
    //TODO: firework_explosion
    //TODO: fireworks
    //TODO: profile
    //TODO: note_block_sound
    //TODO: banner_patterns
    //TODO: base_color
    //TODO: pot_decorations
    //TODO: container
    //TODO: block_state
    //TODO: bees
    //TODO: lock
    //TODO: container_loot


    private Verifier<Identifier> itemVerifier = item -> true;
    private Verifier<Identifier> enchantmentVerifier = enchantment -> true;
    private Verifier<Identifier> potionVerifier = potion -> true;
    private Verifier<Identifier> statusEffectVerifier = statusEffect -> true;
    private Verifier<Identifier> mapDecorationTypeVerifier = type -> true;

    @Override
    public JsonElement mapToJson(ItemComponentMap map) {
        JsonObject object = new JsonObject();
        for (Map.Entry<ItemComponent<?>, Optional<?>> entry : map.getRaw().entrySet()) {
            ItemComponent<Object> component = (ItemComponent<Object>) entry.getKey();
            Optional<?> value = entry.getValue();

            String name = component.getName().get();
            if (value.isPresent()) object.add(name, component.toJson(value.get()));
            else object.add("!" + name, new JsonObject());
        }
        return object;
    }

    @Override
    public ItemComponentMap mapFromJson(JsonElement element) {
        JsonObject object = requireJsonObject(element);
        ItemComponentMap map = new ItemComponentMap(this);
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            String name = entry.getKey();
            boolean forRemoval = name.startsWith("!");
            if (forRemoval) name = name.substring(1);
            Identifier id = Identifier.of(name);
            ItemComponent<Object> component = this.getComponent(id);
            if (component == null) throw new IllegalArgumentException("Unknown item component: " + name);

            if (forRemoval) map.markForRemoval(component);
            else map.set(component, component.fromJson(entry.getValue()));
        }
        return map;
    }

    @Override
    public INbtTag mapToNbt(ItemComponentMap map) {
        CompoundTag compound = new CompoundTag();
        for (Map.Entry<ItemComponent<?>, Optional<?>> entry : map.getRaw().entrySet()) {
            ItemComponent<Object> component = (ItemComponent<Object>) entry.getKey();
            Optional<?> value = entry.getValue();

            String name = component.getName().get();
            if (value.isPresent()) compound.add(name, component.toNbt(value.get()));
            else compound.add("!" + name, new CompoundTag());
        }
        return compound;
    }

    @Override
    public ItemComponentMap mapFromNbt(INbtTag tag) {
        CompoundTag compound = requireCompoundTag(tag);
        ItemComponentMap map = new ItemComponentMap(this);
        for (Map.Entry<String, INbtTag> entry : compound) {
            String name = entry.getKey();
            boolean forRemoval = name.startsWith("!");
            if (forRemoval) name = name.substring(1);
            Identifier id = Identifier.of(name);
            ItemComponent<Object> component = this.getComponent(id);
            if (component == null) throw new IllegalArgumentException("Unknown item component: " + name);

            if (forRemoval) map.markForRemoval(component);
            else map.set(component, component.fromNbt(entry.getValue()));
        }
        return map;
    }

    public ItemComponents_v1_20_5 withItemVerifier(final Verifier<Identifier> verifier) {
        ItemComponents_v1_20_5 itemComponents = this.copy();
        itemComponents.itemVerifier = verifier;
        return itemComponents;
    }

    public ItemComponents_v1_20_5 withEnchantmentVerifier(final Verifier<Identifier> verifier) {
        ItemComponents_v1_20_5 itemComponents = this.copy();
        itemComponents.enchantmentVerifier = verifier;
        return itemComponents;
    }

    public ItemComponents_v1_20_5 withPotionVerifier(final Verifier<Identifier> verifier) {
        ItemComponents_v1_20_5 itemComponents = this.copy();
        itemComponents.potionVerifier = verifier;
        return itemComponents;
    }

    public ItemComponents_v1_20_5 withStatusEffectVerifier(final Verifier<Identifier> verifier) {
        ItemComponents_v1_20_5 itemComponents = this.copy();
        itemComponents.statusEffectVerifier = verifier;
        return itemComponents;
    }

    public ItemComponents_v1_20_5 withMapDecorationTypeVerifier(final Verifier<Identifier> verifier) {
        ItemComponents_v1_20_5 itemComponents = this.copy();
        itemComponents.mapDecorationTypeVerifier = verifier;
        return itemComponents;
    }

    private ItemComponents_v1_20_5 copy() {
        ItemComponents_v1_20_5 itemComponents = new ItemComponents_v1_20_5();
        itemComponents.itemVerifier = this.itemVerifier;
        itemComponents.enchantmentVerifier = this.enchantmentVerifier;
        itemComponents.potionVerifier = this.potionVerifier;
        itemComponents.statusEffectVerifier = this.statusEffectVerifier;
        itemComponents.mapDecorationTypeVerifier = this.mapDecorationTypeVerifier;
        return itemComponents;
    }

}
