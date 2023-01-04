package net.lenni0451.mcstructs.recipes;

import net.lenni0451.mcstructs.items.AItemStack;

/**
 * The base interface for crafting inventories.
 *
 * @param <I> The type of the item (e.g. Integer)
 * @param <S> The type of the item stack (e.g. LegacyItemStack)
 */
public interface ICraftingInventory<I, S extends AItemStack<I, S>> {

    /**
     * @return The size of the crafting inventory
     */
    int getSize();

    /**
     * Get the item stack in the given slot.
     *
     * @param slotId The slot id
     * @return The item stack
     */
    S getStack(final int slotId);

    /**
     * Get the itemstack in the given row and column.
     *
     * @param row    The row
     * @param column The column
     * @return The item stack
     */
    S getStack(final int row, final int column);

}
