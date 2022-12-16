package net.lenni0451.mcstructs.recipes.impl.v1_7.impl;

import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.recipes.ICraftingInventory;
import net.lenni0451.mcstructs.recipes.ICraftingRecipe;

public class BookCopyCraftingRecipe_v1_7<I> implements ICraftingRecipe<I, LegacyItemStack<I>> {

    @Override
    public boolean matches(ItemRegistry<I, LegacyItemStack<I>> itemRegistry, ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        I writableBook = itemRegistry.requireByType(ItemType.WRITABLE_BOOK);
        I writtenBook = itemRegistry.requireByType(ItemType.WRITTEN_BOOK);

        LegacyItemStack<I> book = null;
        int writableBooks = 0;
        for (int i = 0; i < craftingInventory.getSize(); i++) {
            LegacyItemStack<I> stack = craftingInventory.getStack(i);
            if (stack == null) continue;

            if (stack.getItem().equals(writableBook)) {
                writableBooks++;
            } else if (stack.getItem().equals(writtenBook)) {
                if (book != null) return false;
                book = stack;
            } else {
                return false;
            }
        }
        return book != null && writableBooks >= 1;
    }

    @Override
    public LegacyItemStack<I> getResult(ItemRegistry<I, LegacyItemStack<I>> itemRegistry, ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        I writableBook = itemRegistry.requireByType(ItemType.WRITABLE_BOOK);
        I writtenBook = itemRegistry.requireByType(ItemType.WRITTEN_BOOK);

        LegacyItemStack<I> book = null;
        int writableBooks = 0;
        for (int i = 0; i < craftingInventory.getSize(); i++) {
            LegacyItemStack<I> stack = craftingInventory.getStack(i);
            if (stack == null) continue;

            if (stack.getItem().equals(writableBook)) {
                writableBooks++;
            } else if (stack.getItem().equals(writtenBook)) {
                if (book != null) return null;
                book = stack;
            } else {
                return null;
            }
        }

        if (book == null || writableBooks < 1) return null;
        LegacyItemStack<I> result = itemRegistry.create(writtenBook, writableBooks + 1);
        result.setTag((CompoundTag) book.getTag().copy());
        return result;
    }

}
