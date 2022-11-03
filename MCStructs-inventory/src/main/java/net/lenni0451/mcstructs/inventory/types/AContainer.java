package net.lenni0451.mcstructs.inventory.types;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.enums.InventoryAction;
import net.lenni0451.mcstructs.items.AItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

public abstract class AContainer<T extends IInventory<I, S>, I, S extends AItemStack<I, S>> {

    private final int windowId;
    private final List<Slot<T, I, S>> slots;

    public AContainer(final int windowId) {
        this.windowId = windowId;
        this.slots = new ArrayList<>();
    }

    public int getWindowId() {
        return this.windowId;
    }

    protected Slot<T, I, S> addSlot(final IInventory<I, S> inventory, final int inventoryIndex, final Function<S, Integer> acceptor) {
        Slot<T, I, S> slot = new Slot<>(inventory, this.slots.size(), inventoryIndex, acceptor);
        this.slots.add(slot);
        return slot;
    }

    protected <SLOT extends Slot<T, I, S>> SLOT addSlot(final IntFunction<SLOT> slotSupplier) {
        SLOT slot = slotSupplier.apply(this.slots.size());
        this.slots.add(slot);
        return slot;
    }

    public Slot<T, I, S> getSlot(final int index) {
        return this.slots.get(index);
    }

    public int getSlotCount() {
        return this.slots.size();
    }


    public abstract S click(final InventoryHolder<T, I, S> inventoryHolder, final int slot, final int button, final InventoryAction action);

    public void close() {
    }

}
