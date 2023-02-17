package com.github.theredbrain.minecraftxterraria.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AbstractMxTCuttingBlock extends AbstractMxTBlock {
    public AbstractMxTCuttingBlock(Settings settings, int hitPoints) {
        super(settings, hitPoints);
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {

    }
}
