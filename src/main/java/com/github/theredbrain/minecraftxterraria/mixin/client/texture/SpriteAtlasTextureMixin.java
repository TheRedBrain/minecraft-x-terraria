package com.github.theredbrain.minecraftxterraria.mixin.client.texture;

import com.github.theredbrain.minecraftxterraria.screen.MxtPlayerInventoryScreenHandler;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SpriteAtlasTexture.class)
public class SpriteAtlasTextureMixin {
    // TODO replace PlayerScreenHandler with MxtAbstractPlayerScreenHandler
    @Shadow
    @Final
    public static final Identifier BLOCK_ATLAS_TEXTURE = MxtPlayerInventoryScreenHandler.BLOCK_ATLAS_TEXTURE;
}
