package net.lenni0451.mcstructs.items.stacks;

import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.ItemRegistry;

import java.util.Objects;

/**
 * The implementation of {@link AItemStack} which has a damage value.
 *
 * @param <I> The type of the item (e.g. Integer)
 */
public class LegacyItemStack<I> extends AItemStack<I, LegacyItemStack<I>> {

    private int damage;

    public LegacyItemStack(final ItemRegistry<I, LegacyItemStack<I>> registry, final I item) {
        super(registry, item);
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    @Override
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LegacyItemStack<?> that = (LegacyItemStack<?>) o;
        return this.getCount() == that.getCount() && this.damage == that.damage && Objects.equals(this.getItem(), that.getItem()) && Objects.equals(this.getTag(), that.getTag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getItem(), this.getCount(), this.getTag(), this.damage);
    }

    @Override
    public String toString() {
        return "LegacyItemStack{" +
                "item=" + this.getItem() +
                ", count=" + this.getCount() +
                ", damage=" + this.damage +
                ", tag=" + this.getTag() +
                '}';
    }

}
