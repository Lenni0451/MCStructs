package net.lenni0451.mcstructs.inventory.impl.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.impl.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemTag;

public class PlayerInventory_v1_7<I, S extends AItemStack<I, S>> implements IInventory_v1_7<I, S> {

    /**
     * Slots 0 - 8: hotbar<br>
     * Slots 9 - 35: upper inventory<br>
     */
    private final S[] main = (S[]) new AItemStack[36];
    /**
     * Slot 0: boots<br>
     * Slot 1: leggings<br>
     * Slot 2: chestplate<br>
     * Slot 3: helmet
     */
    private final S[] armor = (S[]) new AItemStack[4];
    private S cursorStack;

    public S[] getMain() {
        return this.main;
    }

    public S[] getArmor() {
        return this.armor;
    }

    public S getCursorStack() {
        return this.cursorStack;
    }

    public void setCursorStack(final S cursorItem) {
        this.cursorStack = cursorItem;
    }

    public int getEmptySlot() {
        for (int i = 0; i < this.main.length; i++) {
            if (this.main[i] == null) return i;
        }
        return -1;
    }

    public <T extends PlayerInventory_v1_7<I, S>> boolean addStack(final InventoryHolder<T, I, S> inventoryHolder, S stack) {
        if (stack == null || stack.getCount() == 0 || stack.getItem() == null) return false;
        if (stack.getMeta().tags().contains(ItemTag.DAMAGEABLE) && stack.getDamage() > 0) {
            int emptySlot = this.getEmptySlot();
            if (emptySlot >= 0) {
                this.main[emptySlot] = stack.copy();
                stack.setCount(0);
                return true;
            } else if (inventoryHolder.isCreativeMode()) {
                stack.setCount(0);
                return true;
            } else {
                return false;
            }
        } else {
            int leftItems;
            do {
                leftItems = stack.getCount();
                stack.setCount(this.addItems(stack));
            } while (stack.getCount() > 0 && stack.getCount() < leftItems);
            if (stack.getCount() == leftItems && inventoryHolder.isCreativeMode()) {
                stack.setCount(0);
                return true;
            } else {
                return stack.getCount() < leftItems;
            }
        }
    }

    private int addItems(final S stack) {
        int count = stack.getCount();
        if (stack.getMeta().maxCount() == 1) {
            int emptySlot = this.getEmptySlot();
            if (emptySlot < 0) return count;
            if (this.main[emptySlot] == null) this.main[emptySlot] = stack.copy();
            return 0;
        } else {
            int matchingSlot = this.getMatchingSlot(stack);
            if (matchingSlot < 0) matchingSlot = this.getEmptySlot();
            if (matchingSlot < 0) return count;
            if (this.main[matchingSlot] == null) {
                this.main[matchingSlot] = stack.copy();
                this.main[matchingSlot].setCount(0);
            }
            int freeSpace = Math.min(count, this.main[matchingSlot].getMeta().maxCount() - this.main[matchingSlot].getCount());
            if (freeSpace > 64 - this.main[matchingSlot].getCount()) freeSpace = 64 - this.main[matchingSlot].getCount();
            if (freeSpace == 0) return count;
            count -= freeSpace;
            this.main[matchingSlot].setCount(this.main[matchingSlot].getCount() + freeSpace);
            return count;
        }
    }

    private int getMatchingSlot(final S stack) {
        for (int i = 0; i < this.main.length; i++) {
            S inventoryStack = this.main[i];
            if (inventoryStack != null && inventoryStack.getItem().equals(stack.getItem()) && inventoryStack.getMeta().maxCount() > 1 && inventoryStack.getCount() < inventoryStack.getMeta().maxCount() && (!inventoryStack.getMeta().tags().contains(ItemTag.SUBTYPES) || inventoryStack.getDamage() == stack.getDamage()) && inventoryStack.getTag().equals(stack.getTag())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSize() {
        return this.main.length + this.armor.length;
    }

    @Override
    public S getStack(int slotId) {
        if (slotId >= this.main.length) return this.armor[slotId - this.main.length];
        else return this.main[slotId];
    }

    @Override
    public void setStack(int slotId, S stack) {
        if (slotId >= this.main.length) this.armor[slotId - this.main.length] = stack;
        else this.main[slotId] = stack;
    }

    @Override
    public S split(int slotId, int count) {
        S[] stacks = this.main;
        if (slotId >= this.main.length) {
            stacks = this.armor;
            slotId -= this.main.length;
        }
        if (stacks[slotId] == null) return null;

        S stack = stacks[slotId];
        if (stacks[slotId].getCount() <= count) {
            stacks[slotId] = null;
        } else {
            stack = stack.split(count);
            if (stacks[slotId].getCount() == 0) stacks[slotId] = null;
        }
        return stack;
    }

}
