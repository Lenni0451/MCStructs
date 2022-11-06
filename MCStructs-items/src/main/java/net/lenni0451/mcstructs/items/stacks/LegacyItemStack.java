package net.lenni0451.mcstructs.items.stacks;

import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.ItemRegistry;

import java.util.Objects;

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
        return this.getCount() == that.getCount() && damage == that.damage && Objects.equals(getItem(), that.getItem()) && Objects.equals(getTag(), that.getTag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItem(), getCount(), getTag(), damage);
    }

    @Override
    public String toString() {
        return "LegacyItemStack{" +
                "item=" + getItem() +
                ", count=" + getCount() +
                ", tag=" + getTag() +
                ", damage=" + damage +
                '}';
    }

}
