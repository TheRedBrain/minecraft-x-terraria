package com.github.theredbrain.minecraftxterraria.mixin.client.option;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GameOptions.class)
public class GameOptionsMixin {

    @Shadow
    @Final
    @Mutable
    public KeyBinding[] hotbarKeys = initializeHotbarKeys();

    private static KeyBinding[] initializeHotbarKeys() {
        return new KeyBinding[]{new KeyBinding("key.hotbar.1", GLFW.GLFW_KEY_1, KeyBinding.INVENTORY_CATEGORY), new KeyBinding("key.hotbar.2", GLFW.GLFW_KEY_2, KeyBinding.INVENTORY_CATEGORY), new KeyBinding("key.hotbar.3", GLFW.GLFW_KEY_3, KeyBinding.INVENTORY_CATEGORY), new KeyBinding("key.hotbar.4", GLFW.GLFW_KEY_4, KeyBinding.INVENTORY_CATEGORY), new KeyBinding("key.hotbar.5", GLFW.GLFW_KEY_5, KeyBinding.INVENTORY_CATEGORY), new KeyBinding("key.hotbar.6", GLFW.GLFW_KEY_6, KeyBinding.INVENTORY_CATEGORY), new KeyBinding("key.hotbar.7", GLFW.GLFW_KEY_7, KeyBinding.INVENTORY_CATEGORY), new KeyBinding("key.hotbar.8", GLFW.GLFW_KEY_8, KeyBinding.INVENTORY_CATEGORY), new KeyBinding("key.hotbar.9", GLFW.GLFW_KEY_9, KeyBinding.INVENTORY_CATEGORY), new KeyBinding("key.hotbar.0", GLFW.GLFW_KEY_0, KeyBinding.INVENTORY_CATEGORY)};
    }
}
