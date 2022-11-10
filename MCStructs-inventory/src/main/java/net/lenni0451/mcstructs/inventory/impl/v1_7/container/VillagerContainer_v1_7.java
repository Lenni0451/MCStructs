package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.VillagerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.slots.VillagerResultSlot_v1_7;
import net.lenni0451.mcstructs.inventory.recipes.ARecipeRegistry;
import net.lenni0451.mcstructs.items.AItemStack;

public class VillagerContainer_v1_7<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends AContainer_v1_7<T, I, S> {

    private final T playerInventory;
    private final ARecipeRegistry<I, S> recipeRegistry;
    private final VillagerInventory_v1_7<I, S> villagerInventory;

    public VillagerContainer_v1_7(final int windowId, final T playerInventory, final ARecipeRegistry<I, S> recipeRegistry) {
        super(windowId);
        this.playerInventory = playerInventory;
        this.recipeRegistry = recipeRegistry;
        this.villagerInventory = new VillagerInventory_v1_7<>(this.recipeRegistry);

        this.initSlots();
    }

    @Override
    protected void initSlots() {
        this.addSlot(this.villagerInventory, 0, Slot.acceptAll());
        this.addSlot(this.villagerInventory, 1, Slot.acceptAll());
        this.addSlot(id -> new VillagerResultSlot_v1_7<>(this.villagerInventory, id));
        for (int i = 0; i < 27; i++) this.addSlot(this.playerInventory, 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.playerInventory, i, Slot.acceptAll());
    }

    public T getPlayerInventory() {
        return this.playerInventory;
    }

    public ARecipeRegistry<I, S> getRecipeRegistry() {
        return this.recipeRegistry;
    }

    public VillagerInventory_v1_7<I, S> getVillagerInventory() {
        return this.villagerInventory;
    }

    @Override
    protected S moveStack(InventoryHolder<T, I, S> inventoryHolder, int slotId) {
        Slot<T, I, S> slot = this.getSlot(slotId);
        if (slot == null || slot.getStack() == null) return null;

        S slotStack = slot.getStack();
        S out = slotStack.copy();
        if (slotId == 2) {
            if (!this.mergeStack(slotStack, 3, 39, true)) return null;
        } else if (slotId != 0 && slotId != 1) {
            if (slotId >= 3 && slotId <= 29) {
                if (!this.mergeStack(slotStack, 20, 28, false)) return null;
            } else if (slotId >= 30 && slotId <= 38) {
                if (!this.mergeStack(slotStack, 3, 30, false)) return null;
            }
        } else if (!this.mergeStack(slotStack, 3, 39, false)) {
            return null;
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
        this.getSlot(1).setStack(null);
    }

}
