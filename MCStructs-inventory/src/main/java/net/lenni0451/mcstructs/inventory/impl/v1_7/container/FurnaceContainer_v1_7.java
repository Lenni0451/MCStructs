package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.FurnaceInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemTag;
import net.lenni0451.mcstructs.recipes.ARecipeRegistry;

public class FurnaceContainer_v1_7<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends AContainer_v1_7<T, I, S> {

    private final T playerInventory;
    private final FurnaceInventory_v1_7<I, S> furnaceInventory;
    private final ARecipeRegistry<I, S> recipeRegistry;

    public FurnaceContainer_v1_7(final int windowId, final T playerInventory, final ARecipeRegistry<I, S> recipeRegistry) {
        super(windowId);
        this.playerInventory = playerInventory;
        this.furnaceInventory = new FurnaceInventory_v1_7<>();
        this.recipeRegistry = recipeRegistry;

        this.initSlots();
    }

    @Override
    protected void initSlots() {
        this.addSlot(this.furnaceInventory, 0, Slot.acceptAll());
        this.addSlot(this.furnaceInventory, 1, Slot.acceptAll());
        this.addSlot(this.furnaceInventory, 2, Slot.acceptNone());
        for (int i = 0; i < 27; i++) this.addSlot(this.playerInventory, 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.playerInventory, i, Slot.acceptAll());
    }

    public T getPlayerInventory() {
        return this.playerInventory;
    }

    public FurnaceInventory_v1_7<I, S> getFurnaceInventory() {
        return this.furnaceInventory;
    }

    public ARecipeRegistry<I, S> getRecipeRegistry() {
        return this.recipeRegistry;
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
            if (this.recipeRegistry.findFurnaceRecipe(slotStack) != null) {
                if (!this.mergeStack(slotStack, 0, 1, false)) return null;
            } else if (slotStack.getMeta().getTags().contains(ItemTag.FURNACE_FUEL)) {
                if (!this.mergeStack(slotStack, 1, 2, false)) return null;
            } else if (slotId >= 3 && slotId <= 29) {
                if (!this.mergeStack(slotStack, 30, 39, false)) return null;
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
