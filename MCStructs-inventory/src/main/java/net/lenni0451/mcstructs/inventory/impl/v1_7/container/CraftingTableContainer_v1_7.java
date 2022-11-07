package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.crafting.ARecipeRegistry;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingResultInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.slots.CraftingResultSlot_v1_7;
import net.lenni0451.mcstructs.inventory.types.ICraftingContainer;
import net.lenni0451.mcstructs.inventory.types.ICraftingInventory;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class CraftingTableContainer_v1_7<I> extends AContainer_v1_7<I> implements ICraftingContainer<I, LegacyItemStack<I>> {

    private final PlayerInventory_v1_7<I> playerInventory;
    private final CraftingInventory_v1_7<I> craftingInventory;
    private final CraftingResultInventory_v1_7<I> craftingResultInventory;
    private final ARecipeRegistry<I, LegacyItemStack<I>> recipeRegistry;

    public CraftingTableContainer_v1_7(final int windowId, final PlayerInventory_v1_7<I> playerInventory, final ARecipeRegistry<I, LegacyItemStack<I>> recipeRegistry) {
        super(windowId);
        this.playerInventory = playerInventory;
        this.craftingInventory = new CraftingInventory_v1_7<>(this, 3, 3);
        this.craftingResultInventory = new CraftingResultInventory_v1_7<>();
        this.recipeRegistry = recipeRegistry;

        this.addSlot(id -> new CraftingResultSlot_v1_7<>(this.craftingResultInventory, id, this.craftingInventory));
        for (int i = 0; i < this.craftingInventory.getSize(); i++) this.addSlot(this.craftingInventory, i, Slot.acceptAll());
        for (int i = 0; i < 27; i++) this.addSlot(this.playerInventory, 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.playerInventory, i, Slot.acceptAll());

        this.craftingUpdate(this.craftingInventory);
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

    public ARecipeRegistry<I, LegacyItemStack<I>> getRecipeRegistry() {
        return this.recipeRegistry;
    }

    @Override
    public void craftingUpdate(ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        this.craftingResultInventory.setStack(0, this.recipeRegistry.findCraftingRecipe(craftingInventory));
    }

    @Override
    public void close() {
        for (int i = 0; i < this.craftingInventory.getSize(); i++) this.craftingInventory.setStack(i, null);
        this.craftingResultInventory.setStack(0, null);
    }

    @Override
    protected LegacyItemStack<I> moveStack(InventoryHolder<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> inventoryHolder, int slotId) {
        Slot<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> slot = this.getSlot(slotId);
        if (slot == null || slot.getStack() == null) return null;

        LegacyItemStack<I> slotStack = slot.getStack();
        LegacyItemStack<I> out = slotStack.copy();
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

    @Override
    protected boolean canTakeAll(Slot<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> slot, LegacyItemStack<I> stack) {
        return !slot.getInventory().equals(this.craftingResultInventory);
    }

}
