package com.github.theredbrain.minecraftxterraria.mixin.server.network;

import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.minecraftxterraria.entity.player.MxtPlayerInventory;
import com.github.theredbrain.minecraftxterraria.util.NetworkingChannels;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow
    @Final
    static Logger LOGGER;

    @Shadow
    public ServerPlayerEntity player;

    @Shadow
    public void updateSequence(int sequence) {
        throw new AssertionError();
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void onPickFromInventory(PickFromInventoryC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, (ServerPlayNetworkHandler) (Object) this, this.player.getWorld());
        ((DuckPlayerEntityMixin)this.player).getMxtPlayerInventory().swapSlotWithHotbar(packet.getSlot());
        this.player.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-2, 0, ((DuckPlayerEntityMixin)this.player).getMxtPlayerInventory().selectedSlot, ((DuckPlayerEntityMixin)this.player).getMxtPlayerInventory().getStack(((DuckPlayerEntityMixin)this.player).getMxtPlayerInventory().selectedSlot)));
        this.player.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-2, 0, packet.getSlot(), ((DuckPlayerEntityMixin)this.player).getMxtPlayerInventory().getStack(packet.getSlot())));
        this.player.networkHandler.sendPacket(new UpdateSelectedSlotS2CPacket(((DuckPlayerEntityMixin)this.player).getMxtPlayerInventory().selectedSlot));
    }

//    @Override
//    public void onBookUpdate(BookUpdateC2SPacket packet) {
//        int i = packet.getSlot();
//        if (!PlayerInventory.isValidHotbarIndex(i) && i != 40) {
//            return;
//        }
//        ArrayList<String> list = Lists.newArrayList();
//        Optional<String> optional = packet.getTitle();
//        optional.ifPresent(list::add);
//        packet.getPages().stream().limit(100L).forEach(list::add);
//        Consumer<List> consumer = optional.isPresent() ? texts -> this.addBook((FilteredMessage)texts.get(0), texts.subList(1, texts.size()), i) : texts -> this.updateBookContent((List<FilteredMessage>)texts, i);
//        this.filterTexts(list).thenAcceptAsync(consumer, (Executor)this.server);
//    }

//    private void updateBookContent(List<FilteredMessage> pages, int slotId) {
//        ItemStack itemStack = this.player.getInventory().getStack(slotId);
//        if (!itemStack.isOf(Items.WRITABLE_BOOK)) {
//            return;
//        }
//        this.setTextToBook(pages, UnaryOperator.identity(), itemStack);
//    }
//
//    private void addBook(FilteredMessage title, List<FilteredMessage> pages, int slotId) {
//        ItemStack itemStack = this.player.getInventory().getStack(slotId);
//        if (!itemStack.isOf(Items.WRITABLE_BOOK)) {
//            return;
//        }
//        ItemStack itemStack2 = new ItemStack(Items.WRITTEN_BOOK);
//        NbtCompound nbtCompound = itemStack.getNbt();
//        if (nbtCompound != null) {
//            itemStack2.setNbt(nbtCompound.copy());
//        }
//        itemStack2.setSubNbt("author", NbtString.of(this.player.getName().getString()));
//        if (this.player.shouldFilterText()) {
//            itemStack2.setSubNbt("title", NbtString.of(title.getString()));
//        } else {
//            itemStack2.setSubNbt("filtered_title", NbtString.of(title.getString()));
//            itemStack2.setSubNbt("title", NbtString.of(title.raw()));
//        }
//        this.setTextToBook(pages, text -> Text.Serializer.toJson(Text.literal(text)), itemStack2);
//        this.player.getInventory().setStack(slotId, itemStack2);
//    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void onPlayerAction(PlayerActionC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, (ServerPlayNetworkHandler) (Object) this, this.player.getWorld());
        BlockPos blockPos = packet.getPos();
        this.player.updateLastActionTime();
        PlayerActionC2SPacket.Action action = packet.getAction();
        switch (action) {
            case SWAP_ITEM_WITH_OFFHAND: {
                if (!this.player.isSpectator()) {
                    ItemStack itemStack = this.player.getStackInHand(Hand.OFF_HAND);
                    this.player.setStackInHand(Hand.OFF_HAND, this.player.getStackInHand(Hand.MAIN_HAND));
                    this.player.setStackInHand(Hand.MAIN_HAND, itemStack);
                    this.player.clearActiveItem();
                }
                return;
            }
            case DROP_ITEM: {
                if (!this.player.isSpectator()) {
                    this.player.dropSelectedItem(false);
                }
                return;
            }
            case DROP_ALL_ITEMS: {
                if (!this.player.isSpectator()) {
                    this.player.dropSelectedItem(true);
                }
                return;
            }
            case RELEASE_USE_ITEM: {
                this.player.stopUsingItem();
                return;
            }
            case START_DESTROY_BLOCK:
            case ABORT_DESTROY_BLOCK:
            case STOP_DESTROY_BLOCK: {
                this.player.interactionManager.processBlockBreakingAction(blockPos, action, packet.getDirection(), this.player.world.getTopY(), packet.getSequence());
                this.player.networkHandler.updateSequence(packet.getSequence());
                return;
            }
        }
        throw new IllegalArgumentException("Invalid player action");
    }
