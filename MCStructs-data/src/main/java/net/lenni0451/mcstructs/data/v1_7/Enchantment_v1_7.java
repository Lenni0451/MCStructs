package net.lenni0451.mcstructs.data.v1_7;

import java.util.*;

public class Enchantment_v1_7 {

    public static final List<Enchantment_v1_7> ENCHANTMENT_LIST = new ArrayList<>();
    public static final Map<Enchantment_v1_7, List<Enchantment_v1_7>> INCOMPATIBILITIES = new HashMap<>();

    public static final Enchantment_v1_7 protection = new Enchantment_v1_7("protection", 0, 1, 4, 10, "armor");
    public static final Enchantment_v1_7 fire_protection = new Enchantment_v1_7("fire_protection", 1, 1, 4, 5, "armor");
    public static final Enchantment_v1_7 feather_falling = new Enchantment_v1_7("feather_falling", 2, 1, 4, 5, "armor_feet");
    public static final Enchantment_v1_7 blast_protection = new Enchantment_v1_7("blast_protection", 3, 1, 4, 2, "armor");
    public static final Enchantment_v1_7 projectile_protection = new Enchantment_v1_7("projectile_protection", 4, 1, 4, 5, "armor");
    public static final Enchantment_v1_7 respiration = new Enchantment_v1_7("respiration", 5, 1, 3, 2, "armor_head");
    public static final Enchantment_v1_7 aqua_affinity = new Enchantment_v1_7("aqua_affinity", 6, 1, 1, 2, "armor_head");
    public static final Enchantment_v1_7 thorns = new Enchantment_v1_7("thorns", 7, 1, 3, 1, "armor_torso");
    public static final Enchantment_v1_7 sharpness = new Enchantment_v1_7("sharpness", 16, 1, 5, 10, "weapon");
    public static final Enchantment_v1_7 smite = new Enchantment_v1_7("smite", 17, 1, 5, 5, "weapon");
    public static final Enchantment_v1_7 bane_of_arthropods = new Enchantment_v1_7("bane_of_arthropods", 18, 1, 5, 5, "weapon");
    public static final Enchantment_v1_7 knockback = new Enchantment_v1_7("knockback", 19, 1, 2, 5, "weapon");
    public static final Enchantment_v1_7 fire_aspect = new Enchantment_v1_7("fire_aspect", 20, 1, 2, 2, "weapon");
    public static final Enchantment_v1_7 looting = new Enchantment_v1_7("looting", 21, 1, 3, 2, "weapon");
    public static final Enchantment_v1_7 efficiency = new Enchantment_v1_7("efficiency", 32, 1, 5, 10, "digger");
    public static final Enchantment_v1_7 silk_touch = new Enchantment_v1_7("silk_touch", 33, 1, 1, 1, "digger");
    public static final Enchantment_v1_7 unbreaking = new Enchantment_v1_7("unbreaking", 34, 1, 3, 5, "breakable");
    public static final Enchantment_v1_7 fortune = new Enchantment_v1_7("fortune", 35, 1, 3, 2, "digger");
    public static final Enchantment_v1_7 power = new Enchantment_v1_7("power", 48, 1, 5, 10, "bow");
    public static final Enchantment_v1_7 punch = new Enchantment_v1_7("punch", 49, 1, 2, 2, "bow");
    public static final Enchantment_v1_7 flame = new Enchantment_v1_7("flame", 50, 1, 1, 2, "bow");
    public static final Enchantment_v1_7 infinity = new Enchantment_v1_7("infinity", 51, 1, 1, 1, "bow");
    public static final Enchantment_v1_7 luck_of_the_sea = new Enchantment_v1_7("luck_of_the_sea", 61, 1, 3, 2, "fishing_rod");
    public static final Enchantment_v1_7 lure = new Enchantment_v1_7("lure", 62, 1, 3, 2, "fishing_rod");

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

    private Enchantment_v1_7(final String name, final int id, final int minLevel, final int maxLevel, final int rarity, final String category) {
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

    public List<Enchantment_v1_7> incompatibilities() {
        return INCOMPATIBILITIES.getOrDefault(this, Collections.emptyList());
    }

}

/*
static {
    String enchantments = "";
    String incompatibility = "";
    for (Enchantment enchantment : enchantmentsList) {
        if (enchantment == null) continue;

        String actualName = getActualName(enchantment.effectId);
        enchantments += "public static final Enchantment_v1_7 ";
        enchantments += actualName;
        enchantments += " = new Enchantment_v1_7(\"";
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
                incompatibility += getActualName(incompatible.effectId);
                incompatibility += ", ";
            }
            incompatibility = incompatibility.substring(0, incompatibility.length() - 2);
            incompatibility += "));\n";
        }
    }
    System.out.println(enchantments);
    System.out.println(incompatibility);
}

private static String getActualName(final int id) {
    switch (id) {
        case 0: return "protection";
        case 1: return "fire_protection";
        case 2: return "feather_falling";
        case 3: return "blast_protection";
        case 4: return "projectile_protection";
        case 5: return "respiration";
        case 6: return "aqua_affinity";
        case 7: return "thorns";
        case 16: return "sharpness";
        case 17: return "smite";
        case 18: return "bane_of_arthropods";
        case 19: return "knockback";
        case 20: return "fire_aspect";
        case 21: return "looting";
        case 32: return "efficiency";
        case 33: return "silk_touch";
        case 34: return "unbreaking";
        case 35: return "fortune";
        case 48: return "power";
        case 49: return "punch";
        case 50: return "flame";
        case 51: return "infinity";
        case 61: return "luck_of_the_sea";
        case 62: return "lure";
        default: throw new IllegalArgumentException("Unknown Enchantment ID: " + id);
    }
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
