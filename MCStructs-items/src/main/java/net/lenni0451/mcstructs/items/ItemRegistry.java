package net.lenni0451.mcstructs.items;

import net.lenni0451.mcstructs.items.info.ItemMeta;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class ItemRegistry<I, S extends AItemStack<I, S>> {

    private final Map<I, ItemMeta> itemMetas = new HashMap<>();
    private final BiFunction<ItemRegistry<I, S>, I, S> createItemStack;
    private final S empty;

    public ItemRegistry(final BiFunction<ItemRegistry<I, S>, I, S> createItemStack) {
        this.createItemStack = createItemStack;
        this.empty = this.createItemStack.apply(this, null);
    }

    public ItemMeta getMeta(final I item) {
        return this.itemMetas.computeIfAbsent(item, i -> new ItemMeta());
    }

    public List<I> byType(final ItemType type) {
        return this.itemMetas.entrySet().stream().filter(entry -> entry.getValue().getTypes().contains(type)).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public I requireByType(final ItemType type) {
        return this.requireByType(type, 1).get(0);
    }

    public List<I> requireByType(final ItemType type, final int maxCount) {
        List<I> items = this.byType(type);
        if (items.isEmpty()) throw new IllegalArgumentException("No item of type " + type + " found");
        if (items.size() > maxCount) throw new IllegalArgumentException("More than " + maxCount + " items of type " + type + " found");
        return items;
    }

    public S empty() {
        return this.empty;
    }

    public S create(final I item) {
        return this.createItemStack.apply(this, item);
    }

    public S create(final I item, final int count) {
        S stack = this.create(item);
        stack.setCount(count);
        return stack;
    }

    public S create(final I item, final int count, final int damage) {
        S stack = this.create(item, count);
        stack.setDamage(damage);
        return stack;
    }

    public S create(final I item, final int count, final CompoundTag tag) {
        S stack = this.create(item, count);
        stack.setTag(tag);
        return stack;
    }

    public S create(final I item, final int count, final int damage, final CompoundTag tag) {
        S stack = this.create(item, count, damage);
        stack.setTag(tag);
        return stack;
    }

}
