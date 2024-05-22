package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.codec.Either;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.NbtConverter_v1_20_3;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.ItemComponents_v1_21;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21;
import net.lenni0451.mcstructs.nbt.INbtTag;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21.AttributeModifier;
import static net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21.AttributeModifiers;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test_v1_21 {

    private static final Map<ItemComponent<?>, Object> itemComponents = new HashMap<>();

    static {
        ItemComponents_v1_21 registry = ItemComponentRegistry.V1_21;
        register(registry.ATTRIBUTE_MODIFIERS, new AttributeModifiers(Collections.singletonList(new AttributeModifier(Identifier.of("attr"), new AttributeModifier.EntityAttribute(Identifier.of("te", "st"), 12.34, AttributeModifier.EntityAttribute.Operation.ADD_MULTIPLIED_TOTAL))), false));
        register(registry.FOOD, new Types_v1_21.Food(10, 20, true, 14, new Types_v1_20_5.ItemStack(Identifier.of("test", "test"), 63, new ItemComponentMap(registry)), Collections.singletonList(new Types_v1_20_5.Food.Effect(new Types_v1_20_5.StatusEffect(Identifier.of("test"), 123, 456, true, true, false, null), 0.5F))));
        register(registry.JUKEBOX_PLAYABLE, new Types_v1_21.JukeboxPlayable(Either.left(Identifier.of("test")), false));
    }

    private static <T> void register(final ItemComponent<T> itemComponent, final T value) {
        itemComponents.put(itemComponent, value);
    }

    private static <T> T init(final Supplier<T> supplier) {
        return supplier.get();
    }

    @Test
    void test() {
        ItemComponentMap map = new ItemComponentMap(ItemComponentRegistry.V1_21);
        for (Map.Entry<ItemComponent<?>, Object> entry : itemComponents.entrySet()) {
            map.set(cast(entry.getKey()), entry.getValue());
        }

        INbtTag tag = map.to(NbtConverter_v1_20_3.INSTANCE);
        ItemComponentMap deserialized = ItemComponentRegistry.V1_21.mapFrom(NbtConverter_v1_20_3.INSTANCE, tag);
        assertEquals(map.size(), deserialized.size());
        for (Map.Entry<ItemComponent<?>, ?> entry : deserialized.getValues().entrySet()) {
            assertEquals(itemComponents.get(entry.getKey()), entry.getValue());
        }
    }

    private static <T> T cast(final Object o) {
        return (T) o;
    }

}
