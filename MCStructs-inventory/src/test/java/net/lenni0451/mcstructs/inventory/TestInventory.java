package net.lenni0451.mcstructs.inventory;

import net.lenni0451.mcstructs.inventory.types.IInventory;
import net.lenni0451.mcstructs.items.stacks.ItemStack;

public class TestInventory implements IInventory<Integer, ItemStack<Integer>> {

    private ItemStack<Integer> item;

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public ItemStack<Integer> getStack(int slotId) {
        if (slotId == 0) return this.item;
        return null;
    }

    @Override
    public void setStack(int slotId, ItemStack<Integer> stack) {
        if (slotId == 0) this.item = stack;
    }

}
