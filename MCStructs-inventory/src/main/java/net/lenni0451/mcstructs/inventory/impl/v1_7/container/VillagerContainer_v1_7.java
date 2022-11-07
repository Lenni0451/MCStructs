package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.VillagerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.slots.VillagerResultSlot_v1_7;
import net.lenni0451.mcstructs.inventory.recipes.ARecipeRegistry;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class VillagerContainer_v1_7<I> extends AContainer_v1_7<I> {

    private final PlayerInventory_v1_7<I> playerInventory;
    private final ARecipeRegistry<I, LegacyItemStack<I>> recipeRegistry;
    private final VillagerInventory_v1_7<I> villagerInventory;

    public VillagerContainer_v1_7(final int windowId, final PlayerInventory_v1_7<I> playerInventory, final ARecipeRegistry<I, LegacyItemStack<I>> recipeRegistry) {
        super(windowId);
        this.playerInventory = playerInventory;
        this.recipeRegistry = recipeRegistry;
        this.villagerInventory = new VillagerInventory_v1_7<>(this.recipeRegistry);

        this.addSlot(this.villagerInventory, 0, Slot.acceptAll());
        this.addSlot(this.villagerInventory, 1, Slot.acceptAll());
        this.addSlot(id -> new VillagerResultSlot_v1_7<>(this.villagerInventory, id));
        for (int i = 0; i < 27; i++) this.addSlot(this.playerInventory, 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.playerInventory, i, Slot.acceptAll());
    }

    public PlayerInventory_v1_7<I> getPlayerInventory() {
        return this.playerInventory;
    }

    public ARecipeRegistry<I, LegacyItemStack<I>> getRecipeRegistry() {
        return this.recipeRegistry;
    }

    public VillagerInventory_v1_7<I> getVillagerInventory() {
        return this.villagerInventory;
    }

    @Override
    protected LegacyItemStack<I> moveStack(InventoryHolder<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> inventoryHolder, int slotId) {
        Slot<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> slot = this.getSlot(slotId);
        if (slot == null || slot.getStack() == null) return null;

        LegacyItemStack<I> slotStack = slot.getStack();
        LegacyItemStack<I> out = slotStack.copy();
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

}
