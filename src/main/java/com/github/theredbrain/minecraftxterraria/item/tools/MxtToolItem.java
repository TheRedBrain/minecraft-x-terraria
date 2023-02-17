package com.github.theredbrain.minecraftxterraria.item.tools;

import com.github.theredbrain.minecraftxterraria.item.MxtMeleeWeaponItem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;

public abstract class MxtToolItem extends MxtMeleeWeaponItem {
    int miningSpeed;
    public MxtToolItem(Settings settings, int baseRarity, int researchAmount, int sellPrice, boolean autoSwing, int useTime, int attackDamage, int bonus, int criticalChance, double knockback, @Nullable Pair<StatusEffectInstance, Float> statusEffect, int miningSpeed) {
        super(settings, baseRarity, researchAmount, sellPrice, autoSwing, useTime, attackDamage, bonus, criticalChance, knockback, statusEffect);
        this.miningSpeed = miningSpeed;
    }

    public int getMiningSpeed() {
        return this.miningSpeed;
    }
}
