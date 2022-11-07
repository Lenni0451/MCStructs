package net.lenni0451.mcstructs.inventory.recipes.impl.v1_7.impl;

import net.lenni0451.mcstructs.inventory.recipes.ICraftingRecipe;
import net.lenni0451.mcstructs.inventory.types.ICraftingInventory;
import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.info.ItemTag;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundNbt;

import java.util.ArrayList;
import java.util.List;

public class ArmorColorCraftingRecipe_v1_7<I> implements ICraftingRecipe<I, LegacyItemStack<I>> {

    private static final int[][] DYE_COLOR = {
            {25, 25, 25}, //ink sac
            {153, 51, 51}, //rose red
            {102, 127, 51}, //cactus green
            {102, 76, 51}, //cocoa beans
            {51, 76, 178}, //lapis lazuli
            {127, 63, 178}, //purple dye
            {76, 127, 153}, //cyan dye
            {153, 153, 153}, //light gray dye
            {76, 76, 76}, //gray dye
            {242, 127, 165}, //pink dye
            {127, 204, 25}, //lime dye
            {229, 229, 51}, //dandelion yellow
            {102, 153, 216}, //light blue dye
            {178, 76, 216}, //magenta dye
            {216, 127, 51}, //orange dye
            {255, 255, 255}, //bone meal
    };

    @Override
    public boolean matches(ItemRegistry<I, LegacyItemStack<I>> itemRegistry, ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        LegacyItemStack<I> armorStack = null;
        List<I> colors = new ArrayList<>();
        for (int i = 0; i < craftingInventory.getSize(); i++) {
            LegacyItemStack<I> stack = craftingInventory.getStack(i);
            if (stack == null) continue;

            if (ItemType.isArmor(stack.getMeta().types())) {
                if (armorStack != null) return false;
                if (!stack.getMeta().tags().contains(ItemTag.MATERIAL_LEATHER)) return false;
                armorStack = stack;
            } else if (stack.getMeta().types().contains(ItemType.DYE)) {
                colors.add(stack.getItem());
            } else {
                return false;
            }
        }
        return armorStack != null && !colors.isEmpty();
    }

    @Override
    public LegacyItemStack<I> getResult(ItemRegistry<I, LegacyItemStack<I>> itemRegistry, ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        LegacyItemStack<I> armorStack = null;
        int[] armorColor = new int[3];
        int darkness = 0;
        int colorCount = 0;
        for (int i = 0; i < craftingInventory.getSize(); i++) {
            LegacyItemStack<I> stack = craftingInventory.getStack(i);
            if (stack == null) continue;

            if (ItemType.isArmor(stack.getMeta().types())) {
                if (armorStack != null) return null;
                if (!stack.getMeta().tags().contains(ItemTag.MATERIAL_LEATHER)) return null;
                armorStack = stack.copy();
                armorStack.setCount(1);

                if (this.hasColor(armorStack)) {
                    int color = this.getColor(armorStack);
                    float r = (color >> 16) & 255;
                    float g = (color >> 8) & 255;
                    float b = color & 255;
                    darkness += Math.max(r, Math.max(g, b));

                    armorColor[0] = (int) (armorColor[0] + r * 255F);
                    armorColor[1] = (int) (armorColor[1] + g * 255F);
                    armorColor[2] = (int) (armorColor[2] + b * 255F);
                    colorCount++;
                }
            } else if (stack.getMeta().types().contains(ItemType.DYE)) {
                int[] color = DYE_COLOR[stack.getDamage() & 15];
                int r = color[0];
                int g = color[1];
                int b = color[2];
                darkness += Math.max(r, Math.max(g, b));

                armorColor[0] += r;
                armorColor[1] += g;
                armorColor[2] += b;
                colorCount++;
            } else {
                return null;
            }
        }
        if (armorStack == null) return null;

        int r = armorColor[0] / colorCount;
        int g = armorColor[1] / colorCount;
        int b = armorColor[2] / colorCount;
        float brightness = (float) darkness / (float) colorCount;
        float endBrightness = (float) Math.max(r, Math.max(g, b));
        r = (int) (r * brightness / endBrightness);
        g = (int) (g * brightness / endBrightness);
        b = (int) (b * brightness / endBrightness);
        this.setColor(armorStack, (r << 16) | (g << 8) | b);
        return armorStack;
    }

    private boolean hasColor(final LegacyItemStack<I> stack) {
        if (!ItemType.isArmor(stack.getMeta().types())) return false;
        if (!stack.getMeta().tags().contains(ItemTag.MATERIAL_LEATHER)) return false;
        if (!stack.hasTag()) return false;
        if (!stack.getTag().contains("display", NbtType.COMPOUND)) return false;
        return stack.getTag().getCompound("display").contains("color", NbtType.INT);
    }

    private int getColor(final LegacyItemStack<I> stack) {
        if (!ItemType.isArmor(stack.getMeta().types())) return -1;
        if (!stack.getMeta().tags().contains(ItemTag.MATERIAL_LEATHER)) return -1;
        CompoundNbt tag = stack.getTag();
        if (tag == null) return 0xA0_65_40;
        CompoundNbt display = tag.getCompound("display");
        if (display == null) return 0xA0_65_40;
        if (!display.contains("color", NbtType.INT)) return 0xA0_65_40;
        return display.getInt("color");
    }

    private void setColor(final LegacyItemStack<I> stack, final int color) {
        if (!ItemType.isArmor(stack.getMeta().types()) || !stack.getMeta().tags().contains(ItemTag.MATERIAL_LEATHER)) {
            throw new IllegalArgumentException("The given stack is not a leather armor");
        }
        CompoundNbt tag = stack.getOrCreateTag();
        if (!tag.contains("display", NbtType.COMPOUND)) tag.add("display", new CompoundNbt());
        CompoundNbt display = tag.getCompound("display");
        display.addInt("color", color);
    }

}
