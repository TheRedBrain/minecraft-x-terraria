package com.github.theredbrain.minecraftxterraria.mixin.client.network;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import com.github.theredbrain.minecraftxterraria.block.*;
import com.github.theredbrain.minecraftxterraria.client.DuckClientPlayerInteractionManagerMixin;
import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.minecraftxterraria.item.MxTBlockItem;
import com.github.theredbrain.minecraftxterraria.item.tools.IMxTAxePower;
import com.github.theredbrain.minecraftxterraria.item.tools.IMxTHammerPower;
import com.github.theredbrain.minecraftxterraria.item.tools.IMxTPickaxePower;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin implements DuckClientPlayerInteractionManagerMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Final
    private ClientPlayNetworkHandler networkHandler;

    @Shadow
    private BlockPos currentBreakingPos;// = null;

    @Shadow
    private ItemStack selectedStack;

    @Shadow
    private float currentBreakingProgress;

    @Shadow
    private float blockBreakingSoundCooldown;

    @Shadow
    private boolean breakingBlock;

    @Shadow
    private int lastSelectedSlot;

    @Shadow
    public boolean breakBlock(BlockPos pos) {
        throw new AssertionError();
    }

    @Shadow
    private void sendSequencedPacket(ClientWorld world, SequencedPacketCreator packetCreator) {
        throw new AssertionError();
    }

//    @Shadow
//    private boolean isCurrentlyBreaking(BlockPos pos) {
//        throw new AssertionError();
//    }
//
//    @Shadow
//    private void syncSelectedSlot() {
//        throw new AssertionError();
//    }


    /**
     * @author TheRedBrain
     * @reason use custom inventory
     */
    @Overwrite
    private void syncSelectedSlot() {
        int i = ((DuckPlayerEntityMixin)this.client.player).getMxtPlayerInventory().selectedSlot;
        if (i != this.lastSelectedSlot) {
            this.lastSelectedSlot = i;
            this.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(this.lastSelectedSlot));
        }
    }
