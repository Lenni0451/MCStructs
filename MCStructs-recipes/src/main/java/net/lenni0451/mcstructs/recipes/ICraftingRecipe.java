package net.lenni0451.mcstructs.recipes;

import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.ItemRegistry;

public interface ICraftingRecipe<I, S extends AItemStack<I, S>> {

    boolean matches(final ItemRegistry<I, S> itemRegistry, final ICraftingInventory<I, S> craftingInventory);

    S getResult(final ItemRegistry<I, S> itemRegistry, final ICraftingInventory<I, S> craftingInventory);

}
