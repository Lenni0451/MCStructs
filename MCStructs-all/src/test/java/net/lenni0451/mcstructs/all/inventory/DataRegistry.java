package net.lenni0451.mcstructs.all.inventory;

import net.lenni0451.mcstructs.data.v1_7.CraftingRecipe_v1_7;
import net.lenni0451.mcstructs.data.v1_7.Enchantment_v1_7;
import net.lenni0451.mcstructs.data.v1_7.FurnaceRecipe_v1_7;
import net.lenni0451.mcstructs.data.v1_7.Item_v1_7;
import net.lenni0451.mcstructs.enchantments.Enchantment;
import net.lenni0451.mcstructs.enchantments.EnchantmentRegistry;
import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.info.ItemMeta;
import net.lenni0451.mcstructs.items.info.ItemTag;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;
import net.lenni0451.mcstructs.recipes.impl.v1_7.RecipeRegistry_v1_7;
import net.lenni0451.mcstructs.recipes.impl.v1_7.impl.ShapedCraftingRecipe_v1_7;
import net.lenni0451.mcstructs.recipes.impl.v1_7.impl.ShapelessCraftingRecipe_v1_7;

/**
 * This data registry uses the v1_7 data classes to register all data<br>
 * If you already have the required data anywhere else you have to do the conversion yourself
 */
public class DataRegistry {

