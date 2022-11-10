package net.lenni0451.mcstructs.inventory.impl.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;

public class SimpleInventory_v1_7<I, S extends AItemStack<I, S>> implements IInventory_v1_7<I, S> {

    private final S[] stacks;

    public SimpleInventory_v1_7(final int size) {
        this.stacks = (S[]) new AItemStack[size];
    }

    public S[] getStacks() {
        return this.stacks;
    }

    protected int getMaxCount() {
        return 64;
    }

    @Override
    public S split(int slotId, int count) {
        if (this.stacks[slotId] == null) return null;

        S stack = this.stacks[slotId];
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
    public S getStack(int slotId) {
        if (slotId < 0 || slotId >= this.stacks.length) return null;
        return this.stacks[slotId];
    }

    @Override
    public void setStack(int slotId, S stack) {
        this.stacks[slotId] = stack;
        if (stack != null && stack.getCount() > this.getMaxCount()) stack.setCount(this.getMaxCount());
        this.onUpdate();
    }

}
