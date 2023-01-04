package net.lenni0451.mcstructs.inventory;

import net.lenni0451.mcstructs.inventory.types.AContainer;
import net.lenni0451.mcstructs.inventory.types.IInventory;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemTag;
import net.lenni0451.mcstructs.items.info.ItemType;

import java.util.function.BiFunction;

/**
 * The base slot for containers.
 *
 * @param <T> The type of the inventory (e.g. IInventory)
 * @param <I> The type of the item (e.g. Integer)
 * @param <S> The type of the item stack (e.g. LegacyItemStack)
 */
public class Slot<T extends IInventory<I, S>, I, S extends AItemStack<I, S>> {

    /**
     * Get a slot count function which accepts all items.
     *
     * @param <T> The type of the inventory (e.g. IInventory)
     * @param <I> The type of the item (e.g. Integer)
     * @param <S> The type of the item stack (e.g. LegacyItemStack)
     * @return The slot count function
     */
    public static <T extends IInventory<I, S>, I, S extends AItemStack<I, S>> BiFunction<Slot<T, I, S>, S, Integer> acceptAll() {
        return null;
    }

    /**
     * Get a slots count function which accepts no items.
     *
     * @param <T> The type of the inventory (e.g. IInventory)
     * @param <I> The type of the item (e.g. Integer)
     * @param <S> The type of the item stack (e.g. LegacyItemStack)
     * @return The slot count function
     */
    public static <T extends IInventory<I, S>, I, S extends AItemStack<I, S>> BiFunction<Slot<T, I, S>, S, Integer> acceptNone() {
        return accept(0);
    }

    /**
     * Get a slot count function which accepts the given count of items.
     *
     * @param count The count of items
     * @param <T>   The type of the inventory (e.g. IInventory)
     * @param <I>   The type of the item (e.g. Integer)
     * @param <S>   The type of the item stack (e.g. LegacyItemStack)
     * @return The slot count function
     */
    public static <T extends IInventory<I, S>, I, S extends AItemStack<I, S>> BiFunction<Slot<T, I, S>, S, Integer> accept(final int count) {
        return (slot, stack) -> count;
    }

    /**
     * Get a slot count function which accepts the given item types.
     *
     * @param types The item types
     * @param <T>   The type of the inventory (e.g. IInventory)
     * @param <I>   The type of the item (e.g. Integer)
     * @param <S>   The type of the item stack (e.g. LegacyItemStack)
     * @return The slot count function
     */
    public static <T extends IInventory<I, S>, I, S extends AItemStack<I, S>> BiFunction<Slot<T, I, S>, S, Integer> acceptTypes(final ItemType... types) {
        return acceptTypes(64, types);
    }

    /**
     * Get a slot count function which accepts the given item types with the given max count.
     *
     * @param maxCount The max count of items
     * @param types    The item types
     * @param <T>      The type of the inventory (e.g. IInventory)
     * @param <I>      The type of the item (e.g. Integer)
     * @param <S>      The type of the item stack (e.g. LegacyItemStack)
     * @return The slot count function
     */
    public static <T extends IInventory<I, S>, I, S extends AItemStack<I, S>> BiFunction<Slot<T, I, S>, S, Integer> acceptTypes(final int maxCount, final ItemType... types) {
        return (slot, stack) -> {
            for (ItemType type : types) {
                if (stack.getMeta().getTypes().contains(type)) return maxCount;
            }
            return 0;
        };
    }

    /**
     * Get a slot count function which accepts the given item tags.
     *
     * @param tags The item tags
     * @param <T>  The type of the inventory (e.g. IInventory)
     * @param <I>  The type of the item (e.g. Integer)
     * @param <S>  The type of the item stack (e.g. LegacyItemStack)
     * @return The slot count function
     */
    public static <T extends IInventory<I, S>, I, S extends AItemStack<I, S>> BiFunction<Slot<T, I, S>, S, Integer> acceptTags(final ItemTag... tags) {
        return acceptTags(64, tags);
    }

    /**
     * Get a slot count function which accepts the given item tags with the given max count.
     *
     * @param maxCount The max count of items
     * @param tags     The item tags
     * @param <T>      The type of the inventory (e.g. IInventory)
     * @param <I>      The type of the item (e.g. Integer)
     * @param <S>      The type of the item stack (e.g. LegacyItemStack)
     * @return The slot count function
     */
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

    /**
     * Create a new slot.<br>
     * This should not be called directly. Use {@link AContainer#addSlot(IInventory, int, BiFunction)} instead.
     *
     * @param inventory      The inventory
     * @param slotIndex      The slot index
     * @param inventoryIndex The inventory index
     * @param acceptor       The slot count function
     */
    public Slot(final IInventory<I, S> inventory, final int slotIndex, final int inventoryIndex, final BiFunction<Slot<T, I, S>, S, Integer> acceptor) {
        this.inventory = inventory;
        this.slotIndex = slotIndex;
        this.inventoryIndex = inventoryIndex;
        this.acceptor = acceptor;
    }

    /**
     * Get the inventory of this slot.
     *
     * @return The inventory
     */
    public IInventory<I, S> getInventory() {
        return this.inventory;
    }

    /**
     * Get the slot index of this slot.
     *
     * @return The slot index
     */
    public int getSlotIndex() {
        return this.slotIndex;
    }

    /**
     * Get the inventory index of this slot.
     *
     * @return The inventory index
     */
    public int getInventoryIndex() {
        return this.inventoryIndex;
    }

    /**
     * @return The item stack in this slot
     */
    public S getStack() {
        return this.inventory.getStack(this.inventoryIndex);
    }

    /**
     * Set the item stack in this slot.
     *
     * @param stack The item stack
     */
    public void setStack(final S stack) {
        this.inventory.setStack(this.inventoryIndex, stack);
        this.onUpdate();
    }

    /**
     * Check if this slots accepts the given item stack.
     *
     * @param stack The item stack
     * @return True if this slot accepts the given item stack
     */
    public boolean accepts(final S stack) {
        return this.acceptsCount(stack) > 0;
    }

    /**
     * Get the max count of items this slot accepts.
     *
     * @param stack The item stack
     * @return The max count of items this slot accepts
     */
    public int acceptsCount(final S stack) {
        if (this.acceptor == null) return this.inventory.maxStackCount();
        else return this.acceptor.apply(this, stack);
    }

    /**
     * Check if the given inventory holder can take the contents of this slot.
     *
     * @param inventoryHolder The inventory holder
     * @return True if the given inventory holder can take the contents of this slot
     */
    public boolean canTake(final InventoryHolder<T, I, S> inventoryHolder) {
        return true;
    }

    /**
     * Update the inventory of this slot.
     */
    public void onUpdate() {
        this.inventory.onUpdate();
    }

    /**
     * Update the slot when an item stack is taken from this slot.
     *
     * @param inventoryHolder The inventory holder
     * @param stack           The item stack
     */
    public void onTake(final InventoryHolder<T, I, S> inventoryHolder, final S stack) {
        this.onUpdate();
    }

}
