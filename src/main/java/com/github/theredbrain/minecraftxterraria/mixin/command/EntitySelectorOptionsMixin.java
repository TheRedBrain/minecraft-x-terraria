package com.github.theredbrain.minecraftxterraria.mixin.command;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.command.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.predicate.NumberRange;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.*;
import java.util.function.Predicate;

@Mixin(EntitySelectorOptions.class)
public class EntitySelectorOptionsMixin {

    @Shadow
    @Final
    private static Map<String, EntitySelectorOptions.SelectorOption> OPTIONS;

    @Shadow
    @Final
    public static DynamicCommandExceptionType INAPPLICABLE_OPTION_EXCEPTION;

    @Shadow
    @Final
    public static SimpleCommandExceptionType NEGATIVE_DISTANCE_EXCEPTION;

    @Shadow
    @Final
    public static SimpleCommandExceptionType NEGATIVE_LEVEL_EXCEPTION;

    @Shadow
    @Final
    public static SimpleCommandExceptionType TOO_SMALL_LEVEL_EXCEPTION;

    @Shadow
    @Final
    public static DynamicCommandExceptionType IRREVERSIBLE_SORT_EXCEPTION;

    @Shadow
    @Final
    public static DynamicCommandExceptionType INVALID_MODE_EXCEPTION;

    @Shadow
    @Final
    public static DynamicCommandExceptionType INVALID_TYPE_EXCEPTION;
    
    @Shadow
    private static void putOption(String id, EntitySelectorOptions.SelectorHandler handler, Predicate<EntitySelectorReader> condition, Text description) {
        throw new AssertionError();
    }

