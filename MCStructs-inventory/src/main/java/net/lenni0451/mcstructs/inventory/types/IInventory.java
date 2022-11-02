package net.lenni0451.mcstructs.inventory.types;

import net.lenni0451.mcstructs.items.AItemStack;

public interface IInventory<S extends AItemStack<?, ?>> {

    int getSize();

    S getStack(final int slot);

    void setStack(final int slot, final S stack);

}
