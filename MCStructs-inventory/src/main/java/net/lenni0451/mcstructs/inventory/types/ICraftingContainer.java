package net.lenni0451.mcstructs.inventory.types;

import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.recipes.ICraftingInventory;

/**
 * The base interface for crafting containers.
 *
 * @param <I> The type of the item (e.g. Integer)
 * @param <S> The type of the item stack (e.g. LegacyItemStack)
 */
public interface ICraftingContainer<I, S extends AItemStack<I, S>> {

    /**
     * Update the crafting inventory.
     *
     * @param craftingInventory The crafting inventory
     */
    void craftingUpdate(final ICraftingInventory<I, S> craftingInventory);

}