//
//    /**
//     * @author TheRedBrain
//     */
//    @Overwrite
//    public void onPlayerInteractItem(MxtPlayerUseItemC2SPacket packet) {
//        NetworkThreadUtils.forceMainThread(packet, (ServerPlayNetworkHandler) (Object) this, this.player.getWorld());
//        this.updateSequence(packet.getSequence());
//        ServerWorld serverWorld = this.player.getWorld();
//        Hand hand = packet.getHand();
//        ItemStack itemStack = this.player.getMainHandStack();
//        this.player.updateLastActionTime();
//        if (itemStack.isEmpty() || !itemStack.isItemEnabled(serverWorld.getEnabledFeatures())) {
//            return;
//        }
//        ActionResult actionResult = this.player.interactionManager.interactItem(this.player, serverWorld, itemStack, hand);
////        if (actionResult.shouldSwingHand()) {
////            this.player.swingHand(hand, true);
////        }
//    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void onUpdateSelectedSlot(UpdateSelectedSlotC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, (ServerPlayNetworkHandler) (Object) this, this.player.getWorld());
        if (packet.getSelectedSlot() < 0 || packet.getSelectedSlot() >= MxtPlayerInventory.getHotbarSize()) {
            LOGGER.warn("{} tried to set an invalid carried item", (Object)this.player.getName().getString());
            return;
        }
        if (((DuckPlayerEntityMixin)this.player).getMxtPlayerInventory().selectedSlot != packet.getSelectedSlot() && this.player.getActiveHand() == Hand.MAIN_HAND) {
            this.player.clearActiveItem();
        }
        ((DuckPlayerEntityMixin)this.player).getMxtPlayerInventory().selectedSlot = packet.getSelectedSlot();
        this.player.updateLastActionTime();
    }

//    @Override // TODO entity interacting
//    public void onPlayerInteractEntity(PlayerInteractEntityC2SPacket packet) {
//        NetworkThreadUtils.forceMainThread(packet, this, this.player.getWorld());
//        final ServerWorld serverWorld = this.player.getWorld();
//        final Entity entity = packet.getEntity(serverWorld);
//        this.player.updateLastActionTime();
//        this.player.setSneaking(packet.isPlayerSneaking());
//        if (entity != null) {
//            if (!serverWorld.getWorldBorder().contains(entity.getBlockPos())) {
//                return;
//            }
//            if (entity.squaredDistanceTo(this.player.getEyePos()) < MAX_BREAK_SQUARED_DISTANCE) {
//                packet.handle(new PlayerInteractEntityC2SPacket.Handler(){
//
//                    private void processInteract(Hand hand, ServerPlayNetworkHandler.Interaction action) {
//                        ItemStack itemStack = ServerPlayNetworkHandler.this.player.getStackInHand(hand);
//                        if (!itemStack.isItemEnabled(serverWorld.getEnabledFeatures())) {
//                            return;
//                        }
//                        ItemStack itemStack2 = itemStack.copy();
//                        ActionResult actionResult = action.run(ServerPlayNetworkHandler.this.player, entity, hand);
//                        if (actionResult.isAccepted()) {
//                            Criteria.PLAYER_INTERACTED_WITH_ENTITY.trigger(ServerPlayNetworkHandler.this.player, itemStack2, entity);
//                            if (actionResult.shouldSwingHand()) {
//                                ServerPlayNetworkHandler.this.player.swingHand(hand, true);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void interact(Hand hand) {
//                        this.processInteract(hand, PlayerEntity::interact);
//                    }
//
//                    @Override
//                    public void interactAt(Hand hand2, Vec3d pos) {
//                        this.processInteract(hand2, (player, entity, hand) -> entity.interactAt(player, pos, hand));
//                    }
//
//                    @Override
//                    public void attack() {
//                        if (entity instanceof ItemEntity || entity instanceof ExperienceOrbEntity || entity instanceof PersistentProjectileEntity || entity == ServerPlayNetworkHandler.this.player) {
//                            ServerPlayNetworkHandler.this.disconnect(Text.translatable("multiplayer.disconnect.invalid_entity_attacked"));
//                            LOGGER.warn("Player {} tried to attack an invalid entity", (Object)ServerPlayNetworkHandler.this.player.getName().getString());
//                            return;
//                        }
//                        ItemStack itemStack = ServerPlayNetworkHandler.this.player.getStackInHand(Hand.MAIN_HAND);
//                        if (!itemStack.isItemEnabled(serverWorld.getEnabledFeatures())) {
//                            return;
//                        }
//                        ServerPlayNetworkHandler.this.player.attack(entity);
//                    }
//                });
//            }
//        }
//    }

