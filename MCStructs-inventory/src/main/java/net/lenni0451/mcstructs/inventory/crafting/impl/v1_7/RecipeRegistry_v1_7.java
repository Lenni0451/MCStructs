package net.lenni0451.mcstructs.inventory.crafting.impl.v1_7;

import net.lenni0451.mcstructs.inventory.crafting.RecipeRegistry;
import net.lenni0451.mcstructs.inventory.crafting.impl.v1_7.impl.*;
import net.lenni0451.mcstructs.items.ItemRegistry;
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

        this.register(new ArmorColorRecipe_v1_7<>());
        this.register(new BookCopyRecipe_v1_7<>());
        this.register(new MapCopyRecipe_v1_7<>());
        this.register(new ExtendMapRecipe_v1_7<>(itemRegistry, mapScaleProvider));
        this.register(new FireworksRecipe_v1_7<>());
    }

    public ShapelessRecipe_v1_7<I> shapelessRecipe(final LegacyItemStack<I> result, final Object... args) {
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

        ShapelessRecipe_v1_7<I> recipe = new ShapelessRecipe_v1_7<>(ingredients.toArray(new LegacyItemStack[0]), result);
        this.register(recipe);
        return recipe;
    }

    public ShapedRecipe_v1_7<I> shapedRecipe(final LegacyItemStack<I> result, final Object... args) {
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

        ShapedRecipe_v1_7<I> recipe = new ShapedRecipe_v1_7<>(width, height, ingredients, result);
        this.register(recipe);
        return recipe;
    }

}