    /**
     * @author TheRedBrain
     * @reason use custom inventory
     */
    @Overwrite
    public static void register() {
        if (!OPTIONS.isEmpty()) {
            return;
        }
        putOption("name", reader2 -> {
            int i = reader2.getReader().getCursor();
            boolean bl = reader2.readNegationCharacter();
            String string = reader2.getReader().readString();
            if (reader2.excludesName() && !bl) {
                reader2.getReader().setCursor(i);
                throw INAPPLICABLE_OPTION_EXCEPTION.createWithContext(reader2.getReader(), "name");
            }
            if (bl) {
                reader2.setExcludesName(true);
            } else {
                reader2.setSelectsName(true);
            }
            reader2.setPredicate(reader -> reader.getName().getString().equals(string) != bl);
        }, reader -> !reader.selectsName(), Text.translatable("argument.entity.options.name.description"));
        putOption("distance", reader -> {
            int i = reader.getReader().getCursor();
            NumberRange.FloatRange floatRange = NumberRange.FloatRange.parse(reader.getReader());
            if (floatRange.getMin() != null && (Double)floatRange.getMin() < 0.0 || floatRange.getMax() != null && (Double)floatRange.getMax() < 0.0) {
                reader.getReader().setCursor(i);
                throw NEGATIVE_DISTANCE_EXCEPTION.createWithContext(reader.getReader());
            }
            reader.setDistance(floatRange);
            reader.setLocalWorldOnly();
        }, reader -> reader.getDistance().isDummy(), Text.translatable("argument.entity.options.distance.description"));
        putOption("level", reader -> {
            int i = reader.getReader().getCursor();
            NumberRange.IntRange intRange = NumberRange.IntRange.parse(reader.getReader());
            if (intRange.getMin() != null && (Integer)intRange.getMin() < 0 || intRange.getMax() != null && (Integer)intRange.getMax() < 0) {
                reader.getReader().setCursor(i);
                throw NEGATIVE_LEVEL_EXCEPTION.createWithContext(reader.getReader());
            }
            reader.setLevelRange(intRange);
            reader.setIncludesNonPlayers(false);
        }, reader -> reader.getLevelRange().isDummy(), Text.translatable("argument.entity.options.level.description"));
        putOption("x", reader -> {
            reader.setLocalWorldOnly();
            reader.setX(reader.getReader().readDouble());
        }, reader -> reader.getX() == null, Text.translatable("argument.entity.options.x.description"));
        putOption("y", reader -> {
            reader.setLocalWorldOnly();
            reader.setY(reader.getReader().readDouble());
        }, reader -> reader.getY() == null, Text.translatable("argument.entity.options.y.description"));
        putOption("z", reader -> {
            reader.setLocalWorldOnly();
            reader.setZ(reader.getReader().readDouble());
        }, reader -> reader.getZ() == null, Text.translatable("argument.entity.options.z.description"));
        putOption("dx", reader -> {
            reader.setLocalWorldOnly();
            reader.setDx(reader.getReader().readDouble());
        }, reader -> reader.getDx() == null, Text.translatable("argument.entity.options.dx.description"));
        putOption("dy", reader -> {
            reader.setLocalWorldOnly();
            reader.setDy(reader.getReader().readDouble());
        }, reader -> reader.getDy() == null, Text.translatable("argument.entity.options.dy.description"));
        putOption("dz", reader -> {
            reader.setLocalWorldOnly();
            reader.setDz(reader.getReader().readDouble());
        }, reader -> reader.getDz() == null, Text.translatable("argument.entity.options.dz.description"));
        putOption("x_rotation", reader -> reader.setPitchRange(FloatRangeArgument.parse(reader.getReader(), true, MathHelper::wrapDegrees)), reader -> reader.getPitchRange() == FloatRangeArgument.ANY, Text.translatable("argument.entity.options.x_rotation.description"));
        putOption("y_rotation", reader -> reader.setYawRange(FloatRangeArgument.parse(reader.getReader(), true, MathHelper::wrapDegrees)), reader -> reader.getYawRange() == FloatRangeArgument.ANY, Text.translatable("argument.entity.options.y_rotation.description"));
        putOption("limit", reader -> {
            int i = reader.getReader().getCursor();
            int j = reader.getReader().readInt();
            if (j < 1) {
                reader.getReader().setCursor(i);
                throw TOO_SMALL_LEVEL_EXCEPTION.createWithContext(reader.getReader());
            }
            reader.setLimit(j);
            reader.setHasLimit(true);
        }, reader -> !reader.isSenderOnly() && !reader.hasLimit(), Text.translatable("argument.entity.options.limit.description"));
        putOption("sort", reader -> {
            int i = reader.getReader().getCursor();
            String string = reader.getReader().readUnquotedString();
            reader.setSuggestionProvider((builder, consumer) -> CommandSource.suggestMatching(Arrays.asList("nearest", "furthest", "random", "arbitrary"), builder));
            reader.setSorter(switch (string) {
                case "nearest" -> EntitySelectorReader.NEAREST;
                case "furthest" -> EntitySelectorReader.FURTHEST;
                case "random" -> EntitySelectorReader.RANDOM;
                case "arbitrary" -> EntitySelector.ARBITRARY;
                default -> {
                    reader.getReader().setCursor(i);
                    throw IRREVERSIBLE_SORT_EXCEPTION.createWithContext(reader.getReader(), string);
                }
            });
            reader.setHasSorter(true);
        }, reader -> !reader.isSenderOnly() && !reader.hasSorter(), Text.translatable("argument.entity.options.sort.description"));
        putOption("gamemode", reader -> {
            reader.setSuggestionProvider((builder, consumer) -> {
                String string = builder.getRemaining().toLowerCase(Locale.ROOT);
                boolean bl = !reader.excludesGameMode();
                boolean bl2 = true;
                if (!string.isEmpty()) {
                    if (string.charAt(0) == '!') {
                        bl = false;
                        string = string.substring(1);
                    } else {
                        bl2 = false;
                    }
                }
                for (GameMode gameMode : GameMode.values()) {
                    if (!gameMode.getName().toLowerCase(Locale.ROOT).startsWith(string)) continue;
                    if (bl2) {
                        builder.suggest("!" + gameMode.getName());
                    }
                    if (!bl) continue;
                    builder.suggest(gameMode.getName());
                }
                return builder.buildFuture();
            });
            int i = reader.getReader().getCursor();
            boolean bl = reader.readNegationCharacter();
            if (reader.excludesGameMode() && !bl) {
                reader.getReader().setCursor(i);
                throw INAPPLICABLE_OPTION_EXCEPTION.createWithContext(reader.getReader(), "gamemode");
            }
            String string = reader.getReader().readUnquotedString();
            GameMode gameMode = GameMode.byName(string, null);
            if (gameMode == null) {
                reader.getReader().setCursor(i);
                throw INVALID_MODE_EXCEPTION.createWithContext(reader.getReader(), string);
            }
            reader.setIncludesNonPlayers(false);
            reader.setPredicate(entity -> {
                if (!(entity instanceof ServerPlayerEntity)) {
                    return false;
                }
                GameMode gameMode2 = ((ServerPlayerEntity)entity).interactionManager.getGameMode();
                return bl ? gameMode2 != gameMode : gameMode2 == gameMode;
            });
            if (bl) {
                reader.setExcludesGameMode(true);
            } else {
                reader.setSelectsGameMode(true);
            }
        }, reader -> !reader.selectsGameMode(), Text.translatable("argument.entity.options.gamemode.description"));
        putOption("team", reader -> {
            boolean bl = reader.readNegationCharacter();
            String string = reader.getReader().readUnquotedString();
            reader.setPredicate(entity -> {
                if (!(entity instanceof LivingEntity)) {
                    return false;
                }
                AbstractTeam abstractTeam = entity.getScoreboardTeam();
                String string2 = abstractTeam == null ? "" : abstractTeam.getName();
                return string2.equals(string) != bl;
            });
            if (bl) {
                reader.setExcludesTeam(true);
            } else {
                reader.setSelectsTeam(true);
            }
        }, reader -> !reader.selectsTeam(), Text.translatable("argument.entity.options.team.description"));
        putOption("type", reader -> {
            reader.setSuggestionProvider((builder, consumer) -> {
                CommandSource.suggestIdentifiers(Registries.ENTITY_TYPE.getIds(), builder, String.valueOf('!'));
                CommandSource.suggestIdentifiers(Registries.ENTITY_TYPE.streamTags().map(TagKey::id), builder, "!#");
                if (!reader.excludesEntityType()) {
                    CommandSource.suggestIdentifiers(Registries.ENTITY_TYPE.getIds(), builder);
                    CommandSource.suggestIdentifiers(Registries.ENTITY_TYPE.streamTags().map(TagKey::id), builder, String.valueOf('#'));
                }
                return builder.buildFuture();
            });
            int i = reader.getReader().getCursor();
            boolean bl = reader.readNegationCharacter();
            if (reader.excludesEntityType() && !bl) {
                reader.getReader().setCursor(i);
                throw INAPPLICABLE_OPTION_EXCEPTION.createWithContext(reader.getReader(), "type");
            }
            if (bl) {
                reader.setExcludesEntityType();
            }
            if (reader.readTagCharacter()) {
                TagKey<EntityType<?>> tagKey = TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.fromCommandInput(reader.getReader()));
                reader.setPredicate(entity -> entity.getType().isIn(tagKey) != bl);
            } else {
                Identifier identifier = Identifier.fromCommandInput(reader.getReader());
                EntityType entityType = (EntityType)Registries.ENTITY_TYPE.getOrEmpty(identifier).orElseThrow(() -> {
                    reader.getReader().setCursor(i);
                    return INVALID_TYPE_EXCEPTION.createWithContext(reader.getReader(), identifier.toString());
                });
                if (Objects.equals(EntityType.PLAYER, entityType) && !bl) {
                    reader.setIncludesNonPlayers(false);
                }
                reader.setPredicate(entity -> Objects.equals(entityType, entity.getType()) != bl);
                if (!bl) {
                    reader.setEntityType(entityType);
                }
            }
        }, reader -> !reader.selectsEntityType(), Text.translatable("argument.entity.options.type.description"));
        putOption("tag", reader -> {
            boolean bl = reader.readNegationCharacter();
            String string = reader.getReader().readUnquotedString();
            reader.setPredicate(entity -> {
                if ("".equals(string)) {
                    return entity.getScoreboardTags().isEmpty() != bl;
                }
                return entity.getScoreboardTags().contains(string) != bl;
            });
        }, reader -> true, Text.translatable("argument.entity.options.tag.description"));
        putOption("nbt", reader -> {
            boolean bl = reader.readNegationCharacter();
            NbtCompound nbtCompound = new StringNbtReader(reader.getReader()).parseCompound();
            reader.setPredicate(entity -> {
                ItemStack itemStack;
                NbtCompound nbtCompound2 = entity.writeNbt(new NbtCompound());
                if (entity instanceof ServerPlayerEntity && !(itemStack = ((ServerPlayerEntity)entity).getInventory().getMainHandStack()).isEmpty()) {
                    nbtCompound2.put("SelectedItem", itemStack.writeNbt(new NbtCompound()));
                }
                return NbtHelper.matches(nbtCompound, nbtCompound2, true) != bl;
            });
        }, reader -> true, Text.translatable("argument.entity.options.nbt.description"));
        putOption("scores", reader -> {
            StringReader stringReader = reader.getReader();
            HashMap<String, NumberRange.IntRange> map = Maps.newHashMap();
            stringReader.expect('{');
            stringReader.skipWhitespace();
            while (stringReader.canRead() && stringReader.peek() != '}') {
                stringReader.skipWhitespace();
                String string = stringReader.readUnquotedString();
                stringReader.skipWhitespace();
                stringReader.expect('=');
                stringReader.skipWhitespace();
                NumberRange.IntRange intRange = NumberRange.IntRange.parse(stringReader);
                map.put(string, intRange);
                stringReader.skipWhitespace();
                if (!stringReader.canRead() || stringReader.peek() != ',') continue;
                stringReader.skip();
            }
            stringReader.expect('}');
            if (!map.isEmpty()) {
                reader.setPredicate(entity -> {
                    ServerScoreboard scoreboard = entity.getServer().getScoreboard();
                    String string = entity.getEntityName();
                    for (Map.Entry entry : map.entrySet()) {
                        ScoreboardObjective scoreboardObjective = scoreboard.getNullableObjective((String)entry.getKey());
                        if (scoreboardObjective == null) {
                            return false;
                        }
                        if (!scoreboard.playerHasObjective(string, scoreboardObjective)) {
                            return false;
                        }
                        ScoreboardPlayerScore scoreboardPlayerScore = scoreboard.getPlayerScore(string, scoreboardObjective);
                        int i = scoreboardPlayerScore.getScore();
                        if (((NumberRange.IntRange)entry.getValue()).test(i)) continue;
                        return false;
                    }
                    return true;
                });
            }
            reader.setSelectsScores(true);
        }, reader -> !reader.selectsScores(), Text.translatable("argument.entity.options.scores.description"));
        putOption("advancements", reader -> {
            StringReader stringReader = reader.getReader();
            HashMap<Identifier, Predicate<AdvancementProgress>> map = Maps.newHashMap();
            stringReader.expect('{');
            stringReader.skipWhitespace();
            while (stringReader.canRead() && stringReader.peek() != '}') {
                stringReader.skipWhitespace();
                Identifier identifier = Identifier.fromCommandInput(stringReader);
                stringReader.skipWhitespace();
                stringReader.expect('=');
                stringReader.skipWhitespace();
                if (stringReader.canRead() && stringReader.peek() == '{') {
                    HashMap<String, Predicate<CriterionProgress>> map2 = Maps.newHashMap();
                    stringReader.skipWhitespace();
                    stringReader.expect('{');
                    stringReader.skipWhitespace();
                    while (stringReader.canRead() && stringReader.peek() != '}') {
                        stringReader.skipWhitespace();
                        String string = stringReader.readUnquotedString();
                        stringReader.skipWhitespace();
                        stringReader.expect('=');
                        stringReader.skipWhitespace();
                        boolean bl = stringReader.readBoolean();
                        map2.put(string, criterionProgress -> criterionProgress.isObtained() == bl);
                        stringReader.skipWhitespace();
                        if (!stringReader.canRead() || stringReader.peek() != ',') continue;
                        stringReader.skip();
                    }
                    stringReader.skipWhitespace();
                    stringReader.expect('}');
                    stringReader.skipWhitespace();
                    map.put(identifier, advancementProgress -> {
                        for (Map.Entry entry : map2.entrySet()) {
                            CriterionProgress criterionProgress = advancementProgress.getCriterionProgress((String)entry.getKey());
                            if (criterionProgress != null && ((Predicate)entry.getValue()).test(criterionProgress)) continue;
                            return false;
                        }
                        return true;
                    });
                } else {
                    boolean bl2 = stringReader.readBoolean();
                    map.put(identifier, advancementProgress -> advancementProgress.isDone() == bl2);
                }
                stringReader.skipWhitespace();
                if (!stringReader.canRead() || stringReader.peek() != ',') continue;
                stringReader.skip();
            }
            stringReader.expect('}');
            if (!map.isEmpty()) {
                reader.setPredicate(entity -> {
                    if (!(entity instanceof ServerPlayerEntity)) {
                        return false;
                    }
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
                    PlayerAdvancementTracker playerAdvancementTracker = serverPlayerEntity.getAdvancementTracker();
                    ServerAdvancementLoader serverAdvancementLoader = serverPlayerEntity.getServer().getAdvancementLoader();
                    for (Map.Entry entry : map.entrySet()) {
                        Advancement advancement = serverAdvancementLoader.get((Identifier)entry.getKey());
                        if (advancement != null && ((Predicate)entry.getValue()).test(playerAdvancementTracker.getProgress(advancement))) continue;
                        return false;
                    }
                    return true;
                });
                reader.setIncludesNonPlayers(false);
            }
            reader.setSelectsAdvancements(true);
        }, reader -> !reader.selectsAdvancements(), Text.translatable("argument.entity.options.advancements.description"));
        putOption("predicate", reader -> {
            boolean bl = reader.readNegationCharacter();
            Identifier identifier = Identifier.fromCommandInput(reader.getReader());
            reader.setPredicate(entity -> {
                if (!(entity.world instanceof ServerWorld)) {
                    return false;
                }
                ServerWorld serverWorld = (ServerWorld)entity.world;
                LootCondition lootCondition = serverWorld.getServer().getPredicateManager().get(identifier);
                if (lootCondition == null) {
                    return false;
                }
                LootContext lootContext = new LootContext.Builder(serverWorld).parameter(LootContextParameters.THIS_ENTITY, entity).parameter(LootContextParameters.ORIGIN, entity.getPos()).build(LootContextTypes.SELECTOR);
                return bl ^ lootCondition.test(lootContext);
            });
        }, reader -> true, Text.translatable("argument.entity.options.predicate.description"));
    }

}
