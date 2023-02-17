package com.github.theredbrain.minecraftxterraria.client.network;

public interface DuckClientPlayerEntityMixin {

    void mxtUpdateHealth(float health, float healthRegenerationTime, float healthEffectiveRegenerationTime, float healthRegenerationCounter);
    void mxtUpdateMana(float mana, float manaRegenerationDelay, float manaRegenerationCounter);
}
