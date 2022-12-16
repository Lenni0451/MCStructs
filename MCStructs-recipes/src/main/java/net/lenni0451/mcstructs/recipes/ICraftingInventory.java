package net.lenni0451.mcstructs.recipes;

import net.lenni0451.mcstructs.items.AItemStack;

public interface ICraftingInventory<I, S extends AItemStack<I, S>> {

    int getSize();

    S getStack(final int slotId);

    S getStack(final int row, final int column);

}
