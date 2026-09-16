package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.v26_2.NbtConverter_v26_2;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11.Types_v1_21_11.Consumable;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11.Types_v1_21_11.SwingAnimation;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11.Types_v1_21_11.SwingAnimationType;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_2.Types_v26_2.ItemStackTemplate;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_3.ItemComponents_v26_3;
import net.lenni0451.mcstructs.itemcomponents.impl.v26_3.Types_v26_3.*;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.registry.Holder;
import net.lenni0451.mcstructs.registry.TagEntryList;
import net.lenni0451.mcstructs.text.components.StringComponent;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Test_v26_3 extends ItemComponentTest<ItemComponents_v26_3> {

    @Override
    protected ItemComponents_v26_3 getRegistry() {
        return ItemComponentRegistry.V26_3;
    }

    @Override
    protected DataConverter<NbtTag> getConverter() {
        return NbtConverter_v26_2.INSTANCE;
    }

    @Override
    protected void register(ItemComponents_v26_3 registry) {
        this.register(registry.ATTACK_ANIMATION, new SwingAnimation(SwingAnimationType.STAB, 0));
        this.register(registry.INTERACT_ANIMATION, new SwingAnimation(SwingAnimationType.WHACK, 6));
        this.register(registry.BLOCK_TRANSFORMER, registry.getRegistries().blockTransformer.getEntry(Identifier.of("test")));
        this.register(registry.VILLAGER_FOOD, new VillagerFood(5));
        this.register(registry.COMPOSTABLE, new Compostable(new ResolvableInt(1)));
        this.register(registry.COOKING_FUEL, new CookingFuel(new ResolvableInt(200), new ResolvableFloat(1.5F)));
        this.register(registry.BREWING_FUEL, new BrewingFuel(new ResolvableInt(Identifier.of("test:provider")), new ResolvableFloat(Identifier.of("test:speed"))));
        this.register(registry.MOB_VISIBILITY, new MobVisibility(new TagEntryList(Collections.singletonList(registry.getRegistries().entityType.getEntry(Identifier.of("test")))), 0.5F));
        this.register(registry.PROVIDES_POTTERY_PATTERN, registry.getRegistries().decoratedPotPattern.getEntry(Identifier.of("test")));
        this.register(registry.SIGN_TEXT_FRONT, new SignText(Arrays.asList(new StringComponent("Line 1"), new StringComponent("Line 2"), new StringComponent("Line 3"), new StringComponent("Line 4")), Arrays.asList(new StringComponent("FLine 1"), new StringComponent("FLine 2"), new StringComponent("FLine 3"), new StringComponent("FLine 4")), Types_v1_20_5.DyeColor.RED, true));
        this.register(registry.SIGN_TEXT_BACK, new SignText(Arrays.asList(new StringComponent("BLine 1"), new StringComponent("BLine 2"), new StringComponent("BLine 3"), new StringComponent("BLine 4"))));
        this.register(registry.WAXED, true);
        this.register(registry.CUSHION_COLOR, Types_v1_20_5.DyeColor.BLUE);
        this.register(registry.POT_DECORATIONS, new PotDecorations(new ItemStackTemplate(registry.getRegistries().item.getEntry(Identifier.of("test1")), 1, registry.getItemDefaults()), new ItemStackTemplate(registry.getRegistries().item.getEntry(Identifier.of("test2")), 1, registry.getItemDefaults()), null, null));
        this.register(registry.CONSUMABLE, new Consumable(1.6F, Consumable.ItemUseAnimation.EAT, registry.getRegistries().sound.getHolder(Identifier.of("test")), true, Collections.singletonList(new TeleportRandomly(16F, false))));
        this.register(registry.INSTRUMENT, new Holder<>(new Instrument(registry.getRegistries().sound.getHolder(Identifier.of("test")), 10F, 50F, 1, new StringComponent("description"))));
        this.register(registry.PROVIDES_TRIM_MATERIAL, new Holder<>(new ArmorTrimMaterial(Identifier.of("minecraft:gold"), new StringComponent("Gold Trim"))));
        this.register(registry.TRIM, new ArmorTrim(new Holder<>(new ArmorTrimMaterial(Identifier.of("minecraft:iron"), new StringComponent("Iron Trim"))), registry.getRegistries().armorTrimPattern.getHolder(Identifier.of("test"))));
    }

    @Test
    void testRemovedComponents() {
        assertNull(this.getRegistry().getComponent(Identifier.of("swing_animation")));
        assertNull(this.getRegistry().getComponent(Identifier.of("map_color")));
        assertEquals(122, this.getRegistry().getComponentList().size());
    }

}
