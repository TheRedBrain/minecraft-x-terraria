package com.github.theredbrain.minecraftxterraria.client.gui.screen.ingame;
//
//import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
//import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
//import com.github.theredbrain.minecraftxterraria.screen.MxtEquipmentPlayerScreenHandler;
//import com.github.theredbrain.minecraftxterraria.screen.MxtFirstLoadoutPlayerScreenHandler;
//import com.google.common.collect.Ordering;
//import com.mojang.blaze3d.systems.RenderSystem;
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.minecraft.client.render.GameRenderer;
//import net.minecraft.client.texture.Sprite;
//import net.minecraft.client.texture.StatusEffectSpriteManager;
//import net.minecraft.client.util.math.MatrixStack;
//import net.minecraft.entity.effect.StatusEffect;
//import net.minecraft.entity.effect.StatusEffectInstance;
//import net.minecraft.entity.effect.StatusEffectUtil;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.text.MutableText;
//import net.minecraft.text.Text;
//import net.minecraft.util.Identifier;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//
//@Environment(value= EnvType.CLIENT)
//public class MxtFirstLoadoutInventoryScreen  extends MxtHandledScreen<MxtFirstLoadoutPlayerScreenHandler> {
//    private static final Identifier TAB_TEXTURES = new Identifier(MinecraftXTerraria.MOD_ID, "textures/gui/container/mxt_tabs.png");
//    private float mouseX;
//    private float mouseY;
//    //    private final RecipeBookWidget recipeBook = new RecipeBookWidget();
////    private
////    private boolean open;
////    private boolean narrow;
////    private boolean mouseDown;
//    private PlayerEntity owner;
//
//    public MxtFirstLoadoutInventoryScreen(PlayerEntity owner) {
//        super(((DuckPlayerEntityMixin)owner).getMxtFirstLoadoutPlayerScreenHandler(), ((DuckPlayerEntityMixin)owner).getMxtPlayerInventory(), Text.translatable("container.inventory"));
//        this.owner = owner;
//        this.passEvents = true;
//    }
//
//    @Override
//    public void handledScreenTick() {
//        this.client.setScreen(((DuckPlayerEntityMixin) this.owner).mxtGetCurrentPlayerInventoryScreen());
//        this.owner.currentScreenHandler = (((DuckPlayerEntityMixin) this.owner).mxtGetCurrentPlayerInventoryScreenHandler());
////        if (this.isEquipmentTab(mouseX, mouseY)) {
////        if (this.client.interactionManager.hasCreativeInventory()) {
////            this.client.setScreen(new CreativeInventoryScreen(this.client.player, this.client.player.networkHandler.getEnabledFeatures(), this.client.options.getOperatorItemsTab().getValue()));
////            return;
////        }
////        this.recipeBook.update();
//        // TODO update crafting recipe list
//    }
//
//    @Override
//    protected void init() {
//        super.init();
//        int hotbarTextureWidth = 194; // refer to InGameHudMixin, done to align hotbar and inventory screen
//        this.x_main_inventory = (this.width - hotbarTextureWidth) / 2; // refer to InGameHudMixin, done to align hotbar and inventory screen
//        this.y_main_inventory = 5; // refer to InGameHudMixin, done to align hotbar and inventory screen
//        this.x_gear_inventory_offset = ((DuckPlayerEntityMixin)owner).getMxtPlayerInventory().getXGearInventoryOffset();
//        this.y_gear_inventory_offset = ((DuckPlayerEntityMixin)owner).getMxtPlayerInventory().getYGearInventoryOffset();
//    }
//
//    @Override
//    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
//        this.textRenderer.draw(matrices, this.title, (float)this.titleX, (float)this.titleY, 0x404040);
//    }
//
//    @Override
//    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
//        this.renderBackground(matrices);
//        super.render(matrices, mouseX, mouseY, delta);
//        this.drawStatusEffects(matrices, mouseX, mouseY);
//        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
//        this.mouseX = mouseX;
//        this.mouseY = mouseY;
//    }
//
//    @Override
//    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
//
//        // main inventory
//        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
//        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
//        RenderSystem.setShaderTexture(0, MAIN_INVENTORY_BACKGROUND_TEXTURE);
//        this.drawTexture(matrices, this.x_main_inventory, this.y_main_inventory, 0, 0, this.mainInventoryBackgroundWidth, this.mainInventoryBackgroundHeight);
//
//        // trash slot
//        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
//        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
//        RenderSystem.setShaderTexture(0, TAB_TEXTURES);
//        this.drawTexture(matrices, this.x_main_inventory + 162, this.y_main_inventory + 114, 208, 0, 32, 26);
//
//        // inactive tabs
////        if (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveInventoryTab() == 2) {
////            // inactive equipment tab
////            this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + 46, this.y_main_inventory + this.y_gear_inventory_offset - 26, 78, 2, 26, 30);
////        } else if (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveInventoryTab() == 1) {
////            // inactive housing tab
////            this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + 20, this.y_main_inventory + this.y_gear_inventory_offset - 26, 78, 2, 26, 30);
////        } else {
//            // inactive housing tab
//            this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + 20, this.y_main_inventory + this.y_gear_inventory_offset - 26, 78, 2, 26, 30);
//            // inactive equipment tab
//            this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + 46, this.y_main_inventory + this.y_gear_inventory_offset - 26, 78, 2, 26, 30);
//            // inactive loadout tabs
////            if (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveLoadout() == 2) {
////                // inactive loadout 0 tab
////                this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + this.loadoutInventoryBackgroundWidth - 3, this.y_main_inventory + this.y_gear_inventory_offset, 0, 110, 32, 26);
////                // inactive loadout 1 tab
////                this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + this.loadoutInventoryBackgroundWidth - 3, this.y_main_inventory + this.y_gear_inventory_offset + 26, 0, 110, 32, 26);
////            } else if (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveLoadout() == 1) {
////                // inactive loadout 0 tab
////                this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + this.loadoutInventoryBackgroundWidth - 3, this.y_main_inventory + this.y_gear_inventory_offset, 0, 110, 32, 26);
////                // inactive loadout 2 tab
////                this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + this.loadoutInventoryBackgroundWidth - 3, this.y_main_inventory + this.y_gear_inventory_offset + 52, 0, 110, 32, 26);
////            } else {
//                // inactive loadout 1 tab
//                this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + this.loadoutInventoryBackgroundWidth - 3, this.y_main_inventory + this.y_gear_inventory_offset + 26, 0, 110, 32, 26);
//                // inactive loadout 2 tab
//                this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + this.loadoutInventoryBackgroundWidth - 3, this.y_main_inventory + this.y_gear_inventory_offset + 52, 0, 110, 32, 26);
////            }
////        }
//
//        // gear inventories
////        if (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveInventoryTab() == 2) {
////
////            // TODO temp
////            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
////            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
////            RenderSystem.setShaderTexture(0, EQUIPMENT_INVENTORY_BACKGROUND_TEXTURE);
////            this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + 20, this.y_main_inventory + this.y_gear_inventory_offset, 0, 0, this.equipmentInventoryBackgroundWidth, this.equipmentInventoryBackgroundHeight);
////        } else if (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveInventoryTab() == 1) {
////            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
////            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
////            RenderSystem.setShaderTexture(0, EQUIPMENT_INVENTORY_BACKGROUND_TEXTURE);
////            this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + 20, this.y_main_inventory + this.y_gear_inventory_offset, 0, 0, this.equipmentInventoryBackgroundWidth, this.equipmentInventoryBackgroundHeight);
////        } else {
//            // TODO check for different loadout sizes
//            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
//            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
//            RenderSystem.setShaderTexture(0, LOADOUT_INVENTORY_7_BACKGROUND_TEXTURE);
//            this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset, this.y_main_inventory + this.y_gear_inventory_offset, 0, 0, this.loadoutInventoryBackgroundWidth, this.loadoutInventory7BackgroundHeight);
////        }
//
//        // active tabs
//        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
//        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
//        RenderSystem.setShaderTexture(0, TAB_TEXTURES);
////        if (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveInventoryTab() == 2) {
////            // active housing tab
////            this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + 20, this.y_main_inventory + this.y_gear_inventory_offset - 28, 26, 0, 26, 32);
////        } else if (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveInventoryTab() == 1) {
////            // active equipment tab
////            this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + 46, this.y_main_inventory + this.y_gear_inventory_offset - 28, 52, 0, 26, 32);
////        } else {
////            if (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveLoadout() == 2) {
////                // active loadout 2 tab
////                this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + this.loadoutInventoryBackgroundWidth - 3, this.y_main_inventory + this.y_gear_inventory_offset + 52, 0, 58, 36, 26);
////            } else if (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveLoadout() == 1) {
////                // active loadout 1 tab
////                this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + this.loadoutInventoryBackgroundWidth - 3, this.y_main_inventory + this.y_gear_inventory_offset + 26, 0, 58, 36, 26);
////            } else {
//                // active loadout 0 tab
//                this.drawTexture(matrices, this.x_main_inventory + this.x_gear_inventory_offset + this.loadoutInventoryBackgroundWidth - 3, this.y_main_inventory + this.y_gear_inventory_offset, 0, 32, 36, 26);
//
////            }
////        }
//    }
//
//    @Override
//    public boolean mouseClicked(double mouseX, double mouseY, int button) {
//        if (button == 0) {
//            if (this.isHousingTab(mouseX, mouseY)) {
//                return true;
//            }
//            if (this.isEquipmentTab(mouseX, mouseY)) {
//                return true;
//            }
//            if (this.isLoadout1Tab(mouseX, mouseY)) {
//                return true;
//            }
//            if (this.isLoadout2Tab(mouseX, mouseY)) {
//                return true;
//            }
//            if (this.isLoadout3Tab(mouseX, mouseY)) {
//                return true;
//            }
//        }
//        return super.mouseClicked(mouseX, mouseY, button);
//    }
//
//    @Override
//    public boolean mouseReleased(double mouseX, double mouseY, int button) {
//        if (button == 0) {
//            // TODO disabled until later
////            if (this.isHousingTabClicked(mouseX, mouseY)) {
////                if (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveInventoryTab() == 2) {
////                    ((DuckPlayerEntityMixin) this.owner).getMxtPlayerInventory().setActiveInventoryTab(0);
////                } else {
////                    ((DuckPlayerEntityMixin) this.owner).getMxtPlayerInventory().setActiveInventoryTab(2);
////                }
////                return true;
////            }
//            if (this.isEquipmentTab(mouseX, mouseY)) {
//                if (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveInventoryTab() == 1) {
//                    ((DuckPlayerEntityMixin) this.owner).getMxtPlayerInventory().setActiveInventoryTab(0);
////                    ((DuckPlayerEntityMixin) this.owner).getMxtPlayerScreenHandler().setActiveGearInventory(((DuckPlayerEntityMixin) this.owner).getMxtPlayerInventory().getActiveLoadout() + 1);
//                } else {
//                    ((DuckPlayerEntityMixin) this.owner).getMxtPlayerInventory().setActiveInventoryTab(1);
////                    ((DuckPlayerEntityMixin) this.owner).getMxtPlayerScreenHandler().setActiveGearInventory(0);
////                    if (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveLoadout() == 2) {
////                        this.owner.currentScreenHandler = ((DuckPlayerEntityMixin) this.owner).getMxtThirdLoadoutPlayerScreenHandler();
////                    } else if (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveLoadout() == 1) {
////                        this.owner.currentScreenHandler = ((DuckPlayerEntityMixin) this.owner).getMxtSecondLoadoutPlayerScreenHandler();
////                    } else {
////                        this.owner.currentScreenHandler = ((DuckPlayerEntityMixin) this.owner).getMxtFirstLoadoutPlayerScreenHandler();
////                    }
//                }
////                this.client.setScreen(((DuckPlayerEntityMixin) this.owner).mxtGetCurrentPlayerInventoryScreen());
//                return true;
//            }
//            if (this.isLoadout1Tab(mouseX, mouseY)) {
//                ((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().setActiveInventoryTab(0);
//                ((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().setActiveLoadout(0);
////                ((DuckPlayerEntityMixin) this.owner).getMxtPlayerScreenHandler().setActiveGearInventory(((DuckPlayerEntityMixin) this.owner).getMxtPlayerInventory().getActiveLoadout() + 1);
////                this.client.setScreen(((DuckPlayerEntityMixin) this.owner).mxtGetCurrentPlayerInventoryScreen());
////                this.owner.currentScreenHandler = ((DuckPlayerEntityMixin) this.owner).getMxtFirstLoadoutPlayerScreenHandler();
//                return true;
//            }
//            if (this.isLoadout2Tab(mouseX, mouseY)) {
//                ((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().setActiveInventoryTab(0);
//                ((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().setActiveLoadout(1);
////                ((DuckPlayerEntityMixin) this.owner).getMxtPlayerScreenHandler().setActiveGearInventory(((DuckPlayerEntityMixin) this.owner).getMxtPlayerInventory().getActiveLoadout() + 1);
////                this.client.setScreen(((DuckPlayerEntityMixin) this.owner).mxtGetCurrentPlayerInventoryScreen());
////                this.owner.currentScreenHandler = ((DuckPlayerEntityMixin) this.owner).getMxtSecondLoadoutPlayerScreenHandler();
//                return true;
//            }
//            if (this.isLoadout3Tab(mouseX, mouseY)) {
//                ((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().setActiveInventoryTab(0);
//                ((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().setActiveLoadout(2);
////                ((DuckPlayerEntityMixin) this.owner).getMxtPlayerScreenHandler().setActiveGearInventory(((DuckPlayerEntityMixin) this.owner).getMxtPlayerInventory().getActiveLoadout() + 1);
////                this.client.setScreen(((DuckPlayerEntityMixin) this.owner).mxtGetCurrentPlayerInventoryScreen());
////                this.owner.currentScreenHandler = ((DuckPlayerEntityMixin) this.owner).getMxtThirdLoadoutPlayerScreenHandler();
//                return true;
//            }
//
//        }
//        return super.mouseReleased(mouseX, mouseY, button);
//    }
//
//    @Override
//    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
//        boolean outsideMainInventory = mouseX < (double)left || mouseY < (double)top || mouseX >= (double)(left + this.mainInventoryBackgroundWidth) || mouseY >= (double)(top + this.mainInventoryBackgroundHeight);
//        boolean outsideGearInventory = mouseX < (double)(left + this.x_gear_inventory_offset) || mouseY < (double)(top + this.y_gear_inventory_offset) || mouseX >= (double)(left + this.x_gear_inventory_offset + (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveInventoryTab() == 0 ? this.loadoutInventoryBackgroundWidth : this.equipmentInventoryBackgroundWidth)) || mouseY >= (double)(top + this.x_gear_inventory_offset + (((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveInventoryTab() == 0 ? this.loadoutInventoryBackgroundWidth : this.equipmentInventoryBackgroundHeight));
//        return outsideMainInventory && outsideGearInventory && !isTrashSlot(mouseX, mouseY) && !isHousingTab(mouseX, mouseY) && !isEquipmentTab(mouseX, mouseY) && !isLoadout1Tab(mouseX, mouseY) && !isLoadout2Tab(mouseX, mouseY) && !isLoadout3Tab(mouseX, mouseY);
//    }
//
//    private boolean isTrashSlot(double mouseX, double mouseY) {
//        return this.isPointWithinBounds(162, mainInventoryBackgroundHeight, 32, 22, mouseX, mouseY);
//    }
//
//    private boolean isHousingTab(double mouseX, double mouseY) {
//        boolean bl = ((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveInventoryTab() == 2;
//        return this.isPointWithinBounds(this.x_gear_inventory_offset + 20, bl ? this.y_gear_inventory_offset - 28 : this.y_gear_inventory_offset - 26, 26, bl ? 28 : 26, mouseX, mouseY);
//    }
//
//    private boolean isEquipmentTab(double mouseX, double mouseY) {
//        boolean bl = ((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveInventoryTab() == 1;
//        return this.isPointWithinBounds(this.x_gear_inventory_offset + 46, bl ? this.y_gear_inventory_offset - 28 : this.y_gear_inventory_offset - 26, 26, bl ? 28 : 26, mouseX, mouseY);
//    }
//
//    private boolean isLoadout1Tab(double mouseX, double mouseY) {
//        boolean bl = ((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveLoadout() == 0;
//        return ((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveInventoryTab() == 0 && this.isPointWithinBounds(this.x_gear_inventory_offset + this.loadoutInventoryBackgroundWidth, this.y_gear_inventory_offset, bl ? 33 : 29, 26, mouseX, mouseY);
//    }
//
//    private boolean isLoadout2Tab(double mouseX, double mouseY) {
//        boolean bl = ((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveLoadout() == 1;
//        return ((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveInventoryTab() == 0 && this.isPointWithinBounds(this.x_gear_inventory_offset + this.loadoutInventoryBackgroundWidth, this.y_gear_inventory_offset + 26, bl ? 33 : 29, 26, mouseX, mouseY);
//    }
//
//    private boolean isLoadout3Tab(double mouseX, double mouseY) {
//        boolean bl = ((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveLoadout() == 2;
//        return ((DuckPlayerEntityMixin)this.owner).getMxtPlayerInventory().getActiveInventoryTab() == 0 && this.isPointWithinBounds(this.x_gear_inventory_offset + this.loadoutInventoryBackgroundWidth, this.y_gear_inventory_offset + 52, bl ? 33 : 29, 26, mouseX, mouseY);
//    }
//
//    public boolean hideStatusEffectHud() {
//        int i = this.x_main_inventory + this.mainInventoryBackgroundWidth + 2;
//        int j = this.width - i;
//        return j >= 32;
//    }
//
//    private void drawStatusEffects(MatrixStack matrices, int mouseX, int mouseY) {
//        int i = this.x_main_inventory + this.mainInventoryBackgroundWidth + 2;
//        int j = this.width - i;
//        Collection<StatusEffectInstance> collection = this.client.player.getStatusEffects();
//        if (collection.isEmpty() || j < 32) {
//            return;
//        }
//        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
//        boolean bl = j >= 120;
//        int k = 33;
//        if (collection.size() > 5) {
//            k = 132 / (collection.size() - 1);
//        }
//        List<StatusEffectInstance> iterable = Ordering.natural().sortedCopy(collection);
//        this.drawStatusEffectBackgrounds(matrices, i, k, iterable, bl);
//        this.drawStatusEffectSprites(matrices, i, k, iterable, bl);
//        if (bl) {
//            this.drawStatusEffectDescriptions(matrices, i, k, iterable);
//        } else if (mouseX >= i && mouseX <= i + 33) {
//            int l = this.y_main_inventory;
//            StatusEffectInstance statusEffectInstance = null;
//            for (StatusEffectInstance statusEffectInstance2 : iterable) {
//                if (mouseY >= l && mouseY <= l + k) {
//                    statusEffectInstance = statusEffectInstance2;
//                }
//                l += k;
//            }
//            if (statusEffectInstance != null) {
//                List<Text> list = List.of(this.getStatusEffectDescription(statusEffectInstance), Text.literal(StatusEffectUtil.durationToString(statusEffectInstance, 1.0f)));
//                this.renderTooltip(matrices, list, Optional.empty(), mouseX, mouseY);
//            }
//        }
//    }
//
//    private void drawStatusEffectBackgrounds(MatrixStack matrices, int x, int height, Iterable<StatusEffectInstance> statusEffects, boolean wide) {
//        RenderSystem.setShaderTexture(0, MAIN_INVENTORY_BACKGROUND_TEXTURE);
//        int i = this.y_main_inventory;
//        for (StatusEffectInstance statusEffectInstance : statusEffects) {
//            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
//            if (wide) {
//                this.drawTexture(matrices, x, i, 0, 166, 120, 32);
//            } else {
//                this.drawTexture(matrices, x, i, 0, 198, 32, 32);
//            }
//            i += height;
//        }
//    }
//
//    private void drawStatusEffectSprites(MatrixStack matrices, int x, int height, Iterable<StatusEffectInstance> statusEffects, boolean wide) {
//        StatusEffectSpriteManager statusEffectSpriteManager = this.client.getStatusEffectSpriteManager();
//        int i = this.y_main_inventory;
//        for (StatusEffectInstance statusEffectInstance : statusEffects) {
//            StatusEffect statusEffect = statusEffectInstance.getEffectType();
//            Sprite sprite = statusEffectSpriteManager.getSprite(statusEffect);
//            RenderSystem.setShaderTexture(0, sprite.getAtlasId());
//            drawSprite(matrices, x + (wide ? 6 : 7), i + 7, this.getZOffset(), 18, 18, sprite);
//            i += height;
//        }
//    }
//
//    private void drawStatusEffectDescriptions(MatrixStack matrices, int x, int height, Iterable<StatusEffectInstance> statusEffects) {
//        int i = this.y_main_inventory;
//        for (StatusEffectInstance statusEffectInstance : statusEffects) {
//            Text text = this.getStatusEffectDescription(statusEffectInstance);
//            this.textRenderer.drawWithShadow(matrices, text, (float)(x + 10 + 18), (float)(i + 6), 0xFFFFFF);
//            String string = StatusEffectUtil.durationToString(statusEffectInstance, 1.0f);
//            this.textRenderer.drawWithShadow(matrices, string, (float)(x + 10 + 18), (float)(i + 6 + 10), 0x7F7F7F);
//            i += height;
//        }
//    }
//
//    private Text getStatusEffectDescription(StatusEffectInstance statusEffect) {
//        MutableText mutableText = statusEffect.getEffectType().getName().copy();
//        if (statusEffect.getAmplifier() >= 1 && statusEffect.getAmplifier() <= 9) {
//            mutableText.append(" ").append(Text.translatable("enchantment.level." + (statusEffect.getAmplifier() + 1)));
//        }
//        return mutableText;
//    }
//}