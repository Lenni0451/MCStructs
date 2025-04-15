package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.ItemComponents_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.registry.EitherEntry;
import net.lenni0451.mcstructs.itemcomponents.registry.RegistryEntry;
import net.lenni0451.mcstructs.itemcomponents.registry.TagEntryList;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.components.StringComponent;
import net.lenni0451.mcstructs.text.stringformat.StringFormat;
import net.lenni0451.mcstructs.text.stringformat.handling.ColorHandling;
import net.lenni0451.mcstructs.text.stringformat.handling.DeserializerUnknownHandling;

import java.util.*;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5.*;

public class Test_v1_20_5 extends ItemComponentTest<ItemComponents_v1_20_5> {

    private static final StringFormat FORMAT = StringFormat.vanilla();

    @Override
    protected ItemComponents_v1_20_5 getRegistry() {
        return ItemComponentRegistry.V1_20_5;
    }

    @Override
    protected DataConverter<NbtTag> getConverter() {
        return NbtConverter_v1_20_3.INSTANCE;
    }

    @Override
    public void register(ItemComponents_v1_20_5 registry) {
        register(registry.CUSTOM_DATA, new CompoundTag().addString("test", "test2"));
        register(registry.MAX_STACK_SIZE, 12);
        register(registry.MAX_DAMAGE, 123);
        register(registry.DAMAGE, 654);
        register(registry.UNBREAKABLE, new Unbreakable(false));
        register(registry.CUSTOM_NAME, FORMAT.fromString("§4§lThis is §8a §ktest", ColorHandling.RESET, DeserializerUnknownHandling.IGNORE));
        register(registry.ITEM_NAME, FORMAT.fromString("§oThis is another §3test", ColorHandling.RESET, DeserializerUnknownHandling.IGNORE));
        register(registry.LORE, Arrays.asList(FORMAT.fromString("§4test1", ColorHandling.RESET, DeserializerUnknownHandling.IGNORE), FORMAT.fromString("§ctest2", ColorHandling.RESET, DeserializerUnknownHandling.IGNORE)));
        register(registry.RARITY, Rarity.EPIC);
        register(registry.ENCHANTMENTS, new Enchantments(init(() -> {
            Map<RegistryEntry, Integer> enchantments = new HashMap<>();
            enchantments.put(registry.getRegistries().enchantment.getEntry(Identifier.of("sharpness")), 5);
            enchantments.put(registry.getRegistries().enchantment.getEntry(Identifier.of("unbreaking")), 3);
            return enchantments;
        }), false));
        register(registry.CAN_PLACE_ON, new BlockPredicatesChecker(Collections.singletonList(new BlockPredicate(new TagEntryList(registry.getRegistries().block.getTag(Identifier.of("test"))), init(() -> {
            Map<String, BlockPredicate.ValueMatcher> valueMatchers = new HashMap<>();
            valueMatchers.put("test", new BlockPredicate.ValueMatcher("val"));
            valueMatchers.put("test2", new BlockPredicate.ValueMatcher("min val", null));
            return valueMatchers;
        }), new CompoundTag().addInt("abc", 123))), false));
        copy(registry.CAN_BREAK, registry.CAN_PLACE_ON);
        register(registry.ATTRIBUTE_MODIFIERS, new AttributeModifiers(Collections.singletonList(new AttributeModifier(registry.getRegistries().attributeModifier.getEntry(Identifier.of("attr")), new AttributeModifier.EntityAttribute(UUID.randomUUID(), "name", 12.34, AttributeModifier.EntityAttribute.Operation.ADD_MULTIPLIED_TOTAL))), false));
        register(registry.CUSTOM_MODEL_DATA, 1234);
        register(registry.HIDE_ADDITIONAL_TOOLTIP, true);
        register(registry.HIDE_TOOLTIP, true);
        register(registry.REPAIR_COST, 987);
//        register(registry.CREATIVE_SLOT_LOCK, true);
        register(registry.ENCHANTMENT_GLINT_OVERRIDE, true);
        register(registry.INTANGIBLE_PROJECTILE, true);
        register(registry.FOOD, new Food(10, 20, true, 14, Collections.singletonList(new Food.Effect(new StatusEffect(registry.getRegistries().statusEffect.getEntry(Identifier.of("test")), 123, 456, true, true, false, null), 0.5F))));
        register(registry.FIRE_RESISTANT, true);
        register(registry.TOOL, new ToolComponent(Collections.singletonList(new ToolComponent.Rule(new TagEntryList(Arrays.asList(registry.getRegistries().block.getEntry(Identifier.of("test1")), registry.getRegistries().block.getEntry(Identifier.of("test2")))), 10F, true)), 20, 10));
        copy(registry.STORED_ENCHANTMENTS, registry.ENCHANTMENTS);
        register(registry.DYED_COLOR, new DyedColor(0xFFAABB, false));
        register(registry.MAP_COLOR, 0xAABBCC);
        register(registry.MAP_ID, -12);
        register(registry.MAP_DECORATIONS, init(() -> {
            Map<String, MapDecoration> mapDecorations = new HashMap<>();
            mapDecorations.put("test", new MapDecoration(registry.getRegistries().mapDecorationType.getEntry(Identifier.of("test")), 12, 13, 1.8F));
            return mapDecorations;
        }));
//        register(registry.MAP_POST_PROCESSING, MapPostProcessing.LOCK);
        register(registry.CHARGED_PROJECTILES, Collections.singletonList(new ItemStack(registry.getRegistries().item.getEntry(Identifier.of("test")), 12, registry.getItemDefaults())));
        register(registry.BUNDLE_CONTENTS, Collections.singletonList(new ItemStack(registry.getRegistries().item.getEntry(Identifier.of("test2")), 34, registry.getItemDefaults())));
        register(registry.POTION_CONTENTS, new PotionContents(registry.getRegistries().statusEffect.getEntry(Identifier.of("test")), 123, Arrays.asList(new StatusEffect(registry.getRegistries().statusEffect.getEntry(Identifier.of("test2")), 123, 456, true, true, false, null), new StatusEffect(registry.getRegistries().statusEffect.getEntry(Identifier.of("test3")), 123, 456, true, true, false, null))));
        register(registry.SUSPICIOUS_STEW_EFFECTS, Arrays.asList(new SuspiciousStewEffect(registry.getRegistries().item.getEntry(Identifier.of("test")), 123), new SuspiciousStewEffect(registry.getRegistries().statusEffect.getEntry(Identifier.of("test12")), 654)));
        register(registry.WRITABLE_BOOK_CONTENT, new WritableBook(Arrays.asList(new RawFilteredPair<>("page1", "filtered page 1"), new RawFilteredPair<>("page2", "filtered page 2"))));
        register(registry.WRITTEN_BOOK_CONTENT, new WrittenBook(new RawFilteredPair<>("title"), "author", 3, Arrays.asList(new RawFilteredPair<>(new StringComponent("page1")), new RawFilteredPair<>(new StringComponent("page2"), new StringComponent("filtered page2"))), true));
        register(registry.TRIM, new ArmorTrim(registry.getRegistries().armorTrimMaterial.getLeftEntry(Identifier.of("test")), new EitherEntry<>(new ArmorTrimPattern(Identifier.of("pattern"), registry.getRegistries().armorTrimPattern.getEntry(Identifier.of("test")), new StringComponent("description"))), false));
        register(registry.DEBUG_STICK_STATE, init(() -> {
            Map<RegistryEntry, String> states = new HashMap<>();
            states.put(registry.getRegistries().block.getEntry(Identifier.of("test")), "state");
            states.put(registry.getRegistries().block.getEntry(Identifier.of("test2")), "state2");
            return states;
        }));
        register(registry.ENTITY_DATA, new CompoundTag().addString("id", "pig"));
        register(registry.BUCKET_ENTITY_DATA, new CompoundTag().addLongArray("test2", 4, 5, 6));
        register(registry.BLOCK_ENTITY_DATA, new CompoundTag().addString("id", "pig"));
        register(registry.INSTRUMENT, new EitherEntry<>(new Instrument(new EitherEntry<>(new SoundEvent(Identifier.of("test"), 0.5F)), 12, 13)));
        register(registry.OMINOUS_BOTTLE_AMPLIFIER, 2);
        register(registry.RECIPES, Arrays.asList(Identifier.of("test"), Identifier.of("test2")));
        register(registry.LODESTONE_TRACKER, new LodestoneTracker(new LodestoneTracker.GlobalPos(Identifier.of("test"), new BlockPos(12, 21, 34)), false));
        register(registry.FIREWORK_EXPLOSION, new FireworkExplosions(FireworkExplosions.ExplosionShape.CREEPER, Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6), true, true));
        register(registry.FIREWORKS, new Fireworks(5, Collections.singletonList(new FireworkExplosions(FireworkExplosions.ExplosionShape.STAR, Arrays.asList(8, 9, 7), Arrays.asList(3, 5, 7), true, false))));
        register(registry.PROFILE, new GameProfile("name", UUID.randomUUID(), init(() -> {
            Map<String, List<GameProfile.Property>> properties = new HashMap<>();
            properties.put("test", Collections.singletonList(new GameProfile.Property("test", "value", "signature")));
            return properties;
        })));
        register(registry.NOTE_BLOCK_SOUND, Identifier.of("test"));
        register(registry.BANNER_PATTERNS, Collections.singletonList(new BannerPattern(new EitherEntry<>(new BannerPattern.Pattern(Identifier.of("test"), "translation")), DyeColor.RED)));
        register(registry.BASE_COLOR, DyeColor.YELLOW);
        register(registry.POT_DECORATIONS, Arrays.asList(registry.getRegistries().item.getEntry(Identifier.of("test")), registry.getRegistries().item.getEntry(Identifier.of("test2"))));
        register(registry.CONTAINER, Arrays.asList(new ContainerSlot(10, new ItemStack(registry.getRegistries().item.getEntry(Identifier.of("test")), 12, registry.getItemDefaults())), new ContainerSlot(20, new ItemStack(registry.getRegistries().item.getEntry(Identifier.of("test2")), 34, registry.getItemDefaults()))));
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

}
