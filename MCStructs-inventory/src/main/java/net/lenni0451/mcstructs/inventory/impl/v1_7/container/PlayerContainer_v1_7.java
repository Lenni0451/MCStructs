package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.crafting.ARecipeRegistry;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingResultInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.slots.CraftingResultSlot_v1_7;
import net.lenni0451.mcstructs.inventory.types.ICraftingContainer;
import net.lenni0451.mcstructs.inventory.types.ICraftingInventory;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;

import java.util.List;

/**
 * Slots:<br>
 * 0-4: crafting slots (output, slot 1-1, slot 1-2, slot 2-1, slot 2-2)<br>
 * 5-8: armor slots (boots, leggings, chestplate, helmet)<br>
 * 9-35: upper inventory slots<br>
 * 36-44: hotbar slots
 */
public class PlayerContainer_v1_7<I> extends AContainer_v1_7<I> implements ICraftingContainer<I, LegacyItemStack<I>> {

    private final PlayerInventory_v1_7<I> playerInventory;
    private final CraftingInventory_v1_7<I> craftingInventory;
    private final CraftingResultInventory_v1_7<I> craftingResultInventory;
    private final ARecipeRegistry<I, LegacyItemStack<I>> recipeRegistry;

    public PlayerContainer_v1_7(final PlayerInventory_v1_7<I> playerInventory, final ARecipeRegistry<I, LegacyItemStack<I>> recipeRegistry) {
        super(0);
        this.playerInventory = playerInventory;
        this.craftingInventory = new CraftingInventory_v1_7<>(this, 2, 2);
        this.craftingResultInventory = new CraftingResultInventory_v1_7<>();
        this.recipeRegistry = recipeRegistry;

        this.addSlot(id -> new CraftingResultSlot_v1_7<>(this.craftingResultInventory, id, this.craftingInventory));
        for (int i = 0; i < this.craftingInventory.getSize(); i++) this.addSlot(this.craftingInventory, i, Slot.acceptAll());
        this.addSlot(this.playerInventory, this.playerInventory.getSize() - 1, Slot.acceptTypes(1, ItemType.HELMET, ItemType.SKULL, ItemType.PUMPKIN));
        this.addSlot(this.playerInventory, this.playerInventory.getSize() - 2, Slot.acceptTypes(1, ItemType.CHESTPLATE));
        this.addSlot(this.playerInventory, this.playerInventory.getSize() - 3, Slot.acceptTypes(1, ItemType.LEGGINGS));
        this.addSlot(this.playerInventory, this.playerInventory.getSize() - 4, Slot.acceptTypes(1, ItemType.BOOTS));
        for (int i = 0; i < 27; i++) this.addSlot(this.playerInventory, 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.playerInventory, i, Slot.acceptAll());

        this.craftingUpdate(this.craftingInventory);
    }

    public PlayerInventory_v1_7<I> getPlayerInventory() {
        return this.playerInventory;
    }

    public CraftingInventory_v1_7<I> getCraftingInventory() {
        return this.craftingInventory;
    }

    public CraftingResultInventory_v1_7<I> getCraftingResultInventory() {
        return this.craftingResultInventory;
    }

    public ARecipeRegistry<I, LegacyItemStack<I>> getRecipeRegistry() {
        return this.recipeRegistry;
    }

    public int getArmorSlotOffset(final List<ItemType> types) {
        if (types.contains(ItemType.HELMET)) return 0;
        else if (types.contains(ItemType.CHESTPLATE)) return 1;
        else if (types.contains(ItemType.LEGGINGS)) return 2;
        else if (types.contains(ItemType.BOOTS)) return 3;
        else throw new IllegalArgumentException("The given item type is not an armor type");
    }

    @Override
    public void craftingUpdate(ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        this.craftingResultInventory.setStack(0, this.recipeRegistry.findCraftingRecipe(this.craftingInventory));
    }

    @Override
    public void close() {
        for (int i = 0; i < this.craftingInventory.getSize(); i++) this.craftingInventory.setStack(i, null);
        this.craftingResultInventory.setStack(0, null);
    }

    @Override
    protected LegacyItemStack<I> moveStack(InventoryHolder<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> inventoryHolder, int slotId) {
        Slot<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> slot = this.getSlot(slotId);
        if (slot == null || slot.getStack() == null) return null;

        LegacyItemStack<I> slotStack = slot.getStack();
        LegacyItemStack<I> out = slotStack.copy();
        if (slotId == 0) {
            if (!this.mergeStack(slotStack, 9, 45, true)) return null;
        } else if (slotId >= 1 && slotId <= 8) {
            if (!this.mergeStack(slotStack, 9, 45, false)) return null;
        } else if (ItemType.isArmor(out.getMeta().types()) && this.getSlot(5 + this.getArmorSlotOffset(out.getMeta().types())).getStack() == null) {
            int armorSlot = 5 + this.getArmorSlotOffset(out.getMeta().types());
            if (!this.mergeStack(slotStack, armorSlot, armorSlot + 1, false)) return null;
        } else if (slotId >= 9 && slotId <= 35) {
            if (!this.mergeStack(slotStack, 36, 45, false)) return null;
        } else if (slotId >= 36 && slotId <= 44) {
            if (!this.mergeStack(slotStack, 9, 36, false)) return null;
        } else if (!this.mergeStack(slotStack, 9, 45, false)) {
            return null;
        }
        if (slotStack.getCount() == 0) slot.setStack(null);
        else slot.onUpdate();
        if (slotStack.getCount() == out.getCount()) return null;
        slot.onTake(inventoryHolder, slotStack);
        return out;
    }

    @Override
    protected boolean canTakeAll(Slot<PlayerInventory_v1_7<I>, I, LegacyItemStack<I>> slot, LegacyItemStack<I> stack) {
        return !this.craftingResultInventory.equals(slot.getInventory());
    }

}
