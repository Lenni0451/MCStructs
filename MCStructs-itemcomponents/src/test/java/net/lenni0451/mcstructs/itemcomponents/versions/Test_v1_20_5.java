package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.ItemComponents_v1_20_5;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.serializer.LegacyStringDeserializer;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Supplier;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test_v1_20_5 {

    private static final Map<ItemComponent<?>, Object> itemComponents = new HashMap<>();

    static {
        ItemComponents_v1_20_5 registry = ItemComponentRegistry.V1_20_5;
        register(registry.CUSTOM_DATA, new CompoundTag().addString("test", "test2"));
        register(registry.MAX_STACK_SIZE, 12);
        register(registry.MAX_DAMAGE, 123);
        register(registry.DAMAGE, 654);
        register(registry.UNBREAKABLE, new Unbreakable(false));
        register(registry.CUSTOM_NAME, LegacyStringDeserializer.parse("§4§lThis is §8a §ktest", false));
        register(registry.ITEM_NAME, LegacyStringDeserializer.parse("§oThis is another §3test", false));
        register(registry.LORE, Arrays.asList(LegacyStringDeserializer.parse("§4test1", false), LegacyStringDeserializer.parse("§ctest2", false)));
        register(registry.RARITY, Rarity.EPIC);
        register(registry.ENCHANTMENTS, new Enchantments(init(() -> {
            Map<Identifier, Integer> enchantments = new HashMap<>();
            enchantments.put(Identifier.of("sharpness"), 5);
            enchantments.put(Identifier.of("unbreaking"), 3);
            return enchantments;
        }), false));
        register(registry.CAN_PLACE_ON, new BlockPredicatesChecker(Collections.singletonList(new BlockPredicate(new TagEntryList(Identifier.of("test")), init(() -> {
            Map<String, BlockPredicate.ValueMatcher> valueMatchers = new HashMap<>();
            valueMatchers.put("test", new BlockPredicate.ValueMatcher("val"));
            valueMatchers.put("test2", new BlockPredicate.ValueMatcher("min val", null));
            return valueMatchers;
        }), new CompoundTag().addInt("abc", 123))), false));
        register(registry.CAN_BREAK, (BlockPredicatesChecker) itemComponents.get(registry.CAN_PLACE_ON));
        register(registry.ATTRIBUTE_MODIFIERS, new AttributeModifiers(Collections.singletonList(new AttributeModifier(Identifier.of("attr"), new AttributeModifier.EntityAttribute(UUID.randomUUID(), "name", 12.34, AttributeModifier.EntityAttribute.Operation.ADD_MULTIPLIED_TOTAL))), false));
        register(registry.CUSTOM_MODEL_DATA, 1234);
        register(registry.HIDE_ADDITIONAL_TOOLTIP, true);
        register(registry.HIDE_TOOLTIP, true);
        register(registry.REPAIR_COST, 987);
//        register(registry.CREATIVE_SLOT_LOCK, true);
        register(registry.ENCHANTMENT_GLINT_OVERRIDE, true);
        register(registry.INTANGIBLE_PROJECTILE, true);
        register(registry.FOOD, new Food(10, 20, true, 14, Collections.singletonList(new Food.Effect(new StatusEffect(Identifier.of("test"), 123, 456, true, true, false, null), 0.5F))));
        register(registry.FIRE_RESISTANT, true);
        register(registry.TOOL, new ToolComponent(Collections.singletonList(new ToolComponent.Rule(new TagEntryList(Arrays.asList(Identifier.of("test1"), Identifier.of("test2"))), 10F, true)), 20, 10));
        register(registry.STORED_ENCHANTMENTS, (Enchantments) itemComponents.get(registry.ENCHANTMENTS));
        register(registry.DYED_COLOR, new DyedColor(0xFFAABB, false));
        register(registry.MAP_COLOR, 0xAABBCC);
        register(registry.MAP_ID, -12);
        register(registry.MAP_DECORATIONS, init(() -> {
            Map<String, MapDecoration> mapDecorations = new HashMap<>();
            mapDecorations.put("test", new MapDecoration(Identifier.of("test"), 12, 13, 1.8F));
            return mapDecorations;
        }));
//        register(registry.MAP_POST_PROCESSING, MapPostProcessing.LOCK);
        register(registry.CHARGED_PROJECTILES, Collections.singletonList(new ItemStack(Identifier.of("test"), 12, registry.getItemDefaults())));
        register(registry.BUNDLE_CONTENTS, Collections.singletonList(new ItemStack(Identifier.of("test2"), 34, registry.getItemDefaults())));
        register(registry.POTION_CONTENTS, new PotionContents(Identifier.of("test"), 123, Arrays.asList(new StatusEffect(Identifier.of("test2"), 123, 456, true, true, false, null), new StatusEffect(Identifier.of("test3"), 123, 456, true, true, false, null))));
        register(registry.SUSPICIOUS_STEW_EFFECTS, Arrays.asList(new SuspiciousStewEffect(Identifier.of("test"), 123), new SuspiciousStewEffect(Identifier.of("test12"), 654)));
        register(registry.WRITABLE_BOOK_CONTENT, new WritableBook(Arrays.asList(new RawFilteredPair<>("page1", "filtered page 1"), new RawFilteredPair<>("page2", "filtered page 2"))));
        register(registry.WRITTEN_BOOK_CONTENT, new WrittenBook(new RawFilteredPair<>("title"), "author", 3, Arrays.asList(new RawFilteredPair<>(new StringComponent("page1")), new RawFilteredPair<>(new StringComponent("page2"), new StringComponent("filtered page2"))), true));
        register(registry.TRIM, new ArmorTrim(Either.left(Identifier.of("test")), Either.right(new ArmorTrimPattern(Identifier.of("pattern"), Identifier.of("test"), new StringComponent("description"))), false));
        register(registry.DEBUG_STICK_STATE, init(() -> {
            Map<Identifier, String> states = new HashMap<>();
            states.put(Identifier.of("test"), "state");
            states.put(Identifier.of("test2"), "state2");
            return states;
        }));
        register(registry.ENTITY_DATA, new CompoundTag().addString("id", "pig"));
        register(registry.BUCKET_ENTITY_DATA, new CompoundTag().addLongArray("test2", 4, 5, 6));
        register(registry.BLOCK_ENTITY_DATA, new CompoundTag().addString("id", "pig"));
        register(registry.INSTRUMENT, Either.right(new Instrument(Either.right(new SoundEvent(Identifier.of("test"), 0.5F)), 12, 13)));
        register(registry.OMINOUS_BOTTLE_AMPLIFIER, 2);
        register(registry.RECIPES, Arrays.asList(Identifier.of("test"), Identifier.of("test2")));
        register(registry.LODESTONE_TRACKER, new LodestoneTracker(new LodestoneTracker.GlobalPos(Identifier.of("test"), new BlockPos(12, 21, 34)), false));
        register(registry.FIREWORK_EXPLOSION, new FireworkExplosions(FireworkExplosions.ExplosionShape.CREEPER, new int[]{1, 2, 3}, new int[]{4, 5, 6}, true, true));
        register(registry.FIREWORKS, new Fireworks(5, Collections.singletonList(new FireworkExplosions(FireworkExplosions.ExplosionShape.STAR, new int[]{8, 9, 7}, new int[]{3, 5, 7}, true, false))));
        register(registry.PROFILE, new GameProfile("name", UUID.randomUUID(), init(() -> {
            Map<String, List<GameProfile.Property>> properties = new HashMap<>();
            properties.put("test", Collections.singletonList(new GameProfile.Property("test", "value", "signature")));
            return properties;
        })));
        register(registry.NOTE_BLOCK_SOUND, Identifier.of("test"));
        register(registry.BANNER_PATTERNS, Collections.singletonList(new BannerPattern(Either.right(new BannerPattern.Pattern(Identifier.of("test"), "translation")), DyeColor.RED)));
        register(registry.BASE_COLOR, DyeColor.YELLOW);
        register(registry.POT_DECORATIONS, Arrays.asList(Identifier.of("test"), Identifier.of("test2")));
        register(registry.CONTAINER, Arrays.asList(new ContainerSlot(10, new ItemStack(Identifier.of("test"), 12, registry.getItemDefaults())), new ContainerSlot(20, new ItemStack(Identifier.of("test2"), 34, registry.getItemDefaults()))));
        register(registry.BLOCK_STATE, init(() -> {
            Map<String, String> blockState = new HashMap<>();
            blockState.put("test", "state");
            blockState.put("test2", "state2");
            return blockState;
        }));
        register(registry.BEES, Collections.singletonList(new BeeData(new CompoundTag().addBoolean("test", true), 1, 2)));
        register(registry.LOCK, "test");
        register(registry.CONTAINER_LOOT, new ContainerLoot(Identifier.of("test"), 123));
    }

    private static <T> void register(final ItemComponent<T> itemComponent, final T value) {
        itemComponents.put(itemComponent, value);
    }

    private static <T> T init(final Supplier<T> supplier) {
        return supplier.get();
    }

    @Test
    void test() {
        ItemComponentMap map = new ItemComponentMap(ItemComponentRegistry.V1_20_5);
        for (Map.Entry<ItemComponent<?>, Object> entry : itemComponents.entrySet()) {
            map.set(cast(entry.getKey()), entry.getValue());
        }

        NbtTag tag = map.to(NbtConverter_v1_20_3.INSTANCE);
        ItemComponentMap deserialized = ItemComponentRegistry.V1_20_5.mapFrom(NbtConverter_v1_20_3.INSTANCE, tag);
        assertEquals(map.size(), deserialized.size());
        for (Map.Entry<ItemComponent<?>, ?> entry : deserialized.getValues().entrySet()) {
            assertEquals(itemComponents.get(entry.getKey()), entry.getValue());
        }
    }

    private static <T> T cast(final Object o) {
        return (T) o;
    }

}