    /**
     * Register all items with their types and tags
     *
     * @param itemRegistry The item registry
     */
    public static void register(final ItemRegistry<Integer, LegacyItemStack<Integer>> itemRegistry) {
        for (Item_v1_7 item : Item_v1_7.ITEM_LIST) {
            ItemMeta meta = itemRegistry.getMeta(item.itemId());
            if (item.hasSubtypes()) meta.addTags(ItemTag.SUBTYPES);
            if (item.isDamageable()) meta.addTags(ItemTag.DAMAGEABLE);
            if (item.burnTime() > 0) meta.addTags(ItemTag.FURNACE_FUEL);
            if (item.isPotionIngredient()) meta.addTags(ItemTag.POTION_INGREDIENT);
            meta.setMaxCount(item.maxStackSize());
            meta.setMaxDamage(item.maxDamage());
            if (item.name().contains("boots")) applyMaterial(meta.addTypes(ItemType.BOOTS), item.name());
            else if (item.name().contains("leggings")) applyMaterial(meta.addTypes(ItemType.LEGGINGS), item.name());
            else if (item.name().contains("chestplate")) applyMaterial(meta.addTypes(ItemType.CHESTPLATE), item.name());
            else if (item.name().contains("helmet")) applyMaterial(meta.addTypes(ItemType.HELMET), item.name());
            else if (item.name().contains("horse_armor")) applyMaterial(meta.addTypes(ItemType.HORSE_ARMOR), item.name());
            else if (item.name().contains("sword")) applyMaterial(meta.addTypes(ItemType.SWORD), item.name());
            else if (item.name().contains("pickaxe")) applyMaterial(meta.addTypes(ItemType.PICKAXE), item.name());
            else if (item.name().contains("axe")) applyMaterial(meta.addTypes(ItemType.AXE), item.name());
            else if (item.name().contains("shovel")) applyMaterial(meta.addTypes(ItemType.SHOVEL), item.name());
            else if (item.name().contains("hoe")) applyMaterial(meta.addTypes(ItemType.HOE), item.name());
            else if (item.equals(Item_v1_7.bucket)) meta.addTypes(ItemType.BUCKET);
            else if (item.equals(Item_v1_7.water_bucket)) meta.addTypes(ItemType.WATER_BUCKET);
            else if (item.equals(Item_v1_7.lava_bucket)) meta.addTypes(ItemType.LAVA_BUCKET);
            else if (item.equals(Item_v1_7.milk_bucket)) meta.addTypes(ItemType.MILK_BUCKET);
            else if (item.equals(Item_v1_7.skull)) meta.addTypes(ItemType.SKULL);
            else if (item.equals(Item_v1_7.pumpkin)) meta.addTypes(ItemType.PUMPKIN);
            else if (item.equals(Item_v1_7.written_book)) meta.addTypes(ItemType.WRITTEN_BOOK);
            else if (item.equals(Item_v1_7.writable_book)) meta.addTypes(ItemType.WRITABLE_BOOK);
            else if (item.equals(Item_v1_7.dye)) meta.addTypes(ItemType.DYE);
            else if (item.equals(Item_v1_7.map)) meta.addTypes(ItemType.MAP);
            else if (item.equals(Item_v1_7.filled_map)) meta.addTypes(ItemType.FILLED_MAP);
            else if (item.equals(Item_v1_7.gunpowder)) meta.addTypes(ItemType.GUNPOWDER);
            else if (item.equals(Item_v1_7.firework_charge)) meta.addTypes(ItemType.FIREWORK_STAR);
            else if (item.equals(Item_v1_7.paper)) meta.addTypes(ItemType.PAPER);
            else if (item.equals(Item_v1_7.glowstone_dust)) meta.addTypes(ItemType.GLOWSTONE_DUST);
            else if (item.equals(Item_v1_7.diamond)) meta.addTypes(ItemType.DIAMOND);
            else if (item.equals(Item_v1_7.fire_charge)) meta.addTypes(ItemType.FIRE_CHARGE);
            else if (item.equals(Item_v1_7.feather)) meta.addTypes(ItemType.FEATHER);
            else if (item.equals(Item_v1_7.gold_nugget)) meta.addTypes(ItemType.GOLD_NUGGET);
            else if (item.equals(Item_v1_7.fireworks)) meta.addTypes(ItemType.FIREWORK_ROCKET);
            else if (item.equals(Item_v1_7.glass_bottle)) meta.addTypes(ItemType.BOTTLE);
            else if (item.equals(Item_v1_7.potion)) meta.addTypes(ItemType.WATER_BOTTLE).addTypes(ItemType.POTION).addTypes(ItemType.SPLASH_POTION);
            else if (item.equals(Item_v1_7.fish)) meta.addTypes(ItemType.FISH);
            else if (item.equals(Item_v1_7.iron_ingot)) meta.addTypes(ItemType.IRON_INGOT);
            else if (item.equals(Item_v1_7.gold_ingot)) meta.addTypes(ItemType.GOLD_INGOT);
            else if (item.equals(Item_v1_7.emerald)) meta.addTypes(ItemType.EMERALD);
            else if (item.equals(Item_v1_7.enchanted_book)) meta.addTypes(ItemType.ENCHANTED_BOOK);
            else if (item.equals(Item_v1_7.leather)) meta.addTypes(ItemType.LEATHER);
            else if (item.equals(Item_v1_7.planks)) meta.addTypes(ItemType.PLANKS);
            else if (item.equals(Item_v1_7.cobblestone)) meta.addTypes(ItemType.COBBLESTONE);
            else if (item.equals(Item_v1_7.bow)) meta.addTypes(ItemType.BOW);
            else if (item.equals(Item_v1_7.fishing_rod)) meta.addTypes(ItemType.FISHING_ROD);
            else if (item.equals(Item_v1_7.saddle)) meta.addTypes(ItemType.SADDLE);
        }
    }

    /**
     * Register all crafting recipes and furnace recipes<br>
     * Villager recipes are sent by the server, so we can't register them here
     *
     * @param recipeRegistry The recipe registry
     */
    public static void register(final RecipeRegistry_v1_7<Integer> recipeRegistry, final ItemRegistry<Integer, LegacyItemStack<Integer>> itemRegistry) {
        for (CraftingRecipe_v1_7 recipe : CraftingRecipe_v1_7.CRAFTING_RECIPE_LIST) {
            Object[][] ingredients = recipe.inputStacks();
            LegacyItemStack<Integer>[] input = new LegacyItemStack[ingredients.length];
            for (int i = 0; i < ingredients.length; i++) input[i] = convert(itemRegistry, ingredients[i]);
            if (recipe.isShapeless()) {
                recipeRegistry.registerCraftingRecipe(new ShapelessCraftingRecipe_v1_7<>(input, convert(itemRegistry, recipe.outputStack())));
            } else {
                recipeRegistry.registerCraftingRecipe(new ShapedCraftingRecipe_v1_7<>(recipe.width(), recipe.height(), input, convert(itemRegistry, recipe.outputStack())));
            }
        }
        for (FurnaceRecipe_v1_7 recipe : FurnaceRecipe_v1_7.FURNACE_RECIPE_LIST) {
            recipeRegistry.registerFurnaceRecipe(
                    itemRegistry.create(recipe.input().itemId(), 1, recipe.inputDamage()),
                    itemRegistry.create(recipe.output().itemId(), 1, recipe.outputDamage())
            );
        }
    }

