package com.github.theredbrain.minecraftxterraria.mixin.client.network;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {

    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public float getFovMultiplier() {
        float f = 1.0f;
//        if (this.getAbilities().flying) {
//            f *= 1.1f;
//        }
//        if (this.getAbilities().getWalkSpeed() == 0.0f || Float.isNaN(f *= ((float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) / this.getAbilities().getWalkSpeed() + 1.0f) / 2.0f) || Float.isInfinite(f)) {
//            f = 1.0f;
//        }
//        ItemStack itemStack = this.getActiveItem();
//        if (this.isUsingItem()) {
//            if (itemStack.isOf(Items.BOW)) {
//                int i = this.getItemUseTime();
//                float g = (float)i / 20.0f;
//                g = g > 1.0f ? 1.0f : (g *= g);
//                f *= 1.0f - g * 0.15f;
//            } else if (MinecraftClient.getInstance().options.getPerspective().isFirstPerson() && this.isUsingSpyglass()) {
//                return 0.1f;
//            }
//        }
        return MathHelper.lerp(MinecraftClient.getInstance().options.getFovEffectScale().getValue().floatValue(), 1.0f, f);
    }
}
