package net.lenni0451.mcstructs.inventory.impl.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class BrewingStandInventory_v1_7<I> implements IInventory_v1_7<I> {

    private final LegacyItemStack<I>[] stacks = new LegacyItemStack[4];

    public LegacyItemStack<I>[] getStacks() {
        return this.stacks;
    }

    @Override
    public LegacyItemStack<I> split(int slotId, int count) {
        if (slotId < 0 || slotId >= this.stacks.length) return null;
        LegacyItemStack<I> stack = this.stacks[slotId];
        this.stacks[slotId] = null;
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
        if (slotId < 0 || slotId >= this.stacks.length) return;
        this.stacks[slotId] = stack;
    }

}
