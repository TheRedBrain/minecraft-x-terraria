package com.github.theredbrain.minecraftxterraria.registry;

import com.github.theredbrain.minecraftxterraria.block.MxTDungeonBricksBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;

public class BlocksRegistry {
    // brick blocks
//    public static final IngotBlock BRICK_BLOCK = new IngotBlock(FabricBlockSettings.of(Material.STONE, MapColor.RED).strength(2.0F, 6.0F).sounds(BlockSoundGroup.STONE));
//    public static final IngotBlock NETHER_BRICK_BLOCK = new IngotBlock(FabricBlockSettings.of(Material.STONE, MapColor.DARK_RED).strength(2.0F, 6.0F).sounds(BlockSoundGroup.NETHER_BRICKS));
//    public static final IngotBlock DIAMOND_INGOT_BLOCK = new IngotBlock(FabricBlockSettings.of(Material.METAL, MapColor.DIAMOND_BLUE).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));
//    public static final IngotBlock IRON_INGOT_BLOCK = new IngotBlock(FabricBlockSettings.of(Material.METAL, MapColor.IRON_GRAY).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));
//    public static final IngotBlock COPPER_INGOT_BLOCK = new IngotBlock(FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(3.0F, 6.0F).sounds(BlockSoundGroup.COPPER));
//    public static final IngotBlock GOLD_INGOT_BLOCK = new IngotBlock(FabricBlockSettings.of(Material.METAL, MapColor.GOLD).strength(3.0F, 6.0F).sounds(BlockSoundGroup.METAL));
//    public static final IngotBlock NETHERITE_INGOT_BLOCK = new IngotBlock(FabricBlockSettings.of(Material.METAL, MapColor.BLACK).strength(50.0F, 1200.0F).sounds(BlockSoundGroup.NETHERITE));
    public static final MxTDungeonBricksBlock BLUE_DUNGEON_BRICKS_BLOCK = new MxTDungeonBricksBlock(FabricBlockSettings.of(Material.STONE));
    public static final MxTDungeonBricksBlock GREEN_DUNGEON_BRICKS_BLOCK = new MxTDungeonBricksBlock(FabricBlockSettings.of(Material.STONE));
    public static final MxTDungeonBricksBlock PINK_DUNGEON_BRICKS_BLOCK = new MxTDungeonBricksBlock(FabricBlockSettings.of(Material.STONE));

    public static void registerBlocks() {

//        Registry.register(Registries.BLOCK, new Identifier(MinecraftXTerraria.MOD_ID, "brick"), BRICK_BLOCK);
//        Registry.register(Registries.BLOCK, new Identifier(MinecraftXTerraria.MOD_ID, "nether_brick"), NETHER_BRICK_BLOCK);
//        Registry.register(Registries.BLOCK, new Identifier(MinecraftXTerraria.MOD_ID, "diamond_ingot"), DIAMOND_INGOT_BLOCK);
//        Registry.register(Registries.BLOCK, new Identifier(MinecraftXTerraria.MOD_ID, "iron_ingot"), IRON_INGOT_BLOCK);
//        Registry.register(Registries.BLOCK, new Identifier(MinecraftXTerraria.MOD_ID, "copper_ingot"), COPPER_INGOT_BLOCK);
//        Registry.register(Registries.BLOCK, new Identifier(MinecraftXTerraria.MOD_ID, "gold_ingot"), GOLD_INGOT_BLOCK);
//        Registry.register(Registries.BLOCK, new Identifier(MinecraftXTerraria.MOD_ID, "netherite_ingot"), NETHERITE_INGOT_BLOCK);

    }

    public static void registerFlammableBlocks() {
    }
}
