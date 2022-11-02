package net.lenni0451.mcstructs.items.stacks;

import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.ItemRegistry;

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

}
