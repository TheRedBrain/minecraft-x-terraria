package com.github.theredbrain.minecraftxterraria.item.tool;

import com.github.theredbrain.minecraftxterraria.block.AbstractMxTDiggingBlock;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MxTDrillItem extends AbstractMxTToolItem implements IMxTPickaxePower {
    int pickaxePower;
    public MxTDrillItem(Settings settings, int attackDamage, int criticalChance, int bonus, int useTime, int miningSpeed, int knockback, int baseRarity, int sellPrice, int researchAmount, @Nullable Pair<StatusEffectInstance, Float> statusEffect, int pickaxePower) {
        super(settings, attackDamage, criticalChance, bonus, useTime, miningSpeed, knockback, baseRarity, sellPrice, researchAmount, statusEffect);
        this.pickaxePower = pickaxePower;
    }

    public int getPickaxePower() {
        return this.pickaxePower;
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return state.getBlock() instanceof AbstractMxTDiggingBlock;
    }
}
