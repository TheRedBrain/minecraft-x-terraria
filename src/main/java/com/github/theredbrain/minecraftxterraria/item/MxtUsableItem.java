package com.github.theredbrain.minecraftxterraria.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MxtUsableItem extends MxtItem {
    boolean autoSwing;
    int useTime;
    public MxtUsableItem(Settings settings, int baseRarity, int researchAmount, int sellPrice, boolean autoSwing, int useTime) {
        super(settings, baseRarity, researchAmount, sellPrice);
        this.autoSwing = autoSwing;
        this.useTime = useTime;
    }

    public boolean getAutoSwing() {
        return this.autoSwing;
    }

    public int getUseTime() {
        return this.useTime;
    }

    public TypedActionResult<ItemStack> mxtUse(World world, PlayerEntity player) {
        return TypedActionResult.pass(player.getMainHandStack());
    }

    public boolean mxtCanBeUsed(World world, PlayerEntity player) {
        return true;
    }
}
