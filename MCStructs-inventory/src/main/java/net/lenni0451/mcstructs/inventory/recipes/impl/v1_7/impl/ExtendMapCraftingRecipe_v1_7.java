package net.lenni0451.mcstructs.inventory.recipes.impl.v1_7.impl;

import net.lenni0451.mcstructs.inventory.types.ICraftingInventory;
import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

import java.util.function.Function;

public class ExtendMapCraftingRecipe_v1_7<I> extends ShapedCraftingRecipe_v1_7<I> {

    private final Function<LegacyItemStack<I>, Integer> mapScaleProvider;

    public ExtendMapCraftingRecipe_v1_7(final ItemRegistry<I, LegacyItemStack<I>> itemRegistry, final Function<LegacyItemStack<I>, Integer> mapScaleProvider) {
        super(3, 3, new LegacyItemStack[]{
                itemRegistry.create(itemRegistry.requireByType(ItemType.PAPER)),
                itemRegistry.create(itemRegistry.requireByType(ItemType.PAPER)),
                itemRegistry.create(itemRegistry.requireByType(ItemType.PAPER)),
                itemRegistry.create(itemRegistry.requireByType(ItemType.PAPER)),
                itemRegistry.create(itemRegistry.requireByType(ItemType.FILLED_MAP), 0, 32767),
                itemRegistry.create(itemRegistry.requireByType(ItemType.PAPER)),
                itemRegistry.create(itemRegistry.requireByType(ItemType.PAPER)),
                itemRegistry.create(itemRegistry.requireByType(ItemType.PAPER)),
                itemRegistry.create(itemRegistry.requireByType(ItemType.PAPER))
        }, itemRegistry.create(itemRegistry.requireByType(ItemType.MAP)));
        this.mapScaleProvider = mapScaleProvider;
    }

    @Override
    public boolean matches(ItemRegistry<I, LegacyItemStack<I>> itemRegistry, ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        if (!super.matches(itemRegistry, craftingInventory)) return false;

        LegacyItemStack<I> mapStack = null;
        for (int i = 0; i < craftingInventory.getSize(); i++) {
            LegacyItemStack<I> stack = craftingInventory.getStack(i);
            if (stack == null) continue;

            if (stack.getMeta().types().contains(ItemType.FILLED_MAP)) {
                mapStack = stack;
                break;
            }
        }
        if (mapStack == null) return false;

        return this.mapScaleProvider.apply(mapStack) < 4;
    }

    @Override
    public LegacyItemStack<I> getResult(ItemRegistry<I, LegacyItemStack<I>> itemRegistry, ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        LegacyItemStack<I> mapStack = null;
        for (int i = 0; i < craftingInventory.getSize(); i++) {
            LegacyItemStack<I> stack = craftingInventory.getStack(i);
            if (stack == null) continue;

            if (stack.getMeta().types().contains(ItemType.FILLED_MAP)) {
                mapStack = stack;
                break;
            }
        }

        mapStack = mapStack.copy();
        mapStack.setCount(1);
        mapStack.getOrCreateTag().addByte("map_is_scaling", (byte) 1);
        return mapStack;
    }

}
