package net.lenni0451.mcstructs.inventory.impl.v1_8.container;

import net.lenni0451.mcstructs.enchantments.Enchantment;
import net.lenni0451.mcstructs.enchantments.EnchantmentRegistry;
import net.lenni0451.mcstructs.inventory.InventoryHolder;
import net.lenni0451.mcstructs.inventory.impl.v1_7.container.AnvilContainer_v1_7;
import net.lenni0451.mcstructs.inventory.impl.v1_7.inventory.PlayerInventory_v1_7;
import net.lenni0451.mcstructs.items.AItemStack;
import net.lenni0451.mcstructs.items.info.ItemTag;
import net.lenni0451.mcstructs.items.info.ItemType;

import java.util.Map;

public class AnvilContainer_v1_8<T extends PlayerInventory_v1_7<I, S>, I, S extends AItemStack<I, S>> extends AnvilContainer_v1_7<T, I, S> {

    public AnvilContainer_v1_8(int windowId, InventoryHolder<T, I, S> inventoryHolder, EnchantmentRegistry<I, S> enchantmentRegistry) {
        super(windowId, inventoryHolder, enchantmentRegistry);
    }

    @Override
    protected void refresh() {
        S stack1 = this.getInputSlots().getStack(0);
        this.setRepairCost(1);
        if (stack1 == null) {
            this.getOutputSlot().setStack(0, null);
            this.setRepairCost(0);
            return;
        }

        S out = stack1.copy();
        S stack2 = this.getInputSlots().getStack(1);
        Map<Enchantment, Short> stack1Enchantments = this.getEnchantments(stack1);
        boolean isEnchantedBook;
        int additionalRepairCost = 0;
        int repairCostFactor = 0;
        int newRepairCost = stack1.getRepairCost() + (stack2 == null ? 0 : stack2.getRepairCost());
        this.setRepairStackCount(0);

        if (stack2 != null) {
            isEnchantedBook = stack2.getMeta().types().contains(ItemType.ENCHANTED_BOOK) && !this.getEnchantments(stack2).isEmpty();

            if (out.getMeta().tags().contains(ItemTag.DAMAGEABLE) && stack2.getItem().equals(this.getRepairItem(stack1))) {
                int itemRepairAmount = Math.min(out.getDamage(), out.getMeta().maxDamage() / 4);
                if (itemRepairAmount <= 0) {
                    this.getOutputSlot().setStack(0, null);
                    this.setRepairCost(0);
                    return;
                }

                int neededItems = 0;
                for (; itemRepairAmount > 0 && neededItems < stack2.getCount(); neededItems++) {
                    out.setDamage(out.getDamage() - itemRepairAmount);
                    additionalRepairCost += 1;
                    itemRepairAmount = Math.min(out.getDamage(), out.getMeta().maxDamage() / 4);
                }
                this.setRepairStackCount(neededItems);
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
                        additionalRepairCost += 2;
                    }
                }

                Map<Enchantment, Short> stack2Enchantments = this.getEnchantments(stack2);
                for (Map.Entry<Enchantment, Short> entry : stack2Enchantments.entrySet()) {
                    Enchantment enchantment = entry.getKey();
                    int stack1Level = stack1Enchantments.containsKey(enchantment) ? stack1Enchantments.get(enchantment) : 0;
                    int stack2Level = entry.getValue();

                    if (stack1Level == stack2Level) stack2Level++;
                    else stack2Level = Math.max(stack1Level, stack2Level);

                    boolean isCompatible = this.getEnchantmentRegistry().isItemCompatible(enchantment, stack1);
                    if (this.getInventoryHolder().isCreativeMode() || stack1.getMeta().types().contains(ItemType.ENCHANTED_BOOK)) isCompatible = true;
                    for (Enchantment ench : stack1Enchantments.keySet()) {
                        if (ench.equals(enchantment)) continue;
                        if (!this.getEnchantmentRegistry().isIncompatible(enchantment, ench)) continue;
                        isCompatible = false;
                    }
                    if (isCompatible) {
                        if (stack2Level > enchantment.getMaxLevel()) stack2Level = enchantment.getMaxLevel();
                        stack1Enchantments.put(enchantment, (short) stack2Level);

                        int rarityFactor = this.getRarityFactor(enchantment);
                        if (isEnchantedBook) rarityFactor = Math.max(1, rarityFactor / 2);
                        additionalRepairCost += stack2Level * rarityFactor;
                    }
                }
            } else {
                this.getOutputSlot().setStack(0, null);
                this.setRepairCost(0);
                return;
            }
        }

        if (this.getRepairItemName() == null || this.getRepairItemName().isEmpty()) {
            if (stack1.hasCustomName()) {
                repairCostFactor = 1;
                additionalRepairCost += repairCostFactor;
                out.removeCustomName();
            }
        } else if (!this.getRepairItemName().equals(stack1.getCustomName())) {
            repairCostFactor = 1;
            additionalRepairCost += repairCostFactor;
            out.setCustomName(this.getRepairItemName());
        }

        this.setRepairCost(newRepairCost + additionalRepairCost);
        if (additionalRepairCost <= 0) out = null;
        if (repairCostFactor > 0 && repairCostFactor == additionalRepairCost && this.getRepairCost() >= 40) this.setRepairCost(39);
        if (this.getRepairCost() >= 40 && !this.getInventoryHolder().isCreativeMode()) out = null;

        if (out != null) {
            int oldRepairCost = out.getRepairCost();
            if (stack2 != null && oldRepairCost < stack2.getRepairCost()) oldRepairCost = stack2.getRepairCost();
            oldRepairCost *= 2;
            oldRepairCost++;
            out.setRepairCost(oldRepairCost);
            this.setEnchantments(out, stack1Enchantments);
        }
        this.getOutputSlot().setStack(0, out);
    }

}
