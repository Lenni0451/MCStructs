package net.lenni0451.mcstructs.inventory.impl.v1_7;

import net.lenni0451.mcstructs.inventory.types.IInventory;
import net.lenni0451.mcstructs.items.AItemStack;

public interface IInventory_v1_7<I, S extends AItemStack<I, S>> extends IInventory<I, S> {

    S split(final int slotId, final int count);

}
