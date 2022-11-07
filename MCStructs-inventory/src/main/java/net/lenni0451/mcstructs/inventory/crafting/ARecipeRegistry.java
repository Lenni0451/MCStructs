package net.lenni0451.mcstructs.inventory.crafting;

import net.lenni0451.mcstructs.inventory.types.ICraftingInventory;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ARecipeRegistry<I, S extends AItemStack<I, S>> {

    private final ItemRegistry<I, S> itemRegistry;
    private final List<ICraftingRecipe<I, S>> craftingRecipes;
    private final Map<S, S> furnaceRecipes;

    public ARecipeRegistry(final ItemRegistry<I, S> itemRegistry) {
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

    public Map<S, S> getFurnaceRecipes() {
        return this.furnaceRecipes;
    }

    public void register(final ICraftingRecipe<I, S> recipe) {
        this.craftingRecipes.add(recipe);
    }

    public void register(final S input, final S output) {
        this.furnaceRecipes.put(input, output);
    }

    public abstract LegacyItemStack<I> findCraftingRecipe(final ICraftingInventory<I, LegacyItemStack<I>> craftingInventory);

    public abstract S findFurnaceRecipe(final S input);

}
