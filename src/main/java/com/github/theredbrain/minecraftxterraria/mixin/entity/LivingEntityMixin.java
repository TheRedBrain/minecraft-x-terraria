package com.github.theredbrain.minecraftxterraria.mixin.entity;

import com.github.theredbrain.minecraftxterraria.entity.DuckLivingEntityMixin;
import com.github.theredbrain.minecraftxterraria.entity.MxtEquipmentSlot;
import com.github.theredbrain.minecraftxterraria.item.DuckItemStackMixin;
import com.github.theredbrain.minecraftxterraria.network.packet.s2c.play.MxtEntityEquipmentUpdateS2CPacket;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements DuckLivingEntityMixin {

    @Shadow // 173
    @Final
    protected static TrackedData<Byte> LIVING_FLAGS;

    @Shadow
    @Final
    private static TrackedData<Float> HEALTH;

    @Shadow
    @Final
    private static TrackedData<Integer> POTION_SWIRLS_COLOR;

    @Shadow
    @Final
    private static TrackedData<Boolean> POTION_SWIRLS_AMBIENT;

    @Shadow // 179
    @Final
    private static TrackedData<Optional<BlockPos>> SLEEPING_POSITION;

    @Shadow // 186
    @Final
    @Mutable
    private final DefaultedList<ItemStack> syncedHandStacks = initSyncedHandStacks();

    @Shadow // 187
    @Final
    @Mutable
    private final DefaultedList<ItemStack> syncedArmorStacks = initSyncedArmorStacks();

    @Shadow // 199
    public float handSwingProgress;

    @Shadow // 207
    public float bodyYaw;

    @Shadow // 208
    public float prevBodyYaw;

    @Shadow // 209
    public float headYaw;

    @Shadow // 210
    public float prevHeadYaw;

    @Shadow // 221
    protected float prevStepBobbingAmount;

    @Shadow // 222
    protected float stepBobbingAmount;

    @Shadow // 223
    protected float lookDirection;

    @Shadow // 251
    protected int roll;

    @Shadow // 1587
    public DamageTracker getDamageTracker() {
        throw new AssertionError();
    }

    @Shadow // 1811
    public AttributeContainer getAttributes() {
        throw new AssertionError();
    }

    @Shadow // 2323
    public boolean areItemsDifferent(ItemStack stack, ItemStack stack2) {
        throw new AssertionError();
    }

    @Shadow // 2381
    protected float turnHead(float bodyRotation, float headRotation) {
        throw new AssertionError();
    }

    @Shadow // 2403
    public void tickMovement() {
        throw new AssertionError();
    }

    @Shadow // 2748
    private void tickActiveItemStack() {
        throw new AssertionError();
    }

    @Shadow // 2776
    private void updateLeaningPitch() {
        throw new AssertionError();
    }

    @Shadow // 2930
    public boolean isFallFlying() {
        throw new AssertionError();
    }

    @Shadow // 3024
    public boolean isSleeping() {
        throw new AssertionError();
    }

    @Shadow // 3043
    private void setPositionInBed(BlockPos pos) {
        throw new AssertionError();
    }

    @Shadow // 3047
    private boolean isSleepingInBed() {
        throw new AssertionError();
    }

    @Shadow // 3056
    public void wakeUp() {
        throw new AssertionError();
    }

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    private final DefaultedList<ItemStack> syncedVanityArmorStacks = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private final DefaultedList<ItemStack> syncedArmorDyeStacks = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private final DefaultedList<ItemStack> syncedEquipmentStacks = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private final DefaultedList<ItemStack> syncedEquipmentDyeStacks = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private final DefaultedList<ItemStack> syncedAccessoryStacks = DefaultedList.ofSize(7, ItemStack.EMPTY);
    private final DefaultedList<ItemStack> syncedVanityAccessoryStacks = DefaultedList.ofSize(7, ItemStack.EMPTY);
    private final DefaultedList<ItemStack> syncedAccessoryDyeStacks = DefaultedList.ofSize(7, ItemStack.EMPTY);

    DefaultedList<ItemStack> initSyncedHandStacks() {
        return DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    DefaultedList<ItemStack> initSyncedArmorStacks() {
        return DefaultedList.ofSize(3, ItemStack.EMPTY);
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void initDataTracker() {
        this.dataTracker.startTracking(LIVING_FLAGS, (byte)0);
        this.dataTracker.startTracking(POTION_SWIRLS_COLOR, 0);
        this.dataTracker.startTracking(POTION_SWIRLS_AMBIENT, false);
//        this.dataTracker.startTracking(STUCK_ARROW_COUNT, 0);
//        this.dataTracker.startTracking(STINGER_COUNT, 0);
        this.dataTracker.startTracking(HEALTH, Float.valueOf(1.0f));
        this.dataTracker.startTracking(SLEEPING_POSITION, Optional.empty());
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void tick() {
        super.tick();
        this.tickActiveItemStack();
        this.updateLeaningPitch();
        if (!this.world.isClient) {
//            int j;
//            int i = this.getStuckArrowCount();
//            if (i > 0) {
//                if (this.stuckArrowTimer <= 0) {
//                    this.stuckArrowTimer = 20 * (30 - i);
//                }
//                --this.stuckArrowTimer;
//                if (this.stuckArrowTimer <= 0) {
//                    this.setStuckArrowCount(i - 1);
//                }
//            }
//            if ((j = this.getStingerCount()) > 0) {
//                if (this.stuckStingerTimer <= 0) {
//                    this.stuckStingerTimer = 20 * (30 - j);
//                }
//                --this.stuckStingerTimer;
//                if (this.stuckStingerTimer <= 0) {
//                    this.setStingerCount(j - 1);
//                }
//            }
            this.mxtSendEquipmentChanges();
            if (this.age % 20 == 0) {
                this.getDamageTracker().update();
            }
            if (this.isSleeping() && !this.isSleepingInBed()) {
                this.wakeUp();
            }
        }
        if (!this.isRemoved()) {
            this.tickMovement();
        }
        double d = this.getX() - this.prevX;
        double e = this.getZ() - this.prevZ;
        float f = (float)(d * d + e * e);
        float g = this.bodyYaw;
        float h = 0.0f;
        this.prevStepBobbingAmount = this.stepBobbingAmount;
        float k = 0.0f;
        if (f > 0.0025000002f) {
            k = 1.0f;
            h = (float)Math.sqrt(f) * 3.0f;
            float l = (float) MathHelper.atan2(e, d) * 57.295776f - 90.0f;
            float m = MathHelper.abs(MathHelper.wrapDegrees(this.getYaw()) - l);
            g = 95.0f < m && m < 265.0f ? l - 180.0f : l;
        }
        if (this.handSwingProgress > 0.0f) {
            g = this.getYaw();
        }
        if (!this.onGround) {
            k = 0.0f;
        }
        this.stepBobbingAmount += (k - this.stepBobbingAmount) * 0.3f;
        this.world.getProfiler().push("headTurn");
        h = this.turnHead(g, h);
        this.world.getProfiler().pop();
        this.world.getProfiler().push("rangeChecks");
        while (this.getYaw() - this.prevYaw < -180.0f) {
            this.prevYaw -= 360.0f;
        }
        while (this.getYaw() - this.prevYaw >= 180.0f) {
            this.prevYaw += 360.0f;
        }
        while (this.bodyYaw - this.prevBodyYaw < -180.0f) {
            this.prevBodyYaw -= 360.0f;
        }
        while (this.bodyYaw - this.prevBodyYaw >= 180.0f) {
            this.prevBodyYaw += 360.0f;
        }
        while (this.getPitch() - this.prevPitch < -180.0f) {
            this.prevPitch -= 360.0f;
        }
        while (this.getPitch() - this.prevPitch >= 180.0f) {
            this.prevPitch += 360.0f;
        }
        while (this.headYaw - this.prevHeadYaw < -180.0f) {
            this.prevHeadYaw -= 360.0f;
        }
        while (this.headYaw - this.prevHeadYaw >= 180.0f) {
            this.prevHeadYaw += 360.0f;
        }
        this.world.getProfiler().pop();
        this.lookDirection += h;
        this.roll = this.isFallFlying() ? ++this.roll : 0;
        if (this.isSleeping()) {
            this.setPitch(0.0f);
        }
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public final int getStuckArrowCount() {
        return 0;
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public final void setStuckArrowCount(int stuckArrowCount) {}

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public final int getStingerCount() {
        return 0;
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public final void setStingerCount(int stingerCount) {}

    /**
     * Sends equipment changes to nearby players.
     */
    private void mxtSendEquipmentChanges() {
        Map<MxtEquipmentSlot, ItemStack> map = this.mxtGetEquipmentChanges();
        if (map != null) {
            if (!map.isEmpty()) {
                this.mxtSendEquipmentChanges(map);
            }
        }
    }

    private Map<MxtEquipmentSlot, ItemStack> mxtGetEquipmentChanges() {
        EnumMap<MxtEquipmentSlot, ItemStack> map = null;
        block4: for (MxtEquipmentSlot equipmentSlot : MxtEquipmentSlot.values()) {
            ItemStack itemStack;
            switch (equipmentSlot.getType()) {
                case HAND: {
                    itemStack = this.mxtGetSyncedHandStack(equipmentSlot);
                    break;
                }
                case ARMOUR: {
                    itemStack = this.mxtGetSyncedArmourStack(equipmentSlot);
                    break;
                }
                case VANITY_ARMOUR: {
                    itemStack = this.mxtGetSyncedVanityArmourStack(equipmentSlot);
                    break;
                }
                case ARMOUR_DYE: {
                    itemStack = this.mxtGetSyncedArmourDyeStack(equipmentSlot);
                    break;
                }
                case EQUIPMENT: {
                    itemStack = this.mxtGetSyncedEquipmentStack(equipmentSlot);
                    break;
                }
                case EQUIPMENT_DYE: {
                    itemStack = this.mxtGetSyncedEquipmentDyeStack(equipmentSlot);
                    break;
                }
                case ACCESSORY: {
                    itemStack = this.mxtGetSyncedAccessoryStack(equipmentSlot);
                    break;
                }
                case VANITY_ACCESSORY: {
                    itemStack = this.mxtGetSyncedVanityAccessoryStack(equipmentSlot);
                    break;
                }
                case ACCESSORY_DYE: {
                    itemStack = this.mxtGetSyncedAccessoryDyeStack(equipmentSlot);
                    break;
                }
                default: {
                    continue block4;
                }
            }
            ItemStack itemStack2 = this.mxtGetEquippedStack(equipmentSlot);
            if (!this.areItemsDifferent(itemStack, itemStack2)) continue;
            if (map == null) {
                map = Maps.newEnumMap(MxtEquipmentSlot.class);
            }
            map.put(equipmentSlot, itemStack2);
            if (!itemStack.isEmpty()) {
                this.getAttributes().removeModifiers(((DuckItemStackMixin) (Object) itemStack).mxtGetAttributeModifiers(equipmentSlot));
            }
            if (itemStack2.isEmpty()) continue;
            this.getAttributes().addTemporaryModifiers(((DuckItemStackMixin) (Object) itemStack2).mxtGetAttributeModifiers(equipmentSlot));
        }
        return map;
    }

    private void mxtSendEquipmentChanges(Map<MxtEquipmentSlot, ItemStack> equipmentChanges) {
        ArrayList<Pair<MxtEquipmentSlot, ItemStack>> list = Lists.newArrayListWithCapacity(equipmentChanges.size());
        equipmentChanges.forEach((slot, stack) -> {
            ItemStack itemStack = stack.copy();
            list.add(Pair.of(slot, itemStack));
            switch (slot.getType()) {
                case HAND: {
                    this.mxtSetSyncedHandStack((MxtEquipmentSlot)((Object)slot), itemStack);
                    break;
                }
                case ARMOUR: {
                    this.mxtSetSyncedArmourStack((MxtEquipmentSlot)((Object)slot), itemStack);
                }
                case VANITY_ARMOUR: {
                    this.mxtSetSyncedVanityArmourStack((MxtEquipmentSlot)((Object)slot), itemStack);
                }
                case ARMOUR_DYE: {
                    this.mxtSetSyncedArmourDyeStack((MxtEquipmentSlot)((Object)slot), itemStack);
                }
                case EQUIPMENT: {
                    this.mxtSetSyncedEquipmentStack((MxtEquipmentSlot)((Object)slot), itemStack);
                }
                case EQUIPMENT_DYE: {
                    this.mxtSetSyncedEquipmentDyeStack((MxtEquipmentSlot)((Object)slot), itemStack);
                }
                case ACCESSORY: {
                    this.mxtSetSyncedAccessoryStack((MxtEquipmentSlot)((Object)slot), itemStack);
                }
                case VANITY_ACCESSORY: {
                    this.mxtSetSyncedVanityAccessoryStack((MxtEquipmentSlot)((Object)slot), itemStack);
                }
                case ACCESSORY_DYE: {
                    this.mxtSetSyncedAccessoryDyeStack((MxtEquipmentSlot)((Object)slot), itemStack);
                }
            }
        });
        ((ServerWorld)this.world).getChunkManager().sendToOtherNearbyPlayers(this, new MxtEntityEquipmentUpdateS2CPacket(this.getId(), list));
    }

    private ItemStack mxtGetSyncedHandStack(MxtEquipmentSlot slot) {
        return this.syncedHandStacks.get(slot.getEntitySlotId());
    }

    private void mxtSetSyncedHandStack(MxtEquipmentSlot slot, ItemStack stack) {
        this.syncedHandStacks.set(slot.getEntitySlotId(), stack);
    }

    private ItemStack mxtGetSyncedArmourStack(MxtEquipmentSlot slot) {
        return this.syncedArmorStacks.get(slot.getEntitySlotId());
    }

    private void mxtSetSyncedArmourStack(MxtEquipmentSlot slot, ItemStack armor) {
        this.syncedArmorStacks.set(slot.getEntitySlotId(), armor);
    }

    private ItemStack mxtGetSyncedVanityArmourStack(MxtEquipmentSlot slot) {
        return this.syncedVanityArmorStacks.get(slot.getEntitySlotId());
    }

    private void mxtSetSyncedVanityArmourStack(MxtEquipmentSlot slot, ItemStack armor) {
        this.syncedVanityArmorStacks.set(slot.getEntitySlotId(), armor);
    }

    private ItemStack mxtGetSyncedArmourDyeStack(MxtEquipmentSlot slot) {
        return this.syncedArmorDyeStacks.get(slot.getEntitySlotId());
    }

    private void mxtSetSyncedArmourDyeStack(MxtEquipmentSlot slot, ItemStack dye) {
        this.syncedArmorDyeStacks.set(slot.getEntitySlotId(), dye);
    }

    private ItemStack mxtGetSyncedEquipmentStack(MxtEquipmentSlot slot) {
        return this.syncedEquipmentStacks.get(slot.getEntitySlotId());
    }

    private void mxtSetSyncedEquipmentStack(MxtEquipmentSlot slot, ItemStack equipment) {
        this.syncedEquipmentStacks.set(slot.getEntitySlotId(), equipment);
    }

    private ItemStack mxtGetSyncedEquipmentDyeStack(MxtEquipmentSlot slot) {
        return this.syncedEquipmentDyeStacks.get(slot.getEntitySlotId());
    }

    private void mxtSetSyncedEquipmentDyeStack(MxtEquipmentSlot slot, ItemStack dye) {
        this.syncedEquipmentDyeStacks.set(slot.getEntitySlotId(), dye);
    }

    private ItemStack mxtGetSyncedAccessoryStack(MxtEquipmentSlot slot) {
        return this.syncedAccessoryStacks.get(slot.getEntitySlotId());
    }

    private void mxtSetSyncedAccessoryStack(MxtEquipmentSlot slot, ItemStack accessory) {
        this.syncedAccessoryStacks.set(slot.getEntitySlotId(), accessory);
    }

    private ItemStack mxtGetSyncedVanityAccessoryStack(MxtEquipmentSlot slot) {
        return this.syncedVanityAccessoryStacks.get(slot.getEntitySlotId());
    }

    private void mxtSetSyncedVanityAccessoryStack(MxtEquipmentSlot slot, ItemStack accessory) {
        this.syncedVanityAccessoryStacks.set(slot.getEntitySlotId(), accessory);
    }

    private ItemStack mxtGetSyncedAccessoryDyeStack(MxtEquipmentSlot slot) {
        return this.syncedAccessoryDyeStacks.get(slot.getEntitySlotId());
    }

    private void mxtSetSyncedAccessoryDyeStack(MxtEquipmentSlot slot, ItemStack dye) {
        this.syncedAccessoryDyeStacks.set(slot.getEntitySlotId(), dye);
    }

    public abstract ItemStack mxtGetEquippedStack(MxtEquipmentSlot var1);

    @Override
    public abstract void mxtEquipStack(MxtEquipmentSlot var1, ItemStack var2);
}
