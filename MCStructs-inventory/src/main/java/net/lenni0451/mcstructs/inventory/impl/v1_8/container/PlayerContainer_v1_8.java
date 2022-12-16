package net.lenni0451.mcstructs.inventory.impl.v1_8.container;

import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.impl.v1_7.container.PlayerContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_8.slots.CraftingResultSlot_v1_8;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.recipes.ARecipeRegistry;

public class PlayerContainer_v1_8<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends PlayerContainer_v1_7<T, I, S> {

    public PlayerContainer_v1_8(T playerInventory, ARecipeRegistry<I, S> recipeRegistry) {
        super(playerInventory, recipeRegistry);
    }

    @Override
    protected void initSlots() {
        this.addSlot(id -> new CraftingResultSlot_v1_8<>(this.getCraftingResultInventory(), id, this.getCraftingInventory()));
        for (int i = 0; i < this.getCraftingInventory().getSize(); i++) this.addSlot(this.getCraftingInventory(), i, Slot.acceptAll());
        this.addSlot(this.getPlayerInventory(), this.getPlayerInventory().getSize() - 1, Slot.acceptTypes(1, ItemType.HELMET, ItemType.SKULL, ItemType.PUMPKIN));
        this.addSlot(this.getPlayerInventory(), this.getPlayerInventory().getSize() - 2, Slot.acceptTypes(1, ItemType.CHESTPLATE));
        this.addSlot(this.getPlayerInventory(), this.getPlayerInventory().getSize() - 3, Slot.acceptTypes(1, ItemType.LEGGINGS));
        this.addSlot(this.getPlayerInventory(), this.getPlayerInventory().getSize() - 4, Slot.acceptTypes(1, ItemType.BOOTS));
        for (int i = 0; i < 27; i++) this.addSlot(this.getPlayerInventory(), 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.getPlayerInventory(), i, Slot.acceptAll());
    }

}
