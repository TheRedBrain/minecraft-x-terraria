package com.github.theredbrain.minecraftxterraria.mixin.server.command;
//
//import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
//import com.mojang.brigadier.exceptions.CommandSyntaxException;
//import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
//import net.minecraft.item.ItemStack;
//import net.minecraft.server.command.ClearCommand;
//import net.minecraft.server.command.ServerCommandSource;
//import net.minecraft.server.network.ServerPlayerEntity;
//import net.minecraft.text.Text;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Overwrite;
//import org.spongepowered.asm.mixin.Shadow;
//
//import java.util.Collection;
//import java.util.function.Predicate;
//
//@Mixin(ClearCommand.class)
//public class ClearCommandMixin {
//
//    @Shadow
//    @Final
//    private static DynamicCommandExceptionType FAILED_SINGLE_EXCEPTION;
//
//    @Shadow
//    @Final
//    private static DynamicCommandExceptionType FAILED_MULTIPLE_EXCEPTION;
//
//    /**
//     * @author TheRedBrain
//     * @reason use custom inventory
//     */
//    @Overwrite
//    private static int execute(ServerCommandSource source, Collection<ServerPlayerEntity> targets, Predicate<ItemStack> item, int maxCount) throws CommandSyntaxException {
//        int i = 0;
//        for (ServerPlayerEntity serverPlayerEntity : targets) {
//            i += ((DuckPlayerEntityMixin)serverPlayerEntity).getMxtPlayerInventory().remove(item, maxCount, ((DuckPlayerEntityMixin)serverPlayerEntity).getMxtPlayerScreenHandler().getCraftingInput());
//            serverPlayerEntity.currentScreenHandler.sendContentUpdates();
//            ((DuckPlayerEntityMixin)serverPlayerEntity).getMxtPlayerScreenHandler().onContentChanged(((DuckPlayerEntityMixin)serverPlayerEntity).getMxtPlayerInventory());
//        }
//        if (i == 0) {
//            if (targets.size() == 1) {
//                throw FAILED_SINGLE_EXCEPTION.create(targets.iterator().next().getName());
//            }
//            throw FAILED_MULTIPLE_EXCEPTION.create(targets.size());
//        }
//        if (maxCount == 0) {
//            if (targets.size() == 1) {
//                source.sendFeedback(Text.translatable("commands.clear.test.single", i, targets.iterator().next().getDisplayName()), true);
//            } else {
//                source.sendFeedback(Text.translatable("commands.clear.test.multiple", i, targets.size()), true);
//            }
//        } else if (targets.size() == 1) {
//            source.sendFeedback(Text.translatable("commands.clear.success.single", i, targets.iterator().next().getDisplayName()), true);
//        } else {
//            source.sendFeedback(Text.translatable("commands.clear.success.multiple", i, targets.size()), true);
//        }
//        return i;
//    }
//}