//    private int cancelBreakingCooldown = 0;
//    @Shadow
//    private void syncSelectedSlot() {
//        throw new AssertionError();
//    }
/*
    public void cancelBlockBreaking() {
        if (this.breakingBlock) {
            BlockState blockState = this.client.world.getBlockState(this.currentBreakingPos);
            this.client.getTutorialManager().onBlockBreaking(this.client.world, this.currentBreakingPos, blockState, -1.0f);
            this.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, this.currentBreakingPos, Direction.DOWN));
            this.breakingBlock = false;
            this.currentBreakingProgress = 0.0f;
            this.client.world.setBlockBreakingInfo(this.client.player.getId(), this.currentBreakingPos, -1);
            this.client.player.resetLastAttackedTicks();
        }
    }*/

    //    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    public boolean mxtAttackBlock(BlockPos pos, BlockHitResult blockHitResult) {
        if (!this.client.world.getWorldBorder().contains(pos)) {
            return false;
        }
        if (!this.breakingBlock || !this.mxtIsCurrentlyBreaking(pos)) {
            if (this.breakingBlock) {
                this.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, this.currentBreakingPos, blockHitResult.getSide()));
            }
            BlockState blockState = this.client.world.getBlockState(pos);
            ItemStack itemStack = this.client.player.getMainHandStack();
            if (!blockState.isAir() && (itemStack.getItem() instanceof IMxTAxePower || itemStack.getItem() instanceof IMxTHammerPower || itemStack.getItem() instanceof IMxTPickaxePower || itemStack.getItem() instanceof MxTBlockItem) && blockState.getBlock() instanceof AbstractMxTBlock) {
                this.client.getTutorialManager().onBlockBreaking(this.client.world, pos, blockState, 0.0f);
                this.sendSequencedPacket(this.client.world, sequence -> {

                    if (this.currentBreakingProgress == 0.0f) {
                        // onBlockBreakStart client side
                        MinecraftXTerraria.LOGGER.info("client onBlockBreakStart");
                        blockState.onBlockBreakStart(this.client.world, pos, this.client.player);
                    }
                    if (((AbstractMxTBlock)blockState.getBlock()).canBeMined(this.client.world, pos, this.client.player)) {
//                        if (itemStack.getItem() instanceof IMxTAxePower) {
//                            this.currentBreakingProgress = ((IMxTAxePower) itemStack.getItem()).getAxePower();//= ((AbstractMxTBlock)blockState.getBlock()).getHitPoints()
//                        } else if (itemStack.getItem() instanceof IMxTHammerPower) {
//                            this.currentBreakingProgress = ((IMxTHammerPower) itemStack.getItem()).getHammerPower();
//                        } else if (itemStack.getItem() instanceof IMxTPickaxePower) {
//                            this.currentBreakingProgress = ((IMxTPickaxePower) itemStack.getItem()).getPickaxePower();
//                        } else {
//                            this.currentBreakingProgress = 0;
//                        }
                        MinecraftXTerraria.LOGGER.info("block can be mined");
                        this.currentBreakingProgress = itemStack.getItem() instanceof IMxTAxePower ? ((IMxTAxePower) itemStack.getItem()).getAxePower() :
                                itemStack.getItem() instanceof IMxTHammerPower ? ((IMxTHammerPower) itemStack.getItem()).getHammerPower() :
                                        itemStack.getItem() instanceof IMxTPickaxePower ? ((IMxTPickaxePower) itemStack.getItem()).getPickaxePower() : 0;

                        MinecraftXTerraria.LOGGER.info("currentBreakingProgress: " + this.currentBreakingProgress);
                        // insta break client side
                        if (this.currentBreakingProgress >= ((AbstractMxTBlock) blockState.getBlock()).getHitPoints()) {
                            MinecraftXTerraria.LOGGER.info("insta break client side");
                            this.breakBlock(pos);
                        } else {
                            MinecraftXTerraria.LOGGER.info("no insta break client side");
                            this.breakingBlock = true;
                            this.currentBreakingPos = pos;
                            this.selectedStack = this.client.player.getMainHandStack();
                            this.blockBreakingSoundCooldown = 0.0f;
                            this.client.world.setBlockBreakingInfo(this.client.player.getId(), this.currentBreakingPos, (int) (this.currentBreakingProgress * 10.0f) - 1);
                        }
                    } else {
                        MinecraftXTerraria.LOGGER.info("can't be mined");
                        this.breakingBlock = false;
                        this.currentBreakingPos = pos;
                        this.selectedStack = this.client.player.getMainHandStack();
                        this.currentBreakingProgress = 0.0f;
                        this.blockBreakingSoundCooldown = 0.0f;
                    }
                    return new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, blockHitResult.getSide(), sequence);
                });
//                MinecraftXTerraria.LOGGER.info("end of attackBlock cancelBreakingCooldown: " + this.cancelBreakingCooldown);
//                MinecraftXTerraria.LOGGER.info("end of attackBlock currentBreakingProgress: " + this.currentBreakingProgress);
                return true;
            }
        }
        return false;
    }

    public void mxtCancelBlockBreaking() {
        if (this.breakingBlock) {
//            if (this.cancelBreakingCooldown <= 0) {
                BlockState blockState = this.client.world.getBlockState(this.currentBreakingPos);
                this.client.getTutorialManager().onBlockBreaking(this.client.world, this.currentBreakingPos, blockState, -1.0f);
                this.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, this.currentBreakingPos, Direction.DOWN));
                this.breakingBlock = false;
                this.currentBreakingProgress = 0.0f;
                this.client.world.setBlockBreakingInfo(this.client.player.getId(), this.currentBreakingPos, -1);
                this.client.player.resetLastAttackedTicks();
