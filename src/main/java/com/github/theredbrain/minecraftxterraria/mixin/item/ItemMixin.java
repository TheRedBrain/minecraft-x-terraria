package com.github.theredbrain.minecraftxterraria.mixin.item;

import com.github.theredbrain.minecraftxterraria.entity.MxtEquipmentSlot;
import com.github.theredbrain.minecraftxterraria.item.DuckItemMixin;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin implements DuckItemMixin {

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> mxtGetAttributeModifiers(MxtEquipmentSlot slot) {
        return ImmutableMultimap.of();
    }
}
