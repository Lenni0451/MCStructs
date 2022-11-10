package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.BeaconInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemType;

public class BeaconContainer_v1_7<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends AContainer_v1_7<T, I, S> {

    private final T playerInventory;
    private final BeaconInventory_v1_7<I, S> beaconInventory;

    public BeaconContainer_v1_7(final int windowId, final T playerInventory) {
        super(windowId);
        this.playerInventory = playerInventory;
        this.beaconInventory = new BeaconInventory_v1_7<>();

        this.initSlots();
    }

    @Override
    protected void initSlots() {
        this.addSlot(this.beaconInventory, 0, Slot.acceptTypes(1, ItemType.IRON_INGOT, ItemType.GOLD_INGOT, ItemType.EMERALD, ItemType.DIAMOND));
        for (int i = 0; i < 27; i++) this.addSlot(this.playerInventory, 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.playerInventory, i, Slot.acceptAll());
    }

    public T getPlayerInventory() {
        return this.playerInventory;
    }

    public BeaconInventory_v1_7<I, S> getBeaconInventory() {
        return this.beaconInventory;
    }

    @Override
    protected S moveStack(InventoryHolder<T, I, S> inventoryHolder, int slotId) {
        Slot<T, I, S> slot = this.getSlot(slotId);
        if (slot == null || slot.getStack() == null) return null;

        S slotStack = slot.getStack();
        S out = slotStack.copy();
        if (slotId == 0) {
            if (!this.mergeStack(slotStack, 1, 37, true)) return null;
        } else if (this.getSlot(0).getStack() == null && this.getSlot(0).accepts(slotStack) && slotStack.getCount() == 1) {
            if (!this.mergeStack(slotStack, 0, 1, false)) return null;
        } else if (slotId >= 1 && slotId <= 27) {
            if (!this.mergeStack(slotStack, 28, 37, false)) return null;
        } else if (slotId >= 28 && slotId <= 36) {
            if (!this.mergeStack(slotStack, 1, 28, false)) return null;
        } else if (!this.mergeStack(slotStack, 1, 37, false)) {
            return null;
        }
        if (slotStack.getCount() == 0) slot.setStack(null);
        else slot.onUpdate();
        if (slotStack.getCount() == out.getCount()) return null;
        return out;
    }

}
