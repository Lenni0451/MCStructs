package net.lenni0451.mcstructs.items.stacks;

import net.lenni0451.mcstructs.nbt.NbtType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemStackTest {

    private ItemStack<Integer> itemStack;

    @BeforeEach
    void prepare() {
        this.itemStack = new ItemStack<>(null, 1);
    }

    @Test
    void getDamage() {
        assertEquals(0, this.itemStack.getDamage());
    }

    @Test
    void setDamage() {
        assertEquals(0, this.itemStack.getDamage());
        this.itemStack.setDamage(1);
        assertEquals(1, this.itemStack.getDamage());
        assertTrue(this.itemStack.hasTag());
        assertTrue(this.itemStack.getTag().contains("Damage", NbtType.INT));
    }

}