package net.lenni0451.mcstructs.inventory.impl.v1_7;

import net.lenni0451.mcstructs.inventory.types.IInventory;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public interface IInventory_v1_7<I> extends IInventory<I, LegacyItemStack<I>> {

    LegacyItemStack<I> split(final int slotId, final int count);

}
