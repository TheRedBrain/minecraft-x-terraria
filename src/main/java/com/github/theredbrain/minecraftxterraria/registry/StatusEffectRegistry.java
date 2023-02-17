package com.github.theredbrain.minecraftxterraria.registry;

import com.github.theredbrain.minecraftxterraria.entity.effect.MxtStatusEffect;
import com.github.theredbrain.minecraftxterraria.util.AttributeModifierUUIDs;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class StatusEffectRegistry {

    private static final int RAW_ID_OFFSET_CONSUMABLES = 34; // vanilla effects
    private static final int RAW_ID_OFFSET_EQUIPMENT = RAW_ID_OFFSET_CONSUMABLES + 48;
    private static final int RAW_ID_OFFSET_ACTIVATED_FURNITURE = RAW_ID_OFFSET_EQUIPMENT + 20;
    private static final int RAW_ID_OFFSET_ENVIRONMENTAL = RAW_ID_OFFSET_ACTIVATED_FURNITURE + 6;
    private static final int RAW_ID_OFFSET_SUMMONS = RAW_ID_OFFSET_ENVIRONMENTAL + 8;
    private static final int RAW_ID_OFFSET_MOUNTS = RAW_ID_OFFSET_SUMMONS + 21;
    private static final int RAW_ID_OFFSET_PETS = RAW_ID_OFFSET_MOUNTS + 30;
    private static final int RAW_ID_OFFSET_LIGHT_PETS = RAW_ID_OFFSET_PETS + 69;
    private static final int RAW_ID_OFFSET_ENEMIES = RAW_ID_OFFSET_LIGHT_PETS + 10;
    private static final int RAW_ID_OFFSET_ITEMS = RAW_ID_OFFSET_ENEMIES + 29;
    private static final int RAW_ID_OFFSET_OTHER = RAW_ID_OFFSET_ITEMS + 16;

    // ----buffs----
    // consumables
//    public static final StatusEffect AMMO_RESERVATION = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect ARCHERY = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect BATTLE = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect BUILDER = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect CALM = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect CRATE = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect DANGERSENSE = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect ENDURANCE = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect EXQUISITELY_STUFFED = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect FEATHERFALL = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect FISHING = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect FLIPPER = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect GILLS = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect GRAVITATION = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect HEARTREACH = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect HUNTER = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect INFERNO = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect INVISIBILITY = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect IRONSKIN = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect LIFEFORCE = new MxtStatusEffect(true, true);
//    public static final StatusEffect LUCKY = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect MAGIC_POWER = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect MANA_REGENERATION = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect MINING = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect NIGHT_OWL = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect OBSIDIAN_SKIN = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect PLENTY_SATISFIED = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect RAGE = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect HEALTH_REGENERATION = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect SHINE = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect SONAR = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect SPELUNKER = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect SUMMONING = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect SWIFTNESS = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect THORNS = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect TITAN = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WARMTH = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WATER_WALKING = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WRATH = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WELL_FED = new MxtStatusEffect(true, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WEAPON_IMBUE_CONFETTI = new MxtStatusEffect(true, false).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WEAPON_IMBUE_CURSED_FLAMES = new MxtStatusEffect(true, false).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WEAPON_IMBUE_FIRE = new MxtStatusEffect(true, false).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WEAPON_IMBUE_GOLD = new MxtStatusEffect(true, false).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WEAPON_IMBUE_ICHOR = new MxtStatusEffect(true, false).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WEAPON_IMBUE_NANITES = new MxtStatusEffect(true, false).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WEAPON_IMBUE_POISON = new MxtStatusEffect(true, false).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WEAPON_IMBUE_VENOM = new MxtStatusEffect(true, false).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_REGENERATION, 12.0, EntityAttributeModifier.Operation.ADDITION);

    // equipment
//    public static final StatusEffect BEETLE_ENDURANCE = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect BEETLE_MIGHT = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect DAMAGE_NEBULA = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect DURENDALS_BLESSING = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect HARVEST_TIME = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect HOLY_PROTECTION = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect ICE_BARRIER = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect JUNGLES_FURY = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect LEAF_CRYSTAL = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect LIFE_DRAIN = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect LIFE_NEBULA = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect MANA_NEBULA = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect MERFOLK = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect PANIC = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect RAPID_HEALING = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect TITANIUM_BARRIER = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect SOLAR_BLAZE = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect STARDUST_GUARDIAN = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect STRIKING_MOMENT = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_POSITIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_POSITIVE_HEALTH_REGENERATION_RATE_WEREWOLF, 12.0, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect WEREWOLF = new MxtStatusEffect(false, true);

    // activated furniture
//    public static final StatusEffect AMMO_BOX = new MxtStatusEffect(true, true);
//    public static final StatusEffect BEWITCHED = new MxtStatusEffect(true, true);
//    public static final StatusEffect CLAIRVOYANCE = new MxtStatusEffect(true, true);
//    public static final StatusEffect SHARPENED = new MxtStatusEffect(true, true);
//    public static final StatusEffect STRATEGIST = new MxtStatusEffect(true, true);
//    public static final StatusEffect SUGAR_RUSH = new MxtStatusEffect(true, true);

    // environmental
//    public static final StatusEffect COZY_FIRE = new MxtStatusEffect(false, true);
//    public static final StatusEffect DRYADS_BLESSING = new MxtStatusEffect(false, true);
//    public static final StatusEffect HAPPY = new MxtStatusEffect(false, true);
//    public static final StatusEffect HEART_LAMP = new MxtStatusEffect(false, true);
    public static final StatusEffect HONEY = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_BONUS_HEALTH_REGENERATION_TIME, AttributeModifierUUIDs.MXT_BONUS_HEALTH_REGENERATION_TIME_HONEY, 6.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect PEACE_CANDLE = new MxtStatusEffect(false, true);
//    public static final StatusEffect STAR_IN_A_BOTTLE = new MxtStatusEffect(false, true);
//    public static final StatusEffect THE_BAST_DEFENSE = new MxtStatusEffect(false, true);

    // summons TODO

    // mounts TODO

    // pets TODO

    // light pets TODO

    // ----debuffs----
    // enemies
    public static final StatusEffect BLEEDING = new MxtStatusEffect(false, true);
    public static final StatusEffect POISONED = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_POISONED, 12.0, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect ON_FIRE = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_ON_FIRE, 24.0, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect ACID_VENOM = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_ACID_VENOM, 36.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect DARKNESS = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect BLACKOUT = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect SILENCED = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect CURSED = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect CONFUSED = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect SLOW = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect OOZED = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WEAK = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect BROKEN_ARMOUR = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WITHERED_ARMOUR = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WITHERED_WEAPON = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect HORRIFIED = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect THE_TONGUE = new MxtStatusEffect(false, true);
    public static final StatusEffect CURSED_INFERNO = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_CURSED_INFERNO, 36.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect ICHOR = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect FROSTBURN = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_FROSTBURN, 36.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect CHILLED = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect FROZEN = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WEBBED = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect STONED = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect DISTORTED = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect OBSTRUCTED = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect ELECTRIFIED = new MxtStatusEffect(false, true);
    public static final StatusEffect FERAL_BITE = new MxtStatusEffect(false, true);
//    public static final StatusEffect MOON_BITE = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);

    // items and environment
//    public static final StatusEffect MANA_SICKNESS = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect POTION_SICKNESS = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect CHAOS_STATE = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect SUFFOCATION = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_SUFFOCATION, 120.0, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect BURNING = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_BURNING, 180.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect TIPSY = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_BURNING, 180.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect LOVESTRUCK = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_BURNING, 180.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect STINKY = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_BURNING, 180.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect WATER_CANDLE = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_BURNING, 180.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect MIGHTY_WIND = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_BURNING, 180.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect CREATIVE_SHOCK = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_BURNING, 180.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect PECKISH = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_BURNING, 180.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect HUNGRY = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_BURNING, 180.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect STARVING = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_BURNING, 180.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect CEREBRAL_MINDTRICK = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_BURNING, 180.0, EntityAttributeModifier.Operation.ADDITION);
//    public static final StatusEffect SHIMMERING = new MxtStatusEffect(false, true).addAttributeModifier(EntityAttributesRegistry.MXT_NEGATIVE_HEALTH_REGENERATION_RATE, AttributeModifierUUIDs.MXT_NEGATIVE_HEALTH_REGENERATION_RATE_BURNING, 180.0, EntityAttributeModifier.Operation.ADDITION);

    // other TODO

    public static void registerStatusEffects() {
        // ----buffs----
        // consumables
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 0, "ammo_reservation", AMMO_RESERVATION);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 1, "ammo_reservation", ARCHERY);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 2, "ammo_reservation", BATTLE);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 3, "ammo_reservation", BUILDER);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 4, "ammo_reservation", CALM);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 5, "ammo_reservation", CRATE);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 6, "ammo_reservation", DANGERSENSE);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 7, "ammo_reservation", ENDURANCE);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 8, "ammo_reservation", EXQUISITELY_STUFFED);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 9, "ammo_reservation", FEATHERFALL);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 10, "ammo_reservation", FISHING);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 11, "ammo_reservation", FLIPPER);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 12, "ammo_reservation", GILLS);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 13, "ammo_reservation", GRAVITATION);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 14, "ammo_reservation", HEARTREACH);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 15, "ammo_reservation", HUNTER);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 16, "ammo_reservation", INFERNO);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 17, "ammo_reservation", INVISIBILITY);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 18, "ammo_reservation", IRONSKIN);
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 19, "lifeforce", LIFEFORCE);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 20, "ammo_reservation", LUCKY);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 21, "ammo_reservation", MAGIC_POWER);
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 22, "mana_regeneration", MANA_REGENERATION);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 23, "mining", MINING);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 24, "night_owl", NIGHT_OWL);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 25, "obsidian_skin", OBSIDIAN_SKIN);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 26, "plenty_satisfied", PLENTY_SATISFIED);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 27, "rage", RAGE);
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 28, "health_regeneration", HEALTH_REGENERATION);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 29, "regeneration", SHINE);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 30, "regeneration", SONAR);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 31, "regeneration", SPELUNKER);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 32, "regeneration", SUMMONING);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 33, "regeneration", SWIFTNESS);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 34, "regeneration", THORNS);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 35, "regeneration", TITAN);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 36, "regeneration", WARMTH);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 37, "regeneration", WATER_WALKING);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 38, "regeneration", WRATH);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 39, "regeneration", WELL_FED);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 40, "regeneration", WEAPON_IMBUE_CONFETTI);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 41, "regeneration", WEAPON_IMBUE_CURSED_FLAMES);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 42, "regeneration", WEAPON_IMBUE_FIRE);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 43, "regeneration", WEAPON_IMBUE_GOLD);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 44, "regeneration", WEAPON_IMBUE_ICHOR);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 45, "regeneration", WEAPON_IMBUE_NANITES);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 46, "regeneration", WEAPON_IMBUE_POISON);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_CONSUMABLES + 47, "regeneration", WEAPON_IMBUE_VENOM);

        // equipment
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT, "beetle_endurance", BEETLE_ENDURANCE);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 1, "beetle_might", BEETLE_MIGHT);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 2, "damage_nebula", DAMAGE_NEBULA);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 3, "durendals_blessing", DURENDALS_BLESSING);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 4, "harvest_time", HARVEST_TIME);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 5, "holy_protection", HOLY_PROTECTION);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 6, "ice_barrier", ICE_BARRIER);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 7, "jungles_fury", JUNGLES_FURY);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 8, "leaf_crystal", LEAF_CRYSTAL);
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 9, "life_drain", LIFE_DRAIN);
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 10, "life_nebula", LIFE_NEBULA);
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 11, "mana_nebula", MANA_NEBULA);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 12, "merfolk", MERFOLK);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 13, "panic", PANIC);
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 14, "rapid_healing", RAPID_HEALING);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 15, "titanium_barrier", TITANIUM_BARRIER);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 16, "solar_blaze", SOLAR_BLAZE);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 17, "stardust_guardian", STARDUST_GUARDIAN);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 18, "striking_moment", STRIKING_MOMENT);
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_EQUIPMENT + 19, "werewolf", WEREWOLF);

        // activated furniture

        // environmental

        // summons

        // mounts

        // pets

        // light pets

        // debuffs
        // enemies
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_ENEMIES, "bleeding", BLEEDING);
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_ENEMIES + 1, "poisoned", POISONED);
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_ENEMIES + 2, "on_fire", ON_FIRE);
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_ENEMIES + 3, "acid_venom", ACID_VENOM);

        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_ENEMIES + 16, "the_tongue", THE_TONGUE);
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_ENEMIES + 17, "cursed_inferno", CURSED_INFERNO);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_ENEMIES + 18, "ichor", ICHOR);
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_ENEMIES + 19, "frostburn", FROSTBURN);

        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_ENEMIES + 26, "electrified", ELECTRIFIED);
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_ENEMIES + 27, "feral_bite", FERAL_BITE);
//        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_ENEMIES + 28, "moon_bite", MOON_BITE);

        // items and environment
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_ITEMS + 4, "suffocation", SUFFOCATION);
        Registry.register(Registries.STATUS_EFFECT, RAW_ID_OFFSET_ITEMS + 5, "burning", BURNING);

        // other
    }
}
