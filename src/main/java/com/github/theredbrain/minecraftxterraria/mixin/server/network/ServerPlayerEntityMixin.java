package com.github.theredbrain.minecraftxterraria.mixin.server.network;

import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.minecraftxterraria.entity.player.MxtPlayerInventory;
import com.github.theredbrain.minecraftxterraria.network.packet.s2c.play.MxtHealthUpdateS2CPacket;
import com.github.theredbrain.minecraftxterraria.screen.MxtScreenHandlerFactory;
import com.mojang.authlib.GameProfile;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.NetworkSyncedItem;
import net.minecraft.network.Packet;
import net.minecraft.network.encryption.PublicPlayerSession;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.screen.*;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.OptionalInt;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    @Shadow
    public ServerPlayNetworkHandler networkHandler;

    @Shadow
    @Final
    public ServerPlayerInteractionManager interactionManager;

    @Shadow
    @Final
    private PlayerAdvancementTracker advancementTracker;

    @Shadow
    private float lastHealthScore;

    @Shadow
    private int lastAirScore;

    @Shadow
    private int lastArmorScore;

    @Shadow
    private float syncedHealth;

    @Shadow
    private int syncedFoodLevel;

    @Shadow
    private int syncedExperience;

    @Shadow
    private int joinInvulnerabilityTicks;

    @Shadow
    public boolean seenCredits;

    @Shadow
    private Vec3d levitationStartPos;

    @Shadow
    private int levitationStartTick;

    @Shadow
    public boolean filterText;

