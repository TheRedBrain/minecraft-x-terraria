package com.github.theredbrain.minecraftxterraria.entity.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MxtStatusEffect extends StatusEffect {
    private boolean isCancelable;
    private boolean isRemovedOnDeath;

    public MxtStatusEffect(boolean isCancelable, boolean isRemovedOnDeath) {
        super(StatusEffectCategory.NEUTRAL, 0);
        this.isCancelable = isCancelable;
        this.isRemovedOnDeath = isRemovedOnDeath;
    }

    public boolean isCancelable() {
        return this.isCancelable;
    }

    public boolean isRemovedOnDeath() {
        return this.isRemovedOnDeath;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (this == StatusEffects.REGENERATION) {
            if (entity.getHealth() < entity.getMaxHealth()) {
                entity.heal(1.0f);
            }
        } else if (this == StatusEffects.POISON) {
            if (entity.getHealth() > 1.0f) {
                entity.damage(DamageSource.MAGIC, 1.0f);
            }
        } else if (this == StatusEffects.WITHER) {
            entity.damage(DamageSource.WITHER, 1.0f);
        } else if (this == StatusEffects.HUNGER && entity instanceof PlayerEntity) {
            ((PlayerEntity)entity).addExhaustion(0.005f * (float)(amplifier + 1));
        } else if (this == StatusEffects.SATURATION && entity instanceof PlayerEntity) {
            if (!entity.world.isClient) {
                ((PlayerEntity)entity).getHungerManager().add(amplifier + 1, 1.0f);
            }
        } else if (this == StatusEffects.INSTANT_HEALTH && !entity.isUndead() || this == StatusEffects.INSTANT_DAMAGE && entity.isUndead()) {
            entity.heal(Math.max(4 << amplifier, 0));
        } else if (this == StatusEffects.INSTANT_DAMAGE && !entity.isUndead() || this == StatusEffects.INSTANT_HEALTH && entity.isUndead()) {
            entity.damage(DamageSource.MAGIC, 6 << amplifier);
        }
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        if (this == StatusEffects.INSTANT_HEALTH && !target.isUndead() || this == StatusEffects.INSTANT_DAMAGE && target.isUndead()) {
            int i = (int)(proximity * (double)(4 << amplifier) + 0.5);
            target.heal(i);
        } else if (this == StatusEffects.INSTANT_DAMAGE && !target.isUndead() || this == StatusEffects.INSTANT_HEALTH && target.isUndead()) {
            int i = (int)(proximity * (double)(6 << amplifier) + 0.5);
            if (source == null) {
                target.damage(DamageSource.MAGIC, i);
            } else {
                target.damage(DamageSource.magic(source, attacker), i);
            }
        } else {
            this.applyUpdateEffect(target, amplifier);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        if (this == StatusEffects.REGENERATION) {
            int i = 50 >> amplifier;
            if (i > 0) {
                return duration % i == 0;
            }
            return true;
        }
        if (this == StatusEffects.POISON) {
            int i = 25 >> amplifier;
            if (i > 0) {
                return duration % i == 0;
            }
            return true;
        }
        if (this == StatusEffects.WITHER) {
            int i = 40 >> amplifier;
            if (i > 0) {
                return duration % i == 0;
            }
            return true;
        }
        return this == StatusEffects.HUNGER;
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : this.getAttributeModifiers().entrySet()) {
            EntityAttributeInstance entityAttributeInstance = attributes.getCustomInstance(entry.getKey());
            if (entityAttributeInstance == null) continue;
            entityAttributeInstance.removeModifier(entry.getValue());
        }
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : this.getAttributeModifiers().entrySet()) {
            EntityAttributeInstance entityAttributeInstance = attributes.getCustomInstance(entry.getKey());
            if (entityAttributeInstance == null) continue;
            EntityAttributeModifier entityAttributeModifier = entry.getValue();
            entityAttributeInstance.removeModifier(entityAttributeModifier);
            entityAttributeInstance.addPersistentModifier(new EntityAttributeModifier(entityAttributeModifier.getId(), this.getTranslationKey() + " " + amplifier, this.adjustModifierAmount(amplifier, entityAttributeModifier), entityAttributeModifier.getOperation()));
        }
    }
}
