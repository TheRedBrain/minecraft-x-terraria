package com.github.theredbrain.minecraftxterraria.item;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class MxTBlockItem extends MxtUsableItem {
    Block block;
    public MxTBlockItem(Settings settings, int baseRarity, int researchAmount, int sellPrice, boolean autoSwing, int useTime, Block block) {
        super(settings, baseRarity, researchAmount, sellPrice, autoSwing, useTime);
        this.block = block;
    }

    public Block getBlock() {
        return this.block;
    }

    public ActionResult tryPlacing(World world, ItemStack itemStack, BlockPos blockPos, Direction direction) {
        // FIXME maybe "Mismatch in destroy block pos" on server thread can be fixed here?
        BlockPos newBlockPos = switch (direction) {
            case WEST -> blockPos.west();
            case NORTH -> blockPos.north();
            case EAST -> blockPos.east();
            case SOUTH -> blockPos.south();
            case UP -> blockPos.up();
            case DOWN -> blockPos.down();
        };
//        ItemStack itemStack = blockHitResult.getStack();
//        if (!world.isClient())
        if (itemStack.getItem() instanceof MxTBlockItem) {
            if (world.setBlockState(newBlockPos, ((MxTBlockItem) itemStack.getItem()).getBlock().getDefaultState())) {//.getPlacementState(new ItemPlacementContext(itemUsageContext)))) {
//                MinecraftXTerraria.LOGGER.info("Block placed");
                return ActionResult.SUCCESS;
            }
        }
        MinecraftXTerraria.LOGGER.info("Block not placed");
        return ActionResult.FAIL;
    }
}
