package net.lenni0451.mcstructs.inventory;

import net.lenni0451.mcstructs.inventory.types.AContainer;
import net.lenni0451.mcstructs.inventory.types.IInventory;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.recipes.ARecipeRegistry;

import java.util.function.BiFunction;

public class InventoryHolder<T extends IInventory<I, S>, I, S extends AItemStack<I, S>> {

    private final T playerInventory;
    private final AContainer<T, I, S> playerInventoryContainer;
    private AContainer<T, I, S> openContainer;
    private boolean creativeMode;
    private int xpLevel;

    public <R extends ARecipeRegistry<I, S>> InventoryHolder(final T playerInventory, final R recipeRegistry, final BiFunction<T, R, AContainer<T, I, S>> playerContainerProvider) {
        this(playerInventory, playerContainerProvider.apply(playerInventory, recipeRegistry));
    }

    public InventoryHolder(final T playerInventory, final AContainer<T, I, S> playerContainer) {
        this.playerInventory = playerInventory;
        this.playerInventoryContainer = playerContainer;
        this.openContainer = this.playerInventoryContainer;
    }

    public T getPlayerInventory() {
        return this.playerInventory;
    }

    public AContainer<T, I, S> getPlayerInventoryContainer() {
        return this.playerInventoryContainer;
    }

    public AContainer<T, I, S> getOpenContainer() {
        return this.openContainer;
    }

    public void setOpenContainer(final AContainer<T, I, S> openContainer) {
        this.openContainer.close();
        if (openContainer == null) this.openContainer = this.playerInventoryContainer;
        else this.openContainer = openContainer;
    }

    public boolean isCreativeMode() {
        return this.creativeMode;
    }

    public void setCreativeMode(final boolean creativeMode) {
        this.creativeMode = creativeMode;
    }

    public int getXpLevel() {
        return this.xpLevel;
    }

    public void setXpLevel(final int xpLevel) {
        this.xpLevel = xpLevel;
    }

}
