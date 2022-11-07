package net.lenni0451.mcstructs.inventory.impl.v1_7.slots;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.VillagerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.recipes.VillagerRecipe;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class VillagerResultSlot_v1_7<I> extends Slot<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> {

    public VillagerResultSlot_v1_7(final VillagerInventory_v1_7<I> inventory, final int slotIndex) {
        super(inventory, slotIndex, 2, Slot.acceptNone());
    }

    @Override
    public void onTake(InventoryHolder<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> inventoryHolder, LegacyItemStack<I> stack) {
        VillagerRecipe<I, LegacyItemStack<I>> recipe = ((VillagerInventory_v1_7<I>) this.getInventory()).getCurrentRecipe();
        if (recipe == null) return;

        LegacyItemStack<I> stack1 = this.getInventory().getStack(0);
        LegacyItemStack<I> stack2 = this.getInventory().getStack(1);
        if (!this.canBuy(recipe, stack1, stack2) && !this.canBuy(recipe, stack2, stack1)) return;
        if (stack1 != null && stack1.getCount() <= 0) stack1 = null;
        if (stack2 != null && stack2.getCount() <= 0) stack2 = null;
        this.getInventory().setStack(0, stack1);
        this.getInventory().setStack(1, stack2);
    }

    private boolean canBuy(final VillagerRecipe<I, LegacyItemStack<I>> recipe, final LegacyItemStack<I> stack1, final LegacyItemStack<I> stack2) {
        LegacyItemStack<I> ingredient1 = recipe.getInput1();
        LegacyItemStack<I> ingredient2 = recipe.getInput2();
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
