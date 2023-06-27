package net.lenni0451.mcstructs.data.v1_8;

import java.util.ArrayList;
import java.util.List;

public class FurnaceRecipe_v1_8 {

    public static final List<FurnaceRecipe_v1_8> FURNACE_RECIPE_LIST = new ArrayList<>();

    public static final FurnaceRecipe_v1_8 charcoal = new FurnaceRecipe_v1_8(Item_v1_8.log, Item_v1_8.coal, 1);
    public static final FurnaceRecipe_v1_8 stone = new FurnaceRecipe_v1_8(Item_v1_8.cobblestone, Item_v1_8.stone);
    public static final FurnaceRecipe_v1_8 coal = new FurnaceRecipe_v1_8(Item_v1_8.coal_ore, Item_v1_8.coal);
    public static final FurnaceRecipe_v1_8 redstone = new FurnaceRecipe_v1_8(Item_v1_8.redstone_ore, Item_v1_8.redstone);
    public static final FurnaceRecipe_v1_8 cooked_beef = new FurnaceRecipe_v1_8(Item_v1_8.beef, Item_v1_8.cooked_beef);
    public static final FurnaceRecipe_v1_8 gold_ingot = new FurnaceRecipe_v1_8(Item_v1_8.gold_ore, Item_v1_8.gold_ingot);
    public static final FurnaceRecipe_v1_8 cactus_green = new FurnaceRecipe_v1_8(Item_v1_8.cactus, Item_v1_8.dye, 2);
    public static final FurnaceRecipe_v1_8 quartz = new FurnaceRecipe_v1_8(Item_v1_8.quartz_ore, Item_v1_8.quartz);
    public static final FurnaceRecipe_v1_8 hardened_clay = new FurnaceRecipe_v1_8(Item_v1_8.clay, Item_v1_8.hardened_clay);
    public static final FurnaceRecipe_v1_8 netherbrick = new FurnaceRecipe_v1_8(Item_v1_8.netherrack, Item_v1_8.netherbrick);
    public static final FurnaceRecipe_v1_8 emerald = new FurnaceRecipe_v1_8(Item_v1_8.emerald_ore, Item_v1_8.emerald);
    public static final FurnaceRecipe_v1_8 diamond = new FurnaceRecipe_v1_8(Item_v1_8.diamond_ore, Item_v1_8.diamond);
    public static final FurnaceRecipe_v1_8 cooked_porkchop = new FurnaceRecipe_v1_8(Item_v1_8.porkchop, Item_v1_8.cooked_porkchop);
    public static final FurnaceRecipe_v1_8 charcoal2 = new FurnaceRecipe_v1_8(Item_v1_8.log2, Item_v1_8.coal, 1);
    public static final FurnaceRecipe_v1_8 cooked_salmon = new FurnaceRecipe_v1_8(Item_v1_8.fish, 1, Item_v1_8.cooked_fish, 1);
    public static final FurnaceRecipe_v1_8 lapis = new FurnaceRecipe_v1_8(Item_v1_8.lapis_ore, Item_v1_8.dye, 4);
    public static final FurnaceRecipe_v1_8 baked_potato = new FurnaceRecipe_v1_8(Item_v1_8.potato, Item_v1_8.baked_potato);
    public static final FurnaceRecipe_v1_8 cooked_mutton = new FurnaceRecipe_v1_8(Item_v1_8.mutton, Item_v1_8.cooked_mutton);
    public static final FurnaceRecipe_v1_8 glass = new FurnaceRecipe_v1_8(Item_v1_8.sand, Item_v1_8.glass);
    public static final FurnaceRecipe_v1_8 sponge = new FurnaceRecipe_v1_8(Item_v1_8.sponge, 1, Item_v1_8.sponge, 0);
    public static final FurnaceRecipe_v1_8 cooked_chicken = new FurnaceRecipe_v1_8(Item_v1_8.chicken, Item_v1_8.cooked_chicken);
    public static final FurnaceRecipe_v1_8 cooked_rabbit = new FurnaceRecipe_v1_8(Item_v1_8.rabbit, Item_v1_8.cooked_rabbit);
    public static final FurnaceRecipe_v1_8 brick = new FurnaceRecipe_v1_8(Item_v1_8.clay_ball, Item_v1_8.brick);
    public static final FurnaceRecipe_v1_8 cooked_cod = new FurnaceRecipe_v1_8(Item_v1_8.fish, Item_v1_8.cooked_fish);
    public static final FurnaceRecipe_v1_8 cracked_stonebrick = new FurnaceRecipe_v1_8(Item_v1_8.stonebrick, Item_v1_8.stonebrick, 2);
    public static final FurnaceRecipe_v1_8 iron_ingot = new FurnaceRecipe_v1_8(Item_v1_8.iron_ore, Item_v1_8.iron_ingot);


    private final Item_v1_8 input;
    private final int inputDamage;
    private final Item_v1_8 output;
    private final int outputDamage;

    private FurnaceRecipe_v1_8(final Item_v1_8 input, final Item_v1_8 output) {
        this(input, 32767, output, 0);
    }

    private FurnaceRecipe_v1_8(final Item_v1_8 input, final Item_v1_8 output, final int outputDamage) {
        this(input, 32767, output, outputDamage);
    }

    private FurnaceRecipe_v1_8(final Item_v1_8 input, final int inputDamage, final Item_v1_8 output, final int outputDamage) {
        this.input = input;
        this.inputDamage = inputDamage;
        this.output = output;
        this.outputDamage = outputDamage;

        FURNACE_RECIPE_LIST.add(this);
    }

    public Item_v1_8 input() {
        return this.input;
    }

    public int inputDamage() {
        return this.inputDamage;
    }

    public Item_v1_8 output() {
        return this.output;
    }

    public int outputDamage() {
        return this.outputDamage;
    }

}

/*
for (Map.Entry<ItemStack, ItemStack> entry : this.smeltingList.entrySet()) {
    String input = Item.itemRegistry.getNameForObject(entry.getKey().getItem()).getResourcePath();
    int inputDamage = entry.getKey().getItemDamage() == 32767 ? 0 : entry.getKey().getItemDamage();
    String output = Item.itemRegistry.getNameForObject(entry.getValue().getItem()).getResourcePath();
    int outputDamage = entry.getValue().getItemDamage() == 32767 ? 0 : entry.getValue().getItemDamage();
    String out = "public static final FurnaceRecipe_v1_8 " + output + " = new FurnaceRecipe_v1_8(Item_v1_8." + input;
    if (inputDamage == 0 && outputDamage == 0) {
        out += ", Item_v1_8." + output + ");";
    } else if (inputDamage == 0) {
        out += ", Item_v1_8." + output + ", " + outputDamage + ");";
    } else {
        out += ", " + inputDamage + ", Item_v1_8." + output + ", " + outputDamage + ");";
    }
    System.out.println(out);
}
 */
