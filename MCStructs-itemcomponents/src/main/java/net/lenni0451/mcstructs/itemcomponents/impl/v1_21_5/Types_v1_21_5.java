package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.converter.types.IdentifiedType;
import net.lenni0451.mcstructs.converter.types.NamedType;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.registry.Holder;
import net.lenni0451.mcstructs.registry.RegistryEntry;
import net.lenni0451.mcstructs.registry.TagEntryList;
import net.lenni0451.mcstructs.registry.TagKey;
import net.lenni0451.mcstructs.text.TextComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Types_v1_21_5 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Weapon {
        public static final String ITEM_DAMAGE_PER_ATTACK = "item_damage_per_attack";
        public static final String DISABLE_BLOCKING_FOR_SECONDS = "disable_blocking_for_seconds";

        private int itemDamagePerAttack = 1;
        private float disableBlockingForSeconds = 0;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ToolComponent {
        public static final String RULES = "rules";
        public static final String DEFAULT_MINING_SPEED = "default_mining_speed";
        public static final String DAMAGE_PER_BLOCK = "damage_per_block";
        public static final String CAN_DESTROY_BLOCKS_IN_CREATIVE = "can_destroy_blocks_in_creative";

        private List<Types_v1_20_5.ToolComponent.Rule> rules;
        private float defaultMiningSpeed = 1;
        private int damagePerBlock = 1;
        private boolean canDestroyBlocksInCreative = true;

        public ToolComponent(final List<Types_v1_20_5.ToolComponent.Rule> rules) {
            this.rules = rules;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum VillagerVariant implements IdentifiedType {
        DESERT(Identifier.of("desert")),
        JUNGLE(Identifier.of("jungle")),
        PLAINS(Identifier.of("plains")),
        SAVANNA(Identifier.of("savanna")),
        SNOW(Identifier.of("snow")),
        SWAMP(Identifier.of("swamp")),
        TAIGA(Identifier.of("taiga"));

        private final Identifier identifier;
    }

    @Getter
    @AllArgsConstructor
    public enum FoxVariant implements NamedType {
        RED("red"),
        SNOW("snow");

        private final String name;
    }

    @Getter
    @AllArgsConstructor
    public enum SalmonSize implements NamedType {
        SMALL("small"),
        MEDIUM("medium"),
        LARGE("large");

        private final String name;
    }

    @Getter
    @AllArgsConstructor
    public enum ParrotVariant implements NamedType {
        RED_BLUE("red_blue"),
        BLUE("blue"),
        GREEN("green"),
        YELLOW_BLUE("yellow_blue"),
        GRAY("gray");

        private final String name;
    }

    @Getter
    @AllArgsConstructor
    public enum TropicalFishPattern implements NamedType {
        KOB("kob", 0 | 0 << 8),
        SUNSTREAK("sunstreak", 0 | 1 << 8),
        SNOOPER("snooper", 0 | 2 << 8),
        DASHER("dasher", 0 | 3 << 8),
        BRINELY("brinely", 0 | 4 << 8),
        SPOTTY("spotty", 0 | 5 << 8),
        FLOPPER("flopper", 1 | 0 << 8),
        STRIPEY("stripey", 1 | 1 << 8),
        GLITTER("glitter", 1 | 2 << 8),
        BLOCKFISH("blockfish", 1 | 3 << 8),
        BETTY("betty", 1 | 4 << 8),
        CLAYFISH("clayfish", 1 | 5 << 8);

        private final String name;
        private final int packetId;
    }

    @Getter
    @AllArgsConstructor
    public enum MooshroomVariant implements NamedType {
        RED("red"),
        BROWN("brown");

        private final String name;
    }

    @Getter
    @AllArgsConstructor
    public enum RabbitVariant implements NamedType {
        BROWN("brown", 0),
        WHITE("white", 1),
        BLACK("black", 2),
        WHITE_SPLOTCHED("white_splotched", 3),
        GOLD("gold", 4),
        SALT("salt", 5),
        EVIL("evil", 99);

        private final String name;
        private final int packetId;
    }

    @Getter
    @AllArgsConstructor
    public enum HorseVariant implements NamedType {
        WHITE("white"),
        CREAMY("creamy"),
        CHESTNUT("chestnut"),
        BROWN("brown"),
        BLACK("black"),
        GRAY("gray"),
        DARK_BROWN("dark_brown");

        private final String name;
    }

    @Getter
    @AllArgsConstructor
    public enum LlamaVariant implements NamedType {
        CREAMY("creamy"),
        WHITE("white"),
        BROWN("brown"),
        GRAY("gray");

        private final String name;
    }

    @Getter
    @AllArgsConstructor
    public enum AxolotlVariant implements NamedType {
        LUCY("lucy"),
        WILD("wild"),
        GOLD("gold"),
        CYAN("cyan"),
        BLUE("blue");

        private final String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaintingVariant {
        public static final String WIDTH = "width";
        public static final String HEIGHT = "height";
        public static final String ASSET_ID = "asset_id";
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";

        private int width;
        private int height;
        private Identifier assetId;
        private TextComponent title = null;
        private TextComponent author = null;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BlocksAttacks {
        public static final String BLOCK_DELAY_SECONDS = "block_delay_seconds";
        public static final String DISABLE_COOLDOWN_SCALE = "disable_cooldown_scale";
        public static final String DAMAGE_REDUCTIONS = "damage_reductions";
        public static final String ITEM_DAMAGE = "item_damage";
        public static final String BYPASSED_BY = "bypassed_by";
        public static final String BLOCK_SOUND = "block_sound";
        public static final String DISABLED_SOUND = "disabled_sound";

        private float blockDelaySeconds = 0;
        private float disableCooldownScale = 1;
        private List<DamageReduction> damageReductions = Collections.singletonList(new DamageReduction(90, null, 0, 1));
        private ItemDamageFunction itemDamage = null;
        private TagKey bypassedBy = null;
        private Holder<Types_v1_20_5.SoundEvent> blockSound = null;
        private Holder<Types_v1_20_5.SoundEvent> disabledSound = null;


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class DamageReduction {
            public static final String HORIZONTAL_BLOCKING_ANGLE = "horizontal_blocking_angle";
            public static final String TYPE = "type";
            public static final String BASE = "base";
            public static final String FACTOR = "factor";

            private float horizontalBlockingAngle = 90;
            private TagEntryList type = null;
            private float base;
            private float factor;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ItemDamageFunction {
            public static final String THRESHOLD = "threshold";
            public static final String BASE = "base";
            public static final String FACTOR = "factor";

            private float threshold;
            private float base;
            private float factor;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TooltipDisplay {
        public static final String HIDE_TOOLTIP = "hide_tooltip";
        public static final String HIDDEN_COMPONENTS = "hidden_components";

        private boolean hideTooltip = false;
        private List<ItemComponent<?>> hiddenComponents = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArmorTrimMaterial {
        public static final String ASSET_NAME = "asset_name";
        public static final String OVERRIDE_ARMOR_ASSETS = "override_armor_assets";
        public static final String DESCRIPTION = "description";

        private String assetName;
        private Map<RegistryEntry, String> overrideArmorAssets;
        private TextComponent description;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArmorTrimPattern {
        public static final String ASSET_ID = "asset_id";
        public static final String DESCRIPTION = "description";
        public static final String DECAL = "decal";

        private Identifier assetId;
        private TextComponent description;
        private boolean decal = false;

        public ArmorTrimPattern(final Identifier assetId, final TextComponent description) {
            this.assetId = assetId;
            this.description = description;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArmorTrim {
        public static final String MATERIAL = "material";
        public static final String PATTERN = "pattern";

        private Holder<ArmorTrimMaterial> material;
        private Holder<ArmorTrimPattern> pattern;
    }

}