//            } else {
//                --this.cancelBreakingCooldown;
//            }
        }
    }

    private boolean mxtIsCurrentlyBreaking(BlockPos pos) {
//        if (pos == null) {
//            return false;
//        } else {
            boolean bl;
            ItemStack itemStack = this.client.player.getMainHandStack();
            boolean bl2 = bl = this.selectedStack.isEmpty() && itemStack.isEmpty();
            if (!this.selectedStack.isEmpty() && !itemStack.isEmpty()) {
                bl = itemStack.isOf(this.selectedStack.getItem()) && ItemStack.areNbtEqual(itemStack, this.selectedStack) && (itemStack.isDamageable() || itemStack.getDamage() == this.selectedStack.getDamage());
            }
            return pos.equals(this.currentBreakingPos) && bl;
//        }
    }
/*
//    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    public boolean mxtAttackBlock(BlockPos pos, BlockHitResult blockHitResult) {
        if (!this.client.world.getWorldBorder().contains(pos)) {
            return false;
        }
        if (this.breakingBlock) {
            this.isCurrentlyBreaking(pos);
        }
        if (this.breakingBlock) {
            this.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, this.currentBreakingPos, blockHitResult.getSide()));
        }
        BlockState blockState = this.client.world.getBlockState(pos);
        ItemStack itemStack = this.client.player.getMainHandStack();
        if ((itemStack.getItem() instanceof IMxTAxePower || itemStack.getItem() instanceof IMxTHammerPower || itemStack.getItem() instanceof IMxTPickaxePower || itemStack.getItem() instanceof MxTBlockItem) && blockState.getBlock() instanceof AbstractMxTBlock) {
            this.client.getTutorialManager().onBlockBreaking(this.client.world, pos, blockState, 0.0f);
            this.sendSequencedPacket(this.client.world, sequence -> {
                boolean bl;
                boolean bl2 = bl = !blockState.isAir() && ((AbstractMxTBlock)blockState.getBlock()).canBeMined(this.client.world, pos, this.client.player);
                if (bl && this.currentBreakingProgress == 0.0f) {
                    // onBlockBreakStart client side
                    blockState.onBlockBreakStart(this.client.world, pos, this.client.player);
                }
                // insta break client side
                if (bl && ((itemStack.getItem() instanceof IMxTAxePower && ((IMxTAxePower)itemStack.getItem()).getAxePower() >= ((AbstractMxTBlock)blockState.getBlock()).getHitPoints())
                        || (itemStack.getItem() instanceof IMxTHammerPower && ((IMxTHammerPower)itemStack.getItem()).getHammerPower() >= ((AbstractMxTBlock)blockState.getBlock()).getHitPoints())
                        || (itemStack.getItem() instanceof IMxTPickaxePower && ((IMxTPickaxePower)itemStack.getItem()).getPickaxePower() >= ((AbstractMxTBlock)blockState.getBlock()).getHitPoints()))) {
                    this.breakBlock(pos);
                } else {
                    this.breakingBlock = true;
                    this.currentBreakingPos = pos;
                    this.selectedStack = this.client.player.getMainHandStack();
                    this.currentBreakingProgress = 0.0f;
                    this.blockBreakingSoundCooldown = 0.0f;
                    this.client.world.setBlockBreakingInfo(this.client.player.getId(), this.currentBreakingPos, (int) (this.currentBreakingProgress * 10.0f) - 1);
                }
                return new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, pos, blockHitResult.getSide(), sequence);
            });
        }
        return true;
    }*/

//    private void mxtSendSequencedPacket(ClientWorld world, MxtSequencedPacketCreator packetCreator) {
//        try (PendingUpdateManager pendingUpdateManager = world.getPendingUpdateManager().incrementSequence();){
//            int i = pendingUpdateManager.getSequence();
//            Packet<MxtServerPlayPacketListener> packet = packetCreator.predict(i);
//            this.networkHandler.sendPacket(packet);
//        }
//    }

