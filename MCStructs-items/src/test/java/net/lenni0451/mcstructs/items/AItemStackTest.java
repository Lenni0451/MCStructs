package net.lenni0451.mcstructs.items;

import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AItemStackTest {

    private static final ItemRegistry<Integer, LegacyItemStack<Integer>> itemRegistry = new ItemRegistry<>(LegacyItemStack::new);
    private AItemStack<Integer, LegacyItemStack<Integer>> itemStack;

    @BeforeEach
    void prepare() {
        this.itemStack = new LegacyItemStack<>(itemRegistry, 1);
        this.itemStack.setCount(64);
        this.itemStack.setDamage(0);
    }

    @Test
    void getItem() {
        assertEquals(1, this.itemStack.getItem());
    }

    @Test
    void isEmpty() {
        assertFalse(this.itemStack.isEmpty());
    }

    @Test
    void getCount() {
        assertEquals(64, this.itemStack.getCount());
    }

    @Test
    void setCount() {
        this.itemStack.setCount(32);
        assertEquals(32, this.itemStack.getCount());
    }

    @Test
    void getDamage() {
        assertEquals(0, this.itemStack.getDamage());
    }

    @Test
    void setDamage() {
        this.itemStack.setDamage(1);
        assertEquals(1, this.itemStack.getDamage());
    }

    @Test
    void getTag() {
        assertNull(this.itemStack.getTag());
    }

    @Test
    void copyTag() {
        assertNull(this.itemStack.copyTag());
        this.itemStack.setTag(new CompoundTag());
        assertNotNull(this.itemStack.copyTag());
    }

    @Test
    void getOrCreateTag() {
        assertNotNull(this.itemStack.getOrCreateTag());
    }

    @Test
    void setTag() {
        assertNull(this.itemStack.getTag());
        this.itemStack.setTag(new CompoundTag());
        assertNotNull(this.itemStack.getTag());
    }

    @Test
    void hasTag() {
        assertFalse(this.itemStack.hasTag());
        this.itemStack.setTag(new CompoundTag());
        assertTrue(this.itemStack.hasTag());
    }

    @Test
    void copy() {
        this.itemStack.setTag(new CompoundTag());
        AItemStack<Integer, LegacyItemStack<Integer>> copy = this.itemStack.copy();
        assertEquals(this.itemStack.getItem(), copy.getItem());
        assertEquals(this.itemStack.getCount(), copy.getCount());
        assertEquals(this.itemStack.getDamage(), copy.getDamage());
        assertEquals(this.itemStack.getTag(), copy.getTag());
    }

    @Test
    void split() {
        this.itemStack.setTag(new CompoundTag());
        AItemStack<Integer, LegacyItemStack<Integer>> split = this.itemStack.split(32);
        assertEquals(this.itemStack.getItem(), split.getItem());
        assertEquals(32, split.getCount());
        assertEquals(this.itemStack.getDamage(), split.getDamage());
        assertEquals(this.itemStack.getTag(), split.getTag());
        assertEquals(32, this.itemStack.getCount());
    }

    @Test
    void getRepairCost() {
        assertEquals(0, this.itemStack.getRepairCost());
    }

    @Test
    void setRepairCost() {
        assertEquals(0, this.itemStack.getRepairCost());
        this.itemStack.setRepairCost(1);
        assertEquals(1, this.itemStack.getRepairCost());
        assertTrue(this.itemStack.hasTag());
        assertTrue(this.itemStack.getTag().contains("RepairCost", NbtType.INT));
    }

    @Test
    void hasCustomName() {
        assertFalse(this.itemStack.hasCustomName());
        this.itemStack.setCustomName("Test");
        assertTrue(this.itemStack.hasCustomName());
    }

    @Test
    void getCustomName() {
        assertNull(this.itemStack.getCustomName());
        this.itemStack.setCustomName("Test");
        assertEquals("Test", this.itemStack.getCustomName());
    }

    @Test
    void setCustomName() {
        assertNull(this.itemStack.getCustomName());
        this.itemStack.setCustomName("Test");
        assertEquals("Test", this.itemStack.getCustomName());
        assertTrue(this.itemStack.hasTag());
        assertTrue(this.itemStack.getTag().contains("display", NbtType.COMPOUND));
        assertTrue(this.itemStack.getTag().getCompound("display").contains("Name", NbtType.STRING));
    }

    @Test
    void removeCustomName() {
        assertNull(this.itemStack.getCustomName());
        this.itemStack.setCustomName("Test");
        assertEquals("Test", this.itemStack.getCustomName());
        this.itemStack.removeCustomName();
        assertNull(this.itemStack.getCustomName());
        assertFalse(this.itemStack.hasTag());
    }

}