package net.lenni0451.mcstructs.itemcomponents.impl.v1_21_5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.converter.types.NamedType;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5.Types_v1_20_5;
import net.lenni0451.mcstructs.text.TextComponent;

import java.util.List;

public class Types_v1_21_5 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Weapon {
        public static final String ITEM_DAMAGE_PER_ATTACK = "item_damage_per_attack";
        public static final String CAN_DISABLE_BLOCKING = "can_disable_blocking";

        private int itemDamagePerAttack = 1;
        private boolean canDisableBlocking;
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

}
