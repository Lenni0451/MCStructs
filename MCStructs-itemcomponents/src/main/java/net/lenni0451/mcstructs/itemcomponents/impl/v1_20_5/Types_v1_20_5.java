package net.lenni0451.mcstructs.itemcomponents.impl.v1_20_5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.core.TextFormatting;
import net.lenni0451.mcstructs.itemcomponents.ItemComponentMap;
import net.lenni0451.mcstructs.text.ATextComponent;

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
    public enum Rarity {
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

}
