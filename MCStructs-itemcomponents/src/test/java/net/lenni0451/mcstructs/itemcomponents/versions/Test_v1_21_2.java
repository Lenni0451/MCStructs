package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.ItemComponents_v1_21_2;
import net.lenni0451.mcstructs.nbt.NbtTag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test_v1_21_2 {

    private static final Map<ItemComponent<?>, Object> itemComponents = new HashMap<>();

    static {
        ItemComponents_v1_21_2 registry = ItemComponentRegistry.V1_21_2;
        register(registry.ITEM_MODEL, Identifier.of("test"));
        register(registry.FOOD, new Food(123, 0.24F, false));
        register(registry.CONSUMABLE, new Consumable(1.87F, Consumable.ItemUseAnimation.BOW, Either.left(Identifier.of("test")), false, Arrays.asList(new ConsumeEffect.ClearAllEffects(), new ConsumeEffect.RemoveEffects(new Types_v1_20_5.TagEntryList(Identifier.of("test"))))));
        register(registry.USE_REMAINDER, new Types_v1_20_5.ItemStack(Identifier.of("test"), 123, registry.getItemDefaults()));
        register(registry.USE_COOLDOWN, new UseCooldown(1.23F, Identifier.of("test")));
        register(registry.DAMAGE_RESISTANT, new DamageResistant(Identifier.of("test")));
        register(registry.ENCHANTABLE, new Enchantable(123));
        register(registry.EQUIPPABLE, new Equippable(EquipmentSlot.CHEST, Either.left(Identifier.of("test")), Identifier.of("test"), Identifier.of("test2"), new Types_v1_20_5.TagEntryList(Arrays.asList(Identifier.of("test1"), Identifier.of("test2"))), false, false, false));
        register(registry.REPAIRABLE, new Repairable(new Types_v1_20_5.TagEntryList(Arrays.asList(Identifier.of("test1"), Identifier.of("test2")))));
        register(registry.GLIDER, true);
        register(registry.TOOLTIP_STYLE, Identifier.of("test"));
        register(registry.DEATH_PROTECTION, new DeathProtection(Arrays.asList(new ConsumeEffect.ClearAllEffects(), new ConsumeEffect.TeleportRandomly(10))));
        register(registry.LOCK, new ItemPredicate(new Types_v1_20_5.TagEntryList(Identifier.of("test")), new MinMaxInt(1, 10), Collections.emptyMap(), Collections.emptyMap()));
    }

    private static <T> void register(final ItemComponent<T> itemComponent, final T value) {
        itemComponents.put(itemComponent, value);
    }

    private static <T> T init(final Supplier<T> supplier) {
        return supplier.get();
    }

    @Test
    void test() {
        ItemComponentMap map = new ItemComponentMap(ItemComponentRegistry.V1_21_2);
        for (Map.Entry<ItemComponent<?>, Object> entry : itemComponents.entrySet()) {
            map.set(cast(entry.getKey()), entry.getValue());
        }

        NbtTag tag = map.to(NbtConverter_v1_20_3.INSTANCE);
        ItemComponentMap deserialized = ItemComponentRegistry.V1_21_2.mapFrom(NbtConverter_v1_20_3.INSTANCE, tag);
        assertEquals(map.size(), deserialized.size());
        for (Map.Entry<ItemComponent<?>, ?> entry : deserialized.getValues().entrySet()) {
            assertEquals(itemComponents.get(entry.getKey()), entry.getValue());
        }
    }

    private static <T> T cast(final Object o) {
        return (T) o;
    }

}
