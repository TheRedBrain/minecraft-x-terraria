package com.github.theredbrain.minecraftxterraria.mixin.world.entity;
//
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.util.math.Box;
//import net.minecraft.world.entity.EntityLike;
//import net.minecraft.world.entity.EntityTrackingSection;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Redirect;
//
//@Mixin(EntityTrackingSection.class)
//public class EntityTrackingSectionMixin {

//    @Redirect(method = "forEach(Lnet/minecraft/util/math/Box;Lnet/minecraft/util/function/LazyIterationConsumer;)Lnet/minecraft/util/function/LazyIterationConsumer$NextIteration;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityLike;getBoundingBox()Lnet/minecraft/util/math/Box;"))
//    private Box pehkui$forEach$getBoundingBox(EntityLike obj)
//    {
//        final Box bounds = obj.getBoundingBox();
//
//        if (obj instanceof Entity) {
//            final Entity entity = (Entity) obj;
//            if (entity instanceof PlayerEntity) {
//
//                final float interactionWidth = 1.8F;
//                final float interactionHeight = 1.8F;
//
//                if (interactionWidth != 1.0F || interactionHeight != 1.0F) {
//                    final double scaledXLength = bounds.getXLength() * 0.5D * (interactionWidth - 1.0F);
//                    final double scaledYLength = bounds.getYLength() * 0.5D * (interactionHeight - 1.0F);
//                    final double scaledZLength = bounds.getZLength() * 0.5D * (interactionWidth - 1.0F);
//
//                    return bounds.expand(scaledXLength, scaledYLength, scaledZLength);
//                }
//            }
//        }
//
//        return bounds;
//    }
//
//    @Redirect(method = "forEach(Lnet/minecraft/util/TypeFilter;Lnet/minecraft/util/math/Box;Lnet/minecraft/util/function/LazyIterationConsumer;)Lnet/minecraft/util/function/LazyIterationConsumer$NextIteration;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityLike;getBoundingBox()Lnet/minecraft/util/math/Box;"))
//    private Box pehkui$forEach$getBoundingBox$filtered(EntityLike obj)
//    {
//        final Box bounds = obj.getBoundingBox();
//
//        if (obj instanceof Entity) {
//            final Entity entity = (Entity) obj;
//            if (entity instanceof PlayerEntity) {
//
//                final float interactionWidth = 1.8F;
//                final float interactionHeight = 1.8F;
//
//                if (interactionWidth != 1.0F || interactionHeight != 1.0F) {
//                    final double scaledXLength = bounds.getXLength() * 0.5D * (interactionWidth - 1.0F);
//                    final double scaledYLength = bounds.getYLength() * 0.5D * (interactionHeight - 1.0F);
//                    final double scaledZLength = bounds.getZLength() * 0.5D * (interactionWidth - 1.0F);
//
//                    return bounds.expand(scaledXLength, scaledYLength, scaledZLength);
//                }
//            }
//        }
//
//        return bounds;
//    }
//}
