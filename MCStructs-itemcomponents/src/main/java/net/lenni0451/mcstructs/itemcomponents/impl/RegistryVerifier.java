package net.lenni0451.mcstructs.itemcomponents.impl;

import net.lenni0451.mcstructs.core.Identifier;

import java.util.function.Function;
import java.util.function.Predicate;

public class RegistryVerifier {

    public final Checker<Identifier> item = new Checker<>("item", this::verifyItem);
    public final Checker<Identifier> enchantment = new Checker<>("enchantment", this::verifyEnchantment);
    public final Checker<Identifier> statusEffect = new Checker<>("status effect", this::verifyStatusEffect);
    public final Checker<Identifier> mapDecorationType = new Checker<>("map decoration type", this::verifyMapDecorationType);
    public final Checker<Identifier> bannerPattern = new Checker<>("banner pattern", this::verifyBannerPattern);

    public boolean verifyItem(final Identifier id) {
        return true;
    }

    public boolean verifyEnchantment(final Identifier id) {
        return true;
    }

    public boolean verifyStatusEffect(final Identifier id) {
        return true;
    }

    public boolean verifyMapDecorationType(final Identifier id) {
        return true;
    }

    public boolean verifyBannerPattern(final Identifier id) {
        return true;
    }


    public static class Checker<T> {
        private final String name;
        private final Predicate<T> predicate;

        private Checker(final String name, final Predicate<T> predicate) {
            this.name = name;
            this.predicate = predicate;
        }

        public void verify(final T t) {
            if (!this.predicate.test(t)) throw new IllegalArgumentException("Invalid " + this.name + " value: " + t);
        }

        public <N> Checker<N> map(final Function<N, T> mapper) {
            return new Checker<>(this.name, t -> this.predicate.test(mapper.apply(t)));
        }
    }

}
