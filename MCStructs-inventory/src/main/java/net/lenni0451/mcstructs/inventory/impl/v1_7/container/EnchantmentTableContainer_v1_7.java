package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.SimpleInventory_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;

public class EnchantmentTableContainer_v1_7<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends AContainer_v1_7<T, I, S> {

    private final T playerInventory;
    private final SimpleInventory_v1_7<I, S> enchantmentInventory;

    public EnchantmentTableContainer_v1_7(final int windowId, final T playerInventory) {
        super(windowId);
        this.playerInventory = playerInventory;
        this.enchantmentInventory = this.createEnchantmentInventory();

        this.initSlots();
    }

    protected SimpleInventory_v1_7<I, S> createEnchantmentInventory() {
        return new SimpleInventory_v1_7<I, S>(1) {
            @Override
            public int maxStackCount() {
                return 1;
            }
        };
    }

    @Override
    protected void initSlots() {
        this.addSlot(this.enchantmentInventory, 0, Slot.acceptAll());
        for (int i = 0; i < 27; i++) this.addSlot(this.playerInventory, 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.playerInventory, i, Slot.acceptAll());
    }

    public T getPlayerInventory() {
        return this.playerInventory;
    }

    public SimpleInventory_v1_7<I, S> getEnchantmentInventory() {
        return this.enchantmentInventory;
    }

    @Override
    protected S moveStack(InventoryHolder<T, I, S> inventoryHolder, int slotId) {
        Slot<T, I, S> slot = this.getSlot(slotId);
        if (slot == null || slot.getStack() == null) return null;

        S slotStack = slot.getStack();
        S out = slotStack.copy();
        if (slotId == 0) {
            if (!this.mergeStack(slotStack, 1, 37, true)) return null;
        } else {
            if (this.getSlot(0).getStack() != null) return null;
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

    @Override
    public void close() {
        this.getSlot(0).setStack(null);
    }

}
