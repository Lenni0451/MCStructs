package net.lenni0451.mcstructs.enchantments;

public class Enchantment {

    private final String name;
    private final int id;
    private final int minLevel;
    private final int maxLevel;
    private final int rarity;

    public Enchantment(final String name, final int id, final int minLevel, final int maxLevel, final int rarity) {
        this.name = name;
        this.id = id;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.rarity = rarity;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public int getMinLevel() {
        return this.minLevel;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public int getRarity() {
        return this.rarity;
    }

}
