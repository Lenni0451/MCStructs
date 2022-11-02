package net.lenni0451.mcstructs.inventory.v1_7.inventory;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.v1_7.IInventory_v1_7;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public class PlayerInventory_v1_7 implements IInventory_v1_7 {

    /**
     * Slots 0 - 8: hotbar<br>
     * Slots 9 - 35: upper inventory<br>
     */
    private final LegacyItemStack<?>[] main = new LegacyItemStack[36];
    /**
     * Slot 0: boots<br>
     * Slot 1: leggings<br>
     * Slot 2: chestplate<br>
     * Slot 3: helmet
     */
    private final LegacyItemStack<?>[] armor = new LegacyItemStack[4];
    private LegacyItemStack<?> cursorStack;

    public LegacyItemStack<?>[] getMain() {
        return this.main;
    }

    public LegacyItemStack<?>[] getArmor() {
        return this.armor;
    }

    public LegacyItemStack<?> getCursorStack() {
        return this.cursorStack;
    }

    public void setCursorStack(final LegacyItemStack<?> cursorItem) {
        this.cursorStack = cursorItem;
    }

    public int getEmptySlot() {
        for (int i = 0; i < this.main.length; i++) {
            if (this.main[i] == null) return i;
        }
        return -1;
    }

    public void addStack(final InventoryHolder<PlayerInventory_v1_7, LegacyItemStack<?>> inventoryHolder, LegacyItemStack<?> stack) {
        if (stack == null || stack.getCount() == 0 || stack.getItem() == null) return;
        if (stack.getMeta().damageable() && stack.getDamage() > 0) {
            int emptySlot = this.getEmptySlot();
            if (emptySlot >= 0) {
                this.main[emptySlot] = stack.copy();
                stack.setCount(0);
            } else if (inventoryHolder.isCreativeMode()) {
                stack.setCount(0);
            }
        } else {
            int leftItems;
            do {
                leftItems = stack.getCount();
                stack.setCount(this.addItems(stack));
            } while (stack.getCount() > 0 && stack.getCount() < leftItems);
            if (stack.getCount() == leftItems && inventoryHolder.isCreativeMode()) stack.setCount(0);
        }
    }

    private int addItems(final LegacyItemStack<?> stack) {
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

    private int getMatchingSlot(final LegacyItemStack<?> stack) {
        for (int i = 0; i < this.main.length; i++) {
            LegacyItemStack<?> inventoryStack = this.main[i];
            if (inventoryStack != null && inventoryStack.getItem().equals(stack.getItem()) && inventoryStack.getMeta().maxCount() > 1 && inventoryStack.getCount() < inventoryStack.getMeta().maxCount() && (!inventoryStack.getMeta().hasSubtypes() || inventoryStack.getDamage() == stack.getDamage()) && inventoryStack.getTag().equals(stack.getTag())) {
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
    public LegacyItemStack<?> getStack(int slot) {
        if (slot >= this.main.length) return this.armor[slot - this.main.length];
        else return this.main[slot];
    }

    @Override
    public void setStack(int slot, LegacyItemStack<?> stack) {
        if (slot >= this.main.length) this.armor[slot - this.main.length] = stack;
        else this.main[slot] = stack;
    }

    @Override
    public LegacyItemStack<?> split(int slot, int count) {
        LegacyItemStack<?>[] items = this.main;
        if (slot >= this.main.length) {
            items = this.armor;
            slot -= this.main.length;
        }
        if (items[slot] == null) return null;
        LegacyItemStack<?> item;
        if (items[slot].getCount() <= count) {
            item = items[slot];
            items[slot] = null;
        } else {
            item = items[slot].split(count);
            if (items[slot].getCount() == 0) items[slot] = null;
        }
        return item;
    }

}
