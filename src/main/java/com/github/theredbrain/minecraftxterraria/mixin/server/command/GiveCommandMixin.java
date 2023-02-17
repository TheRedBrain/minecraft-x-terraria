package com.github.theredbrain.minecraftxterraria.mixin.server.command;

import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.GiveCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Collection;

@Mixin(GiveCommand.class)
public class GiveCommandMixin {

    /**
     * @author TheRedBrain
     * @reason use custom inventory
     */
    @Overwrite
    private static int execute(ServerCommandSource source, ItemStackArgument item, Collection<ServerPlayerEntity> targets, int count) throws CommandSyntaxException {
        int i = item.getItem().getMaxCount();
        int j = i * 100;
        if (count > j) {
            source.sendError(Text.translatable("commands.give.failed.toomanyitems", j, item.createStack(count, false).toHoverableText()));
            return 0;
        }
        for (ServerPlayerEntity serverPlayerEntity : targets) {
            int k = count;
            while (k > 0) {
                ItemEntity itemEntity;
                int l = Math.min(i, k);
                k -= l;
                ItemStack itemStack = item.createStack(l, false);
                boolean bl = ((DuckPlayerEntityMixin)serverPlayerEntity).getMxtPlayerInventory().insertStack(itemStack);
                if (!bl || !itemStack.isEmpty()) {
                    itemEntity = serverPlayerEntity.dropItem(itemStack, false);
                    if (itemEntity == null) continue;
                    itemEntity.resetPickupDelay();
                    itemEntity.setOwner(serverPlayerEntity.getUuid());
                    continue;
                }
                itemStack.setCount(1);
                itemEntity = serverPlayerEntity.dropItem(itemStack, false);
                if (itemEntity != null) {
                    itemEntity.setDespawnImmediately();
                }
                serverPlayerEntity.world.playSound(null, serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, ((serverPlayerEntity.getRandom().nextFloat() - serverPlayerEntity.getRandom().nextFloat()) * 0.7f + 1.0f) * 2.0f);
                serverPlayerEntity.currentScreenHandler.sendContentUpdates();
            }
        }
        if (targets.size() == 1) {
            source.sendFeedback(Text.translatable("commands.give.success.single", count, item.createStack(count, false).toHoverableText(), targets.iterator().next().getDisplayName()), true);
        } else {
            source.sendFeedback(Text.translatable("commands.give.success.single", count, item.createStack(count, false).toHoverableText(), targets.size()), true);
        }
        return targets.size();
    }
}
