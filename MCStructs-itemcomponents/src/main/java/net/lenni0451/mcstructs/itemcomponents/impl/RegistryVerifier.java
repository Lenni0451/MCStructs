package net.lenni0451.mcstructs.itemcomponents.impl;

import net.lenni0451.mcstructs.converter.Result;
import net.lenni0451.mcstructs.core.Identifier;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A verifier for all registry values that can be used in item components.<br>
 * The default implementation returns always true, you can override the methods to ensure that the deserialized values are valid.
 */
public class RegistryVerifier {

    public final Checker<Identifier> itemTag = new Checker<>("item tag", this::verifyItemTag);
    public final Checker<Identifier> item = new Checker<>("item", this::verifyItem);
    public final Checker<Identifier> blockTag = new Checker<>("block tag", this::verifyBlockTag);
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
    public final Checker<Identifier> potion = new Checker<>("potion", this::verifyPotion);
    public final Checker<Identifier> jukeboxSong = new Checker<>("jukebox song", this::verifyJukeboxSong);
    public final Checker<Identifier> consumeEffect = new Checker<>("consume effect", this::verifyConsumeEffect);
    public final Checker<Identifier> entityTypeTag = new Checker<>("entity type tag", this::verifyItemTag);
    public final Checker<Identifier> entityType = new Checker<>("entity type", this::verifyEntityType);
    public final Checker<Identifier> damageTypeTag = new Checker<>("damage type tag", this::verifyDamageTypeTag);

    /**
     * Verify the item tag in the item tag registry.
     *
     * @param id The item tag id
     * @return If the item tag is valid
     */
    public boolean verifyItemTag(final Identifier id) {
        return true;
    }

    /**
     * Verify the item in the item registry.
     *
     * @param id The item id
     * @return If the item is valid
     */
    public boolean verifyItem(final Identifier id) {
        return true;
    }

    /**
     * Verify the block tag in the block tag registry.
     *
     * @param id The block tag id
     * @return If the block tag is valid
     */
    public boolean verifyBlockTag(final Identifier id) {
        return true;
    }

    /**
     * Verify the block in the block registry.
     *
     * @param id The block id
     * @return If the block is valid
     */
    public boolean verifyBlock(final Identifier id) {
        return true;
    }

    /**
     * Verify the block state of a block.
     *
     * @param id    The block id
     * @param state The block state
     * @return If the block state is valid
     */
    public boolean verifyBlockState(final Identifier id, final String state) {
        return true;
    }

    /**
     * Verify the enchantment in the enchantment registry.
     *
     * @param id The enchantment id
     * @return If the enchantment is valid
     */
    public boolean verifyEnchantment(final Identifier id) {
        return true;
    }

    /**
     * Verify the status effect in the status effect registry.
     *
     * @param id The status effect id
     * @return If the status effect is valid
     */
    public boolean verifyStatusEffect(final Identifier id) {
        return true;
    }

    /**
     * Verify the map decoration type in the map decoration type registry.
     *
     * @param id The map decoration type id
     * @return If the map decoration type is valid
     */
    public boolean verifyMapDecorationType(final Identifier id) {
        return true;
    }

    /**
     * Verify the banner pattern in the banner pattern registry.
     *
     * @param id The banner pattern id
     * @return If the banner pattern is valid
     */
    public boolean verifyBannerPattern(final Identifier id) {
        return true;
    }

    /**
     * Verify the instrument in the instrument registry.
     *
     * @param id The instrument id
     * @return If the instrument is valid
     */
    public boolean verifyInstrument(final Identifier id) {
        return true;
    }

    /**
     * Verify the sound in the sound registry.
     *
     * @param id The sound id
     * @return If the sound is valid
     */
    public boolean verifySound(final Identifier id) {
        return true;
    }

    /**
     * Verify the attribute modifier in the attribute modifier registry.
     *
     * @param id The attribute modifier id
     * @return If the attribute modifier is valid
     */
    public boolean verifyAttributeModifier(final Identifier id) {
        return true;
    }

    /**
     * Verify the armor material in the armor material registry.
     *
     * @param id The armor material id
     * @return If the armor material is valid
     */
    public boolean verifyArmorMaterial(final Identifier id) {
        return true;
    }

    /**
     * Verify the armor trim material in the armor trim material registry.
     *
     * @param id The armor trim material id
     * @return If the armor trim material is valid
     */
    public boolean verifyArmorTrimMaterial(final Identifier id) {
        return true;
    }

    /**
     * Verify the armor trim pattern in the armor trim pattern registry.
     *
     * @param id The armor trim pattern id
     * @return If the armor trim pattern is valid
     */
    public boolean verifyArmorTrimPattern(final Identifier id) {
        return true;
    }

    /**
     * Verify the potion in the potion registry.
     *
     * @param id The potion id
     * @return If the potion is valid
     */
    public boolean verifyPotion(final Identifier id) {
        return true;
    }

    /**
     * Verify the song in the jukebox song registry.
     *
     * @param id The song id
     * @return If the song is valid
     */
    public boolean verifyJukeboxSong(final Identifier id) {
        return true;
    }

    /**
     * Verify the consume effect in the consume effect registry.
     *
     * @param id The consume effect id
     * @return If the consume effect is valid
     */
    public boolean verifyConsumeEffect(final Identifier id) {
        return true;
    }

    /**
     * Verify the entity type tag in the entity type tag registry.
     *
     * @param id The entity type tag id
     * @return If the entity type tag is valid
     */
    public boolean verifyEntityTypeTag(final Identifier id) {
        return true;
    }

    /**
     * Verify the entity type in the entity type registry.
     *
     * @param id The entity type id
     * @return If the entity type is valid
     */
    public boolean verifyEntityType(final Identifier id) {
        return true;
    }

    /**
     * Verify the damage type tag in the damage type tag registry.
     *
     * @param id The damage type tag id
     * @return If the damage type tag is valid
     */
    public boolean verifyDamageTypeTag(final Identifier id) {
        return true;
    }


    /**
     * A util class ensuring human readable error messages for invalid values.
     *
     * @param <T> The type of the value to check
     */
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
    }

}
