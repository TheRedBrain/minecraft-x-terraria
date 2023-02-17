package com.github.theredbrain.minecraftxterraria.entity;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public interface DuckLivingEntityMixin {
    ItemStack mxtGetEquippedStack(MxtEquipmentSlot var1);
    void mxtEquipStack(MxtEquipmentSlot var1, ItemStack var2);
}
