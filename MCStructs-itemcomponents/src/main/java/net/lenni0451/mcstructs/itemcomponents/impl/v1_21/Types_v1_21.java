package net.lenni0451.mcstructs.itemcomponents.impl.v1_21;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.converter.codec.Either;
import net.lenni0451.mcstructs.converter.codec.NamedType;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.text.TextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5.ItemStack;

public class Types_v1_21 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Food {
        public static final String NUTRITION = "nutrition";
        public static final String SATURATION = "saturation";
        public static final String CAN_ALWAYS_EAT = "can_always_eat";
        public static final String EAT_SECONDS = "eat_seconds";
        public static final String USING_CONVERTS_TO = "using_converts_to";
        public static final String EFFECTS = "effects";

        private int nutrition;
        private float saturation;
        private boolean canAlwaysEat = false;
        private float eatSeconds = 1.6F;
        @Nullable
        private ItemStack usingConvertsTo;
        private List<Types_v1_20_5.Food.Effect> effects = new ArrayList<>();

        public Food(final int nutrition, final float saturation) {
            this.nutrition = nutrition;
            this.saturation = saturation;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JukeboxPlayable {
        public static final String SONG = "song";
        public static final String SHOW_IN_TOOLTIP = "show_in_tooltip";

        private Either<Identifier, JukeboxSong> song;
        private boolean showInTooltip = true;


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class JukeboxSong {
            public static final String SOUND_EVENT = "sound_event";
            public static final String DESCRIPTION = "description";
            public static final String LENGTH_IN_SECONDS = "length_in_seconds";
            public static final String COMPARATOR_OUTPUT = "comparator_output";

            private Either<Identifier, Types_v1_20_5.SoundEvent> soundEvent;
            private TextComponent description;
            private float lengthInSeconds;
            private int comparatorOutput;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttributeModifiers {
        public static final String MODIFIERS = "modifiers";
        public static final String SHOW_IN_TOOLTIP = "show_in_tooltip";

        private List<AttributeModifier> modifiers;
        private boolean showInTooltip = true;

        public AttributeModifiers(final List<AttributeModifier> modifiers) {
            this.modifiers = modifiers;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttributeModifier {
        public static final String TYPE = "type";
        public static final String SLOT = "slot";

        private Identifier type;
        private AttributeModifier.EntityAttribute modifier;
        private Types_v1_20_5.AttributeModifier.Slot slot = Types_v1_20_5.AttributeModifier.Slot.ANY;

        public AttributeModifier(final Identifier type, final AttributeModifier.EntityAttribute modifier) {
            this.type = type;
            this.modifier = modifier;
        }


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class EntityAttribute {
            public static final String ID = "id";
            public static final String AMOUNT = "amount";
            public static final String OPERATION = "operation";

            private Identifier id;
            private double amount;
            private AttributeModifier.EntityAttribute.Operation operation;


            @Getter
            @AllArgsConstructor
            public enum Operation implements NamedType {
                ADD_VALUE("add_value"),
                ADD_MULTIPLIED_BASE("add_multiplied_base"),
                ADD_MULTIPLIED_TOTAL("add_multiplied_total");

                private final String name;
            }
        }
    }

}
