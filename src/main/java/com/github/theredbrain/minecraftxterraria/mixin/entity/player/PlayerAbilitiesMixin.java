package com.github.theredbrain.minecraftxterraria.mixin.entity.player;

import com.github.theredbrain.minecraftxterraria.entity.player.DuckPlayerAbilitiesMixin;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerAbilities.class)
public class PlayerAbilitiesMixin implements DuckPlayerAbilitiesMixin {

    @Shadow
    public boolean invulnerable;

    @Shadow
    public boolean flying;

    @Shadow
    public boolean allowFlying;

    @Shadow
    public boolean creativeMode;

    @Shadow
    public boolean allowModifyWorld = true;

    @Shadow
    private float flySpeed = 0.05f;

    @Shadow
    private float walkSpeed = 0.1f;

    // TODO journeyMode


    private boolean aegisFruit;
    private boolean ambrosia;
    private boolean arcaneCrystal;
    private boolean artisanLoaf;
    private boolean demonHeart;
    private boolean galaxyPearl;
    private boolean gummyWorm;
    private boolean minecartUpgrade;
    private boolean torchGodFavor;
    private boolean vitalCrystal;
    private int difficulty;
    private int lifeCrystal;
    private int lifeFruit;
    private int manaCrystal;

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void writeNbt(NbtCompound nbt) {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putBoolean("invulnerable", this.invulnerable);
        nbtCompound.putBoolean("flying", this.flying);
        nbtCompound.putBoolean("mayfly", this.allowFlying);
        nbtCompound.putBoolean("instabuild", this.creativeMode);
        nbtCompound.putBoolean("mayBuild", this.allowModifyWorld);
        nbtCompound.putFloat("flySpeed", this.flySpeed);
        nbtCompound.putFloat("walkSpeed", this.walkSpeed);
        nbt.put("abilities", nbtCompound);
    }

    /**
     * @author TheRedBrain
     */
    @Overwrite
    public void readNbt(NbtCompound nbt) {
        if (nbt.contains("abilities", NbtElement.COMPOUND_TYPE)) {
            NbtCompound nbtCompound = nbt.getCompound("abilities");
            this.invulnerable = nbtCompound.getBoolean("invulnerable");
            this.flying = nbtCompound.getBoolean("flying");
            this.allowFlying = nbtCompound.getBoolean("mayfly");
            this.creativeMode = nbtCompound.getBoolean("instabuild");
            if (nbtCompound.contains("flySpeed", NbtElement.NUMBER_TYPE)) {
                this.flySpeed = nbtCompound.getFloat("flySpeed");
                this.walkSpeed = nbtCompound.getFloat("walkSpeed");
            }
            if (nbtCompound.contains("mayBuild", NbtElement.BYTE_TYPE)) {
                this.allowModifyWorld = nbtCompound.getBoolean("mayBuild");
            }
        }
    }

    public boolean isAegisFruit() {
        return aegisFruit;
    }

    public void setAegisFruit(boolean aegisFruit) {
        this.aegisFruit = aegisFruit;
    }

    public boolean isAmbrosia() {
        return ambrosia;
    }

    public void setAmbrosia(boolean ambrosia) {
        this.ambrosia = ambrosia;
    }

    public boolean isArcaneCrystal() {
        return arcaneCrystal;
    }

    public void setArcaneCrystal(boolean arcaneCrystal) {
        this.arcaneCrystal = arcaneCrystal;
    }

    public boolean isArtisanLoaf() {
        return artisanLoaf;
    }

    public void setArtisanLoaf(boolean artisanLoaf) {
        this.artisanLoaf = artisanLoaf;
    }

    public boolean isDemonHeart() {
        return demonHeart;
    }

    public void setDemonHeart(boolean demonHeart) {
        this.demonHeart = demonHeart;
    }

    public boolean isGalaxyPearl() {
        return galaxyPearl;
    }

    public void setGalaxyPearl(boolean galaxyPearl) {
        this.galaxyPearl = galaxyPearl;
    }

    public boolean isGummyWorm() {
        return gummyWorm;
    }

    public void setGummyWorm(boolean gummyWorm) {
        this.gummyWorm = gummyWorm;
    }

    public boolean isMinecartUpgrade() {
        return minecartUpgrade;
    }

    public void setMinecartUpgrade(boolean minecartUpgrade) {
        this.minecartUpgrade = minecartUpgrade;
    }

    public boolean isTorchGodFavor() {
        return torchGodFavor;
    }

    public void setTorchGodFavor(boolean torchGodFavor) {
        this.torchGodFavor = torchGodFavor;
    }

    public boolean isVitalCrystal() {
        return vitalCrystal;
    }

    public void setVitalCrystal(boolean vitalCrystal) {
        this.vitalCrystal = vitalCrystal;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getLifeCrystal() {
        return lifeCrystal;
    }

    public void setLifeCrystal(int lifeCrystal) {
        this.lifeCrystal = lifeCrystal;
    }

    public int getLifeFruit() {
        return lifeFruit;
    }

    public void setLifeFruit(int lifeFruit) {
        this.lifeFruit = lifeFruit;
    }

    public int getManaCrystal() {
        return manaCrystal;
    }

    public void setManaCrystal(int manaCrystal) {
        this.manaCrystal = manaCrystal;
    }
}
