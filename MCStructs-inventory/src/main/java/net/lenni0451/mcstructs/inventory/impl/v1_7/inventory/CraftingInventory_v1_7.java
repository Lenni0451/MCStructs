package net.lenni0451.mcstructs.inventory.impl.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.inventory.types.ICraftingContainer;
import net.lenni0451.mcstructs.inventory.types.ICraftingInventory;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class CraftingInventory_v1_7<I> implements IInventory_v1_7<I>, ICraftingInventory<I, LegacyItemStack<I>> {

    private final ICraftingContainer<I, LegacyItemStack<I>> craftingContainer;
    private final int width;
    private final int height;
    private final LegacyItemStack<I>[] stacks;

    public CraftingInventory_v1_7(final ICraftingContainer<I, LegacyItemStack<I>> craftingContainer, final int width, final int height) {
        this.craftingContainer = craftingContainer;
        this.width = width;
        this.height = height;
        this.stacks = new LegacyItemStack[width * height];
    }

    public ICraftingContainer<I, LegacyItemStack<I>> getCraftingContainer() {
        return this.craftingContainer;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public LegacyItemStack<I>[] getStacks() {
        return this.stacks;
    }

    @Override
    public int getSize() {
        return this.width * this.height;
    }

    @Override
    public LegacyItemStack<I> getStack(int slotId) {
        if (slotId >= this.stacks.length) return null;
        else return this.stacks[slotId];
    }

    @Override
    public void setStack(int slotId, LegacyItemStack<I> stack) {
        this.stacks[slotId] = stack;
        this.craftingContainer.craftingUpdate(this);
    }

    @Override
    public LegacyItemStack<I> split(int slotId, int count) {
        if (this.stacks[slotId] == null) return null;

        LegacyItemStack<I> stack = this.stacks[slotId];
        if (stack.getCount() <= count) {
            this.stacks[slotId] = null;
        } else {
            stack = stack.split(count);
            if (this.stacks[slotId].getCount() == 0) this.stacks[slotId] = null;
        }
        this.craftingContainer.craftingUpdate(this);
        return stack;
    }

    @Override
    public LegacyItemStack<I> getStack(int row, int column) {
        if (row >= 0 && row < this.width) {
            int index = row + column * this.width;
            return this.getStack(index);
        }
        return null;
    }

}
