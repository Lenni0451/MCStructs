package net.lenni0451.mcstructs.all.inventory;

import net.lenni0451.mcstructs.inventory.enums.InventoryAction;
import net.lenni0451.mcstructs.inventory.impl.v1_7.container.*;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.types.AContainer;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;
import net.lenni0451.mcstructs.recipes.impl.v1_7.RecipeRegistry_v1_7;

import java.nio.charset.StandardCharsets;

/**
 * The inventory tracking example uses the packet data sent by the client/server.<br>
 * This is an example implementation for a proxy which has read access to the packets in both directions.
 */
public class PacketHandler {

    private final DataContainer dataContainer;

    public PacketHandler(final DataContainer dataContainer) {
        this.dataContainer = dataContainer;
    }

    public DataContainer getDataContainer() {
        return this.dataContainer;
    }

    public void handleS2CSetSlot(final int windowId, final int slot, final LegacyItemStack<Integer> item) {
        if (windowId == -1) {
            this.dataContainer.getInventoryHolder().getPlayerInventory().setCursorStack(item);
        } else if (windowId == 0 && slot >= 36 && slot < 45) {
            this.dataContainer.getInventoryHolder().getPlayerInventoryContainer().getSlot(slot).setStack(item);
        } else if (windowId == this.dataContainer.getInventoryHolder().getOpenContainer().getWindowId() && windowId != 0) {
            this.dataContainer.getInventoryHolder().getOpenContainer().getSlot(slot).setStack(item);
        }
    }

    public void handleS2CWindowItems(final int windowId, final LegacyItemStack<Integer>[] items) {
        AContainer<PlayerInventory_v1_7<Integer, LegacyItemStack<Integer>>, Integer, LegacyItemStack<Integer>> container;
        if (windowId == 0) container = this.dataContainer.getInventoryHolder().getPlayerInventoryContainer();
        else if (windowId == this.dataContainer.getInventoryHolder().getOpenContainer().getWindowId()) container = this.dataContainer.getInventoryHolder().getOpenContainer();
        else return;

        for (int i = 0; i < items.length; i++) container.getSlot(i).setStack(items[i]);
    }

    public void handleC2SClickWindow(final int windowId, final int slot, final int button, final int action) {
        AContainer<PlayerInventory_v1_7<Integer, LegacyItemStack<Integer>>, Integer, LegacyItemStack<Integer>> container;
        if (windowId == 0) container = this.dataContainer.getInventoryHolder().getPlayerInventoryContainer();
        else if (windowId == this.dataContainer.getInventoryHolder().getOpenContainer().getWindowId()) container = this.dataContainer.getInventoryHolder().getOpenContainer();
        else return;

        LegacyItemStack<Integer> oldCursorStack = container.click(this.dataContainer.getInventoryHolder(), slot, button, InventoryAction.values()[action]);
    }

    public void handleS2COpenWindow(final int windowId, final int windowType, final int slots) {
        PlayerInventory_v1_7<Integer, LegacyItemStack<Integer>> playerInventory = this.dataContainer.getInventoryHolder().getPlayerInventory();
        RecipeRegistry_v1_7<Integer> recipeRegistry = this.dataContainer.getRecipeRegistry();

        AContainer<PlayerInventory_v1_7<Integer, LegacyItemStack<Integer>>, Integer, LegacyItemStack<Integer>> newContainer = null;
        switch (windowType) {
            case 0:
                newContainer = new ChestContainer_v1_7<>(windowId, playerInventory, slots);
                break;
            case 1:
                newContainer = new CraftingTableContainer_v1_7<>(windowId, playerInventory, recipeRegistry);
                break;
            case 2:
                newContainer = new FurnaceContainer_v1_7<>(windowId, playerInventory, recipeRegistry);
                break;
            case 3:
            case 10:
                newContainer = new DispenserContainer_v1_7<>(windowId, playerInventory);
                break;
            case 4:
                newContainer = new EnchantmentTableContainer_v1_7<>(windowId, playerInventory);
                break;
            case 5:
                newContainer = new BrewingStandContainer_v1_7<>(windowId, playerInventory);
                break;
            case 6:
                newContainer = new VillagerContainer_v1_7<>(windowId, playerInventory, recipeRegistry);
                break;
            case 7:
                //Fun Fact: The 1.7 does not send a close window packet when clicking the ok or cancel button in the beacon screen. This causes various inventory desyncs between client and server which is fixed after the server resends the entire inventory or after reconnecting.
                newContainer = new BeaconContainer_v1_7<>(windowId, playerInventory);
                break;
            case 8:
                newContainer = new AnvilContainer_v1_7<>(windowId, this.dataContainer.getInventoryHolder(), this.dataContainer.getEnchantmentRegistry());
                break;
            case 9:
                newContainer = new HopperContainer_v1_7<>(windowId, playerInventory);
                break;
            case 11:
                newContainer = new HorseContainer_v1_7<>(windowId, playerInventory, slots, slots > 2, true);
        }
        if (newContainer != null) this.dataContainer.getInventoryHolder().setOpenContainer(newContainer);
    }

