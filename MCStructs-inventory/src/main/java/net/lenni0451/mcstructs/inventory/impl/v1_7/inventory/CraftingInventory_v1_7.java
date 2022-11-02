package net.lenni0451.mcstructs.inventory.impl.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.inventory.types.ICraftingInventory;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class CraftingInventory_v1_7<I> implements IInventory_v1_7<I>, ICraftingInventory<LegacyItemStack<I>> {

    private final int width;
    private final int height;
    private final LegacyItemStack<I>[] stacks;

    public CraftingInventory_v1_7(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.stacks = new LegacyItemStack[width * height];
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    @Override
    public int getSize() {
        return this.width * this.height;
    }

    @Override
    public LegacyItemStack<I> getStack(int slot) {
        if (slot >= this.stacks.length) return null;
        else return this.stacks[slot];
    }

    @Override
    public void setStack(int slot, LegacyItemStack<I> stack) {
        this.stacks[slot] = stack;
    }

    @Override
    public LegacyItemStack<I> split(int slot, int count) {
        if (this.stacks[slot] == null) return null;
        LegacyItemStack<I> stack;
        if (this.stacks[slot].getCount() <= count) {
            stack = this.stacks[slot];
            this.stacks[slot] = null;
        } else {
            stack = this.stacks[slot].split(count);
            if (this.stacks[slot].getCount() == 0) this.stacks[slot] = null;
        }
        return stack;
    }

    @Override
    public LegacyItemStack<I> getStack(int row, int column) {
        if (row >= 0 && row < this.width) {
            int index = row + column * this.width;
            return this.getStack(index);
        }
        return null;
    }

}
