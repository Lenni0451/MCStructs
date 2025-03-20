package net.lenni0451.mcstructs.itemcomponents.versions;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.impl.v1_21_5.HashConverter_v1_21_5;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.nbt.NbtTag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class ItemComponentTest<R extends ItemComponentRegistry> {

    private final Map<ItemComponent<?>, Object> itemComponents = new HashMap<>();

    protected abstract R getRegistry();

    protected abstract DataConverter<NbtTag> getConverter();

    protected abstract void register(final R registry);

    protected <T> void register(final ItemComponent<T> itemComponent, final T value) {
        this.itemComponents.put(itemComponent, value);
    }

    protected void copy(final ItemComponent<?> itemComponent, final ItemComponent<?> from) {
        this.itemComponents.put(itemComponent, this.itemComponents.get(from));
    }

    protected <T> T init(final Supplier<T> supplier) {
        return supplier.get();
    }

    protected <T> T cast(final Object o) {
        return (T) o;
    }

    @Test
    void test() {
        this.register(this.getRegistry());
        ItemComponentMap map = new ItemComponentMap(this.getRegistry());
        for (Map.Entry<ItemComponent<?>, Object> entry : this.itemComponents.entrySet()) {
            map.set(this.cast(entry.getKey()), entry.getValue());
        }

        NbtTag tag = map.to(this.getConverter());
        ItemComponentMap deserialized = this.getRegistry().mapFrom(this.getConverter(), tag);
        assertEquals(map.size(), deserialized.size());
        for (Map.Entry<ItemComponent<?>, ?> entry : deserialized.getValues().entrySet()) {
            assertEquals(this.itemComponents.get(entry.getKey()), entry.getValue());
        }

        for (Map.Entry<ItemComponent<?>, Object> entry : this.itemComponents.entrySet()) {
            assertDoesNotThrow(() -> entry.getKey().getCodec().serialize(HashConverter_v1_21_5.CRC32C, this.cast(entry.getValue())));
        }
    }

}
