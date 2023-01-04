package net.lenni0451.mcstructs.inventory.types;

import net.lenni0451.mcstructs.items.AItemStack;

/**
 * The base interface for inventories.
 *
 * @param <I> The type of the item (e.g. Integer)
 * @param <S> The type of the item stack (e.g. LegacyItemStack)
 */
public interface IInventory<I, S extends AItemStack<I, S>> {

    /**
     * @return The size of the inventory
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
     * Set the item stack in the given slot.
     *
     * @param slotId The slot id
     * @param stack  The item stack
     */
    void setStack(final int slotId, final S stack);

    /**
     * @return The max stack count of the inventory
     */
    default int maxStackCount() {
        return 64;
    }

    /**
     * Handle inventory updates.
     */
    default void onUpdate() {
    }

}
