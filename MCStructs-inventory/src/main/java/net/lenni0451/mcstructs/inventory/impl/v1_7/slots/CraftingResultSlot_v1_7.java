package net.lenni0451.mcstructs.inventory.impl.v1_7.slots;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingResultInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.types.IInventory;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

import java.util.List;

public class CraftingResultSlot_v1_7<I> extends Slot<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> {

    public CraftingResultSlot_v1_7(IInventory<I, LegacyItemStack<I>> inventory, int slotIndex) {
        super(inventory, slotIndex, 0, Slot.acceptNone());
    }

    @Override
    public void onTake(InventoryHolder<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> inventoryHolder, LegacyItemStack<I> stack) {
        CraftingResultInventory_v1_7<I> craftingInventory = (CraftingResultInventory_v1_7<I>) this.getInventory();
        LegacyItemStack<I> slotStack = craftingInventory.getStack(0);
        if (slotStack == null) return;

        craftingInventory.split(0, 1);
        if (this.hasContainer(slotStack.getMeta().types())) {
            LegacyItemStack<I> container = slotStack.getRegistry().create(slotStack.getRegistry().requireByType(ItemType.BUCKET));
            if (!inventoryHolder.getPlayerInventory().addStack(inventoryHolder, container)) {
                if (craftingInventory.getStack(0) == null) craftingInventory.setStack(0, container);
//                else //drop item
            }
        }
    }

    private boolean hasContainer(final List<ItemType> types) {
        return types.contains(ItemType.WATER_BUCKET) || types.contains(ItemType.LAVA_BUCKET) || types.contains(ItemType.MILK_BUCKET);
    }

}
