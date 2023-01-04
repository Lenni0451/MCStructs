package net.lenni0451.mcstructs.inventory;

import net.lenni0451.mcstructs.inventory.types.AContainer;
import net.lenni0451.mcstructs.inventory.types.IInventory;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.recipes.ARecipeRegistry;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

/**
 * The inventory holder.<br>
 * It is the owner of the player inventory and the current container.
 *
 * @param <T> The type of the inventory (e.g. IInventory)
 * @param <I> The type of the item (e.g. Integer)
 * @param <S> The type of the item stack (e.g. LegacyItemStack)
 */
public class InventoryHolder<T extends IInventory<I, S>, I, S extends AItemStack<I, S>> {

    private final T playerInventory;
    private final AContainer<T, I, S> playerInventoryContainer;
    private AContainer<T, I, S> openContainer;
    private boolean creativeMode;
    private int xpLevel;

    /**
     * Create a new inventory holder.
     *
     * @param playerInventory         The player inventory
     * @param recipeRegistry          The recipe registry
     * @param playerContainerProvider The player container provider
     * @param <R>                     The type of the recipe registry (e.g. RecipeRegistry)
     */
    public <R extends ARecipeRegistry<I, S>> InventoryHolder(final T playerInventory, final R recipeRegistry, final BiFunction<T, R, AContainer<T, I, S>> playerContainerProvider) {
        this(playerInventory, playerContainerProvider.apply(playerInventory, recipeRegistry));
    }

    /**
     * Create a new inventory holder.
     *
     * @param playerInventory The player inventory
     * @param playerContainer The player container
     */
    public InventoryHolder(final T playerInventory, final AContainer<T, I, S> playerContainer) {
        this.playerInventory = playerInventory;
        this.playerInventoryContainer = playerContainer;
        this.openContainer = this.playerInventoryContainer;
    }

    /**
     * @return The player inventory
     */
    public T getPlayerInventory() {
        return this.playerInventory;
    }

    /**
     * @return The player inventory container
     */
    public AContainer<T, I, S> getPlayerInventoryContainer() {
        return this.playerInventoryContainer;
    }

    /**
     * @return The currently open container
     */
    public AContainer<T, I, S> getOpenContainer() {
        return this.openContainer;
    }

    /**
     * Set the currently open container.<br>
     * If the container is null the player inventory container will be used.
     *
     * @param openContainer The new open container
     */
    public void setOpenContainer(@Nullable final AContainer<T, I, S> openContainer) {
        this.openContainer.close();
        if (openContainer == null) this.openContainer = this.playerInventoryContainer;
        else this.openContainer = openContainer;
    }

    /**
     * @return If the inventory holder is in creative mode
     */
    public boolean isCreativeMode() {
        return this.creativeMode;
    }

    /**
     * Set the creative mode state.
     *
     * @param creativeMode The new creative mode state
     */
    public void setCreativeMode(final boolean creativeMode) {
        this.creativeMode = creativeMode;
    }

    /**
     * @return The xp level of the inventory holder
     */
    public int getXpLevel() {
        return this.xpLevel;
    }

    /**
     * Set the xp level of the inventory holder.
     *
     * @param xpLevel The new xp level
     */
    public void setXpLevel(final int xpLevel) {
        this.xpLevel = xpLevel;
    }

}
