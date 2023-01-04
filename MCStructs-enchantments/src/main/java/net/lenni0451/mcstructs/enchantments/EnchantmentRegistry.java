package net.lenni0451.mcstructs.enchantments;

import net.lenni0451.mcstructs.items.AItemStack;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

/**
 * The registry for all enchantments.
 *
 * @param <I> The type of the item (e.g. Integer)
 * @param <S> The type of the item stack (e.g. LegacyItemStack)
 */
public class EnchantmentRegistry<I, S extends AItemStack<I, S>> {

    private final List<Enchantment> enchantments = new ArrayList<>();
    private final Map<Enchantment, List<Enchantment>> incompatibilities = new HashMap<>();
    private final Map<Enchantment, Function<S, Boolean>> itemCompatibilities = new HashMap<>();

    /**
     * Register a new enchantment.
     *
     * @param enchantment The enchantment to register
     */
    public void register(final Enchantment enchantment) {
        this.enchantments.add(enchantment);
    }

    /**
     * Get an enchantment by its id.
     *
     * @param id The id of the enchantment
     * @return The enchantment or null if not found
     */
    @Nullable
    public Enchantment get(final int id) {
        return this.enchantments.stream().filter(enchantment -> enchantment.getId() == id).findFirst().orElse(null);
    }

    /**
     * Get an enchantment by its name.
     *
     * @param name The name of the enchantment
     * @return The enchantment or null if not found
     */
    public Enchantment get(final String name) {
        return this.enchantments.stream().filter(enchantment -> enchantment.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * Check if two enchantments are compatible with each other.
     *
     * @param enchantment The first enchantment
     * @param other       The second enchantment
     * @return True if they are compatible
     */
    public boolean isIncompatible(final Enchantment enchantment, final Enchantment other) {
        return this.incompatibilities.getOrDefault(enchantment, Collections.emptyList()).contains(other);
    }

    /**
     * Check if an enchantment can be applied to an item.<br>
     * This method will return true if no item compatibility is set.
     *
     * @param enchantment The enchantment
     * @param itemStack   The item stack
     * @return True if the enchantment can be applied
     */
    public boolean isItemCompatible(final Enchantment enchantment, final S itemStack) {
        return this.itemCompatibilities.getOrDefault(enchantment, stack -> true).apply(itemStack);
    }

    /**
     * Add an incompatibility between two enchantments.
     *
     * @param enchantment     The first enchantment
     * @param incompatibility The second enchantment
     */
    public void addIncompatibility(final Enchantment enchantment, final Enchantment incompatibility) {
        this.incompatibilities.computeIfAbsent(enchantment, ench -> new ArrayList<>()).add(incompatibility);
    }

    /**
     * Add an item compatibility function for an enchantment.
     *
     * @param enchantment     The enchantment
     * @param incompatibility The item compatibility
     */
    public void addItemCompatibility(final Enchantment enchantment, final Function<S, Boolean> incompatibility) {
        this.itemCompatibilities.put(enchantment, incompatibility);
    }

}
