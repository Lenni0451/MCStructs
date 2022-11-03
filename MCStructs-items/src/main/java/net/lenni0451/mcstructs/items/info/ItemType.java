package net.lenni0451.mcstructs.items.info;

import java.util.List;

public enum ItemType {

    GENERIC,
    //armor
    BOOTS, LEGGINGS, CHESTPLATE, HELMET,
    //tools
    SWORD, PICKAXE, AXE, SHOVEL, HOE,
    //other items
    SKULL, PUMPKIN, WRITTEN_BOOK, WRITABLE_BOOK, DYE,
    MAP, FILLED_MAP, GUNPOWDER, FIREWORK_STAR, PAPER,
    GLOWSTONE_DUST, DIAMOND, FIRE_CHARGE, FEATHER, GOLD_NUGGET,
    FIREWORK_ROCKET,

    //materials
    MATERIAL_LEATHER, MATERIAL_CHAIN, MATERIAL_STONE, MATERIAL_IRON, MATERIAL_GOLD, MATERIAL_DIAMOND;

    public static boolean isArmor(final List<ItemType> types) {
        return types.contains(BOOTS) || types.contains(LEGGINGS) || types.contains(CHESTPLATE) || types.contains(HELMET);
    }

    public static boolean isTool(final List<ItemType> types) {
        return types.contains(SWORD) || types.contains(PICKAXE) || types.contains(AXE) || types.contains(SHOVEL) || types.contains(HOE);
    }

}
