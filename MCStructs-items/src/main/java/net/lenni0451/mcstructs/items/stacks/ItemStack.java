package net.lenni0451.mcstructs.items.stacks;

import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.ItemRegistry;

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

}
