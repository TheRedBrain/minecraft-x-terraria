package com.github.theredbrain.minecraftxterraria.network.packet.s2c.play;

import com.github.theredbrain.minecraftxterraria.network.listener.MxtClientPlayPacketListener;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;

public class MxtManaUpdateS2CPacket implements Packet<MxtClientPlayPacketListener> {
    private final float mana;
    private final float manaRegenerationDelay;
    private final float manaRegenerationCounter;

    public MxtManaUpdateS2CPacket(float mana, float manaRegenerationDelay, float manaRegenerationCounter) {
        this.mana = mana;
        this.manaRegenerationDelay = manaRegenerationDelay;
        this.manaRegenerationCounter = manaRegenerationCounter;
    }

    public MxtManaUpdateS2CPacket(PacketByteBuf buf) {
        this.mana = buf.readFloat();
        this.manaRegenerationDelay = buf.readFloat();
        this.manaRegenerationCounter = buf.readFloat();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeFloat(this.mana);
        buf.writeFloat(this.manaRegenerationDelay);
        buf.writeFloat(this.manaRegenerationCounter);
    }

    @Override
    public void apply(MxtClientPlayPacketListener mxtClientPlayPacketListener) {
        mxtClientPlayPacketListener.mxtOnManaUpdate(this);
    }

    public float getMana() {
        return this.mana;
    }

    public float getManaRegenerationDelay() {
        return this.manaRegenerationDelay;
    }

    public float getManaRegenerationCounter() {
        return this.manaRegenerationCounter;
    }
}
