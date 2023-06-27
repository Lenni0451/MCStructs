package net.lenni0451.mcstructs.data.v1_7;

import java.util.ArrayList;
import java.util.List;

public class FurnaceRecipe_v1_7 {

    public static final List<FurnaceRecipe_v1_7> FURNACE_RECIPE_LIST = new ArrayList<>();

    public static final FurnaceRecipe_v1_7 iron_ingot = new FurnaceRecipe_v1_7(Item_v1_7.iron_ore, Item_v1_7.iron_ingot);
    public static final FurnaceRecipe_v1_7 gold_ingot = new FurnaceRecipe_v1_7(Item_v1_7.gold_ore, Item_v1_7.gold_ingot);
    public static final FurnaceRecipe_v1_7 diamond = new FurnaceRecipe_v1_7(Item_v1_7.diamond_ore, Item_v1_7.diamond);
    public static final FurnaceRecipe_v1_7 glass = new FurnaceRecipe_v1_7(Item_v1_7.sand, Item_v1_7.glass);
    public static final FurnaceRecipe_v1_7 cooked_porkchop = new FurnaceRecipe_v1_7(Item_v1_7.porkchop, Item_v1_7.cooked_porkchop);
    public static final FurnaceRecipe_v1_7 cooked_beef = new FurnaceRecipe_v1_7(Item_v1_7.beef, Item_v1_7.cooked_beef);
    public static final FurnaceRecipe_v1_7 cooked_chicken = new FurnaceRecipe_v1_7(Item_v1_7.chicken, Item_v1_7.cooked_chicken);
    public static final FurnaceRecipe_v1_7 stone = new FurnaceRecipe_v1_7(Item_v1_7.cobblestone, Item_v1_7.stone);
    public static final FurnaceRecipe_v1_7 brick = new FurnaceRecipe_v1_7(Item_v1_7.clay_ball, Item_v1_7.brick);
    public static final FurnaceRecipe_v1_7 hardened_clay = new FurnaceRecipe_v1_7(Item_v1_7.clay, Item_v1_7.hardened_clay);
    public static final FurnaceRecipe_v1_7 cactus_green = new FurnaceRecipe_v1_7(Item_v1_7.cactus, Item_v1_7.dye, 2);
    public static final FurnaceRecipe_v1_7 charcoal = new FurnaceRecipe_v1_7(Item_v1_7.log, Item_v1_7.coal, 1);
    public static final FurnaceRecipe_v1_7 charcoal2 = new FurnaceRecipe_v1_7(Item_v1_7.log2, Item_v1_7.coal, 1);
    public static final FurnaceRecipe_v1_7 emerald = new FurnaceRecipe_v1_7(Item_v1_7.emerald_ore, Item_v1_7.emerald);
    public static final FurnaceRecipe_v1_7 baked_potato = new FurnaceRecipe_v1_7(Item_v1_7.potato, Item_v1_7.baked_potato);
    public static final FurnaceRecipe_v1_7 netherbrick = new FurnaceRecipe_v1_7(Item_v1_7.netherrack, Item_v1_7.netherbrick);
    public static final FurnaceRecipe_v1_7 cod = new FurnaceRecipe_v1_7(Item_v1_7.fish, 0, Item_v1_7.fish, 0);
    public static final FurnaceRecipe_v1_7 salmon = new FurnaceRecipe_v1_7(Item_v1_7.fish, 1, Item_v1_7.fish, 1);
    public static final FurnaceRecipe_v1_7 coal = new FurnaceRecipe_v1_7(Item_v1_7.coal_ore, Item_v1_7.coal);
    public static final FurnaceRecipe_v1_7 redstone = new FurnaceRecipe_v1_7(Item_v1_7.redstone_ore, Item_v1_7.redstone);
    public static final FurnaceRecipe_v1_7 lapis = new FurnaceRecipe_v1_7(Item_v1_7.lapis_ore, Item_v1_7.dye, 4);
    public static final FurnaceRecipe_v1_7 quartz = new FurnaceRecipe_v1_7(Item_v1_7.quartz_ore, Item_v1_7.quartz);


    private final Item_v1_7 input;
    private final int inputDamage;
    private final Item_v1_7 output;
    private final int outputDamage;

    private FurnaceRecipe_v1_7(final Item_v1_7 input, final Item_v1_7 output) {
        this(input, 32767, output, 0);
    }

    private FurnaceRecipe_v1_7(final Item_v1_7 input, final Item_v1_7 output, final int outputDamage) {
        this(input, 32767, output, outputDamage);
    }

    private FurnaceRecipe_v1_7(final Item_v1_7 input, final int inputDamage, final Item_v1_7 output, final int outputDamage) {
        this.input = input;
        this.inputDamage = inputDamage;
        this.output = output;
        this.outputDamage = outputDamage;

        FURNACE_RECIPE_LIST.add(this);
    }

    public Item_v1_7 input() {
        return this.input;
    }

    public int inputDamage() {
        return this.inputDamage;
    }

    public Item_v1_7 output() {
        return this.output;
    }

    public int outputDamage() {
        return this.outputDamage;
    }

}
