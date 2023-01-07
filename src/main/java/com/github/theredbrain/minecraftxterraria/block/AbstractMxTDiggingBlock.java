package com.github.theredbrain.minecraftxterraria.block;

import com.github.theredbrain.minecraftxterraria.item.tool.IMxTHammerPower;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public abstract class AbstractMxTDiggingBlock extends AbstractMxTBlock {
    public static final IntProperty ACTUATOR;
    public static final IntProperty SHAPE;
    // part shapes
    protected static final VoxelShape BOTTOM_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape TOP_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape TOP_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape TOP_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape TOP_SOUTH_WEST_CORNER_SHAPE;
    // final shapes
    protected static final VoxelShape BOTTOM_SLAB;
    protected static final VoxelShape TOP_SLAB;
    protected static final VoxelShape STAIR_SOUTH;
    protected static final VoxelShape STAIR_WEST;
    protected static final VoxelShape STAIR_NORTH;
    protected static final VoxelShape STAIR_EAST;
    protected static final VoxelShape TOP_STAIR_SOUTH;
    protected static final VoxelShape TOP_STAIR_WEST;
    protected static final VoxelShape TOP_STAIR_NORTH;
    protected static final VoxelShape TOP_STAIR_EAST;

    // TODO maybe a blockState

    public AbstractMxTDiggingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(ACTUATOR, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{ACTUATOR, SHAPE});
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (!player.getMainHandStack().isEmpty() && player.getMainHandStack().getItem() instanceof IMxTHammerPower) {
            hammerToNextShape(state, world, pos, player);
        }
    }

    private void hammerToNextShape(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        world.setBlockState(pos, state.cycle(SHAPE));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(SHAPE)) {
            case 1:
                return BOTTOM_SLAB;
            case 2:
                return TOP_SLAB;
            case 3:
                return STAIR_NORTH;
            case 4:
                return STAIR_EAST;
            case 5:
                return STAIR_SOUTH;
            case 6:
                return STAIR_WEST;
            case 7:
                return TOP_STAIR_NORTH;
            case 8:
                return TOP_STAIR_EAST;
            case 9:
                return TOP_STAIR_SOUTH;
            case 10:
                return TOP_STAIR_WEST;
            default:
                return VoxelShapes.fullCube();
        }
    }

    static {
        ACTUATOR = IntProperty.of("actuator", 0, 2);
        SHAPE = IntProperty.of("shape", 0, 10);

        BOTTOM_SLAB = Block.createCuboidShape(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 16.0D);
        TOP_SLAB = Block.createCuboidShape(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 16.0D);

        BOTTOM_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        BOTTOM_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        BOTTOM_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        BOTTOM_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
        TOP_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        TOP_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        TOP_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        TOP_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);

        STAIR_SOUTH = VoxelShapes.union(BOTTOM_SLAB, TOP_SOUTH_EAST_CORNER_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE);
        STAIR_WEST = VoxelShapes.union(BOTTOM_SLAB, TOP_NORTH_WEST_CORNER_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE);
        STAIR_NORTH = VoxelShapes.union(BOTTOM_SLAB, TOP_NORTH_EAST_CORNER_SHAPE, TOP_NORTH_WEST_CORNER_SHAPE);
        STAIR_EAST = VoxelShapes.union(BOTTOM_SLAB, TOP_NORTH_EAST_CORNER_SHAPE, TOP_SOUTH_EAST_CORNER_SHAPE);
        TOP_STAIR_SOUTH = VoxelShapes.union(BOTTOM_SOUTH_EAST_CORNER_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE, TOP_SLAB);
        TOP_STAIR_WEST = VoxelShapes.union(BOTTOM_NORTH_WEST_CORNER_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE, TOP_SLAB);
        TOP_STAIR_NORTH = VoxelShapes.union(BOTTOM_NORTH_EAST_CORNER_SHAPE, BOTTOM_NORTH_WEST_CORNER_SHAPE, TOP_SLAB);
        TOP_STAIR_EAST = VoxelShapes.union(BOTTOM_NORTH_EAST_CORNER_SHAPE, BOTTOM_SOUTH_EAST_CORNER_SHAPE, TOP_SLAB);
    }
}
