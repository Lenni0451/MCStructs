package net.lenni0451.mcstructs.inventory;

import net.lenni0451.mcstructs.inventory.types.AContainer;
import net.lenni0451.mcstructs.inventory.types.IInventory;
import net.lenni0451.mcstructs.items.AItemStack;

import java.util.function.Function;

public class InventoryHolder<T extends IInventory<I, S>, I, S extends AItemStack<I, S>> {

    private final T playerInventory;
    private final AContainer<T, I, S> playerInventoryContainer;
    private AContainer<T, I, S> container;
    private boolean creativeMode;

    public InventoryHolder(final T playerInventory, final Function<T, AContainer<T, I, S>> containerSupplier) {
        this.playerInventory = playerInventory;
        this.playerInventoryContainer = containerSupplier.apply(playerInventory);
        this.container = this.playerInventoryContainer;
    }

    public T getPlayerInventory() {
        return this.playerInventory;
    }

    public AContainer<T, I, S> getContainer() {
        return this.container;
    }

    public void setContainer(final AContainer<T, I, S> container) {
        if (container == null) this.container = this.playerInventoryContainer;
        else this.container = container;
    }

    public boolean isCreativeMode() {
        return this.creativeMode;
    }

    public void setCreativeMode(final boolean creativeMode) {
        this.creativeMode = creativeMode;
    }

}
