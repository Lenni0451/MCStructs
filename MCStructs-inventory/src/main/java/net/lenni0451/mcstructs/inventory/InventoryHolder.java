package net.lenni0451.mcstructs.inventory;

import net.lenni0451.mcstructs.inventory.types.AContainer;
import net.lenni0451.mcstructs.inventory.types.IInventory;
import net.lenni0451.mcstructs.items.AItemStack;

import java.util.function.Function;

public class InventoryHolder<I extends IInventory<S>, S extends AItemStack<?, ?>> {

    private final I playerInventory;
    private final AContainer<I, S> playerInventoryContainer;
    private AContainer<I, S> container;
    private boolean creativeMode;

    public InventoryHolder(final I playerInventory, final Function<I, AContainer<I, S>> containerSupplier) {
        this.playerInventory = playerInventory;
        this.playerInventoryContainer = containerSupplier.apply(playerInventory);
        this.container = this.playerInventoryContainer;
    }

    public I getPlayerInventory() {
        return this.playerInventory;
    }

    public AContainer<I, S> getContainer() {
        return this.container;
    }

    public void setContainer(final AContainer<I, S> container) {
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
