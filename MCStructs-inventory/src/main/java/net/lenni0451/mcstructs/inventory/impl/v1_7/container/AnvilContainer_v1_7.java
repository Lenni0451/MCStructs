package net.lenni0451.mcstructs.inventory.impl.v1_7.container;

import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.Slot;
import net.lenni0451.mcstructs.inventory.enchantments.Enchantment;
import net.lenni0451.mcstructs.inventory.enchantments.EnchantmentRegistry;
import net.lenni0451.mcstructs.inventory.impl.v1_7.AContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.CraftingResultInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.SimpleInventory_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.slots.AnvilResultSlot_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemMeta;
import net.lenni0451.mcstructs.items.info.ItemTag;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundNbt;
import net.lenni0451.mcstructs.nbt.tags.ListNbt;

import java.util.HashMap;
import java.util.Map;

public class AnvilContainer_v1_7<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends AContainer_v1_7<T, I, S> {

    private final InventoryHolder<T, I, S> inventoryHolder;
    private final EnchantmentRegistry<I, S> enchantmentRegistry;
    private final SimpleInventory_v1_7<I, S> inputSlots;
    private final CraftingResultInventory_v1_7<I, S> outputSlot;
    /**
     * The amount of xp needed to repair the item
     */
    private int repairCost;
    /**
     * The amount of material needed to fully repair the item
     */
    private int repairStackCount;
    private String repairItemName;

    public AnvilContainer_v1_7(final int windowId, final InventoryHolder<T, I, S> inventoryHolder, final EnchantmentRegistry<I, S> enchantmentRegistry) {
        super(windowId);
        this.inventoryHolder = inventoryHolder;
        this.enchantmentRegistry = enchantmentRegistry;
        this.inputSlots = new SimpleInventory_v1_7<I, S>(2) {
            @Override
            public void onUpdate() {
                AnvilContainer_v1_7.this.refresh();
            }
        };
        this.outputSlot = new CraftingResultInventory_v1_7<>();

        this.initSlots();
    }

    @Override
    protected void initSlots() {
        this.addSlot(this.inputSlots, 0, Slot.acceptAll());
        this.addSlot(this.inputSlots, 1, Slot.acceptAll());
        this.addSlot(id -> new AnvilResultSlot_v1_7<>(this.outputSlot, id, this));
        for (int i = 0; i < 27; i++) this.addSlot(this.inventoryHolder.getPlayerInventory(), 9 + i, Slot.acceptAll());
        for (int i = 0; i < 9; i++) this.addSlot(this.inventoryHolder.getPlayerInventory(), i, Slot.acceptAll());
    }

    public InventoryHolder<T, I, S> getInventoryHolder() {
        return this.inventoryHolder;
    }

    public EnchantmentRegistry<I, S> getEnchantmentRegistry() {
        return this.enchantmentRegistry;
    }

    public SimpleInventory_v1_7<I, S> getInputSlots() {
        return this.inputSlots;
    }

    public CraftingResultInventory_v1_7<I, S> getOutputSlot() {
        return this.outputSlot;
    }

    public int getRepairCost() {
        return this.repairCost;
    }

    public void setRepairCost(final int repairCost) {
        this.repairCost = repairCost;
    }

    public int getRepairStackCount() {
        return this.repairStackCount;
    }

    public void setRepairStackCount(final int repairStackCount) {
        this.repairStackCount = repairStackCount;
    }

    public String getRepairItemName() {
        return this.repairItemName;
    }

    public void setRepairItemName(final String repairItemName) {
        this.repairItemName = repairItemName;
        if (this.getSlot(2).getStack() != null) {
            S stack = this.getSlot(2).getStack();
            if (repairItemName == null || repairItemName.isEmpty()) stack.removeCustomName();
            else stack.setCustomName(repairItemName);
        }
        this.refresh();
    }

    @Override
    protected S moveStack(InventoryHolder<T, I, S> inventoryHolder, int slotId) {
        Slot<T, I, S> slot = this.getSlot(slotId);
        if (slot == null || slot.getStack() == null) return null;

        S slotStack = slot.getStack();
        S out = slotStack.copy();
        if (slotId == 2) {
            if (!this.mergeStack(slotStack, 3, 39, true)) return null;
        } else if (slotId != 0 && slotId != 1) {
            if (slotId >= 3 && slotId <= 38) {
                if (!this.mergeStack(slotStack, 0, 2, false)) return null;
            }
        } else if (!this.mergeStack(slotStack, 3, 39, false)) {
            return null;
        }
        if (slotStack.getCount() == 0) slot.setStack(null);
        else slot.onUpdate();
        if (slotStack.getCount() == out.getCount()) return null;
        slot.onTake(inventoryHolder, slotStack);
        return out;
    }

    @Override
    public void close() {
        this.getSlot(0).setStack(null);
        this.getSlot(1).setStack(null);
    }

