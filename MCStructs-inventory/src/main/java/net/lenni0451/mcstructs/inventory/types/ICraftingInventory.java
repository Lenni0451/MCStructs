package net.lenni0451.mcstructs.inventory.types;

import net.lenni0451.mcstructs.items.AItemStack;

public interface ICraftingInventory<I, S extends AItemStack<I, S>> extends IInventory<I, S> {

    S getStack(final int row, final int column);

}
