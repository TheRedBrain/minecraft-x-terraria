package com.github.theredbrain.minecraftxterraria.world;

import com.github.theredbrain.minecraftxterraria.MinecraftXTerraria;
import com.github.theredbrain.minecraftxterraria.entity.player.PlayerKeys;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class WorldKeys extends PersistentState {
    
    // ---world specific---
    // defeated bosses
    // pre-hardmode
    public boolean kingSlimeDefeated;
    public boolean eyeOfCthulhuDefeated;
    public boolean eaterOfWorldsDefeated;
    public boolean brainOfCthulhuDefeated;
    public boolean queenBeeDefeated;
    public boolean skeletronDefeated;
    public boolean deerclopsDefeated;
    public boolean wallOfFleshDefeated;
    // hardmode
    public boolean queenSlimeDefeated;
    public boolean theTwinsDefeated;
    public boolean theDestroyerDefeated;
    public boolean skeletronPrimeDefeated;
    public boolean planteraDefeated;
    public boolean golemDefeated;
    public boolean dukeFishronDefeated;
    public boolean empressOfLightDefeated;
    public boolean lunaticCultistDefeated;
    public boolean moonLordDefeated;

    // completed events
    // pre-hardmode
    public boolean goblinArmyCompleted;
    public boolean oldOnesArmyCompleted;
    // hardmode
    public boolean frostLegionCompleted;
    public boolean pirateInvasionCompleted;

    // one time consumables
    public boolean peddlersSatchelUsed;
    public boolean advancedCombatTechniquesUsed;
    public boolean advancedCombatTechniquesVol2Used;

    // world progress
    public int smashedOrbs;
    public int destroyedAltars;

    // npc requirements
    // pre-hardmode
    public boolean merchantNpcCanSpawn;
    public boolean nurseNpcCanSpawn;
    public boolean demolitionistNpcCanSpawn;
    public boolean dyeTraderNpcCanSpawn;
    public boolean anglerNpcCanSpawn;
    public boolean painterNpcCanSpawn;
    public boolean golferNpcCanSpawn;
    public boolean armsDealerNpcCanSpawn;
    public boolean tavernkeepNpcCanSpawn;
    public boolean stylistNpcCanSpawn;
    public boolean goblinTinkererNpcCanSpawn;
    public boolean mechanicNpcCanSpawn;
    public boolean partyGirlNpcCanSpawn;
    // post-hardmode
    public boolean wizardNpcCanSpawn;
    public boolean taxCollectorNpcCanSpawn;
    public boolean truffleNpcCanSpawn;
    public boolean princessNpcCanSpawn;
    // town pets
    public boolean townCatNpcCanSpawn;
    public boolean townDogNpcCanSpawn;
    public boolean townBunnyNpcCanSpawn;
    // town slimes
    public boolean coolSlimeNpcCanSpawn;
    public boolean elderSlimeNpcCanSpawn;
    public boolean clumsySlimeNpcCanSpawn;
    public boolean divaSlimeNpcCanSpawn;
    public boolean surlySlimeNpcCanSpawn;
    public boolean mysticSlimeNpcCanSpawn;
    public boolean squireSlimeNpcCanSpawn;

    // npc spawned
    // pre-hardmode
    public boolean guideNpcHasSpawned;
    public boolean merchantNpcHasSpawned;
    public boolean nurseNpcHasSpawned;
    public boolean demolitionistNpcHasSpawned;
    public boolean dyeTraderNpcHasSpawned;
    public boolean anglerNpcHasSpawned;
    public boolean zoologistNpcHasSpawned;
    public boolean dryadNpcHasSpawned;
    public boolean painterNpcHasSpawned;
    public boolean golferNpcHasSpawned;
    public boolean armsDealerNpcHasSpawned;
    public boolean tavernkeepNpcHasSpawned;
    public boolean stylistNpcHasSpawned;
    public boolean goblinTinkererNpcHasSpawned;
    public boolean witchDoctorNpcHasSpawned;
    public boolean clothierNpcHasSpawned;
    public boolean mechanicNpcHasSpawned;
    public boolean partyGirlNpcHasSpawned;
    // post-hardmode
    public boolean wizardNpcHasSpawned;
    public boolean taxCollectorNpcHasSpawned;
    public boolean truffleNpcHasSpawned;
    public boolean pirateNpcHasSpawned;
    public boolean steampunkerNpcHasSpawned;
    public boolean cyborgNpcHasSpawned;
    public boolean santaClausNpcHasSpawned;
    public boolean princessNpcHasSpawned;
    // town pets
    public boolean townCatNpcHasSpawned;
    public boolean townDogNpcHasSpawned;
    public boolean townBunnyNpcHasSpawned;
    // town slimes
    public boolean nerdySlimeNpcHasSpawned;
    public boolean coolSlimeNpcHasSpawned;
    public boolean elderSlimeNpcHasSpawned;
    public boolean clumsySlimeNpcHasSpawned;
    public boolean divaSlimeNpcHasSpawned;
    public boolean surlySlimeNpcHasSpawned;
    public boolean mysticSlimeNpcHasSpawned;
    public boolean squireSlimeNpcHasSpawned;

    public HashMap<UUID, PlayerKeys> playerKeysHashMap = new HashMap<>();

    /*
    Boss alive true/false (for every boss)
    NPC in house true/false (for every npc)
    event active (for every event)*/
    
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {

        NbtCompound playerKeysHashMapNbt = new NbtCompound();
        playerKeysHashMap.forEach((UUID, playerKeys) ->{
            NbtCompound playerKeysNbt = new NbtCompound();

            // saving player keys
            playerKeysNbt.putBoolean("aegisFruitConsumed", playerKeys.aegisFruitConsumed);
            playerKeysNbt.putBoolean("ambrosiaConsumed", playerKeys.ambrosiaConsumed);
            playerKeysNbt.putBoolean("arcaneCrystalConsumed", playerKeys.arcaneCrystalConsumed);
            playerKeysNbt.putBoolean("artisanLoafConsumed", playerKeys.artisanLoafConsumed);
            playerKeysNbt.putBoolean("demonHeartConsumed", playerKeys.demonHeartConsumed);
            playerKeysNbt.putBoolean("galaxyPearlConsumed", playerKeys.galaxyPearlConsumed);
            playerKeysNbt.putBoolean("gummyWormConsumed", playerKeys.gummyWormConsumed);
            playerKeysNbt.putBoolean("minecartUpgradeConsumed", playerKeys.minecartUpgradeConsumed);
            playerKeysNbt.putBoolean("torchGodFavorConsumed", playerKeys.torchGodFavorConsumed);
            playerKeysNbt.putBoolean("vitalCrystalConsumed", playerKeys.vitalCrystalConsumed);

            playerKeysNbt.putInt("lifeCrystalsConsumed", playerKeys.lifeCrystalsConsumed);
            playerKeysNbt.putInt("lifeFruitsConsumed", playerKeys.lifeFruitsConsumed);
            playerKeysNbt.putInt("manaCrystalsConsumed", playerKeys.manaCrystalsConsumed);
            playerKeysNbt.putInt("completedAnglerQuest", playerKeys.completedAnglerQuest);

            playerKeysNbt.putInt("playerDifficulty", playerKeys.playerDifficulty);
//            playerKeysNbt.putString("nickName", playerKeys.nickName);
            playerKeysNbt.putBoolean("characterCreated", playerKeys.characterCreated);

            playerKeysHashMapNbt.put(String.valueOf(UUID), playerKeysNbt);
        });
        nbt.put("playerKeys", playerKeysHashMapNbt);

        // saving server keys
        nbt.putBoolean("kingSlimeDefeated", kingSlimeDefeated);
        nbt.putBoolean("eyeOfCthulhuDefeated", eyeOfCthulhuDefeated);
        nbt.putBoolean("eaterOfWorldsDefeated", eaterOfWorldsDefeated);
        nbt.putBoolean("brainOfCthulhuDefeated", brainOfCthulhuDefeated);
        nbt.putBoolean("queenBeeDefeated", queenBeeDefeated);
        nbt.putBoolean("skeletronDefeated", skeletronDefeated);
        nbt.putBoolean("deerclopsDefeated", deerclopsDefeated);
        nbt.putBoolean("wallOfFleshDefeated", wallOfFleshDefeated);
        
        nbt.putBoolean("queenSlimeDefeated", queenSlimeDefeated);
        nbt.putBoolean("theTwinsDefeated", theTwinsDefeated);
        nbt.putBoolean("theDestroyerDefeated", theDestroyerDefeated);
        nbt.putBoolean("skeletronPrimeDefeated", skeletronPrimeDefeated);
        nbt.putBoolean("planteraDefeated", planteraDefeated);
        nbt.putBoolean("golemDefeated", golemDefeated);
        nbt.putBoolean("dukeFishronDefeated", dukeFishronDefeated);
        nbt.putBoolean("empressOfLightDefeated", empressOfLightDefeated);
        nbt.putBoolean("lunaticCultistDefeated", lunaticCultistDefeated);
        nbt.putBoolean("moonLordDefeated", moonLordDefeated);
        
        nbt.putBoolean("goblinArmyCompleted", goblinArmyCompleted);
        nbt.putBoolean("oldOnesArmyCompleted", oldOnesArmyCompleted);
        
        nbt.putBoolean("frostLegionCompleted", frostLegionCompleted);
        nbt.putBoolean("pirateInvasionCompleted", pirateInvasionCompleted);
        
        nbt.putBoolean("peddlersSatchelUsed", peddlersSatchelUsed);
        nbt.putBoolean("advancedCombatTechniquesUsed", advancedCombatTechniquesUsed);
        nbt.putBoolean("advancedCombatTechniquesVol2Used", advancedCombatTechniquesVol2Used);
        
        nbt.putInt("smashedOrbs", smashedOrbs);
        nbt.putInt("destroyedAltars", destroyedAltars);

        nbt.putBoolean("merchantNpcCanSpawn", merchantNpcCanSpawn);
        nbt.putBoolean("nurseNpcCanSpawn", nurseNpcCanSpawn);
        nbt.putBoolean("demolitionistNpcCanSpawn", demolitionistNpcCanSpawn);
        nbt.putBoolean("dyeTraderNpcCanSpawn", dyeTraderNpcCanSpawn);
        nbt.putBoolean("anglerNpcCanSpawn", anglerNpcCanSpawn);
        nbt.putBoolean("painterNpcCanSpawn", painterNpcCanSpawn);
        nbt.putBoolean("golferNpcCanSpawn", golferNpcCanSpawn);
        nbt.putBoolean("armsDealerNpcCanSpawn", armsDealerNpcCanSpawn);
        nbt.putBoolean("tavernkeepNpcCanSpawn", tavernkeepNpcCanSpawn);
        nbt.putBoolean("stylistNpcCanSpawn", stylistNpcCanSpawn);
        nbt.putBoolean("goblinTinkererNpcCanSpawn", goblinTinkererNpcCanSpawn);
        nbt.putBoolean("mechanicNpcCanSpawn", mechanicNpcCanSpawn);
        nbt.putBoolean("partyGirlNpcCanSpawn", partyGirlNpcCanSpawn);
        
        nbt.putBoolean("wizardNpcCanSpawn", wizardNpcCanSpawn);
        nbt.putBoolean("taxCollectorNpcCanSpawn", taxCollectorNpcCanSpawn);
        nbt.putBoolean("truffleNpcCanSpawn", truffleNpcCanSpawn);
        nbt.putBoolean("princessNpcCanSpawn", princessNpcCanSpawn);
        
        nbt.putBoolean("townCatNpcCanSpawn", townCatNpcCanSpawn);
        nbt.putBoolean("townDogNpcCanSpawn", townDogNpcCanSpawn);
        nbt.putBoolean("townBunnyNpcCanSpawn", townBunnyNpcCanSpawn);
        
        nbt.putBoolean("coolSlimeNpcCanSpawn", coolSlimeNpcCanSpawn);
        nbt.putBoolean("elderSlimeNpcCanSpawn", elderSlimeNpcCanSpawn);
        nbt.putBoolean("clumsySlimeNpcCanSpawn", clumsySlimeNpcCanSpawn);
        nbt.putBoolean("divaSlimeNpcCanSpawn", divaSlimeNpcCanSpawn);
        nbt.putBoolean("surlySlimeNpcCanSpawn", surlySlimeNpcCanSpawn);
        nbt.putBoolean("mysticSlimeNpcCanSpawn", mysticSlimeNpcCanSpawn);
        nbt.putBoolean("squireSlimeNpcCanSpawn", squireSlimeNpcCanSpawn);
        
        nbt.putBoolean("guideNpcHasSpawned", guideNpcHasSpawned);
        nbt.putBoolean("merchantNpcHasSpawned", merchantNpcHasSpawned);
        nbt.putBoolean("nurseNpcHasSpawned", nurseNpcHasSpawned);
        nbt.putBoolean("demolitionistNpcHasSpawned", demolitionistNpcHasSpawned);
        nbt.putBoolean("dyeTraderNpcHasSpawned", dyeTraderNpcHasSpawned);
        nbt.putBoolean("anglerNpcHasSpawned", anglerNpcHasSpawned);
        nbt.putBoolean("zoologistNpcHasSpawned", zoologistNpcHasSpawned);
        nbt.putBoolean("dryadNpcHasSpawned", dryadNpcHasSpawned);
        nbt.putBoolean("painterNpcHasSpawned", painterNpcHasSpawned);
        nbt.putBoolean("golferNpcHasSpawned", golferNpcHasSpawned);
        nbt.putBoolean("armsDealerNpcHasSpawned", armsDealerNpcHasSpawned);
        nbt.putBoolean("tavernkeepNpcHasSpawned", tavernkeepNpcHasSpawned);
        nbt.putBoolean("stylistNpcHasSpawned", stylistNpcHasSpawned);
        nbt.putBoolean("goblinTinkererNpcHasSpawned", goblinTinkererNpcHasSpawned);
        nbt.putBoolean("witchDoctorNpcHasSpawned", witchDoctorNpcHasSpawned);
        nbt.putBoolean("clothierNpcHasSpawned", clothierNpcHasSpawned);
        nbt.putBoolean("mechanicNpcHasSpawned", mechanicNpcHasSpawned);
        nbt.putBoolean("partyGirlNpcHasSpawned", partyGirlNpcHasSpawned);
        
        nbt.putBoolean("wizardNpcHasSpawned", wizardNpcHasSpawned);
        nbt.putBoolean("taxCollectorNpcHasSpawned", taxCollectorNpcHasSpawned);
        nbt.putBoolean("truffleNpcHasSpawned", truffleNpcHasSpawned);
        nbt.putBoolean("pirateNpcHasSpawned", pirateNpcHasSpawned);
        nbt.putBoolean("steampunkerNpcHasSpawned", steampunkerNpcHasSpawned);
        nbt.putBoolean("cyborgNpcHasSpawned", cyborgNpcHasSpawned);
        nbt.putBoolean("santaClausNpcHasSpawned", santaClausNpcHasSpawned);
        nbt.putBoolean("princessNpcHasSpawned", princessNpcHasSpawned);
        
        nbt.putBoolean("townCatNpcHasSpawned", townCatNpcHasSpawned);
        nbt.putBoolean("townDogNpcHasSpawned", townDogNpcHasSpawned);
        nbt.putBoolean("townBunnyNpcHasSpawned", townBunnyNpcHasSpawned);
        
        nbt.putBoolean("nerdySlimeNpcHasSpawned", nerdySlimeNpcHasSpawned);
        nbt.putBoolean("coolSlimeNpcHasSpawned", coolSlimeNpcHasSpawned);
        nbt.putBoolean("elderSlimeNpcHasSpawned", elderSlimeNpcHasSpawned);
        nbt.putBoolean("clumsySlimeNpcHasSpawned", clumsySlimeNpcHasSpawned);
        nbt.putBoolean("divaSlimeNpcHasSpawned", divaSlimeNpcHasSpawned);
        nbt.putBoolean("surlySlimeNpcHasSpawned", surlySlimeNpcHasSpawned);
        nbt.putBoolean("mysticSlimeNpcHasSpawned", mysticSlimeNpcHasSpawned);
        nbt.putBoolean("squireSlimeNpcHasSpawned", squireSlimeNpcHasSpawned);

        return nbt;
    }

    public static WorldKeys createFromNbt(NbtCompound tag) {
        WorldKeys worldKeys = new WorldKeys();

        NbtCompound playerKeysTag = tag.getCompound("playerKeys");
        playerKeysTag.getKeys().forEach(key -> {
            PlayerKeys playerKeys = new PlayerKeys();

            // reading player keys
            playerKeys.aegisFruitConsumed = playerKeysTag.getCompound(key).getBoolean("aegisFruitConsumed");
            playerKeys.ambrosiaConsumed = playerKeysTag.getCompound(key).getBoolean("ambrosiaConsumed");
            playerKeys.arcaneCrystalConsumed = playerKeysTag.getCompound(key).getBoolean("arcaneCrystalConsumed");
            playerKeys.artisanLoafConsumed = playerKeysTag.getCompound(key).getBoolean("artisanLoafConsumed");
            playerKeys.demonHeartConsumed = playerKeysTag.getCompound(key).getBoolean("demonHeartConsumed");
            playerKeys.galaxyPearlConsumed = playerKeysTag.getCompound(key).getBoolean("galaxyPearlConsumed");
            playerKeys.gummyWormConsumed = playerKeysTag.getCompound(key).getBoolean("gummyWormConsumed");
            playerKeys.minecartUpgradeConsumed = playerKeysTag.getCompound(key).getBoolean("minecartUpgradeConsumed");
            playerKeys.torchGodFavorConsumed = playerKeysTag.getCompound(key).getBoolean("torchGodFavorConsumed");
            playerKeys.vitalCrystalConsumed = playerKeysTag.getCompound(key).getBoolean("vitalCrystalConsumed");

            playerKeys.lifeCrystalsConsumed = playerKeysTag.getCompound(key).getInt("lifeCrystalsConsumed");
            playerKeys.lifeFruitsConsumed = playerKeysTag.getCompound(key).getInt("lifeFruitsConsumed");
            playerKeys.manaCrystalsConsumed = playerKeysTag.getCompound(key).getInt("manaCrystalsConsumed");
            playerKeys.completedAnglerQuest = playerKeysTag.getCompound(key).getInt("completedAnglerQuest");

            playerKeys.playerDifficulty = playerKeysTag.getCompound(key).getInt("playerDifficulty");
//            playerKeys.nickName = playerKeysTag.getCompound(key).getString("nickName");
            playerKeys.characterCreated = playerKeysTag.getCompound(key).getBoolean("characterCreated");

            UUID uuid = UUID.fromString(key);
            worldKeys.playerKeysHashMap.put(uuid, playerKeys);
        });

        // reading server keys
        worldKeys.kingSlimeDefeated = tag.getBoolean("kingSlimeDefeated");
        worldKeys.eyeOfCthulhuDefeated = tag.getBoolean("eyeOfCthulhuDefeated");
        worldKeys.eaterOfWorldsDefeated = tag.getBoolean("eaterOfWorldsDefeated");
        worldKeys.brainOfCthulhuDefeated = tag.getBoolean("brainOfCthulhuDefeated");
        worldKeys.queenBeeDefeated = tag.getBoolean("queenBeeDefeated");
        worldKeys.skeletronDefeated = tag.getBoolean("skeletronDefeated");
        worldKeys.deerclopsDefeated = tag.getBoolean("deerclopsDefeated");
        worldKeys.wallOfFleshDefeated = tag.getBoolean("wallOfFleshDefeated");
        
        worldKeys.queenSlimeDefeated = tag.getBoolean("queenSlimeDefeated");
        worldKeys.theTwinsDefeated = tag.getBoolean("theTwinsDefeated");
        worldKeys.theDestroyerDefeated = tag.getBoolean("theDestroyerDefeated");
        worldKeys.skeletronPrimeDefeated = tag.getBoolean("skeletronPrimeDefeated");
        worldKeys.planteraDefeated = tag.getBoolean("planteraDefeated");
        worldKeys.golemDefeated = tag.getBoolean("golemDefeated");
        worldKeys.dukeFishronDefeated = tag.getBoolean("dukeFishronDefeated");
        worldKeys.empressOfLightDefeated = tag.getBoolean("empressOfLightDefeated");
        worldKeys.lunaticCultistDefeated = tag.getBoolean("lunaticCultistDefeated");
        worldKeys.moonLordDefeated = tag.getBoolean("moonLordDefeated");
        
        worldKeys.goblinArmyCompleted = tag.getBoolean("goblinArmyCompleted");
        worldKeys.oldOnesArmyCompleted = tag.getBoolean("oldOnesArmyCompleted");
        
        worldKeys.frostLegionCompleted = tag.getBoolean("frostLegionCompleted");
        worldKeys.pirateInvasionCompleted = tag.getBoolean("pirateInvasionCompleted");
        
        worldKeys.peddlersSatchelUsed = tag.getBoolean("peddlersSatchelUsed");
        worldKeys.advancedCombatTechniquesUsed = tag.getBoolean("advancedCombatTechniquesUsed");
        worldKeys.advancedCombatTechniquesVol2Used = tag.getBoolean("advancedCombatTechniquesVol2Used");
        
        worldKeys.smashedOrbs = tag.getInt("smashedOrbs");
        worldKeys.destroyedAltars = tag.getInt("destroyedAltars");
        
        worldKeys.merchantNpcCanSpawn = tag.getBoolean("merchantNpcCanSpawn");
        worldKeys.nurseNpcCanSpawn = tag.getBoolean("nurseNpcCanSpawn");
        worldKeys.demolitionistNpcCanSpawn = tag.getBoolean("demolitionistNpcCanSpawn");
        worldKeys.dyeTraderNpcCanSpawn = tag.getBoolean("dyeTraderNpcCanSpawn");
        worldKeys.anglerNpcCanSpawn = tag.getBoolean("anglerNpcCanSpawn");
        worldKeys.painterNpcCanSpawn = tag.getBoolean("painterNpcCanSpawn");
        worldKeys.golferNpcCanSpawn = tag.getBoolean("golferNpcCanSpawn");
        worldKeys.armsDealerNpcCanSpawn = tag.getBoolean("armsDealerNpcCanSpawn");
        worldKeys.tavernkeepNpcCanSpawn = tag.getBoolean("tavernkeepNpcCanSpawn");
        worldKeys.stylistNpcCanSpawn = tag.getBoolean("stylistNpcCanSpawn");
        worldKeys.goblinTinkererNpcCanSpawn = tag.getBoolean("goblinTinkererNpcCanSpawn");
        worldKeys.mechanicNpcCanSpawn = tag.getBoolean("mechanicNpcCanSpawn");
        worldKeys.partyGirlNpcCanSpawn = tag.getBoolean("partyGirlNpcCanSpawn");
        
        worldKeys.wizardNpcCanSpawn = tag.getBoolean("wizardNpcCanSpawn");
        worldKeys.taxCollectorNpcCanSpawn = tag.getBoolean("taxCollectorNpcCanSpawn");
        worldKeys.truffleNpcCanSpawn = tag.getBoolean("truffleNpcCanSpawn");
        worldKeys.princessNpcCanSpawn = tag.getBoolean("princessNpcCanSpawn");
        
        worldKeys.townCatNpcCanSpawn = tag.getBoolean("townCatNpcCanSpawn");
        worldKeys.townDogNpcCanSpawn = tag.getBoolean("townDogNpcCanSpawn");
        worldKeys.townBunnyNpcCanSpawn = tag.getBoolean("townBunnyNpcCanSpawn");
        
        worldKeys.coolSlimeNpcCanSpawn = tag.getBoolean("coolSlimeNpcCanSpawn");
        worldKeys.elderSlimeNpcCanSpawn = tag.getBoolean("elderSlimeNpcCanSpawn");
        worldKeys.clumsySlimeNpcCanSpawn = tag.getBoolean("clumsySlimeNpcCanSpawn");
        worldKeys.divaSlimeNpcCanSpawn = tag.getBoolean("divaSlimeNpcCanSpawn");
        worldKeys.surlySlimeNpcCanSpawn = tag.getBoolean("surlySlimeNpcCanSpawn");
        worldKeys.mysticSlimeNpcCanSpawn = tag.getBoolean("mysticSlimeNpcCanSpawn");
        worldKeys.squireSlimeNpcCanSpawn = tag.getBoolean("squireSlimeNpcCanSpawn");

        worldKeys.guideNpcHasSpawned = tag.getBoolean("guideNpcHasSpawned");
        worldKeys.merchantNpcHasSpawned = tag.getBoolean("merchantNpcHasSpawned");
        worldKeys.nurseNpcHasSpawned = tag.getBoolean("nurseNpcHasSpawned");
        worldKeys.demolitionistNpcHasSpawned = tag.getBoolean("demolitionistNpcHasSpawned");
        worldKeys.dyeTraderNpcHasSpawned = tag.getBoolean("dyeTraderNpcHasSpawned");
        worldKeys.anglerNpcHasSpawned = tag.getBoolean("anglerNpcHasSpawned");
        worldKeys.zoologistNpcHasSpawned = tag.getBoolean("zoologistNpcHasSpawned");
        worldKeys.dryadNpcHasSpawned = tag.getBoolean("dryadNpcHasSpawned");
        worldKeys.painterNpcHasSpawned = tag.getBoolean("painterNpcHasSpawned");
        worldKeys.golferNpcHasSpawned = tag.getBoolean("golferNpcHasSpawned");
        worldKeys.armsDealerNpcHasSpawned = tag.getBoolean("armsDealerNpcHasSpawned");
        worldKeys.tavernkeepNpcHasSpawned = tag.getBoolean("tavernkeepNpcHasSpawned");
        worldKeys.stylistNpcHasSpawned = tag.getBoolean("stylistNpcHasSpawned");
        worldKeys.goblinTinkererNpcHasSpawned = tag.getBoolean("goblinTinkererNpcHasSpawned");
        worldKeys.witchDoctorNpcHasSpawned = tag.getBoolean("witchDoctorNpcHasSpawned");
        worldKeys.clothierNpcHasSpawned = tag.getBoolean("clothierNpcHasSpawned");
        worldKeys.mechanicNpcHasSpawned = tag.getBoolean("mechanicNpcHasSpawned");
        worldKeys.partyGirlNpcHasSpawned = tag.getBoolean("partyGirlNpcHasSpawned");

        worldKeys.wizardNpcHasSpawned = tag.getBoolean("wizardNpcHasSpawned");
        worldKeys.taxCollectorNpcHasSpawned = tag.getBoolean("taxCollectorNpcHasSpawned");
        worldKeys.truffleNpcHasSpawned = tag.getBoolean("truffleNpcHasSpawned");
        worldKeys.pirateNpcHasSpawned = tag.getBoolean("pirateNpcHasSpawned");
        worldKeys.steampunkerNpcHasSpawned = tag.getBoolean("steampunkerNpcHasSpawned");
        worldKeys.cyborgNpcHasSpawned = tag.getBoolean("cyborgNpcHasSpawned");
        worldKeys.santaClausNpcHasSpawned = tag.getBoolean("santaClausNpcHasSpawned");
        worldKeys.princessNpcHasSpawned = tag.getBoolean("princessNpcHasSpawned");

        worldKeys.townCatNpcHasSpawned = tag.getBoolean("townCatNpcHasSpawned");
        worldKeys.townDogNpcHasSpawned = tag.getBoolean("townDogNpcHasSpawned");
        worldKeys.townBunnyNpcHasSpawned = tag.getBoolean("townBunnyNpcHasSpawned");

        worldKeys.nerdySlimeNpcHasSpawned = tag.getBoolean("nerdySlimeNpcHasSpawned");
        worldKeys.coolSlimeNpcHasSpawned = tag.getBoolean("coolSlimeNpcHasSpawned");
        worldKeys.elderSlimeNpcHasSpawned = tag.getBoolean("elderSlimeNpcHasSpawned");
        worldKeys.clumsySlimeNpcHasSpawned = tag.getBoolean("clumsySlimeNpcHasSpawned");
        worldKeys.divaSlimeNpcHasSpawned = tag.getBoolean("divaSlimeNpcHasSpawned");
        worldKeys.surlySlimeNpcHasSpawned = tag.getBoolean("surlySlimeNpcHasSpawned");
        worldKeys.mysticSlimeNpcHasSpawned = tag.getBoolean("mysticSlimeNpcHasSpawned");
        worldKeys.squireSlimeNpcHasSpawned = tag.getBoolean("squireSlimeNpcHasSpawned");

        return worldKeys;
    }

    public static WorldKeys getWorldKeys(MinecraftServer server) {

        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        WorldKeys worldKeys = persistentStateManager.getOrCreate(
                WorldKeys::createFromNbt,
                WorldKeys::new,
                MinecraftXTerraria.MOD_ID);

        worldKeys.markDirty();

        return worldKeys;
    }

    public static PlayerKeys getPlayerKeys(LivingEntity player) {
        WorldKeys worldKeys = getWorldKeys(player.world.getServer());

        return worldKeys.playerKeysHashMap.computeIfAbsent(player.getUuid(), uuid -> new PlayerKeys());
    }
}
