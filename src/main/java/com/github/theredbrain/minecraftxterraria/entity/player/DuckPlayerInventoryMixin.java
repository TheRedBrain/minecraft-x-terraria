package com.github.theredbrain.minecraftxterraria.entity.player;

import net.minecraft.item.ItemStack;

public interface DuckPlayerInventoryMixin {

    ItemStack mxtGetArmorStack(int slot, int loadout);

    ItemStack mxtGetVanityArmorStack(int slot, int loadout);

    ItemStack mxtGetArmorDyeStack(int slot, int loadout);

    ItemStack mxtGetAccessoryStack(int slot, int loadout);

    ItemStack mxtGetVanityAccessoryStack(int slot, int loadout);

    ItemStack mxtGetAccessoryDyeStack(int slot, int loadout);

    ItemStack mxtGetEquipmentStack(int slot);

    ItemStack mxtGetEquipmentDyeStack(int slot);
}
