package net.lenni0451.mcstructs.itemcomponents.impl.v1_21;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.converter.codec.Either;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.text.ATextComponent;

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
            private ATextComponent description;
            private float lengthInSeconds;
            private int comparatorOutput;
        }
    }

}
