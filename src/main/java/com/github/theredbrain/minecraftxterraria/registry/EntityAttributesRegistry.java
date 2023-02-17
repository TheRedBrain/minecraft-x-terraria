package com.github.theredbrain.minecraftxterraria.registry;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class EntityAttributesRegistry {
    // dealing damage
    public static final EntityAttribute MXT_KNOCKBACK = new ClampedEntityAttribute("mxt_attribute.name.generic.mxt_knockback", 0.0, 0.0, 24.0).setTracked(true);

    public static final EntityAttribute MXT_MELEE_ATTACK_DAMAGE = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_melee_attack_damage", 0.0, 0.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_RANGED_ATTACK_DAMAGE = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_ranged_attack_damage", 0.0, 0.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_MAGIC_ATTACK_DAMAGE = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_magic_attack_damage", 0.0, 0.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_SUMMON_ATTACK_DAMAGE = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_summon_attack_damage", 0.0, 0.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_ARMOUR_PENETRATION = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_armour_penetration", 0.0, 0.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_CRIT_CHANCE = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_crit_chance", 0.0, 0.0, 100.0).setTracked(true);
    public static final EntityAttribute MXT_MELEE_CRIT_CHANCE_BONUS = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_melee_crit_chance_bonus", 0.0, 0.0, 100.0).setTracked(true);
    public static final EntityAttribute MXT_RANGED_CRIT_CHANCE_BONUS = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_ranged_crit_chance_bonus", 0.0, 0.0, 100.0).setTracked(true);
    public static final EntityAttribute MXT_MAGIC_CRIT_CHANCE_BONUS = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_magic_crit_chance_bonus", 0.0, 0.0, 100.0).setTracked(true);
    public static final EntityAttribute MXT_SUMMON_CRIT_CHANCE_BONUS = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_summon_crit_chance_bonus", 0.0, 0.0, 100.0).setTracked(true);

    public static final EntityAttribute MXT_MONSTER_ATTACK_DAMAGE = new ClampedEntityAttribute("mxt_attribute.name.monster.mxt_monster_attack_damage", 20.0, 1.0, 1024.0).setTracked(true); // TODO

    // taking damage
    public static final EntityAttribute MXT_KNOCKBACK_RESISTANCE = new ClampedEntityAttribute("mxt_attribute.name.generic.mxt_knockback_resistance", 0.0, 0.0, 100.0).setTracked(true);
    public static final EntityAttribute MXT_DEFENSE = new ClampedEntityAttribute("mxt_attribute.name.generic.mxt_defense", 0.0, 0.0, 9999.0).setTracked(true);

    public static final EntityAttribute MXT_DAMAGE_REDUCTION = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_damage_reduction", 0.0, 0.0, 100.0).setTracked(true);

    // pool attribute
    public static final EntityAttribute MXT_MAX_HEALTH = new ClampedEntityAttribute("mxt_attribute.name.generic.mxt_max_health", 100.0, 1.0, 100000.0).setTracked(true);
        // TODO is modified by
        //

    public static final EntityAttribute MXT_MAX_EFFECTIVE_HEALTH_REGENERATION_TIME = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_max_effective_health_regeneration_time", 9.0, 0.0, 38.0).setTracked(true);
        // TODO is modified by
        //  shiny stone

    public static final EntityAttribute MXT_MAX_HEALTH_REGENERATION_TIME = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_max_health_regeneration_time", 3600.0, 0.0, 12000.0).setTracked(true);
        // TODO is modified by
        //  shiny stone

    public static final EntityAttribute MXT_BONUS_HEALTH_REGENERATION_TIME = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_bonus_health_regeneration_time", 0.0, 0.0, 100.0).setTracked(true); // TODO adjust max
        // TODO is modified by
        //  crimson armour + 3
        //  life drain  +15 (and +3 per 2 additional enemies affected)
        //  shiny stone +12

    public static final EntityAttribute MXT_BASE_HEALTH_REGENERATION_RATE_MODIFIER = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_base_health_regeneration_rate_modifier", 1.0, 1.0, 10.0).setTracked(true);
        // TODO is modified by
        //  campfire
        //  crimson armour
        //  shiny stone

    public static final EntityAttribute MXT_BONUS_HEALTH_REGENERATION_RATE = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_bonus_health_regeneration_rate", 0.0, -300.0, 300.0).setTracked(true);
        // TODO is modified by
        //  heart lantern +6
        //  campfire +3
        //  life drain +9 (and +3 per 2 additional enemies affected)

    public static final EntityAttribute MXT_POSITIVE_HEALTH_REGENERATION_RATE = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_positive_health_regeneration_rate", 0.0, 00.0, 300.0).setTracked(true);
        // TODO is modified by
        //  band of regeneration buff +6
        //  celestial buff +6
        //  squire helmet +6
        //  solar flare helmet +6
        //  solar flare breastplate +6
        //  solar flare leggings +6
        //  dryads blessing +18
        //  valhalla knight breastplate +24
        //  life nebula +18/+36/+54

    public static final EntityAttribute MXT_NEGATIVE_HEALTH_REGENERATION_RATE = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_negative_health_regeneration_rate", 0.0, 0.0, 256.0).setTracked(true);
        // TODO is modified by
        //  poisoned +12
        //  on fire! +24
        //  venom, cursed inferno and frostburn +36
        //  suffocation +120
        //  electrified +24 (when standing still), or +96 (when moving)
        //  life drain +9 (and +3 per 2 additional enemies affected)

    public static final EntityAttribute MXT_MAX_MANA = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_max_mana", 20.0, 0.0, 400.0).setTracked(true);
        // TODO is modified by
        //

    public static final EntityAttribute MXT_MANA_USAGE_REDUCTION = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_mana_usage_reduction", 0.0, 0.0, 100.0).setTracked(true);
        // TODO is modified by
        //

    // attributes
    public static final EntityAttribute MXT_LUCK = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_luck", 20.0, 0.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_FISHING_POWER = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_fishing_power", 0.0, 0.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_MINING_SPEED = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_mining_speed", 1.0, 1.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_USE_SPEED = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_use_speed", 1.0, 1.0, 1024.0).setTracked(true); // TODO

    // entity interaction ranges
    public static final EntityAttribute MXT_COMMON_PICK_UP_RANGE = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_common_pick_up_range", 20.0, 0.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_BOOSTER_PICK_UP_RANGE = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_booster_pick_up_range", 20.0, 0.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_MANA_PICK_UP_RANGE = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_mana_pick_up_range", 20.0, 0.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_LIFE_PICK_UP_RANGE = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_life_pick_up_range", 20.0, 0.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_ETHERIAN_MANA_PICK_UP_RANGE = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_etherian_mana_pick_up_range", 20.0, 0.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_COINS_PICK_UP_RANGE = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_coin_pick_up_range", 20.0, 0.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_AGGRO_RANGE = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_aggro_range", 0.0, -4300.0, 2200.0).setTracked(true);
    public static final EntityAttribute MXT_NPC_INTERACTION_REACH = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_npc_interaction_range", 20.0, 1.0, 1024.0).setTracked(true); // TODO

    // block interaction ranges
    public static final EntityAttribute MXT_BLOCK_PLACEMENT_REACH = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_block_placement_range", 20.0, 1.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_BLOCK_BREAKING_REACH = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_block_breaking_range", 20.0, 1.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_CRAFTING_REACH = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_crafting_reach", 20.0, 1.0, 1024.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_BLOCK_INTERACTION_REACH = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_block_interaction_range", 20.0, 1.0, 1024.0).setTracked(true); // TODO

    // movement
    public static final EntityAttribute MXT_MAX_ACCELERATION = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_max_acceleration", 1.0, 0.0, 10.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_MAX_DECELERATION = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_max_deceleration", 1.0, 0.0, 10.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_MAX_MOVEMENT_SPEED = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_max_movement_speed", 1.0, 0.0, 100.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_MAX_JUMP_SPEED = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_max_jump_speed", 1.0, 0.0, 100.0).setTracked(true); // TODO
    public static final EntityAttribute MXT_MAX_JUMP_DURATION = new ClampedEntityAttribute("mxt_attribute.name.player.mxt_max_jump_duration", 1.0, 0.0, 100.0).setTracked(true); // TODO

    public static void registerAttributes() {

        // dealing damage
        Registry.register(Registries.ATTRIBUTE, "generic.mxt_knockback", MXT_KNOCKBACK);

        Registry.register(Registries.ATTRIBUTE, "player.mxt_melee_attack_damage", MXT_MELEE_ATTACK_DAMAGE);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_ranged_attack_damage", MXT_RANGED_ATTACK_DAMAGE);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_magic_attack_damage", MXT_MAGIC_ATTACK_DAMAGE);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_summon_attack_damage", MXT_SUMMON_ATTACK_DAMAGE);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_armour_penetration", MXT_ARMOUR_PENETRATION);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_crit_chance", MXT_CRIT_CHANCE);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_melee_crit_chance_bonus", MXT_MELEE_CRIT_CHANCE_BONUS);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_ranged_crit_chance_bonus", MXT_RANGED_CRIT_CHANCE_BONUS);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_magic_crit_chance_bonus", MXT_MAGIC_CRIT_CHANCE_BONUS);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_summon_crit_chance_bonus", MXT_SUMMON_CRIT_CHANCE_BONUS);

        Registry.register(Registries.ATTRIBUTE, "monster.mxt_monster_attack_damage", MXT_MONSTER_ATTACK_DAMAGE);

        // taking damage
        Registry.register(Registries.ATTRIBUTE, "generic.mxt_knockback_resistance", MXT_KNOCKBACK_RESISTANCE);
        Registry.register(Registries.ATTRIBUTE, "generic.mxt_defense", MXT_DEFENSE);

        Registry.register(Registries.ATTRIBUTE, "player.mxt_damage_reduction", MXT_DAMAGE_REDUCTION);

        // attribute pools
        Registry.register(Registries.ATTRIBUTE, "generic.mxt_max_health", MXT_MAX_HEALTH);

        Registry.register(Registries.ATTRIBUTE, "player.mxt_max_effective_health_regeneration_time", MXT_MAX_EFFECTIVE_HEALTH_REGENERATION_TIME);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_max_health_regeneration_time", MXT_MAX_HEALTH_REGENERATION_TIME);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_bonus_health_regeneration_time", MXT_BONUS_HEALTH_REGENERATION_TIME);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_base_health_regeneration_rate_modifier", MXT_BASE_HEALTH_REGENERATION_RATE_MODIFIER);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_bonus_health_regeneration_rate", MXT_BONUS_HEALTH_REGENERATION_RATE);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_positive_health_regeneration_rate", MXT_POSITIVE_HEALTH_REGENERATION_RATE);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_negative_health_regeneration_rate", MXT_NEGATIVE_HEALTH_REGENERATION_RATE);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_max_mana", MXT_MAX_MANA);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_mana_usage_reduction", MXT_MANA_USAGE_REDUCTION);
