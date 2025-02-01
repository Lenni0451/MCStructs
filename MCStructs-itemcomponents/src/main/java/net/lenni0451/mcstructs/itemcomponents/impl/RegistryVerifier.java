package net.lenni0451.mcstructs.itemcomponents.impl;

import net.lenni0451.mcstructs.converter.model.Result;
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
    public final Checker<Identifier> enchantmentTag = new Checker<>("enchantment tag", this::verifyEnchantmentTag);
    public final Checker<Identifier> enchantment = new Checker<>("enchantment", this::verifyEnchantment);
    public final Checker<Identifier> statusEffectTag = new Checker<>("status effect", this::verifyStatusEffectTag);
    public final Checker<Identifier> statusEffect = new Checker<>("status effect", this::verifyStatusEffect);
    public final Checker<Identifier> mapDecorationType = new Checker<>("map decoration type", this::verifyMapDecorationType);
    public final Checker<Identifier> bannerPatternTag = new Checker<>("banner pattern tag", this::verifyBannerPatternTag);
    public final Checker<Identifier> bannerPattern = new Checker<>("banner pattern", this::verifyBannerPattern);
    public final Checker<Identifier> instrument = new Checker<>("instrument", this::verifyInstrument);
    public final Checker<Identifier> sound = new Checker<>("sound", this::verifySound);
    public final Checker<Identifier> attributeModifierTag = new Checker<>("attribute modifier tag", this::verifyAttributeModifierTag);
    public final Checker<Identifier> attributeModifier = new Checker<>("attribute modifier", this::verifyAttributeModifier);
    public final Checker<Identifier> armorMaterial = new Checker<>("armor material", this::verifyArmorMaterial);
    public final Checker<Identifier> armorTrimMaterialTag = new Checker<>("armor trim material tag", this::verifyArmorTrimMaterialTag);
    public final Checker<Identifier> armorTrimMaterial = new Checker<>("armor trim material", this::verifyArmorTrimMaterial);
    public final Checker<Identifier> armorTrimPatternTag = new Checker<>("armor trim pattern tag", this::verifyArmorTrimPatternTag);
    public final Checker<Identifier> armorTrimPattern = new Checker<>("armor trim pattern", this::verifyArmorTrimPattern);
    public final Checker<Identifier> potionTag = new Checker<>("potion tag", this::verifyPotionTag);
    public final Checker<Identifier> potion = new Checker<>("potion", this::verifyPotion);
    public final Checker<Identifier> jukeboxSongTag = new Checker<>("jukebox song tag", this::verifyJukeboxSongTag);
    public final Checker<Identifier> jukeboxSong = new Checker<>("jukebox song", this::verifyJukeboxSong);
    public final Checker<Identifier> entityTypeTag = new Checker<>("entity type tag", this::verifyEntityTypeTag);
    public final Checker<Identifier> entityType = new Checker<>("entity type", this::verifyEntityType);
    public final Checker<Identifier> damageTypeTag = new Checker<>("damage type tag", this::verifyDamageTypeTag);
    public final Checker<Identifier> damageType = new Checker<>("damage type", this::verifyDamageType);
    public final Checker<Identifier> wolfVariant = new Checker<>("wolf variant", this::verifyWolfVariant);
    public final Checker<Identifier> biomeTag = new Checker<>("biome tag", this::verifyBiomeTag);
    public final Checker<Identifier> biome = new Checker<>("biome", this::verifyBiome);
    public final Checker<Identifier> frogVariant = new Checker<>("frog variant", this::verifyFrogVariant);
    public final Checker<Identifier> catVariant = new Checker<>("cat variant", this::verifyCatVariant);
    public final Checker<Identifier> pigVariant = new Checker<>("pig variant", this::verifyPigVariant);
    public final Checker<Identifier> paintingVariant = new Checker<>("painting variant", this::verifyPaintingVariant);
    public final Checker<Identifier> spawnConditionType = new Checker<>("spawn condition type", this::verifySpawnConditionType);

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
     * Verify the enchantment tag in the enchantment tag registry.
     *
     * @param id The enchantment tag id
     * @return If the enchantment tag is valid
     */
    public boolean verifyEnchantmentTag(final Identifier id) {
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
     * Verify the status effect tag in the status effect registry.
     *
     * @param id The status effect tag id
     * @return If the status effect tag is valid
     */
    public boolean verifyStatusEffectTag(final Identifier id) {
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
     * Verify the banner pattern tag in the banner pattern tag registry.
     *
     * @param id The banner pattern tag id
     * @return If the banner pattern tag is valid
     */
    public boolean verifyBannerPatternTag(final Identifier id) {
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
     * Verify the attribute modifier tag in the attribute modifier tag registry.
     *
     * @param id The attribute modifier tag id
     * @return If the attribute modifier tag is valid
     */
    public boolean verifyAttributeModifierTag(final Identifier id) {
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
     * Verify the armor trim material tag in the armor trim material tag registry.
     *
     * @param id The armor trim material tag id
     * @return If the armor trim material tag is valid
     */
    public boolean verifyArmorTrimMaterialTag(final Identifier id) {
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
     * Verify the armor trim pattern tag in the armor trim pattern tag registry.
     *
     * @param id The armor trim pattern tag id
     * @return If the armor trim pattern tag is valid
     */
    public boolean verifyArmorTrimPatternTag(final Identifier id) {
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
     * Verify the potion tag in the potion tag registry.
     *
     * @param id The potion tag id
     * @return If the potion tag is valid
     */
    public boolean verifyPotionTag(final Identifier id) {
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
     * Verify the jukebox song tag in the jukebox song tag registry.
     *
     * @param id The jukebox song tag id
     * @return If the jukebox song tag is valid
     */
    public boolean verifyJukeboxSongTag(final Identifier id) {
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
     * Verify the damage type in the damage type registry.
     *
     * @param id The damage type id
     * @return If the damage type is valid
     */
    public boolean verifyDamageType(final Identifier id) {
        return true;
    }

    /**
     * Verify the wolf variant in the wolf variant registry.
     *
     * @param id The wolf variant id
     * @return If the variant type is valid
     */
    public boolean verifyWolfVariant(final Identifier id) {
        return true;
    }

    /**
     * Verify the biome tag in the biome tag registry.
     *
     * @param id The biome tag id
     * @return If the biome tag is valid
     */
    public boolean verifyBiomeTag(final Identifier id) {
        return true;
    }

    /**
     * Verify the biome in the biome registry.
     *
     * @param id The biome id
     * @return If the biome is valid
     */
    public boolean verifyBiome(final Identifier id) {
        return true;
    }

    /**
     * Verify the frog variant in the frog variant registry.
     *
     * @param id The frog variant id
     * @return If the frog variant is valid
     */
    public boolean verifyFrogVariant(final Identifier id) {
        return true;
    }

    /**
     * Verify the cat variant in the cat variant registry.
     *
     * @param id The cat variant id
     * @return If the cat variant is valid
     */
    public boolean verifyCatVariant(final Identifier id) {
        return true;
    }

    /**
     * Verify the pig variant in the pig variant registry.
     *
     * @param id The pig variant id
     * @return If the pig variant is valid
     */
    public boolean verifyPigVariant(final Identifier id) {
        return true;
    }

    /**
     * Verify the painting variant in the painting variant registry.
     *
     * @param id The painting variant id
     * @return If the painting variant is valid
     */
    public boolean verifyPaintingVariant(final Identifier id) {
        return true;
    }

    /**
     * Verify the spawn condition type in the spawn condition type registry.
     *
     * @param id The spawn condition type id
     * @return If the spawn condition type is valid
     */
    public boolean verifySpawnConditionType(final Identifier id) {
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
