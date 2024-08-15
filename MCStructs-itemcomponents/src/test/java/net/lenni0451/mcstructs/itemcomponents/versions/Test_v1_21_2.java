package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.ItemComponents_v1_21_2;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_1_21_2;
import net.lenni0451.mcstructs.nbt.INbtTag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test_v1_21_2 {

    private static final Map<ItemComponent<?>, Object> itemComponents = new HashMap<>();

    static {
        ItemComponents_v1_21_2 registry = ItemComponentRegistry.V1_21_2;
        register(registry.ENCHANTABLE, new Types_1_21_2.Enchantable(123));
        register(registry.REPAIRABLE, new Types_1_21_2.Repairable(new Types_v1_20_5.TagEntryList(Arrays.asList(Identifier.of("test1"), Identifier.of("test2")))));
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

        INbtTag tag = map.to(NbtConverter_v1_20_3.INSTANCE);
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
