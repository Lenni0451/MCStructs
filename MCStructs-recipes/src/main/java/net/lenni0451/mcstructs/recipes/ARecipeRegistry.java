package net.lenni0451.mcstructs.recipes;

import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.ItemRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The registry for all crafting/furnace/villager recipes.
 *
 * @param <I> The type of the item (e.g. Integer)
 * @param <S> The type of the item stack (e.g. LegacyItemStack)
 */
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

    /**
     * @return The item registry
     */
    public ItemRegistry<I, S> getItemRegistry() {
        return this.itemRegistry;
    }

    /**
     * @return The crafting recipes
     */
    public List<ICraftingRecipe<I, S>> getCraftingRecipes() {
        return this.craftingRecipes;
    }

    /**
     * @return The furnace recipes
     */
    public Map<S, S> getFurnaceRecipes() {
        return this.furnaceRecipes;
    }

    /**
     * @return The villager recipes
     */
    public List<VillagerRecipe<I, S>> getVillagerRecipes() {
        return this.villagerRecipes;
    }

    /**
     * Register a crafting recipe.
     *
     * @param recipe The recipe
     */
    public void registerCraftingRecipe(final ICraftingRecipe<I, S> recipe) {
        this.craftingRecipes.add(recipe);
    }

    /**
     * Register a furnace recipe.
     *
     * @param input  The input item stack
     * @param output The output item stack
     */
    public void registerFurnaceRecipe(final S input, final S output) {
        this.furnaceRecipes.put(input, output);
    }

    /**
     * Register a villager recipe.
     *
     * @param input1 The first input item stack
     * @param input2 The second input item stack
     * @param output The output item stack
     */
    public void registerVillagerRecipe(final S input1, @Nullable final S input2, final S output) {
        this.registerVillagerRecipe(input1, input2, output, true);
    }

    /**
     * Register a villager recipe.
     *
     * @param input1  The first input item stack
     * @param input2  The second input item stack
     * @param output  The output item stack
     * @param enabled If the recipe is enabled
     */
    public void registerVillagerRecipe(final S input1, final S input2, final S output, final boolean enabled) {
        this.villagerRecipes.add(new VillagerRecipe<>(input1, input2, output, enabled));
    }

    /**
     * Find a crafting recipe for the given crafting inventory.
     *
     * @param craftingInventory The crafting inventory
     * @return The crafting recipe or null if no recipe was found
     */
    @Nullable
    public abstract S findCraftingRecipe(final ICraftingInventory<I, S> craftingInventory);

    /**
     * Find a furnace recipe for the given input item stack.
     *
     * @param input The input item stack
     * @return The output item stack or null if no recipe was found
     */
    public abstract S findFurnaceRecipe(final S input);

}