//    @Shadow
//    private SculkShriekerWarningManager sculkShriekerWarningManager;



    @Shadow
    public PublicPlayerSession session;

    @Shadow
    private int screenHandlerSyncId;

    @Shadow
    private void onScreenHandlerOpened(ScreenHandler screenHandler) { // 408
        throw new AssertionError();
    }

    @Shadow
    public void tickFallStartPos() { // 539
        throw new AssertionError();
    }

    @Shadow
    private void updateScores(ScoreboardCriterion criterion, int score2) { // 558
        throw new AssertionError();
    }

    @Shadow
    public void tickVehicleInLavaRiding() { // 545
        throw new AssertionError();
    }

    @Shadow
    private void incrementScreenHandlerSyncId() { // 932
        throw new AssertionError();
    }

    @Shadow
    public ServerWorld getWorld() { // 1193
        throw new AssertionError();
    }

    @Shadow
    public Entity getCameraEntity() { // 1331
        throw new AssertionError();
    }

    @Shadow
    public void setCameraEntity(@Nullable Entity entity) { // 1335
        throw new AssertionError();
    }

    private float syncedHealthRegenerationTime;
    private float syncedHealthEffectiveRegenerationTime;
    private float syncedHealthRegenerationCounter;
    private float syncedMana;
    private float syncedManaRegenerationDelay;
    private float syncedManaRegenerationCounter;
    private float lastManaScore;

    // TODO playerKeys

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void onSpawn() {
        this.onScreenHandlerOpened(((DuckPlayerEntityMixin)this).getMxtPlayerInventoryScreenHandler());
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void tick() {
        Entity entity;
        this.interactionManager.update();
//        this.sculkShriekerWarningManager.tick();
        --this.joinInvulnerabilityTicks;
        if (this.timeUntilRegen > 0) {
            --this.timeUntilRegen;
        }
        this.currentScreenHandler.sendContentUpdates();
        if (!this.world.isClient && !this.currentScreenHandler.canUse(this)) {
            this.closeHandledScreen();
            this.currentScreenHandler = ((DuckPlayerEntityMixin)this).getMxtPlayerInventoryScreenHandler();
        }
        if ((entity = this.getCameraEntity()) != this) {
            if (entity.isAlive()) {
                this.updatePositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), entity.getYaw(), entity.getPitch());
                this.getWorld().getChunkManager().updatePosition((ServerPlayerEntity) (Object) this);
                if (this.shouldDismount()) {
                    this.setCameraEntity(this);
                }
            } else {
                this.setCameraEntity(this);
            }
        }
        Criteria.TICK.trigger((ServerPlayerEntity) (Object) this);
        if (this.levitationStartPos != null) {
            Criteria.LEVITATION.trigger((ServerPlayerEntity) (Object) this, this.levitationStartPos, this.age - this.levitationStartTick);
        }
        this.tickFallStartPos();
        this.tickVehicleInLavaRiding();
        this.advancementTracker.sendUpdate((ServerPlayerEntity) (Object) this);
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void playerTick() {
        try {
            if (!this.isRegionUnloaded()) {
                super.tick();
            }
            for (int i = 0; i < ((DuckPlayerEntityMixin)this).getMxtPlayerInventory().size(); ++i) {
                Packet<?> packet;
                ItemStack itemStack = ((DuckPlayerEntityMixin)this).getMxtPlayerInventory().getStack(i);
                if (!itemStack.getItem().isNetworkSynced() || (packet = ((NetworkSyncedItem)itemStack.getItem()).createSyncPacket(itemStack, this.world, this)) == null) continue;
                this.networkHandler.sendPacket(packet);
            }
            if (this.getHealth() != this.syncedHealth ||
                    ((DuckPlayerEntityMixin)this).mxtGetHealthRegenerationTime() != this.syncedHealthRegenerationTime ||
                    ((DuckPlayerEntityMixin)this).mxtGetEffectiveHealthRegenerationTime() != this.syncedHealthEffectiveRegenerationTime ||
                    ((DuckPlayerEntityMixin)this).mxtGetHealthRegenerationCounter() != this.syncedHealthRegenerationCounter) {
                this.networkHandler.sendPacket(new MxtHealthUpdateS2CPacket(this.getHealth(), ((DuckPlayerEntityMixin)this).mxtGetHealthRegenerationTime(), ((DuckPlayerEntityMixin)this).mxtGetEffectiveHealthRegenerationTime(), ((DuckPlayerEntityMixin)this).mxtGetHealthRegenerationCounter()));
                this.syncedHealth = this.getHealth();
                this.syncedHealthRegenerationTime = ((DuckPlayerEntityMixin)this).mxtGetHealthRegenerationTime();
                this.syncedHealthEffectiveRegenerationTime = ((DuckPlayerEntityMixin)this).mxtGetEffectiveHealthRegenerationTime();
                this.syncedHealthRegenerationCounter = ((DuckPlayerEntityMixin)this).mxtGetHealthRegenerationCounter();
            }
            if (((DuckPlayerEntityMixin)this).mxtGetMana() != this.syncedMana ||
                    ((DuckPlayerEntityMixin)this).mxtGetMana() != this.syncedManaRegenerationDelay ||
                    ((DuckPlayerEntityMixin)this).mxtGetMana() != this.syncedManaRegenerationCounter) {
                this.syncedMana = ((DuckPlayerEntityMixin)this).mxtGetMana();
                this.syncedManaRegenerationDelay = ((DuckPlayerEntityMixin)this).mxtGetManaRegenerationDelay();
                this.syncedManaRegenerationCounter = ((DuckPlayerEntityMixin)this).mxtGetManaRegenerationCounter();
            }
            if (this.getHealth() != this.lastHealthScore) {
                this.lastHealthScore = this.getHealth();
                this.updateScores(ScoreboardCriterion.HEALTH, MathHelper.ceil(this.lastHealthScore));
            }
            if (((DuckPlayerEntityMixin)this).mxtGetMana() != this.lastManaScore) {
                this.lastManaScore = ((DuckPlayerEntityMixin)this).mxtGetMana();
                // TODO scoreboardCriterion mana
//                this.updateScores(ScoreboardCriterion.HEALTH, MathHelper.ceil(this.lastManaScore));
            }
            // TODO breath and breathCD
            if (this.getAir() != this.lastAirScore) {
                this.lastAirScore = this.getAir();
                this.updateScores(ScoreboardCriterion.AIR, MathHelper.ceil(this.lastAirScore));
            }
//            if (this.getArmor() != this.lastArmorScore) {
//                this.lastArmorScore = this.getArmor();
//                this.updateScores(ScoreboardCriterion.ARMOR, MathHelper.ceil(this.lastArmorScore));
//            }
//            if (this.totalExperience != this.lastExperienceScore) {
//                this.lastExperienceScore = this.totalExperience;
//                this.updateScores(ScoreboardCriterion.XP, MathHelper.ceil(this.lastExperienceScore));
//            }
//            if (this.experienceLevel != this.lastLevelScore) {
//                this.lastLevelScore = this.experienceLevel;
//                this.updateScores(ScoreboardCriterion.LEVEL, MathHelper.ceil(this.lastLevelScore));
//            }
//            if (this.totalExperience != this.syncedExperience) {
//                this.syncedExperience = this.totalExperience;
//                this.networkHandler.sendPacket(new ExperienceBarUpdateS2CPacket(this.experienceProgress, this.totalExperience, this.experienceLevel));
//            }
            if (this.age % 20 == 0) {
                Criteria.LOCATION.trigger((ServerPlayerEntity) (Object) this);
            }
        }
        catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.create(throwable, "Ticking player");
            CrashReportSection crashReportSection = crashReport.addElement("Player being ticked");
            this.populateCrashReport(crashReportSection);
            throw new CrashException(crashReport);
        }
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public OptionalInt openHandledScreen(@Nullable NamedScreenHandlerFactory factory) {
        if (factory == null) {
            return OptionalInt.empty();
        }
        if (this.currentScreenHandler != ((DuckPlayerEntityMixin)this).getMxtPlayerInventoryScreenHandler()) {
            this.closeHandledScreen();
        }
        this.incrementScreenHandlerSyncId();
        ScreenHandler screenHandler = ((MxtScreenHandlerFactory)factory).mxtCreateMenu(this.screenHandlerSyncId, ((DuckPlayerEntityMixin)this).getMxtPlayerInventory(), this);
        if (screenHandler == null) {
            if (this.isSpectator()) {
                this.sendMessage(Text.translatable("container.spectatorCantOpen").formatted(Formatting.RED), true);
            }
            return OptionalInt.empty();
        }
        this.networkHandler.sendPacket(new OpenScreenS2CPacket(screenHandler.syncId, screenHandler.getType(), factory.getDisplayName()));
        this.onScreenHandlerOpened(screenHandler);
        this.currentScreenHandler = screenHandler;
        return OptionalInt.of(this.screenHandlerSyncId);
    }

