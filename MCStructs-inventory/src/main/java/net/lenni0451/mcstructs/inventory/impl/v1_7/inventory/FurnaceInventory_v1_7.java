package net.lenni0451.mcstructs.inventory.impl.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;

public class FurnaceInventory_v1_7<I, S extends AItemStack<I, S>> implements IInventory_v1_7<I, S> {

    private final S[] stacks = (S[]) new AItemStack[3];

    public S[] getStacks() {
        return this.stacks;
    }

    @Override
    public S split(int slotId, int count) {
        if (this.stacks[slotId] == null) return null;

        S stack = this.stacks[slotId];
        if (stack.getCount() <= count) {
            this.stacks[slotId] = null;
        } else {
            stack = stack.split(count);
            if (this.stacks[slotId].getCount() == 0) this.stacks[slotId] = null;
        }
        return stack;
    }

    @Override
    public int getSize() {
        return this.stacks.length;
    }

    @Override
    public S getStack(int slotId) {
        return this.stacks[slotId];
    }

    @Override
    public void setStack(int slotId, S stack) {
        this.stacks[slotId] = stack;
        if (stack != null && stack.getCount() > 64) stack.setCount(64);
    }

}