    protected void refresh() {
        S stack1 = this.inputSlots.getStack(0);
        this.repairCost = 0;
        if (stack1 == null) {
            this.outputSlot.setStack(0, null);
            return;
        }

        S out = stack1.copy();
        S stack2 = this.inputSlots.getStack(1);
        Map<Enchantment, Short> stack1Enchantments = this.getEnchantments(stack1);
        boolean isEnchantedBook = false;
        int additionalRepairCost = 0;
        int repairCostFactor = 0;
        int newRepairCost = stack1.getRepairCost() + (stack2 == null ? 0 : stack2.getRepairCost());
        this.repairStackCount = 0;

        if (stack2 != null) {
            isEnchantedBook = stack2.getMeta().types().contains(ItemType.ENCHANTED_BOOK) && !this.getEnchantments(stack2).isEmpty();

            if (out.getMeta().tags().contains(ItemTag.DAMAGEABLE) && stack2.getItem().equals(this.getRepairItem(stack1))) {
                int itemRepairAmount = Math.min(out.getDamage(), out.getMeta().maxDamage() / 4);
                if (itemRepairAmount <= 0) {
                    this.outputSlot.setStack(0, null);
                    this.repairCost = 0;
                    return;
                }

                int neededItems = 0;
                for (; itemRepairAmount > 0 && neededItems < stack2.getCount(); neededItems++) {
                    out.setDamage(out.getDamage() - itemRepairAmount);
                    additionalRepairCost += Math.max(1, itemRepairAmount / 100) + stack1Enchantments.size();
                    itemRepairAmount = Math.min(out.getDamage(), out.getMeta().maxDamage() / 4);
                }
                this.repairStackCount = neededItems;
            } else if (isEnchantedBook || (out.getItem().equals(stack2.getItem()) && out.getMeta().tags().contains(ItemTag.DAMAGEABLE))) {
                if (!isEnchantedBook && out.getMeta().tags().contains(ItemTag.DAMAGEABLE)) {
                    int stack1Durability = stack1.getMeta().maxDamage() - stack1.getDamage();
                    int stack2Durability = stack2.getMeta().maxDamage() - stack2.getDamage();
                    int repairFactor = stack2Durability + out.getMeta().maxDamage() * 12 / 100;
                    int repairedDurability = stack1Durability + repairFactor;
                    int repairedDamage = out.getMeta().maxDamage() - repairedDurability;
                    if (repairedDamage < 0) repairedDamage = 0;
                    if (repairedDamage < out.getDamage()) {
                        out.setDamage(repairedDamage);
                        additionalRepairCost += Math.max(1, repairFactor / 100);
                    }
                }

                Map<Enchantment, Short> stack2Enchantments = this.getEnchantments(stack2);
                for (Map.Entry<Enchantment, Short> entry : stack2Enchantments.entrySet()) {
                    Enchantment enchantment = entry.getKey();
                    int stack1Level = stack1Enchantments.containsKey(enchantment) ? stack1Enchantments.get(enchantment) : 0;
                    int stack2Level = entry.getValue();

                    if (stack1Level == stack2Level) stack2Level++;
                    else stack2Level = Math.max(stack1Level, stack2Level);

                    int levelDiff = stack2Level - stack1Level;
                    boolean isCompatible = this.enchantmentRegistry.isItemCompatible(enchantment, stack1);
                    if (this.inventoryHolder.isCreativeMode() || stack1.getMeta().types().contains(ItemType.ENCHANTED_BOOK)) isCompatible = true;
                    for (Enchantment ench : stack1Enchantments.keySet()) {
                        if (ench.equals(enchantment)) continue;
                        if (!this.enchantmentRegistry.isIncompatible(enchantment, ench)) continue;
                        isCompatible = false;
                        additionalRepairCost += levelDiff;
                    }
                    if (isCompatible) {
                        if (stack2Level > enchantment.getMaxLevel()) stack2Level = enchantment.getMaxLevel();
                        stack1Enchantments.put(enchantment, (short) stack2Level);

                        int rarityFactor = this.getRarityFactor(enchantment);
                        if (isEnchantedBook) rarityFactor = Math.max(1, rarityFactor / 2);
                        additionalRepairCost += levelDiff * rarityFactor;
                    }
                }
            } else {
                this.outputSlot.setStack(0, null);
                this.repairCost = 0;
                return;
            }
        }

        if (this.repairItemName == null || this.repairItemName.isEmpty()) {
            if (stack1.hasCustomName()) {
                if (stack1.getMeta().tags().contains(ItemTag.DAMAGEABLE)) repairCostFactor = 7;
                else repairCostFactor = stack1.getCount() * 5;
                additionalRepairCost += repairCostFactor;
                out.removeCustomName();
            }
        } else if (!this.repairItemName.equals(stack1.getCustomName())) {
            if (stack1.getMeta().tags().contains(ItemTag.DAMAGEABLE)) repairCostFactor = 7;
            else repairCostFactor = stack1.getCount() * 5;
            additionalRepairCost += repairCostFactor;
            if (stack1.hasCustomName()) newRepairCost += repairCostFactor / 2;
            out.setCustomName(this.repairItemName);
        }

        int i = 0;
        int rarityFactor;
        int enchantmentLevel;
        for (Map.Entry<Enchantment, Short> entry : stack1Enchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            enchantmentLevel = entry.getValue();
            i++;

            rarityFactor = this.getRarityFactor(enchantment);
            if (isEnchantedBook) rarityFactor = Math.max(1, rarityFactor / 2);
            newRepairCost += i + enchantmentLevel * rarityFactor;
        }

        if (isEnchantedBook) newRepairCost = Math.max(1, newRepairCost / 2);
        this.repairCost = newRepairCost + additionalRepairCost;
        if (additionalRepairCost <= 0) out = null;
        if (repairCostFactor > 0 && repairCostFactor == additionalRepairCost && this.repairCost >= 40) this.repairCost = 39;
        if (this.repairCost >= 40 && !this.inventoryHolder.isCreativeMode()) out = null;

        if (out != null) {
            int oldRepairCost = out.getRepairCost();
            if (stack2 != null && oldRepairCost < stack2.getRepairCost()) oldRepairCost = stack2.getRepairCost();
            if (out.hasCustomName()) oldRepairCost -= 9;
            if (oldRepairCost < 0) oldRepairCost = 0;
            oldRepairCost += 2;
            out.setRepairCost(oldRepairCost);
            this.setEnchantments(out, stack1Enchantments);
        }
        this.outputSlot.setStack(0, out);
    }

