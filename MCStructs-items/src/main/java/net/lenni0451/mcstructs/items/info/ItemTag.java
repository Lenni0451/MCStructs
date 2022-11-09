package net.lenni0451.mcstructs.items.info;

import java.util.List;

public enum ItemTag {

    //general tags
    DAMAGEABLE, SUBTYPES, FURNACE_FUEL, POTION_INGREDIENT,

    //materials
    MATERIAL_LEATHER, MATERIAL_WOOD, MATERIAL_STONE, MATERIAL_IRON, MATERIAL_GOLD, MATERIAL_DIAMOND;

    public static ItemType getMaterial(final List<ItemTag> tags) {
        if (tags.contains(MATERIAL_LEATHER)) return ItemType.LEATHER;
        else if (tags.contains(MATERIAL_WOOD)) return ItemType.PLANKS;
        else if (tags.contains(MATERIAL_STONE)) return ItemType.COBBLESTONE;
        else if (tags.contains(MATERIAL_IRON)) return ItemType.IRON_INGOT;
        else if (tags.contains(MATERIAL_GOLD)) return ItemType.GOLD_INGOT;
        else if (tags.contains(MATERIAL_DIAMOND)) return ItemType.DIAMOND;
        else return null;
    }

}
