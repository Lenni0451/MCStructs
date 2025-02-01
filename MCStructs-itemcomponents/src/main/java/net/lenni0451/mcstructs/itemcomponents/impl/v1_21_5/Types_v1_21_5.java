package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.converter.model.Either;
import net.lenni0451.mcstructs.converter.types.NamedType;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.ItemComponent;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_21.Types_v1_21;
import net.lenni0451.mcstructs.text.TextComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public enum VillagerVariant implements NamedType {
        DESERT("desert"),
        JUNGLE("jungle"),
        PLAINS("plains"),
        SAVANNA("savanna"),
        SNOW("snow"),
        SWAMP("swamp"),
        TAIGA("taiga");

        private final String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WolfVariant {
        public static final String WILD_TEXTURE = "wild_texture";
        public static final String TAME_TEXTURE = "tame_texture";
        public static final String ANGRY_TEXTURE = "angry_texture";
        public static final String BIOMES = "biomes";

        private Identifier wildTexture;
        private Identifier tameTexture;
        private Identifier angryTexture;
        private Types_v1_20_5.TagEntryList biomes;
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
        KOB("kob"),
        SUNSTREAK("sunstreak"),
        SNOOPER("snooper"),
        DASHER("dasher"),
        BRINELY("brinely"),
        SPOTTY("spotty"),
        FLOPPER("flopper"),
        STRIPEY("stripey"),
        GLITTER("glitter"),
        BLOCKFISH("blockfish"),
        BETTY("betty"),
        CLAYFISH("clayfish");

        private final String name;
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
        BROWN("brown"),
        WHITE("white"),
        BLACK("black"),
        WHITE_SPLOTCHED("white_splotched"),
        GOLD("gold"),
        SALT("salt"),
        EVIL("evil");

        private final String name;
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
    public static class PigVariant {
        public static final String MODEL = "model";
        public static final String TEXTURE = "texture";
        public static final String BIOMES = "biomes";

        private ModelType model = ModelType.NORMAL;
        private Identifier texture;
        private Types_v1_20_5.TagEntryList biomes;


        @Getter
        @AllArgsConstructor
        public enum ModelType implements NamedType {
            NORMAL("normal"),
            COLD("cold");

            private final String name;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CowVariant {
        public static final String SPAWN_CONDITIONS = "spawn_conditions";

        private ModelAndTexture modelAndTexture;
        private List<SpawnCondition> spawnConditions;


        @Getter
        @AllArgsConstructor
        public enum ModelType implements NamedType {
            NORMAL("normal"),
            COLD("cold"),
            WARM("warm");

            private final String name;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ModelAndTexture {
            public static final String MODEL = "model";
            public static final String ASSET_ID = "asset_id";

            private ModelType model;
            private Identifier assetId;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SpawnCondition {
            public static final String CONDITION = "condition";
            public static final String PRIORITY = "priority";

            private Identifier condition;
            private int priority;
        }
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
        private Identifier bypassedBy = null;
        private Either<Identifier, Types_v1_20_5.SoundEvent> blockSound = null;
        private Either<Identifier, Types_v1_20_5.SoundEvent> disabledSound = null;


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class DamageReduction {
            public static final String HORIZONTAL_BLOCKING_ANGLE = "horizontal_blocking_angle";
            public static final String TYPE = "type";
            public static final String BASE = "base";
            public static final String FACTOR = "factor";

            private float horizontalBlockingAngle = 90;
            private Types_v1_20_5.TagEntryList type = null;
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
    public static class AttributeModifiers {
        public static final String MODIFIERS = "modifiers";

        private List<Types_v1_21.AttributeModifier> modifiers;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArmorTrim {
        public static final String MATERIAL = "material";
        public static final String PATTERN = "pattern";

        private Either<Identifier, Types_v1_20_5.ArmorTrimMaterial> material;
        private Either<Identifier, Types_v1_20_5.ArmorTrimPattern> pattern;
    }

}
