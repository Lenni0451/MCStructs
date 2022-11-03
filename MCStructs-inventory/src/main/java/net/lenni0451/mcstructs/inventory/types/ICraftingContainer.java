package net.lenni0451.mcstructs.inventory.types;

import net.lenni0451.mcstructs.items.AItemStack;

public interface ICraftingContainer<I, S extends AItemStack<I, S>> {

    void craftingUpdate(final ICraftingInventory<I, S> craftingInventory);

}
