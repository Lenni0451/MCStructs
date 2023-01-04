package net.lenni0451.mcstructs.items.stacks;

import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.ItemRegistry;

import java.util.Objects;

/**
 * The implementation of {@link AItemStack} which stores the damage value in the Nbt tag.
 *
 * @param <I> The type of the item (e.g. Integer)
 */
public class ItemStack<I> extends AItemStack<I, ItemStack<I>> {

    public ItemStack(final ItemRegistry<I, ItemStack<I>> registry, final I item) {
        super(registry, item);
    }

    @Override
    public int getDamage() {
        if (this.getTag() == null) return 0;
        return this.getTag().getInt("Damage");
    }

    @Override
    public void setDamage(int damage) {
        this.getOrCreateTag().addInt("Damage", Math.max(0, damage));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemStack<?> itemStack = (ItemStack<?>) o;
        return getCount() == itemStack.getCount() && Objects.equals(getItem(), itemStack.getItem()) && Objects.equals(getTag(), itemStack.getTag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItem(), getCount(), getTag());
    }

    @Override
    public String toString() {
        return "ItemStack{" +
                "item=" + getItem() +
                ", count=" + getCount() +
                ", tag=" + getTag() +
                '}';
    }

}
