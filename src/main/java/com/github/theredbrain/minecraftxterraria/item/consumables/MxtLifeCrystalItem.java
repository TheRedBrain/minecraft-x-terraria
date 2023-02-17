package com.github.theredbrain.minecraftxterraria.item.consumables;

import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.minecraftxterraria.item.MxtUsableItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MxtLifeCrystalItem extends MxtUsableItem {

    public MxtLifeCrystalItem(Settings settings, int baseRarity, int researchAmount, int sellPrice, boolean autoSwing, int useTime) {
        super(settings, baseRarity, researchAmount, sellPrice, autoSwing, useTime);
    }

    @Override
    public TypedActionResult<ItemStack> mxtUse(World world, PlayerEntity player) {
        ItemStack itemStack = player.getMainHandStack();
//        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
//        user.getItemCooldownManager().set(this, 20);
        if (!world.isClient) {
//            EnderPearlEntity enderPearlEntity = new EnderPearlEntity(world, user);
//            enderPearlEntity.setItem(itemStack);
//            enderPearlEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
//            world.spawnEntity(enderPearlEntity);
        }
//        user.incrementStat(Stats.USED.getOrCreateStat(this));
        ((DuckPlayerEntityMixin) player).mxtAddLifeCrystalsConsumed(1);
        player.heal(20);
        player.getMainHandStack().decrement(1);
//        if (!user.getAbilities().creativeMode) {
//            itemStack.decrement(1);
//        }
        return TypedActionResult.success(itemStack, world.isClient());

//        if (!world.isClient() && player.getMainHandStack().getItem() == this) {
//            ((DuckPlayerEntityMixin) player).mxtAddLifeCrystalsConsumed(1);
//            player.heal(20);
//            player.getMainHandStack().decrement(1);
//        }
    }

    @Override
    public boolean mxtCanBeUsed(World world, PlayerEntity player) {
        if (!world.isClient()) {
            return ((DuckPlayerEntityMixin) player).mxtGetLifeCrystalsConsumed() < 15;
        }
        return false;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        return super.useOnEntity(stack, user, entity, hand);
    }
}
