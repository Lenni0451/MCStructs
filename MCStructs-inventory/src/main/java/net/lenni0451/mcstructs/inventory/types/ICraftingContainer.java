package net.lenni0451.mcstructs.inventory.types;

import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.recipes.ICraftingInventory;

public interface ICraftingContainer<I, S extends AItemStack<I, S>> {

    void craftingUpdate(final ICraftingInventory<I, S> craftingInventory);

}
