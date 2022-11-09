package net.lenni0451.mcstructs.inventory.impl.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class BeaconInventory_v1_7<I> implements IInventory_v1_7<I> {

    private LegacyItemStack<I> stack;

    @Override
    public LegacyItemStack<I> split(int slotId, int count) {
        if (slotId != 0 || this.stack == null) return null;

        LegacyItemStack<I> stack = this.stack;
        if (stack.getCount() <= count) {
            this.stack = null;
        } else {
            stack = stack.split(count);
            if (this.stack.getCount() == 0) this.stack = null;
        }
        return stack;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public LegacyItemStack<I> getStack(int slotId) {
        if (slotId != 0) return null;
        return this.stack;
    }

    @Override
    public void setStack(int slotId, LegacyItemStack<I> stack) {
        if (slotId == 0) this.stack = stack;
    }

}
