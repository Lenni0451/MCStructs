package net.lenni0451.mcstructs.inventory;

import net.lenni0451.mcstructs.inventory.types.IInventory;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemType;

import java.util.function.Function;

public class Slot<S extends AItemStack<?, ?>> {

    public static <S extends AItemStack<?, ?>> Function<S, Integer> acceptAll() {
        return stack -> stack.getMeta().maxCount();
    }

    public static <S extends AItemStack<?, ?>> Function<S, Integer> acceptNone() {
        return item -> 0;
    }

    public static <S extends AItemStack<?, ?>> Function<S, Integer> acceptType(final ItemType type) {
        return item -> item.getMeta().type().equals(type) ? item.getMeta().maxCount() : 0;
    }

    public static <S extends AItemStack<?, ?>> Function<S, Integer> acceptType(final ItemType type, final int maxCount) {
        return item -> item.getMeta().type().equals(type) ? Math.min(item.getMeta().maxCount(), maxCount) : 0;
    }


    private final IInventory<S> inventory;
    private final int slotIndex;
    private final int inventoryIndex;
    private final Function<S, Integer> acceptor;

    public Slot(final IInventory<S> inventory, final int slotIndex, final int inventoryIndex, final Function<S, Integer> acceptor) {
        this.inventory = inventory;
        this.slotIndex = slotIndex;
        this.inventoryIndex = inventoryIndex;
        this.acceptor = acceptor;
    }

    public IInventory<S> getInventory() {
        return this.inventory;
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    public int getInventoryIndex() {
        return this.inventoryIndex;
    }

    public S getStack() {
        return this.inventory.getStack(this.inventoryIndex);
    }

    public void setStack(final S stack) {
        this.inventory.setStack(this.inventoryIndex, stack);
    }

    public boolean accepts(final S stack) {
        return this.acceptsCount(stack) > 0;
    }

    public int acceptsCount(final S stack) {
        return this.acceptor.apply(stack);
    }

    public boolean canTake(final InventoryHolder<? extends IInventory<S>, S> inventoryHolder) {
        //TODO: canTakeStack (anvil xp check)
        return true;
    }

}
