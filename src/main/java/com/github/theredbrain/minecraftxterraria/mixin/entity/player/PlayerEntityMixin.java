package com.github.theredbrain.minecraftxterraria.mixin.entity.player;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
//import com.github.theredbrain.minecraftxterraria.client.gui.screen.ingame.MxtEquipmentInventoryScreen;
//import com.github.theredbrain.minecraftxterraria.client.gui.screen.ingame.MxtFirstLoadoutInventoryScreen;
//import com.github.theredbrain.minecraftxterraria.client.gui.screen.ingame.MxtSecondLoadoutInventoryScreen;
//import com.github.theredbrain.minecraftxterraria.client.gui.screen.ingame.MxtThirdLoadoutInventoryScreen;
import com.github.theredbrain.minecraftxterraria.entity.MxtEquipmentSlot;
import com.github.theredbrain.minecraftxterraria.entity.MxtLivingEntity;
import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.minecraftxterraria.entity.player.MxtGearPlayerInventory;
import com.github.theredbrain.minecraftxterraria.entity.player.MxtPlayerInventory;
import com.github.theredbrain.minecraftxterraria.item.MxtMagicWeaponItem;
import com.github.theredbrain.minecraftxterraria.item.MxtMeleeWeaponItem;
import com.github.theredbrain.minecraftxterraria.item.MxtRangedWeaponItem;
import com.github.theredbrain.minecraftxterraria.item.MxtSummonWeaponItem;
import com.github.theredbrain.minecraftxterraria.registry.EntityAttributesRegistry;
import com.github.theredbrain.minecraftxterraria.registry.ItemsRegistry;
import com.github.theredbrain.minecraftxterraria.registry.StatusEffectRegistry;
import com.github.theredbrain.minecraftxterraria.screen.*;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import net.minecraft.SharedConstants;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.*;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import virtuoel.pehkui.util.ScaleUtils;

