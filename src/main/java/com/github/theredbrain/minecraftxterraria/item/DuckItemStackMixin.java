package com.github.theredbrain.minecraftxterraria.item;

import com.github.theredbrain.minecraftxterraria.entity.MxtEquipmentSlot;
import com.google.common.collect.Multimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;

public interface DuckItemStackMixin {

    Multimap<EntityAttribute, EntityAttributeModifier> mxtGetAttributeModifiers(MxtEquipmentSlot slot);
}