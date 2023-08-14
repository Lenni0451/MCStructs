package net.lenni0451.mcstructs.enchantments;

import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class EnchantmentRegistryTest {

    private static final ItemRegistry<Integer, LegacyItemStack<Integer>> itemRegistry = new ItemRegistry<>(LegacyItemStack::new);
    private static final EnchantmentRegistry<Integer, LegacyItemStack<Integer>> enchantmentRegistry = new EnchantmentRegistry<>();

    @BeforeAll
    static void prepare() {
        Enchantment enchantment1 = new Enchantment("enchantment 1", 0, 1, 2, 0);
        Enchantment enchantment2 = new Enchantment("enchantment 2", 1, 1, 5, 2);
        Enchantment enchantment3 = new Enchantment("enchantment 3", 2, 4, 5, 8);
        enchantmentRegistry.register(enchantment1);
        enchantmentRegistry.register(enchantment2);
        enchantmentRegistry.register(enchantment3);

        enchantmentRegistry.addIncompatibility(enchantment1, enchantment2);
        enchantmentRegistry.addItemCompatibility(enchantment3, integerLegacyItemStack -> integerLegacyItemStack.getItem() == 0);
    }

    @ParameterizedTest
    @CsvSource({
            "0, enchantment 1, 1, 2, 0",
            "1, enchantment 2, 1, 5, 2",
            "2, enchantment 3, 4, 5, 8"
    })
    void get(final int id, final String name, final int minLevel, final int maxLevel, final int rarity) {
        Enchantment enchantment = enchantmentRegistry.get(id);
        assertNotNull(enchantment);
        assertEquals(enchantmentRegistry.get(name), enchantment);
        assertEquals(name, enchantment.getName());
        assertEquals(minLevel, enchantment.getMinLevel());
        assertEquals(maxLevel, enchantment.getMaxLevel());
        assertEquals(rarity, enchantment.getRarity());
    }

    @Test
    void isIncompatible() {
        Enchantment enchantment1 = enchantmentRegistry.get("enchantment 1");
        Enchantment enchantment2 = enchantmentRegistry.get("enchantment 2");
        Enchantment enchantment3 = enchantmentRegistry.get("enchantment 3");

        assertTrue(enchantmentRegistry.isIncompatible(enchantment1, enchantment2));
        assertFalse(enchantmentRegistry.isIncompatible(enchantment1, enchantment3));
        assertFalse(enchantmentRegistry.isIncompatible(enchantment2, enchantment3));
    }

    @Test
    void isItemCompatible() {
        Enchantment enchantment1 = enchantmentRegistry.get("enchantment 1");
        Enchantment enchantment2 = enchantmentRegistry.get("enchantment 2");
        Enchantment enchantment3 = enchantmentRegistry.get("enchantment 3");

        LegacyItemStack<Integer> item1 = itemRegistry.create(0);
        LegacyItemStack<Integer> item2 = itemRegistry.create(1);

        assertTrue(enchantmentRegistry.isItemCompatible(enchantment1, item1));
        assertTrue(enchantmentRegistry.isItemCompatible(enchantment2, item1));
        assertTrue(enchantmentRegistry.isItemCompatible(enchantment3, item1));
        assertTrue(enchantmentRegistry.isItemCompatible(enchantment1, item2));
        assertTrue(enchantmentRegistry.isItemCompatible(enchantment2, item2));
        assertFalse(enchantmentRegistry.isItemCompatible(enchantment3, item2));
    }

}
