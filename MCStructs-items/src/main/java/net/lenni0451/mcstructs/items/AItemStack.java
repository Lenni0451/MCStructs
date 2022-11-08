package net.lenni0451.mcstructs.items;

import net.lenni0451.mcstructs.items.info.ItemMeta;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundNbt;

public abstract class AItemStack<I, S extends AItemStack<I, S>> {

    private final ItemRegistry<I, S> registry;
    private final I item;
    private int count = 1;
    private CompoundNbt tag = null;

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

    public int getRepairCost() {
        if (this.tag == null) return 0;
        if (!this.tag.contains("RepairCost", NbtType.INT)) return 0;
        return this.tag.getInt("RepairCost");
    }

    public void setRepairCost(final int cost) {
        this.getOrCreateTag().addInt("RepairCost", cost);
    }

    public boolean hasCustomName() {
        if (this.tag == null) return false;
        if (!this.tag.contains("display", NbtType.COMPOUND)) return false;
        return this.tag.getCompound("display").contains("Name", NbtType.STRING);
    }

    public String getCustomName() {
        if (!this.hasCustomName()) return null;
        return this.tag.getCompound("display").getString("Name");
    }

    public void setCustomName(final String name) {
        if (this.tag == null) this.tag = new CompoundNbt();
        if (!this.tag.contains("display", NbtType.COMPOUND)) this.tag.addCompound("display", new CompoundNbt());
        this.tag.getCompound("display").addString("Name", name);
    }

    public void removeCustomName() {
        if (this.tag == null) return;
        if (!this.tag.contains("display", NbtType.COMPOUND)) return;
        CompoundNbt display = this.tag.getCompound("display");
        display.remove("Name");
        if (display.isEmpty()) this.tag.remove("display");
        if (this.tag.isEmpty()) this.tag = null;
    }

    public abstract boolean equals(final Object other);

    public abstract int hashCode();

    public abstract String toString();

}
