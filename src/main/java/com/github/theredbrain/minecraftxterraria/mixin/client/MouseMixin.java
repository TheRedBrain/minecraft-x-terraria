package com.github.theredbrain.minecraftxterraria.mixin.client;

import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Mouse.class)
public class MouseMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private double x;

    @Shadow
    private double y;

    @Shadow
    private double eventDeltaWheel;

    // TODO scroll crafting recipes
    //  change to invoke and re enable fabric-screen-api-v1
    /**
     * @author TheRedBrain
     * @reason use custom hotbar
     */
    @Overwrite
    private void onMouseScroll(long window, double horizontal, double vertical) {
        if (window == MinecraftClient.getInstance().getWindow().getHandle()) {
            double d = (this.client.options.getDiscreteMouseScroll().getValue() != false ? Math.signum(vertical) : vertical) * this.client.options.getMouseWheelSensitivity().getValue();
            if (this.client.getOverlay() == null) {
                if (this.client.currentScreen != null) {
                    double e = this.x * (double)this.client.getWindow().getScaledWidth() / (double)this.client.getWindow().getWidth();
                    double f = this.y * (double)this.client.getWindow().getScaledHeight() / (double)this.client.getWindow().getHeight();
                    this.client.currentScreen.mouseScrolled(e, f, d);
                    this.client.currentScreen.applyMousePressScrollNarratorDelay();
                } else if (this.client.player != null) {
                    if (this.eventDeltaWheel != 0.0 && Math.signum(d) != Math.signum(this.eventDeltaWheel)) {
                        this.eventDeltaWheel = 0.0;
                    }
                    this.eventDeltaWheel += d;
                    int i = (int)this.eventDeltaWheel;
                    if (i == 0) {
                        return;
                    }
                    this.eventDeltaWheel -= (double)i;
                    if (this.client.player.isSpectator()) {
                        if (this.client.inGameHud.getSpectatorHud().isOpen()) {
                            this.client.inGameHud.getSpectatorHud().cycleSlot(-i);
                        } else {
                            float g = MathHelper.clamp(this.client.player.getAbilities().getFlySpeed() + (float)i * 0.005f, 0.0f, 0.2f);
                            this.client.player.getAbilities().setFlySpeed(g);
                        }
                    } else {
                        ((DuckPlayerEntityMixin)this.client.player).getMxtPlayerInventory().scrollInHotbar(i);
                    }
                }
            }
        }
    }
}
