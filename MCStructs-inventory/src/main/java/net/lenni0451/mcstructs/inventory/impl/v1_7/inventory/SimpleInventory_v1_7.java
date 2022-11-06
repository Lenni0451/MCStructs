package net.lenni0451.mcstructs.inventory.impl.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class SimpleInventory_v1_7<I> implements IInventory_v1_7<I> {

    private final LegacyItemStack<I>[] stacks;

    public SimpleInventory_v1_7(final int size) {
        this.stacks = new LegacyItemStack[size];
    }

    protected int getMaxCount() {
        return 64;
    }

    @Override
    public LegacyItemStack<I> split(int slot, int count) {
        if (this.stacks[slot] == null) return null;

        LegacyItemStack<I> stack = this.stacks[slot];
        if (this.stacks[slot].getCount() <= count) {
            this.stacks[slot] = null;
        } else {
            stack = stack.copy();
            if (this.stacks[slot].getCount() == 0) this.stacks[slot] = null;
        }
        return stack;
    }

    @Override
    public int getSize() {
        return this.stacks.length;
    }

    @Override
    public LegacyItemStack<I> getStack(int slot) {
        if (slot < 0 || slot >= this.stacks.length) return null;
        return this.stacks[slot];
    }

    @Override
    public void setStack(int slot, LegacyItemStack<I> stack) {
        this.stacks[slot] = stack;

        if (stack != null && stack.getCount() > this.getMaxCount()) stack.setCount(this.getMaxCount());
    }

}