//    /**
//     * @author TheRedBrain
//     */
//    @Overwrite
//    public void openHorseInventory(AbstractHorseEntity horse, Inventory inventory) {
//        if (this.currentScreenHandler != ((DuckPlayerEntityMixin)this).getMxtPlayerScreenHandler()) {
//            this.closeHandledScreen();
//        }
//        this.incrementScreenHandlerSyncId();
//        this.networkHandler.sendPacket(new OpenHorseScreenS2CPacket(this.screenHandlerSyncId, inventory.size(), horse.getId()));
//        this.currentScreenHandler = new HorseScreenHandler(this.screenHandlerSyncId, ((DuckPlayerEntityMixin)this).getMxtPlayerInventory(), inventory, horse);
//        this.onScreenHandlerOpened(this.currentScreenHandler);
//    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void closeScreenHandler() {
        this.currentScreenHandler.close(this);
        ((DuckPlayerEntityMixin)this).getMxtPlayerInventoryScreenHandler().copySharedSlots(this.currentScreenHandler);
        this.currentScreenHandler = ((DuckPlayerEntityMixin)this).getMxtPlayerInventoryScreenHandler();
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive) {
//        this.sculkShriekerWarningManager = oldPlayer.sculkShriekerWarningManager;
        this.filterText = oldPlayer.filterText;
        this.session = oldPlayer.session;
        // TODO
//        this.interactionManager.setGameMode(oldPlayer.interactionManager.getGameMode(), oldPlayer.interactionManager.getPreviousGameMode());
        this.sendAbilitiesUpdate();
        if (alive) {
            ((DuckPlayerEntityMixin)this).getMxtPlayerInventory().clone(((DuckPlayerEntityMixin)oldPlayer).getMxtPlayerInventory());
            this.setHealth(oldPlayer.getHealth());
            ((DuckPlayerEntityMixin)this).mxtSetHealthRegenerationTime(((DuckPlayerEntityMixin)oldPlayer).mxtGetHealthRegenerationTime());
            ((DuckPlayerEntityMixin)this).mxtSetEffectiveHealthRegenerationTime(((DuckPlayerEntityMixin)oldPlayer).mxtGetEffectiveHealthRegenerationTime());
            ((DuckPlayerEntityMixin)this).mxtSetHealthRegenerationCounter(((DuckPlayerEntityMixin)oldPlayer).mxtGetHealthRegenerationCounter());
            ((DuckPlayerEntityMixin)this).mxtSetMana(((DuckPlayerEntityMixin)oldPlayer).mxtGetMana());
            ((DuckPlayerEntityMixin)this).mxtSetManaRegenerationDelay(((DuckPlayerEntityMixin)oldPlayer).mxtGetManaRegenerationDelay());
            ((DuckPlayerEntityMixin)this).mxtSetManaRegenerationCounter(((DuckPlayerEntityMixin)oldPlayer).mxtGetManaRegenerationCounter());
//            this.hungerManager = oldPlayer.hungerManager;
//            this.experienceLevel = oldPlayer.experienceLevel;
//            this.totalExperience = oldPlayer.totalExperience;
//            this.experienceProgress = oldPlayer.experienceProgress;
//            this.setScore(oldPlayer.getScore());
//            this.lastNetherPortalPosition = oldPlayer.lastNetherPortalPosition;
        } else if (this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) || oldPlayer.isSpectator()) { // TODO gameMode
            this.getInventory().clone(oldPlayer.getInventory());
//            this.experienceLevel = oldPlayer.experienceLevel;
//            this.totalExperience = oldPlayer.totalExperience;
//            this.experienceProgress = oldPlayer.experienceProgress;
//            this.setScore(oldPlayer.getScore());
        }
