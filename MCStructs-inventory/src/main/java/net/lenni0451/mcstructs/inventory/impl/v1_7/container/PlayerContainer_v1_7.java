package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingResultInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

/**
 * Slots:<br>
 * 0-4: crafting slots (output, slot 1-1, slot 1-2, slot 2-1, slot 2-2)<br>
 * 5-8: armor slots (boots, leggings, chestplate, helmet)<br>
 * 9-35: upper inventory slots<br>
 * 36-44: hotbar slots
 */
public class PlayerContainer_v1_7<I> extends AContainer_v1_7<I> {

    private final PlayerInventory_v1_7<I> playerInventory;
    private final CraftingInventory_v1_7<I> craftingInventory;
    private final CraftingResultInventory_v1_7<I> craftingResultInventory;

    public PlayerContainer_v1_7(final PlayerInventory_v1_7<I> playerInventory) {
        super(0);
        this.playerInventory = playerInventory;
        this.craftingInventory = new CraftingInventory_v1_7<>(2, 2);
        this.craftingResultInventory = new CraftingResultInventory_v1_7<>();

        this.addSlot(this.craftingResultInventory, 0, Slot.acceptNone());
        for (int i = 0; i < this.craftingInventory.getSize(); i++) this.addSlot(this.craftingInventory, i, Slot.acceptAll());
        this.addSlot(this.playerInventory, this.playerInventory.getSize() - 1, Slot.acceptType(ItemType.HELMET, 1));
        this.addSlot(this.playerInventory, this.playerInventory.getSize() - 2, Slot.acceptType(ItemType.CHESTPLATE, 1));
        this.addSlot(this.playerInventory, this.playerInventory.getSize() - 3, Slot.acceptType(ItemType.LEGGINGS, 1));
        this.addSlot(this.playerInventory, this.playerInventory.getSize() - 4, Slot.acceptType(ItemType.BOOTS, 1));
        for (int i = 0; i < 27; i++) this.addSlot(this.playerInventory, 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.playerInventory, i, Slot.acceptAll());
    }

    public PlayerInventory_v1_7<I> getPlayerInventory() {
        return this.playerInventory;
    }

    public CraftingInventory_v1_7<I> getCraftingInventory() {
        return this.craftingInventory;
    }

    public CraftingResultInventory_v1_7<I> getCraftingResultInventory() {
        return this.craftingResultInventory;
    }

    public int getArmorSlotOffset(final ItemType type) {
        switch (type) {
            case HELMET:
                return 0;
            case CHESTPLATE:
                return 1;
            case LEGGINGS:
                return 2;
            case BOOTS:
                return 3;
        }
        throw new IllegalArgumentException("The given item type is not an armor type");
    }

    @Override
    protected boolean canTakeAll(Slot<I, LegacyItemStack<I>> slot, LegacyItemStack<I> stack) {
        return !this.craftingResultInventory.equals(slot.getInventory());
    }

    @Override
    protected LegacyItemStack<I> moveStack(InventoryHolder<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> inventoryHolder, int slotId) {
        LegacyItemStack<I> out = null;
        Slot<I, LegacyItemStack<I>> slot = this.getSlot(slotId);
        if (slot != null && slot.getStack() != null) {
            LegacyItemStack<I> slotStack = slot.getStack();
            out = slotStack.copy();
            if (slotId == 0) {
                if (!this.mergeStack(slotStack, 9, 45, true)) return null;
                //slot.onChange(slotStack, out)
            } else if (slotId >= 1 && slotId <= 8) {
                if (!this.mergeStack(slotStack, 9, 45, false)) return null;
            } else if (ItemType.isArmor(out.getMeta().type()) && this.getSlot(5 + this.getArmorSlotOffset(out.getMeta().type())).getStack() == null) {
                int armorSlot = 5 + this.getArmorSlotOffset(out.getMeta().type());
                if (!this.mergeStack(slotStack, armorSlot, armorSlot + 1, false)) return null;
            } else if (slotId >= 9 && slotId <= 35) {
                if (!this.mergeStack(slotStack, 36, 45, false)) return null;
            } else if (slotId >= 36 && slotId <= 44) {
                if (!this.mergeStack(slotStack, 9, 36, false)) return null;
            } else if (!this.mergeStack(slotStack, 9, 45, false)) {
                return null;
            }
            if (slotStack.getCount() == 0) slot.setStack(null);
            //else slot.onChange(slotStack, out);
            if (slotStack.getCount() == out.getCount()) return null;
            //slot.onTake(inventoryHolder, slotStack);
        }
        return out;
    }

}
