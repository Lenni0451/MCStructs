package net.lenni0451.mcstructs.items.info;

import java.util.List;

/**
 * The types an item can have.
 */
public enum ItemType {

    //armor
    BOOTS, LEGGINGS, CHESTPLATE, HELMET,
    //tools
    SWORD, PICKAXE, AXE, SHOVEL, HOE, BOW, FISHING_ROD,
    //buckets
    BUCKET, WATER_BUCKET, LAVA_BUCKET, MILK_BUCKET,
    //other items
    SKULL, PUMPKIN, WRITTEN_BOOK, WRITABLE_BOOK, DYE,
    MAP, FILLED_MAP, GUNPOWDER, FIREWORK_STAR, PAPER,
    GLOWSTONE_DUST, DIAMOND, FIRE_CHARGE, FEATHER, GOLD_NUGGET,
    FIREWORK_ROCKET, BOTTLE, WATER_BOTTLE, POTION, SPLASH_POTION,
    FISH, IRON_INGOT, GOLD_INGOT, EMERALD, ENCHANTED_BOOK,
    LEATHER, PLANKS, COBBLESTONE, SADDLE, HORSE_ARMOR;


    /**
     * Check if the given item types are from an armor item.
     *
     * @param types The item types to check
     * @return True if the given item types are from an armor item
     */
    public static boolean isArmor(final List<ItemType> types) {
        return types.contains(BOOTS) || types.contains(LEGGINGS) || types.contains(CHESTPLATE) || types.contains(HELMET);
    }

    /**
     * Check if the given item types are from a tool item.
     *
     * @param types The item types to check
     * @return True if the given item types are from a tool item
     */
    public static boolean isTool(final List<ItemType> types) {
        return types.contains(SWORD) || types.contains(PICKAXE) || types.contains(AXE) || types.contains(SHOVEL) || types.contains(HOE);
    }

}