    /**
     * Register all enchantments and item/enchantment incompatibilities
     *
     * @param enchantmentRegistry The enchantment registry
     */
    public static void register(final EnchantmentRegistry<Integer, LegacyItemStack<Integer>> enchantmentRegistry) {
        for (Enchantment_v1_7 enchantment : Enchantment_v1_7.ENCHANTMENT_LIST) {
            Enchantment ench = convert(enchantment);
            String category = enchantment.category();

            enchantmentRegistry.register(ench);
            if (Enchantment_v1_7.INCOMPATIBILITIES.containsKey(enchantment)) {
                for (Enchantment_v1_7 incompatible : Enchantment_v1_7.INCOMPATIBILITIES.get(enchantment)) {
                    enchantmentRegistry.addIncompatibility(ench, convert(incompatible));
                }
            }
            enchantmentRegistry.addItemCompatibility(ench, item -> {
                switch (category) {
                    case "all":
                        return true;
                    case "armor":
                        return ItemType.isArmor(item.getMeta().getTypes());
                    case "armor_feet":
                        return item.getMeta().getTypes().contains(ItemType.BOOTS);
                    case "armor_legs":
                        return item.getMeta().getTypes().contains(ItemType.LEGGINGS);
                    case "armor_torso":
                        return item.getMeta().getTypes().contains(ItemType.CHESTPLATE);
                    case "armor_head":
                        return item.getMeta().getTypes().contains(ItemType.HELMET);
                    case "weapon":
                        return item.getMeta().getTypes().contains(ItemType.SWORD);
                    case "digger":
                        return item.getMeta().getTypes().contains(ItemType.PICKAXE) || item.getMeta().getTypes().contains(ItemType.AXE) || item.getMeta().getTypes().contains(ItemType.SHOVEL);
                    case "fishing_rod":
                        return item.getMeta().getTypes().contains(ItemType.FISHING_ROD);
                    case "breakable":
                        return item.getMeta().getTags().contains(ItemTag.DAMAGEABLE);
                    case "bow":
                        return item.getMeta().getTypes().contains(ItemType.BOW);
                    default:
                        return null;
                }
            });
        }
    }

    /**
     * Get the armor/tool material from the item name
     *
     * @param meta The item meta
     * @param name The item name
     */
    private static void applyMaterial(final ItemMeta meta, final String name) {
        if (name.startsWith("leather_")) meta.addTags(ItemTag.MATERIAL_LEATHER);
        else if (name.startsWith("wooden_")) meta.addTags(ItemTag.MATERIAL_WOOD);
        else if (name.startsWith("stone_")) meta.addTags(ItemTag.MATERIAL_STONE);
        else if (name.startsWith("iron_")) meta.addTags(ItemTag.MATERIAL_IRON);
        else if (name.startsWith("golden_")) meta.addTags(ItemTag.MATERIAL_GOLD);
        else if (name.startsWith("diamond_")) meta.addTags(ItemTag.MATERIAL_DIAMOND);
    }

    /**
     * Convert the Object[] of the crafting recipe list to a legacy item stack
     *
     * @param itemRegistry The item registry
     * @param stack        The Object[] stack
     * @return The legacy item stack
     */
    private static LegacyItemStack<Integer> convert(final ItemRegistry<Integer, LegacyItemStack<Integer>> itemRegistry, final Object[] stack) {
        if (stack == null) return null;
        return itemRegistry.create(((Item_v1_7) stack[0]).itemId(), (int) stack[1], (int) stack[2]);
    }

    /**
     * Convert the data enchantment instance to an enchantment instance
     *
     * @param enchantment The data enchantment
     * @return The enchantment
     */
    private static Enchantment convert(final Enchantment_v1_7 enchantment) {
        return new Enchantment(enchantment.name(), enchantment.id(), enchantment.minLevel(), enchantment.maxLevel(), enchantment.rarity());
    }

}