    public void handleC2SCloseWindow() {
        this.dataContainer.getInventoryHolder().setOpenContainer(null);
    }

    public void handleS2CCloseWindow() {
        this.dataContainer.getInventoryHolder().setOpenContainer(null);
    }

    public void handleS2CCustomPayload(final String channel, final byte[] data) {
        if ("MC|TrList".equals(channel)) {
            this.dataContainer.getRecipeRegistry().getVillagerRecipes().clear();
            DummyReader reader = new DummyReader(data);

            int count = reader.readUnsignedByte();
            for (int i = 0; i < count; i++) {
                LegacyItemStack<Integer> input1 = reader.readItem();
                LegacyItemStack<Integer> input2 = null;
                LegacyItemStack<Integer> output = reader.readItem();

                if (reader.readBoolean()) input2 = reader.readItem();
                boolean isDisabled = reader.readBoolean();

                this.dataContainer.getRecipeRegistry().registerVillagerRecipe(input1, input2, output, !isDisabled);
            }
        }
    }

    public void handleC2SCustomPayload(final String channel, final byte[] data) {
        if ("MC|TrSel".equals(channel)) {
            if (this.dataContainer.getInventoryHolder().getOpenContainer() instanceof VillagerContainer_v1_7) {
                DummyReader reader = new DummyReader(data);
                int selectedRecipe = reader.readInt();

                VillagerContainer_v1_7<PlayerInventory_v1_7<Integer, LegacyItemStack<Integer>>, Integer, LegacyItemStack<Integer>> villagerContainer = (VillagerContainer_v1_7<PlayerInventory_v1_7<Integer, LegacyItemStack<Integer>>, Integer, LegacyItemStack<Integer>>) this.dataContainer.getInventoryHolder().getOpenContainer();
                villagerContainer.getVillagerInventory().setCurrentRecipeIndex(selectedRecipe);
            }
        } else if ("MC|ItemName".equals(channel)) {
            if (this.dataContainer.getInventoryHolder().getOpenContainer() instanceof VillagerContainer_v1_7) {
                AnvilContainer_v1_7<PlayerInventory_v1_7<Integer, LegacyItemStack<Integer>>, Integer, LegacyItemStack<Integer>> anvilContainer = (AnvilContainer_v1_7<PlayerInventory_v1_7<Integer, LegacyItemStack<Integer>>, Integer, LegacyItemStack<Integer>>) this.dataContainer.getInventoryHolder().getOpenContainer();
                if (data == null || data.length < 1) {
                    anvilContainer.setRepairItemName("");
                } else {
                    String name = new String(data, StandardCharsets.UTF_8);
                    name = this.dataContainer.filerAllowedCharacters(name);

                    //Fun Fact: The 1.7 client has a char limit of 40 in the anvil screen. This causes the server to reject the name even though the vanilla client can send it.
                    if (name.length() <= 30) anvilContainer.setRepairItemName(name);
                }
            }
        }
    }

    public void handleS2CSetExperience(final int level) {
        this.dataContainer.getInventoryHolder().setXpLevel(level);
    }

    public void handleC2SCreativeInventoryAction(final int slot, final LegacyItemStack<Integer> item) {
        this.dataContainer.getInventoryHolder().getPlayerInventoryContainer().getSlot(slot).setStack(item);
    }

    public void handleS2CPlayerAbilities(final byte flags) {
        this.dataContainer.getInventoryHolder().setCreativeMode((flags & 0x8) != 0);
    }

}
