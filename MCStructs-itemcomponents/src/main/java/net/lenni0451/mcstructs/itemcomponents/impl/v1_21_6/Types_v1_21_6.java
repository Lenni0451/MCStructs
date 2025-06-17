package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_6;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.converter.types.NamedType;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2;
import net.lenni0451.mcstructs.registry.EitherEntry;
import net.lenni0451.mcstructs.registry.RegistryEntry;
import net.lenni0451.mcstructs.registry.TagEntryList;
import net.lenni0451.mcstructs.text.TextComponent;

public class Types_v1_21_6 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttributeModifier {
        public static final String TYPE = "type";
        public static final String SLOT = "slot";
        public static final String DISPLAY = "display";

        private RegistryEntry type;
        private Types_v1_21.AttributeModifier.EntityAttribute modifier;
        private Types_v1_20_5.AttributeModifier.Slot slot = Types_v1_20_5.AttributeModifier.Slot.ANY;
        private Display display = new Display.Default();

        public AttributeModifier(final RegistryEntry type, final Types_v1_21.AttributeModifier.EntityAttribute modifier) {
            this.type = type;
            this.modifier = modifier;
        }


        public interface Display {
            Type getType();

            @Getter
            @AllArgsConstructor
            enum Type implements NamedType {
                DEFAULT("default"),
                HIDDEN("hidden"),
                OVERRIDE("override"),
                ;

                private final String name;
            }

            @Data
            @NoArgsConstructor
            class Default implements Display {
                private final Type type = Type.DEFAULT;
            }

            @Data
            @NoArgsConstructor
            class Hidden implements Display {
                private final Type type = Type.HIDDEN;
            }

            @Data
            @NoArgsConstructor
            class OverrideText implements Display {
                public static final String VALUE = "value";

                private final Type type = Type.HIDDEN;
                private TextComponent value;
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Equippable {
        public static final String SLOT = "slot";
        public static final String EQUIP_SOUND = "equip_sound";
        public static final String ASSET_ID = "asset_id";
        public static final String CAMERA_OVERLAY = "camera_overlay";
        public static final String ALLOWED_ENTITIES = "allowed_entities";
        public static final String DISPENSABLE = "dispensable";
        public static final String SWAPPABLE = "swappable";
        public static final String DAMAGE_ON_HURT = "damage_on_hurt";
        public static final String EQUIP_ON_INTERACT = "equip_on_interact";
        public static final String CAN_BE_SHEARED = "can_be_sheared";
        public static final String SHEARING_SOUND = "shearing_sound";

        private Types_v1_21_2.EquipmentSlot slot;
        private EitherEntry<Types_v1_20_5.SoundEvent> equipSound; //Default: item.armor.equip_generic
        private Identifier assetId = null;
        private Identifier cameraOverlay = null;
        private TagEntryList allowedEntities = null;
        private boolean dispensable = true;
        private boolean swappable = true;
        private boolean damageOnHurt = true;
        private boolean equipOnInteract = false;
        private boolean canBeSheared = false;
        private EitherEntry<Types_v1_20_5.SoundEvent> shearingSound = null; //Default: item.shears.snip

        public Equippable(final Types_v1_21_2.EquipmentSlot slot) {
            this.slot = slot;
        }
    }

}
