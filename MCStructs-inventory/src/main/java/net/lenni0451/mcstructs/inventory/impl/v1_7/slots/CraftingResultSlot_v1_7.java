package net.lenni0451.mcstructs.inventory.impl.v1_7.slots;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingResultInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemType;

import java.util.List;

public class CraftingResultSlot_v1_7<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends Slot<T, I, S> {

    private final CraftingInventory_v1_7<I, S> craftingInventory;

    public CraftingResultSlot_v1_7(final CraftingResultInventory_v1_7<I, S> craftingResultInventory, final int slotIndex, final CraftingInventory_v1_7<I, S> craftingInventory) {
        super(craftingResultInventory, slotIndex, 0, Slot.acceptNone());
        this.craftingInventory = craftingInventory;
    }

    public CraftingInventory_v1_7<I, S> getCraftingInventory() {
        return this.craftingInventory;
    }

    @Override
    public void onTake(InventoryHolder<T, I, S> inventoryHolder, S stack) {
        for (int i = 0; i < this.craftingInventory.getSize(); i++) {
            S slotStack = this.craftingInventory.getStack(i);
            if (slotStack == null) continue;

            this.craftingInventory.split(i, 1);
            if (this.hasContainer(slotStack.getMeta().types())) {
                S container = slotStack.getRegistry().create(slotStack.getRegistry().requireByType(ItemType.BUCKET));
                if (!inventoryHolder.getPlayerInventory().addStack(inventoryHolder, container)) {
                    if (craftingInventory.getStack(0) == null) craftingInventory.setStack(0, container);
//                    else //drop item
                }
            }
        }
    }

    protected boolean hasContainer(final List<ItemType> types) {
        return types.contains(ItemType.WATER_BUCKET) || types.contains(ItemType.LAVA_BUCKET) || types.contains(ItemType.MILK_BUCKET);
    }

}
