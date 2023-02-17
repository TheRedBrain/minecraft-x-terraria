package com.github.theredbrain.minecraftxterraria.mixin.screen;

import com.github.theredbrain.minecraftxterraria.screen.MxtScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerFactory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ScreenHandlerFactory.class)
public interface ScreenHandlerFactoryMixin extends MxtScreenHandlerFactory {
}
