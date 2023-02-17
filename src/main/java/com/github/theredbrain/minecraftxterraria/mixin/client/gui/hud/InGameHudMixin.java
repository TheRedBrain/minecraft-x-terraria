package com.github.theredbrain.minecraftxterraria.mixin.client.gui.hud;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.minecraftxterraria.registry.EntityAttributesRegistry;
import com.github.theredbrain.minecraftxterraria.registry.StatusEffectRegistry;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.*;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.*;

@Mixin(value = InGameHud.class, priority = 900)
public abstract class InGameHudMixin extends DrawableHelper {

    @Shadow
    @Final
    @Mutable
    private static Identifier WIDGETS_TEXTURE = new Identifier(MinecraftXTerraria.MOD_ID, "textures/gui/mxt_widgets.png"); //  93

    @Shadow
    @Final
    private static Identifier POWDER_SNOW_OUTLINE;

    @Shadow
    @Final
    private Random random;

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Final
    private  ChatHud chatHud;

    @Shadow
    private int ticks;

    @Shadow
    private Text overlayMessage;

    @Shadow
    private int overlayRemaining;

    @Shadow
    private boolean overlayTinted;

    @Shadow
    private int heldItemTooltipFade;

    @Shadow
    private ItemStack currentStack;

    @Shadow
    @Final
    private DebugHud debugHud;

    @Shadow
    @Final
    private SubtitlesHud subtitlesHud;

    @Shadow
    @Final
    private SpectatorHud spectatorHud;

    @Shadow
    @Final
    private PlayerListHud playerListHud;

    @Shadow
    @Final
    private BossBarHud bossBarHud;

    @Shadow
    private int titleRemainTicks;

    @Shadow
    private Text title;

    @Shadow
    private Text subtitle;

    @Shadow
    private int titleFadeInTicks;

    @Shadow
    private int titleStayTicks;

    @Shadow
    private int titleFadeOutTicks;

    @Shadow
    private int lastHealthValue;

    @Shadow
    private int renderHealthValue;

    @Shadow
    private long lastHealthCheckTime;

    @Shadow
    private int scaledWidth;

    @Shadow // 139
    private int scaledHeight;

    @Shadow // 348
    private void drawTextBackground(MatrixStack matrices, TextRenderer textRenderer, int yOffset, int width, int color) {
        throw new AssertionError();
    }

    @Shadow // 400
    private boolean shouldRenderSpectatorCrosshair(HitResult hitResult) {
        throw new AssertionError();
    }

    @Shadow // 415
    protected void renderStatusEffectOverlay(MatrixStack matrices) {
        throw new AssertionError();
    }

    @Shadow // 573
    public void renderHeldItemTooltip(MatrixStack matrices) {
        throw new AssertionError();
    }

    @Shadow // 601
    public void renderDemoTimer(MatrixStack matrices) {
        throw new AssertionError();
    }

    @Shadow // 609
    private void renderScoreboardSidebar(MatrixStack matrices, ScoreboardObjective objective) {
        throw new AssertionError();
    }

    @Shadow // 649
    private PlayerEntity getCameraPlayer() {
        throw new AssertionError();
    }

    @Shadow // 656
    private LivingEntity getRiddenEntity() {
        throw new AssertionError();
    }

    @Shadow // 670
    private int getHeartCount(LivingEntity entity) {
        throw new AssertionError();
    }

    @Shadow // 682
    private int getHeartRows(int heartCount) {
        throw new AssertionError();
    }

    @Shadow // 869
    private void renderOverlay(Identifier texture, float opacity) {
        throw new AssertionError();
    }

    @Shadow // 938
    private void updateVignetteDarkness(Entity entity) {
        throw new AssertionError();
    }

    @Shadow // 948
    private void renderVignetteOverlay(Entity entity) {
        throw new AssertionError();
    }

    @Shadow // 981
    private void renderPortalOverlay(float nauseaStrength) {
        throw new AssertionError();
    }

