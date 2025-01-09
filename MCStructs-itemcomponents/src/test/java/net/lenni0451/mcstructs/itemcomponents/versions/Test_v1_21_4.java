package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4.ItemComponents_v1_21_4;
import net.lenni0451.mcstructs.nbt.NbtTag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4.Types_v1_21_4.CustomModelData;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test_v1_21_4 {

    private static final Map<ItemComponent<?>, Object> itemComponents = new HashMap<>();

    static {
        ItemComponents_v1_21_4 registry = ItemComponentRegistry.V1_21_4;
        register(registry.CUSTOM_MODEL_DATA, new CustomModelData(Arrays.asList(1F, 2F), Arrays.asList(true, false), Arrays.asList("test1", "test2"), Arrays.asList(0x123456, 0x789ABC)));
        register(registry.EQUIPPABLE, new Types_v1_21_2.Equippable(Types_v1_21_2.EquipmentSlot.CHEST, Either.left(Identifier.of("test")), Identifier.of("test"), Identifier.of("test2"), new Types_v1_20_5.TagEntryList(Arrays.asList(Identifier.of("test1"), Identifier.of("test2"))), false, false, false));
    }

    private static <T> void register(final ItemComponent<T> itemComponent, final T value) {
        itemComponents.put(itemComponent, value);
    }

    private static <T> T init(final Supplier<T> supplier) {
        return supplier.get();
    }

    @Test
    void test() {
        ItemComponentMap map = new ItemComponentMap(ItemComponentRegistry.V1_21_4);
        for (Map.Entry<ItemComponent<?>, Object> entry : itemComponents.entrySet()) {
            map.set(cast(entry.getKey()), entry.getValue());
        }

        NbtTag tag = map.to(NbtConverter_v1_20_3.INSTANCE);
        ItemComponentMap deserialized = ItemComponentRegistry.V1_21_4.mapFrom(NbtConverter_v1_20_3.INSTANCE, tag);
        assertEquals(map.size(), deserialized.size());
        for (Map.Entry<ItemComponent<?>, ?> entry : deserialized.getValues().entrySet()) {
            assertEquals(itemComponents.get(entry.getKey()), entry.getValue());
        }
    }

    private static <T> T cast(final Object o) {
        return (T) o;
    }

}
