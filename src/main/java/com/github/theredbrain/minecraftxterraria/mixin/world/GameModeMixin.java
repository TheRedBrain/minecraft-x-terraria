package com.github.theredbrain.minecraftxterraria.mixin.world;

import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.*;

@Mixin(GameMode.class)
public class GameModeMixin {

    @Shadow
    @Final
    @Mutable
    public static GameMode DEFAULT = GameMode.CREATIVE;

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void setAbilities(PlayerAbilities abilities) {
        abilities.allowFlying = false;
        abilities.creativeMode = false;
        abilities.invulnerable = false;
        abilities.flying = false;
        abilities.allowModifyWorld = true;
    }
}
