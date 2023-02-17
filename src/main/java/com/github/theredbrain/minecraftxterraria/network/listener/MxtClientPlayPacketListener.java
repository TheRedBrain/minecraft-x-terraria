package com.github.theredbrain.minecraftxterraria.network.listener;

import com.github.theredbrain.minecraftxterraria.network.packet.s2c.play.MxtEntityEquipmentUpdateS2CPacket;
import com.github.theredbrain.minecraftxterraria.network.packet.s2c.play.MxtHealthUpdateS2CPacket;
import com.github.theredbrain.minecraftxterraria.network.packet.s2c.play.MxtManaUpdateS2CPacket;
import net.minecraft.network.listener.PacketListener;

public interface MxtClientPlayPacketListener extends PacketListener {

    void mxtOnEntityEquipmentUpdate(MxtEntityEquipmentUpdateS2CPacket var1);

    void mxtOnHealthUpdate(MxtHealthUpdateS2CPacket var1);

    void mxtOnManaUpdate(MxtManaUpdateS2CPacket var1);

}
