package net.lenni0451.mcstructs.inventory;

import net.lenni0451.mcstructs.inventory.types.IInventory;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemTag;
import net.lenni0451.mcstructs.items.info.ItemType;

import java.util.function.Function;

public class Slot<T extends IInventory<I, S>, I, S extends AItemStack<I, S>> {

    public static <I, S extends AItemStack<I, S>> Function<S, Integer> acceptAll() {
        return accept(64);
    }

    public static <I, S extends AItemStack<I, S>> Function<S, Integer> acceptNone() {
        return accept(0);
    }

    public static <I, S extends AItemStack<I, S>> Function<S, Integer> accept(final int count) {
        return stack -> Math.min(stack.getMeta().maxCount(), count);
    }

    public static <I, S extends AItemStack<I, S>> Function<S, Integer> acceptTypes(final ItemType... types) {
        return acceptTypes(64, types);
    }

    public static <I, S extends AItemStack<I, S>> Function<S, Integer> acceptTypes(final int maxCount, final ItemType... types) {
        return stack -> {
            for (ItemType type : types) {
                if (stack.getMeta().types().contains(type)) return Math.min(stack.getMeta().maxCount(), maxCount);
            }
            return 0;
        };
    }

    public static <I, S extends AItemStack<I, S>> Function<S, Integer> acceptTags(final ItemTag... tags) {
        return acceptTags(64, tags);
    }

    public static <I, S extends AItemStack<I, S>> Function<S, Integer> acceptTags(final int maxCount, final ItemTag... tags) {
        return stack -> {
            for (ItemTag tag : tags) {
                if (stack.getMeta().tags().contains(tag)) return Math.min(stack.getMeta().maxCount(), maxCount);
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
        this.onUpdate();
    }

    public boolean accepts(final S stack) {
        return this.acceptsCount(stack) > 0;
    }

    public int acceptsCount(final S stack) {
        return this.acceptor.apply(stack);
    }

    public boolean canTake(final InventoryHolder<T, I, S> inventoryHolder) {
        //TODO: canTakeStack (anvil xp check)
        return true;
    }

    public void onUpdate() {
        this.inventory.onUpdate();
    }

    public void onTake(final InventoryHolder<T, I, S> inventoryHolder, final S stack) {
        this.onUpdate();
    }

}
