package net.lenni0451.mcstructs.all.inventory;

import net.lenni0451.mcstructs.enchantments.EnchantmentRegistry;
import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.impl.v1_7.container.PlayerContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;
import net.lenni0451.mcstructs.recipes.impl.v1_7.RecipeRegistry_v1_7;

/**
 * The data container is used to store all the data needed for the inventory tracking<br>
 * The 1.7 data is used here. You may have to change some things if you want to use another version
 */
public class DataContainer {

    /**
     * The item registry containing all items and their types and tags
     */
    private final ItemRegistry<Integer, LegacyItemStack<Integer>> itemRegistry;
    /**
     * The recipe registry containing all crafting recipes, furnace recipes and villager recipes
     */
    private final RecipeRegistry_v1_7<Integer> recipeRegistry;
    /**
     * The enchantment registry containing all enchantments and item/enchantment incompatibilities
     */
    private final EnchantmentRegistry<Integer, LegacyItemStack<Integer>> enchantmentRegistry;
    /**
     * The inventory holder containing all player/inventory related data
     */
    private final InventoryHolder<PlayerInventory_v1_7<Integer, LegacyItemStack<Integer>>, Integer, LegacyItemStack<Integer>> inventoryHolder;

    public DataContainer() {
        this.itemRegistry = new ItemRegistry<>(LegacyItemStack::new);
        DataRegistry.register(this.itemRegistry); //Register all items
        //It is important that you register all data before creating the next registry because the constructor may query the other registries!

        this.recipeRegistry = new RecipeRegistry_v1_7<>(this.itemRegistry);
        DataRegistry.register(this.recipeRegistry, this.itemRegistry); //Register all recipes
        //It is important that you register all data before creating the next registry because the constructor may query the other registries!

        this.enchantmentRegistry = new EnchantmentRegistry<>();
        DataRegistry.register(this.enchantmentRegistry); //Register all enchantments
        //It is important that you register all data before creating the next registry because the constructor may query the other registries!

        this.inventoryHolder = new InventoryHolder<>(new PlayerInventory_v1_7<>(), this.recipeRegistry, PlayerContainer_v1_7::new);
    }

    public ItemRegistry<Integer, LegacyItemStack<Integer>> getItemRegistry() {
        return this.itemRegistry;
    }

    public RecipeRegistry_v1_7<Integer> getRecipeRegistry() {
        return this.recipeRegistry;
    }

    public EnchantmentRegistry<Integer, LegacyItemStack<Integer>> getEnchantmentRegistry() {
        return this.enchantmentRegistry;
    }

    public InventoryHolder<PlayerInventory_v1_7<Integer, LegacyItemStack<Integer>>, Integer, LegacyItemStack<Integer>> getInventoryHolder() {
        return this.inventoryHolder;
    }

    public boolean isAllowedCharacter(final char c) {
        return c != 167 && c >= 32 && c != 127;
    }

    public String filerAllowedCharacters(final String s) {
        StringBuilder out = new StringBuilder();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (isAllowedCharacter(c)) out.append(c);
        }
        return out.toString();
    }

}
