package net.lenni0451.mcstructs.inventory.impl.v1_8.container;

import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.container.CraftingTableContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_8.slots.CraftingResultSlot_v1_8;
import net.lenni0451.mcstructs.inventory.recipes.ARecipeRegistry;
import net.lenni0451.mcstructs.items.AItemStack;

public class CraftingTableContainer_v1_8<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends CraftingTableContainer_v1_7<T, I, S> {

    public CraftingTableContainer_v1_8(int windowId, T playerInventory, ARecipeRegistry<I, S> recipeRegistry) {
        super(windowId, playerInventory, recipeRegistry);
    }

    @Override
    protected void initSlots() {
        this.addSlot(id -> new CraftingResultSlot_v1_8<>(this.getCraftingResultInventory(), id, this.getCraftingInventory()));
        for (int i = 0; i < this.getCraftingInventory().getSize(); i++) this.addSlot(this.getCraftingInventory(), i, Slot.acceptAll());
        for (int i = 0; i < 27; i++) this.addSlot(this.getPlayerInventory(), 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.getPlayerInventory(), i, Slot.acceptAll());

        this.craftingUpdate(this.getCraftingInventory());
    }

}
