package net.lenni0451.mcstructs.inventory.types;

import net.lenni0451.mcstructs.items.AItemStack;

public interface IInventory<I, S extends AItemStack<I, S>> {

    int getSize();

    S getStack(final int slotId);

    void setStack(final int slotId, final S stack);

    default int maxStackCount() {
        return 64;
    }

    default void onUpdate() {
    }

}
