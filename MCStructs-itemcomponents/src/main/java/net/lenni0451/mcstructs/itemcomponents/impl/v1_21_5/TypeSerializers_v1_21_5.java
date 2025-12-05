package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_4.TypeSerializers_v1_21_4;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5.Types_v1_21_5.ArmorTrimMaterial;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5.Types_v1_21_5.ArmorTrimPattern;
import net.lenni0451.mcstructs.registry.Holder;
import net.lenni0451.mcstructs.registry.RegistryEntry;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.HashMap;
import java.util.Map;

public class TypeSerializers_v1_21_5 extends TypeSerializers_v1_21_4 {

    public TypeSerializers_v1_21_5(final ItemComponentRegistry registry, final TextComponentCodec textComponentCodec) {
        super(registry, textComponentCodec);
    }

    @Override
    public Codec<TextComponent> textComponent(int maxLength) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Codec<Map<RegistryEntry, Integer>> enchantmentLevels() {
        return this.init(ENCHANTMENT_LEVELS, () -> Codec.mapOf(registry.getRegistries().enchantment.entryCodec(), Codec.rangedInt(1, 255)));
    }

    @Override
    @Deprecated
    public Codec<Holder<Types_v1_20_5.ArmorTrimMaterial>> armorTrimMaterial() {
        throw new UnsupportedOperationException();
    }

    public Codec<Holder<ArmorTrimMaterial>> armorTrimMaterial_v1_21_5() {
        return this.init(ARMOR_TRIM_MATERIAL, () -> Holder.fileCodec(
                this.registry.getRegistries().armorTrimMaterial,
                MapCodecMerger.codec(
                        Codec.STRING_IDENTIFIER_PATH.mapCodec(ArmorTrimMaterial.ASSET_NAME).required(), ArmorTrimMaterial::getAssetName,
                        Codec.mapOf(this.registry.getRegistries().equipmentAsset.entryCodec(), Codec.STRING_IDENTIFIER_PATH).mapCodec(ArmorTrimMaterial.OVERRIDE_ARMOR_ASSETS).optional().defaulted(Map::isEmpty, HashMap::new), ArmorTrimMaterial::getOverrideArmorAssets,
                        this.textComponentCodec.getTextCodec().mapCodec(ArmorTrimMaterial.DESCRIPTION).required(), ArmorTrimMaterial::getDescription,
                        ArmorTrimMaterial::new
                )
        ));
    }

    @Override
    @Deprecated
    public Codec<Holder<Types_v1_20_5.ArmorTrimPattern>> armorTrimPattern() {
        throw new UnsupportedOperationException();
    }

    public Codec<Holder<ArmorTrimPattern>> armorTrimPattern_v1_21_5() {
        return this.init(ARMOR_TRIM_PATTERN, () -> Holder.fileCodec(
                this.registry.getRegistries().armorTrimPattern,
                MapCodecMerger.codec(
                        Codec.STRING_IDENTIFIER.mapCodec(ArmorTrimPattern.ASSET_ID).required(), ArmorTrimPattern::getAssetId,
                        this.textComponentCodec.getTextCodec().mapCodec(ArmorTrimPattern.DESCRIPTION).required(), ArmorTrimPattern::getDescription,
                        Codec.BOOLEAN.mapCodec(ArmorTrimPattern.DECAL).optional().elseGet(() -> false), ArmorTrimPattern::isDecal,
                        ArmorTrimPattern::new
                )
        ));
    }

}
