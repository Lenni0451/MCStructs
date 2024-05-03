package net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.itemcomponents.serialization.Named;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.ATextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public enum Rarity implements Named {
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

        private List<Page> pages;

        public void addPage(final String raw) {
            this.addPage(new Page(raw));
        }

        public void addPage(final String raw, final String filtered) {
            this.addPage(new Page(raw, filtered));
        }

        public void addPage(final Page page) {
            if (this.pages.size() >= MAX_PAGES) throw new IllegalArgumentException("Book can only have " + MAX_PAGES + " pages");
            this.pages.add(page);
        }

        public void removePage(final int index) {
            this.pages.remove(index);
        }

        public void removePage(final String raw) {
            this.pages.removeIf(page -> page.getRaw().equals(raw));
        }

        public void removePage(final Page page) {
            this.pages.remove(page);
        }


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Page {
            private String raw;
            @Nullable
            private String filtered;

            public Page(final String raw) {
                this(raw, null);
            }
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
        public enum ExplosionShape implements Named {
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
    public static class BannerPattern {
        public static final String PATTERN = "pattern";
        public static final String COLOR = "color";

        private Pattern pattern;
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
    public enum DyeColor implements Named {
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

}
