package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.v1_21_5.NbtConverter_v1_21_5;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5.ItemComponents_v1_21_5;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.text.components.StringComponent;

import java.util.Arrays;

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
        register(registry.WEAPON, new Weapon(32, true));
        register(registry.POTION_DURATION_SCALE, 12.3F);
        register(registry.TOOL, new ToolComponent(Arrays.asList(new Types_v1_20_5.ToolComponent.Rule(new Types_v1_20_5.TagEntryList(Identifier.of("test")), 10F, true)), 12.3F, 10, true));
        register(registry.VILLAGER_VARIANT, VillagerVariant.SAVANNA);
        register(registry.WOLF_VARIANT, Either.right(new WolfVariant(Identifier.of("test1"), Identifier.of("test2"), Identifier.of("test3"), new Types_v1_20_5.TagEntryList(Identifier.of("test")))));
        register(registry.WOLF_COLLAR, Types_v1_20_5.DyeColor.BLACK);
        register(registry.FOX_VARIANT, FoxVariant.SNOW);
        register(registry.SALMON_SIZE, SalmonSize.LARGE);
        register(registry.PARROT_VARIANT, ParrotVariant.GREEN);
        register(registry.TROPICAL_FISH_PATTERN, TropicalFishPattern.BETTY);
        register(registry.TROPICAL_FISH_BASE_COLOR, Types_v1_20_5.DyeColor.GREEN);
        register(registry.TROPICAL_FISH_PATTERN_COLOR, Types_v1_20_5.DyeColor.MAGENTA);
        register(registry.MOOSHROOM_VARIANT, MooshroomVariant.BROWN);
        register(registry.RABBIT_VARIANT, RabbitVariant.EVIL);
        register(registry.PIG_VARIANT, Either.right(new PigVariant(PigVariant.ModelType.NORMAL, Identifier.of("test"), new Types_v1_20_5.TagEntryList(Identifier.of("test")))));
        register(registry.FROG_VARIANT, Identifier.of("test"));
        register(registry.HORSE_VARIANT, HorseVariant.BROWN);
        register(registry.PAINTING_VARIANT, Either.right(new PaintingVariant(1, 2, Identifier.of("test"), new StringComponent("a"), new StringComponent("b"))));
        register(registry.LLAMA_VARIANT, LlamaVariant.BROWN);
        register(registry.AXOLOTL_VARIANT, AxolotlVariant.BLUE);
        register(registry.CAT_VARIANT, Identifier.of("test"));
        register(registry.CAT_COLLAR, Types_v1_20_5.DyeColor.LIGHT_BLUE);
        register(registry.SHEEP_COLOR, Types_v1_20_5.DyeColor.MAGENTA);
        register(registry.SHULKER_COLOR, Types_v1_20_5.DyeColor.WHITE);
    }

}
