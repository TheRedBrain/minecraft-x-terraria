package com.github.theredbrain.minecraftxterraria.mixin;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	/*
	*
	*
	*/

	// handles right-click
//	public ActionResult interact(Entity entity, Hand hand) {
//
//	}
}