    @Shadow // 1011
    private void renderHotbarItem(int x, int y, float tickDelta, PlayerEntity player, ItemStack stack, int seed) {
        throw new AssertionError();
    }

    @Shadow // 1137
    public TextRenderer getTextRenderer() {
        throw new AssertionError();
    }

    @Shadow // 1165
    private void renderAutosaveIndicator(MatrixStack matrices) {
        throw new AssertionError();
    }

    private static final Identifier HOTBAR_TEXTURE = new Identifier(MinecraftXTerraria.MOD_ID, "textures/gui/mxt_hotbar.png");
    private static final Identifier STATUS_BARS_TEXTURE = new Identifier(MinecraftXTerraria.MOD_ID, "textures/gui/mxt_icons.png");

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void render(MatrixStack matrices, float tickDelta) {
        int k;
        float g;
        Window window = this.client.getWindow();
        this.scaledWidth = window.getScaledWidth();
        this.scaledHeight = window.getScaledHeight();
        TextRenderer textRenderer = this.getTextRenderer();
        RenderSystem.enableBlend();
        if (MinecraftClient.isFancyGraphicsOrBetter()) {
            this.renderVignetteOverlay(this.client.getCameraEntity());
        } else {
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.defaultBlendFunc();
        }
        float f = this.client.getLastFrameDuration();
//        this.spyglassScale = MathHelper.lerp(0.5f * f, this.spyglassScale, 1.125f);
//        if (this.client.options.getPerspective().isFirstPerson()) {
//            if (this.client.player.isUsingSpyglass()) {
//                this.renderSpyglassOverlay(this.spyglassScale);
//            } else {
//                this.spyglassScale = 0.5f;
//                ItemStack itemStack = this.client.player.getInventory().getArmorStack(3);
//                if (itemStack.isOf(Blocks.CARVED_PUMPKIN.asItem())) {
//                    this.renderOverlay(PUMPKIN_BLUR, 1.0f);
//                }
//            }
//        }
        if (this.client.player.getFrozenTicks() > 0) {
            this.renderOverlay(POWDER_SNOW_OUTLINE, this.client.player.getFreezingScale());
        }
        if ((g = MathHelper.lerp(tickDelta, this.client.player.lastNauseaStrength, this.client.player.nextNauseaStrength)) > 0.0f && !this.client.player.hasStatusEffect(StatusEffects.NAUSEA)) {
            this.renderPortalOverlay(g);
        }
//        if (this.client.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) {
//            this.spectatorHud.renderSpectatorMenu(matrices);
//        } else
        if (!this.client.options.hudHidden) {
            this.renderHotbar(tickDelta, matrices);
        }
        if (!this.client.options.hudHidden) {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
            RenderSystem.enableBlend();
            this.renderCrosshair(matrices);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.defaultBlendFunc();
            this.client.getProfiler().push("bossHealth");
            this.bossBarHud.render(matrices);
            this.client.getProfiler().pop();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, STATUS_BARS_TEXTURE);
            if (this.client.interactionManager.hasStatusBars()) {
                this.renderStatusBars(matrices);
            }
//            this.renderMountHealth(matrices);
            RenderSystem.disableBlend();
//            int i = this.scaledWidth / 2 - 91;
//            JumpingMount jumpingMount = this.client.player.getJumpingMount();
//            if (jumpingMount != null) {
//                this.renderMountJumpBar(jumpingMount, matrices, i);
//            } else if (this.client.interactionManager.hasExperienceBar()) {
//                this.renderExperienceBar(matrices, i);
//            }
            if (this.client.options.heldItemTooltips && this.client.interactionManager.getCurrentGameMode() != GameMode.SPECTATOR) {
                this.renderHeldItemTooltip(matrices);
            } else if (this.client.player.isSpectator()) {
                this.spectatorHud.render(matrices);
            }
        }
        if (this.client.player.getSleepTimer() > 0) {
            this.client.getProfiler().push("sleep");
            RenderSystem.disableDepthTest();
            float h = this.client.player.getSleepTimer();
            float j = h / 100.0f;
            if (j > 1.0f) {
                j = 1.0f - (h - 100.0f) / 10.0f;
            }
            k = (int)(220.0f * j) << 24 | 0x101020;
            InGameHud.fill(matrices, 0, 0, this.scaledWidth, this.scaledHeight, k);
            RenderSystem.enableDepthTest();
            this.client.getProfiler().pop();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        if (this.client.isDemo()) {
            this.renderDemoTimer(matrices);
        }
        this.renderStatusEffectOverlay(matrices);
        if (this.client.options.debugEnabled) {
            this.debugHud.render(matrices);
        }
        if (!this.client.options.hudHidden) {
            ScoreboardObjective scoreboardObjective2;
            int n;
            int m;
            if (this.overlayMessage != null && this.overlayRemaining > 0) {
                this.client.getProfiler().push("overlayMessage");
                float h = (float)this.overlayRemaining - tickDelta;
                int l = (int)(h * 255.0f / 20.0f);
                if (l > 255) {
                    l = 255;
                }
                if (l > 8) {
                    matrices.push();
                    matrices.translate(this.scaledWidth / 2, this.scaledHeight - 68, 0.0f);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    k = 0xFFFFFF;
                    if (this.overlayTinted) {
                        k = MathHelper.hsvToRgb(h / 50.0f, 0.7f, 0.6f) & 0xFFFFFF;
                    }
                    m = l << 24 & 0xFF000000;
                    n = textRenderer.getWidth(this.overlayMessage);
                    this.drawTextBackground(matrices, textRenderer, -4, n, 0xFFFFFF | m);
                    textRenderer.drawWithShadow(matrices, this.overlayMessage, (float)(-n / 2), -4.0f, k | m);
                    RenderSystem.disableBlend();
                    matrices.pop();
                }
                this.client.getProfiler().pop();
            }
            if (this.title != null && this.titleRemainTicks > 0) {
                this.client.getProfiler().push("titleAndSubtitle");
                float h = (float)this.titleRemainTicks - tickDelta;
                int l = 255;
                if (this.titleRemainTicks > this.titleFadeOutTicks + this.titleStayTicks) {
                    float o = (float)(this.titleFadeInTicks + this.titleStayTicks + this.titleFadeOutTicks) - h;
                    l = (int)(o * 255.0f / (float)this.titleFadeInTicks);
                }
                if (this.titleRemainTicks <= this.titleFadeOutTicks) {
                    l = (int)(h * 255.0f / (float)this.titleFadeOutTicks);
                }
                if ((l = MathHelper.clamp(l, 0, 255)) > 8) {
                    matrices.push();
                    matrices.translate(this.scaledWidth / 2, this.scaledHeight / 2, 0.0f);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    matrices.push();
                    matrices.scale(4.0f, 4.0f, 4.0f);
                    int k2 = l << 24 & 0xFF000000;
                    m = textRenderer.getWidth(this.title);
                    this.drawTextBackground(matrices, textRenderer, -10, m, 0xFFFFFF | k2);
                    textRenderer.drawWithShadow(matrices, this.title, (float)(-m / 2), -10.0f, 0xFFFFFF | k2);
                    matrices.pop();
                    if (this.subtitle != null) {
                        matrices.push();
                        matrices.scale(2.0f, 2.0f, 2.0f);
                        n = textRenderer.getWidth(this.subtitle);
                        this.drawTextBackground(matrices, textRenderer, 5, n, 0xFFFFFF | k2);
                        textRenderer.drawWithShadow(matrices, this.subtitle, (float)(-n / 2), 5.0f, 0xFFFFFF | k2);
                        matrices.pop();
                    }
                    RenderSystem.disableBlend();
                    matrices.pop();
                }
                this.client.getProfiler().pop();
            }
            this.subtitlesHud.render(matrices);
            Scoreboard scoreboard = this.client.world.getScoreboard();
            ScoreboardObjective scoreboardObjective = null;
            Team team = scoreboard.getPlayerTeam(this.client.player.getEntityName());
            if (team != null && (m = team.getColor().getColorIndex()) >= 0) {
                scoreboardObjective = scoreboard.getObjectiveForSlot(3 + m);
            }
            ScoreboardObjective scoreboardObjective3 = scoreboardObjective2 = scoreboardObjective != null ? scoreboardObjective : scoreboard.getObjectiveForSlot(1);
            if (scoreboardObjective2 != null) {
                this.renderScoreboardSidebar(matrices, scoreboardObjective2);
            }
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            n = MathHelper.floor(this.client.mouse.getX() * (double)window.getScaledWidth() / (double)window.getWidth());
            int p = MathHelper.floor(this.client.mouse.getY() * (double)window.getScaledHeight() / (double)window.getHeight());
            this.client.getProfiler().push("chat");
            this.chatHud.render(matrices, this.ticks, n, p);
            this.client.getProfiler().pop();
            scoreboardObjective2 = scoreboard.getObjectiveForSlot(0);
            if (this.client.options.playerListKey.isPressed() && (!this.client.isInSingleplayer() || this.client.player.networkHandler.getListedPlayerListEntries().size() > 1 || scoreboardObjective2 != null)) {
                this.playerListHud.setVisible(true);
                this.playerListHud.render(matrices, this.scaledWidth, scoreboard, scoreboardObjective2);
            } else {
                this.playerListHud.setVisible(false);
            }
            this.renderAutosaveIndicator(matrices);
        }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    private void renderCrosshair(MatrixStack matrices) {
        GameOptions gameOptions = this.client.options;
        if (!gameOptions.getPerspective().isFirstPerson()) {
            return;
        }
        if (this.client.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR && !this.shouldRenderSpectatorCrosshair(this.client.crosshairTarget)) {
            return;
        }
        if (gameOptions.debugEnabled && !gameOptions.hudHidden && !this.client.player.hasReducedDebugInfo() && !gameOptions.getReducedDebugInfo().getValue().booleanValue()) {
            Camera camera = this.client.gameRenderer.getCamera();
            MatrixStack matrixStack = RenderSystem.getModelViewStack();
            matrixStack.push();
            matrixStack.translate(this.scaledWidth / 2, this.scaledHeight / 2, this.getZOffset());
            matrixStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(camera.getPitch()));
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw()));
            matrixStack.scale(-1.0f, -1.0f, -1.0f);
            RenderSystem.applyModelViewMatrix();
            RenderSystem.renderCrosshair(10);
            matrixStack.pop();
            RenderSystem.applyModelViewMatrix();
        } else {
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
            int i = 15;
            this.drawTexture(matrices, (this.scaledWidth - 15) / 2, (this.scaledHeight - 15) / 2, 0, 0, 15, 15);
            /*if (this.client.options.getAttackIndicator().getValue() == AttackIndicator.CROSSHAIR) {
                float f = this.client.player.getAttackCooldownProgress(0.0f);
                boolean bl = false;
                if (this.client.targetedEntity != null && this.client.targetedEntity instanceof LivingEntity && f >= 1.0f) {
                    bl = this.client.player.getAttackCooldownProgressPerTick() > 5.0f;
                    bl &= this.client.targetedEntity.isAlive();
                }
                int j = this.scaledHeight / 2 - 7 + 16;
                int k = this.scaledWidth / 2 - 8;
                if (bl) {
                    this.drawTexture(matrices, k, j, 68, 94, 16, 16);
                } else if (f < 1.0f) {
                    int l = (int)(f * 17.0f);
                    this.drawTexture(matrices, k, j, 36, 94, 16, 4);
                    this.drawTexture(matrices, k, j, 52, 94, l, 4);
                }
            }*/
        }
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    private void renderHotbar(float tickDelta, MatrixStack matrices) {
        float f;
        int p;
        int o;
        int n;
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity == null) {
            return;
        }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, HOTBAR_TEXTURE);
        int i = this.scaledWidth / 2;
        int j = this.getZOffset();
        int k = 182;
        int l = 91;
        int hotbarTextureWidth = 194; // refer to MxtEquipmentInventoryScreen, done to align hotbar and inventory screen
        int x_main_inventory = (this.scaledWidth - hotbarTextureWidth) / 2; // refer to MxtEquipmentInventoryScreen, done to align hotbar and inventory screen
        int y_main_inventory = 5; // refer to MxtEquipmentInventoryScreen, done to align hotbar and inventory screen
        this.setZOffset(-90);
        this.drawTexture(matrices, x_main_inventory, y_main_inventory + 10, 0, 0, hotbarTextureWidth, 32);
        RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
        this.drawTexture(matrices, x_main_inventory + 4 + ((DuckPlayerEntityMixin)playerEntity).getMxtPlayerInventory().selectedSlot * 18, 19, 0, 22, 24, 24);
        this.setZOffset(j);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        int m = 1;
        for (n = 0; n < 10; ++n) {
            o = x_main_inventory + n * 18 + 8;
            p = y_main_inventory + 18;
            this.renderHotbarItem(o, p, tickDelta, playerEntity, ((DuckPlayerEntityMixin)playerEntity).getMxtPlayerInventory().main.get(n + 40), m++);
        }
        /*
        if (this.client.options.getAttackIndicator().getValue() == AttackIndicator.HOTBAR && (f = this.client.player.getAttackCooldownProgress(0.0f)) < 1.0f) {
            o = this.scaledHeight - 20;
            p = i + 91 + 6;
            if (arm == Arm.RIGHT) {
                p = i - 91 - 22;
            }
            RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
            int q = (int)(f * 19.0f);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexture(matrices, p, o, 0, 94, 18, 18);
            this.drawTexture(matrices, p, o + 18 - q, 18, 112 - q, 18, q);
        }*/
        RenderSystem.disableBlend();
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    private void renderStatusBars(MatrixStack matrices) {
        int ac;
        int ab;
        int aa;
        int z;
        int y;
        int x;
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity == null) {
            return;
        }
        /*
        int playerHealth = MathHelper.ceil(playerEntity.getHealth());
//        boolean bl = this.heartJumpEndTick > (long)this.ticks && (this.heartJumpEndTick - (long)this.ticks) / 3L % 2L == 1L;
        long l = Util.getMeasuringTimeMs();
        if (playerHealth < this.lastHealthValue && playerEntity.timeUntilRegen > 0) {
            this.lastHealthCheckTime = l;
//            this.heartJumpEndTick = this.ticks + 20;
        } else if (playerHealth > this.lastHealthValue && playerEntity.timeUntilRegen > 0) {
            this.lastHealthCheckTime = l;
//            this.heartJumpEndTick = this.ticks + 10;
        }
        if (l - this.lastHealthCheckTime > 1000L) {
            this.lastHealthValue = playerHealth;
            this.renderHealthValue = playerHealth;
            this.lastHealthCheckTime = l;
        }
        this.lastHealthValue = playerHealth;
        int j = this.renderHealthValue;*/
        this.random.setSeed(this.ticks * 312871);
