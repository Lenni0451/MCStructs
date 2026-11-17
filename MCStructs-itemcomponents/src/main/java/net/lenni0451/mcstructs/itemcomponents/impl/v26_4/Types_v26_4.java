package net.lenni0451.mcstructs.itemcomponents.impl.v26_4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.converter.types.IdentifiedType;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.registry.Holder;
import net.lenni0451.mcstructs.registry.RegistryEntry;

import javax.annotation.Nullable;

public class Types_v26_4 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Vec3d {
        private double x;
        private double y;
        private double z;
    }

    public interface FloatProvider {
        Type getType();

        @Getter
        @AllArgsConstructor
        enum Type implements IdentifiedType {
            CONSTANT(Identifier.of("constant")),
            UNIFORM(Identifier.of("uniform")),
            CLAMPED_NORMAL(Identifier.of("clamped_normal")),
            TRAPEZOID(Identifier.of("trapezoid")),
            ;

            private final Identifier identifier;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class ConstantFloat implements FloatProvider {
            public static final String VALUE = "value";

            private final Type type = Type.CONSTANT;
            private float value;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class UniformFloat implements FloatProvider {
            public static final String MIN_INCLUSIVE = "min_inclusive";
            public static final String MAX_EXCLUSIVE = "max_exclusive";

            private final Type type = Type.UNIFORM;
            private float minInclusive;
            private float maxExclusive;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class ClampedNormalFloat implements FloatProvider {
            public static final String MEAN = "mean";
            public static final String DEVIATION = "deviation";
            public static final String MIN = "min";
            public static final String MAX = "max";

            private final Type type = Type.CLAMPED_NORMAL;
            private float mean;
            private float deviation;
            private float min;
            private float max;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class TrapezoidFloat implements FloatProvider {
            public static final String MIN = "min";
            public static final String MAX = "max";
            public static final String PLATEAU = "plateau";

            private final Type type = Type.TRAPEZOID;
            private float min;
            private float max;
            private float plateau;
        }
    }

    public interface DispenseBehavior {
        Type getType();

        @Getter
        @AllArgsConstructor
        enum Type implements IdentifiedType {
            BRUSH_ARMADILLO(Identifier.of("brush_armadillo"), true),
            CHARGE_RESPAWN_ANCHOR(Identifier.of("charge_respawn_anchor"), true),
            CONVERT_TO_MUD(Identifier.of("convert_to_mud"), true),
            DROP_ITEM(Identifier.of("drop_item"), true),
            EMPTY_BUCKET(Identifier.of("empty_bucket"), true),
            EQUIP(Identifier.of("equip"), true),
            EQUIP_CHEST(Identifier.of("equip_chest"), true),
            EQUIP_SULFUR_CUBE(Identifier.of("equip_sulfur_cube"), true),
            FILL_BOTTLE(Identifier.of("fill_bottle"), true),
            FILL_BUCKET(Identifier.of("fill_bucket"), true),
            GROW_PLANT(Identifier.of("grow_plant"), true),
            IGNITE(Identifier.of("ignite"), true),
            INSERT_INTO_CONTAINER(Identifier.of("insert_into_container"), true),
            PLACE_ARMOR_STAND(Identifier.of("place_armor_stand"), true),
            PLACE_BOAT(Identifier.of("place_boat"), true),
            PLACE_MINECART(Identifier.of("place_minecart"), true),
            PLACE_BLOCK(Identifier.of("place_block"), true),
            SHEAR(Identifier.of("shear"), true),
            SHOOT_PROJECTILE(Identifier.of("shoot_projectile"), false),
            SPAWN_GOLEM_FROM_CARVED_PUMPKIN(Identifier.of("spawn_golem_from_carved_pumpkin"), true),
            SPAWN_MOB(Identifier.of("spawn_mob"), true),
            SPAWN_TNT(Identifier.of("spawn_tnt"), true),
            SPAWN_WITHER_FROM_WITHER_SKELETON_SKULL(Identifier.of("spawn_wither_from_wither_skeleton_skull"), true),
            TRANSFORM_BLOCK(Identifier.of("transform_block"), false),
            ;

            private final Identifier identifier;
            private final boolean simple;
        }

        @Data
        class Simple implements DispenseBehavior {
            private final Type type;

            public Simple(final Type type) {
                if (!type.isSimple()) throw new IllegalArgumentException("Dispense behavior type " + type + " has additional data");
                this.type = type;
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class ShootProjectile implements DispenseBehavior {
            public static final String OFFSET = "offset";
            public static final String SCALE = "scale";
            public static final String UNCERTAINTY = "uncertainty";
            public static final String POWER = "power";
            public static final String SOUND = "sound";

            private final Type type = Type.SHOOT_PROJECTILE;
            private Vec3d offset;
            private double scale;
            private float uncertainty;
            private float power;
            @Nullable
            private DispenseSound sound;

            public ShootProjectile(final Vec3d offset, final double scale, final float uncertainty, final float power) {
                this(offset, scale, uncertainty, power, null);
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class TransformBlock implements DispenseBehavior {
            public static final String TRANSFORMER = "transformer";

            private final Type type = Type.TRANSFORM_BLOCK;
            private RegistryEntry transformer;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DispenseSound {
        public static final String SOUND = "sound";
        public static final String VOLUME = "volume";
        public static final String PITCH = "pitch";

        private Holder<Types_v1_20_5.SoundEvent> sound;
        private FloatProvider volume;
        private FloatProvider pitch;
    }

}
