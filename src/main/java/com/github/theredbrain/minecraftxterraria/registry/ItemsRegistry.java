package com.github.theredbrain.minecraftxterraria.registry;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import com.github.theredbrain.minecraftxterraria.item.MxTBlockItem;
import com.github.theredbrain.minecraftxterraria.item.MxtItem;
import com.github.theredbrain.minecraftxterraria.item.MxtOpenVoidBagItem;
import com.github.theredbrain.minecraftxterraria.item.accessories.MxtAccessory;
import com.github.theredbrain.minecraftxterraria.item.consumables.MxtLifeCrystalItem;
import com.github.theredbrain.minecraftxterraria.item.tools.MxTAxeItem;
import com.github.theredbrain.minecraftxterraria.item.tools.MxTHammerItem;
import com.github.theredbrain.minecraftxterraria.item.tools.MxTPickaxeItem;
import com.github.theredbrain.minecraftxterraria.item.weapons.MxTSwordItem;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemsRegistry {
    // accessories
    public static final MxtAccessory MANA_REGENERATION_BAND = new MxtAccessory(new FabricItemSettings().maxCount(1), 1, 1, 10000);
    public static final MxtAccessory SHINY_STONE = new MxtAccessory(new FabricItemSettings().maxCount(1), 13, 1, 50000);

    // consumables
    public static final MxtLifeCrystalItem LIFE_CRYSTAL_ITEM = new MxtLifeCrystalItem(new FabricItemSettings().maxCount(9999), 2, 10, 15000, false, 30);

    // weapons
    // axes
    public static final MxTAxeItem IRON_AXE = new MxTAxeItem(new FabricItemSettings().maxCount(1), 0, 1, 320, true, 27, 5, 0, 4, 4.5, null, 19, 45);

    // hammers
    public static final MxTHammerItem IRON_HAMMER = new MxTHammerItem(new FabricItemSettings().maxCount(1), 0, 1, 320, true, 30, 7, 0, 4, 5.5, null, 20, 40);

    // pickaxes
    public static final MxTPickaxeItem COPPER_PICKAXE = new MxTPickaxeItem(new FabricItemSettings().maxCount(1), 0, 1, 100, true, 23, 4, -1, 4, 2.0, null, 15, 35);
    public static final MxTPickaxeItem IRON_PICKAXE = new MxTPickaxeItem(new FabricItemSettings().maxCount(1), 0, 1, 400, true, 20, 5, 0, 4, 2.0, null, 13, 40);
    public static final MxTPickaxeItem MOLTEN_PICKAXE = new MxTPickaxeItem(new FabricItemSettings().maxCount(1), 3, 1, 5400, true, 23, 12, 0, 4, 2.0, new Pair<>(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3F), 18, 100);
    public static final MxTPickaxeItem STARDUST_PICKAXE = new MxTPickaxeItem(new FabricItemSettings().maxCount(1), 10, 1, 70000, true, 12, 80, 4, 4, 5.5, null, 6, 225);

    // swords
    public static final MxTSwordItem IRON_SWORD = new MxTSwordItem(new FabricItemSettings().maxCount(1), 0, 1, 360, false, 20, 12, 0, 4, 5.5, null);
    public static final MxTSwordItem MURAMASA = new MxTSwordItem(new FabricItemSettings().maxCount(1), 2, 1, 17500, true, 18, 24, 0, 4, 3, null);

    // misc
    public static final MxtOpenVoidBagItem OPEN_VOID_BAG = new MxtOpenVoidBagItem(new FabricItemSettings().maxCount(1), 3, 1, 20000, true, 28);

    // block items
    // soil
    public static final MxTBlockItem CLAY_BLOCK_ITEM = new MxTBlockItem(new FabricItemSettings().maxCount(9999), 0, 100, 0, true, 15, BlocksRegistry.CLAY_BLOCK);

    // bricks
    public static final MxTBlockItem BLUE_DUNGEON_BRICKS_BLOCK_ITEM = new MxTBlockItem(new FabricItemSettings().maxCount(9999), 0, 100, 0, true, 15, BlocksRegistry.BLUE_DUNGEON_BRICKS_BLOCK);
    public static final MxTBlockItem GREEN_DUNGEON_BRICKS_BLOCK_ITEM = new MxTBlockItem(new FabricItemSettings().maxCount(9999), 0, 100, 0, true, 15, BlocksRegistry.GREEN_DUNGEON_BRICKS_BLOCK);
    public static final MxTBlockItem PINK_DUNGEON_BRICKS_BLOCK_ITEM = new MxTBlockItem(new FabricItemSettings().maxCount(9999), 0, 100, 0, true, 15, BlocksRegistry.PINK_DUNGEON_BRICKS_BLOCK);
    public static final MxTBlockItem LIHZAHRD_BRICKS_BLOCK_ITEM = new MxTBlockItem(new FabricItemSettings().maxCount(9999), 0, 100, 0, true, 15, BlocksRegistry.LIHZAHRD_BRICKS_BLOCK);

    public static final MxTBlockItem TEST = new MxTBlockItem(new FabricItemSettings().maxCount(9999), 0, 100, 0, true, 15, BlocksRegistry.TEST);

    public static void registerItems() {
        // accessories
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "mana_regeneration_band"), MANA_REGENERATION_BAND);
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "shiny_stone"), SHINY_STONE);
        // consumables
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "life_crystal"), LIFE_CRYSTAL_ITEM);
        // weapons
        // axes
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "iron_axe"), IRON_AXE);
        // hammers
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "iron_hammer"), IRON_HAMMER);
        // pickaxes
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "copper_pickaxe"), COPPER_PICKAXE);
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "iron_pickaxe"), IRON_PICKAXE);
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "molten_pickaxe"), MOLTEN_PICKAXE);
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "stardust_pickaxe"), STARDUST_PICKAXE);
        // swords
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "iron_sword"), IRON_SWORD);
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "muramasa"), MURAMASA);
        // misc
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "open_void_bag"), OPEN_VOID_BAG);
    }
        public static void registerBlockItems() {
        // soil
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "clay_block"), CLAY_BLOCK_ITEM);

        // bricks
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "blue_dungeon_brick_block"), BLUE_DUNGEON_BRICKS_BLOCK_ITEM);
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "green_dungeon_brick_block"), GREEN_DUNGEON_BRICKS_BLOCK_ITEM);
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "pink_dungeon_brick_block"), PINK_DUNGEON_BRICKS_BLOCK_ITEM);
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "lihzahrd_bricks_block"), LIHZAHRD_BRICKS_BLOCK_ITEM);


        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "test"), TEST);
    }
}
