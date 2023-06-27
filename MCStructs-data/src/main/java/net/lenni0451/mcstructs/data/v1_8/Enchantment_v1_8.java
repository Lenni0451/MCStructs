package net.lenni0451.mcstructs.data.v1_8;

import java.util.*;

public class Enchantment_v1_8 {

    public static final List<Enchantment_v1_8> ENCHANTMENT_LIST = new ArrayList<>();
    public static final Map<Enchantment_v1_8, List<Enchantment_v1_8>> INCOMPATIBILITIES = new HashMap<>();

    public static final Enchantment_v1_8 protection = new Enchantment_v1_8("protection", 0, 1, 4, 10, "ARMOR");
    public static final Enchantment_v1_8 fire_protection = new Enchantment_v1_8("fire_protection", 1, 1, 4, 5, "ARMOR");
    public static final Enchantment_v1_8 feather_falling = new Enchantment_v1_8("feather_falling", 2, 1, 4, 5, "ARMOR_FEET");
    public static final Enchantment_v1_8 blast_protection = new Enchantment_v1_8("blast_protection", 3, 1, 4, 2, "ARMOR");
    public static final Enchantment_v1_8 projectile_protection = new Enchantment_v1_8("projectile_protection", 4, 1, 4, 5, "ARMOR");
    public static final Enchantment_v1_8 respiration = new Enchantment_v1_8("respiration", 5, 1, 3, 2, "ARMOR_HEAD");
    public static final Enchantment_v1_8 aqua_affinity = new Enchantment_v1_8("aqua_affinity", 6, 1, 1, 2, "ARMOR_HEAD");
    public static final Enchantment_v1_8 thorns = new Enchantment_v1_8("thorns", 7, 1, 3, 1, "ARMOR_TORSO");
    public static final Enchantment_v1_8 depth_strider = new Enchantment_v1_8("depth_strider", 8, 1, 3, 2, "ARMOR_FEET");
    public static final Enchantment_v1_8 sharpness = new Enchantment_v1_8("sharpness", 16, 1, 5, 10, "WEAPON");
    public static final Enchantment_v1_8 smite = new Enchantment_v1_8("smite", 17, 1, 5, 5, "WEAPON");
    public static final Enchantment_v1_8 bane_of_arthropods = new Enchantment_v1_8("bane_of_arthropods", 18, 1, 5, 5, "WEAPON");
    public static final Enchantment_v1_8 knockback = new Enchantment_v1_8("knockback", 19, 1, 2, 5, "WEAPON");
    public static final Enchantment_v1_8 fire_aspect = new Enchantment_v1_8("fire_aspect", 20, 1, 2, 2, "WEAPON");
    public static final Enchantment_v1_8 looting = new Enchantment_v1_8("looting", 21, 1, 3, 2, "WEAPON");
    public static final Enchantment_v1_8 efficiency = new Enchantment_v1_8("efficiency", 32, 1, 5, 10, "DIGGER");
    public static final Enchantment_v1_8 silk_touch = new Enchantment_v1_8("silk_touch", 33, 1, 1, 1, "DIGGER");
    public static final Enchantment_v1_8 unbreaking = new Enchantment_v1_8("unbreaking", 34, 1, 3, 5, "BREAKABLE");
    public static final Enchantment_v1_8 fortune = new Enchantment_v1_8("fortune", 35, 1, 3, 2, "DIGGER");
    public static final Enchantment_v1_8 power = new Enchantment_v1_8("power", 48, 1, 5, 10, "BOW");
    public static final Enchantment_v1_8 punch = new Enchantment_v1_8("punch", 49, 1, 2, 2, "BOW");
    public static final Enchantment_v1_8 flame = new Enchantment_v1_8("flame", 50, 1, 1, 2, "BOW");
    public static final Enchantment_v1_8 infinity = new Enchantment_v1_8("infinity", 51, 1, 1, 1, "BOW");
    public static final Enchantment_v1_8 luck_of_the_sea = new Enchantment_v1_8("luck_of_the_sea", 61, 1, 3, 2, "FISHING_ROD");
    public static final Enchantment_v1_8 lure = new Enchantment_v1_8("lure", 62, 1, 3, 2, "FISHING_ROD");

