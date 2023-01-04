package net.lenni0451.mcstructs.inventory;

import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.info.ItemTag;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.items.stacks.ItemStack;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

class SlotTest {

    private static final ItemRegistry<Integer, ItemStack<Integer>> itemRegistry = new ItemRegistry<>(ItemStack::new);
    private static final TestInventory inventory = new TestInventory();
    private static final Slot<TestInventory, Integer, ItemStack<Integer>> slot = new Slot<>(inventory, 0, 0, (testInventoryIntegerItemStackSlot, integerItemStack) -> integerItemStack.getItem() == 1 ? 1 : 0);

    @BeforeAll
    static void init() {
        itemRegistry.getMeta(1).addTypes(ItemType.PAPER);
        itemRegistry.getMeta(2).addTags(ItemTag.DAMAGEABLE);
    }

    @Test
    void acceptAll() {
        BiFunction<Slot<TestInventory, Integer, ItemStack<Integer>>, ItemStack<Integer>, Integer> acceptor = Slot.acceptAll();
        assertNull(acceptor);
    }

    @Test
    void acceptNone() {
        BiFunction<Slot<TestInventory, Integer, ItemStack<Integer>>, ItemStack<Integer>, Integer> acceptor = Slot.acceptNone();
        assertEquals(0, acceptor.apply(slot, itemRegistry.create(0)));
    }

    @Test
    void accept() {
        BiFunction<Slot<TestInventory, Integer, ItemStack<Integer>>, ItemStack<Integer>, Integer> acceptor = Slot.accept(5);
        assertEquals(5, acceptor.apply(slot, itemRegistry.create(0)));
    }

    @Test
    void acceptTypes() {
        BiFunction<Slot<TestInventory, Integer, ItemStack<Integer>>, ItemStack<Integer>, Integer> acceptor = Slot.acceptTypes(ItemType.PAPER);
        assertEquals(0, acceptor.apply(slot, itemRegistry.create(0)));
        assertEquals(64, acceptor.apply(slot, itemRegistry.create(1)));
    }

    @Test
    void acceptTags() {
        BiFunction<Slot<TestInventory, Integer, ItemStack<Integer>>, ItemStack<Integer>, Integer> acceptor = Slot.acceptTags(ItemTag.DAMAGEABLE);
        assertEquals(0, acceptor.apply(slot, itemRegistry.create(0)));
        assertEquals(64, acceptor.apply(slot, itemRegistry.create(2)));
    }

    @Test
    void accepts() {
        assertFalse(slot.accepts(itemRegistry.create(0)));
        assertTrue(slot.accepts(itemRegistry.create(1)));
    }

    @Test
    void acceptsCount() {
        assertEquals(0, slot.acceptsCount(itemRegistry.create(0)));
        assertEquals(1, slot.acceptsCount(itemRegistry.create(1)));
    }

}
