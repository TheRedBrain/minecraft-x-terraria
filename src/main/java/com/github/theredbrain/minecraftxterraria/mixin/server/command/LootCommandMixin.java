package com.github.theredbrain.minecraftxterraria.mixin.server.command;

import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.LootCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(LootCommand.class)
public class LootCommandMixin {

    /**
     * @author TheRedBrain
     * @reason use custom inventory
     */
    @Overwrite
    private static int executeGive(Collection<ServerPlayerEntity> players, List<ItemStack> stacks, LootCommand.FeedbackMessage messageSender) throws CommandSyntaxException {
        ArrayList<ItemStack> list = Lists.newArrayListWithCapacity(stacks.size());
        for (ItemStack itemStack : stacks) {
            for (ServerPlayerEntity serverPlayerEntity : players) {
                if (!((DuckPlayerEntityMixin)serverPlayerEntity).getMxtPlayerInventory().insertStack(itemStack.copy())) continue;
                list.add(itemStack);
            }
        }
        messageSender.accept(list);
        return list.size();
    }
}