    static {
        INCOMPATIBILITIES.put(protection, Arrays.asList(fire_protection, blast_protection, projectile_protection));
        INCOMPATIBILITIES.put(fire_protection, Arrays.asList(protection, blast_protection, projectile_protection));
        INCOMPATIBILITIES.put(blast_protection, Arrays.asList(protection, fire_protection, projectile_protection));
        INCOMPATIBILITIES.put(projectile_protection, Arrays.asList(protection, fire_protection, blast_protection));
        INCOMPATIBILITIES.put(sharpness, Arrays.asList(smite, bane_of_arthropods));
        INCOMPATIBILITIES.put(smite, Arrays.asList(sharpness, bane_of_arthropods));
        INCOMPATIBILITIES.put(bane_of_arthropods, Arrays.asList(sharpness, smite));
        INCOMPATIBILITIES.put(silk_touch, Arrays.asList(looting, fortune, luck_of_the_sea));
        INCOMPATIBILITIES.put(fortune, Arrays.asList(silk_touch));
    }


    private final String name;
    private final int id;
    private final int minLevel;
    private final int maxLevel;
    private final int rarity;
    private final String category;

    private Enchantment_v1_8(final String name, final int id, final int minLevel, final int maxLevel, final int rarity, final String category) {
        this.name = name;
        this.id = id;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.rarity = rarity;
        this.category = category;

        ENCHANTMENT_LIST.add(this);
    }

    public String name() {
        return this.name;
    }

    public int id() {
        return this.id;
    }

    public int minLevel() {
        return this.minLevel;
    }

    public int maxLevel() {
        return this.maxLevel;
    }

    public int rarity() {
        return this.rarity;
    }

    public String category() {
        return this.category;
    }

    public List<Enchantment_v1_8> incompatibilities() {
        return INCOMPATIBILITIES.getOrDefault(this, Collections.emptyList());
    }

}

/*
static {
    String enchantments = "";
    String incompatibility = "";
    for (Enchantment enchantment : enchantmentsList) {
        if (enchantment == null) continue;

        String actualName = getActualName(enchantment);
        enchantments += "public static final Enchantment_v1_8 ";
        enchantments += actualName;
        enchantments += " = new Enchantment_v1_8(\"";
        enchantments += actualName;
        enchantments += "\", ";
        enchantments += enchantment.effectId;
        enchantments += ", ";
        enchantments += enchantment.getMinLevel();
        enchantments += ", ";
        enchantments += enchantment.getMaxLevel();
        enchantments += ", ";
        enchantments += enchantment.weight;
        enchantments += ", \"";
        enchantments += enchantment.type.name();
        enchantments += "\");\n";

        Enchantment[] incompatibles = getIncompatibilities(enchantment);
        if (incompatibles.length != 0) {
            incompatibility += "INCOMPATIBILITIES.put(";
            incompatibility += actualName;
            incompatibility += ", Arrays.asList(";
            for (Enchantment incompatible : incompatibles) {
                incompatibility += getActualName(incompatible);
                incompatibility += ", ";
            }
            incompatibility = incompatibility.substring(0, incompatibility.length() - 2);
            incompatibility += "));\n";
        }
    }
    System.out.println(enchantments);
    System.out.println(incompatibility);
}

private static String getActualName(final Enchantment enchantment) {
    return locationEnchantments.entrySet().stream().filter(e -> e.getValue().equals(enchantment)).findFirst().get().getKey().getResourcePath();
}

private static Enchantment[] getIncompatibilities(final Enchantment ench) {
    List<Enchantment> list = new ArrayList<>();
    for (Enchantment enchantment : enchantmentsList) {
        if (enchantment == null) continue;
        if (enchantment.equals(ench)) continue;

        if (!enchantment.canApplyTogether(ench)) list.add(enchantment);
    }
    return list.toArray(new Enchantment[0]);
}
 */
