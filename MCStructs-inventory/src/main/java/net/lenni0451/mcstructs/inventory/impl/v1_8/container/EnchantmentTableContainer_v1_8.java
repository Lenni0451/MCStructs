package net.lenni0451.mcstructs.inventory.impl.v1_8.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.container.EnchantmentTableContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.SimpleInventory_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemType;

public class EnchantmentTableContainer_v1_8<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends EnchantmentTableContainer_v1_7<T, I, S> {

    public EnchantmentTableContainer_v1_8(int windowId, T playerInventory) {
        super(windowId, playerInventory);
    }

    @Override
    protected SimpleInventory_v1_7<I, S> createEnchantmentInventory() {
        return new SimpleInventory_v1_7<>(2);
    }

    @Override
    protected void initSlots() {
        this.addSlot(this.getEnchantmentInventory(), 0, Slot.accept(1));
        this.addSlot(this.getEnchantmentInventory(), 1, (slot, stack) -> {
            if (stack.getMeta().getTypes().contains(ItemType.DYE) && stack.getDamage() == 4) return stack.getMeta().getMaxCount();
            else return 0;
        });
        for (int i = 0; i < 27; i++) this.addSlot(this.getPlayerInventory(), 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.getPlayerInventory(), i, Slot.acceptAll());
    }

    @Override
    protected S moveStack(InventoryHolder<T, I, S> inventoryHolder, int slotId) {
        Slot<T, I, S> slot = this.getSlot(slotId);
        if (slot == null || slot.getStack() == null) return null;

        S slotStack = slot.getStack();
        S out = slotStack.copy();
        if (slotId == 0 || slotId == 1) {
            if (!this.mergeStack(slotStack, 2, 38, true)) return null;
        } else if (slotStack.getMeta().getTypes().contains(ItemType.DYE) && slotStack.getDamage() == 4) {
            if (!this.mergeStack(slotStack, 1, 2, true)) return null;
        } else {
            if (this.getSlot(0).getStack() != null) return null;
            if (!this.getSlot(0).accepts(slotStack)) return null;

            if (slotStack.hasTag() && slotStack.getCount() == 1) {
                this.getSlot(0).setStack(slotStack.copy());
                slotStack.setCount(0);
            } else if (slotStack.getCount() >= 1) {
                this.getSlot(0).setStack(slotStack.split(1));
            }
        }
        if (slotStack.getCount() == 0) slot.setStack(null);
        else slot.onUpdate();
        if (slotStack.getCount() == out.getCount()) return null;
        slot.onTake(inventoryHolder, slotStack);
        return out;
    }

}
