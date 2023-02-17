package com.github.theredbrain.minecraftxterraria.network.packet.s2c.play;

import com.github.theredbrain.minecraftxterraria.network.listener.MxtClientPlayPacketListener;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;

public class MxtHealthUpdateS2CPacket implements Packet<MxtClientPlayPacketListener> {
    private final float health;
    private final float healthRegenerationTime;
    private final float healthEffectiveRegenerationTime;
    private final float healthRegenerationCounter;

    public MxtHealthUpdateS2CPacket(float health, float healthRegenerationTime, float healthEffectiveRegenerationTime, float healthRegenerationCounter) {
        this.health = health;
        this.healthRegenerationTime = healthRegenerationTime;
        this.healthEffectiveRegenerationTime = healthEffectiveRegenerationTime;
        this.healthRegenerationCounter = healthRegenerationCounter;
    }

    public MxtHealthUpdateS2CPacket(PacketByteBuf buf) {
        this.health = buf.readFloat();
        this.healthRegenerationTime = buf.readFloat();
        this.healthEffectiveRegenerationTime = buf.readFloat();
        this.healthRegenerationCounter = buf.readFloat();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeFloat(this.health);
        buf.writeFloat(this.healthRegenerationTime);
        buf.writeFloat(this.healthEffectiveRegenerationTime);
        buf.writeFloat(this.healthRegenerationCounter);
    }

    @Override
    public void apply(MxtClientPlayPacketListener mxtClientPlayPacketListener) {
        mxtClientPlayPacketListener.mxtOnHealthUpdate(this);
    }

    public float getHealth() {
        return this.health;
    }

    public float getHealthRegenerationTime() {
        return this.healthRegenerationTime;
    }

    public float getHealthEffectiveRegenerationTime() {
        return this.healthEffectiveRegenerationTime;
    }

    public float getHealthRegenerationCounter() {
        return this.healthRegenerationCounter;
    }
}
