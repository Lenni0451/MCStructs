package net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.converter.codec.Either;
import net.lenni0451.mcstructs.converter.codec.NamedType;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.ATextComponent;

import javax.annotation.Nullable;
import java.util.*;

public class Types_v1_20_5 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Unbreakable {
        public static final String SHOW_IN_TOOLTIP = "show_in_tooltip";

        private boolean showInTooltip = true;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Lore {
        public static final int MAX_COUNT = 256;

        private List<ATextComponent> lines = new ArrayList<>();

        public void addLine(final ATextComponent line) {
            if (this.lines.size() >= MAX_COUNT) throw new IllegalArgumentException("Lore can only have " + MAX_COUNT + " lines");
            this.lines.add(line);
        }

        public void removeLine(final ATextComponent line) {
            this.lines.remove(line);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Rarity implements NamedType {
        COMMON("common", TextFormatting.WHITE),
        UNCOMMON("uncommon", TextFormatting.YELLOW),
        RARE("rare", TextFormatting.AQUA),
        EPIC("epic", TextFormatting.LIGHT_PURPLE);

        private final String name;
        private final TextFormatting color;

        public static Rarity byOrdinal(final int ordinal) {
            if (ordinal < 0 || ordinal >= values().length) throw new IllegalArgumentException("Unknown rarity ordinal: " + ordinal);
            return values()[ordinal];
        }

        public static Rarity byName(final String name) {
            for (Rarity rarity : values()) {
                if (rarity.getName().equalsIgnoreCase(name)) return rarity;
            }
            throw new IllegalArgumentException("Unknown rarity: " + name);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Enchantments {
        public static final String LEVELS = "levels";
        public static final String SHOW_IN_TOOLTIP = "show_in_tooltip";

        private Map<Identifier, Integer> enchantments = new HashMap<>();
        private boolean showInTooltip = true;

        public void addEnchantment(final Identifier enchantment, final int level) {
            this.enchantments.put(enchantment, level);
        }

        public void removeEnchantment(final Identifier enchantment) {
            this.enchantments.remove(enchantment);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DyedColor {
        public static final String RGB = "rgb";
        public static final String SHOW_IN_TOOLTIP = "show_in_tooltip";

        private int rgb;
        private boolean showInTooltip = true;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MapDecoration {
        public static final String TYPE = "type";
        public static final String X = "x";
        public static final String Z = "z";
        public static final String ROTATION = "rotation";

        private Identifier type;
        private double x;
        private double z;
        private float rotation;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemStack {
        public static final String ID = "id";
        public static final String COUNT = "count";
        public static final String COMPONENTS = "components";

        private Identifier id;
        private int count;
        private ItemComponentMap components;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WritableBook {
        public static final String PAGES = "pages";
        public static final int MAX_PAGES = 100;

        private List<RawFilteredPair<String>> pages;

        public void addPage(final String raw) {
            this.addPage(new RawFilteredPair<>(raw));
        }

        public void addPage(final String raw, final String filtered) {
            this.addPage(new RawFilteredPair<>(raw, filtered));
        }

        public void addPage(final RawFilteredPair<String> page) {
            if (this.pages.size() >= MAX_PAGES) throw new IllegalArgumentException("Book can only have " + MAX_PAGES + " pages");
            this.pages.add(page);
        }

        public void removePage(final int index) {
            this.pages.remove(index);
        }

        public void removePage(final String raw) {
            this.pages.removeIf(page -> page.getRaw().equals(raw));
        }

        public void removePage(final RawFilteredPair<String> page) {
            this.pages.remove(page);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FireworkExplosions {
        public static final String SHAPE = "shape";
        public static final String COLORS = "colors";
        public static final String FADE_COLORS = "fade_colors";
        public static final String HAS_TRAIL = "has_trail";
        public static final String HAS_TWINKLE = "has_twinkle";

        private ExplosionShape shape;
        private int[] colors;
        private int[] fadeColors;
        private boolean hasTrail;
        private boolean hasTwinkle;


        @Getter
        @AllArgsConstructor
        public enum ExplosionShape implements NamedType {
            SMALL_BALL("small_ball"),
            LARGE_BALL("large_ball"),
            STAR("star"),
            CREEPER("creeper"),
            BURST("burst");

            private final String name;

            public static ExplosionShape byName(final String name) {
                for (ExplosionShape shape : values()) {
                    if (shape.getName().equalsIgnoreCase(name)) return shape;
                }
                throw new IllegalArgumentException("Unknown explosion shape: " + name);
            }

            public static ExplosionShape byOrdinal(final int ordinal) {
                if (ordinal < 0 || ordinal >= values().length) throw new IllegalArgumentException("Unknown explosion shape ordinal: " + ordinal);
                return values()[ordinal];
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fireworks {
        public static final String FLIGHT_DURATION = "flight_duration";
        public static final String EXPLOSIONS = "explosions";

        private int flightDuration;
        private List<FireworkExplosions> explosions;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BannerPattern {
        public static final String PATTERN = "pattern";
        public static final String COLOR = "color";

        private DyeColor color;
        private Either<Identifier, Pattern> pattern;


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Pattern {
            public static final String ASSET_ID = "asset_id";
            public static final String TRANSLATION_KEY = "translation_key";

            private Identifier assetId;
            private String translationKey;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum DyeColor implements NamedType {
        WHITE("white", 0xF9_FF_FE),
        ORANGE("orange", 0xF9_80_1D),
        MAGENTA("magenta", 0xC7_4E_BD),
        LIGHT_BLUE("light_blue", 0x3A_B3_DA),
        YELLOW("yellow", 0xFE_D8_3D),
        LIME("lime", 0x80_C7_1F),
        PINK("pink", 0xF3_8B_AA),
        GRAY("gray", 0x47_4F_52),
        LIGHT_GRAY("light_gray", 0x9D_9D_97),
        CYAN("cyan", 0x16_9C_9C),
        PURPLE("purple", 0x89_32_B8),
        BLUE("blue", 0x3C_44_AA),
        BROWN("brown", 0x83_54_32),
        GREEN("green", 0x5E_7C_16),
        RED("red", 0xB0_2E_26),
        BLACK("black", 0x1D_1D_21);

        private final String name;
        private final int color;

        public static DyeColor byName(final String name) {
            for (DyeColor color : values()) {
                if (color.getName().equalsIgnoreCase(name)) return color;
            }
            throw new IllegalArgumentException("Unknown dye color: " + name);
        }

        public static DyeColor byColor(final int color) {
            for (DyeColor dyeColor : values()) {
                if (dyeColor.getColor() == color) return dyeColor;
            }
            throw new IllegalArgumentException("Unknown dye color: " + color);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BeeData {
        public static final String ENTITY_DATA = "entity_data";
        public static final String TICKS_IN_HIVE = "ticks_in_hive";
        public static final String MIN_TICKS_IN_HIVE = "min_ticks_in_hive";

        private CompoundTag entityData;
        private int ticksInHive;
        private int minTicksInHive;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContainerLoot {
        public static final String LOOT_TABLE = "loot_table";
        public static final String SEED = "seed";

        private Identifier lootTable;
        private long seed;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContainerSlot {
        public static final String SLOT = "slot";
        public static final String ITEM = "item";

        private int slot;
        private ItemStack item;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameProfile {
        public static final String NAME = "name";
        public static final String ID = "id";
        public static final String PROPERTIES = "properties";

        @Nullable
        private String name;
        @Nullable
        private UUID uuid;
        private Map<String, List<Property>> properties;


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Property {
            public static final String NAME = "name";
            public static final String VALUE = "value";
            public static final String SIGNATURE = "signature";

            private String name;
            private String value;
            @Nullable
            private String signature;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LodestoneTracker {
        public static final String TARGET = "target";
        public static final String TRACKED = "tracked";

        @Nullable
        private GlobalPos target;
        private boolean tracked = true;


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class GlobalPos {
            public static final String DIMENSION = "dimension";
            public static final String POS = "pos";

            private Identifier dimension;
            private BlockPos pos;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BlockPos {
        private int x;
        private int y;
        private int z;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SoundEvent {
        public static final String SOUND_ID = "sound_id";
        public static final String RANGE = "range";

        private Identifier soundId;
        private float range;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Instrument {
        public static final String SOUND_EVENT = "sound_event";
        public static final String USE_DURATION = "use_duration";
        public static final String RANGE = "range";

        private Either<Identifier, SoundEvent> soundEvent;
        private int useDuration;
        private float range;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SuspiciousStewEffect {
        public static final String ID = "id";
        public static final String DURATION = "duration";

        private Identifier id;
        private int duration;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RawFilteredPair<T> {
        public static final String RAW = "raw";
        public static final String FILTERED = "filtered";

        private T raw;
        @Nullable
        private T filtered;

        public RawFilteredPair(final T raw) {
            this(raw, null);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WrittenBook {
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";
        public static final String GENERATION = "generation";
        public static final String PAGES = "pages";
        public static final String RESOLVED = "resolved";

        private RawFilteredPair<String> title;
        private String author;
        private int generation;
        private List<RawFilteredPair<ATextComponent>> pages;
        private boolean resolved;
    }


    @Data
    @NoArgsConstructor
    public static class ValueMatcher {
        public static final String MIN = "min";
        public static final String MAX = "max";

        @Nullable
        private String value;
        @Nullable
        private String min;
        @Nullable
        private String max;

        public ValueMatcher(final String value) {
            this.value = value;
        }

        public ValueMatcher(final String min, final String max) {
            this.min = min;
            this.max = max;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BlockPredicate {
        public static final String BLOCKS = "blocks";
        public static final String STATE = "state";
        public static final String NBT = "nbt";

        @Nullable
        private List<Identifier> blocks;
        @Nullable
        private Map<String, ValueMatcher> state;
        @Nullable
        private CompoundTag nbt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BlockPredicatesChecker {
        public static final String PREDICATES = "predicates";
        public static final String SHOW_IN_TOOLTIP = "show_in_tooltip";

        private List<BlockPredicate> predicates;
        private boolean showInTooltip = true;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttributeModifier {
        public static final String TYPE = "type";
        public static final String SLOT = "slot";

        private Identifier type;
        private EntityAttributeModifier modifier;
        private Slot slot;


        @Getter
        @AllArgsConstructor
        public enum Slot implements NamedType {
            ANY("any"),
            MAINHAND("mainhand"),
            OFFHAND("offhand"),
            HAND("hand"),
            FEET("feet"),
            LEGS("legs"),
            CHEST("chest"),
            HEAD("head"),
            ARMOR("armor"),
            BODY("body");

            private final String name;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EntityAttributeModifier {
        public static final String UUID = "uuid";
        public static final String NAME = "name";
        public static final String AMOUNT = "amount";
        public static final String OPERATION = "operation";

        private UUID uuid;
        private String name;
        private double amount;
        private Operation operation;


        @Getter
        @AllArgsConstructor
        public enum Operation implements NamedType {
            ADD_VALUE("add_value"),
            ADD_MULTIPLIED_BASE("add_multiplied_base"),
            ADD_MULTIPLIED_TOTAL("add_multiplied_total");

            private final String name;
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
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ToolComponent {
        public static final String RULES = "rules";
        public static final String DEFAULT_MINING_SPEED = "default_mining_speed";
        public static final String DAMAGE_PER_BLOCK = "damage_per_block";

        private List<Rule> rules;
        private float defaultMiningSpeed = 1;
        private int damagePerBlock = 1;


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Rule {
            public static final String BLOCKS = "blocks";
            public static final String SPEED = "speed";
            public static final String CORRECT_FOR_DROPS = "correct_for_drops";

            private List<Identifier> blocks;
            private Float speed;
            private Boolean correctForDrops;
        }
    }

}
