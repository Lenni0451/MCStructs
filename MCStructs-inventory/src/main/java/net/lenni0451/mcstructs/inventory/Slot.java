package net.lenni0451.mcstructs.inventory;

import net.lenni0451.mcstructs.inventory.types.IInventory;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemType;

import java.util.function.Function;

public class Slot<I, S extends AItemStack<I, S>> {

    public static <I, S extends AItemStack<I, S>> Function<S, Integer> acceptAll() {
        return stack -> stack.getMeta().maxCount();
    }

    public static <I, S extends AItemStack<I, S>> Function<S, Integer> acceptNone() {
        return item -> 0;
    }

    public static <I, S extends AItemStack<I, S>> Function<S, Integer> acceptType(final ItemType type) {
        return item -> item.getMeta().types().contains(type) ? item.getMeta().maxCount() : 0;
    }

    public static <I, S extends AItemStack<I, S>> Function<S, Integer> acceptType(final ItemType type, final int maxCount) {
        return item -> item.getMeta().types().contains(type) ? Math.min(item.getMeta().maxCount(), maxCount) : 0;
    }

    public static <I, S extends AItemStack<I, S>> Function<S, Integer> acceptTypes(final ItemType... types) {
        return item -> {
            for (ItemType type : types) {
                if (item.getMeta().types().contains(type)) return item.getMeta().maxCount();
            }
            return 0;
        };
    }

    public static <I, S extends AItemStack<I, S>> Function<S, Integer> acceptTypes(final int maxCount, final ItemType... types) {
        return item -> {
            for (ItemType type : types) {
                if (item.getMeta().types().contains(type)) return Math.min(item.getMeta().maxCount(), maxCount);
            }
            return 0;
        };
    }


    private final IInventory<I, S> inventory;
    private final int slotIndex;
    private final int inventoryIndex;
    private final Function<S, Integer> acceptor;

    public Slot(final IInventory<I, S> inventory, final int slotIndex, final int inventoryIndex, final Function<S, Integer> acceptor) {
        this.inventory = inventory;
        this.slotIndex = slotIndex;
        this.inventoryIndex = inventoryIndex;
        this.acceptor = acceptor;
    }

    public IInventory<I, S> getInventory() {
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

    public boolean canTake(final InventoryHolder<? extends IInventory<I, S>, I, S> inventoryHolder) {
        //TODO: canTakeStack (anvil xp check)
        return true;
    }

}
