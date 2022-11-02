package net.lenni0451.mcstructs.items;

import net.lenni0451.mcstructs.items.info.ItemMeta;
import net.lenni0451.mcstructs.nbt.tags.CompoundNbt;

public abstract class AItemStack<I, S extends AItemStack<I, S>> {

    private final ItemRegistry<I, S> registry;
    private final I item;
    private int count;
    private CompoundNbt tag;

    public AItemStack(final ItemRegistry<I, S> registry, final I item) {
        this.registry = registry;
        this.item = item;
    }

    public ItemRegistry<I, S> getRegistry() {
        return this.registry;
    }

    public ItemMeta getMeta() {
        return this.registry.getMeta(this.item);
    }

    public I getItem() {
        return this.item;
    }

    public boolean isEmpty() {
        return this.item == null || this.count <= 0;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public abstract int getDamage();

    public abstract void setDamage(final int damage);

    public CompoundNbt getTag() {
        return this.tag;
    }

    public CompoundNbt copyTag() {
        if (this.tag == null) return null;
        return (CompoundNbt) this.tag.copy();
    }

    public CompoundNbt getOrCreateTag() {
        if (this.tag == null) this.tag = new CompoundNbt();
        return this.tag;
    }

    public void setTag(CompoundNbt tag) {
        this.tag = tag;
    }

    public boolean hasTag() {
        return this.tag != null;
    }

    public S copy() {
        S stack = this.getRegistry().create(this.getItem(), this.getCount(), this.copyTag());
        stack.setDamage(this.getDamage());
        return stack;
    }

    public S split(int count) {
        count = Math.min(count, this.count);
        S stack = this.copy();
        stack.setCount(count);
        this.count -= count;
        return stack;
    }

}