    protected int getRarityFactor(final Enchantment enchantment) {
        switch (enchantment.getRarity()) {
            case 1:
                return 8;
            case 2:
                return 4;
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            default:
                return 0;
            case 5:
                return 2;
            case 10:
                return 1;
        }
    }

    protected Map<Enchantment, Short> getEnchantments(final S stack) {
        Map<Enchantment, Short> enchantments = new HashMap<>();
        if (!stack.hasTag()) return enchantments;

        ListNbt<CompoundNbt> enchantmentsTag;
        if (stack.getMeta().types().contains(ItemType.ENCHANTED_BOOK)) {
            if (!stack.getTag().contains("StoredEnchantments", NbtType.LIST)) return enchantments;
            enchantmentsTag = stack.getTag().getList("StoredEnchantments", NbtType.COMPOUND);
        } else {
            if (!stack.getTag().contains("ench", NbtType.LIST)) return enchantments;
            enchantmentsTag = stack.getTag().getList("ench", NbtType.COMPOUND);
        }

        for (int i = 0; i < enchantmentsTag.size(); i++) {
            CompoundNbt enchantmentTag = enchantmentsTag.get(i);
            Enchantment enchantment = this.enchantmentRegistry.get(enchantmentTag.getShort("id"));
            if (enchantment == null) continue;

            enchantments.put(enchantment, enchantmentTag.getShort("lvl"));
        }
        return enchantments;
    }

    protected void setEnchantments(final S stack, final Map<Enchantment, Short> enchantments) {
        boolean isEnchantedBook = stack.getMeta().types().contains(ItemType.ENCHANTED_BOOK);
        ListNbt<CompoundNbt> ench = new ListNbt<>();
        for (Map.Entry<Enchantment, Short> entry : enchantments.entrySet()) {
            CompoundNbt enchantmentTag = new CompoundNbt();
            enchantmentTag.addShort("id", (short) entry.getKey().getId());
            enchantmentTag.addShort("lvl", entry.getValue());
            ench.add(enchantmentTag);

            if (isEnchantedBook) this.addBookEnchantment(stack, entry.getKey(), entry.getValue());
        }
        if (!ench.isEmpty()) {
            if (!isEnchantedBook) stack.getOrCreateTag().addList("ench", ench);
        } else if (stack.hasTag()) {
            stack.getTag().remove("ench");
        }
    }

    protected void addBookEnchantment(final S stack, final Enchantment enchantment, final int level) {
        if (!stack.getOrCreateTag().contains("StoredEnchantments", NbtType.LIST)) stack.getTag().addList("StoredEnchantments");
        ListNbt<CompoundNbt> storedEnchantments = stack.getTag().getList("StoredEnchantments", NbtType.COMPOUND);
        boolean hasEnchantment = false;
        for (int i = 0; i < storedEnchantments.size(); i++) {
            CompoundNbt tag = storedEnchantments.get(i);
            if (tag.getShort("id") != enchantment.getId()) continue;

            if (tag.getShort("lvl") < level) tag.addShort("lvl", (short) level);
            hasEnchantment = true;
        }

        if (!hasEnchantment) {
            CompoundNbt tag = new CompoundNbt();
            tag.addShort("id", (short) enchantment.getId());
            tag.addShort("lvl", (short) level);
            storedEnchantments.add(tag);
        }
    }

    protected I getRepairItem(final S stack) {
        ItemMeta meta = stack.getMeta();
        if (!ItemType.isArmor(meta.types())) return null;
        if (!ItemType.isTool(meta.types())) return null;

        ItemType material = ItemTag.getMaterial(meta.tags());
        if (material == null) return null;
        return stack.getRegistry().requireByType(material);
    }

}