//        this.enchantmentTableSeed = oldPlayer.enchantmentTableSeed;
//        this.enderChestInventory = oldPlayer.enderChestInventory;
        this.getDataTracker().set(PLAYER_MODEL_PARTS, (Byte)oldPlayer.getDataTracker().get(PLAYER_MODEL_PARTS));
        // TODO playerKeys
//        this.syncedExperience = -1;
        this.syncedHealth = -1.0f;
        this.syncedHealthRegenerationTime = -1.0f;
        this.syncedHealthEffectiveRegenerationTime = -1.0f;
        this.syncedHealthRegenerationCounter = -1.0f;
        this.syncedMana = -1.0f;
        this.syncedManaRegenerationDelay = -1.0f;
        this.syncedManaRegenerationCounter = -1.0f;
//        this.syncedFoodLevel = -1;
//        this.recipeBook.copyFrom(oldPlayer.recipeBook);
        this.seenCredits = oldPlayer.seenCredits;
//        this.enteredNetherPos = oldPlayer.enteredNetherPos;
//        this.setShoulderEntityLeft(oldPlayer.getShoulderEntityLeft());
//        this.setShoulderEntityRight(oldPlayer.getShoulderEntityRight());
        this.setLastDeathPos(oldPlayer.getLastDeathPos());
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public boolean dropSelectedItem(boolean entireStack) {
        MxtPlayerInventory playerInventory = ((DuckPlayerEntityMixin)this).getMxtPlayerInventory();
        ItemStack itemStack = playerInventory.dropSelectedItem(entireStack);
        this.currentScreenHandler.getSlotIndex(playerInventory, playerInventory.selectedSlot).ifPresent(index -> this.currentScreenHandler.setPreviousTrackedSlot(index, playerInventory.getMainHandStack()));
        return this.dropItem(itemStack, false, true) != null;
    }
}
