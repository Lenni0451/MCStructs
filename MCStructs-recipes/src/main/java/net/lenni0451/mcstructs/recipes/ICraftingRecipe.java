package net.lenni0451.mcstructs.recipes;

import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.ItemRegistry;

/**
 * The base interface for crafting recipes.
 *
 * @param <I> The type of the item (e.g. Integer)
 * @param <S> The type of the item stack (e.g. LegacyItemStack)
 */
public interface ICraftingRecipe<I, S extends AItemStack<I, S>> {

    /**
     * Check if the crafting recipe matches the given input.
     *
     * @param itemRegistry      The item registry
     * @param craftingInventory The crafting inventory
     * @return If the recipe matches
     */
    boolean matches(final ItemRegistry<I, S> itemRegistry, final ICraftingInventory<I, S> craftingInventory);

    /**
     * Get the result of the crafting recipe.
     *
     * @param itemRegistry      The item registry
     * @param craftingInventory The crafting inventory
     * @return The result
     */
    S getResult(final ItemRegistry<I, S> itemRegistry, final ICraftingInventory<I, S> craftingInventory);

}
