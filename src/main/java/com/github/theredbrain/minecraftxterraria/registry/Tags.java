package com.github.theredbrain.minecraftxterraria.registry;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class Tags {
    public static final TagKey<Item> COIN_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "coin_slot_items"));
    public static final TagKey<Item> AMMUNITION_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "ammunition_slot_items"));
    public static final TagKey<Item> DYE_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "dye_slot_items"));
    public static final TagKey<Item> HELMET_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "helmet_slot_items"));
    public static final TagKey<Item> VANITY_HELMET_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "vanity_helmet_slot_items"));
    public static final TagKey<Item> TORSO_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "torso_slot_items"));
    public static final TagKey<Item> VANITY_TORSO_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "vanity_torso_slot_items"));
    public static final TagKey<Item> LEGS_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "legs_slot_items"));
    public static final TagKey<Item> VANITY_LEGS_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "vanity_legs_slot_items"));
    public static final TagKey<Item> ACCESSORY_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "accessory_slot_items"));
    public static final TagKey<Item> VANITY_ACCESSORY_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "vanity_accessory_slot_items"));
    public static final TagKey<Item> PET_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "pet_slot_items"));
    public static final TagKey<Item> LIGHT_PET_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "light_pet_slot_items"));
    public static final TagKey<Item> MINECART_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "minecart_slot_items"));
    public static final TagKey<Item> MOUNT_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "mount_slot_items"));
    public static final TagKey<Item> HOOK_SLOT_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "hook_slot_items"));
}
