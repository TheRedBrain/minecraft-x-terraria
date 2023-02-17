package com.github.theredbrain.minecraftxterraria.entity.player;

import com.github.theredbrain.minecraftxterraria.entity.MxtEquipmentSlot;
import com.github.theredbrain.minecraftxterraria.screen.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

public interface DuckPlayerEntityMixin {

    boolean mxtHasStackEquipped(MxtEquipmentSlot slot);
    ItemStack mxtGetEquippedStack(MxtEquipmentSlot slot);
    boolean mxtIsArmorSlot(MxtEquipmentSlot slot);
    void mxtEquipStack(MxtEquipmentSlot slot, ItemStack stack);
    boolean mxtGiveItemStack(ItemStack stack);
    void mxtOnEquipStack(MxtEquipmentSlot slot, ItemStack oldStack, ItemStack newStack);
//    MxtAbstractPlayerScreenHandler getMxtPlayerScreenHandler();
//    MxtEquipmentPlayerScreenHandler getMxtEquipmentPlayerScreenHandler();
//    MxtFirstLoadoutPlayerScreenHandler getMxtFirstLoadoutPlayerScreenHandler();
//    MxtSecondLoadoutPlayerScreenHandler getMxtSecondLoadoutPlayerScreenHandler();
    MxtPlayerInventoryScreenHandler getMxtPlayerInventoryScreenHandler();
    MxtPlayerInventory getMxtPlayerInventory();
//    MxtGearPlayerInventory getMxtGearPlayerInventory();

    Screen mxtGetCurrentPlayerInventoryScreen();
    ScreenHandler mxtGetCurrentPlayerInventoryScreenHandler();

    int getActiveLoadout();
    void setActiveLoadout(int activeLoadout);
    int getActiveInventoryTab();
    void setActiveInventoryTab(int activeInventoryTab);

    // attribute pools
    void mxtAddHealthRegenerationTime(float amount);
    float mxtGetHealthRegenerationTime();
    void mxtSetHealthRegenerationTime(float healthRegenerationTime);
    float mxtGetEffectiveHealthRegenerationTime();
    void mxtSetEffectiveHealthRegenerationTime(float effectiveHealthRegenerationTime);
    void mxtAddHealthRegenerationCounter(float amount);
    float mxtGetHealthRegenerationCounter();
    void mxtSetHealthRegenerationCounter(float healthRegenerationCounter);
    float mxtGetMaxMana();
    void mxtAddMana(float amount);
    float mxtGetMana();
    void mxtSetMana(float mana);
    void mxtReduceManaRegenerationDelay(float amount);
    float mxtGetManaRegenerationDelay();
    void mxtSetManaRegenerationDelay(float manaRegenerationDelay);
    void mxtAddManaRegenerationCounter(float amount);
    float mxtGetManaRegenerationCounter();
    void mxtSetManaRegenerationCounter(float manaRegenerationCounter);

    // player keys
    boolean mxtGetAegisFruitConsumed();
    void mxtSetAegisFruitConsumed(boolean key);
    boolean mxtGetAmbrosiaConsumed();
    void mxtSetAmbrosiaConsumed(boolean key);
    boolean mxtGetArcaneCrystalConsumed();
    void mxtSetArcaneCrystalConsumed(boolean key);
    boolean mxtGetArtisanLoafConsumed();
    void mxtSetArtisanLoafConsumed(boolean key);
    boolean mxtGetDemonHeartConsumed();
    void mxtSetDemonHeartConsumed(boolean key);
    boolean mxtGetGalaxyPearlConsumed();
    void mxtSetGalaxyPearlConsumed(boolean key);
    boolean mxtGetGummyWormConsumed();
    void mxtSetGummyWormConsumed(boolean key);
    boolean mxtGetMminecartUpgradeConsumed();
    void mxtSetMminecartUpgradeConsumed(boolean key);
    boolean mxtGetTorchGodFavorConsumed();
    void mxtSetTorchGodFavorConsumed(boolean key);
    boolean mxtGetVitalCrystalConsumed();
    void mxtSetVitalCrystalConsumed(boolean key);
    boolean mxtGetCharacterCreated();
    void mxtSetCharacterCreated(boolean key);

    void mxtAddLifeCrystalsConsumed(int amount);
    int mxtGetLifeCrystalsConsumed();
    void mxtSetLifeCrystalsConsumed(int lifeCrystalsConsumed);
    void mxtAddLifeFruitsConsumed(int amount);
    int mxtGetLifeFruitsConsumed();
    void mxtSetLifeFruitsConsumed(int lifeFruitsConsumed);
    void mxtAddManaCrystalsConsumed(int amount);
    int mxtGetManaCrystalsConsumed();
    void mxtSetManaCrystalsConsumed(int manaCrystalsConsumed);
    void mxtIncreaseCompletedAnglerQuests(int amount);
    int mxtGetCompletedAnglerQuests();
    void mxtSetCompletedAnglerQuests(int completedAnglerQuests);
}
