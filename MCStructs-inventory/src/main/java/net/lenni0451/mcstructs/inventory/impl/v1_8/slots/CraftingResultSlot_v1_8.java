package net.lenni0451.mcstructs.inventory.impl.v1_8.slots;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingResultInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.slots.CraftingResultSlot_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemType;

public class CraftingResultSlot_v1_8<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends CraftingResultSlot_v1_7<T, I, S> {

    public CraftingResultSlot_v1_8(CraftingResultInventory_v1_7<I, S> craftingResultInventory, int slotIndex, CraftingInventory_v1_7<I, S> craftingInventory) {
        super(craftingResultInventory, slotIndex, craftingInventory);
    }

    @Override
    public void onTake(InventoryHolder<T, I, S> inventoryHolder, S stack) {
        for (int i = 0; i < this.getCraftingInventory().getSize(); i++) {
            S slotStack = this.getCraftingInventory().getStack(i);
            if (slotStack == null) continue;

            this.getCraftingInventory().split(i, 1);
            if (this.hasContainer(slotStack.getMeta().types())) {
                S container = slotStack.getRegistry().create(slotStack.getRegistry().requireByType(ItemType.BUCKET));
                if (!inventoryHolder.getPlayerInventory().addStack(inventoryHolder, container)) {
                    if (getCraftingInventory().getStack(0) == null) getCraftingInventory().setStack(0, container);
                } else if (!inventoryHolder.getPlayerInventory().addStack(inventoryHolder, container)) /*drop item*/ ;
            }
        }
    }

}
