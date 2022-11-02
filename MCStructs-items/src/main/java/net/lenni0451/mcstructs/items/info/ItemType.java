package net.lenni0451.mcstructs.items.info;

public enum ItemType {

    GENERIC,
    BOOTS, LEGGINGS, CHESTPLATE, HELMET,
    SWORD, PICKAXE, AXE, SHOVEL, HOE,
    ;

    public static boolean isArmor(final ItemType type) {
        return BOOTS.equals(type) || LEGGINGS.equals(type) || CHESTPLATE.equals(type) || HELMET.equals(type);
    }

    public static boolean isTool(final ItemType type) {
        return SWORD.equals(type) || PICKAXE.equals(type) || AXE.equals(type) || SHOVEL.equals(type) || HOE.equals(type);
    }

}
