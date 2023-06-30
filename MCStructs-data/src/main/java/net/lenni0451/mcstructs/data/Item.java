package net.lenni0451.mcstructs.data;

public interface Item {

    int itemId();

    String name();

    int maxStackSize();

    int maxDamage();

    boolean hasSubtypes();

    int burnTime();

    boolean isPotionIngredient();

    boolean isDamageable();

}
