package net.lenni0451.mcstructs.inventory.impl.v1_7.slots;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.VillagerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.recipes.VillagerRecipe;
import net.lenni0451.mcstructs.items.AItemStack;

public class VillagerResultSlot_v1_7<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends Slot<T, I, S> {

    public VillagerResultSlot_v1_7(final VillagerInventory_v1_7<I, S> inventory, final int slotIndex) {
        super(inventory, slotIndex, 2, Slot.acceptNone());
    }

    @Override
    public void onTake(InventoryHolder<T, I, S> inventoryHolder, S stack) {
        VillagerRecipe<I, S> recipe = ((VillagerInventory_v1_7<I, S>) this.getInventory()).getCurrentRecipe();
        if (recipe == null) return;

        S stack1 = this.getInventory().getStack(0);
        S stack2 = this.getInventory().getStack(1);
        if (!this.canBuy(recipe, stack1, stack2) && !this.canBuy(recipe, stack2, stack1)) return;
        if (stack1 != null && stack1.getCount() <= 0) stack1 = null;
        if (stack2 != null && stack2.getCount() <= 0) stack2 = null;
        this.getInventory().setStack(0, stack1);
        this.getInventory().setStack(1, stack2);
    }

    private boolean canBuy(final VillagerRecipe<I, S> recipe, final S stack1, final S stack2) {
        S ingredient1 = recipe.getInput1();
        S ingredient2 = recipe.getInput2();
        if (stack1 == null || !stack1.getItem().equals(ingredient1.getItem())) return false;
        if (stack2 != null && ingredient2 != null && stack2.getItem().equals(ingredient2.getItem())) {
            stack1.setCount(stack1.getCount() - ingredient1.getCount());
            stack2.setCount(stack2.getCount() - ingredient2.getCount());
            return true;
        } else if (stack2 == null && ingredient2 == null) {
            stack1.setCount(stack1.getCount() - ingredient1.getCount());
            return true;
        }
        return false;
    }

}
