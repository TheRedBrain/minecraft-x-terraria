package com.github.theredbrain.minecraftxterraria.client.gui.screen;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerEntityMixin;
import com.github.theredbrain.minecraftxterraria.entity.player.PlayerKeys;
import com.github.theredbrain.minecraftxterraria.util.NetworkingChannels;
import com.github.theredbrain.minecraftxterraria.world.WorldKeys;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

@Environment(value= EnvType.CLIENT)
public class MxtCharacterCreationScreen extends Screen {

    protected int x_main;
    protected int y_main;

    private static final Text PLAYER_DIFFICULTY_TEXT = Text.translatable("selectWorld.gameMode"); // TODO
    private static final Text SAVING_LEVEL_TEXT = Text.translatable("menu.savingLevel");
    private static final Text RETURN_TO_MENU_TEXT = Text.translatable("menu.returnToMenu");
    private static final Text DISCONNECT_TEXT = Text.translatable("menu.disconnect");
    private GameMode currentMode = GameMode.CREATIVE;
    private ButtonWidget createCharacterButton;
    private CyclingButtonWidget<GameMode> gameModeSwitchButton;
    private Text firstGameModeDescriptionLine;
    private Text secondGameModeDescriptionLine;

    public MxtCharacterCreationScreen() {
//        super(NarratorManager.EMPTY);
        super(Text.of("Character Creation")/*Text.translatable("selectWorld.create")*/); // TODO make Text translatable
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        this.x_main = this.width / 2 - 200;
        this.y_main = this.height / 2 - 200;
        int i = this.width / 2;
        int j = this.height / 2;
        this.gameModeSwitchButton = this.addDrawableChild(CyclingButtonWidget
                .builder(GameMode::getSimpleTranslatableName).values((GameMode[]) new GameMode[]{GameMode.SURVIVAL, GameMode.CREATIVE, GameMode.ADVENTURE, GameMode.SPECTATOR})
                .initially(this.currentMode)
                /*.narration(button -> ClickableWidget.getNarrationMessage(button.getMessage()).append(ScreenTexts.SENTENCE_SEPARATOR).append(this.firstGameModeDescriptionLine).append(" ").append(this.secondGameModeDescriptionLine))*/
                .build(i, 100, 150, 20, PLAYER_DIFFICULTY_TEXT, (button, mode) -> this.tweakDefaultsTo((GameMode) ((Object) mode))));
        this.createCharacterButton = this.addDrawableChild(ButtonWidget.builder(Text.of("Create Character")/*.translatable("mxt.createCharacter.create")*/, button -> this.createCharacter())
                .dimensions(i - 170, j + 28, 150, 20).build());
        Text text = this.client.isInSingleplayer() ? RETURN_TO_MENU_TEXT : DISCONNECT_TEXT;
        this.addDrawableChild(ButtonWidget.builder(text, button -> {
            button.active = false;
            this.client.getAbuseReportContext().tryShowDraftScreen(this.client, this, this::disconnect, true);
        }).dimensions(i + 20, j + 28 , 204, 20).build());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
//        this.drawBackground(matrices, delta, mouseX, mouseY);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, -1);
//        CreateWorldScreen.drawTextWithShadow(matrices, this.textRenderer, this.firstGameModeDescriptionLine, this.width / 2 - 150, 122, -6250336);
//        CreateWorldScreen.drawTextWithShadow(matrices, this.textRenderer, this.secondGameModeDescriptionLine, this.width / 2 - 150, 134, -6250336);
        super.render(matrices, mouseX, mouseY, delta);
    }

    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

    }

    @Override
    public void renderBackground(MatrixStack matrices, int vOffset) {
        this.renderBackgroundTexture(vOffset);
    }

    private void disconnect() {
        boolean bl = this.client.isInSingleplayer();
        boolean bl2 = this.client.isConnectedToRealms();
        this.client.world.disconnect();
        if (bl) {
            this.client.disconnect(new MessageScreen(SAVING_LEVEL_TEXT));
        } else {
            this.client.disconnect();
        }
        TitleScreen titleScreen = new TitleScreen();
        if (bl) {
            this.client.setScreen(titleScreen);
        } else if (bl2) {
            this.client.setScreen(new RealmsMainScreen(titleScreen));
        } else {
            this.client.setScreen(new MultiplayerScreen(titleScreen));
        }
    }

    private void updateSettingsLabels() {
        this.firstGameModeDescriptionLine = Text.translatable("selectWorld.gameMode." + this.currentMode.getName() + ".line1");
        this.secondGameModeDescriptionLine = Text.translatable("selectWorld.gameMode." + this.currentMode.getName() + ".line2");
    }

    // TODO set playerKeys
    //  set player gameMode

    private void tweakDefaultsTo(GameMode gameMode) {
        this.currentMode = gameMode;
        this.updateSettingsLabels();
    }

    private void createCharacter() {
        this.client.interactionManager.setGameMode(this.currentMode);
        ((DuckPlayerEntityMixin)this.client.player).mxtSetCharacterCreated(true);
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(true);
        this.client.getNetworkHandler().sendPacket(new CustomPayloadC2SPacket(NetworkingChannels.CHARACTER_CREATION, buf));
        MinecraftXTerraria.LOGGER.info("player " + this.client.player.getUuid().toString() + "character is created: " + ((DuckPlayerEntityMixin)this.client.player).mxtGetCharacterCreated());

        // TODO send packet to server to switch playerKeys.characterCreated to true
//        PlayerKeys playerKeys = WorldKeys.getPlayerKeys(this.client.player);
//        playerKeys.characterCreated = true;

        this.close();
    }
}
