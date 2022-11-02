package net.lenni0451.mcstructs.inventory.crafting;

import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

public class RecipeRegistry<I, S extends AItemStack<I, S>> {

    private final ItemRegistry<I, S> itemRegistry;
    private final List<IRecipe<I, S>> recipes;

    public RecipeRegistry(final ItemRegistry<I, S> itemRegistry) {
        this.itemRegistry = itemRegistry;
        this.recipes = new ArrayList<>();
    }

    public ItemRegistry<I, S> getItemRegistry() {
        return this.itemRegistry;
    }

    public List<IRecipe<I, S>> getRecipes() {
        return this.recipes;
    }

}
