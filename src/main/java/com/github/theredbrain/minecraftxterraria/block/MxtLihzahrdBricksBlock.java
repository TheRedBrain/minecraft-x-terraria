package com.github.theredbrain.minecraftxterraria.block;

import com.github.theredbrain.minecraftxterraria.item.tools.IMxTPickaxePower;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public class MxtLihzahrdBricksBlock extends MxtDiggingBlock {
    public MxtLihzahrdBricksBlock(Settings settings, int hitPoints) {
        super(settings, hitPoints);
        this.setDefaultState(this.stateManager.getDefaultState().with(ACTUATOR, 0).with(SHAPE, 0).with(RED_WIRE, 0));
    }

    @Override
    public boolean canBeMined(World world, BlockPos blockPos, PlayerEntity player) {
        ItemStack itemStack = player.getMainHandStack();
        return itemStack.getItem() instanceof IMxTPickaxePower && ((IMxTPickaxePower) itemStack.getItem()).getPickaxePower() >= 210;
    }
}
