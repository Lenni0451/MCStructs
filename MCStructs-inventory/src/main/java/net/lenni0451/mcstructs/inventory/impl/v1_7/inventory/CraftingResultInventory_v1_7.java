package net.lenni0451.mcstructs.inventory.impl.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class CraftingResultInventory_v1_7<I> implements IInventory_v1_7<I> {

    private LegacyItemStack<I> result;

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public LegacyItemStack<I> getStack(int slotId) {
        return this.result;
    }

    @Override
    public void setStack(int slotId, LegacyItemStack<I> stack) {
        this.result = stack;
    }

    @Override
    public LegacyItemStack<I> split(int slotId, int count) {
        if (this.result == null) return null;

        LegacyItemStack<I> stack = this.result;
        this.result = null;
        return stack;
    }

}
