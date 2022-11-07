package net.lenni0451.mcstructs.inventory.recipes;

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
    private final List<VillagerRecipe<I, S>> villagerRecipes;

    public ARecipeRegistry(final ItemRegistry<I, S> itemRegistry) {
        this.itemRegistry = itemRegistry;
        this.craftingRecipes = new ArrayList<>();
        this.furnaceRecipes = new HashMap<>();
        this.villagerRecipes = new ArrayList<>();
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

    public List<VillagerRecipe<I, S>> getVillagerRecipes() {
        return this.villagerRecipes;
    }

    public void registerCraftingRecipe(final ICraftingRecipe<I, S> recipe) {
        this.craftingRecipes.add(recipe);
    }

    public void registerFurnaceRecipe(final S input, final S output) {
        this.furnaceRecipes.put(input, output);
    }

    public void registerVillagerRecipe(final S input1, final S input2, final S output) {
        this.registerVillagerRecipe(input1, input2, output, true);
    }

    public void registerVillagerRecipe(final S input1, final S input2, final S output, final boolean enabled) {
        this.villagerRecipes.add(new VillagerRecipe<>(input1, input2, output, enabled));
    }

    public abstract LegacyItemStack<I> findCraftingRecipe(final ICraftingInventory<I, LegacyItemStack<I>> craftingInventory);

    public abstract S findFurnaceRecipe(final S input);

}
