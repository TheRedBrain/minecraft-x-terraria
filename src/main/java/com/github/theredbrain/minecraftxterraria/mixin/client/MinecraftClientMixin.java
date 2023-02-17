package com.github.theredbrain.minecraftxterraria.mixin.client;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import com.github.theredbrain.minecraftxterraria.block.AbstractMxTBlock;
import com.github.theredbrain.minecraftxterraria.client.DuckClientPlayerInteractionManagerMixin;
import com.github.theredbrain.minecraftxterraria.client.gui.screen.MxtCharacterCreationScreen;
import com.github.theredbrain.minecraftxterraria.client.gui.screen.ingame.MxtPlayerInventoryScreen;
import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.minecraftxterraria.item.*;
import com.github.theredbrain.minecraftxterraria.item.tools.MxtToolItem;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Overlay;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SleepingChatScreen;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.multiplayer.SocialInteractionsScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.toast.TutorialToast;
import net.minecraft.client.tutorial.TutorialManager;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.profiler.Profiler;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

//@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin/* implements DuckMinecraftClientMixin*/ {

    @Shadow
    @Final
    private static Logger LOGGER;

    @Shadow
    @Final
    private static Text SOCIAL_INTERACTIONS_NOT_AVAILABLE;

    @Shadow
    @Final
    private TextureManager textureManager;

    @Shadow
    @Final
    public WorldRenderer worldRenderer;

    @Shadow
    @Final
    public ParticleManager particleManager;

    @Shadow
    @Final
    public GameRenderer gameRenderer;

    @Shadow
    @Final
    public InGameHud inGameHud;

    @Shadow
    @Final
    public GameOptions options;

    @Shadow
    @Final
    public Mouse mouse;

    @Shadow
    @Final
    public Keyboard keyboard;

    @Shadow
    @Final
    private SoundManager soundManager;

    @Shadow
    @Final
    private MusicTracker musicTracker;

    @Shadow
    @Final
    private TutorialManager tutorialManager;

    @Shadow
    private TutorialToast socialInteractionsToast;

    //    @Nullable
    @Shadow
    public ClientPlayerInteractionManager interactionManager;

    //    @Nullable
    @Shadow
    public ClientWorld world;

    //    @Nullable
    @Shadow
    public ClientPlayerEntity player;

    @Shadow
    private IntegratedServer server;

    //    @Nullable
    @Shadow
    private ClientConnection integratedServerConnection;

    //    @Nullable
    @Shadow
    public HitResult crosshairTarget;

    @Shadow
    private int itemUseCooldown; // is determined by the useTime

//    @Shadow
//    protected int attackCooldown; // is determined by the miningSpeed

    @Shadow
    private volatile boolean paused;

    //    @Nullable
    @Shadow
    public Screen currentScreen;

    @Shadow
    private Overlay overlay;

    @Shadow
    private Profiler profiler;

    @Shadow
    @Final
    private NarratorManager narratorManager;

    @Shadow
    @Final
    private MessageHandler messageHandler;

    @Shadow
    private void openChatScreen(String text) {
        throw new AssertionError();
    }

    @Shadow
    public void setScreen(@Nullable Screen screen) {
        throw new AssertionError();
    }

    @Shadow
    @Final
    public void doItemUse() {
        throw new AssertionError();
    }

    @Shadow
    private boolean isConnectedToServer() {
        throw new AssertionError();
    }

    @Shadow
    public ClientPlayNetworkHandler getNetworkHandler() {
        throw new AssertionError();
    }

    @Shadow
    private void doItemPick() {
        throw new AssertionError();
    }

    @Shadow
    public Entity getCameraEntity() {
        throw new AssertionError();
    }

    // TODO refactor miningCooldown and itemUseCooldown to playerEntity variables
    protected int miningCooldown;

    /**
     * @author TheRedBrain
     */
    @Overwrite//(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick() {
