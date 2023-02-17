package com.github.theredbrain.minecraftxterraria.mixin.entity;

import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

    @Shadow
    private int pickupDelay;

    @Shadow
    private UUID owner;

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public ItemStack getStack() {
        throw new AssertionError();
    }

    /**
     * @author TheRedBrain
     * @reason insert into custom inventory
     */
    @Overwrite
    public void onPlayerCollision(PlayerEntity player) {
        if (this.world.isClient) {
            return;
        }
        ItemStack itemStack = this.getStack();
        Item item = itemStack.getItem();
        int i = itemStack.getCount();
        if (this.pickupDelay == 0 && (this.owner == null || this.owner.equals(player.getUuid())) && ((DuckPlayerEntityMixin)player).getMxtPlayerInventory().insertStack(itemStack)) {
            player.sendPickup(this, i);
            if (itemStack.isEmpty()) {
                this.discard();
                itemStack.setCount(i);
            }
            player.increaseStat(Stats.PICKED_UP.getOrCreateStat(item), i);
            player.triggerItemPickedUpByEntityCriteria((ItemEntity) (Object) this);
        }
    }
}
