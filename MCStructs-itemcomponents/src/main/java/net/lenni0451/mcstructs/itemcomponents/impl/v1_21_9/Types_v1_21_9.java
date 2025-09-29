package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_9;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.converter.types.NamedType;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;

public class Types_v1_21_9 {

    @Data
    public static class ResourceTexture {
        private Identifier id;
        private Identifier texturePath;

        public ResourceTexture(final Identifier id) {
            this.id = id;
            this.texturePath = Identifier.of(id.getKey(), "textures/" + id.getValue() + ".png");
        }
    }

    @Getter
    @AllArgsConstructor
    public enum PlayerModelType implements NamedType {
        SLIM("slim"),
        WIDE("wide");

        private final String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlayerSkinPatch {
        public static final String TEXTURE = "texture";
        public static final String CAPE = "cape";
        public static final String ELYTRA = "elytra";
        public static final String MODEL = "model";

        private ResourceTexture texture;
        private ResourceTexture cape;
        private ResourceTexture elytra;
        private PlayerModelType model;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResolvableProfile {
        private Types_v1_20_5.GameProfile profile;
        private PlayerSkinPatch skinPatch;
    }

}
