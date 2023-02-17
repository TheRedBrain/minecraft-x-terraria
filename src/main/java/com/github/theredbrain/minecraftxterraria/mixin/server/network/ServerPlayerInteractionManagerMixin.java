package com.github.theredbrain.minecraftxterraria.mixin.server.network;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import com.github.theredbrain.minecraftxterraria.block.*;
import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.minecraftxterraria.item.MxtItem;
import com.github.theredbrain.minecraftxterraria.item.MxTBlockItem;
import com.github.theredbrain.minecraftxterraria.item.tools.MxtToolItem;
import com.github.theredbrain.minecraftxterraria.item.tools.IMxTAxePower;
import com.github.theredbrain.minecraftxterraria.item.tools.IMxTHammerPower;
import com.github.theredbrain.minecraftxterraria.item.tools.IMxTPickaxePower;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OperatorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {

    @Shadow
    @Final
    private static Logger LOGGER;

    @Shadow
    protected ServerWorld world;

    @Shadow
    @Final
    protected ServerPlayerEntity player;

    @Shadow
    private GameMode gameMode;

    @Shadow
    private boolean mining;

    @Shadow
    private int startMiningTime;

    @Shadow
    private BlockPos miningPos;

    @Shadow
    private int tickCounter;

    @Shadow
    private boolean failedToMine;

    @Shadow
    private BlockPos failedMiningPos;

    @Shadow
    private int failedStartMiningTime;

    @Shadow
    private int blockBreakingProgress;

    @Shadow
    public boolean isCreative() {
        throw new AssertionError();
    }

//    @Shadow
//    public boolean tryBreakBlock(BlockPos pos) {
//        throw new AssertionError();
//    }

    @Shadow
    private void method_41250(BlockPos pos, boolean success, int sequence, String reason) {
        throw new AssertionError();
    }

//    @Shadow
//    public void finishMining(BlockPos pos, int sequence, String reason) {
//        throw new AssertionError();
//    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void update() {
        ++this.tickCounter;
        if (this.failedToMine) {
            BlockState blockState = this.world.getBlockState(this.failedMiningPos);
            if (blockState.isAir()) {
                this.failedToMine = false;
            } else {
                if (blockState.getBlock() instanceof AbstractMxTBlock) {
                    if (this.mxtContinueMining(blockState, this.failedMiningPos, this.failedStartMiningTime) >= ((AbstractMxTBlock) blockState.getBlock()).getHitPoints()) { // TODO check for f >= block hit points
                        this.failedToMine = false;
                        this.mxtTryBreakBlock(this.failedMiningPos);
                    }
                } else {
                    this.world.setBlockBreakingInfo(this.player.getId(), this.miningPos, -1);
                    this.blockBreakingProgress = -1;
                    this.mining = false;
                }
            }
        } else if (this.mining) {
            BlockState blockState = this.world.getBlockState(this.miningPos);
            if (blockState.isAir()) {
                this.world.setBlockBreakingInfo(this.player.getId(), this.miningPos, -1);
                this.blockBreakingProgress = -1;
                this.mining = false;
            } else {
                this.mxtContinueMining(blockState, this.miningPos, this.startMiningTime);
            }
        }
    }

    // calculate current breaking progress
    private int mxtContinueMining(BlockState state, BlockPos pos, int failedStartMiningTime) {
        int miningTicksAmount = this.tickCounter - failedStartMiningTime + 1;
        int newBlockBreakingProgress = 0;
        ItemStack itemStack = this.player.getMainHandStack();

        if (!state.isAir() && itemStack.getItem() instanceof MxtToolItem) {
            int toolMiningSpeed = ((MxtToolItem) itemStack.getItem()).getMiningSpeed();
            int k = ((miningTicksAmount - (miningTicksAmount % toolMiningSpeed)) / toolMiningSpeed);

//            MinecraftXTerraria.LOGGER.info("server continueMining k: " + k);
            if (k >= 1) {
                k++;
            }
            if (state.getBlock() instanceof AbstractMxTCuttingBlock && itemStack.getItem() instanceof IMxTAxePower) {
                newBlockBreakingProgress = k * ((IMxTAxePower) itemStack.getItem()).getAxePower();
            } else if (state.getBlock() instanceof MxtDiggingBlock && itemStack.getItem() instanceof IMxTHammerPower) {
                newBlockBreakingProgress = k * ((IMxTHammerPower)itemStack.getItem()).getHammerPower();
            } else if ((state.getBlock() instanceof MxtDiggingBlock || state.getBlock() instanceof AbstractMxTFurnitureBlock) && itemStack.getItem() instanceof IMxTPickaxePower) {
                newBlockBreakingProgress = k * ((IMxTPickaxePower) itemStack.getItem()).getPickaxePower();
            }
        }

        if (newBlockBreakingProgress != this.blockBreakingProgress) {
            this.world.setBlockBreakingInfo(this.player.getId(), pos, newBlockBreakingProgress);
            this.blockBreakingProgress = newBlockBreakingProgress;
        }
        return newBlockBreakingProgress;
    }

    public void mxtFinishMining(BlockPos pos, int sequence, String reason) {
        if (this.mxtTryBreakBlock(pos)) {
            this.method_41250(pos, true, sequence, reason);
        } else {
            this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(pos, this.world.getBlockState(pos)));
            this.method_41250(pos, false, sequence, reason);
        }
    }

    public boolean mxtTryBreakBlock(BlockPos pos) {
        BlockState blockState = this.world.getBlockState(pos);
        if (!this.player.getMainHandStack().getItem().canMine(blockState, this.world, pos, this.player)) {
            return false;
        }
        BlockEntity blockEntity = this.world.getBlockEntity(pos);
        Block block = blockState.getBlock();
        if (block instanceof OperatorBlock && !this.player.isCreativeLevelTwoOp()) {
            this.world.updateListeners(pos, blockState, blockState, Block.NOTIFY_ALL);
            return false;
        }
        if (this.player.isBlockBreakingRestricted(this.world, pos, this.gameMode)) {
            return false;
        }
        block.onBreak(this.world, pos, blockState, this.player);
        boolean bl = this.world.removeBlock(pos, false);
        if (bl) {
            block.onBroken(this.world, pos, blockState);
        }
        if (this.isCreative()) {
            return true;
        }
        ItemStack itemStack = this.player.getMainHandStack();
        ItemStack itemStack2 = itemStack.copy();
        boolean bl2 = this.player.canHarvest(blockState);
        itemStack.postMine(this.world, blockState, pos, this.player);
        if (bl && bl2) {
            block.afterBreak(this.world, this.player, pos, blockState, blockEntity, itemStack2);
        }
        return true;
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void processBlockBreakingAction(BlockPos pos, PlayerActionC2SPacket.Action action, Direction direction, int worldHeight, int sequence) {
        if (this.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter(pos)) > ServerPlayNetworkHandler.MAX_BREAK_SQUARED_DISTANCE) {
            this.method_41250(pos, false, sequence, "too far");
            return;
        }
        if (pos.getY() >= worldHeight) {
            this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(pos, this.world.getBlockState(pos)));
            this.method_41250(pos, false, sequence, "too high");
            return;
        }
        if (action == PlayerActionC2SPacket.Action.START_DESTROY_BLOCK) {
            if (!this.world.canPlayerModifyAt(this.player, pos)) {
                this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(pos, this.world.getBlockState(pos)));
                this.method_41250(pos, false, sequence, "may not interact");
                return;
            }
            if (this.player.isBlockBreakingRestricted(this.world, pos, this.gameMode)) {
                this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(pos, this.world.getBlockState(pos)));
                this.method_41250(pos, false, sequence, "block action restricted");
                return;
            }
            ItemStack itemStack = this.player.getMainHandStack();
            if (itemStack.getItem() instanceof MxTBlockItem) {
//                ActionResult actionResult;
                MinecraftXTerraria.LOGGER.info("try block placing server side");
                if ((((MxTBlockItem)itemStack.getItem()).tryPlacing(this.world, itemStack, pos, direction)).isAccepted()) {
                    MinecraftXTerraria.LOGGER.info("block placing server side successful");
                    Criteria.ITEM_USED_ON_BLOCK.trigger(player, pos, itemStack);
                }
                return;
            }
            this.startMiningTime = this.tickCounter;
            int i = 0;
            BlockState blockState = this.world.getBlockState(pos);
            boolean canBeMined = ((AbstractMxTBlock) blockState.getBlock()).canBeMined(this.world, pos, this.player);

            if (!blockState.isAir() && canBeMined) {
                if (blockState.getBlock() instanceof AbstractMxTCuttingBlock && itemStack.getItem() instanceof IMxTAxePower) { // TODO use canBeMined to check for all actions
                        blockState.onBlockBreakStart(this.world, pos, this.player);
                        i = ((IMxTAxePower)itemStack.getItem()).getAxePower();

                } else if (blockState.getBlock() instanceof MxtDiggingBlock && itemStack.getItem() instanceof IMxTHammerPower) {
                        blockState.onBlockBreakStart(this.world, pos, this.player);
                        i = ((IMxTHammerPower)itemStack.getItem()).getHammerPower();

                } else if ((blockState.getBlock() instanceof MxtDiggingBlock || blockState.getBlock() instanceof AbstractMxTFurnitureBlock)
                    && itemStack.getItem() instanceof IMxTPickaxePower) {
                        blockState.onBlockBreakStart(this.world, pos, this.player);
                        i = ((IMxTPickaxePower)itemStack.getItem()).getPickaxePower();
                }
            }
            if (!blockState.isAir() && i >= ((AbstractMxTBlock)blockState.getBlock()).getHitPoints() && canBeMined) {
                this.mxtFinishMining(pos, sequence, "insta mine");
            } else if (canBeMined) {
                if (this.mining) {
                    this.player.networkHandler.sendPacket(new BlockUpdateS2CPacket(this.miningPos, this.world.getBlockState(this.miningPos)));
                    this.method_41250(pos, false, sequence, "abort destroying since another started (client insta mine, server disagreed)");
                }
                this.mining = true;
                this.miningPos = pos.toImmutable();
//                int i = (int)(f * 10.0f);
                this.world.setBlockBreakingInfo(this.player.getId(), pos, i);
                this.method_41250(pos, true, sequence, "actual start of destroying");
                this.blockBreakingProgress = i;
            }
        } else if (action == PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK) {
            if (pos.equals(this.miningPos)) {
                MinecraftXTerraria.LOGGER.info("package arrived server side");
                int miningTicksAmount = this.tickCounter - this.startMiningTime + 1;
                BlockState blockState = this.world.getBlockState(pos);
                ItemStack itemStack = this.player.getMainHandStack();
                if (!blockState.isAir() && itemStack.getItem() instanceof MxtToolItem) {
                    int toolMiningSpeed = ((MxtToolItem) itemStack.getItem()).getMiningSpeed();
                    int k = ((miningTicksAmount - (miningTicksAmount % toolMiningSpeed)) / toolMiningSpeed);

                    if (blockState.getBlock() instanceof AbstractMxTCuttingBlock && itemStack.getItem() instanceof IMxTAxePower) {
                        if (k * ((IMxTAxePower)itemStack.getItem()).getAxePower() >= ((AbstractMxTBlock) blockState.getBlock()).getHitPoints()) {
                            this.mining = false;
                            this.world.setBlockBreakingInfo(this.player.getId(), pos, -1);
                            this.mxtFinishMining(pos, sequence, "destroyed");
                            return;
                        }
                        if (!this.failedToMine) {
                            this.mining = false;
                            this.failedToMine = true;
                            this.failedMiningPos = pos;
                            this.failedStartMiningTime = this.startMiningTime;
                        }
                    } else if (blockState.getBlock() instanceof MxtDiggingBlock && itemStack.getItem() instanceof IMxTHammerPower) {
                        if (k * ((IMxTHammerPower)itemStack.getItem()).getHammerPower() >= ((AbstractMxTBlock) blockState.getBlock()).getHitPoints()) {
                            this.mining = false;
                            this.world.setBlockBreakingInfo(this.player.getId(), pos, -1);
                            this.mxtFinishMining(pos, sequence, "destroyed");
                            return;
                        }
                        if (!this.failedToMine) {
                            this.mining = false;
                            this.failedToMine = true;
                            this.failedMiningPos = pos;
                            this.failedStartMiningTime = this.startMiningTime;
                        }
                    } else if ((blockState.getBlock() instanceof MxtDiggingBlock || blockState.getBlock() instanceof AbstractMxTFurnitureBlock) && itemStack.getItem() instanceof IMxTPickaxePower) {
                        MinecraftXTerraria.LOGGER.info("digging block or furniture and digging tool");
                        if (k * ((IMxTPickaxePower)itemStack.getItem()).getPickaxePower() >= ((AbstractMxTBlock) blockState.getBlock()).getHitPoints()) {
                            MinecraftXTerraria.LOGGER.info("breakingProcess >= block hitpoints");
                            this.mining = false;
                            this.world.setBlockBreakingInfo(this.player.getId(), pos, -1);
                            this.mxtFinishMining(pos, sequence, "destroyed");
                            return;
                        }
                        MinecraftXTerraria.LOGGER.info("server test");
                        if (!this.failedToMine) {
                            this.mining = false;
                            this.failedToMine = true;
                            this.failedMiningPos = pos;
                            this.failedStartMiningTime = this.startMiningTime;
                        }
                    }
                }
            }
            this.method_41250(pos, true, sequence, "stopped destroying");
        } else if (action == PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK) {
            this.mining = false;
            if (!Objects.equals(this.miningPos, pos) && !(this.player.getMainHandStack().getItem() instanceof MxtItem)) {
                LOGGER.warn("Mismatch in destroy block pos: {} {}", (Object)this.miningPos, (Object)pos); // TODO maybe check if held item instanceof MxTBlockItem and suppress the warning
                this.world.setBlockBreakingInfo(this.player.getId(), this.miningPos, -1);
                this.method_41250(pos, true, sequence, "aborted mismatched destroying");
            }
            this.world.setBlockBreakingInfo(this.player.getId(), pos, -1);
            this.method_41250(pos, true, sequence, "aborted destroying");
        }
    }

