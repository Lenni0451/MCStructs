package net.lenni0451.mcstructs.recipes.impl.v1_7.impl;

import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;
import net.lenni0451.mcstructs.recipes.ICraftingInventory;
import net.lenni0451.mcstructs.recipes.ICraftingRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShapelessCraftingRecipe_v1_7<I> implements ICraftingRecipe<I, LegacyItemStack<I>> {

    private final LegacyItemStack<I>[] ingredients;
    private final LegacyItemStack<I> result;

    public ShapelessCraftingRecipe_v1_7(final LegacyItemStack<I>[] ingredients, final LegacyItemStack<I> result) {
        this.ingredients = ingredients;
        this.result = result;
    }

    public LegacyItemStack<I>[] getIngredients() {
        return this.ingredients;
    }

    public LegacyItemStack<I> getResult() {
        return this.result;
    }

    @Override
    public boolean matches(ItemRegistry<I, LegacyItemStack<I>> itemRegistry, ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        List<LegacyItemStack<I>> ingredients = new ArrayList<>();
        Collections.addAll(ingredients, this.ingredients);

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                LegacyItemStack<I> stack = craftingInventory.getStack(x, y);
                if (stack == null) continue;

                boolean found = false;
                for (LegacyItemStack<I> ingredient : ingredients) {
                    if (stack.getItem().equals(ingredient.getItem()) && (ingredient.getCount() == 32767 || stack.getDamage() == ingredient.getDamage())) {
                        found = true;
                        ingredients.remove(ingredient);
                        break;
                    }
                }
                if (!found) return false;
            }
        }
        return ingredients.isEmpty();
    }

    @Override
    public LegacyItemStack<I> getResult(ItemRegistry<I, LegacyItemStack<I>> itemRegistry, ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        return this.result.copy();
    }

}
