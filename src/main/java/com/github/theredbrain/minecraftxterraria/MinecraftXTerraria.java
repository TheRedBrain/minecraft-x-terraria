package com.github.theredbrain.minecraftxterraria;

import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.minecraftxterraria.entity.player.PlayerKeys;
import com.github.theredbrain.minecraftxterraria.registry.*;
import com.github.theredbrain.minecraftxterraria.util.NetworkingChannels;
import com.github.theredbrain.minecraftxterraria.world.WorldKeys;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinecraftXTerraria implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "minecraftxterraria";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ItemGroup MXT_CONTENT = FabricItemGroup.builder(new Identifier(MOD_ID, "mxt_content"))
			.icon(() -> new ItemStack(ItemsRegistry.COPPER_PICKAXE))
			.build();

	@Override
	public void onInitialize() {
//		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
////			((DuckPlayerEntityMixin)handler.player).mxtGetCharacterCreated();
////			PlayerKeys playerKeys = WorldKeys.getPlayerKeys(handler.player);
//
////			if (!playerKeys.characterCreated) {
////			if (!((DuckPlayerEntityMixin)handler.player).mxtGetCharacterCreated()) {
//				// Sending the packet to the player (look at the networking page for more information)
////				PacketByteBuf data = PacketByteBufs.create();
////				data.writeBoolean(playerKeys.characterCreated);
//				ServerPlayNetworking.send(handler.player, NetworkingChannels.CHARACTER_CREATION, PacketByteBufs.empty());
////			}
//		});
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Hello Fabric world!");
		BlocksRegistry.registerBlocks();
		ItemsRegistry.registerItems();
		ItemsRegistry.registerBlockItems();
		ItemGroupsRegistry.modifyItemGroups();
		EntityAttributesRegistry.registerAttributes();
		StatusEffectRegistry.registerStatusEffects();
	}
}
