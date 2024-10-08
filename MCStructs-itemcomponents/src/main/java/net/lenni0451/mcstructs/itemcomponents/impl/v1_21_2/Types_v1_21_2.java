package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.converter.codec.Either;
import net.lenni0451.mcstructs.converter.codec.IdentifiedType;
import net.lenni0451.mcstructs.converter.codec.NamedType;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Types_v1_21_2 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PotionContents {
        public static final String POTION = "potion";
        public static final String CUSTOM_COLOR = "custom_color";
        public static final String CUSTOM_EFFECTS = "custom_effects";
        public static final String CUSTOM_NAME = "custom_name";

        @Nullable
        private Identifier potion;
        @Nullable
        private Integer customColor;
        private List<Types_v1_20_5.StatusEffect> customEffects;
        @Nullable
        private String customName;
    }

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
        private List<ConsumeEffect> onConsumeEffects = new ArrayList<>();


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
    public static class DamageResistant {
        public static final String TYPES = "types";

        private Identifier types;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Equippable {
        public static final String SLOT = "slot";
        public static final String EQUIP_SOUND = "equip_sound";
        public static final String MODEL = "model";
        public static final String CAMERA_OVERLAY = "camera_overlay";
        public static final String ALLOWED_ENTITIES = "allowed_entities";
        public static final String DISPENSABLE = "dispensable";
        public static final String SWAPPABLE = "swappable";
        public static final String DAMAGE_ON_HURT = "damage_on_hurt";

        private EquipmentSlot slot;
        private Either<Identifier, Types_v1_20_5.SoundEvent> equipSound = Either.left(Identifier.of("item.armor.equip_generic"));
        private Identifier model = null;
        private Identifier cameraOverlay = null;
        private Types_v1_20_5.TagEntryList allowedEntities = null;
        private boolean dispensable = true;
        private boolean swappable = true;
        private boolean damageOnHurt = true;

        public Equippable(final EquipmentSlot slot) {
            this.slot = slot;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Repairable {
        public static final String ITEMS = "items";

        private Types_v1_20_5.TagEntryList items;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeathProtection {
        public static final String DEATH_EFFECTS = "death_effects";

        private List<ConsumeEffect> deathEffects = new ArrayList<>();
    }

    @Getter
    @AllArgsConstructor
    public enum EquipmentSlot implements NamedType {
        MAINHAND("mainhand"),
        OFFHAND("offhand"),
        FEET("feet"),
        LEGS("legs"),
        CHEST("chest"),
        HEAD("head"),
        BODY("body");

        private final String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MinMaxInt {
        public static final String MIN = "min";
        public static final String MAX = "max";

        private Integer min;
        private Integer max;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MinMaxDouble {
        public static final String MIN = "min";
        public static final String MAX = "max";

        private Double min;
        private Double max;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnchantmentPredicate {
        public static final String ENCHANTMENTS = "enchantments";
        public static final String LEVELS = "levels";

        private Types_v1_20_5.TagEntryList enchantments;
        private MinMaxInt levels;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CollectionPredicate<T> {
        public static final String CONTAINS = "contains";
        public static final String COUNT = "count";
        public static final String SIZE = "size";

        private List<T> contains;
        private List<CountPredicate<T>> count;
        private MinMaxInt size;


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class CountPredicate<T> {
            public static final String TEST = "test";
            public static final String COUNT = "count";

            private T test;
            private MinMaxInt count;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemPredicate {
        public static final String ITEMS = "items";
        public static final String COUNT = "count";
        public static final String COMPONENTS = "components";
        public static final String PREDICATES = "predicates";

        private Types_v1_20_5.TagEntryList items;
        private MinMaxInt count;
        private Map<ItemComponent<?>, ?> components;
        private Map<ItemSubPredicate.Type, ItemSubPredicate> predicates;
    }

    public interface ConsumeEffect {
        Type getType();

        @Getter
        @AllArgsConstructor
        enum Type implements IdentifiedType {
            APPLY_EFFECTS(Identifier.of("apply_effects")),
            REMOVE_EFFECTS(Identifier.of("remove_effects")),
            CLEAR_ALL_EFFECTS(Identifier.of("clear_all_effects")),
            TELEPORT_RANDOMLY(Identifier.of("teleport_randomly")),
            PLAY_SOUND(Identifier.of("play_sound")),
            ;

            private final Identifier identifier;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class ApplyEffects implements ConsumeEffect {
            public static final String EFFECTS = "effects";
            public static final String PROBABILITY = "probability";

            private final Type type = Type.APPLY_EFFECTS;
            private List<Types_v1_20_5.StatusEffect> effects;
            private float probability = 1;

            public ApplyEffects(final List<Types_v1_20_5.StatusEffect> effects) {
                this.effects = effects;
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class RemoveEffects implements ConsumeEffect {
            public static final String EFFECTS = "effects";

            private final Type type = Type.REMOVE_EFFECTS;
            private Types_v1_20_5.TagEntryList effects;
        }

        @Data
        @NoArgsConstructor
        class ClearAllEffects implements ConsumeEffect {
            private final Type type = Type.CLEAR_ALL_EFFECTS;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class TeleportRandomly implements ConsumeEffect {
            public static final String DIAMETER = "diameter";

            private final Type type = Type.TELEPORT_RANDOMLY;
            private float diameter = 16;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class PlaySound implements ConsumeEffect {
            public static final String SOUND = "sound";

            private final Type type = Type.PLAY_SOUND;
            private Either<Identifier, Types_v1_20_5.SoundEvent> sound;
        }
    }

    public interface ItemSubPredicate {
        Type getType();

        @Getter
        @AllArgsConstructor
        enum Type implements IdentifiedType {
            DAMAGE(Identifier.of("damage")),
            ENCHANTMENTS(Identifier.of("enchantments")),
            STORED_ENCHANTMENTS(Identifier.of("stored_enchantments")),
            POTION_CONTENTS(Identifier.of("potion_contents")),
            CUSTOM_DATA(Identifier.of("custom_data")),
            CONTAINER(Identifier.of("container")),
            BUNDLE_CONTENTS(Identifier.of("bundle_contents")),
            FIREWORK_EXPLOSION(Identifier.of("firework_explosion")),
            FIREWORKS(Identifier.of("fireworks")),
            WRITABLE_BOOK_CONTENT(Identifier.of("writable_book_content")),
            WRITTEN_BOOK_CONTENT(Identifier.of("written_book_content")),
            ATTRIBUTE_MODIFIERS(Identifier.of("attribute_modifiers")),
            TRIM(Identifier.of("trim")),
            JUKEBOX_PLAYABLE(Identifier.of("jukebox_playable")),
            ;

            private final Identifier identifier;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class Damage implements ItemSubPredicate {
            public static final String DURABILITY = "durability";
            public static final String DAMAGE = "damage";

            private final Type type = Type.DAMAGE;
            private MinMaxInt durability;
            private MinMaxInt damage;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class Enchantments implements ItemSubPredicate {
            private final Type type = Type.ENCHANTMENTS;
            private List<EnchantmentPredicate> enchantments;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class StoredEnchantments implements ItemSubPredicate {
            private final Type type = Type.STORED_ENCHANTMENTS;
            private List<EnchantmentPredicate> enchantments;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class PotionContents implements ItemSubPredicate {
            private final Type type = Type.POTION_CONTENTS;
            private Types_v1_20_5.TagEntryList potion;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class CustomData implements ItemSubPredicate {
            private final Type type = Type.CUSTOM_DATA;
            private CompoundTag data;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class Container implements ItemSubPredicate {
            public static final String ITEMS = "items";

            private final Type type = Type.CONTAINER;
            private CollectionPredicate<ItemPredicate> items;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class BundleContents implements ItemSubPredicate {
            public static final String ITEMS = "items";

            private final Type type = Type.BUNDLE_CONTENTS;
            private CollectionPredicate<ItemPredicate> items;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class FireworkExplosion implements ItemSubPredicate {
            public static final String SHAPE = "shape";
            public static final String HAS_TWINKLE = "has_twinkle";
            public static final String HAS_TRAIL = "has_trail";

            private final Type type = Type.FIREWORK_EXPLOSION;
            private Types_v1_20_5.FireworkExplosions.ExplosionShape shape;
            private boolean hasTwinkle;
            private boolean hasTrail;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class Fireworks implements ItemSubPredicate {
            public static final String EXPLOSIONS = "explosions";
            public static final String FLIGHT_DURATION = "flight_duration";

            private final Type type = Type.FIREWORKS;
            private CollectionPredicate<FireworkExplosion> explosions;
            private MinMaxInt flightDuration;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class WritableBookContent implements ItemSubPredicate {
            public static final String PAGES = "pages";

            private final Type type = Type.WRITABLE_BOOK_CONTENT;
            private CollectionPredicate<PageContent> pages;


            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class PageContent {
                private String page;
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class WrittenBookContent implements ItemSubPredicate {
            public static final String PAGES = "pages";
            public static final String AUTHOR = "author";
            public static final String TITLE = "title";
            public static final String GENERATION = "generation";
            public static final String RESOLVED = "resolved";

            private final Type type = Type.WRITTEN_BOOK_CONTENT;
            private CollectionPredicate<PageContent> pages;
            private String author;
            private String title;
            private MinMaxInt generation;
            private boolean resolved;


            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class PageContent {
                private String page;
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class AttributeModifiers implements ItemSubPredicate {
            public static final String MODIFIERS = "modifiers";

            private final Type type = Type.ATTRIBUTE_MODIFIERS;
            private CollectionPredicate<ModifierPredicate> modifiers;


            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class ModifierPredicate {
                public static final String ATTRIBUTE = "attribute";
                public static final String ID = "id";
                public static final String AMOUNT = "amount";
                public static final String OPERATION = "operation";
                public static final String SLOT = "slot";

                private Types_v1_20_5.TagEntryList attribute;
                private Identifier id;
                private MinMaxDouble amount;
                private Types_v1_21.AttributeModifier.EntityAttribute.Operation operation;
                private Types_v1_20_5.AttributeModifier.Slot slot;
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class Trim implements ItemSubPredicate {
            public static final String MATERIAL = "material";
            public static final String PATTERN = "pattern";

            private final Type type = Type.TRIM;
            private Types_v1_20_5.TagEntryList material;
            private Types_v1_20_5.TagEntryList pattern;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class JukeboxPlayable implements ItemSubPredicate {
            public static final String SONG = "song";

            private final Type type = Type.JUKEBOX_PLAYABLE;
            private Types_v1_20_5.TagEntryList song;
        }
    }

}