//        MinecraftXTerraria.LOGGER.info("client tick start");
//
//        if (this.player != null && this.player.world != null && this.server != null) {
//            MinecraftXTerraria.LOGGER.info("test_1");
//        }
//        if (this.world != null) {
//            MinecraftXTerraria.LOGGER.info("test_4");
//            if (this.world.getServer() != null) {
//                MinecraftXTerraria.LOGGER.info("test_5");
//            }
//        }
//        if (this.server != null) {
//            MinecraftXTerraria.LOGGER.info("test_6");
//        }
/*
        if (this.server != null) {
            if (this.player != null) {
                MinecraftXTerraria.LOGGER.info("test_1");
                WorldKeys worldKeys = WorldKeys.getWorldKeys(this.server);
                PlayerKeys playerKeys = WorldKeys.getPlayerKeys(this.player);
                if (!playerKeys.characterCreated && this.currentScreen == null) {
                    this.setScreen(new MxtCharacterCreationScreen());
                }
            }
        }
        if (this.player != null && this.player.world != null && this.player.world.getServer() != null) {
            MinecraftXTerraria.LOGGER.info("test_2");
            PlayerKeys playerKeys = WorldKeys.getPlayerKeys(this.player);
            if (!playerKeys.characterCreated && this.currentScreen == null) {
                this.setScreen(new MxtCharacterCreationScreen());
            }
        }*/
        if (this.itemUseCooldown > 0) {
            --this.itemUseCooldown;
        }
        if (this.miningCooldown > 0) {
            --this.miningCooldown;
        }
        this.profiler.push("gui");
        this.messageHandler.processDelayedMessages();
        this.inGameHud.tick(this.paused);
        this.profiler.pop();
        this.gameRenderer.updateTargetedEntity(1.0f);
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

            if (!((DuckPlayerEntityMixin)this.player).mxtGetCharacterCreated()) {
                this.setScreen(new MxtCharacterCreationScreen());
            }

            if (this.player.isDead() && !(this.currentScreen instanceof DeathScreen)) {
                this.setScreen(null);
            } else if (this.player.isSleeping() && this.world != null) {
                this.setScreen(new SleepingChatScreen());
            }
        } else {
            Screen screen = this.currentScreen;
            if (screen instanceof SleepingChatScreen) {
                SleepingChatScreen sleepingChatScreen = (SleepingChatScreen)screen;
                if (!this.player.isSleeping()) {
                    sleepingChatScreen.closeChatIfEmpty();
                }
            }
        }
//        if (this.currentScreen != null) {
//            this.attackCooldown = 10000;
//        }
        if (this.currentScreen != null) {
            Screen.wrapScreenError(() -> this.currentScreen.tick(), "Ticking screen", this.currentScreen.getClass().getCanonicalName());
        }
        if (!this.options.debugEnabled) {
            this.inGameHud.resetDebugHudChunk();
        }
        if (this.overlay == null && (this.currentScreen == null || this.currentScreen.passEvents)) {
            this.profiler.swap("Keybindings");
            this.mxtHandleInputEvents();
//            if (this.attackCooldown > 0) {
//                --this.attackCooldown;
//            }
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
                    MutableText text = Text.translatable("tutorial.socialInteractions.title");
                    MutableText text2 = Text.translatable("tutorial.socialInteractions.description", TutorialManager.keyToText("socialInteractions"));
                    this.socialInteractionsToast = new TutorialToast(TutorialToast.Type.SOCIAL_INTERACTIONS, text, text2, true);
                    this.tutorialManager.add(this.socialInteractionsToast, 160);
                    this.options.joinedFirstServer = true;
                    this.options.write();
                }
                this.tutorialManager.tick();
                try {
                    this.world.tick(() -> true);
                }
                catch (Throwable throwable) {
                    CrashReport crashReport = CrashReport.create(throwable, "Exception in world tick");
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
//        MinecraftXTerraria.LOGGER.info("client tick end");
//        ci.cancel();
    }