//    /**
//     * @author TheRedBrain
//     */
//    @Overwrite
//    public ActionResult interactItem(ServerPlayerEntity player, World world, ItemStack stack, Hand hand) {
////        if (this.gameMode == GameMode.SPECTATOR) {
////            return ActionResult.PASS;
////        }
//        // TODO migrate itemUseCooldown to player
//        if (player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
//            return ActionResult.PASS;
//        }
//        int i = stack.getCount();
//        int j = stack.getDamage();
//        TypedActionResult<ItemStack> typedActionResult = stack.use(world, player, Hand.MAIN_HAND);
//        ItemStack itemStack = typedActionResult.getValue();
//        if (itemStack == stack && itemStack.getCount() == i && itemStack.getMaxUseTime() <= 0 && itemStack.getDamage() == j) {
//            return typedActionResult.getResult();
//        }
//        if (typedActionResult.getResult() == ActionResult.FAIL && itemStack.getMaxUseTime() > 0 && !player.isUsingItem()) {
//            return typedActionResult.getResult();
//        }
//        if (stack != itemStack) {
//            player.setStackInHand(Hand.MAIN_HAND, itemStack);
//        }
////        if (this.isCreative()) {
////            itemStack.setCount(i);
////            if (itemStack.isDamageable() && itemStack.getDamage() != j) {
////                itemStack.setDamage(j);
////            }
////        }
//        if (itemStack.isEmpty()) {
//            player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
//        }
//        // TODO set LivingFlags when using items
//        if (!player.isUsingItem()) {
//            ((DuckPlayerEntityMixin)player).getMxtPlayerScreenHandler().syncState();
//        }
//        return typedActionResult.getResult();
//    }
}