import java.util.List;
import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements DuckPlayerEntityMixin {

    @Shadow
    @Final
    private static Logger LOGGER;

//    @Shadow
//    @Final
//    @Mutable
//    public static float DEFAULT_EYE_HEIGHT = 2.59f;
//
//    @Shadow
//    @Final
//    @Mutable
//    public static EntityDimensions STANDING_DIMENSIONS = EntityDimensions.changing(0.96f, 2.88f);

//    @Shadow
//    @Final
//    @Mutable
//    private static Map<EntityPose, EntityDimensions> POSE_DIMENSIONS = ImmutableMap.builder().put(EntityPose.STANDING, STANDING_DIMENSIONS).put(EntityPose.SLEEPING, SLEEPING_DIMENSIONS).build();

    @Shadow public ScreenHandler currentScreenHandler;

    @Shadow
    protected HungerManager hungerManager;

    @Shadow
    protected int abilityResyncCountdown;

    @Shadow
    private int sleepTimer;

    @Shadow
    @Final
    private PlayerAbilities abilities;

    @Shadow
    @Final
    private GameProfile gameProfile;

    @Shadow
    private ItemStack selectedItem;

    @Shadow
    @Final
    private ItemCooldownManager itemCooldownManager;

    @Shadow
    @Final
    protected static TrackedData<Byte> PLAYER_MODEL_PARTS;

    @Shadow
    @Final
    protected static TrackedData<Byte> MAIN_ARM;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    protected boolean updateWaterSubmersionState() {
        throw new AssertionError();
    }

    @Shadow
    protected void closeScreenHandler() {
        throw new AssertionError();
    }

    @Shadow
    private void collideWithEntity(Entity entity) {
        throw new AssertionError();
    }

    @Shadow
    public void incrementStat(Identifier stat) {
        throw new AssertionError();
    }

    @Shadow
    public void increaseStat(Identifier stat, int amount) {
        throw new AssertionError();
    }
//    @Shadow
//    protected void updatePose() {
//        throw new AssertionError();
//    }

    @Shadow
    public void resetLastAttackedTicks() {
        throw new AssertionError();
    }

    @Shadow
    public void wakeUp(boolean skipSleepTimer, boolean updateSleepingPlayers) {
        throw new AssertionError();
    }

    @Shadow
    public Optional<GlobalPos> getLastDeathPos() {
        throw new AssertionError();
    }

    @Shadow
    public void setLastDeathPos(Optional<GlobalPos> lastDeathPos) {
        throw new AssertionError();
    }

    public EntityDimensions MXT_STANDING_DIMENSIONS = EntityDimensions.changing(0.96f, 2.88f);

    public EntityDimensions MXT_SLEEPING_DIMENSIONS = EntityDimensions.fixed(0.2f, 0.2f);

    private int activeLoadout;
    private int activeInventoryTab;

//    private MxtEquipmentPlayerScreenHandler mxtEquipmentPlayerScreenHandler;
//    private MxtFirstLoadoutPlayerScreenHandler mxtFirstLoadoutPlayerScreenHandler;
//    private MxtSecondLoadoutPlayerScreenHandler mxtSecondLoadoutPlayerScreenHandler;
//    private MxtThirdLoadoutPlayerScreenHandler mxtThirdLoadoutPlayerScreenHandler;
    private MxtPlayerInventoryScreenHandler mxtPlayerInventoryScreenHandler;

    private final MxtPlayerInventory mxtPlayerInventory = new MxtPlayerInventory((PlayerEntity) (Object) this);
//    private final MxtGearPlayerInventory mxtGearPlayerInventory = new MxtGearPlayerInventory((PlayerEntity) (Object) this);

    private static final float MIN_HEALTH_REGENERATION_RATE = -1000; // TODO
    private static final float MAX_HEALTH_REGENERATION_RATE = 1000; // TODO
    private static final float MIN_HEALTH_REGENERATION_COUNTER = -1000;
    private static final float MAX_HEALTH_REGENERATION_COUNTER = 1000;
    private static final float MIN_MANA_REGENERATION_COUNTER = -1000;
    private static final float MAX_MANA_REGENERATION_COUNTER = 1000;

    private static final TrackedData<Optional<BlockPos>> MXT_SLEEPING_POSITION = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_POS);

    private static final TrackedData<Float> MXT_HEALTH = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> MXT_HEALTH_REGENERATION_TIME = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> MXT_EFFECTIVE_HEALTH_REGENERATION_TIME = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> MXT_HEALTH_REGENERATION_COUNTER = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Float> MXT_MANA = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> MXT_MANA_REGENERATION_DELAY = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> MXT_MANA_REGENERATION_COUNTER = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Float> MXT_BREATH = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> MXT_BREATH_COUNTER = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Boolean> MXT_AEGIS_FRUIT_CONSUMED = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> MXT_AMBROSIA_CONSUMED = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> MXT_ARCANE_CRYSTAL_CONSUMED = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> MXT_ARTISAN_LOAF_CONSUMED = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> MXT_DEMON_HEART_CONSUMED = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> MXT_GALAXY_PEARL_CONSUMED = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> MXT_GUMMY_WORM_CONSUMED = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> MXT_MINECART_UPGRADE_CONSUMED = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> MXT_TORCH_GOD_FAVOR_CONSUMED = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> MXT_VITAL_CRYSTAL_CONSUMED = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> MXT_CHARACTER_CREATED = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final TrackedData<Integer> MXT_LIFE_CRYSTALS_CONSUMED = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> MXT_LIFE_FRUITS_CONSUMED = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> MXT_MANA_CRYSTALS_CONSUMED = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> COMPLETED_ANGLER_QUESTS = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);

    // TODO temp
    private static final TrackedData<NbtCompound> LEFT_SHOULDER_ENTITY = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
    private static final TrackedData<NbtCompound> RIGHT_SHOULDER_ENTITY = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);

    /**
     * @author TheRedBrain
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    public void PlayerEntity(World world, BlockPos pos, float yaw, GameProfile gameProfile, CallbackInfo ci) {
//        this.mxtEquipmentPlayerScreenHandler = new MxtEquipmentPlayerScreenHandler(this.mxtPlayerInventory, !world.isClient, (PlayerEntity) (Object) this);
//        this.mxtFirstLoadoutPlayerScreenHandler = new MxtFirstLoadoutPlayerScreenHandler(this.mxtPlayerInventory, !world.isClient, (PlayerEntity) (Object) this);
//        this.mxtSecondLoadoutPlayerScreenHandler = new MxtSecondLoadoutPlayerScreenHandler(this.mxtPlayerInventory, !world.isClient, (PlayerEntity) (Object) this);
//        this.mxtThirdLoadoutPlayerScreenHandler = new MxtThirdLoadoutPlayerScreenHandler(this.mxtPlayerInventory, !world.isClient, (PlayerEntity) (Object) this);
        this.mxtPlayerInventoryScreenHandler = new MxtPlayerInventoryScreenHandler(this.mxtPlayerInventory, !world.isClient, (PlayerEntity) (Object) this);
        this.currentScreenHandler = this.mxtPlayerInventoryScreenHandler;
        this.activeInventoryTab = 0;
        this.activeLoadout = 0;
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public static DefaultAttributeContainer.Builder createPlayerAttributes() {
        return DefaultAttributeContainer.builder()
                // dealing damage
                .add(EntityAttributesRegistry.MXT_KNOCKBACK, 0)
                .add(EntityAttributesRegistry.MXT_MELEE_ATTACK_DAMAGE, 0)
                .add(EntityAttributesRegistry.MXT_RANGED_ATTACK_DAMAGE, 0)
                .add(EntityAttributesRegistry.MXT_MAGIC_ATTACK_DAMAGE, 0)
                .add(EntityAttributesRegistry.MXT_SUMMON_ATTACK_DAMAGE, 0)
                .add(EntityAttributesRegistry.MXT_ARMOUR_PENETRATION, 0)
                .add(EntityAttributesRegistry.MXT_CRIT_CHANCE, 0)
                // taking damage
                .add(EntityAttributesRegistry.MXT_KNOCKBACK_RESISTANCE, 0)
                .add(EntityAttributesRegistry.MXT_DEFENSE, 0)
                .add(EntityAttributesRegistry.MXT_DAMAGE_REDUCTION, 0)
                // pool attribute
                .add(EntityAttributesRegistry.MXT_MAX_HEALTH, 100.0)
                .add(EntityAttributesRegistry.MXT_MAX_EFFECTIVE_HEALTH_REGENERATION_TIME, 9.0)
                .add(EntityAttributesRegistry.MXT_MAX_HEALTH_REGENERATION_TIME, 1200.0)
                .add(EntityAttributesRegistry.MXT_BONUS_HEALTH_REGENERATION_TIME, 0.0)
                .add(EntityAttributesRegistry.MXT_BASE_HEALTH_REGENERATION_RATE_MODIFIER, 1.0)
                .add(EntityAttributesRegistry.MXT_BONUS_HEALTH_REGENERATION_RATE, 0.0)
                .add(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, 0.0)
                .add(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, 0.0)
                .add(EntityAttributesRegistry.MXT_MAX_MANA, 20.0)
                .add(EntityAttributesRegistry.MXT_MANA_USAGE_REDUCTION, 100.0)
//                .add(EntityAttributesRegistry.MXT_MAX_MANA_REGENERATION, 0)
                // attributes
                .add(EntityAttributesRegistry.MXT_LUCK, 0)
                .add(EntityAttributesRegistry.MXT_FISHING_POWER, 0)
                .add(EntityAttributesRegistry.MXT_MINING_SPEED, 0)
                .add(EntityAttributesRegistry.MXT_USE_SPEED, 0)
                // entity interaction ranges
                .add(EntityAttributesRegistry.MXT_COMMON_PICK_UP_RANGE, 0)
                .add(EntityAttributesRegistry.MXT_BOOSTER_PICK_UP_RANGE, 0)
                .add(EntityAttributesRegistry.MXT_MANA_PICK_UP_RANGE, 0)
                .add(EntityAttributesRegistry.MXT_LIFE_PICK_UP_RANGE, 0)
                .add(EntityAttributesRegistry.MXT_ETHERIAN_MANA_PICK_UP_RANGE, 0)
                .add(EntityAttributesRegistry.MXT_COINS_PICK_UP_RANGE, 0)
                .add(EntityAttributesRegistry.MXT_AGGRO_RANGE, 0)
                .add(EntityAttributesRegistry.MXT_NPC_INTERACTION_REACH, 0)
                // block interaction ranges
                .add(EntityAttributesRegistry.MXT_BLOCK_PLACEMENT_REACH, 0)
                .add(EntityAttributesRegistry.MXT_BLOCK_BREAKING_REACH, 0)
                .add(EntityAttributesRegistry.MXT_CRAFTING_REACH, 0)
                .add(EntityAttributesRegistry.MXT_BLOCK_INTERACTION_REACH, 0)
                // movement
                .add(EntityAttributesRegistry.MXT_MAX_ACCELERATION, 0)
                .add(EntityAttributesRegistry.MXT_MAX_DECELERATION, 0)
                .add(EntityAttributesRegistry.MXT_MAX_MOVEMENT_SPEED, 11)
                .add(EntityAttributesRegistry.MXT_MAX_JUMP_SPEED, 5.01)
                .add(EntityAttributesRegistry.MXT_MAX_JUMP_DURATION, 15.0);
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(MXT_HEALTH, 100.0F);
        this.dataTracker.startTracking(MXT_HEALTH_REGENERATION_TIME, 0.0F);
        this.dataTracker.startTracking(MXT_EFFECTIVE_HEALTH_REGENERATION_TIME, 0.0F);
        this.dataTracker.startTracking(MXT_HEALTH_REGENERATION_COUNTER, 0.0F);

        this.dataTracker.startTracking(MXT_MANA, 20.0F);
        this.dataTracker.startTracking(MXT_MANA_REGENERATION_DELAY, 0.0F);
        this.dataTracker.startTracking(MXT_MANA_REGENERATION_COUNTER, 20.0F);

        this.dataTracker.startTracking(MXT_BREATH, 200.0F);
        this.dataTracker.startTracking(MXT_BREATH_COUNTER, 0.0F);

//        this.dataTracker.startTracking(MXT_SLEEPING_POSITION, Optional.empty());
//        this.dataTracker.startTracking(ABSORPTION_AMOUNT, Float.valueOf(0.0f));
//        this.dataTracker.startTracking(SCORE, 0);
        this.dataTracker.startTracking(PLAYER_MODEL_PARTS, (byte)0);
        this.dataTracker.startTracking(MAIN_ARM, (byte)1);
        this.dataTracker.startTracking(LEFT_SHOULDER_ENTITY, new NbtCompound());
        this.dataTracker.startTracking(RIGHT_SHOULDER_ENTITY, new NbtCompound());

        this.dataTracker.startTracking(MXT_AEGIS_FRUIT_CONSUMED, false);
        this.dataTracker.startTracking(MXT_AMBROSIA_CONSUMED, false);
        this.dataTracker.startTracking(MXT_ARCANE_CRYSTAL_CONSUMED, false);
        this.dataTracker.startTracking(MXT_ARTISAN_LOAF_CONSUMED, false);
        this.dataTracker.startTracking(MXT_DEMON_HEART_CONSUMED, false);
        this.dataTracker.startTracking(MXT_GALAXY_PEARL_CONSUMED, false);
        this.dataTracker.startTracking(MXT_GUMMY_WORM_CONSUMED, false);
        this.dataTracker.startTracking(MXT_MINECART_UPGRADE_CONSUMED, false);
        this.dataTracker.startTracking(MXT_TORCH_GOD_FAVOR_CONSUMED, false);
        this.dataTracker.startTracking(MXT_VITAL_CRYSTAL_CONSUMED, false);
        this.dataTracker.startTracking(MXT_CHARACTER_CREATED, false);

        this.dataTracker.startTracking(MXT_LIFE_CRYSTALS_CONSUMED, 0);
        this.dataTracker.startTracking(MXT_LIFE_FRUITS_CONSUMED, 0);
        this.dataTracker.startTracking(MXT_MANA_CRYSTALS_CONSUMED, 0);
        this.dataTracker.startTracking(COMPLETED_ANGLER_QUESTS, 0);
    }

    @Override
    public float getScaleFactor() {
        return 1.8f;
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void tick() {
        this.noClip = this.isSpectator();
        if (this.isSpectator()) {
            this.onGround = false;
        }
//        if (this.experiencePickUpDelay > 0) {
//            --this.experiencePickUpDelay;
//        }
        if (this.isSleeping()) {
            ++this.sleepTimer;
            if (this.sleepTimer > 100) {
                this.sleepTimer = 100;
            }
            if (!this.world.isClient && this.world.isDay()) {
                this.wakeUp(false, true);
            }
        } else if (this.sleepTimer > 0) {
            ++this.sleepTimer;
            if (this.sleepTimer >= 110) {
                this.sleepTimer = 0;
            }
        }
        this.updateWaterSubmersionState();
        super.tick();
        if (!this.world.isClient && this.currentScreenHandler != null && !this.currentScreenHandler.canUse((PlayerEntity) (Object) this)) {
            this.closeHandledScreen();
            this.currentScreenHandler = this.mxtPlayerInventoryScreenHandler;
        }
//        this.updateCapeAngles();
        if (!this.world.isClient) {
//            this.hungerManager.update((PlayerEntity) (Object) this);
            this.updateHealthAndMana();
            this.incrementStat(Stats.PLAY_TIME);
            this.incrementStat(Stats.TOTAL_WORLD_TIME);
            if (this.isAlive()) {
                this.incrementStat(Stats.TIME_SINCE_DEATH);
            }
            if (this.isSneaky()) {
                this.incrementStat(Stats.SNEAK_TIME);
            }
            if (!this.isSleeping()) {
                this.incrementStat(Stats.TIME_SINCE_REST);
            }
        }
        int i = 29999999;
        double d = MathHelper.clamp(this.getX(), -2.9999999E7, 2.9999999E7);
        double e = MathHelper.clamp(this.getZ(), -2.9999999E7, 2.9999999E7);
        if (d != this.getX() || e != this.getZ()) {
            this.setPosition(d, this.getY(), e);
        }
//        ++this.lastAttackedTicks; // TODO test
        ItemStack itemStack = this.getMainHandStack();
        if (!ItemStack.areEqual(this.selectedItem, itemStack)) {
//            if (!ItemStack.areItemsEqual(this.selectedItem, itemStack)) {
//                this.resetLastAttackedTicks();
//            } // TODO test
            this.selectedItem = itemStack.copy();
        }
//        this.updateTurtleHelmet();
//        this.itemCooldownManager.update(); // TODO test
        this.updatePose();
//        MinecraftXTerraria.LOGGER.info(this.getDimensions(this.getPose()).toString());
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void updatePose() {
//        if (!this.wouldPoseNotCollide(EntityPose.SWIMMING)) {
//            return;
//        }
//        EntityPose entityPose = this.isFallFlying() ? EntityPose.FALL_FLYING : (this.isSleeping() ? EntityPose.SLEEPING : (this.isSwimming() ? EntityPose.SWIMMING : (this.isUsingRiptide() ? EntityPose.SPIN_ATTACK : (this.isSneaking() && !this.abilities.flying ? EntityPose.CROUCHING : EntityPose.STANDING))));
//        EntityPose entityPose2 = this.isSpectator() || this.hasVehicle() || this.wouldPoseNotCollide(entityPose) ? entityPose : (this.wouldPoseNotCollide(EntityPose.CROUCHING) ? EntityPose.CROUCHING : EntityPose.SWIMMING);
        this.setPose(this.isSleeping() ? EntityPose.SLEEPING : EntityPose.STANDING);
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void dropInventory() {
        super.dropInventory();
        // TODO check world difficulty
        this.mxtPlayerInventory.dropAll();
//        this.mxtGearPlayerInventory.dropAll();
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
        // TODO knockback
//        if ((strength *= 1.0 - this.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)) <= 0.0) {
//            return;
//        }
//        this.velocityDirty = true;
//        Vec3d vec3d = this.getVelocity();
//        Vec3d vec3d2 = new Vec3d(x, 0.0, z).normalize().multiply(strength);
//        this.setVelocity(vec3d.x / 2.0 - vec3d2.x, this.onGround ? Math.min(0.4, vec3d.y / 2.0 + strength) : vec3d.y, vec3d.z / 2.0 - vec3d2.z);
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void closeHandledScreen() {
        this.currentScreenHandler = this.mxtPlayerInventoryScreenHandler;
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void tickMovement() {
        if (this.abilityResyncCountdown > 0) {
            --this.abilityResyncCountdown;
        }

        this.mxtPlayerInventory.updateItems();
//        this.mxtGearPlayerInventory.updateItems();
        // no striders
//        this.prevStrideDistance = this.strideDistance;
        super.tickMovement();
        this.airStrafingSpeed = 0.02f;
        if (this.isSprinting()) {
            this.airStrafingSpeed += 0.006f;
        }
        // TODO movement speed
        this.setMovementSpeed((float)this.getAttributeValue(EntityAttributesRegistry.MXT_MAX_MOVEMENT_SPEED));
        // no striders
//        float f = !this.onGround || this.isDead() || this.isSwimming() ? 0.0f : Math.min(0.1f, (float)this.getVelocity().horizontalLength());
//        this.strideDistance += (f - this.strideDistance) * 0.4f;

        // TODO entity collision
        if (this.getHealth() > 0.0f && !this.isSpectator()) {
            Box box = this.hasVehicle() && !this.getVehicle().isRemoved() ? this.getBoundingBox().union(this.getVehicle().getBoundingBox()).expand(1.0, 0.0, 1.0) : this.getBoundingBox().expand(1.0, 0.5, 1.0);
            List<Entity> list = this.world.getOtherEntities(this, box);
            // no experience
//            ArrayList<Entity> list2 = Lists.newArrayList();
            for (int i = 0; i < list.size(); ++i) {
                Entity entity = list.get(i);
                // no experience
//                if (entity.getType() == EntityType.EXPERIENCE_ORB) {
//                    list2.add(entity);
//                    continue;
//                }
                if (entity.isRemoved()) continue;
                this.collideWithEntity(entity);
            }
            // no experience
//            if (!list2.isEmpty()) {
//                this.collideWithEntity((Entity) Util.getRandom(list2, this.random));
//            }
        }
        // no shoulder entities
//        this.updateShoulderEntity(this.getShoulderEntityLeft());
//        this.updateShoulderEntity(this.getShoulderEntityRight());
//        if (!this.world.isClient && (this.fallDistance > 0.5f || this.isTouchingWater()) || this.abilities.flying || this.isSleeping() || this.inPowderSnow) {
//            this.dropShoulderEntities();
//        }
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public int getScore() {
        return 0;
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void setScore(int score) {}

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void addScore(int score) {}

    @Override
    public float getMaxHealth() {
        return (float) this.getAttributeValue(EntityAttributesRegistry.MXT_MAX_HEALTH);
    }

    @Override
    public void heal(float amount) {
        float f = this.getHealth();
        if (f > 0.0f) {
            this.setHealth(f + amount);
        }
    }

    @Override
    public float getHealth() {
        return this.dataTracker.get(MXT_HEALTH);
    }

    @Override
    public void setHealth(float health) {
        this.dataTracker.set(MXT_HEALTH, MathHelper.clamp(health, 0.0f, this.getMaxHealth()));
    }

    public float mxtGetMaxEffectiveHealthRegenerationTime() {
        return (float) this.getAttributeValue(EntityAttributesRegistry.MXT_MAX_EFFECTIVE_HEALTH_REGENERATION_TIME);
    }

    public float mxtGetMaxHealthRegenerationTime() {
        return (float) (this.mxtGetVitalCrystalConsumed() ? this.getAttributeValue(EntityAttributesRegistry.MXT_MAX_HEALTH_REGENERATION_TIME) * 0.8 : this.getAttributeValue(EntityAttributesRegistry.MXT_MAX_HEALTH_REGENERATION_TIME));
    }

    public float mxtGetBonusHealthRegenerationTime() {
        return (float) this.getAttributeValue(EntityAttributesRegistry.MXT_BONUS_HEALTH_REGENERATION_TIME);
    }

    public float mxtGetBaseHealthRegenerationRateModifier() {
        return (float) this.getAttributeValue(EntityAttributesRegistry.MXT_BASE_HEALTH_REGENERATION_RATE_MODIFIER);
    }

    public float mxtGetBonusHealthRegenerationRate() {
        return (float) this.getAttributeValue(EntityAttributesRegistry.MXT_BONUS_HEALTH_REGENERATION_RATE);
    }

    public float mxtGetPositiveHealthRegenerationRate() {
        return (float) this.getAttributeValue(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE);
    }

    public float mxtGetNegativeHealthRegenerationRate() {
        return (float) this.getAttributeValue(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE) + (this.hasStatusEffect(StatusEffectRegistry.THE_TONGUE) ? 300 : 0) + (this.hasStatusEffect(StatusEffectRegistry.ELECTRIFIED) ? (/*is player moving ? 96 */24) : 0);
    }

    public void mxtAddHealthRegenerationTime(float amount) {
        float f = this.mxtGetHealthRegenerationTime();
        this.mxtSetHealthRegenerationTime(f + amount);
    }

    public float mxtGetHealthRegenerationTime() {
        return this.dataTracker.get(MXT_HEALTH_REGENERATION_TIME);
    }

    public void mxtSetHealthRegenerationTime(float healthRegenerationTime) {
        this.dataTracker.set(MXT_HEALTH_REGENERATION_TIME, MathHelper.clamp(healthRegenerationTime, 0, this.mxtGetMaxHealthRegenerationTime()));
    }

    public void mxtAddEffectiveHealthRegenerationTime(float amount) {
        float f = this.mxtGetEffectiveHealthRegenerationTime();
        this.mxtSetEffectiveHealthRegenerationTime(f + amount);
    }

    public float mxtGetEffectiveHealthRegenerationTime() {
        return this.dataTracker.get(MXT_EFFECTIVE_HEALTH_REGENERATION_TIME);
    }

    public void mxtSetEffectiveHealthRegenerationTime(float effectiveHealthRegenerationTime) {
        this.dataTracker.set(MXT_EFFECTIVE_HEALTH_REGENERATION_TIME, MathHelper.clamp(effectiveHealthRegenerationTime, 0, this.mxtGetMaxEffectiveHealthRegenerationTime()));
    }

    public void mxtAddHealthRegenerationCounter(float amount) {
        float f = this.mxtGetHealthRegenerationCounter();
        this.mxtSetHealthRegenerationCounter(f + amount);
    }

    public float mxtGetHealthRegenerationCounter() {
        return this.dataTracker.get(MXT_HEALTH_REGENERATION_COUNTER);
    }

    public void mxtSetHealthRegenerationCounter(float healthRegenerationCounter) {
        this.dataTracker.set(MXT_HEALTH_REGENERATION_COUNTER, MathHelper.clamp(healthRegenerationCounter, MIN_HEALTH_REGENERATION_COUNTER, MAX_HEALTH_REGENERATION_COUNTER));
    }

    public float mxtGetMaxMana() {
        return (float) this.getAttributeValue(EntityAttributesRegistry.MXT_MAX_MANA);
    }

    public void mxtAddMana(float amount) {
        float f = this.mxtGetMana();
        this.mxtSetMana(f + amount);
    }

    public float mxtGetMana() {
        return this.dataTracker.get(MXT_MANA);
    }

    public void mxtSetMana(float mana) {
        this.dataTracker.set(MXT_MANA, MathHelper.clamp(mana, 0, this.mxtGetMaxMana()));
    }

    public void mxtReduceManaRegenerationDelay(float amount) {
        float f = this.mxtGetManaRegenerationDelay();
        this.mxtSetManaRegenerationDelay(f + amount);
    }

    public float mxtGetManaRegenerationDelay() {
        return this.dataTracker.get(MXT_MANA_REGENERATION_DELAY);
    }

    public void mxtSetManaRegenerationDelay(float manaRegenerationDelay) {
        this.dataTracker.set(MXT_MANA_REGENERATION_DELAY, MathHelper.clamp(manaRegenerationDelay, 0, 200));
    }

    public void mxtAddManaRegenerationCounter(float amount) {
        float f = this.mxtGetManaRegenerationCounter();
        this.mxtSetManaRegenerationCounter(f + amount);
    }

    public float mxtGetManaRegenerationCounter() {
        return this.dataTracker.get(MXT_MANA_REGENERATION_COUNTER);
    }

    public void mxtSetManaRegenerationCounter(float manaRegenerationCounter) {
        this.dataTracker.set(MXT_MANA_REGENERATION_COUNTER, MathHelper.clamp(manaRegenerationCounter, MIN_MANA_REGENERATION_COUNTER, MAX_MANA_REGENERATION_COUNTER));
    }

    public boolean mxtGetAegisFruitConsumed() {
        return this.dataTracker.get(MXT_AEGIS_FRUIT_CONSUMED);
    }

    public void mxtSetAegisFruitConsumed(boolean key) {
        this.dataTracker.set(MXT_AEGIS_FRUIT_CONSUMED, key);
    }

    public boolean mxtGetAmbrosiaConsumed() {
        return this.dataTracker.get(MXT_AMBROSIA_CONSUMED);
    }

    public void mxtSetAmbrosiaConsumed(boolean key) {
        this.dataTracker.set(MXT_AMBROSIA_CONSUMED, key);
    }

    public boolean mxtGetArcaneCrystalConsumed() {
        return this.dataTracker.get(MXT_ARCANE_CRYSTAL_CONSUMED);
    }

    public void mxtSetArcaneCrystalConsumed(boolean key) {
        this.dataTracker.set(MXT_ARCANE_CRYSTAL_CONSUMED, key);
    }

    public boolean mxtGetArtisanLoafConsumed() {
        return this.dataTracker.get(MXT_ARTISAN_LOAF_CONSUMED);
    }

    public void mxtSetArtisanLoafConsumed(boolean key) {
        this.dataTracker.set(MXT_ARTISAN_LOAF_CONSUMED, key);
    }

    public boolean mxtGetDemonHeartConsumed() {
        return this.dataTracker.get(MXT_DEMON_HEART_CONSUMED);
    }

    public void mxtSetDemonHeartConsumed(boolean key) {
        this.dataTracker.set(MXT_DEMON_HEART_CONSUMED, key);
    }

    public boolean mxtGetGalaxyPearlConsumed() {
        return this.dataTracker.get(MXT_GALAXY_PEARL_CONSUMED);
    }

    public void mxtSetGalaxyPearlConsumed(boolean key) {
        this.dataTracker.set(MXT_GALAXY_PEARL_CONSUMED, key);
    }

    public boolean mxtGetGummyWormConsumed() {
        return this.dataTracker.get(MXT_GUMMY_WORM_CONSUMED);
    }

    public void mxtSetGummyWormConsumed(boolean key) {
        this.dataTracker.set(MXT_GUMMY_WORM_CONSUMED, key);
    }

    public boolean mxtGetMminecartUpgradeConsumed() {
        return this.dataTracker.get(MXT_MINECART_UPGRADE_CONSUMED);
    }

    public void mxtSetMminecartUpgradeConsumed(boolean key) {
        this.dataTracker.set(MXT_MINECART_UPGRADE_CONSUMED, key);
    }

    public boolean mxtGetTorchGodFavorConsumed() {
        return this.dataTracker.get(MXT_TORCH_GOD_FAVOR_CONSUMED);
    }

    public void mxtSetTorchGodFavorConsumed(boolean key) {
        this.dataTracker.set(MXT_TORCH_GOD_FAVOR_CONSUMED, key);
    }

    public boolean mxtGetVitalCrystalConsumed() {
        return this.dataTracker.get(MXT_VITAL_CRYSTAL_CONSUMED);
    }

    public void mxtSetVitalCrystalConsumed(boolean key) {
        this.dataTracker.set(MXT_VITAL_CRYSTAL_CONSUMED, key);
    }

    public boolean mxtGetCharacterCreated() {
        return this.dataTracker.get(MXT_CHARACTER_CREATED);
    }

    public void mxtSetCharacterCreated(boolean key) {
        this.dataTracker.set(MXT_CHARACTER_CREATED, key);
    }

    public void mxtAddLifeCrystalsConsumed(int amount) {
        int i = this.mxtGetLifeCrystalsConsumed();
        this.mxtSetLifeCrystalsConsumed(i + amount);
    }

    public int mxtGetLifeCrystalsConsumed() {
        return this.dataTracker.get(MXT_LIFE_CRYSTALS_CONSUMED);
    }

    public void mxtSetLifeCrystalsConsumed(int lifeCrystalsConsumed) {
        this.dataTracker.set(MXT_LIFE_CRYSTALS_CONSUMED, MathHelper.clamp(lifeCrystalsConsumed, 0, 15));
    }

    public void mxtAddLifeFruitsConsumed(int amount) {
        int i = this.mxtGetLifeFruitsConsumed();
        this.mxtSetLifeFruitsConsumed(i + amount);
    }

    public int mxtGetLifeFruitsConsumed() {
        return this.dataTracker.get(MXT_LIFE_FRUITS_CONSUMED);
    }

    public void mxtSetLifeFruitsConsumed(int lifeFruitsConsumed) {
        this.dataTracker.set(MXT_LIFE_FRUITS_CONSUMED, MathHelper.clamp(lifeFruitsConsumed, 0, 20));
    }

    public void mxtAddManaCrystalsConsumed(int amount) {
        int i = this.mxtGetManaCrystalsConsumed();
        this.mxtSetManaCrystalsConsumed(i + amount);
    }

    public int mxtGetManaCrystalsConsumed() {
        return this.dataTracker.get(MXT_MANA_CRYSTALS_CONSUMED);
    }

    public void mxtSetManaCrystalsConsumed(int manaCrystalsConsumed) {
        this.dataTracker.set(MXT_MANA_CRYSTALS_CONSUMED, MathHelper.clamp(manaCrystalsConsumed, 0, 9));
    }

    public void mxtIncreaseCompletedAnglerQuests(int amount) {
        int i = this.mxtGetCompletedAnglerQuests();
        this.mxtSetCompletedAnglerQuests(i + amount);
    }

    public int mxtGetCompletedAnglerQuests() {
        return this.dataTracker.get(COMPLETED_ANGLER_QUESTS);
    }

    public void mxtSetCompletedAnglerQuests(int completedAnglerQuests) {
        this.dataTracker.set(COMPLETED_ANGLER_QUESTS, Math.max(completedAnglerQuests, 0));
    }

    private void updateHealthAndMana() {
        if (this.getAttributeInstance(EntityAttributesRegistry.MXT_MAX_HEALTH).getBaseValue() != 100.0 + this.mxtGetLifeCrystalsConsumed() * 20.0 + this.mxtGetLifeFruitsConsumed() * 5.0) {
            this.getAttributeInstance(EntityAttributesRegistry.MXT_MAX_HEALTH).setBaseValue(100.0 + this.mxtGetLifeCrystalsConsumed() * 20.0 + this.mxtGetLifeFruitsConsumed() * 5.0);
        }
        if (this.getAttributeInstance(EntityAttributesRegistry.MXT_MAX_MANA).getBaseValue() != 20.0 + this.mxtGetManaCrystalsConsumed() * 20.0) {
            this.getAttributeInstance(EntityAttributesRegistry.MXT_MAX_MANA).setBaseValue(20.0 + this.mxtGetManaCrystalsConsumed() * 20.0);
        }
        if (this.getHealth() < this.getMaxHealth()) {

            // reminder every additive value is multiplied by 3 compared to the terraria values because minecraft has only 20 ticks per second instead of 60 like terraria
            boolean isShinyStoneEquipped = this.mxtHasItemInSlotTypeEquipped(ItemsRegistry.SHINY_STONE, MxtEquipmentSlot.Type.ACCESSORY);
            MinecraftXTerraria.LOGGER.info("isShinyStoneEquipped: " + isShinyStoneEquipped);
            boolean isResting = false; // TODO check if player is sitting on a chair/sofa or lying in bed
            float movementMultiplier = /*TODO is player moving ? 0.5 : */1.25F;
            float restBonus = isResting ? 1.5F : 1.0F;
            float shinyStoneModifier = isShinyStoneEquipped ? 1.1F : 1.0F;
            float feralBiteModifier = this.hasStatusEffect(StatusEffectRegistry.FERAL_BITE) ? (isShinyStoneEquipped ? 0.75F : 0.5F) : 1.0F;
            float difficultyMultiplier = /*TODO player difficulty is expert or master and player does not have well fed status effect ? (isShinyStoneEquipped ? 0.75F : 0.5F) : */1.0F;

            // advance healthRegenerationTime
            if (this.mxtGetHealthRegenerationTime() < this.mxtGetMaxHealthRegenerationTime()) {
                this.mxtAddHealthRegenerationTime(3 + this.mxtGetBonusHealthRegenerationTime());
            }

            int firstRegenerationTimeExchangeRate = this.mxtGetVitalCrystalConsumed() ? 240 : 300;
            int secondRegenerationTimeExchangeRate = this.mxtGetVitalCrystalConsumed() ? 480 : 600;
            int firstRegenerationTimeThreshold = this.mxtGetVitalCrystalConsumed() ? 72 : 90;
            int secondRegenerationTimeThreshold = this.mxtGetVitalCrystalConsumed() ? 1440 : 1800;
            int thirdRegenerationTimeThreshold = this.mxtGetVitalCrystalConsumed() ? 2400 : 3000;
            if (!isShinyStoneEquipped/*TODO && itemUseCooldown == 0*/) {
                if (this.mxtGetHealthRegenerationTime() <= secondRegenerationTimeThreshold) {
                    this.mxtSetEffectiveHealthRegenerationTime((float) Math.floor(this.mxtGetHealthRegenerationTime() / firstRegenerationTimeExchangeRate));
                } else {
                    this.mxtSetEffectiveHealthRegenerationTime((float) (6 + Math.floor((this.mxtGetHealthRegenerationTime() - secondRegenerationTimeThreshold) / secondRegenerationTimeExchangeRate)));
                }
            } else {

                if (firstRegenerationTimeThreshold < this.mxtGetHealthRegenerationTime() && this.mxtGetHealthRegenerationTime() < secondRegenerationTimeThreshold) {
                    this.mxtSetHealthRegenerationTime(secondRegenerationTimeThreshold);
                }

                if (this.mxtGetHealthRegenerationTime() <= secondRegenerationTimeThreshold) {
                    this.mxtSetEffectiveHealthRegenerationTime((float) Math.floor(this.mxtGetHealthRegenerationTime() / firstRegenerationTimeExchangeRate));
                } else if (this.mxtGetHealthRegenerationTime() <= thirdRegenerationTimeThreshold) {
                    this.mxtSetEffectiveHealthRegenerationTime((float) (6 + Math.floor((this.mxtGetHealthRegenerationTime() - secondRegenerationTimeThreshold) / secondRegenerationTimeExchangeRate)));
                } else {
                    this.mxtSetEffectiveHealthRegenerationTime((float) (8 + Math.floor((this.mxtGetHealthRegenerationTime() - thirdRegenerationTimeThreshold) / firstRegenerationTimeExchangeRate)));
                }
            }

            float baseHealthRegenerationRate = (float) (((getMaxHealth() / 400) * 0.85 + 0.15) * this.mxtGetEffectiveHealthRegenerationTime() * mxtGetBaseHealthRegenerationRateModifier() * movementMultiplier * restBonus * shinyStoneModifier * feralBiteModifier);

            float modifiedHealthRegenerationRate = Math.round(baseHealthRegenerationRate) + mxtGetBonusHealthRegenerationRate() + (mxtGetNegativeHealthRegenerationRate() > 0.0F ? mxtGetNegativeHealthRegenerationRate() : mxtGetPositiveHealthRegenerationRate());

            if (isShinyStoneEquipped/*TODO && itemUseCooldown == 0*/) {
                if (modifiedHealthRegenerationRate < 0) {
                    if (this.hasStatusEffect(StatusEffectRegistry.HONEY)) {
                        modifiedHealthRegenerationRate = Math.round(Math.min(0, modifiedHealthRegenerationRate + 12) * 0.5) + 6;
                    } else {
                        modifiedHealthRegenerationRate = Math.round(modifiedHealthRegenerationRate * 0.5);

                    }
                }
                modifiedHealthRegenerationRate += 12;
            } else {
                if (this.hasStatusEffect(StatusEffectRegistry.HONEY)) {
                    if (modifiedHealthRegenerationRate <= -4) {
                        modifiedHealthRegenerationRate = modifiedHealthRegenerationRate + 18;
                    } else if (modifiedHealthRegenerationRate <= 0) {
                        modifiedHealthRegenerationRate = 6;
                    } else if (modifiedHealthRegenerationRate > 0) {
                        modifiedHealthRegenerationRate = modifiedHealthRegenerationRate + 6;
                    }
                }
            }

            float finalHealthRegenerationRate = Math.round(modifiedHealthRegenerationRate * difficultyMultiplier);

            if (finalHealthRegenerationRate > 0) {
                this.mxtAddHealthRegenerationCounter(finalHealthRegenerationRate + 3);
            } else {
                this.mxtAddHealthRegenerationCounter(finalHealthRegenerationRate);
            }
            if (this.hasStatusEffect(StatusEffectRegistry.RAPID_HEALING)) {
                this.mxtAddHealthRegenerationCounter(12);
            }

            boolean isBurningOrSuffocating = this.hasStatusEffect(StatusEffectRegistry.BURNING) || this.hasStatusEffect(StatusEffectRegistry.SUFFOCATION);
            // check regeneration counter
            if (this.mxtGetHealthRegenerationCounter() >= 40) {
                this.mxtAddHealthRegenerationCounter(-40);
                this.heal(1); // TODO play test for balance
            } else if (!isBurningOrSuffocating && this.mxtGetHealthRegenerationCounter() <= -40) {
                this.mxtAddHealthRegenerationCounter(40);
                this.heal(-1); // TODO play test for balance
            } else if (isBurningOrSuffocating && this.mxtGetHealthRegenerationCounter() <= -200) {
                this.mxtAddHealthRegenerationCounter(40); // TODO play test +40 or +200 ?
                this.heal(-5); // TODO play test for balance
            }
        } else {
            this.setHealth(this.getMaxHealth()); // TODO maybe make it proportional
            this.mxtSetHealthRegenerationCounter(0);
        }
        boolean wearsManaRegenerationBand = this.mxtHasItemInSlotTypeEquipped(ItemsRegistry.MANA_REGENERATION_BAND, MxtEquipmentSlot.Type.ACCESSORY);
        if (this.mxtGetManaRegenerationDelay() > 0) {
            if (this.hasStatusEffect(StatusEffectRegistry.MANA_REGENERATION) && this.mxtGetManaRegenerationDelay() > 20) {
                this.mxtSetManaRegenerationDelay(20);
            }
            this.mxtReduceManaRegenerationDelay((/*TODO is player moving ? 3 : */6) + (wearsManaRegenerationBand ? 3 : 0));
        } else {
            if (this.mxtGetMana() < this.mxtGetMaxMana()) {

                float manaRegenerationRate;
                if (this.hasStatusEffect(StatusEffectRegistry.MANA_REGENERATION)) {
                    manaRegenerationRate = (float) (((this.mxtGetMaxMana() / 7) + 1 + (this.mxtGetMaxMana() / 2) + (wearsManaRegenerationBand ? 25 : 0)) * 1.15);
                } else {
                    if (false/*TODO is player moving*/) {
                        manaRegenerationRate = (float) (((this.mxtGetMaxMana() / 7) + 1 + (wearsManaRegenerationBand ? 25 : 0)) * ((this.mxtGetMana() / this.mxtGetMaxMana()) * 0.8 + 0.2) * 1.15);
                    } else {
                        manaRegenerationRate = (float) (((this.mxtGetMaxMana() / 7) + 1 + (this.mxtGetMaxMana() / 2) + (wearsManaRegenerationBand ? 25 : 0)) * ((this.mxtGetMana() / this.mxtGetMaxMana()) * 0.8 + 0.2) * 1.15);
                    }
                }

                this.mxtAddManaRegenerationCounter(manaRegenerationRate);

                // check mana regeneration counter
                if (this.mxtGetManaRegenerationCounter() >= 40) {
                    this.mxtAddManaRegenerationCounter(-40);
                    this.mxtAddMana(1); // TODO play test for balance
                } else if (this.mxtGetManaRegenerationCounter() <= -40) {
                    this.mxtAddManaRegenerationCounter(40);
                    this.mxtAddMana(-1); // TODO play test for balance
                }
            } else {
                this.mxtSetManaRegenerationCounter(0);
            }
        }
    }

    public void resetManaRegenerationDelay() {
        float newManaRegenerationDelay = (float) (((1 - (this.mxtGetMana() / this.mxtGetMaxMana())) * 240 + 45) * 0.7);
        this.mxtSetManaRegenerationDelay(newManaRegenerationDelay);
    }

    @Override
    public boolean isDead() {
        return this.getHealth() <= 0.0f;
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setUuid(Uuids.getUuidFromProfile(this.gameProfile));
        NbtList nbtList = nbt.getList("MxtInventory", NbtElement.COMPOUND_TYPE);
        this.mxtPlayerInventory.readNbt(nbtList);
//        NbtList nbtGearList = nbt.getList("MxtGearInventory", NbtElement.COMPOUND_TYPE);
//        this.mxtGearPlayerInventory.readNbt(nbtGearList);
        this.mxtPlayerInventory.selectedSlot = nbt.getInt("SelectedItemSlot");
        this.setActiveLoadout(nbt.getInt("ActiveLoadout"));
        this.sleepTimer = nbt.getShort("SleepTimer");
        this.abilities.readNbt(nbt);
        this.getAttributeInstance(EntityAttributesRegistry.MXT_MAX_MOVEMENT_SPEED).setBaseValue(this.abilities.getWalkSpeed());

        // attributes
        this.mxtSetHealthRegenerationTime(nbt.getFloat("HealthRegenerationTime"));
        this.mxtSetEffectiveHealthRegenerationTime(nbt.getFloat("EffectiveHealthRegenerationTime"));
        this.mxtSetHealthRegenerationCounter(nbt.getFloat("HealthRegenerationCounter"));
        this.mxtSetMana(nbt.getFloat("Mana"));
        this.mxtSetManaRegenerationDelay(nbt.getFloat("ManaRegenerationDelay"));
        this.mxtSetManaRegenerationCounter(nbt.getFloat("ManaRegenerationCounter"));

        // player keys
        this.mxtSetAegisFruitConsumed(nbt.getBoolean("AegisFruitConsumed"));
        this.mxtSetVitalCrystalConsumed(nbt.getBoolean("VitalCrystalConsumed"));
        this.mxtSetCharacterCreated(nbt.getBoolean("CharacterCreated"));

        if (nbt.contains("LastDeathLocation", NbtElement.COMPOUND_TYPE)) {
            this.setLastDeathPos(GlobalPos.CODEC.parse(NbtOps.INSTANCE, nbt.get("LastDeathLocation")).resultOrPartial(LOGGER::error));
        }
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("DataVersion", SharedConstants.getGameVersion().getWorldVersion());
        nbt.put("MxtInventory", this.mxtPlayerInventory.writeNbt(new NbtList()));
//        nbt.put("MxtGearInventory", this.mxtGearPlayerInventory.writeNbt(new NbtList()));
        nbt.putInt("SelectedItemSlot", this.mxtPlayerInventory.selectedSlot);
        nbt.putInt("ActiveLoadout", this.getActiveLoadout());
        nbt.putShort("SleepTimer", (short)this.sleepTimer);

        this.abilities.writeNbt(nbt);

        // attributes
        nbt.putFloat("HealthRegenerationTime", this.mxtGetHealthRegenerationTime());
        nbt.putFloat("EffectiveHealthRegenerationTime", this.mxtGetEffectiveHealthRegenerationTime());
        nbt.putFloat("HealthRegenerationCounter", this.mxtGetHealthRegenerationCounter());
        nbt.putFloat("Mana", this.mxtGetMana());
        nbt.putFloat("ManaRegenerationDelay", this.mxtGetManaRegenerationDelay());
        nbt.putFloat("ManaRegenerationCounter", this.mxtGetManaRegenerationCounter());

        // player keys
        nbt.putBoolean("AegisFruitConsumed", this.mxtGetAegisFruitConsumed());
        nbt.putBoolean("VitalCrystalConsumed", this.mxtGetVitalCrystalConsumed());
        nbt.putBoolean("CharacterCreated", this.mxtGetCharacterCreated());

        this.getLastDeathPos().flatMap(globalPos -> GlobalPos.CODEC.encodeStart(NbtOps.INSTANCE, (GlobalPos)globalPos).resultOrPartial(LOGGER::error)).ifPresent(nbtElement -> nbt.put("LastDeathLocation", (NbtElement)nbtElement));
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (this.abilities.invulnerable && !source.isOutOfWorld()) {
            return false;
        }
        this.despawnCounter = 0;
        if (this.isDead()) {
            return false;
        }
//        if (!this.world.isClient) {
//            this.dropShoulderEntities();
//        }
//        if (source.isScaledWithDifficulty()) {
//            if (this.world.getDifficulty() == Difficulty.PEACEFUL) {
//                amount = 0.0f;
//            }
//            if (this.world.getDifficulty() == Difficulty.EASY) {
//                amount = Math.min(amount / 2.0f + 1.0f, amount);
//            }
//            if (this.world.getDifficulty() == Difficulty.HARD) {
//                amount = amount * 3.0f / 2.0f;
//            }
//        }
        if (amount == 0.0f) {
            return false;
        }
        boolean bl3;
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (this.world.isClient) {
            return false;
        }
        if (this.isDead()) {
            return false;
        }
//        if (source.isFire() && this.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
//            return false;
//        }
        if (this.isSleeping() && !this.world.isClient) {
            this.wakeUp();
        }
        this.despawnCounter = 0;
        float f = amount;
//        boolean bl = false;
        float g = 0.0f;
        // shield block
//        if (amount > 0.0f && this.blockedByShield(source)) {
//            Entity entity;
//            this.damageShield(amount);
//            g = amount;
//            amount = 0.0f;
//            if (!source.isProjectile() && (entity = source.getSource()) instanceof LivingEntity) {
//                LivingEntity livingEntity = (LivingEntity)entity;
//                this.takeShieldHit(livingEntity);
//            }
//            bl = true;
//        }
        this.limbDistance = 1.5f;
        boolean bl2 = true;
        if ((float)this.timeUntilRegen > 10.0f) {
            if (amount <= this.lastDamageTaken) {
                return false;
            }
            this.applyDamage(source, amount - this.lastDamageTaken);
            this.lastDamageTaken = amount;
            bl2 = false;
        } else {
            this.lastDamageTaken = amount;
            this.timeUntilRegen = 20;
            this.applyDamage(source, amount);
            this.hurtTime = this.maxHurtTime = 10;
        }
        // TODO suffocation damage
        //  inflict status effect instead
        if (source.isFallingBlock() && !this.mxtGetEquippedStack(MxtEquipmentSlot.HEAD).isEmpty()) {
            this.damageHelmet(source, amount);
            amount *= 0.75f;
        }
        this.knockbackVelocity = 0.0f;
        Entity entity2 = source.getAttacker();
//        if (entity2 != null) {
//            WolfEntity wolfEntity;
//            if (entity2 instanceof LivingEntity && !source.isNeutral()) {
//                this.setAttacker((LivingEntity)entity2);
//            }
//            if (entity2 instanceof PlayerEntity) {
//                this.playerHitTimer = 100;
//                this.attackingPlayer = (PlayerEntity)entity2;
//            } else if (entity2 instanceof WolfEntity && (wolfEntity = (WolfEntity)entity2).isTamed()) {
//                this.playerHitTimer = 100;
//                LivingEntity livingEntity2 = wolfEntity.getOwner();
//                this.attackingPlayer = livingEntity2 != null && livingEntity2.getType() == EntityType.PLAYER ? (PlayerEntity)livingEntity2 : null;
//            }
//        }

        // TODO knockback
        if (bl2) {
//            if (bl) {
//                this.world.sendEntityStatus(this, EntityStatuses.BLOCK_WITH_SHIELD);
//            } else
            if (source instanceof EntityDamageSource && ((EntityDamageSource)source).isThorns()) {
                this.world.sendEntityStatus(this, EntityStatuses.DAMAGE_FROM_THORNS);
            } else {
                int b = source == DamageSource.DROWN ? 36 : (source.isFire() ? 37 : (source == DamageSource.SWEET_BERRY_BUSH ? 44 : (source == DamageSource.FREEZE ? 57 : (int)EntityStatuses.DAMAGE_FROM_GENERIC_SOURCE)));
                this.world.sendEntityStatus(this, (byte)b);
            }
            if (source != DamageSource.DROWN && amount > 0.0f) {
                this.scheduleVelocityUpdate();
            }
            if (entity2 != null && !source.isExplosive()) {
                double d = entity2.getX() - this.getX();
                double e = entity2.getZ() - this.getZ();
                while (d * d + e * e < 1.0E-4) {
                    d = (Math.random() - Math.random()) * 0.01;
                    e = (Math.random() - Math.random()) * 0.01;
                }
                this.knockbackVelocity = (float)(MathHelper.atan2(e, d) * 57.2957763671875 - (double)this.getYaw());
                this.takeKnockback(0.4f, d, e);
            } else {
                this.knockbackVelocity = (int)(Math.random() * 2.0) * 180;
            }
        }
//        if (this.isDead()) {
//            if (!this.tryUseTotem(source)) {
//                SoundEvent soundEvent = this.getDeathSound();
//                if (bl2 && soundEvent != null) {
//                    this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
//                }
//                this.onDeath(source);
//            }
//        } else
        if (bl2) {
            this.playHurtSound(source);
        }
        boolean bl4 = bl3 = /*!bl ||*/ amount > 0.0f;
        if (bl3) {
            this.lastDamageSource = source;
            this.lastDamageTime = this.world.getTime();
        }
        if (((DuckPlayerEntityMixin) (Object) this) instanceof ServerPlayerEntity) {
            Criteria.ENTITY_HURT_PLAYER.trigger((ServerPlayerEntity)((DuckPlayerEntityMixin) (Object) this), source, f, amount, false);
            if (g > 0.0f && g < 3.4028235E37f) {
                ((ServerPlayerEntity)((DuckPlayerEntityMixin) (Object) this)).increaseStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round(g * 10.0f));
            }
        }
        if (entity2 instanceof ServerPlayerEntity) {
            Criteria.PLAYER_HURT_ENTITY.trigger((ServerPlayerEntity)entity2, this, source, f, amount, false);
        }
        return bl3;
    }

    @Override
    public boolean isAlive() {
        return !this.isRemoved() && this.getHealth() > 0.0f;
    }

    @Override
    public int getArmor() {
        return 0;
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void damageArmor(DamageSource source, float amount) {
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void damageHelmet(DamageSource source, float amount) {
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void damageShield(float amount) {
    }

    @Override
    protected float applyArmorToDamage(DamageSource source, float amount) {
//        if (!source.bypassesArmor()) {
//            this.damageArmor(source, amount);
//            amount = DamageUtil.getDamageLeft(amount, this.getArmor(), (float)this.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS));
//        }
//        return amount;
        return 0.0F;
    }

//    protected float modifyAppliedDamage(DamageSource source, float amount) {
//        int i;
//        int j;
//        float f;
//        float g;
//        float h;
//        if (source.isUnblockable()) {
//            return amount;
//        }
//        if (this.hasStatusEffect(StatusEffects.RESISTANCE) && source != DamageSource.OUT_OF_WORLD && (h = (g = amount) - (amount = Math.max((f = amount * (float)(j = 25 - (i = (this.getStatusEffect(StatusEffects.RESISTANCE).getAmplifier() + 1) * 5))) / 25.0f, 0.0f))) > 0.0f && h < 3.4028235E37f) {
//            if (this instanceof ServerPlayerEntity) {
//                ((ServerPlayerEntity)this).increaseStat(Stats.DAMAGE_RESISTED, Math.round(h * 10.0f));
//            } else if (source.getAttacker() instanceof ServerPlayerEntity) {
//                ((ServerPlayerEntity)source.getAttacker()).increaseStat(Stats.DAMAGE_DEALT_RESISTED, Math.round(h * 10.0f));
//            }
//        }
//        if (amount <= 0.0f) {
//            return 0.0f;
//        }
//        if (source.bypassesProtection()) {
//            return amount;
//        }
//        i = EnchantmentHelper.getProtectionAmount(this.getArmorItems(), source);
//        if (i > 0) {
//            amount = DamageUtil.getInflictedDamage(amount, i);
//        }
//        return amount;
//    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void applyDamage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return;
        }
        amount = this.applyArmorToDamage(source, amount);
        float f = amount = this.modifyAppliedDamage(source, amount);
        amount = Math.max(amount - this.getAbsorptionAmount(), 0.0f);
        this.setAbsorptionAmount(this.getAbsorptionAmount() - (f - amount));
        float g = f - amount;
        if (g > 0.0f && g < 3.4028235E37f) {
            this.increaseStat(Stats.DAMAGE_ABSORBED, Math.round(g * 10.0f));
        }
        if (amount == 0.0f) {
            return;
        }
//        this.addExhaustion(source.getExhaustion());
        float h = this.getHealth();
        this.setHealth(this.getHealth() - amount);
        this.getDamageTracker().onDamage(source, h, amount);
        if (amount < 3.4028235E37f) {
            this.increaseStat(Stats.DAMAGE_TAKEN, Math.round(amount * 10.0f));
        }
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void attack(Entity target) {
        if (!target.isAttackable()) {
            return;
        }
        if (target.handleAttack(this)) {
            return;
        }
        // TODO check for target instance
        ItemStack itemStack = this.getMainHandStack();
        // get base attack damage
        // this includes the base weapon damage and all modifiers to the attribute
        // (weapon modifier, accessories, accessory modifiers, armour modifiers, armour set bonus and status effects)
        double weaponDamage;
        if (itemStack.getItem() instanceof MxtMeleeWeaponItem) {
            weaponDamage = this.getAttributeValue(EntityAttributesRegistry.MXT_MELEE_ATTACK_DAMAGE);
        } else if (itemStack.getItem() instanceof MxtRangedWeaponItem) {
            weaponDamage = this.getAttributeValue(EntityAttributesRegistry.MXT_RANGED_ATTACK_DAMAGE);
        } else if (itemStack.getItem() instanceof MxtMagicWeaponItem) {
            weaponDamage = this.getAttributeValue(EntityAttributesRegistry.MXT_MAGIC_ATTACK_DAMAGE);
        } else if (itemStack.getItem() instanceof MxtSummonWeaponItem) {
            weaponDamage = this.getAttributeValue(EntityAttributesRegistry.MXT_SUMMON_ATTACK_DAMAGE);
        } else {
            weaponDamage = 0.0F;
        }

        // TODO check for exceptions to the damageRandomization
        double damageRandomization = random.nextBetween(0, 15) * 0.1 * (random.nextBoolean() ? 1 : -1);
//        weaponDamage = weaponDamage * random.nextBetween(0, 15) * 0.1 * (random.nextBoolean() ? 1 : -1);

        double enemyDamageReduction;
        double armourPenetration = this.getAttributeValue(EntityAttributesRegistry.MXT_ARMOUR_PENETRATION);
        if (target.isPlayer()) {
            double playerDefense = ((PlayerEntity) target).getAttributeValue(EntityAttributesRegistry.MXT_DEFENSE);
            if (armourPenetration > playerDefense) {
                playerDefense = 0.0;
            } else {
                playerDefense = playerDefense - armourPenetration;
            }
            if (this.world.getDifficulty() == Difficulty.EASY) {
                enemyDamageReduction = Math.floor(playerDefense * 0.5);
            } else if (this.world.getDifficulty() == Difficulty.NORMAL) {
                enemyDamageReduction = Math.floor(playerDefense * 0.75);
            } else if (this.world.getDifficulty() == Difficulty.HARD) {
                enemyDamageReduction = playerDefense;
            } else {
                // TODO journey mode
                //  get current difficulty
                enemyDamageReduction = Math.floor(playerDefense * 0.5);
            }
        } else {
            double defense = ((MxtLivingEntity)target).getAttributeValue(EntityAttributesRegistry.MXT_DEFENSE);
            if (armourPenetration > defense) {
                defense = 0.0;
            } else {
                defense = defense - armourPenetration;
            }
            enemyDamageReduction = Math.floor(defense * 0.5);
        }
        // TODO implement enemy banner effects
        boolean nearbyEnemyBanner = !target.isPlayer();// && this.hasStatusEffect();
        double bannerMultiplier;
        if (nearbyEnemyBanner) {
            if (this.world.getDifficulty() == Difficulty.EASY) {
                bannerMultiplier = 1.5;
            } else if (this.world.getDifficulty() == Difficulty.NORMAL || this.world.getDifficulty() == Difficulty.HARD) {
                bannerMultiplier = 2.0;
            } else {
                // TODO journey mode
                //  get current difficulty
                bannerMultiplier = 1.0;
            }
        } else {
            bannerMultiplier = 1.0;
        }
        double critMultiplier = random.nextDouble() <= this.getAttributeValue(EntityAttributesRegistry.MXT_CRIT_CHANCE) && !target.isPlayer() ? 2 : 1;

        // calculate damage dealt to target
        double dealtDamage = (Math.floor(Math.floor(weaponDamage * damageRandomization) * bannerMultiplier) - enemyDamageReduction) * critMultiplier;

        Vec3d vec3d = target.getVelocity();

        // damage target
        boolean bl6 = target.damage(DamageSource.player((PlayerEntity) (Object) this), (float) dealtDamage);

        if (bl6) {

            int i = 0;
            // TODO calculate knockback
            i += EnchantmentHelper.getKnockback(this);

                // TODO deal knockback
                if (i > 0) {
                    if (target instanceof LivingEntity) {
                        ((LivingEntity)target).takeKnockback((float)i * 0.5f, MathHelper.sin(this.getYaw() * ((float)Math.PI / 180)), -MathHelper.cos(this.getYaw() * ((float)Math.PI / 180)));
                    } else {
                        target.addVelocity(-MathHelper.sin(this.getYaw() * ((float)Math.PI / 180)) * (float)i * 0.5f, 0.1, MathHelper.cos(this.getYaw() * ((float)Math.PI / 180)) * (float)i * 0.5f);
                    }
                    this.setVelocity(this.getVelocity().multiply(0.6, 1.0, 0.6));
                    this.setSprinting(false);
                }

                // TODO knockback
                if (target instanceof ServerPlayerEntity && target.velocityModified) {
                    ((ServerPlayerEntity)target).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(target));
                    target.velocityModified = false;
                    target.setVelocity(vec3d);
                }

                // TODO play when critical hit?
//                if (bl3) {
//                    this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, this.getSoundCategory(), 1.0f, 1.0f);
//                    this.addCritParticles(target);
//                }


                // TODO play attack sound when not critical hit?
//                if (!bl3 && !bl42) {
//                    if (bl) {
//                        this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, this.getSoundCategory(), 1.0f, 1.0f);
//                    } else {
//                        this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, this.getSoundCategory(), 1.0f, 1.0f);
//                    }
//                }

                // TODO add status effects to target

//                if (g > 0.0f) {
//                    this.addEnchantedHitParticles(target);
//                }

                // TODO necessary?
//                this.onAttacking(target);

                // TODO summons?
//                if (target instanceof LivingEntity) {
//                    EnchantmentHelper.onUserDamaged((LivingEntity)target, this);
//                }

                // TODO necessary?
//                if (!this.world.isClient && !itemStack2.isEmpty() && entity instanceof LivingEntity) {
//                    itemStack2.postHit((LivingEntity)entity, this);
//                    if (itemStack2.isEmpty()) {
//                        this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
//                    }
//                }

                // TODO statistics
//                if (target instanceof LivingEntity) {
//                    float m = j - ((LivingEntity)target).getHealth();
//                    this.increaseStat(Stats.DAMAGE_DEALT, Math.round(m * 10.0f));
//                    if (k > 0) {
//                        target.setOnFireFor(k * 4);
//                    }
//                    if (this.world instanceof ServerWorld && m > 2.0f) {
//                        int n = (int)((double)m * 0.5);
//                        ((ServerWorld)this.world).spawnParticles(ParticleTypes.DAMAGE_INDICATOR, target.getX(), target.getBodyY(0.5), target.getZ(), n, 0.1, 0.0, 0.1, 0.2);
//                    }
//                }
        }
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void remove(Entity.RemovalReason reason) {
        super.remove(reason);
        this.mxtPlayerInventoryScreenHandler.close((PlayerEntity) (Object) this);
        if (this.currentScreenHandler != null && this.shouldCloseHandledScreenOnRespawn()) {
            this.closeScreenHandler();
        }
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public boolean shouldCloseHandledScreenOnRespawn() {
        return this.currentScreenHandler != this.mxtPlayerInventoryScreenHandler;
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public float getMovementSpeed() {
        return (float)this.getAttributeValue(EntityAttributesRegistry.MXT_MAX_MOVEMENT_SPEED);
    }

    @Override
    public ItemStack getMainHandStack() {
        return this.mxtGetEquippedStack(MxtEquipmentSlot.HAND);
    }

    @Override
    public ItemStack getOffHandStack() {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getStackInHand(Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            return this.mxtGetEquippedStack(MxtEquipmentSlot.HAND);
        }
        if (hand == Hand.OFF_HAND) {
            return ItemStack.EMPTY;
        }
        throw new IllegalArgumentException("Invalid hand " + hand);
    }

    @Override
    public void setStackInHand(Hand hand, ItemStack stack) {
        if (hand == Hand.MAIN_HAND) {
            this.mxtEquipStack(MxtEquipmentSlot.HAND, stack);
        } else if (hand == Hand.OFF_HAND) {
            return;
        } else {
            throw new IllegalArgumentException("Invalid hand " + hand);
        }
    }

    public boolean mxtHasItemInSlotTypeEquipped(Item item, MxtEquipmentSlot.Type slotType) {
        List<MxtEquipmentSlot> slotList = MxtEquipmentSlot.listByType(slotType);
        boolean bl = false;
        for (MxtEquipmentSlot equipmentSlot : slotList) {
            if (this.mxtGetEquippedStack(equipmentSlot).getItem() == item) {
                bl = true;
            }
        }
        return bl;
    }

    @Override
    public boolean hasStackEquipped(EquipmentSlot slot) {
        return false;
    }

    public boolean mxtHasStackEquipped(MxtEquipmentSlot slot) {
        return !this.mxtGetEquippedStack(slot).isEmpty();
    }

    /**
     * @author TheRedBrain
     * @reason custom implementation is used and this one causes crashes
     */
    @Overwrite
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack mxtGetEquippedStack(MxtEquipmentSlot slot) {

        switch (slot.getType()) {
            case HAND:
                return this.mxtPlayerInventory.getMainHandStack();
            case ARMOUR:
                return this.mxtPlayerInventory.mxtGetArmorStack(slot.getEntitySlotId(), this.getActiveLoadout());
            case VANITY_ARMOUR:
                return this.mxtPlayerInventory.mxtGetVanityArmorStack(slot.getEntitySlotId(), this.getActiveLoadout());
            case ARMOUR_DYE:
                return this.mxtPlayerInventory.mxtGetArmorDyeStack(slot.getEntitySlotId(), this.getActiveLoadout());
            case ACCESSORY:
                return this.mxtPlayerInventory.mxtGetAccessoryStack(slot.getEntitySlotId(), this.getActiveLoadout());
            case VANITY_ACCESSORY:
                return this.mxtPlayerInventory.mxtGetVanityAccessoryStack(slot.getEntitySlotId(), this.getActiveLoadout());
            case ACCESSORY_DYE:
                return this.mxtPlayerInventory.mxtGetAccessoryDyeStack(slot.getEntitySlotId(), this.getActiveLoadout());
            case EQUIPMENT:
                return this.mxtPlayerInventory.mxtGetEquipmentStack(slot.getEntitySlotId());
            case EQUIPMENT_DYE:
                return this.mxtPlayerInventory.mxtGetEquipmentDyeStack(slot.getEntitySlotId());
        }
        return ItemStack.EMPTY;
    }

    /**
     * @author TheRedBrain
     * @reason custom implementation is used and this one causes crashes
     */
    @Overwrite
    public boolean isArmorSlot(EquipmentSlot slot) {
        return false;
    }

    public boolean mxtIsArmorSlot(MxtEquipmentSlot slot) {
        return slot.getType() == MxtEquipmentSlot.Type.ARMOUR;
    }

    /**
     * @author TheRedBrain
     * @reason custom implementation is used and this one causes crashes
     */
    @Overwrite
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
    }

    @Override
    public void mxtEquipStack(MxtEquipmentSlot slot, ItemStack stack) {
        this.processEquippedStack(stack);
        if (slot == MxtEquipmentSlot.HAND) {
            this.mxtOnEquipStack(slot, this.mxtPlayerInventory.main.set(this.mxtPlayerInventory.selectedSlot, stack), stack);
        } else if (slot.getType() == MxtEquipmentSlot.Type.ARMOUR) {
            if (this.getActiveLoadout() == 2) {
                this.mxtOnEquipStack(slot, this.mxtPlayerInventory.armor_3.set(slot.getEntitySlotId(), stack), stack);
            } else if (this.getActiveLoadout() == 1) {
                this.mxtOnEquipStack(slot, this.mxtPlayerInventory.armor_2.set(slot.getEntitySlotId(), stack), stack);
            } else {
                this.mxtOnEquipStack(slot, this.mxtPlayerInventory.armor.set(slot.getEntitySlotId(), stack), stack);
            }
        } else if (slot.getType() == MxtEquipmentSlot.Type.ACCESSORY) {
            if (this.getActiveLoadout() == 2) {
                this.mxtOnEquipStack(slot, this.mxtPlayerInventory.accessories_3.set(slot.getEntitySlotId(), stack), stack);
            } else if (this.getActiveLoadout() == 1) {
                this.mxtOnEquipStack(slot, this.mxtPlayerInventory.accessories_2.set(slot.getEntitySlotId(), stack), stack);
            } else {
                this.mxtOnEquipStack(slot, this.mxtPlayerInventory.accessories.set(slot.getEntitySlotId(), stack), stack);
            }
        }
    }

    @Override
    public void setSprinting(boolean sprinting) {
        super.setSprinting(sprinting);
//        EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
//        if (entityAttributeInstance.getModifier(SPRINTING_SPEED_BOOST_ID) != null) {
//            entityAttributeInstance.removeModifier(SPRINTING_SPEED_BOOST);
//        }
//        if (sprinting) {
//            entityAttributeInstance.addTemporaryModifier(SPRINTING_SPEED_BOOST);
//        }
    }

    /**
     * @author TheRedBrain
     * @reason custom implementation is used and this one causes crashes
     */
    @Overwrite
    public boolean giveItemStack(ItemStack stack) {
        return false;
    }

    public boolean mxtGiveItemStack(ItemStack stack) {
        return this.mxtPlayerInventory.insertStack(stack);
    }

    /**
     * @author TheRedBrain
     * @reason custom implementation is used and this one causes crashes
     */
    @Overwrite
    public Iterable<ItemStack> getHandItems() {
        return Lists.newArrayList(this.getMainHandStack());
    }

    /**
     * @author TheRedBrain
     * @reason custom implementation is used and this one causes crashes
     */
    @Overwrite
    public Iterable<ItemStack> getArmorItems() {
        if (this.getActiveLoadout() == 2) {
            return this.mxtPlayerInventory.armor_3;
        } else if (this.getActiveLoadout() == 1) {
            return this.mxtPlayerInventory.armor_2;
        } else {
            return this.mxtPlayerInventory.armor;
        }
    }

    public void mxtOnEquipStack(MxtEquipmentSlot slot, ItemStack oldStack, ItemStack newStack) {
        boolean bl = newStack.isEmpty() && oldStack.isEmpty();
        if (bl || ItemStack.areItemsEqual(oldStack, newStack) || this.firstUpdate) {
            return;
        }
        if (slot.getType() == MxtEquipmentSlot.Type.ARMOUR) {
            this.playEquipSound(newStack);
            this.emitGameEvent(GameEvent.EQUIP);
            this.setActiveInventoryTab(0);
        }
        if (slot.getType() == MxtEquipmentSlot.Type.ACCESSORY) {
            this.playEquipSound(newStack);
            this.emitGameEvent(GameEvent.EQUIP);
            this.setActiveInventoryTab(0);
        }
        if (slot.getType() == MxtEquipmentSlot.Type.EQUIPMENT) {
            this.playEquipSound(newStack);
            this.emitGameEvent(GameEvent.EQUIP);
            this.setActiveInventoryTab(1);
        }
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return pose == EntityPose.SLEEPING ? 0.4f : 2.59f;
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void setAbsorptionAmount(float amount) {}

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public float getAbsorptionAmount() {
        return 0.0F;
    }

    // TODO reactivate when renderer fixed
//    /**
//     * @author TheRedBrain
//     */
//    @Overwrite
//    public NbtCompound getShoulderEntityLeft() {
//        return null;
//    }
//
//    /**
//     * @author TheRedBrain
//     */
//    @Overwrite
//    public void setShoulderEntityLeft(NbtCompound entityNbt) {}
//
//    /**
//     * @author TheRedBrain
//     */
//    @Overwrite
//    public NbtCompound getShoulderEntityRight() {
//        return null;
//    }
//
//    /**
//     * @author TheRedBrain
//     */
//    @Overwrite
//    public void setShoulderEntityRight(NbtCompound entityNbt) {}

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public float getAttackCooldownProgressPerTick() {
        return (float)(0.1);
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public float getLuck() {
        return (float)this.getAttributeValue(EntityAttributesRegistry.MXT_LUCK);
    }

//    /**
//     * @author TheRedBrain
//     */
//    @Overwrite
//    public EntityDimensions getDimensions(EntityPose pose) {
//        return pose == EntityPose.SLEEPING ? MXT_SLEEPING_DIMENSIONS : MXT_STANDING_DIMENSIONS;
//    }
//    @Inject(at = @At("RETURN"), method = "getDimensions", cancellable = true)
//    private void mxtGetDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> info)
//    {
//        info.setReturnValue(info.getReturnValue().scaled(1.8F, 1.8F));
//    }
//
//    /**
//     * @author TheRedBrain
//     */
//    @Overwrite
//    public ImmutableList<EntityPose> getPoses() {
//        return ImmutableList.of(EntityPose.STANDING);
//    }

//    public MxtAbstractPlayerScreenHandler getMxtPlayerScreenHandler() {
//        if (this.getMxtPlayerInventory().getActiveInventoryTab() == 1) {
//            return this.getMxtEquipmentPlayerScreenHandler();
//        } else {
//            if (this.getMxtPlayerInventory().getActiveLoadout() == 2) {
//                return this.getMxtThirdLoadoutPlayerScreenHandler();
//            } else if (this.getMxtPlayerInventory().getActiveLoadout() == 1) {
//                return this.getMxtSecondLoadoutPlayerScreenHandler();
//            } else {
//                return this.getMxtFirstLoadoutPlayerScreenHandler();
//            }
//        }
//    }

//    public Screen mxtGetCurrentPlayerInventoryScreen() {
//        if (this.getMxtPlayerInventory().getActiveInventoryTab() == 1) {
//            return new MxtEquipmentInventoryScreen((PlayerEntity) (Object) this);
//        } else {
//            if (this.getMxtPlayerInventory().getActiveLoadout() == 2) {
//                return new MxtThirdLoadoutInventoryScreen((PlayerEntity) (Object) this);
//            } else if (this.getMxtPlayerInventory().getActiveLoadout() == 1) {
//                return new MxtSecondLoadoutInventoryScreen((PlayerEntity) (Object) this);
//            } else {
//                return new MxtFirstLoadoutInventoryScreen((PlayerEntity) (Object) this);
//            }
//        }
//    }

//    public ScreenHandler mxtGetCurrentPlayerInventoryScreenHandler() {
//        if (this.getMxtPlayerInventory().getActiveInventoryTab() == 1) {
//            return this.getMxtEquipmentPlayerScreenHandler();
//        } else {
//            if (this.getMxtPlayerInventory().getActiveLoadout() == 2) {
//                return this.getMxtThirdLoadoutPlayerScreenHandler();
//            } else if (this.getMxtPlayerInventory().getActiveLoadout() == 1) {
//                return this.getMxtSecondLoadoutPlayerScreenHandler();
//            } else {
//                return this.getMxtFirstLoadoutPlayerScreenHandler();
//            }
//        }
//    }

    public MxtPlayerInventory getMxtPlayerInventory() {
        return this.mxtPlayerInventory;
    }

//    public MxtGearPlayerInventory getMxtGearPlayerInventory() {
//        return this.mxtGearPlayerInventory;
//    }

//    public MxtEquipmentPlayerScreenHandler getMxtEquipmentPlayerScreenHandler() {
//        return this.mxtEquipmentPlayerScreenHandler;
//    }
//
//    public MxtFirstLoadoutPlayerScreenHandler getMxtFirstLoadoutPlayerScreenHandler() {
//        return this.mxtFirstLoadoutPlayerScreenHandler;
//    }
//
//    public MxtSecondLoadoutPlayerScreenHandler getMxtSecondLoadoutPlayerScreenHandler() {
//        return this.mxtSecondLoadoutPlayerScreenHandler;
//    }
//
    public MxtPlayerInventoryScreenHandler getMxtPlayerInventoryScreenHandler() {
        return this.mxtPlayerInventoryScreenHandler;
    }

    public int getActiveLoadout() {
//        MinecraftXTerraria.LOGGER.info("getCurrentLoadout: " + this.activeLoadout);
        return activeLoadout;
    }

    public void setActiveLoadout(int activeLoadout) {
        this.activeLoadout = activeLoadout;
//        MinecraftXTerraria.LOGGER.info("setCurrentLoadout to: " + this.activeLoadout);
    }

    /**
     *
     * @return 0 -> loadouts, 1 -> equipment, 2 -> housing
     */
    public int getActiveInventoryTab() {
        return activeInventoryTab;
    }

    /**
     *
     * @param activeInventoryTab:  0 -> loadouts, 1 -> equipment, 2 -> housing
     */
    public void setActiveInventoryTab(int activeInventoryTab) {
        if (activeInventoryTab < 0 || activeInventoryTab > 2) {
            activeInventoryTab = 0;
        }
        this.activeInventoryTab = activeInventoryTab;
    }
}
