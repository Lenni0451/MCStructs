package net.lenni0451.mcstructs.inventory.impl.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.inventory.recipes.ARecipeRegistry;
import net.lenni0451.mcstructs.inventory.recipes.VillagerRecipe;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class VillagerInventory_v1_7<I> implements IInventory_v1_7<I> {

    private final ARecipeRegistry<I, LegacyItemStack<I>> recipeRegistry;
    private final LegacyItemStack<I>[] stacks = new LegacyItemStack[3];
    private int currentRecipeIndex;
    private VillagerRecipe<I, LegacyItemStack<I>> currentRecipe;

    public VillagerInventory_v1_7(final ARecipeRegistry<I, LegacyItemStack<I>> recipeRegistry) {
        this.recipeRegistry = recipeRegistry;
    }

    public ARecipeRegistry<I, LegacyItemStack<I>> getRecipeRegistry() {
        return this.recipeRegistry;
    }

    public LegacyItemStack<I>[] getStacks() {
        return this.stacks;
    }

    public int getCurrentRecipeIndex() {
        return this.currentRecipeIndex;
    }

    public void setCurrentRecipeIndex(final int currentRecipeIndex) {
        this.currentRecipeIndex = currentRecipeIndex;
        this.onUpdate();
    }

    public VillagerRecipe<I, LegacyItemStack<I>> getCurrentRecipe() {
        return this.currentRecipe;
    }

    @Override
    public LegacyItemStack<I> split(int slotId, int count) {
        if (this.stacks[slotId] == null) return null;

        LegacyItemStack<I> stack = this.stacks[slotId];
        if (slotId == 2) {
            this.stacks[slotId] = null;
        } else if (stack.getCount() <= count) {
            this.stacks[slotId] = null;
            if (slotId == 0 || slotId == 1) this.onUpdate();
        } else {
            stack = stack.split(count);
            if (this.stacks[slotId].getCount() == 0) this.stacks[slotId] = null;
            if (slotId == 0 || slotId == 1) this.onUpdate();
        }
        return stack;
    }

    @Override
    public int getSize() {
        return this.stacks.length;
    }

    @Override
    public LegacyItemStack<I> getStack(int slotId) {
        return this.stacks[slotId];
    }

    @Override
    public void setStack(int slotId, LegacyItemStack<I> stack) {
        this.stacks[slotId] = stack;
        if (stack != null && stack.getCount() > 64) stack.setCount(64);
        if (slotId == 0 || slotId == 1) this.onUpdate();
    }

    @Override
    public void onUpdate() {
        LegacyItemStack<I> stack1 = this.stacks[0];
        LegacyItemStack<I> stack2 = this.stacks[1];
        if (stack1 == null) {
            stack1 = stack2;
            stack2 = null;
        }
        if (stack1 == null) {
            this.stacks[2] = null;
            return;
        }

        VillagerRecipe<I, LegacyItemStack<I>> recipe = this.getUsableRecipe(stack1, stack2);
        if (recipe != null && recipe.isEnabled()) {
            this.currentRecipe = recipe;
            this.setStack(2, recipe.getOutput().copy());
        } else if (stack2 != null) {
            recipe = this.getUsableRecipe(stack2, stack1);
            if (recipe != null && recipe.isEnabled()) {
                this.currentRecipe = recipe;
                this.setStack(2, recipe.getOutput().copy());
            } else {
                this.setStack(2, null);
            }
        } else {
            this.setStack(2, null);
        }
    }

    private VillagerRecipe<I, LegacyItemStack<I>> getUsableRecipe(final LegacyItemStack<I> stack1, final LegacyItemStack<I> stack2) {
        if (this.currentRecipeIndex > 0 && this.currentRecipeIndex < this.recipeRegistry.getVillagerRecipes().size()) {
            VillagerRecipe<I, LegacyItemStack<I>> recipe = this.recipeRegistry.getVillagerRecipes().get(this.currentRecipeIndex);
            if (this.isUsableRecipe(recipe, stack1, stack2)) return recipe;
            else return null;
        }
        for (VillagerRecipe<I, LegacyItemStack<I>> recipe : this.recipeRegistry.getVillagerRecipes()) {
            if (this.isUsableRecipe(recipe, stack1, stack2)) return recipe;
        }
        return null;
    }

    private boolean isUsableRecipe(final VillagerRecipe<I, LegacyItemStack<I>> recipe, final LegacyItemStack<I> stack1, final LegacyItemStack<I> stack2) {
        if (!stack1.getItem().equals(recipe.getInput1().getItem())) return false;
        if (stack1.getCount() < recipe.getInput1().getCount()) return false;
        if (recipe.hasInput2()) {
            if (stack2 == null) return false;
            if (!stack2.getItem().equals(recipe.getInput2().getItem())) return false;
            if (stack2.getCount() < recipe.getInput2().getCount()) return false;
        } else if (stack2 != null) {
            return false;
        }
        return true;
    }

}
