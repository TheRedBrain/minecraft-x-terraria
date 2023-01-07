package com.github.theredbrain.minecraftxterraria.registry;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import com.github.theredbrain.minecraftxterraria.item.tool.MxTPickaxeItem;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemsRegistry {
    public static final MxTPickaxeItem IRON_PICKAXE = new MxTPickaxeItem(new FabricItemSettings().maxCount(1), 5, 4, 0, 20, 13, 2, 0, 400, 1, null, 40);
    public static final MxTPickaxeItem MOLTEN_PICKAXE = new MxTPickaxeItem(new FabricItemSettings().maxCount(1), 12, 4, 0, 23, 18, 2, 3, 5400, 1, new Pair<>(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3F), 100);

    public static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "iron_pickaxe"), IRON_PICKAXE);
        Registry.register(Registries.ITEM, new Identifier(MinecraftXTerraria.MOD_ID, "molten_pickaxe"), MOLTEN_PICKAXE);
    }
}