//    @Override // TODO crafting?
//    public void onCraftRequest(CraftRequestC2SPacket packet) {
//        NetworkThreadUtils.forceMainThread(packet, this, this.player.getWorld());
//        this.player.updateLastActionTime();
//        if (this.player.isSpectator() || this.player.currentScreenHandler.syncId != packet.getSyncId() || !(this.player.currentScreenHandler instanceof AbstractRecipeScreenHandler)) {
//            return;
//        }
//        if (!this.player.currentScreenHandler.canUse(this.player)) {
//            LOGGER.debug("Player {} interacted with invalid menu {}", (Object)this.player, (Object)this.player.currentScreenHandler);
//            return;
//        }
//        this.server.getRecipeManager().get(packet.getRecipe()).ifPresent(recipe -> ((AbstractRecipeScreenHandler)this.player.currentScreenHandler).fillInputSlots(packet.shouldCraftAll(), (Recipe<?>)recipe, this.player));
//    }

//    @Override
//    public void onCreativeInventoryAction(CreativeInventoryActionC2SPacket packet) {
//        NetworkThreadUtils.forceMainThread(packet, this, this.player.getWorld());
//        if (this.player.interactionManager.isCreative()) {
//            boolean bl3;
//            BlockEntity blockEntity;
//            BlockPos blockPos;
//            boolean bl = packet.getSlot() < 0;
//            ItemStack itemStack = packet.getItemStack();
//            if (!itemStack.isItemEnabled(this.player.getWorld().getEnabledFeatures())) {
//                return;
//            }
//            NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(itemStack);
//            if (!itemStack.isEmpty() && nbtCompound != null && nbtCompound.contains("x") && nbtCompound.contains("y") && nbtCompound.contains("z") && this.player.world.canSetBlock(blockPos = BlockEntity.posFromNbt(nbtCompound)) && (blockEntity = this.player.world.getBlockEntity(blockPos)) != null) {
//                blockEntity.setStackNbt(itemStack);
//            }
//            boolean bl2 = packet.getSlot() >= 1 && packet.getSlot() <= 45;
//            boolean bl4 = bl3 = itemStack.isEmpty() || itemStack.getDamage() >= 0 && itemStack.getCount() <= 64 && !itemStack.isEmpty();
//            if (bl2 && bl3) {
//                this.player.playerScreenHandler.getSlot(packet.getSlot()).setStack(itemStack);
//                this.player.playerScreenHandler.sendContentUpdates();
//            } else if (bl && bl3 && this.creativeItemDropThreshold < 200) {
//                this.creativeItemDropThreshold += 20;
//                this.player.dropItem(itemStack, true);
//            }
//        }
//    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void onCustomPayload(CustomPayloadC2SPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, (ServerPlayNetworkHandler) (Object) this, this.player.getWorld());
        if (packet.getChannel() == NetworkingChannels.CHARACTER_CREATION) {
            ((DuckPlayerEntityMixin)this.player).mxtSetCharacterCreated(packet.getData().getBoolean(0));
        }
    }
}
