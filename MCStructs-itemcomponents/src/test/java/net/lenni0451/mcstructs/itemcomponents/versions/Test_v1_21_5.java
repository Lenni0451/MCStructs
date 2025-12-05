package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.v1_21_5.NbtConverter_v1_21_5;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5.ItemComponents_v1_21_5;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.registry.EitherHolder;
import net.lenni0451.mcstructs.registry.Holder;
import net.lenni0451.mcstructs.registry.RegistryEntry;
import net.lenni0451.mcstructs.registry.TagEntryList;
import net.lenni0451.mcstructs.text.components.StringComponent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5.Types_v1_21_5.*;

public class Test_v1_21_5 extends ItemComponentTest<ItemComponents_v1_21_5> {

    @Override
    protected ItemComponents_v1_21_5 getRegistry() {
        return ItemComponentRegistry.V1_21_5;
    }

    @Override
    protected DataConverter<NbtTag> getConverter() {
        return NbtConverter_v1_21_5.INSTANCE;
    }

    @Override
    protected void register(ItemComponents_v1_21_5 registry) {
        register(registry.CUSTOM_NAME, new StringComponent("Test"));
        register(registry.ITEM_NAME, new StringComponent("Test"));
        register(registry.LORE, Arrays.asList(new StringComponent("Test1"), new StringComponent("Test2")));
        register(registry.WRITTEN_BOOK_CONTENT, new Types_v1_20_5.WrittenBook(new Types_v1_20_5.RawFilteredPair<>("title"), "author", 3, Arrays.asList(new Types_v1_20_5.RawFilteredPair<>(new StringComponent("page1")), new Types_v1_20_5.RawFilteredPair<>(new StringComponent("page2"), new StringComponent("filtered page2"))), true));
        register(registry.WEAPON, new Weapon(32, 12.3F));
        register(registry.POTION_DURATION_SCALE, 12.3F);
        register(registry.TOOL, new ToolComponent(Arrays.asList(new Types_v1_20_5.ToolComponent.Rule(new TagEntryList(registry.getRegistries().block.getTag(Identifier.of("test"))), 10F, true)), 12.3F, 10, true));
        register(registry.VILLAGER_VARIANT, VillagerVariant.SAVANNA);
        register(registry.WOLF_VARIANT, registry.getRegistries().wolfVariant.getEntry(Identifier.of("test")));
        register(registry.WOLF_SOUND_VARIANT, registry.getRegistries().wolfSoundVariant.getEntry(Identifier.of("test")));
        register(registry.WOLF_COLLAR, Types_v1_20_5.DyeColor.BLACK);
        register(registry.FOX_VARIANT, FoxVariant.SNOW);
        register(registry.SALMON_SIZE, SalmonSize.LARGE);
        register(registry.PARROT_VARIANT, ParrotVariant.GREEN);
        register(registry.TROPICAL_FISH_PATTERN, TropicalFishPattern.BETTY);
        register(registry.TROPICAL_FISH_BASE_COLOR, Types_v1_20_5.DyeColor.GREEN);
        register(registry.TROPICAL_FISH_PATTERN_COLOR, Types_v1_20_5.DyeColor.MAGENTA);
        register(registry.MOOSHROOM_VARIANT, MooshroomVariant.BROWN);
        register(registry.RABBIT_VARIANT, RabbitVariant.EVIL);
        register(registry.PIG_VARIANT, registry.getRegistries().pigVariant.getEntry(Identifier.of("test")));
        register(registry.COW_VARIANT, registry.getRegistries().cowVariant.getEntry(Identifier.of("test")));
        register(registry.CHICKEN_VARIANT, new EitherHolder<>(registry.getRegistries().chickenVariant.getHolder(Identifier.of("test"))));
        register(registry.FROG_VARIANT, registry.getRegistries().frogVariant.getEntry(Identifier.of("test")));
        register(registry.HORSE_VARIANT, HorseVariant.BROWN);
        register(registry.PAINTING_VARIANT, new Holder<>(new PaintingVariant(1, 2, Identifier.of("test"), new StringComponent("a"), new StringComponent("b"))));
        register(registry.LLAMA_VARIANT, LlamaVariant.BROWN);
        register(registry.AXOLOTL_VARIANT, AxolotlVariant.BLUE);
        register(registry.CAT_VARIANT, registry.getRegistries().catVariant.getEntry(Identifier.of("test")));
        register(registry.CAT_COLLAR, Types_v1_20_5.DyeColor.LIGHT_BLUE);
        register(registry.SHEEP_COLOR, Types_v1_20_5.DyeColor.MAGENTA);
        register(registry.SHULKER_COLOR, Types_v1_20_5.DyeColor.WHITE);
        register(registry.BLOCKS_ATTACKS, new BlocksAttacks(12, 34, Arrays.asList(new BlocksAttacks.DamageReduction(12, new TagEntryList(registry.getRegistries().damageType.getTag(Identifier.of("test"))), 1, 2)), new BlocksAttacks.ItemDamageFunction(12, 34, 56), registry.getRegistries().damageType.getTag(Identifier.of("test")), registry.getRegistries().sound.getHolder(Identifier.of("test")), registry.getRegistries().sound.getHolder(Identifier.of("test"))));
        register(registry.BREAK_SOUND, registry.getRegistries().sound.getHolder(Identifier.of("test")));
        register(registry.PROVIDES_BANNER_PATTERNS, registry.getRegistries().bannerPattern.getTag(Identifier.of("test")));
        register(registry.PROVIDES_TRIM_MATERIAL, registry.getRegistries().armorTrimMaterial.getTag(Identifier.of("test")));
        register(registry.TOOLTIP_DISPLAY, new TooltipDisplay(true, Arrays.asList(registry.TOOLTIP_DISPLAY, registry.BREAK_SOUND)));
        register(registry.ATTRIBUTE_MODIFIERS, Arrays.asList(new Types_v1_21.AttributeModifier(registry.getRegistries().attributeModifier.getEntry(Identifier.of("test")), new Types_v1_21.AttributeModifier.EntityAttribute(Identifier.of("test"), 1, Types_v1_21.AttributeModifier.EntityAttribute.Operation.ADD_MULTIPLIED_TOTAL))));
        register(registry.DYED_COLOR, 0xFF_00_FF);
        register(registry.CAN_PLACE_ON, Arrays.asList(new Types_v1_20_5.BlockPredicate(new TagEntryList(registry.getRegistries().block.getTag(Identifier.of("test"))), init(() -> {
            Map<String, Types_v1_20_5.BlockPredicate.ValueMatcher> valueMatchers = new HashMap<>();
            valueMatchers.put("test", new Types_v1_20_5.BlockPredicate.ValueMatcher("val"));
            valueMatchers.put("test2", new Types_v1_20_5.BlockPredicate.ValueMatcher("min val", null));
            return valueMatchers;
        }), new CompoundTag().addInt("abc", 123))));
        copy(registry.CAN_BREAK, registry.CAN_PLACE_ON);
        register(registry.ENCHANTMENTS, init(() -> {
            Map<RegistryEntry, Integer> enchantments = new HashMap<>();
            enchantments.put(registry.getRegistries().enchantment.getEntry(Identifier.of("test")), 1);
            enchantments.put(registry.getRegistries().enchantment.getEntry(Identifier.of("test2")), 2);
            return enchantments;
        }));
        copy(registry.STORED_ENCHANTMENTS, registry.ENCHANTMENTS);
        register(registry.TRIM, new ArmorTrim(registry.getRegistries().armorTrimMaterial.getHolder(Identifier.of("test")), registry.getRegistries().armorTrimPattern.getHolder(Identifier.of("test"))));
        register(registry.UNBREAKABLE, true);
    }

}
