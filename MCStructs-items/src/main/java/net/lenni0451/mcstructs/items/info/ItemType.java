package net.lenni0451.mcstructs.items.info;

import java.util.List;

public enum ItemType {

    //armor
    BOOTS, LEGGINGS, CHESTPLATE, HELMET,
    //buckets
    BUCKET, WATER_BUCKET, LAVA_BUCKET, MILK_BUCKET,
    //other items
    SKULL, PUMPKIN, WRITTEN_BOOK, WRITABLE_BOOK, DYE,
    MAP, FILLED_MAP, GUNPOWDER, FIREWORK_STAR, PAPER,
    GLOWSTONE_DUST, DIAMOND, FIRE_CHARGE, FEATHER, GOLD_NUGGET,
    FIREWORK_ROCKET, BOTTLE, WATER_BOTTLE, POTION, SPLASH_POTION,
    FISH, IRON_INGOT, GOLD_INGOT, EMERALD;

    public static boolean isArmor(final List<ItemType> types) {
        return types.contains(BOOTS) || types.contains(LEGGINGS) || types.contains(CHESTPLATE) || types.contains(HELMET);
    }

}
