package com.github.theredbrain.minecraftxterraria.client;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

public interface DuckClientPlayerInteractionManagerMixin {

//    ActionResult placeBlock(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult);
    boolean mxtAttackBlock(BlockPos pos, BlockHitResult blockHitResult);

    boolean mxtUpdateBlockBreakingProgress(BlockPos pos, BlockHitResult blockHitResult);
    void mxtCancelBlockBreaking();
//    float getCurrentBreakingProgress();
//    ActionResult mxtUseItem(PlayerEntity player);
}
