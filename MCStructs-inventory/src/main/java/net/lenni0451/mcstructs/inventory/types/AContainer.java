package net.lenni0451.mcstructs.inventory.types;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.enums.InventoryAction;
import net.lenni0451.mcstructs.items.AItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.IntFunction;

/**
 * The base class for containers.
 *
 * @param <T> The type of the inventory (e.g. IInventory)
 * @param <I> The type of the item (e.g. Integer)
 * @param <S> The type of the item stack (e.g. LegacyItemStack)
 */
public abstract class AContainer<T extends IInventory<I, S>, I, S extends AItemStack<I, S>> {

    private final int windowId;
    private final List<Slot<T, I, S>> slots;

    public AContainer(final int windowId) {
        this.windowId = windowId;
        this.slots = new ArrayList<>();
    }

    /**
     * Initialize all slots of this container.
     */
    protected abstract void initSlots();

    /**
     * @return The window id of this container
     */
    public int getWindowId() {
        return this.windowId;
    }

    /**
     * Add a slot to this container.
     *
     * @param inventory      The inventory
     * @param inventoryIndex The index of the slot in the inventory
     * @param acceptor       The slot count function
     * @return The added slot
     */
    protected Slot<T, I, S> addSlot(final IInventory<I, S> inventory, final int inventoryIndex, final BiFunction<Slot<T, I, S>, S, Integer> acceptor) {
        Slot<T, I, S> slot = new Slot<>(inventory, this.slots.size(), inventoryIndex, acceptor);
        this.slots.add(slot);
        return slot;
    }

    /**
     * Add a custom slot to this container.
     *
     * @param slotSupplier The slot supplier
     * @param <SLOT>       The type of the slot
     * @return The added slot
     */
    protected <SLOT extends Slot<T, I, S>> SLOT addSlot(final IntFunction<SLOT> slotSupplier) {
        SLOT slot = slotSupplier.apply(this.slots.size());
        this.slots.add(slot);
        return slot;
    }

    /**
     * Get a slot by its index.
     *
     * @param index The index of the slot
     * @return The slot
     */
    public Slot<T, I, S> getSlot(final int index) {
        return this.slots.get(index);
    }

    /**
     * @return The slot count of this container
     */
    public int getSlotCount() {
        return this.slots.size();
    }


    /**
     * Handle a click in this container.
     *
     * @param inventoryHolder The inventory holder
     * @param slotId          The slot id
     * @param button          The button
     * @param action          The action
     * @return The old item stack in this slot
     */
    public abstract S click(final InventoryHolder<T, I, S> inventoryHolder, final int slotId, final int button, final InventoryAction action);

    /**
     * Handle the closing of this container.
     */
    public void close() {
    }

}
