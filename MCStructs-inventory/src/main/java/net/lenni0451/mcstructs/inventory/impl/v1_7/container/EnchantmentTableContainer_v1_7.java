package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.SimpleInventory_v1_7;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class EnchantmentTableContainer_v1_7<I> extends AContainer_v1_7<I> {

    private final PlayerInventory_v1_7<I> playerInventory;
    private final SimpleInventory_v1_7<I> enchantmentInventory;

    public EnchantmentTableContainer_v1_7(final int windowId, final PlayerInventory_v1_7<I> playerInventory) {
        super(windowId);
        this.playerInventory = playerInventory;
        this.enchantmentInventory = new SimpleInventory_v1_7<I>(1) {
            @Override
            protected int getMaxCount() {
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

    public PlayerInventory_v1_7<I> getPlayerInventory() {
        return this.playerInventory;
    }

    public SimpleInventory_v1_7<I> getEnchantmentInventory() {
        return this.enchantmentInventory;
    }

    @Override
    protected LegacyItemStack<I> moveStack(InventoryHolder<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> inventoryHolder, int slotId) {
        Slot<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> slot = this.getSlot(slotId);
        if (slot == null || slot.getStack() == null) return null;

        LegacyItemStack<I> slotStack = slot.getStack();
        LegacyItemStack<I> out = slotStack.copy();
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
