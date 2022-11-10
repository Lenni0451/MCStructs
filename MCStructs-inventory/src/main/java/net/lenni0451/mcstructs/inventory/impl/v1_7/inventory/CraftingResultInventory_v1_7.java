package net.lenni0451.mcstructs.inventory.impl.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;

public class CraftingResultInventory_v1_7<I, S extends AItemStack<I, S>> implements IInventory_v1_7<I, S> {

    private S result;

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public S getStack(int slotId) {
        return this.result;
    }

    @Override
    public void setStack(int slotId, S stack) {
        this.result = stack;
    }

    @Override
    public S split(int slotId, int count) {
        if (this.result == null) return null;

        S stack = this.result;
        this.result = null;
        return stack;
    }

}