//    @Inject(method = "handleInputEvents", at = @At("HEAD"), cancellable = true)
    private void mxtHandleInputEvents() {
        while (this.options.togglePerspectiveKey.wasPressed()) {
            Perspective perspective = this.options.getPerspective();
            this.options.setPerspective(this.options.getPerspective().next());
            if (perspective.isFirstPerson() != this.options.getPerspective().isFirstPerson()) {
                this.gameRenderer.onCameraEntitySet(this.options.getPerspective().isFirstPerson() ? this.getCameraEntity() : null);
            }
            this.worldRenderer.scheduleTerrainUpdate();
        }
        while (this.options.smoothCameraKey.wasPressed()) {
            this.options.smoothCameraEnabled = !this.options.smoothCameraEnabled;
        }
        for (int i = 0; i < 10; ++i) {
            boolean bl = this.options.saveToolbarActivatorKey.isPressed();
            boolean bl2 = this.options.loadToolbarActivatorKey.isPressed();
            if (!this.options.hotbarKeys[i].wasPressed()) continue;
            if (this.player.isSpectator()) {
                this.inGameHud.getSpectatorHud().selectSlot(i);
                continue;
            }
            if (this.player.isCreative() && this.currentScreen == null && (bl2 || bl)) {
                CreativeInventoryScreen.onHotbarKeyPress(((MinecraftClient) (Object) this), i, bl2, bl);
                continue;
            }
            ((DuckPlayerEntityMixin)(this.player)).getMxtPlayerInventory().selectedSlot = i;
        }
        while (this.options.socialInteractionsKey.wasPressed()) {
            if (!this.isConnectedToServer()) {
                this.player.sendMessage(SOCIAL_INTERACTIONS_NOT_AVAILABLE, true);
                this.narratorManager.narrate(SOCIAL_INTERACTIONS_NOT_AVAILABLE);
                continue;
            }
            if (this.socialInteractionsToast != null) {
                this.tutorialManager.remove(this.socialInteractionsToast);
                this.socialInteractionsToast = null;
            }
            this.setScreen(new SocialInteractionsScreen());
        }
        while (this.options.inventoryKey.wasPressed()) {
            if (this.interactionManager.hasRidingInventory()) {
                this.player.openRidingInventory();
                continue;
            }
            this.tutorialManager.onInventoryOpened();
            this.setScreen(new MxtPlayerInventoryScreen(this.player));
        }
        while (this.options.advancementsKey.wasPressed()) {
            this.setScreen(new AdvancementsScreen(this.player.networkHandler.getAdvancementHandler()));
        }
//        while (this.options.swapHandsKey.wasPressed()) {
//            if (this.player.isSpectator()) continue;
//            this.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
//        }
        while (this.options.dropKey.wasPressed()) {
            if (this.player.isSpectator() || !this.player.dropSelectedItem(Screen.hasControlDown())) continue;
            this.player.swingHand(Hand.MAIN_HAND);
        }
        while (this.options.chatKey.wasPressed()) {
            this.openChatScreen("");
        }
        if (this.currentScreen == null && this.overlay == null && this.options.commandKey.wasPressed()) {
            this.openChatScreen("/");
        }
        boolean bl3 = false;
        /*
        if (this.player.isUsingItem()) {
            if (!this.options.useKey.isPressed()) {
                this.interactionManager.stopUsingItem(this.player);
            }
            while (this.options.attackKey.wasPressed()) {
            }
            while (this.options.useKey.wasPressed()) {
            }
            while (this.options.pickItemKey.wasPressed()) {
            }
        } else {
            while (this.options.attackKey.wasPressed()) {
                bl3 |= this.doAttack();
            }
            while (this.options.useKey.wasPressed()) {
                this.doItemUse();
            }
            while (this.options.pickItemKey.wasPressed()) {
                this.doItemPick();
            }
        }*/
        boolean canAutoSwing = this.player.getMainHandStack().getItem() instanceof MxtUsableItem && ((MxtUsableItem) this.player.getMainHandStack().getItem()).getAutoSwing();// || this.player.canAutoSwing() || this.options.globalAutoSwingAllowed()
        if (canAutoSwing) {
            if (this.options.attackKey.isPressed()) {
                bl3 = this.mxtAttack();
//                this.mxtAttack();
            }
        } else {
//            this.doAttack();
            if (this.options.attackKey.wasPressed()) {
                bl3 = this.mxtAttack();
//                this.mxtAttack();
            }
        }
        if (this.options.useKey.wasPressed()/* && this.itemUseCooldown == 0 && !this.player.isUsingItem()*/) {
            this.doItemUse();
        }
        if (this.options.pickItemKey.wasPressed()) {
            this.doItemPick();
        }
        // TODO maybe don't check if attackKey.isPressed()
//        if (!(this.mouse.isCursorLocked())/* && (this.player.getMainHandStack().getItem() instanceof MxtToolItem || this.player.getMainHandStack().getItem() instanceof MxTBlockItem)*/) {
//            MinecraftXTerraria.LOGGER.info("--------------!(this.mouse.isCursorLocked())-------------");
//        }
//        if (!(this.options.attackKey.isPressed())/* && (this.player.getMainHandStack().getItem() instanceof MxtToolItem || this.player.getMainHandStack().getItem() instanceof MxTBlockItem)*/) {
//            MinecraftXTerraria.LOGGER.info("--------------!(this.options.attackKey.isPressed())-------------");
//        }
//        if (bl3/* && (this.player.getMainHandStack().getItem() instanceof MxtToolItem || this.player.getMainHandStack().getItem() instanceof MxTBlockItem)*/) {
//            MinecraftXTerraria.LOGGER.info("--------------bl3-------------");
//        }
//        if (!(this.currentScreen == null)/* && (this.player.getMainHandStack().getItem() instanceof MxtToolItem || this.player.getMainHandStack().getItem() instanceof MxTBlockItem)*/) {
//            MinecraftXTerraria.LOGGER.info("--------------!(this.currentScreen == null)-------------");
//        }
        this.mxtBlockBreaking(this.currentScreen == null && !bl3 && this.options.attackKey.isPressed() && this.mouse.isCursorLocked()/* && (this.player.getMainHandStack().getItem() instanceof MxtToolItem || this.player.getMainHandStack().getItem() instanceof MxTBlockItem)*/);
    }

    public final void mxtBlockBreaking(boolean breaking) {
        if (breaking && this.crosshairTarget != null && this.crosshairTarget.getType() == HitResult.Type.BLOCK) {
//            MinecraftXTerraria.LOGGER.info("mxtBlockBreaking");
            BlockHitResult blockHitResult = (BlockHitResult)this.crosshairTarget;
            BlockPos blockPos = blockHitResult.getBlockPos();
            if ((this.player.getMainHandStack().getItem() instanceof MxTBlockItem && !(this.itemUseCooldown > 0)) || (this.player.getMainHandStack().getItem() instanceof MxtToolItem && !(this.miningCooldown > 0))) {
                // TODO MAYBE make separate checks for mining, axing and hammering
//                MinecraftXTerraria.LOGGER.info("mxtBlockBreaking before if, currentBreakingProgress: " + ((DuckClientPlayerInteractionManagerMixin)this.interactionManager).getCurrentBreakingProgress());
                if (this.world.getBlockState(blockPos).getBlock() instanceof AbstractMxTBlock && ((DuckClientPlayerInteractionManagerMixin)this.interactionManager).mxtUpdateBlockBreakingProgress(blockPos, blockHitResult)) {
                    this.particleManager.addBlockBreakingParticles(blockPos, blockHitResult.getSide());
//                    this.player.swingHand(Hand.MAIN_HAND);
                    this.itemUseCooldown = ((MxtUsableItem) this.player.getMainHandStack().getItem()).getUseTime();
                    if (this.player.getMainHandStack().getItem() instanceof MxtToolItem) {
                        this.miningCooldown = ((MxtToolItem)this.player.getMainHandStack().getItem()).getMiningSpeed();
                    }
                }
//                MinecraftXTerraria.LOGGER.info("mxtBlockBreaking after if, currentBreakingProgress: " + ((DuckClientPlayerInteractionManagerMixin)this.interactionManager).getCurrentBreakingProgress());
            }
            return;
        }
//        MinecraftXTerraria.LOGGER.info("-----------------CANCEL BREAKING---------------------");
        ((DuckClientPlayerInteractionManagerMixin)this.interactionManager).mxtCancelBlockBreaking();
    }

    // called on left-click
    // uses the currently held item
    // attacking with weapons and block breaking with tools
    // placing blocks when targeting another block
    // using of other items like consumables
    public final boolean mxtAttack() {
        ItemStack itemStack = this.player.getStackInHand(Hand.MAIN_HAND);

        boolean usedItem = false;
        boolean minedBlock = false;
        // minecraft internal
        if (this.crosshairTarget == null) {
            LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");

            return false;
        } else if (this.player.isRiding()) {
            return false;
        } else if (itemStack.isEmpty()) {
            MinecraftXTerraria.LOGGER.info("You need an item to attack");
            return false;
        }

        // should not happen as all vanilla items are disabled
        else if (!(itemStack.getItem() instanceof MxtItem)) {
            MinecraftXTerraria.LOGGER.info("Non MxT item used, this item should be disabled");
            return false;
        } else {

//            MinecraftXTerraria.LOGGER.info("itemUseCooldown: " + this.itemUseCooldown);
//            MinecraftXTerraria.LOGGER.info("miningCooldown: " + this.miningCooldown);

            // this is the useTime determined by the last used item
            if (!(this.itemUseCooldown > 0)) {
//                MinecraftXTerraria.LOGGER.info("itemUseCooldown <= 0");
                if (itemStack.getItem() instanceof MxtMeleeWeaponItem) {
//                    MinecraftXTerraria.LOGGER.info("attack");
                    // every item which has a melee attack (melee weapons and tools)
                    // TODO get useTime modifiers
                    this.itemUseCooldown = ((MxtUsableItem) itemStack.getItem()).getUseTime();
                    usedItem = true;

//                     startAttackAnimation(itemStack);

                } else if (itemStack.getItem() instanceof MxtRangedWeaponItem) {
                    // every item which has a ranged attack (ammo consuming and magic)

                    // TODO get useTime modifiers
                    this.itemUseCooldown = ((MxtUsableItem) itemStack.getItem()).getUseTime();
                    usedItem = true;

                } else if (itemStack.getItem() instanceof MxtUsableItem) {
                    // every other usable item (eg consumables, items like magic mirror etc)
                    if (((MxtUsableItem) itemStack.getItem()).mxtCanBeUsed(this.world, this.player)) {
//                        this.interactionManager.interactItem(this.player, Hand.MAIN_HAND);
//                        ((MxtUsableItem) itemStack.getItem()).mxtUse(this.world, this.player);
//                    // TODO get useTime modifiers
                        this.itemUseCooldown = ((MxtUsableItem) itemStack.getItem()).getUseTime();
                        usedItem = true;
                    }
                }
            }

            if (!(this.miningCooldown > 0)) {
//                MinecraftXTerraria.LOGGER.info("miningCooldown <= 0");
                if (itemStack.getItem() instanceof MxtToolItem && this.crosshairTarget.getType() == HitResult.Type.BLOCK) {
//                BlockHitResult blockHitResult = (BlockHitResult) this.crosshairTarget;
//                BlockPos blockPos = blockHitResult.getBlockPos();
//                if (!this.world.getBlockState(blockPos).isAir() && ((DuckClientPlayerInteractionManagerMixin)this.interactionManager).mxTAttackBlock(blockPos, blockHitResult)) {
//                    this.attackCooldown = ((MxtToolItem) itemStack.getItem()).getMiningSpeed();
//                    minedBlock = true;
//                }


//                if (this.attackCooldown > 0 || this.player.isUsingItem()) {
//                    return;
//                }
//                if (breaking && this.crosshairTarget != null && this.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                    Direction direction;
                    BlockHitResult blockHitResult = (BlockHitResult) this.crosshairTarget;
                    BlockPos blockPos = blockHitResult.getBlockPos();
                    if (this.world.getBlockState(blockPos).getBlock() instanceof AbstractMxTBlock && ((DuckClientPlayerInteractionManagerMixin)this.interactionManager).mxtUpdateBlockBreakingProgress(blockPos, blockHitResult)) {
//                    if (this.world.getBlockState(blockPos).getBlock() instanceof AbstractMxTBlock && ((DuckClientPlayerInteractionManagerMixin)this.interactionManager).mxtAttackBlock(blockPos, blockHitResult)) {
                        this.particleManager.addBlockBreakingParticles(blockPos, blockHitResult.getSide());
//                        this.player.swingHand(Hand.MAIN_HAND);
                        this.miningCooldown = ((MxtToolItem) itemStack.getItem()).getMiningSpeed();
                        minedBlock = true;
//                        MinecraftXTerraria.LOGGER.info("minedBlock = true");
                    } else {
//                    this.interactionManager.cancelBlockBreaking();
                        this.miningCooldown = 0;
                        minedBlock = false;
                        MinecraftXTerraria.LOGGER.info("minedBlock = false");
                    }
                } else {
//                    this.interactionManager.cancelBlockBreaking();
                    this.miningCooldown = 0;
                    minedBlock = false;
                }
            } else {
//                MinecraftXTerraria.LOGGER.info("attackCooldown > 0");
            }
        }
        // TODO RESEARCH what does it do?
//        MinecraftXTerraria.LOGGER.info("before resetLastAttackedTicks currentBreakingProgress :" + ((DuckClientPlayerInteractionManagerMixin)this.interactionManager).getCurrentBreakingProgress());
        this.player.resetLastAttackedTicks();
//        MinecraftXTerraria.LOGGER.info("after resetLastAttackedTicks currentBreakingProgress :" + ((DuckClientPlayerInteractionManagerMixin)this.interactionManager).getCurrentBreakingProgress());
//        return usedItem || minedBlock;
//            return false;
         return usedItem || minedBlock;
    }
}
