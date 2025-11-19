package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_11;

import lombok.*;
import net.lenni0451.mcstructs.converter.types.NamedType;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2.Types_v1_21_2;
import net.lenni0451.mcstructs.registry.EitherEntry;

import java.util.ArrayList;
import java.util.List;

public class Types_v1_21_11 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UseEffects {
        public static final String CAN_SPRINT = "can_sprint";
        public static final String INTERACT_VIBRATIONS = "interact_vibrations";
        public static final String SPEED_MULTIPLIER = "speed_multiplier";

        private boolean canSprint = false;
        private boolean interactVibrations = true;
        private float speedMultiplier = 0.2F;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DamageType {
        public static final String MESSAGE_ID = "message_id";
        public static final String SCALING = "scaling";
        public static final String EXHAUSTION = "exhaustion";
        public static final String EFFECTS = "effects";
        public static final String DEATH_MESSAGE_TYPE = "death_message_type";

        private String messageId;
        private DamageScaling scaling;
        private float exhaustion;
        private DamageEffects effects = DamageEffects.HURT;
        private DeathMessageType deathMessageType = DeathMessageType.DEFAULT;

        public DamageType(final String messageId, final DamageScaling scaling, final float exhaustion) {
            this.messageId = messageId;
            this.scaling = scaling;
            this.exhaustion = exhaustion;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum DamageScaling implements NamedType {
        NEVER("never"),
        WHEN_CAUSED_BY_LIVING_NON_PLAYER("when_caused_by_living_non_player"),
        ALWAYS("always");

        private final String name;
    }

    @Getter
    @RequiredArgsConstructor
    public enum DamageEffects implements NamedType {
        HURT("hurt"),
        THORNS("thorns"),
        DROWNING("drowning"),
        BURNING("burning"),
        POKING("poking"),
        FREEZING("freezing");

        private final String name;
    }

    @Getter
    @RequiredArgsConstructor
    public enum DeathMessageType implements NamedType {
        DEFAULT("default"),
        FALL_VARIANTS("fall_variants"),
        INTENTIONAL_GAME_DESIGN("intentional_game_design");

        private final String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KineticWeapon {
        public static final String CONTACT_COOLDOWN_TICKS = "contact_cooldown_ticks";
        public static final String DELAY_TICKS = "delay_ticks";
        public static final String DISMOUNT_CONDITIONS = "dismount_conditions";
        public static final String KNOCKBACK_CONDITIONS = "knockback_conditions";
        public static final String DAMAGE_CONDITIONS = "damage_conditions";
        public static final String FORWARD_MOVEMENT = "forward_movement";
        public static final String DAMAGE_MULTIPLIER = "damage_multiplier";
        public static final String SOUND = "sound";
        public static final String HIT_SOUND = "hit_sound";

        private int contactCooldownTicks = 10;
        private int delayTicks = 0;
        private Condition dismountConditions = null;
        private Condition knockbackConditions = null;
        private Condition damageConditions = null;
        private float forwardMovement = 0;
        private float damageMultiplier = 1;
        private EitherEntry<Types_v1_20_5.SoundEvent> sound = null;
        private EitherEntry<Types_v1_20_5.SoundEvent> hitSound = null;


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Condition {
            public static final String MAX_DURATION_TICKS = "max_duration_ticks";
            public static final String MIN_SPEED = "min_speed";
            public static final String MIN_RELATIVE_SPEED = "min_relative_speed";

            private int maxDurationTicks;
            private float minSpeed = 0;
            private float minRelativeSpeed = 0;

            public Condition(final int maxDurationTicks) {
                this.maxDurationTicks = maxDurationTicks;
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PiercingWeapon {
        public static final String DEALS_KNOCKBACK = "deals_knockback";
        public static final String DISMOUNTS = "dismounts";
        public static final String SOUND = "sound";
        public static final String HIT_SOUND = "hit_sound";

        private boolean dealsKnockback = true;
        private boolean dismounts = false;
        private EitherEntry<Types_v1_20_5.SoundEvent> sound;
        private EitherEntry<Types_v1_20_5.SoundEvent> hitSound;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SwingAnimation {
        public static final String TYPE = "type";
        public static final String DURATION = "duration";

        private SwingAnimationType type = SwingAnimationType.WHACK;
        private int duration = 6;
    }

    @Getter
    @RequiredArgsConstructor
    public enum SwingAnimationType implements NamedType {
        NONE("none"),
        WHACK("whack"),
        STAB("stab");

        private final String name;
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
        private Consumable.ItemUseAnimation animation = Consumable.ItemUseAnimation.EAT;
        private EitherEntry<Types_v1_20_5.SoundEvent> sound; //Default: entity.generic.eat
        private boolean hasConsumeParticles = true;
        private List<Types_v1_21_2.ConsumeEffect> onConsumeEffects = new ArrayList<>();


        @Getter
        @AllArgsConstructor
        public enum ItemUseAnimation implements NamedType {
            NONE("none"),
            EAT("eat"),
            DRINK("drink"),
            BLOCK("block"),
            BOW("bow"),
            TRIDENT("trident"),
            CROSSBOW("crossbow"),
            SPYGLASS("spyglass"),
            TOOT_HORN("toot_horn"),
            BRUSH("brush"),
            BUNDLE("bundle"),
            SPEAR("spear");

            private final String name;
        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttackRange {
        public static final String MIN_REACH = "min_reach";
        public static final String MAX_REACH = "max_reach";
        public static final String HITBOX_MARGIN = "hitbox_margin";
        public static final String MOB_FACTOR = "mob_factor";

        private float minReach = 0;
        private float maxReach = 3;
        private float hitboxMargin = 0.3F;
        private float mobFactor = 1;
    }

}
