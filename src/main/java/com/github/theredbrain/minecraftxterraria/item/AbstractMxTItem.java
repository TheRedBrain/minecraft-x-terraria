package com.github.theredbrain.minecraftxterraria.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractMxTItem extends Item {
    int baseRarity;
    int researchAmount;
    int useTime;
    public AbstractMxTItem(Settings settings, int baseRarity, int researchAmount, int useTime) {
        super(settings);
        this.baseRarity = baseRarity;
        this.researchAmount = researchAmount;
        this.useTime = useTime;
    }

    public int getResearchAmount() {
        return this.researchAmount;
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return false;
    }

    @Override
    public Text getName(ItemStack stack) {
        String modifierPrefix = "";
        int modifierRarity = 0;
        // check if stack has a modifier
            // get modifierPrefix and modifierRarity from stack
        int finalRarity = baseRarity + modifierRarity;
        String modifiedName = modifierPrefix + this.getTranslationKey(stack);
        switch (finalRarity) {
            case -2:
            case -1:
                return Text.translatable(modifiedName).formatted(Formatting.GRAY);
            case 1:
                return Text.translatable(modifiedName).formatted(Formatting.BLUE);
            case 2:
                return Text.translatable(modifiedName).formatted(Formatting.DARK_GREEN);
            case 3:
                return Text.translatable(modifiedName).formatted(Formatting.GOLD);
            case 4:
                return Text.translatable(modifiedName).formatted(Formatting.RED);
            case 5:
                return Text.translatable(modifiedName).formatted(Formatting.LIGHT_PURPLE);
            case 6:
                return Text.translatable(modifiedName).formatted(Formatting.AQUA);
            case 7:
                return Text.translatable(modifiedName).formatted(Formatting.GREEN);
            case 8:
                return Text.translatable(modifiedName).formatted(Formatting.YELLOW);
            case 9:
                return Text.translatable(modifiedName).formatted(Formatting.DARK_AQUA);
            case 10:
                return Text.translatable(modifiedName).formatted(Formatting.DARK_RED);
            case 11:
                return Text.translatable(modifiedName).formatted(Formatting.DARK_PURPLE);
            case 12:
                return Text.translatable(modifiedName).formatted(Formatting.DARK_BLUE);
            case 13:
                return Text.translatable(modifiedName).formatted(Formatting.DARK_BLUE, Formatting.BOLD);
            case 14:
                return Text.translatable(modifiedName).formatted(Formatting.GOLD, Formatting.ITALIC);
            default:
                return Text.translatable(modifiedName).formatted(Formatting.WHITE);
        }
    }

    @Override
    public boolean isFireproof() {
        return this.baseRarity != 0;
    }
}
