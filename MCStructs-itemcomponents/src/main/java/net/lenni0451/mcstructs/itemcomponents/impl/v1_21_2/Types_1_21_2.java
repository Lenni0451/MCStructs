package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.converter.codec.Either;
import net.lenni0451.mcstructs.converter.codec.NamedType;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;

import java.util.ArrayList;
import java.util.List;

public class Types_1_21_2 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Food {
        public static final String NUTRITION = "nutrition";
        public static final String SATURATION = "saturation";
        public static final String CAN_ALWAYS_EAT = "can_always_eat";

        private int nutrition;
        private float saturation;
        private boolean canAlwaysEat = false;

        public Food(final int nutrition, final float saturation) {
            this.nutrition = nutrition;
            this.saturation = saturation;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Consumable {
        public static final String CONSUME_SECONDS = "consume_seconds";
        public static final String ANIMATION = "animation";
        public static final String SOUND = "sound";
        public static final String HAS_CONSUME_PARTICLES = "has_consume_particles";
        public static final String ON_CONSUME_EFFECTS = "on_consume_effects";

        private float consumeSeconds = 1.6F;
        private ItemUseAnimation animation = ItemUseAnimation.EAT;
        private Either<Identifier, Types_v1_20_5.SoundEvent> sound = Either.left(Identifier.of("entity.generic.eat"));
        private boolean hasConsumeParticles = true;
        private List<Identifier> onConsumeEffects = new ArrayList<>();


        @Getter
        @AllArgsConstructor
        public enum ItemUseAnimation implements NamedType {
            NONE("none"),
            EAT("eat"),
            DRINK("drink"),
            BLOCK("block"),
            BOW("bow"),
            SPEAR("spear"),
            CROSSBOW("crossbow"),
            SPYGLASS("spyglass"),
            TOOT_HORN("toot_horn"),
            BRUSH("brush");

            private final String name;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UseCooldown {
        public static final String SECONDS = "seconds";
        public static final String COOLDOWN_GROUP = "cooldown_group";

        private float seconds;
        private Identifier cooldownGroup;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Enchantable {

        public static final String VALUE = "value";
        private int value;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Repairable {

        public static final String ITEMS = "items";
        private Types_v1_20_5.TagEntryList items;

    }

}
