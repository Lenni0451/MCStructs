package net.lenni0451.mcstructs.items;

import net.lenni0451.mcstructs.items.info.ItemMeta;
import net.lenni0451.mcstructs.nbt.tags.CompoundNbt;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class ItemRegistry<I, S extends AItemStack<I, S>> {

    private final Map<I, ItemMeta> itemMetas = new HashMap<>();
    private final BiFunction<ItemRegistry<I, S>, I, S> createItemStack;
    private final S empty;

    public ItemRegistry(final BiFunction<ItemRegistry<I, S>, I, S> createItemStack) {
        this.createItemStack = createItemStack;
        this.empty = this.createItemStack.apply(this, null);
    }

    public ItemMeta registerMeta(final I item) {
        return this.itemMetas.computeIfAbsent(item, i -> new ItemMeta());
    }

    public ItemMeta getMeta(final I item) {
        return this.itemMetas.getOrDefault(item, ItemMeta.EMPTY);
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

    public S create(final I item, final int count, final CompoundNbt tag) {
        S stack = this.create(item, count);
        stack.setTag(tag);
        return stack;
    }

}
