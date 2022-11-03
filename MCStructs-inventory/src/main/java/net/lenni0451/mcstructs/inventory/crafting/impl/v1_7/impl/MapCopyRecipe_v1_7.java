package net.lenni0451.mcstructs.inventory.crafting.impl.v1_7.impl;

import net.lenni0451.mcstructs.inventory.crafting.IRecipe;
import net.lenni0451.mcstructs.inventory.types.ICraftingInventory;
import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundNbt;

public class MapCopyRecipe_v1_7<I> implements IRecipe<I, LegacyItemStack<I>> {

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
        if (this.hasDisplayName(filledMapStack)) this.setDisplayName(result, this.getDisplayName(filledMapStack));
        return result;
    }

    private boolean hasDisplayName(final LegacyItemStack<I> stack) {
        if (!stack.hasTag()) return false;
        if (!stack.getTag().contains("display", NbtType.COMPOUND)) return false;
        return stack.getTag().getCompound("display").contains("Name", NbtType.STRING);
    }

    private String getDisplayName(final LegacyItemStack<I> stack) {
        if (!this.hasDisplayName(stack)) return null;
        return stack.getTag().getCompound("display").getString("Name");
    }

    private void setDisplayName(final LegacyItemStack<I> stack, final String name) {
        CompoundNbt tag = stack.getOrCreateTag();
        if (!tag.contains("display", NbtType.COMPOUND)) tag.add("display", new CompoundNbt());
        CompoundNbt display = tag.getCompound("display");
        display.addString("Name", name);
    }

}
