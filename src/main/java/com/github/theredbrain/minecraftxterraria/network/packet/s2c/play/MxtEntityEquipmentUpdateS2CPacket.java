package com.github.theredbrain.minecraftxterraria.network.packet.s2c.play;

import com.github.theredbrain.minecraftxterraria.entity.MxtEquipmentSlot;
import com.github.theredbrain.minecraftxterraria.network.listener.MxtClientPlayPacketListener;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;

import java.util.List;

public class MxtEntityEquipmentUpdateS2CPacket implements Packet<MxtClientPlayPacketListener> {
    private static final byte field_33342 = -128;
    private final int id;
    private final List<Pair<MxtEquipmentSlot, ItemStack>> equipmentList;

    public MxtEntityEquipmentUpdateS2CPacket(int id, List<Pair<MxtEquipmentSlot, ItemStack>> equipmentList) {
        this.id = id;
        this.equipmentList = equipmentList;
    }

    public MxtEntityEquipmentUpdateS2CPacket(PacketByteBuf buf) {
        byte i;
        this.id = buf.readVarInt();
        MxtEquipmentSlot[] equipmentSlots = MxtEquipmentSlot.values();
        this.equipmentList = Lists.newArrayList();
        do {
            i = buf.readByte();
            MxtEquipmentSlot equipmentSlot = equipmentSlots[i & 0x7F];
            ItemStack itemStack = buf.readItemStack();
            this.equipmentList.add(Pair.of(equipmentSlot, itemStack));
        } while ((i & 0xFFFFFF80) != 0);
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeVarInt(this.id);
        int i = this.equipmentList.size();
        for (int j = 0; j < i; ++j) {
            Pair<MxtEquipmentSlot, ItemStack> pair = this.equipmentList.get(j);
            MxtEquipmentSlot equipmentSlot = pair.getFirst();
            boolean bl = j != i - 1;
            int k = equipmentSlot.ordinal();
            buf.writeByte(bl ? k | 0xFFFFFF80 : k);
            buf.writeItemStack(pair.getSecond());
        }
    }

    @Override
    public void apply(MxtClientPlayPacketListener mxtClientPlayPacketListener) {
        mxtClientPlayPacketListener.mxtOnEntityEquipmentUpdate(this);
    }

    public int getId() {
        return this.id;
    }

    public List<Pair<MxtEquipmentSlot, ItemStack>> getEquipmentList() {
        return this.equipmentList;
    }
}

