package com.github.theredbrain.minecraftxterraria.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;

public class MxtMeleeWeaponItem extends MxtUsableItem {
    int attackDamage;
    int criticalChance;
    int bonus;
    double knockback;
    private Pair<StatusEffectInstance, Float> statusEffects;

    public MxtMeleeWeaponItem(Settings settings, int baseRarity, int researchAmount, int sellPrice, boolean autoSwing, int useTime, int attackDamage, int bonus, int criticalChance, double knockback, @Nullable Pair<StatusEffectInstance, Float> statusEffect) {
        super(settings, baseRarity, researchAmount, sellPrice, autoSwing, useTime);
        this.attackDamage = attackDamage;
        this.bonus = bonus;
        this.criticalChance = criticalChance;
        this.knockback = knockback;
        this.statusEffects = statusEffect;
    }
}
