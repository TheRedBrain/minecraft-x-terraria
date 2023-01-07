package com.github.theredbrain.minecraftxterraria.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    /*
    private void handleBlockBreaking(boolean breaking) {
        if (!breaking) {
            this.attackCooldown = 0;
        }

        if (this.attackCooldown <= 0 && !this.player.isUsingItem()) {
            if (breaking && this.crosshairTarget != null && this.crosshairTarget.getType() == net.minecraft.util.hit.HitResult.Type.BLOCK) {
                BlockHitResult blockHitResult = (BlockHitResult)this.crosshairTarget;
                BlockPos blockPos = blockHitResult.getBlockPos();
                if (!this.world.getBlockState(blockPos).isAir()) {
                    Direction direction = blockHitResult.getSide();
                    if (this.interactionManager.updateBlockBreakingProgress(blockPos, direction)) {
                        this.particleManager.addBlockBreakingParticles(blockPos, direction);
                        this.player.swingHand(Hand.MAIN_HAND);
                    }
                }

            } else {
                this.interactionManager.cancelBlockBreaking();
            }
        }
    }
    */
/*
    // called on left-click
    // uses the currently held item
    // attacking with weapons and block breaking with tools
    // placing blocks when targeting another block
    // using of other items like consumables
    private boolean doAttack() {
        // this is the useTime determined by the last used item
        if (this.attackCooldown > 0) {
            return false;
        } else if (this.crosshairTarget == null) {
            LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
            if (this.interactionManager.hasLimitedAttackSpeed()) {
                this.attackCooldown = 10;
            }

            return false;
        } else if (this.player.isRiding()) {
            return false;
        } else {
            ItemStack itemStack = this.player.getStackInHand(Hand.MAIN_HAND);
            if (!itemStack.isItemEnabled(this.world.getEnabledFeatures())) {
                return false;
            } else {
                boolean bl = false;
                switch(this.crosshairTarget.getType()) {
                    case ENTITY:
                        this.interactionManager.attackEntity(this.player, ((EntityHitResult)this.crosshairTarget).getEntity());
                        break;
                    case BLOCK:
                        BlockHitResult blockHitResult = (BlockHitResult)this.crosshairTarget;
                        BlockPos blockPos = blockHitResult.getBlockPos();
                        if (!this.world.getBlockState(blockPos).isAir()) {
                            this.interactionManager.attackBlock(blockPos, blockHitResult.getSide());
                            if (this.world.getBlockState(blockPos).isAir()) {
                                bl = true;
                            }
                            break;
                        }
                    case MISS:
                        if (this.interactionManager.hasLimitedAttackSpeed()) {
                            this.attackCooldown = 10;
                        }

                        this.player.resetLastAttackedTicks();
                }

                this.player.swingHand(Hand.MAIN_HAND);
                return bl;
            }
        }
    }*/

    /*
    // called on right-click
    // used for interaction with placed objects like chests, doors, etc and npcs
    private void doItemUse() {
        if (!this.interactionManager.isBreakingBlock()) {
            this.itemUseCooldown = 4;
            if (!this.player.isRiding()) {
                if (this.crosshairTarget == null) {
                    LOGGER.warn("Null returned as 'hitResult', this shouldn't happen!");
                }

                Hand[] var1 = Hand.values();
                int var2 = var1.length;

                for(int var3 = 0; var3 < var2; ++var3) {
                    Hand hand = var1[var3];
                    ItemStack itemStack = this.player.getStackInHand(hand);
                    if (!itemStack.isItemEnabled(this.world.getEnabledFeatures())) {
                        return;
                    }

                    if (this.crosshairTarget != null) {
                        switch(this.crosshairTarget.getType()) {
                            case ENTITY:
                                EntityHitResult entityHitResult = (EntityHitResult)this.crosshairTarget;
                                Entity entity = entityHitResult.getEntity();
                                if (!this.world.getWorldBorder().contains(entity.getBlockPos())) {
                                    return;
                                }

                                ActionResult actionResult = this.interactionManager.interactEntityAtLocation(this.player, entity, entityHitResult, hand);
                                if (!actionResult.isAccepted()) {
                                    actionResult = this.interactionManager.interactEntity(this.player, entity, hand);
                                }

                                if (actionResult.isAccepted()) {
                                    if (actionResult.shouldSwingHand()) {
                                        this.player.swingHand(hand);
                                    }

                                    return;
                                }
                                break;
                            case BLOCK:
                                BlockHitResult blockHitResult = (BlockHitResult)this.crosshairTarget;
                                int i = itemStack.getCount();
                                ActionResult actionResult2 = this.interactionManager.interactBlock(this.player, hand, blockHitResult);
                                if (actionResult2.isAccepted()) {
                                    if (actionResult2.shouldSwingHand()) {
                                        this.player.swingHand(hand);
                                        if (!itemStack.isEmpty() && (itemStack.getCount() != i || this.interactionManager.hasCreativeInventory())) {
                                            this.gameRenderer.firstPersonRenderer.resetEquipProgress(hand);
                                        }
                                    }

                                    return;
                                }

                                if (actionResult2 == ActionResult.FAIL) {
                                    return;
                                }
                        }
                    }

                    if (!itemStack.isEmpty()) {
                        ActionResult actionResult3 = this.interactionManager.interactItem(this.player, hand);
                        if (actionResult3.isAccepted()) {
                            if (actionResult3.shouldSwingHand()) {
                                this.player.swingHand(hand);
                            }

                            this.gameRenderer.firstPersonRenderer.resetEquipProgress(hand);
                            return;
                        }
                    }
                }

            }
        }
    }

    public void tick() {
        if (this.itemUseCooldown > 0) {
            --this.itemUseCooldown;
        }

        this.profiler.push("gui");
        this.messageHandler.processDelayedMessages();
        this.inGameHud.tick(this.paused);
        this.profiler.pop();
        this.gameRenderer.updateTargetedEntity(1.0F);
        this.tutorialManager.tick(this.world, this.crosshairTarget);
        this.profiler.push("gameMode");
        if (!this.paused && this.world != null) {
            this.interactionManager.tick();
        }

        this.profiler.swap("textures");
        if (this.world != null) {
            this.textureManager.tick();
        }

        if (this.currentScreen == null && this.player != null) {
            if (this.player.isDead() && !(this.currentScreen instanceof DeathScreen)) {
                this.setScreen((Screen)null);
            } else if (this.player.isSleeping() && this.world != null) {
                this.setScreen(new SleepingChatScreen());
            }
        } else {
            Screen var2 = this.currentScreen;
            if (var2 instanceof SleepingChatScreen) {
                SleepingChatScreen sleepingChatScreen = (SleepingChatScreen)var2;
                if (!this.player.isSleeping()) {
                    sleepingChatScreen.closeChatIfEmpty();
                }
            }
        }

        if (this.currentScreen != null) {
            this.attackCooldown = 10000;
        }

        if (this.currentScreen != null) {
            Screen.wrapScreenError(() -> {
                this.currentScreen.tick();
            }, "Ticking screen", this.currentScreen.getClass().getCanonicalName());
        }

        if (!this.options.debugEnabled) {
            this.inGameHud.resetDebugHudChunk();
        }

        if (this.overlay == null && (this.currentScreen == null || this.currentScreen.passEvents)) {
            this.profiler.swap("Keybindings");
            this.handleInputEvents();
            if (this.attackCooldown > 0) {
                --this.attackCooldown;
            }
        }

        if (this.world != null) {
            this.profiler.swap("gameRenderer");
            if (!this.paused) {
                this.gameRenderer.tick();
            }

            this.profiler.swap("levelRenderer");
            if (!this.paused) {
                this.worldRenderer.tick();
            }

            this.profiler.swap("level");
            if (!this.paused) {
                if (this.world.getLightningTicksLeft() > 0) {
                    this.world.setLightningTicksLeft(this.world.getLightningTicksLeft() - 1);
                }

                this.world.tickEntities();
            }
        } else if (this.gameRenderer.getPostProcessor() != null) {
            this.gameRenderer.disablePostProcessor();
        }

        if (!this.paused) {
            this.musicTracker.tick();
        }

        this.soundManager.tick(this.paused);
        if (this.world != null) {
            if (!this.paused) {
                if (!this.options.joinedFirstServer && this.isConnectedToServer()) {
                    Text text = Text.translatable("tutorial.socialInteractions.title");
                    Text text2 = Text.translatable("tutorial.socialInteractions.description", new Object[]{TutorialManager.keyToText("socialInteractions")});
                    this.socialInteractionsToast = new TutorialToast(net.minecraft.client.toast.TutorialToast.Type.SOCIAL_INTERACTIONS, text, text2, true);
                    this.tutorialManager.add(this.socialInteractionsToast, 160);
                    this.options.joinedFirstServer = true;
                    this.options.write();
                }

                this.tutorialManager.tick();

                try {
                    this.world.tick(() -> {
                        return true;
                    });
                } catch (Throwable var4) {
                    CrashReport crashReport = CrashReport.create(var4, "Exception in world tick");
                    if (this.world == null) {
                        CrashReportSection crashReportSection = crashReport.addElement("Affected level");
                        crashReportSection.add("Problem", "Level is null!");
                    } else {
                        this.world.addDetailsToCrashReport(crashReport);
                    }

                    throw new CrashException(crashReport);
                }
            }

            this.profiler.swap("animateTick");
            if (!this.paused && this.world != null) {
                this.world.doRandomBlockDisplayTicks(this.player.getBlockX(), this.player.getBlockY(), this.player.getBlockZ());
            }

            this.profiler.swap("particles");
            if (!this.paused) {
                this.particleManager.tick();
            }
        } else if (this.integratedServerConnection != null) {
            this.profiler.swap("pendingConnection");
            this.integratedServerConnection.tick();
        }

        this.profiler.swap("keyboard");
        this.keyboard.pollDebugCrash();
        this.profiler.pop();
    }

    private void handleInputEvents() {
        for(; this.options.togglePerspectiveKey.wasPressed(); this.worldRenderer.scheduleTerrainUpdate()) {
            Perspective perspective = this.options.getPerspective();
            this.options.setPerspective(this.options.getPerspective().next());
            if (perspective.isFirstPerson() != this.options.getPerspective().isFirstPerson()) {
                this.gameRenderer.onCameraEntitySet(this.options.getPerspective().isFirstPerson() ? this.getCameraEntity() : null);
            }
        }

        while(this.options.smoothCameraKey.wasPressed()) {
            this.options.smoothCameraEnabled = !this.options.smoothCameraEnabled;
        }

        for(int i = 0; i < 9; ++i) {
            boolean bl = this.options.saveToolbarActivatorKey.isPressed();
            boolean bl2 = this.options.loadToolbarActivatorKey.isPressed();
            if (this.options.hotbarKeys[i].wasPressed()) {
                if (this.player.isSpectator()) {
                    this.inGameHud.getSpectatorHud().selectSlot(i);
                } else if (!this.player.isCreative() || this.currentScreen != null || !bl2 && !bl) {
                    this.player.getInventory().selectedSlot = i;
                } else {
                    CreativeInventoryScreen.onHotbarKeyPress(this, i, bl2, bl);
                }
            }
        }

        while(this.options.socialInteractionsKey.wasPressed()) {
            if (!this.isConnectedToServer()) {
                this.player.sendMessage(SOCIAL_INTERACTIONS_NOT_AVAILABLE, true);
                this.narratorManager.narrate(SOCIAL_INTERACTIONS_NOT_AVAILABLE);
            } else {
                if (this.socialInteractionsToast != null) {
                    this.tutorialManager.remove(this.socialInteractionsToast);
                    this.socialInteractionsToast = null;
                }

                this.setScreen(new SocialInteractionsScreen());
            }
        }

        while(this.options.inventoryKey.wasPressed()) {
            if (this.interactionManager.hasRidingInventory()) {
                this.player.openRidingInventory();
            } else {
                this.tutorialManager.onInventoryOpened();
                this.setScreen(new InventoryScreen(this.player));
            }
        }

        while(this.options.advancementsKey.wasPressed()) {
            this.setScreen(new AdvancementsScreen(this.player.networkHandler.getAdvancementHandler()));
        }

        while(this.options.swapHandsKey.wasPressed()) {
            if (!this.player.isSpectator()) {
                this.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
            }
        }

        while(this.options.dropKey.wasPressed()) {
            if (!this.player.isSpectator() && this.player.dropSelectedItem(Screen.hasControlDown())) {
                this.player.swingHand(Hand.MAIN_HAND);
            }
        }

        while(this.options.chatKey.wasPressed()) {
            this.openChatScreen("");
        }

        if (this.currentScreen == null && this.overlay == null && this.options.commandKey.wasPressed()) {
            this.openChatScreen("/");
        }

        boolean bl3 = false;
        if (this.player.isUsingItem()) {
            if (!this.options.useKey.isPressed()) {
                this.interactionManager.stopUsingItem(this.player);
            }

            label117:
            while(true) {
                if (!this.options.attackKey.wasPressed()) {
                    while(this.options.useKey.wasPressed()) {
                    }

                    while(true) {
                        if (this.options.pickItemKey.wasPressed()) {
                            continue;
                        }
                        break label117;
                    }
                }
            }
        } else {
            while(this.options.attackKey.wasPressed()) {
                bl3 |= this.doAttack();
            }

            while(this.options.useKey.wasPressed()) {
                this.doItemUse();
            }

            while(this.options.pickItemKey.wasPressed()) {
                this.doItemPick();
            }
        }

        if (this.options.useKey.isPressed() && this.itemUseCooldown == 0 && !this.player.isUsingItem()) {
            this.doItemUse();
        }

        this.handleBlockBreaking(this.currentScreen == null && !bl3 && this.options.attackKey.isPressed() && this.mouse.isCursorLocked());
    }
     */
}
