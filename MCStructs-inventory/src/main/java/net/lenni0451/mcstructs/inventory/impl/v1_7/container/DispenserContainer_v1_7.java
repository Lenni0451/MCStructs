package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.DispenserInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class DispenserContainer_v1_7<I> extends AContainer_v1_7<I> {

    private final PlayerInventory_v1_7<I> playerInventory;
    private final DispenserInventory_v1_7<I> dispenserInventory;

    public DispenserContainer_v1_7(final int windowId, final PlayerInventory_v1_7<I> playerInventory) {
        super(windowId);
        this.playerInventory = playerInventory;
        this.dispenserInventory = new DispenserInventory_v1_7<>();

        for (int i = 0; i < 9; i++) this.addSlot(this.dispenserInventory, i, Slot.acceptAll());
        for (int i = 0; i < 27; i++) this.addSlot(this.playerInventory, 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.playerInventory, i, Slot.acceptAll());
    }

    @Override
    protected LegacyItemStack<I> moveStack(InventoryHolder<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> inventoryHolder, int slotId) {
        Slot<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> slot = this.getSlot(slotId);
        if (slot == null || slot.getStack() == null) return null;

        LegacyItemStack<I> slotStack = slot.getStack();
        LegacyItemStack<I> out = slotStack.copy();
        if (slotId <= 8) {
            if (!this.mergeStack(slotStack, 9, 45, true)) return null;
        } else if (!this.mergeStack(slotStack, 0, 9, false)) {
            return null;
        }
        if (slotStack.getCount() == 0) slot.setStack(null);
        else slot.onUpdate();
        if (slotStack.getCount() == out.getCount()) return null;
        return out;
    }

}
