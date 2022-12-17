package net.lenni0451.mcstructs.inventory.impl.v1_8.container;

import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.container.FurnaceContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.recipes.ARecipeRegistry;

public class FurnaceContainer_v1_8<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends FurnaceContainer_v1_7<T, I, S> {

    public FurnaceContainer_v1_8(int windowId, T playerInventory, ARecipeRegistry<I, S> recipeRegistry) {
        super(windowId, playerInventory, recipeRegistry);
    }

    @Override
    protected void initSlots() {
        this.addSlot(this.getFurnaceInventory(), 0, Slot.acceptAll());
        this.addSlot(this.getFurnaceInventory(), 1, (slot, stack) -> {
            if (stack.getMeta().getTypes().contains(ItemType.BUCKET)) return 1;
            else return stack.getMeta().getMaxCount();
        });
        this.addSlot(this.getFurnaceInventory(), 2, Slot.acceptNone());
        for (int i = 0; i < 27; i++) this.addSlot(this.getPlayerInventory(), 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.getPlayerInventory(), i, Slot.acceptAll());
    }

}
