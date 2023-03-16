package net.lenni0451.mcstructs.items;

import net.lenni0451.mcstructs.core.ICopyable;
import net.lenni0451.mcstructs.items.info.ItemMeta;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The abstract implementation of an item stack.<br>
 * Because of changes to the item damage system there are two different implementations:<br>
 * - {@link net.lenni0451.mcstructs.items.stacks.LegacyItemStack} which has the damage stored in the item stack itself<br>
 * - {@link net.lenni0451.mcstructs.items.stacks.ItemStack} which has the damage stored in the item stack's Nbt data
 *
 * @param <I> The type of the item (e.g. Integer)
 * @param <S> The type of the item stack (e.g. LegacyItemStack)
 */
public abstract class AItemStack<I, S extends AItemStack<I, S>> implements ICopyable<S> {

    private final ItemRegistry<I, S> registry;
    private final I item;
    private int count = 1;
    private CompoundTag tag = null;

    public AItemStack(@Nonnull final ItemRegistry<I, S> registry, @Nullable final I item) {
        this.registry = registry;
        this.item = item;
    }

    /**
     * @return The item registry this item stack belongs to
     */
    public ItemRegistry<I, S> getRegistry() {
        return this.registry;
    }

    /**
     * @return The meta of this item
     */
    public ItemMeta getMeta() {
        return this.registry.getMeta(this.item);
    }

    /**
     * @return The item of this item stack
     */
    @Nullable
    public I getItem() {
        return this.item;
    }

    /**
     * @return If this item stack is empty (the item is null or the count is {@literal <}= 0)
     */
    public boolean isEmpty() {
        return this.item == null || this.count <= 0;
    }

    /**
     * @return The count of this item stack
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Set the count of this item stack.
     *
     * @param count The new count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return The damage of this item stack
     */
    public abstract int getDamage();

    /**
     * Set the damage of this item stack.
     *
     * @param damage The new damage
     */
    public abstract void setDamage(final int damage);

    /**
     * @return The Nbt tag of this item stack
     */
    @Nullable
    public CompoundTag getTag() {
        return this.tag;
    }

    /**
     * @return A copy of the Nbt tag of this item stack
     */
    @Nullable
    public CompoundTag copyTag() {
        if (this.tag == null) return null;
        return (CompoundTag) this.tag.copy();
    }

    /**
     * Get or create a new Nbt tag for this item stack.<br>
     * If the tag is not null it will just be returned.
     *
     * @return The Nbt tag of this item stack
     */
    public CompoundTag getOrCreateTag() {
        if (this.tag == null) this.tag = new CompoundTag();
        return this.tag;
    }

    /**
     * Set the Nbt tag of this item stack.
     *
     * @param tag The new Nbt tag
     */
    public void setTag(final CompoundTag tag) {
        this.tag = tag;
    }

    /**
     * @return If this item stack has a Nbt tag
     */
    public boolean hasTag() {
        return this.tag != null;
    }

    @Override
    public S copy() {
        S stack = this.getRegistry().create(this.getItem(), this.getCount(), this.copyTag());
        stack.setDamage(this.getDamage());
        return stack;
    }

    /**
     * Split an item stack into two stacks.<br>
     * The current stack will be reduced by the given count and a new stack will be created with the given count.<br>
     * If the given count is bigger than the current count the current stack will be empty and the new stack will have the current count.
     *
     * @param count The count of the new stack
     * @return The split stack
     */
    public S split(int count) {
        count = Math.min(count, this.count);
        S stack = this.copy();
        stack.setCount(count);
        this.count -= count;
        return stack;
    }

    /**
     * @return The repair cost of this item stack
     */
    public int getRepairCost() {
        if (this.tag == null) return 0;
        if (!this.tag.contains("RepairCost", NbtType.INT)) return 0;
        return this.tag.getInt("RepairCost");
    }

    /**
     * Set the repair cost of this item stack.<br>
     * A new Nbt tag will be created if there is none.
     *
     * @param cost The new repair cost
     */
    public void setRepairCost(final int cost) {
        this.getOrCreateTag().addInt("RepairCost", cost);
    }

    /**
     * @return If the current item stack has a custom name
     */
    public boolean hasCustomName() {
        if (this.tag == null) return false;
        if (!this.tag.contains("display", NbtType.COMPOUND)) return false;
        return this.tag.getCompound("display").contains("Name", NbtType.STRING);
    }

    /**
     * @return The custom name of this item stack or null if there is none
     */
    public String getCustomName() {
        if (!this.hasCustomName()) return null;
        return this.tag.getCompound("display").getString("Name");
    }

    /**
     * Set the custom name of this item stack.<br>
     * A new Nbt tag will be created if there is none.
     *
     * @param name The new custom name
     */
    public void setCustomName(final String name) {
        if (this.tag == null) this.tag = new CompoundTag();
        if (!this.tag.contains("display", NbtType.COMPOUND)) this.tag.addCompound("display", new CompoundTag());
        this.tag.getCompound("display").addString("Name", name);
    }

    /**
     * Remove the custom name of this item stack.
     */
    public void removeCustomName() {
        if (this.tag == null) return;
        if (!this.tag.contains("display", NbtType.COMPOUND)) return;
        CompoundTag display = this.tag.getCompound("display");
        display.remove("Name");
        if (display.isEmpty()) this.tag.remove("display");
        if (this.tag.isEmpty()) this.tag = null;
    }

    public abstract boolean equals(final Object other);

    public abstract int hashCode();

    public abstract String toString();

}
