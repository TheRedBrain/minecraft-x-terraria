package com.github.theredbrain.minecraftxterraria.mixin.client.network;

import com.github.theredbrain.minecraftxterraria.client.network.DuckClientPlayerEntityMixin;
import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity implements DuckClientPlayerEntityMixin {

    @Shadow
    @Final
    public ClientPlayNetworkHandler networkHandler;

    @Shadow
    private boolean healthInitialized;


    private boolean manaInitialized;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public boolean dropSelectedItem(boolean entireStack) {
        PlayerActionC2SPacket.Action action = entireStack ? PlayerActionC2SPacket.Action.DROP_ALL_ITEMS : PlayerActionC2SPacket.Action.DROP_ITEM;
        ItemStack itemStack = ((DuckPlayerEntityMixin)this).getMxtPlayerInventory().dropSelectedItem(entireStack);
        this.networkHandler.sendPacket(new PlayerActionC2SPacket(action, BlockPos.ORIGIN, Direction.DOWN));
        return !itemStack.isEmpty();
    }

    public void mxtUpdateHealth(float health, float healthRegenerationTime, float healthEffectiveRegenerationTime, float healthRegenerationCounter) {
        if (this.healthInitialized) {
            this.setHealth(health);
            ((DuckPlayerEntityMixin) this).mxtSetHealthRegenerationTime(healthRegenerationTime);
            ((DuckPlayerEntityMixin) this).mxtSetEffectiveHealthRegenerationTime(healthEffectiveRegenerationTime);
            ((DuckPlayerEntityMixin) this).mxtSetHealthRegenerationCounter(healthRegenerationCounter);

        } else {
            this.setHealth(health);
            ((DuckPlayerEntityMixin) this).mxtSetHealthRegenerationTime(healthRegenerationTime);
            ((DuckPlayerEntityMixin) this).mxtSetEffectiveHealthRegenerationTime(healthEffectiveRegenerationTime);
            ((DuckPlayerEntityMixin) this).mxtSetHealthRegenerationCounter(healthRegenerationCounter);
            this.healthInitialized = true;
        }
    }

    public void mxtUpdateMana(float mana, float manaRegenerationDelay, float manaRegenerationCounter) {
        if (this.manaInitialized) {
            ((DuckPlayerEntityMixin) this).mxtSetMana(mana);
            ((DuckPlayerEntityMixin) this).mxtSetManaRegenerationDelay(manaRegenerationDelay);
            ((DuckPlayerEntityMixin) this).mxtSetManaRegenerationCounter(manaRegenerationCounter);

        } else {
            ((DuckPlayerEntityMixin) this).mxtSetMana(mana);
            ((DuckPlayerEntityMixin) this).mxtSetManaRegenerationDelay(manaRegenerationDelay);
            ((DuckPlayerEntityMixin) this).mxtSetManaRegenerationCounter(manaRegenerationCounter);
            this.manaInitialized = true;
        }
    }
}
