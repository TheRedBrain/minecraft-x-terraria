package com.github.theredbrain.minecraftxterraria.item.weapons;

import com.github.theredbrain.minecraftxterraria.item.MxtMeleeWeaponItem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;

public class MxTSwordItem extends MxtMeleeWeaponItem {
    public MxTSwordItem(Settings settings, int baseRarity, int researchAmount, int sellPrice, boolean autoSwing, int useTime, int attackDamage, int bonus, int criticalChance, double knockback, @Nullable Pair<StatusEffectInstance, Float> statusEffect) {
        super(settings, baseRarity, researchAmount, sellPrice, autoSwing, useTime, attackDamage, bonus, criticalChance, knockback, statusEffect);
    }
}
