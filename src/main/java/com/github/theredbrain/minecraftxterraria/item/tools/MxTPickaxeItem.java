package com.github.theredbrain.minecraftxterraria.item.tools;

import com.github.theredbrain.minecraftxterraria.block.MxtDiggingBlock;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MxTPickaxeItem extends MxtToolItem implements IMxTPickaxePower {
    int pickaxePower;
    public MxTPickaxeItem(Settings settings, int baseRarity, int researchAmount, int sellPrice, boolean autoSwing, int useTime, int attackDamage, int bonus, int criticalChance, double knockback, @Nullable Pair<StatusEffectInstance, Float> statusEffect, int miningSpeed, int pickaxePower) {
        super(settings, baseRarity, researchAmount, sellPrice, autoSwing, useTime, attackDamage, bonus, criticalChance, knockback, statusEffect, miningSpeed);
        this.pickaxePower = pickaxePower;
    }

    public int getPickaxePower() {
        return this.pickaxePower;
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return state.getBlock() instanceof MxtDiggingBlock;
    }
}
