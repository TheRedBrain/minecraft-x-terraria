package com.github.theredbrain.minecraftxterraria;

import com.github.theredbrain.minecraftxterraria.client.gui.screen.MxtCharacterCreationScreen;
import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.minecraftxterraria.util.NetworkingChannels;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;

public class MinecraftXTerrariaClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

//        ClientPlayConnectionEvents.JOIN.register((clientPlayNetworkHandler, packetSender, minecraftClient) -> {
//            ClientPlayNetworking.registerReceiver(NetworkingChannels.CHARACTER_CREATION, (client, handler, buf, responseSender) -> {
//
//
////            boolean characterCreated = buf.readBoolean();
//                client.execute(() -> {
//                    if (client.player != null) {
////                    client.setScreen(new InventoryScreen(client.player));
//                        if (!((DuckPlayerEntityMixin) client.player).mxtGetCharacterCreated()) {
//                            client.setScreen(new MxtCharacterCreationScreen());
//                            // Everything in this lambda is run on the render thread
//
//                        }
//                    }
//                });
//            });
//        });
    }
}
