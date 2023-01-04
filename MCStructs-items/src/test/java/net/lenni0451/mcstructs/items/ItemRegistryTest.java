package net.lenni0451.mcstructs.items;

import net.lenni0451.mcstructs.items.info.ItemMeta;
import net.lenni0451.mcstructs.items.info.ItemTag;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemRegistryTest {

    private static final ItemRegistry<Integer, LegacyItemStack<Integer>> itemRegistry = new ItemRegistry<>(LegacyItemStack::new);

    @BeforeAll
    static void prepare() {
        itemRegistry
                .getMeta(0)
                .addTypes(ItemType.SWORD)
                .addTags(ItemTag.DAMAGEABLE)
                .setMaxDamage(100)
                .setMaxCount(1);
        itemRegistry
                .getMeta(1)
                .addTypes(ItemType.SWORD)
                .addTags(ItemTag.DAMAGEABLE)
                .setMaxDamage(200)
                .setMaxCount(1);
    }

    @Test
    void getMeta() {
        ItemMeta meta = itemRegistry.getMeta(-1);
        assertNotNull(meta);
        assertTrue(meta.getTypes().isEmpty());
        assertTrue(meta.getTags().isEmpty());
        assertEquals(64, meta.getMaxCount());
        assertEquals(0, meta.getMaxDamage());
    }

    @Test
    void byType() {
        assertEquals(2, itemRegistry.byType(ItemType.SWORD).size());
        assertEquals(0, itemRegistry.byType(ItemType.BOW).size());
    }

    @Test
    void requireByType() {
        assertDoesNotThrow(() -> itemRegistry.requireByType(ItemType.SWORD, 2));
        assertThrows(IllegalArgumentException.class, () -> itemRegistry.requireByType(ItemType.BOW));
    }

    @Test
    void empty() {
        assertNull(itemRegistry.empty().getItem());
    }

    @Test
    void create() {
        LegacyItemStack<Integer> stack = itemRegistry.create(0);
        assertEquals(0, stack.getItem());
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.getCount());
        assertEquals(0, stack.getDamage());
        assertNull(stack.getTag());
        assertFalse(stack.hasTag());
        assertEquals(stack.copy(), stack);
        assertEquals(0, stack.getRepairCost());
        assertFalse(stack.hasCustomName());
        assertNull(stack.getCustomName());
    }

}