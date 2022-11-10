package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.enums.InventoryAction;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingResultInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.slots.CraftingResultSlot_v1_7;
import net.lenni0451.mcstructs.inventory.recipes.ARecipeRegistry;
import net.lenni0451.mcstructs.inventory.types.ICraftingContainer;
import net.lenni0451.mcstructs.inventory.types.ICraftingInventory;
import net.lenni0451.mcstructs.items.AItemStack;

public class CraftingTableContainer_v1_7<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends AContainer_v1_7<T, I, S> implements ICraftingContainer<I, S> {

    private final T playerInventory;
    private final CraftingInventory_v1_7<I, S> craftingInventory;
    private final CraftingResultInventory_v1_7<I, S> craftingResultInventory;
    private final ARecipeRegistry<I, S> recipeRegistry;

    public CraftingTableContainer_v1_7(final int windowId, final T playerInventory, final ARecipeRegistry<I, S> recipeRegistry) {
        super(windowId);
        this.playerInventory = playerInventory;
        this.craftingInventory = new CraftingInventory_v1_7<>(this, 3, 3);
        this.craftingResultInventory = new CraftingResultInventory_v1_7<>();
        this.recipeRegistry = recipeRegistry;

        this.initSlots();
        this.craftingUpdate(this.craftingInventory);
    }

    @Override
    protected void initSlots() {
        this.addSlot(id -> new CraftingResultSlot_v1_7<>(this.craftingResultInventory, id, this.craftingInventory));
        for (int i = 0; i < this.craftingInventory.getSize(); i++) this.addSlot(this.craftingInventory, i, Slot.acceptAll());
        for (int i = 0; i < 27; i++) this.addSlot(this.playerInventory, 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.playerInventory, i, Slot.acceptAll());
    }

    @Override
    public S click(InventoryHolder<T, I, S> inventoryHolder, int slotId, int button, InventoryAction action) {
        return null;
    }

    public T getPlayerInventory() {
        return this.playerInventory;
    }

    public CraftingInventory_v1_7<I, S> getCraftingInventory() {
        return this.craftingInventory;
    }

    public CraftingResultInventory_v1_7<I, S> getCraftingResultInventory() {
        return this.craftingResultInventory;
    }

    public ARecipeRegistry<I, S> getRecipeRegistry() {
        return this.recipeRegistry;
    }

    @Override
    public void craftingUpdate(ICraftingInventory<I, S> craftingInventory) {
        this.craftingResultInventory.setStack(0, this.recipeRegistry.findCraftingRecipe(craftingInventory));
    }

    @Override
    public void close() {
        for (int i = 0; i < this.craftingInventory.getSize(); i++) this.craftingInventory.setStack(i, null);
        this.craftingResultInventory.setStack(0, null);
    }

    @Override
    protected S moveStack(InventoryHolder<T, I, S> inventoryHolder, int slotId) {
        Slot<T, I, S> slot = this.getSlot(slotId);
        if (slot == null || slot.getStack() == null) return null;

        S slotStack = slot.getStack();
        S out = slotStack.copy();
        if (slotId == 0) {
            if (!this.mergeStack(slotStack, 10, 46, true)) return null;
        } else if (slotId >= 10 && slotId <= 36) {
            if (!this.mergeStack(slotStack, 37, 46, false)) return null;
        } else if (slotId >= 37 && slotId <= 45) {
            if (!this.mergeStack(slotStack, 10, 37, false)) return null;
        } else if (!this.mergeStack(slotStack, 10, 46, false)) {
            return null;
        }
        if (slotStack.getCount() == 0) slot.setStack(null);
        else slot.onUpdate();
        if (slotStack.getCount() == out.getCount()) return null;
        slot.onTake(inventoryHolder, slotStack);
        return out;
    }

}
