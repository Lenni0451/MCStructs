package net.lenni0451.mcstructs.inventory.enchantments;

import net.lenni0451.mcstructs.items.AItemStack;

import java.util.*;
import java.util.function.Function;

public class EnchantmentRegistry<I, S extends AItemStack<I, S>> {

    private final List<Enchantment> enchantments = new ArrayList<>();
    private final Map<Enchantment, List<Enchantment>> incompatibilities = new HashMap<>();
    private final Map<Enchantment, Function<S, Boolean>> itemCompatibilities = new HashMap<>();

    public void register(final Enchantment enchantment) {
        this.enchantments.add(enchantment);
    }

    public Enchantment get(final int id) {
        return this.enchantments.stream().filter(enchantment -> enchantment.getId() == id).findFirst().orElse(null);
    }

    public Enchantment get(final String name) {
        return this.enchantments.stream().filter(enchantment -> enchantment.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public boolean isIncompatible(final Enchantment enchantment, final Enchantment other) {
        return this.incompatibilities.getOrDefault(enchantment, Collections.emptyList()).contains(other);
    }

    public boolean isItemCompatible(final Enchantment enchantment, final S itemStack) {
        return this.itemCompatibilities.getOrDefault(enchantment, stack -> false).apply(itemStack);
    }

    public void addIncompatibility(final Enchantment enchantment, final Enchantment incompatibility) {
        this.incompatibilities.computeIfAbsent(enchantment, ench -> new ArrayList<>()).add(incompatibility);
    }

    public void addItemCompatibility(final Enchantment enchantment, final Function<S, Boolean> incompatibility) {
        this.itemCompatibilities.put(enchantment, incompatibility);
    }

}
