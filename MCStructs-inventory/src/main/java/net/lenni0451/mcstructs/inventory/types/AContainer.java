package net.lenni0451.mcstructs.inventory.types;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.enums.InventoryAction;
import net.lenni0451.mcstructs.items.AItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class AContainer<I extends IInventory<S>, S extends AItemStack<?, ?>> {

    private final int windowId;
    private final List<Slot<S>> slots;

    public AContainer(final int windowId) {
        this.windowId = windowId;
        this.slots = new ArrayList<>();
    }

    public int getWindowId() {
        return this.windowId;
    }

    protected Slot<S> addSlot(final IInventory<S> inventory, final int inventoryIndex, final Function<S, Integer> acceptor) {
        Slot<S> slot = new Slot<>(inventory, this.slots.size(), inventoryIndex, acceptor);
        this.slots.add(slot);
        return slot;
    }

    public Slot<S> getSlot(final int index) {
        return this.slots.get(index);
    }

    public int getSlotCount() {
        return this.slots.size();
    }

    public abstract S click(final InventoryHolder<I, S> inventoryHolder, final int slot, final int button, final InventoryAction action);

}
