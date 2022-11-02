package net.lenni0451.mcstructs.inventory.crafting.impl.v1_7;

import net.lenni0451.mcstructs.inventory.crafting.IRecipe;
import net.lenni0451.mcstructs.inventory.types.ICraftingInventory;
import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;
import net.lenni0451.mcstructs.nbt.tags.CompoundNbt;

public class BookCopyRecipe_v1_7<I> implements IRecipe<I, LegacyItemStack<I>> {

    private final I writableBook;
    private final I writtenBook;

    public BookCopyRecipe_v1_7(final I writableBook, final I writtenBook) {
        this.writableBook = writableBook;
        this.writtenBook = writtenBook;
    }

    public I getWritableBook() {
        return this.writableBook;
    }

    public I getWrittenBook() {
        return this.writtenBook;
    }

    @Override
    public boolean matches(ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        LegacyItemStack<I> book = null;
        int writableBooks = 0;
        for (int i = 0; i < craftingInventory.getSize(); i++) {
            LegacyItemStack<I> stack = craftingInventory.getStack(i);
            if (stack == null) continue;

            if (stack.getItem().equals(this.writableBook)) {
                writableBooks++;
            } else if (stack.getItem().equals(this.writtenBook)) {
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
        LegacyItemStack<I> book = null;
        int writableBooks = 0;
        for (int i = 0; i < craftingInventory.getSize(); i++) {
            LegacyItemStack<I> stack = craftingInventory.getStack(i);
            if (stack == null) continue;

            if (stack.getItem().equals(this.writableBook)) {
                writableBooks++;
            } else if (stack.getItem().equals(this.writtenBook)) {
                if (book != null) return null;
                book = stack;
            } else {
                return null;
            }
        }

        if (book == null || writableBooks < 1) return null;
        LegacyItemStack<I> result = itemRegistry.create(this.writtenBook, writableBooks + 1);
        result.setTag((CompoundNbt) book.getTag().copy());
        return result;
    }

}
