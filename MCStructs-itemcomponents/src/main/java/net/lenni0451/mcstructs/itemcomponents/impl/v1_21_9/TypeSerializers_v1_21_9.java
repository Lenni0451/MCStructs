package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_9;

import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.converter.model.Result;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentRegistry;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_6.TypeSerializers_v1_21_6;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5.GameProfile;

public class TypeSerializers_v1_21_9 extends TypeSerializers_v1_21_6 {

    protected static final String GAME_PROFILE = "game_profile";

    public TypeSerializers_v1_21_9(final ItemComponentRegistry registry, final TextComponentCodec textComponentCodec) {
        super(registry, textComponentCodec);
    }

    public Codec<GameProfile> gameProfile() {
        return this.init(GAME_PROFILE, () -> {
            Codec<String> playerName = Codec.sizedString(0, 16).verified(name -> {
                for (char c : name.toCharArray()) {
                    if (c <= 32 || c >= 127) {
                        return Result.error("Invalid character in player name");
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
            Codec<Map<String, List<GameProfile.Property>>> propertiesCodec = Codec.either(Codec.mapOf(Codec.STRING, Codec.STRING.listOf()), property.listOf()).map(map -> {
                List<GameProfile.Property> result = new ArrayList<>();
                for (List<GameProfile.Property> value : map.values()) {
                    result.addAll(value);
                }
                return Either.right(result);
            }, either -> either.xmap(map -> {
                Map<String, List<GameProfile.Property>> result = new HashMap<>();
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    for (String value : entry.getValue()) {
                        result.computeIfAbsent(entry.getKey(), k -> new ArrayList<>())
                                .add(new GameProfile.Property(entry.getKey(), value));
                    }
                }
                return result;
            }, properties -> {
                Map<String, List<GameProfile.Property>> result = new HashMap<>();
                for (GameProfile.Property property1 : properties) {
                    result.computeIfAbsent(property1.getName(), k -> new ArrayList<>()).add(property1);
                }
                return result;
            }));
            return Codec.either(MapCodecMerger.codec(
                    Codec.INT_ARRAY_UUID.mapCodec(GameProfile.ID).required(), GameProfile::getUuid,
                    playerName.mapCodec(GameProfile.NAME).required(), GameProfile::getName,
                    propertiesCodec.mapCodec(GameProfile.PROPERTIES).optional().defaulted(Map::isEmpty, HashMap::new), GameProfile::getProperties,
                    (uuid, name, properties) -> new GameProfile(name, uuid, properties)
            ), Codec.withAlternative(MapCodecMerger.codec(
                    playerName.mapCodec(GameProfile.NAME).optional().defaulted(null), GameProfile::getName,
                    Codec.INT_ARRAY_UUID.mapCodec(GameProfile.ID).optional().defaulted(null), GameProfile::getUuid,
                    propertiesCodec.mapCodec(GameProfile.PROPERTIES).optional().defaulted(Map::isEmpty, HashMap::new), GameProfile::getProperties,
                    GameProfile::new
            ), playerName, name -> new GameProfile(name, null, new HashMap<>()))).map(gameProfile -> {
                if (gameProfile.getName() != null && gameProfile.getUuid() != null) {
                    return Either.left(gameProfile);
                }
                return Either.right(gameProfile);
            }, Either::unwrap);
        });
    }

}
