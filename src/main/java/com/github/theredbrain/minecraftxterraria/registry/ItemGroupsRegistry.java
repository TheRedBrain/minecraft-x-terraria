package com.github.theredbrain.minecraftxterraria.registry;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public class ItemGroupsRegistry {

    public static void modifyItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(MinecraftXTerraria.MXT_CONTENT).register(content -> {
            // ---tools---
            // axes
            content.add(ItemsRegistry.IRON_AXE);
            // hammers
            content.add(ItemsRegistry.IRON_HAMMER);
            // pickaxes
            content.add(ItemsRegistry.COPPER_PICKAXE);
            content.add(ItemsRegistry.IRON_PICKAXE);
            content.add(ItemsRegistry.MOLTEN_PICKAXE);
            content.add(ItemsRegistry.STARDUST_PICKAXE);
            // ---weapons---
            // swords
            content.add(ItemsRegistry.IRON_SWORD);
            // ---blocks---
            // soil
            content.add(ItemsRegistry.CLAY_BLOCK_ITEM);
            // bricks
            content.add(ItemsRegistry.BLUE_DUNGEON_BRICKS_BLOCK_ITEM);
            content.add(ItemsRegistry.GREEN_DUNGEON_BRICKS_BLOCK_ITEM);
            content.add(ItemsRegistry.PINK_DUNGEON_BRICKS_BLOCK_ITEM);
            content.add(ItemsRegistry.LIHZAHRD_BRICKS_BLOCK_ITEM);

            content.add(ItemsRegistry.TEST);
        });
    }
}
