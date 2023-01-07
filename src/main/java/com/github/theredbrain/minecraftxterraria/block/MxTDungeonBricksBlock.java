package com.github.theredbrain.minecraftxterraria.block;

import com.github.theredbrain.minecraftxterraria.item.tool.IMxTPickaxePower;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class MxTDungeonBricksBlock extends AbstractMxTDiggingBlock {

    public static final BooleanProperty CURSED;
    public MxTDungeonBricksBlock(Settings settings) {
        super(settings);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[]{CURSED});
    }

    // TODO explosion resistant

    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        int currentMiningProgress = state.get(MINING_PROGRESS);
        int pickaxePower;
        if (!player.getMainHandStack().isEmpty()) {
            Item currentItem = player.getMainHandStack().getItem();
            if (currentItem instanceof IMxTPickaxePower) {
                pickaxePower = ((IMxTPickaxePower) currentItem).getPickaxePower();
                // check if near world center
//                if (world.get) {
//
//                } else {
                    if (pickaxePower >= 65 && pickaxePower < 70) {
                        if (currentMiningProgress + 3 >= 12) {
                            // block is broken
                            world.breakBlock(pos, true);
                            player.incrementStat(Stats.MINED.getOrCreateStat(this));
                            dropStacks(state, world, pos, blockEntity, player, stack);
                        } else {
                            world.setBlockState(pos, state.with(MINING_PROGRESS, currentMiningProgress + 3));
                        }
                    } else if (pickaxePower >= 70 && pickaxePower < 100) {
                        if (currentMiningProgress + 4 >= 12) {
                            // block is broken
                            world.breakBlock(pos, true);
                            player.incrementStat(Stats.MINED.getOrCreateStat(this));
                            dropStacks(state, world, pos, blockEntity, player, stack);
                        } else {
                            world.setBlockState(pos, state.with(MINING_PROGRESS, currentMiningProgress + 4));
                        }
                    } else if (pickaxePower >= 100 && pickaxePower < 200) {
                        if (currentMiningProgress + 6 >= 12) {
                            // block is broken
                            world.breakBlock(pos, true);
                            player.incrementStat(Stats.MINED.getOrCreateStat(this));
                            dropStacks(state, world, pos, blockEntity, player, stack);
                        } else {
                            world.setBlockState(pos, state.with(MINING_PROGRESS, currentMiningProgress + 6));
                        }
                    } else if (pickaxePower >= 200) {
                        // block is broken
                        world.breakBlock(pos, true);
                        player.incrementStat(Stats.MINED.getOrCreateStat(this));
                        dropStacks(state, world, pos, blockEntity, player, stack);
                    }
//                }
            }
        }
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        // TODO resistant to explosions
    }

    static {
        MINING_PROGRESS = IntProperty.of("mining_progress", 0, 12);
        CURSED = BooleanProperty.of("cursed");
    }
}
