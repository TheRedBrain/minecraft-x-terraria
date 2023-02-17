package com.github.theredbrain.minecraftxterraria.mixin.entity;

import com.github.theredbrain.minecraftxterraria.entity.DuckEntityMixin;
import com.github.theredbrain.minecraftxterraria.entity.MxtEquipmentSlot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public class EntityMixin implements DuckEntityMixin {

    @Shadow
    public World world;

    @Shadow
    public boolean noClip;

    @Shadow
    protected boolean firstUpdate;

    @Shadow
    public EntityDimensions dimensions;

    @Shadow
    public float standingEyeHeight;

    @Shadow
    public EntityPose getPose() {
        throw new AssertionError();
    } // 583

    @Shadow
    public final void setPosition(Vec3d pos) {
        throw new AssertionError();
    } // 640

    @Shadow
    protected void refreshPosition() {
        throw new AssertionError();
    } // 649

    @Shadow
    protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        throw new AssertionError();
    } // 3979

    @Shadow
    public EntityDimensions getDimensions(EntityPose pose) {
        throw new AssertionError();
} // 4609

    @Shadow
    public Vec3d getPos() {
        throw new AssertionError();
    } // 4620

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void calculateDimensions() {
        boolean bl;
        EntityDimensions entityDimensions2;
        EntityDimensions entityDimensions = this.dimensions;
        EntityPose entityPose = this.getPose();
        this.dimensions = entityDimensions2 = this.getDimensions(entityPose);
        this.standingEyeHeight = this.getEyeHeight(entityPose, entityDimensions2);
        this.refreshPosition();
        boolean bl2 = bl = (double)entityDimensions2.width <= 4.0 && (double)entityDimensions2.height <= 4.0;
        if (!(this.world.isClient || this.firstUpdate || this.noClip || !bl || !(entityDimensions2.width > entityDimensions.width) && !(entityDimensions2.height > entityDimensions.height))) {
            Vec3d vec3d = this.getPos().add(0.0, (double)entityDimensions.height / 2.0, 0.0);
            double d = (double)Math.max(0.0f, entityDimensions2.width - entityDimensions.width) + 1.0E-6;
            double e = (double)Math.max(0.0f, entityDimensions2.height - entityDimensions.height) + 1.0E-6;
            VoxelShape voxelShape = VoxelShapes.cuboid(Box.of(vec3d, d, e, d));
            this.world.findClosestCollision((Entity) (Object) this, voxelShape, vec3d, entityDimensions2.width, entityDimensions2.height, entityDimensions2.width).ifPresent(pos -> this.setPosition(pos.add(0.0, (double)(-entityDimensions.height) / 2.0, 0.0)));
        }
    }

    public void mxtEquipStack(MxtEquipmentSlot slot, ItemStack stack) {
    }
}
