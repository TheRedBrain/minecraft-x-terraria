package com.github.theredbrain.minecraftxterraria.mixin.predicate;

import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.NbtPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(NbtPredicate.class)
public class NbtPredicateMixin {

    /**
     * @author TheRedBrain
     * @reason use custom inventory
     */
    @Overwrite
    public static NbtCompound entityToNbt(Entity entity) {
        ItemStack itemStack;
        NbtCompound nbtCompound = entity.writeNbt(new NbtCompound());
        if (entity instanceof PlayerEntity && !(itemStack = ((DuckPlayerEntityMixin)entity).getMxtPlayerInventory().getMainHandStack()).isEmpty()) {
            nbtCompound.put("SelectedItem", itemStack.writeNbt(new NbtCompound()));
        }
        return nbtCompound;
    }
}
