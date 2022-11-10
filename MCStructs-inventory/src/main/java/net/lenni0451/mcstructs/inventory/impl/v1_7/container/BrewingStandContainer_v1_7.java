package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.BrewingStandInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemTag;
import net.lenni0451.mcstructs.items.info.ItemType;

import java.util.List;

public class BrewingStandContainer_v1_7<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends AContainer_v1_7<T,I, S> {

    private final T playerInventory;
    private final BrewingStandInventory_v1_7<I, S> brewingStandInventory;

    public BrewingStandContainer_v1_7(final int windowId, final T playerInventory) {
        super(windowId);
        this.playerInventory = playerInventory;
        this.brewingStandInventory = new BrewingStandInventory_v1_7<>();

        this.initSlots();
    }

    @Override
    protected void initSlots() {
        for (int i = 0; i < 3; i++) {
            this.addSlot(this.brewingStandInventory, i, Slot.acceptTypes(1, ItemType.BOTTLE, ItemType.WATER_BOTTLE, ItemType.POTION, ItemType.SPLASH_POTION));
        }
        this.addSlot(this.brewingStandInventory, 3, (slot, stack) -> this.isIngredient(stack) ? stack.getMeta().maxCount() : 0);
        for (int i = 0; i < 27; i++) this.addSlot(this.playerInventory, 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.playerInventory, i, Slot.acceptAll());
    }

    public T getPlayerInventory() {
        return this.playerInventory;
    }

    public BrewingStandInventory_v1_7<I, S> getBrewingStandInventory() {
        return this.brewingStandInventory;
    }

    @Override
    protected S moveStack(InventoryHolder<T, I, S> inventoryHolder, int slotId) {
        Slot<T, I, S> slot = this.getSlot(slotId);
        if (slot == null || slot.getStack() == null) return null;

        S slotStack = slot.getStack();
        S out = slotStack.copy();
        if ((slotId < 0 || slotId > 2) && slotId != 3) {
            if (this.getSlot(3).getStack() == null && this.isIngredient(slotStack)) {
                if (!this.mergeStack(slotStack, 3, 4, false)) return null;
            } else if (this.isPotion(slotStack.getMeta().types())) {
                if (!this.mergeStack(slotStack, 0, 3, false)) return null;
            } else if (slotId >= 4 && slotId <= 30) {
                if (!this.mergeStack(slotStack, 31, 40, false)) return null;
            } else if (slotId >= 31 && slotId <= 39) {
                if (!this.mergeStack(slotStack, 4, 31, false)) return null;
            } else if (!this.mergeStack(slotStack, 4, 40, false)) {
                return null;
            }
        } else {
            if (!this.mergeStack(slotStack, 4, 40, true)) return null;
        }
        if (slotStack.getCount() == 0) slot.setStack(null);
        else slot.onUpdate();
        if (slotStack.getCount() == out.getCount()) return null;
        slot.onTake(inventoryHolder, slotStack);
        return out;
    }

    private boolean isPotion(final List<ItemType> types) {
        return types.contains(ItemType.BOTTLE) || types.contains(ItemType.WATER_BUCKET) || types.contains(ItemType.POTION) || types.contains(ItemType.SPLASH_POTION);
    }

    private boolean isIngredient(final S stack) {
        if (stack.getMeta().types().contains(ItemType.FISH) && stack.getDamage() == 3) return true;
        return stack.getMeta().tags().contains(ItemTag.POTION_INGREDIENT);
    }

}
