package com.github.theredbrain.minecraftxterraria.block;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.ShapeContext;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.ItemPlacementContext;
//import net.minecraft.item.ItemStack;
//import net.minecraft.sound.SoundCategory;
//import net.minecraft.sound.SoundEvents;
//import net.minecraft.state.StateManager;
//import net.minecraft.state.property.IntProperty;
//import net.minecraft.state.property.Property;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import net.minecraft.util.ItemScatterer;
//import net.minecraft.util.hit.BlockHitResult;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Direction;
//import net.minecraft.util.shape.VoxelShape;
//import net.minecraft.world.BlockView;
//import net.minecraft.world.World;
//import net.minecraft.world.event.GameEvent;
//import org.jetbrains.annotations.Nullable;
//
//public class IngotBlock extends AbstractBrickBlock {
//    protected static IntProperty INGOTS;
//    public IngotBlock(Settings settings) {
//        super(settings);
//        this.setDefaultState((BlockState)((BlockState)this.getDefaultState()).with(INGOTS, 1));
//    }
//
//    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
//
//        super.appendProperties(builder);
//        builder.add(new Property[]{INGOTS});
//    }
//
//    public boolean canReplace(BlockState state, ItemPlacementContext context) {
//        return !context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && (Integer)state.get(INGOTS) < 8 ? true : super.canReplace(state, context);
//    }
//
//    @Nullable
//    public BlockState getPlacementState(ItemPlacementContext ctx) {
//        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
//        return blockState.isOf(this) ? (BlockState)blockState.with(INGOTS, Math.min(8, (Integer)blockState.get(INGOTS) + 1)).with(FACING, blockState.get(FACING)) : super.getPlacementState(ctx).with(FACING, ctx.getPlayerFacing());
//    }
//
//    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
//        return (Integer)state.get(INGOTS) > 6 ? SHAPE_5 : (Integer)state.get(INGOTS) > 4 ? SHAPE_4 : (Integer)state.get(INGOTS) > 2 ? SHAPE_3 : (Integer)state.get(INGOTS) > 1 && ((Direction)state.get(FACING) == Direction.NORTH || (Direction)state.get(FACING) == Direction.SOUTH) ? SHAPE_2_SN :  (Integer)state.get(INGOTS) > 1 && ((Direction)state.get(FACING) == Direction.EAST || (Direction)state.get(FACING) == Direction.WEST) ? SHAPE_2_EW : (Direction)state.get(FACING) == Direction.NORTH || (Direction)state.get(FACING) == Direction.SOUTH ? SHAPE_1_SN : SHAPE_1_EW;
//    }
//
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        ItemStack itemStack = player.getStackInHand(hand);
//        if (!(itemStack.isOf(this.asItem()))) {
//            if (state.get(INGOTS) >= 2) {
//                world.setBlockState(pos, state.with(INGOTS, state.get(INGOTS) - 1));
//                world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
//            } else if (state.get(INGOTS) == 1) {
//
//                world.removeBlock(pos, false);
//                world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
//            }
//            if (!player.isCreative()) {
//                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this.asItem()));
//                world.playSound(null, pos, SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F); // TODO find better sound
//            }
//            return ActionResult.SUCCESS;
//        }
//        return ActionResult.PASS;
//    }
//
//    static {
//        INGOTS = IntProperty.of("ingots", 1, 8);
//    }
//}
