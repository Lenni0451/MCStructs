package net.lenni0451.mcstructs.inventory.crafting.impl.v1_7.impl;

import net.lenni0451.mcstructs.inventory.crafting.ICraftingRecipe;
import net.lenni0451.mcstructs.inventory.types.ICraftingInventory;
import net.lenni0451.mcstructs.items.ItemRegistry;
import net.lenni0451.mcstructs.items.info.ItemType;
import net.lenni0451.mcstructs.items.stacks.LegacyItemStack;
import net.lenni0451.mcstructs.nbt.NbtType;
import net.lenni0451.mcstructs.nbt.tags.CompoundNbt;
import net.lenni0451.mcstructs.nbt.tags.IntArrayNbt;
import net.lenni0451.mcstructs.nbt.tags.IntNbt;
import net.lenni0451.mcstructs.nbt.tags.ListNbt;

import java.util.List;

public class FireworksCraftingRecipe_v1_7<I> implements ICraftingRecipe<I, LegacyItemStack<I>> {

    private static final int[] DYE_COLORS = {
            0x1E_1B_1B, //ink sac
            0xB3_31_2C, //rose red
            0x3B_51_1A, //cactus green
            0x51_30_1A, //cocoa beans
            0x25_31_92, //lapis lazuli
            0x7B_2F_BE, //purple dye
            0x28_76_97, //cyan dye
            0xAB_AB_AB, //light gray dye
            0x43_43_43, //gray dye
            0xD8_81_98, //pink dye
            0x41_CD_34, //lime dye
            0xDE_CF_2A, //dandelion yellow
            0x66_89_D3, //light blue dye
            0xC3_54_CD, //magenta dye
            0xEB_88_44, //orange dye
            0xF0_F0_F0 //bone meal
    };

    @Override
    public boolean matches(ItemRegistry<I, LegacyItemStack<I>> itemRegistry, ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        return this.getResult(itemRegistry, craftingInventory) != null;
    }

    @Override
    public LegacyItemStack<I> getResult(ItemRegistry<I, LegacyItemStack<I>> itemRegistry, ICraftingInventory<I, LegacyItemStack<I>> craftingInventory) {
        int paper = 0;
        int flyDuration = 0;
        int colors = 0;
        int fireworkStars = 0;
        int effects = 0;
        int shapes = 0;

        for (int i = 0; i < craftingInventory.getSize(); i++) {
            LegacyItemStack<I> stack = craftingInventory.getStack(i);
            if (stack == null) continue;

            List<ItemType> types = stack.getMeta().types();
            if (types.contains(ItemType.GUNPOWDER)) flyDuration++;
            else if (types.contains(ItemType.FIREWORK_STAR)) fireworkStars++;
            else if (types.contains(ItemType.DYE)) colors++;
            else if (types.contains(ItemType.PAPER)) paper++;
            else if (types.contains(ItemType.GLOWSTONE_DUST)) effects++;
            else if (types.contains(ItemType.DIAMOND)) effects++;
            else if (types.contains(ItemType.FIRE_CHARGE)) shapes++;
            else if (types.contains(ItemType.FEATHER)) shapes++;
            else if (types.contains(ItemType.GOLD_NUGGET)) shapes++;
            else if (types.contains(ItemType.SKULL)) shapes++;
            else return null;
        }
        effects += colors + shapes;
        if (flyDuration > 3 || paper > 1) return null;

        LegacyItemStack<I> result = null;
        if (flyDuration >= 1 && paper == 1 && effects == 0) {
            result = itemRegistry.create(itemRegistry.requireByType(ItemType.FIREWORK_ROCKET));
            if (fireworkStars > 0) {
                ListNbt<CompoundNbt> explosions = new ListNbt<>(NbtType.COMPOUND);

                for (int i = 0; i < craftingInventory.getSize(); i++) {
                    LegacyItemStack<I> stack = craftingInventory.getStack(i);
                    if (stack == null) continue;

                    if (stack.getMeta().types().contains(ItemType.FIREWORK_STAR) && stack.hasTag() && stack.getTag().contains("Explosion", NbtType.COMPOUND)) {
                        explosions.add(stack.getTag().getCompound("Explosion"));
                    }
                }

                CompoundNbt fireworks = new CompoundNbt();
                fireworks.add("Explosions", explosions);
                fireworks.addByte("Flight", (byte) flyDuration);
                result.getOrCreateTag().add("Fireworks", fireworks);
            }
        } else if (flyDuration == 1 && paper == 0 && fireworkStars == 0 && colors > 0 && shapes <= 1) {
            result = itemRegistry.create(itemRegistry.requireByType(ItemType.FIREWORK_STAR));
            ListNbt<IntNbt> colorsTag = new ListNbt<>(NbtType.INT);
            CompoundNbt explosion = new CompoundNbt();
            byte type = 0;

            for (int i = 0; i < craftingInventory.getSize(); i++) {
                LegacyItemStack<I> stack = craftingInventory.getStack(i);
                if (stack == null) continue;

                List<ItemType> types = stack.getMeta().types();
                if (types.contains(ItemType.DYE)) colorsTag.add(new IntNbt(DYE_COLORS[stack.getDamage()]));
                else if (types.contains(ItemType.GLOWSTONE_DUST)) explosion.addByte("Flicker", (byte) 1);
                else if (types.contains(ItemType.DIAMOND)) explosion.addByte("Trail", (byte) 1);
                else if (types.contains(ItemType.FIRE_CHARGE)) type = 1;
                else if (types.contains(ItemType.GOLD_NUGGET)) type = 2;
                else if (types.contains(ItemType.SKULL)) type = 3;
                else if (types.contains(ItemType.FEATHER)) type = 4;
            }

            explosion.add("Colors", new IntArrayNbt(colorsTag));
            explosion.addByte("Type", type);
            result.getOrCreateTag().add("Explosion", explosion);
        } else if (flyDuration == 0 && paper == 0 && fireworkStars == 1 && colors > 0 && colors == effects) {
            ListNbt<IntNbt> fadeColorsTag = new ListNbt<>(NbtType.INT);

            for (int i = 0; i < craftingInventory.getSize(); i++) {
                LegacyItemStack<I> stack = craftingInventory.getStack(i);
                if (stack == null) continue;

                if (stack.getMeta().types().contains(ItemType.DYE)) {
                    fadeColorsTag.add(new IntNbt(DYE_COLORS[stack.getDamage()]));
                } else if (stack.getMeta().types().contains(ItemType.FIREWORK_STAR)) {
                    result = stack.copy();
                    result.setCount(1);
                }
            }
            if (result == null || !result.hasTag()) return null;

            CompoundNbt explosion = result.getTag().getCompound("Explosion");
            explosion.add("FadeColors", new IntArrayNbt(fadeColorsTag));
        }
        return result;
    }

}
