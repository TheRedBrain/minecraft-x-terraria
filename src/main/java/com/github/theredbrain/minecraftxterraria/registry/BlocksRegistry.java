package com.github.theredbrain.minecraftxterraria.registry;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import com.github.theredbrain.minecraftxterraria.block.MxTDungeonBricksBlock;
import com.github.theredbrain.minecraftxterraria.block.MxTInteractiveTestBlock;
import com.github.theredbrain.minecraftxterraria.block.MxtDiggingBlock;
import com.github.theredbrain.minecraftxterraria.block.MxtLihzahrdBricksBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class BlocksRegistry {
    // soil blocks
    public static final MxtDiggingBlock CLAY_BLOCK = new MxtDiggingBlock(FabricBlockSettings.of(Material.SOIL).sounds(BlockSoundGroup.GRAVEL), 50);

    // brick blocks
    public static final MxTDungeonBricksBlock BLUE_DUNGEON_BRICKS_BLOCK = new MxTDungeonBricksBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 1200.0f).sounds(BlockSoundGroup.STONE), 200);
    public static final MxTDungeonBricksBlock GREEN_DUNGEON_BRICKS_BLOCK = new MxTDungeonBricksBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 1200.0f).sounds(BlockSoundGroup.STONE), 200);
    public static final MxTDungeonBricksBlock PINK_DUNGEON_BRICKS_BLOCK = new MxTDungeonBricksBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 1200.0f).sounds(BlockSoundGroup.STONE), 200);
    public static final MxtLihzahrdBricksBlock LIHZAHRD_BRICKS_BLOCK = new MxtLihzahrdBricksBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE), 400);

    public static final MxTInteractiveTestBlock TEST = new MxTInteractiveTestBlock(FabricBlockSettings.of(Material.STONE).strength(0.0f, 1200.0f).sounds(BlockSoundGroup.STONE), 200);

    public static void registerBlocks() {
        // soil blocks
        Registry.register(Registries.BLOCK, new Identifier(MinecraftXTerraria.MOD_ID, "clay_block"), CLAY_BLOCK);
        Registry.register(Registries.BLOCK, new Identifier(MinecraftXTerraria.MOD_ID, "blue_dungeon_bricks_block"), BLUE_DUNGEON_BRICKS_BLOCK);
        Registry.register(Registries.BLOCK, new Identifier(MinecraftXTerraria.MOD_ID, "green_dungeon_bricks_block"), GREEN_DUNGEON_BRICKS_BLOCK);
        Registry.register(Registries.BLOCK, new Identifier(MinecraftXTerraria.MOD_ID, "pink_dungeon_bricks_block"), PINK_DUNGEON_BRICKS_BLOCK);
        Registry.register(Registries.BLOCK, new Identifier(MinecraftXTerraria.MOD_ID, "lihzahrd_bricks_block"), LIHZAHRD_BRICKS_BLOCK);
        Registry.register(Registries.BLOCK, new Identifier(MinecraftXTerraria.MOD_ID, "test"), TEST);

    }
}
