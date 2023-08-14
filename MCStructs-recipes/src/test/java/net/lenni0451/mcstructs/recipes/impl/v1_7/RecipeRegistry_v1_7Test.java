package net.lenni0451.mcstructs.recipes.impl.v1_7;

import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;
import net.lenni0451.mcstructs.recipes.ARecipeRegistry;
import net.lenni0451.mcstructs.recipes.ICraftingInventory;
import net.lenni0451.mcstructs.recipes.VillagerRecipe;
import net.lenni0451.mcstructs.recipes.impl.v1_7.impl.ShapelessCraftingRecipe_v1_7;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeRegistry_v1_7Test {

    private static final ItemRegistry<Integer, LegacyItemStack<Integer>> itemRegistry = new ItemRegistry<>(LegacyItemStack::new);
    private static ARecipeRegistry<Integer, LegacyItemStack<Integer>> recipeRegistry;

    @BeforeAll
    static void prepare() {
        itemRegistry.getMeta(1).addTypes(ItemType.PAPER);
        itemRegistry.getMeta(2).addTypes(ItemType.FILLED_MAP);
        itemRegistry.getMeta(3).addTypes(ItemType.MAP);
        itemRegistry.getMeta(4).addTypes(ItemType.WRITABLE_BOOK);
        itemRegistry.getMeta(4).addTypes(ItemType.WRITTEN_BOOK);

        recipeRegistry = new RecipeRegistry_v1_7<>(itemRegistry);
    }

    @Test
    void registerCraftingRecipe() {
        recipeRegistry.registerCraftingRecipe(new ShapelessCraftingRecipe_v1_7<Integer>(new LegacyItemStack[]{
                itemRegistry.create(1), itemRegistry.create(2)
        }, itemRegistry.create(3)));
        assertEquals(itemRegistry.create(3), recipeRegistry.findCraftingRecipe(new RecipeRegistry_v1_7Test.DummyCraftingInventory()));
    }

    @Test
    void registerFurnaceRecipe() {
        recipeRegistry.registerFurnaceRecipe(itemRegistry.create(1), itemRegistry.create(2));
        assertEquals(itemRegistry.create(2), recipeRegistry.findFurnaceRecipe(itemRegistry.create(1)));
    }

    @Test
    void registerVillagerRecipe() {
        recipeRegistry.registerVillagerRecipe(itemRegistry.create(1), itemRegistry.create(2), itemRegistry.create(3));
        recipeRegistry.registerVillagerRecipe(itemRegistry.create(2), itemRegistry.create(3), itemRegistry.create(4), false);
        assertEquals(2, recipeRegistry.getVillagerRecipes().size());
        for (VillagerRecipe<Integer, LegacyItemStack<Integer>> villagerRecipe : recipeRegistry.getVillagerRecipes()) {
            if (villagerRecipe.getInput1().equals(itemRegistry.create(1))) assertEquals(itemRegistry.create(3), villagerRecipe.getOutput());
            else if (villagerRecipe.getInput1().equals(itemRegistry.create(2))) assertEquals(itemRegistry.create(4), villagerRecipe.getOutput());
            else throw new RuntimeException("Unknown villager recipe");
        }
    }


    private static class DummyCraftingInventory implements ICraftingInventory<Integer, LegacyItemStack<Integer>> {
        @Override
        public int getSize() {
            return 4;
        }

        @Override
        public LegacyItemStack<Integer> getStack(int slotId) {
            switch (slotId) {
                case 0:
                    return itemRegistry.create(1);
                case 1:
                    return itemRegistry.create(2);
                default:
                    return null;
            }
        }

        @Override
        public LegacyItemStack<Integer> getStack(int row, int column) {
            return this.getStack(row * 2 + column);
        }
    }

}
