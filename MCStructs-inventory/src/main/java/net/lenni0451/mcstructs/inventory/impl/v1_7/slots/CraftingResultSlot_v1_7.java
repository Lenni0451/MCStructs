package net.lenni0451.mcstructs.inventory.impl.v1_7.slots;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingResultInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

import java.util.List;

public class CraftingResultSlot_v1_7<I> extends Slot<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> {

    private final CraftingInventory_v1_7<I> craftingInventory;

    public CraftingResultSlot_v1_7(final CraftingResultInventory_v1_7<I> craftingResultInventory, final int slotIndex, final CraftingInventory_v1_7<I> craftingInventory) {
        super(craftingResultInventory, slotIndex, 0, Slot.acceptNone());
        this.craftingInventory = craftingInventory;
    }

    @Override
    public void onTake(InventoryHolder<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> inventoryHolder, LegacyItemStack<I> stack) {
        for (int i = 0; i < this.craftingInventory.getSize(); i++) {
            LegacyItemStack<I> slotStack = this.craftingInventory.getStack(i);
            if (slotStack == null) continue;

            this.craftingInventory.split(i, 1);
            if (this.hasContainer(slotStack.getMeta().types())) {
                LegacyItemStack<I> container = slotStack.getRegistry().create(slotStack.getRegistry().requireByType(ItemType.BUCKET));
                if (!inventoryHolder.getPlayerInventory().addStack(inventoryHolder, container)) {
                    if (craftingInventory.getStack(0) == null) craftingInventory.setStack(0, container);
//                    else //drop item
                }
            }
        }
    }

    private boolean hasContainer(final List<ItemType> types) {
        return types.contains(ItemType.WATER_BUCKET) || types.contains(ItemType.LAVA_BUCKET) || types.contains(ItemType.MILK_BUCKET);
    }

}
