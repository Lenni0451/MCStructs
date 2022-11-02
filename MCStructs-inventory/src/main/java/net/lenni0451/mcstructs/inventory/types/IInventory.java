package net.lenni0451.mcstructs.inventory.types;

import net.lenni0451.mcstructs.items.AItemStack;

public interface IInventory<I, S extends AItemStack<I, S>> {

    int getSize();

    S getStack(final int slot);

    void setStack(final int slot, final S stack);

}
