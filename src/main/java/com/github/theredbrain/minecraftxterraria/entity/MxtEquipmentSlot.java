package com.github.theredbrain.minecraftxterraria.entity;

import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public enum MxtEquipmentSlot {
    HAND(Type.HAND, 0, "hand"),
    HEAD(Type.ARMOUR, 0, "head"),
    TORSO(Type.ARMOUR, 1, "torso"),
    LEGS(Type.ARMOUR, 2, "legs"),
    HEAD_VANITY(Type.VANITY_ARMOUR, 0, "head_vanity"),
    TORSO_VANITY(Type.VANITY_ARMOUR, 1, "torso_vanity"),
    LEGS_VANITY(Type.VANITY_ARMOUR, 2, "legs_vanity"),
    HEAD_DYE(Type.ARMOUR_DYE, 0, "head_dye"),
    TORSO_DYE(Type.ARMOUR_DYE, 1, "torso_dye"),
    LEGS_DYE(Type.ARMOUR_DYE, 2, "legs_dye"),
    ACCESSORY_1(Type.ACCESSORY, 0, "accessory_1"),
    ACCESSORY_2(Type.ACCESSORY, 1, "accessory_2"),
    ACCESSORY_3(Type.ACCESSORY, 2, "accessory_3"),
    ACCESSORY_4(Type.ACCESSORY, 3, "accessory_4"),
    ACCESSORY_5(Type.ACCESSORY, 4, "accessory_5"),
    ACCESSORY_6(Type.ACCESSORY, 5, "accessory_6"),
    ACCESSORY_7(Type.ACCESSORY, 6, "accessory_7"),
    ACCESSORY_1_VANITY(Type.VANITY_ACCESSORY, 0, "accessory_1_vanity"),
    ACCESSORY_2_VANITY(Type.VANITY_ACCESSORY, 1, "accessory_2_vanity"),
    ACCESSORY_3_VANITY(Type.VANITY_ACCESSORY, 2, "accessory_3_vanity"),
    ACCESSORY_4_VANITY(Type.VANITY_ACCESSORY, 3, "accessory_4_vanity"),
    ACCESSORY_5_VANITY(Type.VANITY_ACCESSORY, 4, "accessory_5_vanity"),
    ACCESSORY_6_VANITY(Type.VANITY_ACCESSORY, 5, "accessory_6_vanity"),
    ACCESSORY_7_VANITY(Type.VANITY_ACCESSORY, 6, "accessory_7_vanity"),
    ACCESSORY_1_DYE(Type.ACCESSORY_DYE, 0, "accessory_1_dye"),
    ACCESSORY_2_DYE(Type.ACCESSORY_DYE, 1, "accessory_2_dye"),
    ACCESSORY_3_DYE(Type.ACCESSORY_DYE, 2, "accessory_3_dye"),
    ACCESSORY_4_DYE(Type.ACCESSORY_DYE, 3, "accessory_4_dye"),
    ACCESSORY_5_DYE(Type.ACCESSORY_DYE, 4, "accessory_5_dye"),
    ACCESSORY_6_DYE(Type.ACCESSORY_DYE, 5, "accessory_6_dye"),
    ACCESSORY_7_DYE(Type.ACCESSORY_DYE, 6, "accessory_7_dye"),
    PET(Type.EQUIPMENT, 0, "pet"),
    LIGHT_PET(Type.EQUIPMENT, 1, "light_pet"),
    MINECART(Type.EQUIPMENT, 2, "minecart"),
    MOUNT(Type.EQUIPMENT, 3, "mount"),
    HOOK(Type.EQUIPMENT, 4, "hook"),
    PET_DYE(Type.EQUIPMENT_DYE, 0, "pet_dye"),
    LIGHT_PET_DYE(Type.EQUIPMENT_DYE, 1, "light_pet_dye"),
    MINECART_DYE(Type.EQUIPMENT_DYE, 2, "minecart_dye"),
    MOUNT_DYE(Type.EQUIPMENT_DYE, 3, "mount_dye"),
    HOOK_DYE(Type.EQUIPMENT_DYE, 4, "hook_dye");

    private final Type type;
    private final int entityId;
    private final String name;

    private MxtEquipmentSlot(Type type, int entityId, String name) {
        this.type = type;
        this.entityId = entityId;
        this.name = name;
    }

    public Type getType() {
        return this.type;
    }

    public int getEntitySlotId() {
        return this.entityId;
    }

    public int getOffsetEntitySlotId(int offset) {
        return offset + this.entityId;
    }

    public String getName() {
        return this.name;
    }

    public static MxtEquipmentSlot byName(String name) {
        for (MxtEquipmentSlot equipmentSlot : MxtEquipmentSlot.values()) {
            if (!equipmentSlot.getName().equals(name)) continue;
            return equipmentSlot;
        }
        throw new IllegalArgumentException("Invalid slot '" + name + "'");
    }

    public static List<MxtEquipmentSlot> listByType(Type type) {
        List<MxtEquipmentSlot> slotList = DefaultedList.ofSize(0);
        for (MxtEquipmentSlot equipmentSlot : MxtEquipmentSlot.values()) {
            if (!equipmentSlot.getType().equals(type)) continue;
            slotList.add(equipmentSlot);
        }
        if (!slotList.isEmpty()) {
            return slotList;
        } else {
            throw new IllegalArgumentException("Invalid slot type '" + type + "'");
        }
    }

    public static MxtEquipmentSlot fromTypeIndex(Type type, int index) {
        for (MxtEquipmentSlot equipmentSlot : MxtEquipmentSlot.values()) {
            if (equipmentSlot.getType() != type || equipmentSlot.getEntitySlotId() != index) continue;
            return equipmentSlot;
        }
        throw new IllegalArgumentException("Invalid slot '" + type + "': " + index);
    }

    public static enum Type {
        HAND,
        ARMOUR,
        VANITY_ARMOUR,
        ARMOUR_DYE,
        ACCESSORY,
        VANITY_ACCESSORY,
        ACCESSORY_DYE,
        EQUIPMENT,
        EQUIPMENT_DYE;

    }
}
