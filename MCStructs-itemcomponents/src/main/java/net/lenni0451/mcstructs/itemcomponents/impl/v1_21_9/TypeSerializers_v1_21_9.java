package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_9;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_6.TypeSerializers_v1_21_6;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_9.Types_v1_21_9.PlayerModelType;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_9.Types_v1_21_9.PlayerSkinPatch;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_9.Types_v1_21_9.ResolvableProfile;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_9.Types_v1_21_9.ResourceTexture;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5.GameProfile;

public class TypeSerializers_v1_21_9 extends TypeSerializers_v1_21_6 {

    protected static final String RESOLVABLE_PROFILE = "resolvable_profile";

    public TypeSerializers_v1_21_9(final ItemComponentRegistry registry, final TextComponentCodec textComponentCodec) {
        super(registry, textComponentCodec);
    }

    public Codec<ResolvableProfile> resolvableProfile() {
        return this.init(RESOLVABLE_PROFILE, () -> {
            Codec<String> playerName = Codec.sizedString(0, 16)
                    .verified(name -> {
                        for (char c : name.toCharArray()) {
                            if (c <= 32 || c >= 127) {
                                return Result.error("Illegal character in player name: " + c);
                            }
                        }
                        return null;
                    });
            Codec<GameProfile.Property> property = MapCodecMerger.codec(
                    Codec.STRING.mapCodec(GameProfile.Property.NAME).required(), GameProfile.Property::getName,
                    Codec.STRING.mapCodec(GameProfile.Property.VALUE).required(), GameProfile.Property::getValue,
                    Codec.STRING.mapCodec(GameProfile.Property.SIGNATURE).optional().lenient().defaulted(null), GameProfile.Property::getSignature,
                    GameProfile.Property::new
            );
            Codec<Map<String, List<GameProfile.Property>>> propertyMap = Codec.either(Codec.mapOf(Codec.STRING, Codec.STRING.listOf()), property.listOf()).map(
                    map -> {
                        List<GameProfile.Property> properties = new ArrayList<>();
                        map.values().forEach(properties::addAll);
                        return Either.right(properties);
                    },
                    either -> either.xmap(
                            map -> {
                                Map<String, List<GameProfile.Property>> properties = new HashMap<>();
                                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                                    List<GameProfile.Property> props = new ArrayList<>();
                                    for (String value : entry.getValue()) {
                                        props.add(new GameProfile.Property(entry.getKey(), value, null));
                                    }
                                    properties.put(entry.getKey(), props);
                                }
                                return properties;
                            },
                            properties -> {
                                Map<String, List<GameProfile.Property>> map = new HashMap<>();
                                for (GameProfile.Property prop : properties) {
                                    map.computeIfAbsent(prop.getName(), k -> new ArrayList<>()).add(prop);
                                }
                                return map;
                            }
                    )
            );
            MapCodec<GameProfile> fullProfile = MapCodecMerger.mapCodec(
                    Codec.INT_ARRAY_UUID.mapCodec(GameProfile.ID).required(), GameProfile::getUuid,
                    playerName.mapCodec(GameProfile.NAME).required(), GameProfile::getName,
                    propertyMap.mapCodec(GameProfile.PROPERTIES).optional().defaulted(Map::isEmpty, HashMap::new), GameProfile::getProperties,
                    (uuid, name, properties) -> new GameProfile(name, uuid, properties)
            );
            MapCodec<GameProfile> partialProfile = MapCodecMerger.mapCodec(
                    playerName.mapCodec(GameProfile.NAME).optional().defaulted(null), GameProfile::getName,
                    Codec.INT_ARRAY_UUID.mapCodec(GameProfile.ID).optional().defaulted(null), GameProfile::getUuid,
                    propertyMap.mapCodec(GameProfile.PROPERTIES).optional().defaulted(Map::isEmpty, HashMap::new), GameProfile::getProperties,
                    GameProfile::new
            );
            Codec<ResourceTexture> resourceTexture = Codec.STRING_IDENTIFIER.map(ResourceTexture::getId, ResourceTexture::new);
            MapCodec<PlayerSkinPatch> skinPatch = MapCodecMerger.mapCodec(
                    resourceTexture.mapCodec(PlayerSkinPatch.TEXTURE).optional().defaulted(null), PlayerSkinPatch::getBody,
                    resourceTexture.mapCodec(PlayerSkinPatch.CAPE).optional().defaulted(null), PlayerSkinPatch::getCape,
                    resourceTexture.mapCodec(PlayerSkinPatch.ELYTRA).optional().defaulted(null), PlayerSkinPatch::getElytra,
                    Codec.named(PlayerModelType.values()).mapCodec(PlayerSkinPatch.MODEL).optional().defaulted(null), PlayerSkinPatch::getModel,
                    PlayerSkinPatch::new
            );
            return MapCodecMerger.codec(
                    MapCodec.either(fullProfile, partialProfile).map(gameProfile -> {
                        if (gameProfile.getName() != null && gameProfile.getUuid() != null) {
                            return Either.left(gameProfile);
                        } else {
                            return Either.right(gameProfile);
                        }
                    }, Either::unwrap), ResolvableProfile::getProfile,
                    skinPatch, ResolvableProfile::getSkinPatch,
                    ResolvableProfile::new
            );
        });
    }

}
