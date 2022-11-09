package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.FurnaceInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.recipes.ARecipeRegistry;
import net.lenni0451.mcstructs.items.info.ItemTag;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class FurnaceContainer_v1_7<I> extends AContainer_v1_7<I> {

    private final PlayerInventory_v1_7<I> playerInventory;
    private final FurnaceInventory_v1_7<I> furnaceInventory;
    private final ARecipeRegistry<I, LegacyItemStack<I>> recipeRegistry;

    public FurnaceContainer_v1_7(final int windowId, final PlayerInventory_v1_7<I> playerInventory, final ARecipeRegistry<I, LegacyItemStack<I>> recipeRegistry) {
        super(windowId);
        this.playerInventory = playerInventory;
        this.furnaceInventory = new FurnaceInventory_v1_7<>();
        this.recipeRegistry = recipeRegistry;
    }

    @Override
    protected void initSlots() {
        this.addSlot(this.furnaceInventory, 0, Slot.acceptAll());
        this.addSlot(this.furnaceInventory, 1, Slot.acceptAll());
        this.addSlot(this.furnaceInventory, 2, Slot.acceptNone());
        for (int i = 0; i < 27; i++) this.addSlot(this.playerInventory, 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.playerInventory, i, Slot.acceptAll());
    }

    public PlayerInventory_v1_7<I> getPlayerInventory() {
        return this.playerInventory;
    }

    public FurnaceInventory_v1_7<I> getFurnaceInventory() {
        return this.furnaceInventory;
    }

    public ARecipeRegistry<I, LegacyItemStack<I>> getRecipeRegistry() {
        return this.recipeRegistry;
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
            if (this.recipeRegistry.findFurnaceRecipe(slotStack) != null) {
                if (!this.mergeStack(slotStack, 0, 1, false)) return null;
            } else if (slotStack.getMeta().tags().contains(ItemTag.FURNACE_FUEL)) {
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