//    /**
//     * @author TheRedBrain
//     * @reason use custom inventory
//     */
//    @Overwrite
//    public ActionResult interactItem(PlayerEntity player, Hand hand) {
//        this.syncSelectedSlot();
//        this.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(player.getX(), player.getY(), player.getZ(), player.getYaw(), player.getPitch(), player.isOnGround()));
//        MutableObject mutableObject = new MutableObject();
//        this.mxtSendSequencedPacket(this.client.world, sequence -> {
//            // TODO new MxtPlayerUseItemC2SPacket
//            MxtPlayerUseItemC2SPacket mxtplayerUseItemC2SPacket = new MxtPlayerUseItemC2SPacket(sequence);
//            ItemStack itemStack = player.getMainHandStack();
//            // TODO migrate itemUseCooldown to player
//            if (player.getItemCooldownManager().isCoolingDown(itemStack.getItem()) || !(itemStack.getItem() instanceof MxtUsableItem)) {
//                mutableObject.setValue(ActionResult.PASS);
//                return mxtplayerUseItemC2SPacket;
//            }
//            TypedActionResult<ItemStack> typedActionResult = itemStack.use(this.client.world, player, Hand.MAIN_HAND);
//            ItemStack itemStack2 = typedActionResult.getValue();
//            if (itemStack2 != itemStack) {
//                player.setStackInHand(Hand.MAIN_HAND, itemStack2);
//            }
//            mutableObject.setValue(typedActionResult.getResult());
//            return mxtplayerUseItemC2SPacket;
//        });
//        return (ActionResult)((Object)mutableObject.getValue());
//    }

    // TODO change all calls to updateBlockBreakingProgress
    public boolean mxtUpdateBlockBreakingProgress(BlockPos pos, BlockHitResult blockHitResult) {
        this.syncSelectedSlot();
//        if (this.currentBreakingPos == null) {
//            this.currentBreakingPos = pos;
//        }

        // TODO extract PlayerActionC2SPacket to be send once
        if (this.mxtIsCurrentlyBreaking(pos)) {
            MinecraftXTerraria.LOGGER.info("mxtIsCurrentlyBreaking(" + pos.toString() + ")");
            MinecraftXTerraria.LOGGER.info("start of updateProgress currentBreakingProgress: " + this.currentBreakingProgress);
            BlockState blockState = this.client.world.getBlockState(pos);
            ItemStack heldItem = this.client.player.getMainHandStack();
            if (blockState.isAir() || !(((AbstractMxTBlock)blockState.getBlock()).canBeMined(this.client.world, pos, this.client.player))) {
                this.breakingBlock = false;
//                this.cancelBreakingCooldown = 0;
//                this.currentBreakingPos = null;
                return false;
            }
            if (blockState.getBlock() instanceof AbstractMxTCuttingBlock && heldItem.getItem() instanceof IMxTAxePower) {

                // first time counts double to account for the reset progress in mxtAttackBlock
                if (this.currentBreakingProgress == 0) {
                    this.currentBreakingProgress += ((IMxTAxePower)heldItem.getItem()).getAxePower();
                }
                // update breaking progress
                this.currentBreakingProgress += ((IMxTAxePower)heldItem.getItem()).getAxePower();

                // set cancel breaking cooldown
//                this.cancelBreakingCooldown = 2;

//                // block break sound
//                if (this.blockBreakingSoundCooldown % 4.0f == 0.0f) {
//                    BlockSoundGroup blockSoundGroup = blockState.getSoundGroup();
//                    this.client.getSoundManager().play(new PositionedSoundInstance(blockSoundGroup.getHitSound(), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0f) / 8.0f, blockSoundGroup.getPitch() * 0.5f, SoundInstance.createRandom(), pos));
//                }
//                this.blockBreakingSoundCooldown += 1.0f;
//
//                // TODO tutorial stuff needed?
//                this.client.getTutorialManager().onBlockBreaking(this.client.world, pos, blockState, MathHelper.clamp(this.currentBreakingProgress, 0.0f, 1.0f));
//
//                // check if breaking is complete
//                if (this.currentBreakingProgress >= ((AbstractMxTBlock) blockState.getBlock()).getHitPoints()) {
//                    this.breakingBlock = false;
//                    this.sendSequencedPacket(this.client.world, sequence -> {
//                        this.breakBlock(pos);
//                        return new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, blockHitResult.getSide(), sequence);
//                    });
//                    this.currentBreakingProgress = 0.0f;
//                    this.blockBreakingSoundCooldown = 0.0f;
////                    this.cancelBreakingCooldown = 0;
////                    this.currentBreakingPos = null;
//                }
            } else if (blockState.getBlock() instanceof MxtDiggingBlock && heldItem.getItem() instanceof IMxTHammerPower) {

                // first time counts double to account for the reset progress in mxtAttackBlock
                if (this.currentBreakingProgress == 0) {
                    this.currentBreakingProgress += ((IMxTHammerPower)heldItem.getItem()).getHammerPower();
                }

                // update breaking progress
                this.currentBreakingProgress += ((IMxTHammerPower)heldItem.getItem()).getHammerPower();

                // set cancel breaking cooldown
//                this.cancelBreakingCooldown = 2;

//                // block break sound
//                if (this.blockBreakingSoundCooldown % 4.0f == 0.0f) {
//                    BlockSoundGroup blockSoundGroup = blockState.getSoundGroup();
//                    this.client.getSoundManager().play(new PositionedSoundInstance(blockSoundGroup.getHitSound(), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0f) / 8.0f, blockSoundGroup.getPitch() * 0.5f, SoundInstance.createRandom(), pos));
//                }
//                this.blockBreakingSoundCooldown += 1.0f;
//
//                // TODO tutorial stuff needed?
//                this.client.getTutorialManager().onBlockBreaking(this.client.world, pos, blockState, MathHelper.clamp(this.currentBreakingProgress, 0.0f, 1.0f));
//
//                // check if breaking is complete
//                if (this.currentBreakingProgress >= ((AbstractMxTBlock) blockState.getBlock()).getHitPoints()) {
//                    this.breakingBlock = false;
//                    this.sendSequencedPacket(this.client.world, sequence -> {
//                        this.breakBlock(pos);
//                        return new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, blockHitResult.getSide(), sequence);
//                    });
//                    this.currentBreakingProgress = 0.0f;
//                    this.blockBreakingSoundCooldown = 0.0f;
////                    this.cancelBreakingCooldown = 0;
////                    this.currentBreakingPos = null;
//                }
            } else if ((blockState.getBlock() instanceof MxtDiggingBlock || blockState.getBlock() instanceof AbstractMxTFurnitureBlock) && heldItem.getItem() instanceof IMxTPickaxePower) {

                // first time counts double to account for the reset progress in mxtAttackBlock
                if (this.currentBreakingProgress == 0) {
                    this.currentBreakingProgress += ((IMxTPickaxePower)heldItem.getItem()).getPickaxePower();
                }

                // update breaking progress
                this.currentBreakingProgress += ((IMxTPickaxePower)heldItem.getItem()).getPickaxePower();

                // set cancel breaking cooldown
//                this.cancelBreakingCooldown = 2;

//                // block break sound
//                if (this.blockBreakingSoundCooldown % 4.0f == 0.0f) {
//                    BlockSoundGroup blockSoundGroup = blockState.getSoundGroup();
//                    this.client.getSoundManager().play(new PositionedSoundInstance(blockSoundGroup.getHitSound(), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0f) / 8.0f, blockSoundGroup.getPitch() * 0.5f, SoundInstance.createRandom(), pos));
//                }
//                this.blockBreakingSoundCooldown += 1.0f;
//
//                // TODO tutorial stuff needed?
////                this.client.getTutorialManager().onBlockBreaking(this.client.world, pos, blockState, MathHelper.clamp(this.currentBreakingProgress, 0.0f, 1.0f));
//
//                MinecraftXTerraria.LOGGER.info("end of updateProgress currentBreakingProgress: " + this.currentBreakingProgress);
//                MinecraftXTerraria.LOGGER.info("blockHitPoints: " + ((AbstractMxTBlock) blockState.getBlock()).getHitPoints());
////
////                if ((int) this.currentBreakingProgress >= ((AbstractMxTBlock) blockState.getBlock()).getHitPoints()) {
////                    MinecraftXTerraria.LOGGER.info("1st test");
////                }
////                int a = (int) this.currentBreakingProgress;
////                int b = ((AbstractMxTBlock) blockState.getBlock()).getHitPoints();
//
////                MinecraftXTerraria.LOGGER.info("2nd currentBreakingProgress: " + a);
////                MinecraftXTerraria.LOGGER.info("2nd blockHitPoints: " + b);
//                // check if breaking is complete
//                if ((int) this.currentBreakingProgress >= ((AbstractMxTBlock) blockState.getBlock()).getHitPoints()) {
//                    MinecraftXTerraria.LOGGER.info("breaking complete");
//                    this.breakingBlock = false;
////                    this.cancelBreakingCooldown = 0;
//                    this.sendSequencedPacket(this.client.world, sequence -> {
//                        this.breakBlock(pos);
//                        return new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, blockHitResult.getSide(), sequence);
//                    });
//                    this.currentBreakingProgress = 0.0f;
//                    this.blockBreakingSoundCooldown = 0.0f;
////                    this.currentBreakingPos = null;
//                }
            } else {
                this.breakingBlock = false;
//                this.cancelBreakingCooldown = 0;
//                this.currentBreakingPos = null;
                return false;
            }

            // block break sound
            if (this.blockBreakingSoundCooldown % 4.0f == 0.0f) {
                BlockSoundGroup blockSoundGroup = blockState.getSoundGroup();
                this.client.getSoundManager().play(new PositionedSoundInstance(blockSoundGroup.getHitSound(), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0f) / 8.0f, blockSoundGroup.getPitch() * 0.5f, SoundInstance.createRandom(), pos));
            }
            this.blockBreakingSoundCooldown += 1.0f;

            // TODO tutorial stuff needed?
            this.client.getTutorialManager().onBlockBreaking(this.client.world, pos, blockState, MathHelper.clamp(this.currentBreakingProgress, 0.0f, 1.0f));

//            MinecraftXTerraria.LOGGER.info("end of updateProgress currentBreakingProgress: " + this.currentBreakingProgress);
//            MinecraftXTerraria.LOGGER.info("blockHitPoints: " + ((AbstractMxTBlock) blockState.getBlock()).getHitPoints());

            // check if breaking is complete
            if (this.currentBreakingProgress >= ((AbstractMxTBlock) blockState.getBlock()).getHitPoints()) {
                this.breakingBlock = false;
                this.sendSequencedPacket(this.client.world, sequence -> {
                    this.breakBlock(pos);
                    return new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, pos, blockHitResult.getSide(), sequence);
                });
                this.currentBreakingProgress = 0.0f;
                this.blockBreakingSoundCooldown = 0.0f;
//                    this.cancelBreakingCooldown = 0;
//                    this.currentBreakingPos = null;
            }
        } else {
            return this.mxtAttackBlock(pos, blockHitResult);
        }
        this.client.world.setBlockBreakingInfo(this.client.player.getId(), this.currentBreakingPos, (int)(this.currentBreakingProgress * 10.0f) - 1); // TODO break overlay
        return true;
    }

    // TODO temp
    public float getCurrentBreakingProgress() {
        return this.currentBreakingProgress;
    }
}
