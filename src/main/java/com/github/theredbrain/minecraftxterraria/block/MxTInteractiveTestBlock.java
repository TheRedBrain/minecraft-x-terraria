package com.github.theredbrain.minecraftxterraria.block;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeverBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.lwjgl.stb.STBIWriteCallbackI;
import org.spongepowered.asm.mixin.Overwrite;

public class MxTInteractiveTestBlock extends AbstractMxTInteractiveFurnitureBlock {
    public static final BooleanProperty SWITCH;
    public MxTInteractiveTestBlock(Settings settings, int hitPoints) {
        super(settings, hitPoints);
        this.setDefaultState(this.getStateManager().getDefaultState().with(RED_WIRE, 0).with(SWITCH, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{SWITCH});
//        builder.add(new Property[]{MINING_PROGRESS, RED_WIRE, GREEN_WIRE, BLUE_WIRE, YELLOW_WIRE});
    }

//    @Override
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        MinecraftXTerraria.LOGGER.info("Interaction initiated");
//        BlockPos blockPos = hit.getBlockPos();
//        BlockState blockState = world.getBlockState(blockPos);
////        if (world.isClient()) {
////            BlockState newBlockState = (BlockState)blockState.cycle(SWITCH);
////            return ActionResult.SUCCESS;
////        }
//        BlockState newBlockState = this.toggleSwitch(blockState, world, blockPos);
////        float f = newBlockState.get(SWITCH) != false ? 0.6f : 0.5f;
////        world.playSound(null, blockPos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3f, f);
//        world.emitGameEvent((Entity)player, newBlockState.get(SWITCH) != false ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, blockPos);
//        return ActionResult.CONSUME;
        /*
        world.emitGameEvent((Entity)player, blockState.get(POWERED) != false ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
        return ActionResult.CONSUME;
        if (world.setBlockState(blockPos, world.getBlockState(blockPos).cycle(MxTInteractiveTestBlock.SWITCH))) {
            MinecraftXTerraria.LOGGER.info("Interaction successful");
            return ActionResult.SUCCESS;
        } else {
            MinecraftXTerraria.LOGGER.info("Interaction failed");
            return ActionResult.FAIL;
        }*/
//    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        if (world.isClient) {
//            BlockState blockState = (BlockState)state.cycle(SWITCH);
////            if (blockState.get(SWITCH).booleanValue()) {
////                LeverBlock.spawnParticles(blockState, world, pos, 1.0f);
////            }
//            return ActionResult.SUCCESS;
//        }
        BlockState blockState = this.toggleSwitch(state, world, pos);
        float f = blockState.get(SWITCH) != false ? 0.6f : 0.5f;
        world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3f, f);
        world.emitGameEvent((Entity)player, blockState.get(SWITCH) != false ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
        return ActionResult.CONSUME;
    }

    public BlockState toggleSwitch(BlockState state, World world, BlockPos pos) {
        state = (BlockState)state.cycle(SWITCH);
        world.setBlockState(pos, state, Block.NOTIFY_ALL);
//        this.updateNeighbors(state, world, pos);
        return state;
    }

    static {
        SWITCH = BooleanProperty.of("switch");
    }
}
