package net.lenni0451.mcstructs.inventory.recipes.impl.v1_7;

import net.lenni0451.mcstructs.inventory.recipes.ARecipeRegistry;
import net.lenni0451.mcstructs.inventory.recipes.ICraftingRecipe;
import net.lenni0451.mcstructs.inventory.recipes.impl.v1_7.impl.*;
import net.lenni0451.mcstructs.inventory.types.ICraftingInventory;
import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.info.ItemTag;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

import java.util.Map;
import java.util.function.Function;

public class RecipeRegistry_v1_7<I> extends ARecipeRegistry<I, LegacyItemStack<I>> {

    public RecipeRegistry_v1_7(final ItemRegistry<I, LegacyItemStack<I>> itemRegistry) {
        this(itemRegistry, stack -> 0);
    }

    public RecipeRegistry_v1_7(final ItemRegistry<I, LegacyItemStack<I>> itemRegistry, final Function<LegacyItemStack<I>, Integer> mapScaleProvider) {
        super(itemRegistry);

        this.registerCraftingRecipe(new ArmorColorCraftingRecipe_v1_7<>());
        this.registerCraftingRecipe(new BookCopyCraftingRecipe_v1_7<>());
        this.registerCraftingRecipe(new MapCopyCraftingRecipe_v1_7<>());
        this.registerCraftingRecipe(new ExtendMapCraftingRecipe_v1_7<>(itemRegistry, mapScaleProvider));
        this.registerCraftingRecipe(new FireworksCraftingRecipe_v1_7<>());
    }

    public LegacyItemStack<I> findCraftingRecipe(final ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        LegacyItemStack<I> stack1 = null;
        LegacyItemStack<I> stack2 = null;
        int stackCount = 0;

        for (int i = 0; i < craftingInventory.getSize(); i++) {
            LegacyItemStack<I> stack = craftingInventory.getStack(i);
            if (stack == null) continue;

            if (stackCount == 0) stack1 = stack;
            else if (stackCount == 1) stack2 = stack;
            stackCount++;
        }

        if (stackCount == 2 && stack1.getItem().equals(stack2.getItem()) && stack1.getCount() == 1 && stack2.getCount() == 1 && stack1.getMeta().tags().contains(ItemTag.DAMAGEABLE)) {
            int damage1 = stack1.getMeta().maxDamage() - stack1.getDamage();
            int damage2 = stack2.getMeta().maxDamage() - stack2.getDamage();
            int totalDamage = damage1 + damage2 + stack1.getMeta().maxDamage() * 5 / 100;
            int newDamage = Math.max(0, stack1.getMeta().maxDamage() - totalDamage);

            return this.getItemRegistry().create(stack1.getItem(), 1, newDamage);
        } else {
            for (ICraftingRecipe<I, LegacyItemStack<I>> recipe : this.getCraftingRecipes()) {
                if (recipe.matches(this.getItemRegistry(), craftingInventory)) return recipe.getResult(this.getItemRegistry(), craftingInventory);
            }
            return null;
        }
    }

    @Override
    public LegacyItemStack<I> findFurnaceRecipe(LegacyItemStack<I> input) {
        for (Map.Entry<LegacyItemStack<I>, LegacyItemStack<I>> entry : this.getFurnaceRecipes().entrySet()) {
            if (entry.getKey().getItem().equals(input.getItem()) && (entry.getKey().getDamage() == 32767 || entry.getKey().getDamage() == input.getDamage())) {
                return entry.getValue();
            }
        }
        return null;
    }

}
