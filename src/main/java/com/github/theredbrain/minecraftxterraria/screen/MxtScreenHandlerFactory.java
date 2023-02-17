package com.github.theredbrain.minecraftxterraria.screen;

import com.github.theredbrain.minecraftxterraria.entity.player.MxtPlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import org.jetbrains.annotations.Nullable;

public interface MxtScreenHandlerFactory {
    @Nullable
    ScreenHandler mxtCreateMenu(int var1, MxtPlayerInventory var2, PlayerEntity var3);
}
