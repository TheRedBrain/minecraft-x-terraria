package com.github.theredbrain.minecraftxterraria.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public class MxTDungeonBricksBlock extends MxtDiggingBlock {

    public static final BooleanProperty CURSED;

    public MxTDungeonBricksBlock(Settings settings, int hitPoints) {
        super(settings, hitPoints);
        this.setDefaultState(this.stateManager.getDefaultState().with(CURSED, false).with(ACTUATOR, 0).with(SHAPE, 0).with(RED_WIRE, 0));
//        this.setDefaultState(this.stateManager.getDefaultState().with(CURSED, true).with(ACTUATOR, 0).with(SHAPE, 0).with(MINING_PROGRESS, 0).with(RED_WIRE, 0).with(GREEN_WIRE, 0).with(BLUE_WIRE, 0).with(YELLOW_WIRE, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{CURSED});
    }

    // TODO explosion resistant

    @Override
    public boolean canBeMined(World world, BlockPos blockPos, PlayerEntity player) {
        // TODO check if in inner third of the world
        return blockPos.getY() < 0; // TODO change to blockPos.getY() > 0
    }


//    @Override
//    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
//        int currentMiningProgress = state.get(MINING_PROGRESS);
//        int pickaxePower;
//        if (!player.getMainHandStack().isEmpty()) {
//            Item currentItem = player.getMainHandStack().getItem();
//            if (currentItem instanceof IMxTPickaxePower) {
//                pickaxePower = ((IMxTPickaxePower) currentItem).getPickaxePower();
//                // check if near world center
////                if (world.get) {
////
////                } else {
//                    if (pickaxePower >= 65 && pickaxePower < 70) {
//                        if (currentMiningProgress + 3 >= 12) {
//                            // block is broken
//                            world.breakBlock(pos, true);
//                            player.incrementStat(Stats.MINED.getOrCreateStat(this));
//                            dropStacks(state, world, pos, blockEntity, player, stack);
//                        } else {
//                            world.setBlockState(pos, state.with(MINING_PROGRESS, currentMiningProgress + 3));
//                        }
//                    } else if (pickaxePower >= 70 && pickaxePower < 100) {
//                        if (currentMiningProgress + 4 >= 12) {
//                            // block is broken
//                            world.breakBlock(pos, true);
//                            player.incrementStat(Stats.MINED.getOrCreateStat(this));
//                            dropStacks(state, world, pos, blockEntity, player, stack);
//                        } else {
//                            world.setBlockState(pos, state.with(MINING_PROGRESS, currentMiningProgress + 4));
//                        }
//                    } else if (pickaxePower >= 100 && pickaxePower < 200) {
//                        if (currentMiningProgress + 6 >= 12) {
//                            // block is broken
//                            world.breakBlock(pos, true);
//                            player.incrementStat(Stats.MINED.getOrCreateStat(this));
//                            dropStacks(state, world, pos, blockEntity, player, stack);
//                        } else {
//                            world.setBlockState(pos, state.with(MINING_PROGRESS, currentMiningProgress + 6));
//                        }
//                    } else if (pickaxePower >= 200) {
//                        // block is broken
//                        world.breakBlock(pos, true);
//                        player.incrementStat(Stats.MINED.getOrCreateStat(this));
//                        dropStacks(state, world, pos, blockEntity, player, stack);
//                    }
////                }
//            }
//        }
//    }

//    @Override
//    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
//        // TODO resistant to explosions
//    }

    static {
        CURSED = BooleanProperty.of("cursed");
    }

//    // MxtDiggingBlock Properties
//    static {
//        ACTUATOR = IntProperty.of("actuator", 0, 2);
//        SHAPE = IntProperty.of("shape", 0, 10);
//
//        BOTTOM_SLAB = Block.createCuboidShape(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 16.0D);
//        TOP_SLAB = Block.createCuboidShape(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 16.0D);
//
//        BOTTOM_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
//        BOTTOM_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
//        BOTTOM_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
//        BOTTOM_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
//        TOP_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
//        TOP_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
//        TOP_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
//        TOP_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
//
//        STAIR_SOUTH = VoxelShapes.union(BOTTOM_SLAB, TOP_SOUTH_EAST_CORNER_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE);
//        STAIR_WEST = VoxelShapes.union(BOTTOM_SLAB, TOP_NORTH_WEST_CORNER_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE);
//        STAIR_NORTH = VoxelShapes.union(BOTTOM_SLAB, TOP_NORTH_EAST_CORNER_SHAPE, TOP_NORTH_WEST_CORNER_SHAPE);
//        STAIR_EAST = VoxelShapes.union(BOTTOM_SLAB, TOP_NORTH_EAST_CORNER_SHAPE, TOP_SOUTH_EAST_CORNER_SHAPE);
//        TOP_STAIR_SOUTH = VoxelShapes.union(BOTTOM_SOUTH_EAST_CORNER_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE, TOP_SLAB);
//        TOP_STAIR_WEST = VoxelShapes.union(BOTTOM_NORTH_WEST_CORNER_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE, TOP_SLAB);
//        TOP_STAIR_NORTH = VoxelShapes.union(BOTTOM_NORTH_EAST_CORNER_SHAPE, BOTTOM_NORTH_WEST_CORNER_SHAPE, TOP_SLAB);
//        TOP_STAIR_EAST = VoxelShapes.union(BOTTOM_NORTH_EAST_CORNER_SHAPE, BOTTOM_SOUTH_EAST_CORNER_SHAPE, TOP_SLAB);
//    }
//
//    // AbstractMxTBlock Properties
//    static {
//        RED_WIRE = IntProperty.of("red_wire", 0, 2);
////        GREEN_WIRE = IntProperty.of("green_wire", 0, 2);
////        BLUE_WIRE = IntProperty.of("blue_wire", 0, 2);
////        YELLOW_WIRE = IntProperty.of("yellow_wire", 0, 2);
//    }
}
