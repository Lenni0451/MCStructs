package net.lenni0451.mcstructs.enchantments;

import javax.annotation.Nonnull;

/**
 * The wrapper class for enchantments.
 */
public class Enchantment {

    private final String name;
    private final int id;
    private final int minLevel;
    private final int maxLevel;
    private final int rarity;

    public Enchantment(@Nonnull final String name, final int id, final int minLevel, final int maxLevel, final int rarity) {
        this.name = name;
        this.id = id;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.rarity = rarity;
    }

    /**
     * @return The name of the enchantment
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return The id of the enchantment
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return The minimum level of the enchantment
     */
    public int getMinLevel() {
        return this.minLevel;
    }

    /**
     * @return The maximum level of the enchantment
     */
    public int getMaxLevel() {
        return this.maxLevel;
    }

    /**
     * @return The rarity of the enchantment
     */
    public int getRarity() {
        return this.rarity;
    }

}
