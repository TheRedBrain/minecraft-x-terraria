package com.github.theredbrain.minecraftxterraria.mixin;

import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.*;

@Mixin(Inventory.class)
public interface InventoryMixin {
//    @Final
//    @Mutable
//    @Shadow
//    public static int MAX_COUNT_PER_STACK = 9999;

    /**
     * @author TheRedBrain
     */
    @Overwrite
    default int getMaxCountPerStack() {
        return 9999;
    }
}
