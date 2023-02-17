package com.github.theredbrain.minecraftxterraria.network.listener;

import com.github.theredbrain.minecraftxterraria.network.packet.c2s.play.MxtPlayerUseItemC2SPacket;
import net.minecraft.network.listener.ServerPacketListener;

public interface MxtServerPlayPacketListener extends ServerPacketListener {

    void onPlayerUseItem(MxtPlayerUseItemC2SPacket var1);
}
