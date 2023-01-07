package com.github.theredbrain.minecraftxterraria.item.tool;

import com.github.theredbrain.minecraftxterraria.item.AbstractMxTItem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractMxTToolItem extends AbstractMxTItem {
    int attackDamage;
    int criticalChance;
    int bonus;
    int miningSpeed;
    int knockback;
    int sellPrice;
    private final Pair<StatusEffectInstance, Float> statusEffects;
    public AbstractMxTToolItem(Settings settings, int attackDamage, int criticalChance, int bonus, int useTime, int miningSpeed, int knockback, int baseRarity, int sellPrice, int researchAmount, @Nullable Pair<StatusEffectInstance, Float> statusEffect) {
        super(settings, baseRarity, researchAmount, useTime);
        this.attackDamage = attackDamage;
        this.criticalChance = criticalChance;
        this.bonus = bonus;
        this.miningSpeed = miningSpeed;
        this.knockback = knockback;
        this.sellPrice = sellPrice;
        this.statusEffects = statusEffect;
    }
}
