package net.lenni0451.mcstructs.inventory.impl.v1_7.slots;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.container.AnvilContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingResultInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;

public class AnvilResultSlot_v1_7<I, S extends AItemStack<I, S>> extends Slot<PlayerInventory_v1_7<I, S>, I, S> {

    private final AnvilContainer_v1_7<I, S> anvilContainer;

    public AnvilResultSlot_v1_7(final CraftingResultInventory_v1_7<I, S> inventory, final int slotIndex, final AnvilContainer_v1_7<I, S> anvilContainer) {
        super(inventory, slotIndex, 0, Slot.acceptNone());
        this.anvilContainer = anvilContainer;
    }

    public AnvilContainer_v1_7<I, S> getAnvilContainer() {
        return this.anvilContainer;
    }

    @Override
    public boolean canTake(InventoryHolder<PlayerInventory_v1_7<I, S>, I, S> inventoryHolder) {
        if (this.getStack() == null) return false;
        if (this.anvilContainer.getRepairCost() <= 0) return false;
        return inventoryHolder.isCreativeMode() || inventoryHolder.getXpLevel() >= this.anvilContainer.getRepairCost();
    }

    @Override
    public void onTake(InventoryHolder<PlayerInventory_v1_7<I, S>, I, S> inventoryHolder, S stack) {
        if (!inventoryHolder.isCreativeMode()) inventoryHolder.setXpLevel(inventoryHolder.getXpLevel() - this.anvilContainer.getRepairCost());
        this.anvilContainer.getInputSlots().setStack(0, null);
        if (this.anvilContainer.getRepairStackCount() > 0) {
            S repairStack = this.anvilContainer.getInputSlots().getStack(1);
            if (repairStack != null && repairStack.getCount() > this.anvilContainer.getRepairStackCount()) {
                repairStack.setCount(repairStack.getCount() - this.anvilContainer.getRepairStackCount());
            } else {
                repairStack = null;
            }
            this.anvilContainer.getInputSlots().setStack(1, repairStack);
        } else {
            this.anvilContainer.getInputSlots().setStack(1, null);
        }
        this.anvilContainer.setRepairCost(0);
    }

}
