package net.lenni0451.mcstructs.inventory.v1_7;

import net.lenni0451.mcstructs.inventory.types.IInventory;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

public interface IInventory_v1_7 extends IInventory<LegacyItemStack<?>> {

    LegacyItemStack<?> split(final int slot, final int count);

}
