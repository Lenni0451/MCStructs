package net.lenni0451.mcstructs.itemcomponents.impl;

import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.registry.NoOpRegistry;
import net.lenni0451.mcstructs.itemcomponents.registry.Registry;

/**
 * A class that contains all the registries used in item components.<br>
 * Not every item component version will use all of these registries.
 */
public class Registries {

    public final Registry item = this.register(Identifier.of("item"));
    public final Registry block = this.register(Identifier.of("block"));
    public final Registry enchantment = this.register(Identifier.of("enchantment"));
    public final Registry statusEffect = this.register(Identifier.of("mob_effect"));
    public final Registry mapDecorationType = this.register(Identifier.of("map_decoration_type"));
    public final Registry bannerPattern = this.register(Identifier.of("banner_pattern"));
    public final Registry instrument = this.register(Identifier.of("instrument"));
    public final Registry sound = this.register(Identifier.of("sound_event"));
    public final Registry attributeModifier = this.register(Identifier.of("attribute"));
    public final Registry armorMaterial = this.register(Identifier.of("armor_material"));
    public final Registry armorTrimMaterial = this.register(Identifier.of("trim_material"));
    public final Registry armorTrimPattern = this.register(Identifier.of("trim_pattern"));
    public final Registry equipmentAsset = this.register(Identifier.of("equipment_asset"));
    public final Registry potion = this.register(Identifier.of("potion"));
    public final Registry jukeboxSong = this.register(Identifier.of("jukebox_song"));
    public final Registry entityType = this.register(Identifier.of("entity_type"));
    public final Registry damageType = this.register(Identifier.of("damage_type"));
    public final Registry wolfVariant = this.register(Identifier.of("wolf_variant"));
    public final Registry wolfSoundVariant = this.register(Identifier.of("wolf_sound_variant"));
    public final Registry frogVariant = this.register(Identifier.of("frog_variant"));
    public final Registry catVariant = this.register(Identifier.of("cat_variant"));
    public final Registry pigVariant = this.register(Identifier.of("pig_variant"));
    public final Registry cowVariant = this.register(Identifier.of("cow_variant"));
    public final Registry chickenVariant = this.register(Identifier.of("chicken_variant"));
    public final Registry paintingVariant = this.register(Identifier.of("painting_variant"));
    public final Registry dimension = this.register(Identifier.of("dimension"));

    protected Registry register(final Identifier name) {
        return new NoOpRegistry(name);
    }

}
