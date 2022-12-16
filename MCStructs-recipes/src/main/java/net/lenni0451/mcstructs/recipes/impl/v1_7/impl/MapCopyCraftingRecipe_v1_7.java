package net.lenni0451.mcstructs.recipes.impl.v1_7.impl;

import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;
import net.lenni0451.mcstructs.recipes.ICraftingInventory;
import net.lenni0451.mcstructs.recipes.ICraftingRecipe;

public class MapCopyCraftingRecipe_v1_7<I> implements ICraftingRecipe<I, LegacyItemStack<I>> {

    @Override
    public boolean matches(ItemRegistry<I, LegacyItemStack<I>> itemRegistry, ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        LegacyItemStack<I> filledMapStack = null;
        int mapCount = 0;
        for (int i = 0; i < craftingInventory.getSize(); i++) {
            LegacyItemStack<I> stack = craftingInventory.getStack(i);
            if (stack == null) continue;

            if (stack.getMeta().types().contains(ItemType.FILLED_MAP)) {
                if (filledMapStack != null) return false;
                filledMapStack = stack;
            } else if (stack.getMeta().types().contains(ItemType.MAP)) {
                mapCount++;
            } else {
                return false;
            }
        }
        return filledMapStack != null && mapCount >= 1;
    }

    @Override
    public LegacyItemStack<I> getResult(ItemRegistry<I, LegacyItemStack<I>> itemRegistry, ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        LegacyItemStack<I> filledMapStack = null;
        int mapCount = 0;
        for (int i = 0; i < craftingInventory.getSize(); i++) {
            LegacyItemStack<I> stack = craftingInventory.getStack(i);
            if (stack == null) continue;

            if (stack.getMeta().types().contains(ItemType.FILLED_MAP)) {
                if (filledMapStack != null) return null;
                filledMapStack = stack;
            } else if (stack.getMeta().types().contains(ItemType.MAP)) {
                mapCount++;
            } else {
                return null;
            }
        }
        if (filledMapStack == null || mapCount < 1) return null;

        LegacyItemStack<I> result = itemRegistry.create(filledMapStack.getItem(), mapCount + 1, filledMapStack.getDamage());
        if (filledMapStack.hasCustomName()) result.setCustomName(filledMapStack.getCustomName());
        return result;
    }

}
