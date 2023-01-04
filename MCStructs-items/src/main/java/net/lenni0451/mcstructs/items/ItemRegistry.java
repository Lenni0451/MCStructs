package net.lenni0451.mcstructs.items;

import net.lenni0451.mcstructs.items.info.ItemMeta;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * The registry for all items and their metadata.<br>
 * It is also used to create new item stacks.
 *
 * @param <I> The type of the item (e.g. Integer)
 * @param <S> The type of the item stack (e.g. LegacyItemStack)
 */
public class ItemRegistry<I, S extends AItemStack<I, S>> {

    private final Map<I, ItemMeta> itemMetas = new HashMap<>();
    private final BiFunction<ItemRegistry<I, S>, I, S> createItemStack;
    private final S empty;

    public ItemRegistry(final BiFunction<ItemRegistry<I, S>, I, S> createItemStack) {
        this.createItemStack = createItemStack;
        this.empty = this.createItemStack.apply(this, null);
    }

    /**
     * Get or create the meta of an item.
     *
     * @param item The item
     * @return The meta of the item
     */
    public ItemMeta getMeta(final I item) {
        return this.itemMetas.computeIfAbsent(item, i -> new ItemMeta());
    }

    /**
     * Get a list of items which have the given type.
     *
     * @param type The type
     * @return A list of items which have the given type
     */
    public List<I> byType(final ItemType type) {
        return this.itemMetas.entrySet().stream().filter(entry -> entry.getValue().getTypes().contains(type)).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * Get a required item by its type.
     *
     * @param type The type
     * @return The item
     * @throws IllegalArgumentException If there are more than one item or no items with the given type
     */
    public I requireByType(final ItemType type) {
        return this.requireByType(type, 1).get(0);
    }

    /**
     * Get a required list of items with the given type.
     *
     * @param type     The type
     * @param maxCount The maximum count of items
     * @return A list of items which have the given type
     */
    public List<I> requireByType(final ItemType type, final int maxCount) {
        List<I> items = this.byType(type);
        if (items.isEmpty()) throw new IllegalArgumentException("No item of type " + type + " found");
        if (items.size() > maxCount) throw new IllegalArgumentException("More than " + maxCount + " items of type " + type + " found");
        return items;
    }

    /**
     * @return An empty item stack
     */
    public S empty() {
        return this.empty;
    }

    /**
     * Create a new item stack.<br>
     * The default count is 1.
     *
     * @param item The item
     * @return The created item stack
     */
    public S create(final I item) {
        return this.createItemStack.apply(this, item);
    }

    /**
     * Create a new item stack.
     *
     * @param item  The item
     * @param count The count
     * @return The created item stack
     */
    public S create(final I item, final int count) {
        S stack = this.create(item);
        stack.setCount(count);
        return stack;
    }

    /**
     * Create a new item stack.
     *
     * @param item   The item
     * @param count  The count
     * @param damage The damage
     * @return The created item stack
     */
    public S create(final I item, final int count, final int damage) {
        S stack = this.create(item, count);
        stack.setDamage(damage);
        return stack;
    }

    /**
     * Create a new item stack.
     *
     * @param item  The item
     * @param count The count
     * @param tag   The Nbt tag
     * @return The created item stack
     */
    public S create(final I item, final int count, final CompoundTag tag) {
        S stack = this.create(item, count);
        stack.setTag(tag);
        return stack;
    }

    /**
     * Create a new item stack.
     *
     * @param item   The item
     * @param count  The count
     * @param damage The damage
     * @param tag    The Nbt tag
     * @return The created item stack
     */
    public S create(final I item, final int count, final int damage, final CompoundTag tag) {
        S stack = this.create(item, count, damage);
        stack.setTag(tag);
        return stack;
    }

}