//        Registry.register(Registries.ATTRIBUTE, "mxt_mana_regeneration", MXT_MANA_REGENERATION);

        // attributes
        Registry.register(Registries.ATTRIBUTE, "player.mxt_luck", MXT_LUCK);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_fishing_power", MXT_FISHING_POWER);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_mining_speed", MXT_MINING_SPEED);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_use_speed", MXT_USE_SPEED);

        // entity interaction ranges
        Registry.register(Registries.ATTRIBUTE, "player.mxt_common_pick_up_range", MXT_COMMON_PICK_UP_RANGE);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_booster_pick_up_range", MXT_BOOSTER_PICK_UP_RANGE);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_mana_pick_up_range", MXT_MANA_PICK_UP_RANGE);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_life_pick_up_range", MXT_LIFE_PICK_UP_RANGE);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_etherian_mana_pick_up_range", MXT_ETHERIAN_MANA_PICK_UP_RANGE);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_coin_pick_up_range", MXT_COINS_PICK_UP_RANGE);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_aggro_range", MXT_AGGRO_RANGE);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_npc_interaction_range", MXT_NPC_INTERACTION_REACH);

        // block interaction ranges
        Registry.register(Registries.ATTRIBUTE, "player.mxt_block_placement_range", MXT_BLOCK_PLACEMENT_REACH);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_block_breaking_range", MXT_BLOCK_BREAKING_REACH);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_crafting_reach", MXT_CRAFTING_REACH);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_block_interaction_range", MXT_BLOCK_INTERACTION_REACH);

        // movement
        Registry.register(Registries.ATTRIBUTE, "player.mxt_max_acceleration", MXT_MAX_ACCELERATION);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_max_deceleration", MXT_MAX_DECELERATION);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_max_movement_speed", MXT_MAX_MOVEMENT_SPEED);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_max_jump_speed", MXT_MAX_JUMP_SPEED);
        Registry.register(Registries.ATTRIBUTE, "player.mxt_max_jump_duration", MXT_MAX_JUMP_DURATION);
    }
}
