package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.SimpleInventory_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemType;

public class HorseContainer_v1_7<I, S extends AItemStack<I, S>> extends AContainer_v1_7<I, S> {

    private final PlayerInventory_v1_7<I, S> playerInventory;
    private final SimpleInventory_v1_7<I, S> horseInventory;
    private final boolean hasChest;
    private final boolean isHorse;

    public HorseContainer_v1_7(final int windowId, final PlayerInventory_v1_7<I, S> playerInventory, final int size, final boolean hasChest, final boolean isHorse) {
        super(windowId);
        this.playerInventory = playerInventory;
        this.horseInventory = new SimpleInventory_v1_7<>(size);
        this.hasChest = hasChest;
        this.isHorse = isHorse;

        this.initSlots();
    }

    @Override
    protected void initSlots() {
        this.addSlot(this.horseInventory, 0, (slot, stack) -> {
            if (stack.getMeta().types().contains(ItemType.SADDLE) && slot.getStack() == null) return stack.getMeta().maxCount();
            else return 0;
        });
        this.addSlot(this.horseInventory, 1, (slot, stack) -> {
            if (stack.getMeta().types().contains(ItemType.HORSE_ARMOR) && this.isHorse) return stack.getMeta().maxCount();
            else return 0;
        });
        if (this.hasChest) {
            for (int i = 0; i < 15; i++) this.addSlot(this.horseInventory, 2 + i, Slot.acceptAll());
        }
        for (int i = 0; i < 27; i++) this.addSlot(this.playerInventory, 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.playerInventory, i, Slot.acceptAll());
    }

    public PlayerInventory_v1_7<I, S> getPlayerInventory() {
        return this.playerInventory;
    }

    public SimpleInventory_v1_7<I, S> getHorseInventory() {
        return this.horseInventory;
    }

    public boolean hasChest() {
        return this.hasChest;
    }

    public boolean isHorse() {
        return this.isHorse;
    }

    @Override
    protected S moveStack(InventoryHolder<PlayerInventory_v1_7<I, S>, I, S> inventoryHolder, int slotId) {
        Slot<PlayerInventory_v1_7<I, S>, I, S> slot = this.getSlot(slotId);
        if (slot == null || slot.getStack() == null) return null;

        S slotStack = slot.getStack();
        S out = slotStack.copy();
        if (slotId < this.horseInventory.getSize()) {
            if (!this.mergeStack(slotStack, this.horseInventory.getSize(), this.getSlotCount(), true)) return null;
        } else if (this.getSlot(1).accepts(slotStack) && this.getSlot(1).getStack() == null) {
            if (!this.mergeStack(slotStack, 1, 2, false)) return null;
        } else if (this.getSlot(0).accepts(slotStack)) {
            if (!this.mergeStack(slotStack, 0, 1, false)) return null;
        } else if (this.horseInventory.getSize() <= 2) {
            return null;
        } else if (!this.mergeStack(slotStack, 2, this.horseInventory.getSize(), false)) {
            return null;
        }
        if (slotStack.getCount() == 0) slot.setStack(null);
        else slot.onUpdate();
        return out;
    }

}
