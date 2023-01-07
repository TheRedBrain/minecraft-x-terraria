package com.github.theredbrain.minecraftxterraria.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

public abstract class AbstractBrickBlock extends FallingBlock {

    protected static DirectionProperty FACING;
    protected static VoxelShape SHAPE_1_SN;
    protected static VoxelShape SHAPE_1_EW;
    protected static VoxelShape SHAPE_2_SN;
    protected static VoxelShape SHAPE_2_EW;
    protected static VoxelShape SHAPE_3;
    protected static VoxelShape SHAPE_4;
    protected static VoxelShape SHAPE_5;



    public AbstractBrickBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState()).with(FACING, Direction.NORTH));
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
    }

    static {
        FACING = Properties.HORIZONTAL_FACING;
        SHAPE_1_SN = Block.createCuboidShape(5.0D, 0.0D, 2.0D, 11.0D, 4.0D, 14.0D); // 1 brick facing north or south
        SHAPE_1_EW = Block.createCuboidShape(2.0D, 0.0D, 5.0D, 14.0D, 4.0D, 11.0D); // 1 brick facing east or west
        SHAPE_2_SN = Block.createCuboidShape(1.0D, 0.0D, 2.0D, 15.0D, 4.0D, 14.0D); // 2 bricks facing north or south
        SHAPE_2_EW = Block.createCuboidShape(2.0D, 0.0D, 1.0D, 14.0D, 4.0D, 15.0D); // 2 bricks facing east or west
        SHAPE_3 = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D); // 3 or 4 bricks
        SHAPE_4 = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D); // 5 or 6 bricks
        SHAPE_5 = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D); // 7 or 8 bricks
    }
}
