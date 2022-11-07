package net.lenni0451.mcstructs.inventory.crafting;

import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.ItemRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeRegistry<I, S extends AItemStack<I, S>> {

    private final ItemRegistry<I, S> itemRegistry;
    private final List<ICraftingRecipe<I, S>> craftingRecipes;
    private final Map<I, I> furnaceRecipes;

    public RecipeRegistry(final ItemRegistry<I, S> itemRegistry) {
        this.itemRegistry = itemRegistry;
        this.craftingRecipes = new ArrayList<>();
        this.furnaceRecipes = new HashMap<>();
    }

    public ItemRegistry<I, S> getItemRegistry() {
        return this.itemRegistry;
    }

    public List<ICraftingRecipe<I, S>> getCraftingRecipes() {
        return this.craftingRecipes;
    }

    public Map<I, I> getFurnaceRecipes() {
        return this.furnaceRecipes;
    }

    public void register(final ICraftingRecipe<I, S> recipe) {
        this.craftingRecipes.add(recipe);
    }

}