//        HungerManager hungerManager = playerEntity.getHungerManager();
//        int k = hungerManager.getFoodLevel();
        int healthBarX = this.scaledWidth - 150;
        int healthBarY = 10;
        int manaBarX = healthBarX + 130;
        int manaBarY = 10;
        int j = this.getZOffset();
        this.setZOffset(-90);
//        float maxHealth = Math.max((float)playerEntity.getAttributeValue(EntityAttributesRegistry.MXT_MAX_HEALTH), (float)Math.max(j, playerHealth));
//        int p = MathHelper.ceil(playerEntity.getAbsorptionAmount());
//        int q = MathHelper.ceil((f + (float)p) / 2.0f / 10.0f);
//        int r = Math.max(10 - (q - 2), 3);
//        int s = o - (q - 1) * r - 10;
//        int t = o - 10;
//        int u = playerEntity.getArmor();
//        int v = -1;
//        if (playerEntity.hasStatusEffect(StatusEffects.REGENERATION)) {
//            v = this.ticks % MathHelper.ceil(f + 5.0f);
//        }
//        this.client.getProfiler().push("armor");
//        for (int w = 0; w < 10; ++w) {
//            if (u <= 0) continue;
//            x = m + w * 8;
//            if (w * 2 + 1 < u) {
//                this.drawTexture(matrices, x, s, 34, 9, 9, 9);
//            }
//            if (w * 2 + 1 == u) {
//                this.drawTexture(matrices, x, s, 25, 9, 9, 9);
//            }
//            if (w * 2 + 1 <= u) continue;
//            this.drawTexture(matrices, x, s, 16, 9, 9, 9);
//        }
        this.client.getProfiler().push("health");
        this.mxtRenderHealthBar(matrices, playerEntity, healthBarX, healthBarY);
