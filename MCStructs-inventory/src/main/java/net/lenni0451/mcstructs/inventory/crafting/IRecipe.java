package net.lenni0451.mcstructs.inventory.crafting;

import net.lenni0451.mcstructs.inventory.types.ICraftingInventory;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.ItemRegistry;

public interface IRecipe<I, S extends AItemStack<I, S>> {

    boolean matches(final ItemRegistry<I, S> itemRegistry, final ICraftingInventory<I, S> craftingInventory);

    S getResult(final ItemRegistry<I, S> itemRegistry, final ICraftingInventory<I, S> craftingInventory);

}
