package net.lenni0451.mcstructs.inventory;

import net.lenni0451.mcstructs.inventory.types.IInventory;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemTag;
import net.lenni0451.mcstructs.items.info.ItemType;

import java.util.function.BiFunction;

public class Slot<T extends IInventory<I, S>, I, S extends AItemStack<I, S>> {

    public static <T extends IInventory<I, S>, I, S extends AItemStack<I, S>> BiFunction<Slot<T, I, S>, S, Integer> acceptAll() {
        return null;
    }

    public static <T extends IInventory<I, S>, I, S extends AItemStack<I, S>> BiFunction<Slot<T, I, S>, S, Integer> acceptNone() {
        return accept(0);
    }

    public static <T extends IInventory<I, S>, I, S extends AItemStack<I, S>> BiFunction<Slot<T, I, S>, S, Integer> accept(final int count) {
        return (slot, stack) -> count;
    }

    public static <T extends IInventory<I, S>, I, S extends AItemStack<I, S>> BiFunction<Slot<T, I, S>, S, Integer> acceptTypes(final ItemType... types) {
        return acceptTypes(64, types);
    }

    public static <T extends IInventory<I, S>, I, S extends AItemStack<I, S>> BiFunction<Slot<T, I, S>, S, Integer> acceptTypes(final int maxCount, final ItemType... types) {
        return (slot, stack) -> {
            for (ItemType type : types) {
                if (stack.getMeta().getTypes().contains(type)) return maxCount;
            }
            return 0;
        };
    }

    public static <T extends IInventory<I, S>, I, S extends AItemStack<I, S>> BiFunction<Slot<T, I, S>, S, Integer> acceptTags(final ItemTag... tags) {
        return acceptTags(64, tags);
    }

    public static <T extends IInventory<I, S>, I, S extends AItemStack<I, S>> BiFunction<Slot<T, I, S>, S, Integer> acceptTags(final int maxCount, final ItemTag... tags) {
        return (slot, stack) -> {
            for (ItemTag tag : tags) {
                if (stack.getMeta().getTags().contains(tag)) return maxCount;
            }
            return 0;
        };
    }


    private final IInventory<I, S> inventory;
    private final int slotIndex;
    private final int inventoryIndex;
    private final BiFunction<Slot<T, I, S>, S, Integer> acceptor;

    public Slot(final IInventory<I, S> inventory, final int slotIndex, final int inventoryIndex, final BiFunction<Slot<T, I, S>, S, Integer> acceptor) {
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
        if (this.acceptor == null) return this.inventory.maxStackCount();
        else return this.acceptor.apply(this, stack);
    }

    public boolean canTake(final InventoryHolder<T, I, S> inventoryHolder) {
        return true;
    }

    public void onUpdate() {
        this.inventory.onUpdate();
    }

    public void onTake(final InventoryHolder<T, I, S> inventoryHolder, final S stack) {
        this.onUpdate();
    }

}
