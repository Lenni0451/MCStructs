package net.lenni0451.mcstructs.inventory.impl.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class SimpleInventory_v1_7<I> implements IInventory_v1_7<I> {

    private final LegacyItemStack<I>[] stacks;

    public SimpleInventory_v1_7(final int size) {
        this.stacks = new LegacyItemStack[size];
    }

    public LegacyItemStack<I>[] getStacks() {
        return this.stacks;
    }

    protected int getMaxCount() {
        return 64;
    }

    @Override
    public LegacyItemStack<I> split(int slotId, int count) {
        if (this.stacks[slotId] == null) return null;

        LegacyItemStack<I> stack = this.stacks[slotId];
        if (stack.getCount() <= count) {
            this.stacks[slotId] = null;
            this.onUpdate();
        } else {
            stack = stack.split(count);
            if (this.stacks[slotId].getCount() == 0) this.stacks[slotId] = null;
            this.onUpdate();
        }
        return stack;
    }

    @Override
    public int getSize() {
        return this.stacks.length;
    }

    @Override
    public LegacyItemStack<I> getStack(int slotId) {
        if (slotId < 0 || slotId >= this.stacks.length) return null;
        return this.stacks[slotId];
    }

    @Override
    public void setStack(int slotId, LegacyItemStack<I> stack) {
        this.stacks[slotId] = stack;
        if (stack != null && stack.getCount() > this.getMaxCount()) stack.setCount(this.getMaxCount());
        this.onUpdate();
    }

}
