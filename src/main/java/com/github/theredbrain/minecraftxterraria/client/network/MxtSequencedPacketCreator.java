package com.github.theredbrain.minecraftxterraria.client.network;

import com.github.theredbrain.minecraftxterraria.network.listener.MxtServerPlayPacketListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.Packet;

@FunctionalInterface
@Environment(value= EnvType.CLIENT)
public interface MxtSequencedPacketCreator {
    public Packet<MxtServerPlayPacketListener> predict(int var1);
}
