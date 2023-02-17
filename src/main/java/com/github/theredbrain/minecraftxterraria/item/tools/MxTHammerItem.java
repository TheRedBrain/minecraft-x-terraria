package com.github.theredbrain.minecraftxterraria.item.tools;

import com.github.theredbrain.minecraftxterraria.block.MxtDiggingBlock;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MxTHammerItem extends MxtToolItem implements IMxTHammerPower {
    int hammerPower;
    public MxTHammerItem(Settings settings, int baseRarity, int researchAmount, int sellPrice, boolean autoSwing, int useTime, int attackDamage, int bonus, int criticalChance, double knockback, @Nullable Pair<StatusEffectInstance, Float> statusEffect, int miningSpeed, int hammerPower) {
        super(settings, baseRarity, researchAmount, sellPrice, autoSwing, useTime, attackDamage, bonus, criticalChance, knockback, statusEffect, miningSpeed);
        this.hammerPower = hammerPower;
    }

    public int getHammerPower() {
        return this.hammerPower;
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return state.getBlock() instanceof MxtDiggingBlock;
    }
}