//        t -= 10;
        this.client.getProfiler().swap("mana");
        this.mxtRenderManaBar(matrices, playerEntity, manaBarX, manaBarY);
        this.client.getProfiler().swap("lava");
        this.client.getProfiler().swap("air");
//        y = playerEntity.getMaxAir();
//        z = Math.min(playerEntity.getAir(), y);
//        if (playerEntity.isSubmergedIn(FluidTags.WATER) || z < y) {
//            aa = this.getHeartRows(x) - 1;
//            t -= aa * 10;
//            ab = MathHelper.ceil((double)(z - 2) * 10.0 / (double)y);
//            ac = MathHelper.ceil((double)z * 10.0 / (double)y) - ab;
//            for (int ad = 0; ad < ab + ac; ++ad) {
//                if (ad < ab) {
//                    this.drawTexture(matrices, n - ad * 8 - 9, t, 16, 18, 9, 9);
//                    continue;
//                }
//                this.drawTexture(matrices, n - ad * 8 - 9, t, 25, 18, 9, 9);
//            }
//        }
        this.client.getProfiler().pop();
        this.setZOffset(j);
    }

    private void mxtRenderHealthBar(MatrixStack matrices, PlayerEntity player, int x, int y) {
        int heartCount = ((DuckPlayerEntityMixin)player).mxtGetLifeCrystalsConsumed() + 5;
        int goldenHeartCount = ((DuckPlayerEntityMixin)player).mxtGetLifeFruitsConsumed();
        boolean hasLifeForceEffect = player.hasStatusEffect(StatusEffectRegistry.LIFEFORCE);
        int currentHealth = (int) player.getHealth();

        // draw containers
        for (int i = 0; i < heartCount; i++) {
            if (i > 9) {
                this.drawHeart(matrices, x + (i - 10) * 12, y + 12, false, 0);
            } else {
                this.drawHeart(matrices, x + i * 12, y, false, 0);
            }
        }
        if (hasLifeForceEffect) {

        } else {
            for (int i = 0; i < heartCount; i++) {
                boolean isGolden = i < goldenHeartCount;
                int type = (currentHealth >= (i * (isGolden ? 25 : 20)) + (isGolden ? 25 : 20)) ? 1 : (currentHealth >= (i * (isGolden ? 25 : 20)) + (isGolden ? 20 : 16)) ? 2 : (currentHealth >= (i * (isGolden ? 25 : 20)) + (isGolden ? 15 : 12)) ? 3 : (currentHealth >= (i * (isGolden ? 25 : 20)) + (isGolden ? 10 : 8)) ? 4 : (currentHealth >= (i * (isGolden ? 25 : 20)) + (isGolden ? 5 : 4)) ? 5 : 6;
//                int yOffset = i > 9 ? 12 : 0;
                if (i > 9) {
                    this.drawHeart(matrices, x + (i - 10) * 12, y + 12, isGolden, type);
                } else {
                    this.drawHeart(matrices, x + i * 12, y, isGolden, type);
                }
            }
        }
//        InGameHud.HeartType heartType = InGameHud.HeartType.fromPlayerState(player);
//        int i = 9 * (player.world.getLevelProperties().isHardcore() ? 5 : 0);
//        int j = MathHelper.ceil((double)maxHealth / 2.0);
//        int k = MathHelper.ceil((double)absorption / 2.0);
//        int l = j * 2;
//        for (int m = j + k - 1; m >= 0; --m) {
//            boolean bl3;
//            int s;
//            boolean bl;
//            int n = m / 10;
//            int o = m % 10;
//            int p = x + o * 8;
//            int q = y - n * lines;
//            if (lastHealth + absorption <= 4) {
//                q += this.random.nextInt(2);
//            }
//            if (m < j && m == regeneratingHeartIndex) {
//                q -= 2;
//            }
//            this.drawHeart(matrices, InGameHud.HeartType.CONTAINER, p, q, i, blinking, false);
//            int r = m * 2;
//            boolean bl2 = bl = m >= j;
//            if (bl && (s = r - l) < absorption) {
//                boolean bl22 = s + 1 == absorption;
//                this.drawHeart(matrices, heartType == InGameHud.HeartType.WITHERED ? heartType : InGameHud.HeartType.ABSORBING, p, q, i, false, bl22);
//            }
//            if (blinking && r < health) {
//                bl3 = r + 1 == health;
//                this.drawHeart(matrices, p, q, i, true, bl3);
//            }
//            if (r >= lastHealth) continue;
//            bl3 = r + 1 == lastHealth;
//            this.drawHeart(matrices, p, q, i, false, bl3);
//        }
    }

    private void mxtRenderManaBar(MatrixStack matrices, PlayerEntity player, int x, int y) {
        int manaCrystalCount = (int) Math.ceil(player.getAttributeValue(EntityAttributesRegistry.MXT_MAX_MANA) / 20);

//        int goldenHeartCount = ((DuckPlayerEntityMixin)player).mxtGetLifeFruitsConsumed();
//        boolean hasLifeForceEffect = player.hasStatusEffect(StatusEffectRegistry.LIFEFORCE);
        int currentMana = (int) ((DuckPlayerEntityMixin)player).mxtGetMana();

        // draw containers
        for (int i = 0; i < manaCrystalCount; i++) {
            this.drawManaCrystal(matrices, x, y + i * 12, 0);
        }

        for (int i = 0; i < manaCrystalCount; i++) {
            int type = (currentMana >= (i * 20)) ? 1 : (currentMana >= (i * 20) + 15) ? 2 : (currentMana >= (i * 20) + 10) ? 3 : (currentMana >= (i * 20) + 5) ? 4 : 5;
            this.drawManaCrystal(matrices, x, y + i * 12, type);
        }
    }

    private void drawHeart(MatrixStack matrices, int x, int y, boolean isGolden, int type) {
        this.drawTexture(matrices, x, y, type * 11, isGolden ? 11 : 0, 11, 11);
    }

    private void drawManaCrystal(MatrixStack matrices, int x, int y, int type) {
        this.drawTexture(matrices, x, y, type * 11, 22, 11, 11);
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    private void tick() {
        if (this.overlayRemaining > 0) {
            --this.overlayRemaining;
        }
        if (this.titleRemainTicks > 0) {
            --this.titleRemainTicks;
            if (this.titleRemainTicks <= 0) {
                this.title = null;
                this.subtitle = null;
            }
        }
        ++this.ticks;
        Entity entity = this.client.getCameraEntity();
        if (entity != null) {
            this.updateVignetteDarkness(entity);
        }
        if (this.client.player != null) {
            ItemStack itemStack = ((DuckPlayerEntityMixin)this.client.player).getMxtPlayerInventory().getMainHandStack();
            if (itemStack.isEmpty()) {
                this.heldItemTooltipFade = 0;
            } else if (this.currentStack.isEmpty() || !itemStack.isOf(this.currentStack.getItem()) || !itemStack.getName().equals(this.currentStack.getName())) {
                this.heldItemTooltipFade = 40;
            } else if (this.heldItemTooltipFade > 0) {
                --this.heldItemTooltipFade;
            }
            this.currentStack = itemStack;
        }
        this.chatHud.tickRemovalQueueIfExists();
    }

}
