package com.github.theredbrain.minecraftxterraria.network.packet.c2s.play;

import com.github.theredbrain.minecraftxterraria.network.listener.MxtServerPlayPacketListener;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;

public class MxtPlayerUseItemC2SPacket implements Packet<MxtServerPlayPacketListener> {
    private final int sequence;

    public MxtPlayerUseItemC2SPacket(int sequence) {
        this.sequence = sequence;
    }

    public MxtPlayerUseItemC2SPacket(PacketByteBuf buf) {
        this.sequence = buf.readVarInt();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeVarInt(this.sequence);
    }

    @Override
    public void apply(MxtServerPlayPacketListener mxtServerPlayPacketListener) {
        mxtServerPlayPacketListener.onPlayerUseItem(this);
    }

    public int getSequence() {
        return this.sequence;
    }
}

