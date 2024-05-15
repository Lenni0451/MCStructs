package net.lenni0451.mcstructs.itemcomponents.impl;

import net.lenni0451.mcstructs.converter.Result;
import net.lenni0451.mcstructs.core.Identifier;

import java.util.function.Function;
import java.util.function.Predicate;

public class RegistryVerifier {

    public final Checker<Identifier> item = new Checker<>("item", this::verifyItem);
    public final Checker<Identifier> block = new Checker<>("block", this::verifyBlock);
    public final Checker<Identifier> enchantment = new Checker<>("enchantment", this::verifyEnchantment);
    public final Checker<Identifier> statusEffect = new Checker<>("status effect", this::verifyStatusEffect);
    public final Checker<Identifier> mapDecorationType = new Checker<>("map decoration type", this::verifyMapDecorationType);
    public final Checker<Identifier> bannerPattern = new Checker<>("banner pattern", this::verifyBannerPattern);
    public final Checker<Identifier> instrument = new Checker<>("instrument", this::verifyInstrument);
    public final Checker<Identifier> sound = new Checker<>("sound", this::verifySound);
    public final Checker<Identifier> attributeModifier = new Checker<>("attribute modifier", this::verifyAttributeModifier);
    public final Checker<Identifier> armorMaterial = new Checker<>("armor material", this::verifyArmorMaterial);
    public final Checker<Identifier> armorTrimMaterial = new Checker<>("armor trim material", this::verifyArmorTrimMaterial);
    public final Checker<Identifier> armorTrimPattern = new Checker<>("armor trim pattern", this::verifyArmorTrimPattern);

    public boolean verifyItem(final Identifier id) {
        return true;
    }

    public boolean verifyBlock(final Identifier id) {
        return true;
    }

    public boolean verifyBlockState(final Identifier id, final String state) {
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

    public boolean verifyInstrument(final Identifier id) {
        return true;
    }

    public boolean verifySound(final Identifier id) {
        return true;
    }

    public boolean verifyAttributeModifier(final Identifier id) {
        return true;
    }

    public boolean verifyArmorMaterial(final Identifier id) {
        return true;
    }

    public boolean verifyArmorTrimMaterial(final Identifier id) {
        return true;
    }

    public boolean verifyArmorTrimPattern(final Identifier id) {
        return true;
    }


    public static class Checker<T> implements Function<T, Result<Void>> {
        private final String name;
        private final Predicate<T> predicate;

        private Checker(final String name, final Predicate<T> predicate) {
            this.name = name;
            this.predicate = predicate;
        }

        @Override
        public Result<Void> apply(T t) {
            if (!this.predicate.test(t)) Result.error("Invalid " + this.name + " value: " + t);
            return Result.success(null);
        }

        public <N> Checker<N> map(final Function<N, T> mapper) {
            return new Checker<>(this.name, t -> this.predicate.test(mapper.apply(t)));
        }
    }

}
