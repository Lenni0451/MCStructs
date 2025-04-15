package net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.converter.types.NamedType;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.registry.EitherEntry;
import net.lenni0451.mcstructs.itemcomponents.registry.RegistryEntry;
import net.lenni0451.mcstructs.itemcomponents.registry.TagEntryList;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.TextFormatting;

import javax.annotation.Nonnull;
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
        private List<TextComponent> lines = new ArrayList<>();

        public void addLine(final TextComponent line) {
            this.lines.add(line);
        }

        public void removeLine(final TextComponent line) {
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
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Enchantments {
        public static final String LEVELS = "levels";
        public static final String SHOW_IN_TOOLTIP = "show_in_tooltip";

        private Map<RegistryEntry, Integer> enchantments = new HashMap<>();
        private boolean showInTooltip = true;

        public void addEnchantment(final RegistryEntry enchantment, final int level) {
            this.enchantments.put(enchantment, level);
        }

        public void removeEnchantment(final RegistryEntry enchantment) {
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

        public DyedColor(final int rgb) {
            this.rgb = rgb;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MapDecoration {
        public static final String TYPE = "type";
        public static final String X = "x";
        public static final String Z = "z";
        public static final String ROTATION = "rotation";

        private RegistryEntry type;
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

        private RegistryEntry id;
        private int count = 1;
        private ItemComponentMap components /* = new ItemComponentMap() */;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WritableBook {
        public static final String PAGES = "pages";

        private List<RawFilteredPair<String>> pages;

        public void addPage(final String raw) {
            this.addPage(new RawFilteredPair<>(raw));
        }

        public void addPage(final String raw, final String filtered) {
            this.addPage(new RawFilteredPair<>(raw, filtered));
        }

        public void addPage(final RawFilteredPair<String> page) {
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
        private List<Integer> colors = new ArrayList<>();
        private List<Integer> fadeColors = new ArrayList<>();
        private boolean hasTrail = false;
        private boolean hasTwinkle = false;

        public FireworkExplosions(final ExplosionShape shape) {
            this.shape = shape;
        }


        @Getter
        @AllArgsConstructor
        public enum ExplosionShape implements NamedType {
            SMALL_BALL("small_ball"),
            LARGE_BALL("large_ball"),
            STAR("star"),
            CREEPER("creeper"),
            BURST("burst");

            private final String name;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fireworks {
        public static final String FLIGHT_DURATION = "flight_duration";
        public static final String EXPLOSIONS = "explosions";

        private int flightDuration = 0;
        private List<FireworkExplosions> explosions = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BannerPattern {
        public static final String PATTERN = "pattern";
        public static final String COLOR = "color";

        private EitherEntry<Pattern> pattern;
        private DyeColor color;


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
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BeeData {
        public static final String ENTITY_DATA = "entity_data";
        public static final String TICKS_IN_HIVE = "ticks_in_hive";
        public static final String MIN_TICKS_IN_HIVE = "min_ticks_in_hive";

        private CompoundTag entityData = new CompoundTag();
        private int ticksInHive;
        private int minTicksInHive;

        public BeeData(final int ticksInHive, final int minTicksInHive) {
            this.ticksInHive = ticksInHive;
            this.minTicksInHive = minTicksInHive;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContainerLoot {
        public static final String LOOT_TABLE = "loot_table";
        public static final String SEED = "seed";

        private Identifier lootTable;
        private long seed = 0;

        public ContainerLoot(final Identifier lootTable) {
            this.lootTable = lootTable;
        }
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
        private float range = 16;

        public SoundEvent(final Identifier soundId) {
            this.soundId = soundId;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Instrument {
        public static final String SOUND_EVENT = "sound_event";
        public static final String USE_DURATION = "use_duration";
        public static final String RANGE = "range";

        private EitherEntry<SoundEvent> soundEvent;
        private int useDuration;
        private float range;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SuspiciousStewEffect {
        public static final String ID = "id";
        public static final String DURATION = "duration";

        private RegistryEntry id;
        private int duration = 0;

        public SuspiciousStewEffect(final RegistryEntry id) {
            this.id = id;
        }
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
        private int generation = 0;
        private List<RawFilteredPair<TextComponent>> pages = new ArrayList<>();
        private boolean resolved = false;

        public WrittenBook(final RawFilteredPair<String> title, final String author) {
            this.title = title;
            this.author = author;
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
        private TagEntryList blocks;
        @Nullable
        private Map<String, ValueMatcher> state;
        @Nullable
        private CompoundTag nbt;


        @Data
        @NoArgsConstructor
        public static class ValueMatcher {
            public static final String MIN = "min";
            public static final String MAX = "max";

            private String value;
            @Nullable
            private String min;
            @Nullable
            private String max;

            public ValueMatcher(@Nonnull final String value) {
                this.value = value;
            }

            public ValueMatcher(@Nullable final String min, @Nullable final String max) {
                this.min = min;
                this.max = max;
            }

            public boolean isValue() {
                return this.value != null;
            }

            public boolean isRange() {
                return !this.isValue();
            }
        }
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

        private RegistryEntry type;
        private EntityAttribute modifier;
        private Slot slot = Slot.ANY;

        public AttributeModifier(final RegistryEntry type, final EntityAttribute modifier) {
            this.type = type;
            this.modifier = modifier;
        }


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class EntityAttribute {
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
    public static class ToolComponent {
        public static final String RULES = "rules";
        public static final String DEFAULT_MINING_SPEED = "default_mining_speed";
        public static final String DAMAGE_PER_BLOCK = "damage_per_block";

        private List<Rule> rules;
        private float defaultMiningSpeed = 1;
        private int damagePerBlock = 1;

        public ToolComponent(final List<Rule> rules) {
            this.rules = rules;
        }


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Rule {
            public static final String BLOCKS = "blocks";
            public static final String SPEED = "speed";
            public static final String CORRECT_FOR_DROPS = "correct_for_drops";

            private TagEntryList blocks;
            private Float speed = null;
            private Boolean correctForDrops = null;

            public Rule(final TagEntryList blocks) {
                this.blocks = blocks;
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArmorTrim {
        public static final String MATERIAL = "material";
        public static final String PATTERN = "pattern";
        public static final String SHOW_IN_TOOLTIP = "show_in_tooltip";

        private EitherEntry<ArmorTrimMaterial> material;
        private EitherEntry<ArmorTrimPattern> pattern;
        private boolean showInTooltip = true;

        public ArmorTrim(final EitherEntry<ArmorTrimMaterial> material, final EitherEntry<ArmorTrimPattern> pattern) {
            this.material = material;
            this.pattern = pattern;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArmorTrimMaterial {
        public static final String ASSET_NAME = "asset_name";
        public static final String INGREDIENT = "ingredient";
        public static final String ITEM_MODEL_INDEX = "item_model_index";
        public static final String OVERRIDE_ARMOR_MATERIALS = "override_armor_materials";
        public static final String DESCRIPTION = "description";

        private String assetName;
        private RegistryEntry ingredient;
        private float itemModelIndex;
        private Map<RegistryEntry, String> overrideArmorMaterials;
        private TextComponent description;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArmorTrimPattern {
        public static final String ASSET_ID = "asset_id";
        public static final String TEMPLATE_ITEM = "template_item";
        public static final String DESCRIPTION = "description";
        public static final String DECAL = "decal";

        private Identifier assetId;
        private RegistryEntry templateItem;
        private TextComponent description;
        private boolean decal = false;

        public ArmorTrimPattern(final Identifier assetId, final RegistryEntry templateItem, final TextComponent description) {
            this.assetId = assetId;
            this.templateItem = templateItem;
            this.description = description;
        }
    }


    @Data
    @NoArgsConstructor
    public static class StatusEffect {
        public static final String ID = "id";

        private RegistryEntry id;
        private int amplifier = 0;
        private int duration = 0;
        private boolean ambient = false;
        private boolean showParticles = true;
        private boolean showIcon = this.showParticles;
        @Nullable
        private StatusEffect hiddenEffect;

        public StatusEffect(final RegistryEntry id) {
            this.id = id;
        }

        public StatusEffect(final RegistryEntry id, final Parameters parameters) {
            this.id = id;
            this.amplifier = parameters.amplifier;
            this.duration = parameters.duration;
            this.ambient = parameters.ambient;
            this.showParticles = parameters.showParticles;
            this.showIcon = parameters.showIcon;
            if (parameters.hiddenEffect != null) this.hiddenEffect = new StatusEffect(id, parameters.hiddenEffect);
        }

        public StatusEffect(final RegistryEntry id, final int amplifier, final int duration, final boolean ambient, final boolean showParticles, final Boolean showIcon, final StatusEffect hiddenEffect) {
            this.id = id;
            this.amplifier = amplifier;
            this.duration = duration;
            this.ambient = ambient;
            this.showParticles = showParticles;
            this.showIcon = showIcon != null ? showIcon : showParticles;
            this.hiddenEffect = hiddenEffect;
        }

        public Parameters getParameters() {
            return new Parameters(this.amplifier, this.duration, this.ambient, this.showParticles, this.showIcon, this.hiddenEffect != null ? this.hiddenEffect.getParameters() : null);
        }

        public void setParameters(final Parameters parameters) {
            this.amplifier = parameters.amplifier;
            this.duration = parameters.duration;
            this.ambient = parameters.ambient;
            this.showParticles = parameters.showParticles;
            this.showIcon = parameters.showIcon;
            if (parameters.hiddenEffect != null) {
                if (this.hiddenEffect == null) this.hiddenEffect = new StatusEffect(this.id, parameters.hiddenEffect);
                else this.hiddenEffect.setParameters(parameters.hiddenEffect);
            } else {
                this.hiddenEffect = null;
            }
        }


        @Data
        @NoArgsConstructor
        public static class Parameters {
            public static final String AMPLIFIER = "amplifier";
            public static final String DURATION = "duration";
            public static final String AMBIENT = "ambient";
            public static final String SHOW_PARTICLES = "show_particles";
            public static final String SHOW_ICON = "show_icon";
            public static final String HIDDEN_EFFECT = "hidden_effect";

            private int amplifier = 0;
            private int duration = 0;
            private boolean ambient = false;
            private boolean showParticles = true;
            private boolean showIcon = this.showParticles;
            @Nullable
            private Parameters hiddenEffect;

            public Parameters(final int amplifier, final int duration, final boolean ambient, final boolean showParticles, @Nullable final Boolean showIcon, @Nullable final Parameters hiddenEffect) {
                this.amplifier = amplifier;
                this.duration = duration;
                this.ambient = ambient;
                this.showParticles = showParticles;
                this.showIcon = showIcon != null ? showIcon : showParticles;
                this.hiddenEffect = hiddenEffect;
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PotionContents {
        public static final String POTION = "potion";
        public static final String CUSTOM_COLOR = "custom_color";
        public static final String CUSTOM_EFFECTS = "custom_effects";

        @Nullable
        private RegistryEntry potion;
        @Nullable
        private Integer customColor;
        private List<StatusEffect> customEffects;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Food {
        public static final String NUTRITION = "nutrition";
        public static final String SATURATION = "saturation";
        public static final String CAN_ALWAYS_EAT = "can_always_eat";
        public static final String EAT_SECONDS = "eat_seconds";
        public static final String EFFECTS = "effects";

        private int nutrition;
        private float saturation;
        private boolean canAlwaysEat = false;
        private float eatSeconds = 1.6F;
        private List<Effect> effects = new ArrayList<>();

        public Food(final int nutrition, final float saturation) {
            this.nutrition = nutrition;
            this.saturation = saturation;
        }


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Effect {
            public static final String EFFECT = "effect";
            public static final String PROBABILITY = "probability";

            private StatusEffect effect;
            private float probability = 1;

            public Effect(final StatusEffect effect) {
                this.effect = effect;
            }
        }
    }

    public enum MapPostProcessing {
        LOCK, SCALE
    }

}
