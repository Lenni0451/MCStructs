package net.lenni0451.mcstructs.inventory.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class CraftingInventory_v1_7 implements IInventory_v1_7 {

    private final LegacyItemStack<?>[] stacks;

    public CraftingInventory_v1_7(final int width, final int height) {
        this.stacks = new LegacyItemStack<?>[width * height];
    }

    @Override
    public int getSize() {
        return this.stacks.length;
    }

    @Override
    public LegacyItemStack<?> getStack(int slot) {
        if (slot >= this.stacks.length) return null;
        else return this.stacks[slot];
    }

    @Override
    public void setStack(int slot, LegacyItemStack<?> stack) {
        this.stacks[slot] = stack;
    }

    @Override
    public LegacyItemStack<?> split(int slot, int count) {
        if (this.stacks[slot] == null) return null;
        LegacyItemStack<?> stack;
        if (this.stacks[slot].getCount() <= count) {
            stack = this.stacks[slot];
            this.stacks[slot] = null;
        } else {
            stack = this.stacks[slot].split(count);
            if (this.stacks[slot].getCount() == 0) this.stacks[slot] = null;
        }
        return stack;
    }

}
