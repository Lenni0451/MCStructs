package net.lenni0451.mcstructs.inventory.crafting.impl.v1_7;

import net.lenni0451.mcstructs.inventory.crafting.ICraftingRecipe;
import net.lenni0451.mcstructs.inventory.crafting.RecipeRegistry;
import net.lenni0451.mcstructs.inventory.crafting.impl.v1_7.impl.*;
import net.lenni0451.mcstructs.inventory.types.ICraftingInventory;
import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.info.ItemTag;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class RecipeRegistry_v1_7<I> extends RecipeRegistry<I, LegacyItemStack<I>> {

    public RecipeRegistry_v1_7(final ItemRegistry<I, LegacyItemStack<I>> itemRegistry) {
        this(itemRegistry, stack -> 0);
    }

    public RecipeRegistry_v1_7(final ItemRegistry<I, LegacyItemStack<I>> itemRegistry, final Function<LegacyItemStack<I>, Integer> mapScaleProvider) {
        super(itemRegistry);

        this.register(new ArmorColorCraftingRecipe_v1_7<>());
        this.register(new BookCopyCraftingRecipe_v1_7<>());
        this.register(new MapCopyCraftingRecipe_v1_7<>());
        this.register(new ExtendMapCraftingRecipe_v1_7<>(itemRegistry, mapScaleProvider));
        this.register(new FireworksCraftingRecipe_v1_7<>());
    }

    public ShapelessCraftingRecipe_v1_7<I> shapelessCraftingRecipe(final LegacyItemStack<I> result, final Object... args) {
        List<LegacyItemStack<I>> ingredients = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof LegacyItemStack) {
                ingredients.add((LegacyItemStack<I>) arg);
            } else if (arg instanceof ItemType) {
                ingredients.add(this.getItemRegistry().create((I) arg));
            } else {
                try {
                    ingredients.add(this.getItemRegistry().create((I) arg));
                    continue;
                } catch (Exception ignored) {
                }
                throw new IllegalArgumentException("Invalid argument: " + arg);
            }
        }

        ShapelessCraftingRecipe_v1_7<I> recipe = new ShapelessCraftingRecipe_v1_7<>(ingredients.toArray(new LegacyItemStack[0]), result);
        this.register(recipe);
        return recipe;
    }

    public ShapedCraftingRecipe_v1_7<I> shapedCraftingRecipe(final LegacyItemStack<I> result, final Object... args) {
        StringBuilder pattern = new StringBuilder();
        int index = 0;
        int width = 0;
        int height = 0;

        if (args[index] instanceof String[]) {
            String[] lines = (String[]) args[index++];
            for (String line : lines) {
                height++;
                width = line.length();
                pattern.append(line);
            }
        } else {
            while (args[index] instanceof String) {
                String line = (String) args[index++];
                height++;
                width = line.length();
                pattern.append(line);
            }
        }

        Map<Character, LegacyItemStack<I>> charToStack = new HashMap<>();
        while (index < args.length) {
            Character c = (Character) args[index];
            LegacyItemStack<I> stack = null;

            Object arg = args[index + 1];
            if (arg instanceof LegacyItemStack) {
                stack = (LegacyItemStack<I>) arg;
            } else if (arg instanceof ItemType) {
                stack = this.getItemRegistry().create(this.getItemRegistry().requireByType((ItemType) arg));
            } else {
                try {
                    stack = this.getItemRegistry().create((I) arg);
                } catch (ClassCastException ignored) {
                }
            }
            charToStack.put(c, stack);

            index += 2;
        }

        LegacyItemStack<I>[] ingredients = new LegacyItemStack[width * height];
        for (int i = 0; i < ingredients.length; i++) {
            char c = pattern.charAt(i);
            if (charToStack.containsKey(c)) ingredients[i] = charToStack.get(c).copy();
            else ingredients[i] = null;
        }

        ShapedCraftingRecipe_v1_7<I> recipe = new ShapedCraftingRecipe_v1_7<>(width, height, ingredients, result);
        this.register(recipe);
        return recipe;
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

}
