package net.lenni0451.mcstructs.inventory.impl.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.inventory.types.ICraftingContainer;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.recipes.ICraftingInventory;

public class CraftingInventory_v1_7<I, S extends AItemStack<I, S>> implements IInventory_v1_7<I, S>, ICraftingInventory<I, S> {

    private final ICraftingContainer<I, S> craftingContainer;
    private final int width;
    private final int height;
    private final S[] stacks;

    public CraftingInventory_v1_7(final ICraftingContainer<I, S> craftingContainer, final int width, final int height) {
        this.craftingContainer = craftingContainer;
        this.width = width;
        this.height = height;
        this.stacks = (S[]) new AItemStack[width * height];
    }

    public ICraftingContainer<I, S> getCraftingContainer() {
        return this.craftingContainer;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public S[] getStacks() {
        return this.stacks;
    }

    @Override
    public int getSize() {
        return this.width * this.height;
    }

    @Override
    public S getStack(int slotId) {
        if (slotId >= this.stacks.length) return null;
        else return this.stacks[slotId];
    }

    @Override
    public void setStack(int slotId, S stack) {
        this.stacks[slotId] = stack;
        this.craftingContainer.craftingUpdate(this);
    }

    @Override
    public S split(int slotId, int count) {
        if (this.stacks[slotId] == null) return null;

        S stack = this.stacks[slotId];
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
    public S getStack(int row, int column) {
        if (row >= 0 && row < this.width) {
            int index = row + column * this.width;
            return this.getStack(index);
        }
        return null;
    }

}
