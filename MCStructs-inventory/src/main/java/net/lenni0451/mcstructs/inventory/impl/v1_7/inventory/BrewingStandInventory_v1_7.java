package net.lenni0451.mcstructs.inventory.impl.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;

public class BrewingStandInventory_v1_7<I, S extends AItemStack<I, S>> implements IInventory_v1_7<I, S> {

    private final S[] stacks = (S[]) new AItemStack[4];

    public S[] getStacks() {
        return this.stacks;
    }

    @Override
    public S split(int slotId, int count) {
        if (slotId < 0 || slotId >= this.stacks.length) return null;
        S stack = this.stacks[slotId];
        this.stacks[slotId] = null;
        return stack;
    }

    @Override
    public int getSize() {
        return this.stacks.length;
    }

    @Override
    public S getStack(int slotId) {
        if (slotId < 0 || slotId >= this.stacks.length) return null;
        return this.stacks[slotId];
    }

    @Override
    public void setStack(int slotId, S stack) {
        if (slotId < 0 || slotId >= this.stacks.length) return;
        this.stacks[slotId] = stack;
    }

}
