package com.github.theredbrain.minecraftxterraria.block;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AbstractMxTInteractiveFurnitureBlock extends AbstractMxTFurnitureBlock {
    public AbstractMxTInteractiveFurnitureBlock(Settings settings, int hitPoints) {
        super(settings, hitPoints);
    }

//    public ActionResult onInteraction(World world, PlayerEntity player, BlockHitResult hitResult) {
//        MinecraftXTerraria.LOGGER.info("abstract furniture: Interaction successful");
//        return ActionResult.SUCCESS;
//    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        MinecraftXTerraria.LOGGER.info("abstract furniture: Interaction successful");
        return ActionResult.SUCCESS;
    }
}
