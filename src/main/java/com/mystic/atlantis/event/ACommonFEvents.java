package com.mystic.atlantis.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.init.EffectsInit;
import com.mystic.atlantis.structures.AtlantisConfiguredStructures;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ACommonFEvents {

    public static final String NOT_FIRST_SPAWN_NBT = "atlantis.not_first_spawn";
    public static ResourceKey<Level> previousDimension;

    @SubscribeEvent
    public static void spikesEffectEvent(final LivingHurtEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Random random = player.getRandom();
            Entity entity = event.getSource().getEntity();
            if (player.hasEffect(EffectsInit.SPIKES.get())) {
                if (player.isHurt()) {
                    entity.hurt(DamageSource.thorns(player), (float) getDamage(3, random));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoginEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (AtlantisConfig.INSTANCE.startInAtlantis.get()) {
            if (event.getPlayer().getServer() != null) {
                ServerLevel atlantisLevel = event.getPlayer().getServer().getLevel(DimensionAtlantis.ATLANTIS_WORLD);
                CompoundTag tag = event.getPlayer().getPersistentData();
                CompoundTag persistedTag = tag.getCompound(Player.PERSISTED_NBT_TAG);
                if (DimensionAtlantis.ATLANTIS_WORLD != null) {
                    boolean isFirstTimeSpawning = !persistedTag.getBoolean(NOT_FIRST_SPAWN_NBT);
                    if (isFirstTimeSpawning) {
                        if (atlantisLevel != null) {
                            persistedTag.putBoolean(NOT_FIRST_SPAWN_NBT, true);
                            tag.put(Player.PERSISTED_NBT_TAG, persistedTag);
                            if (event.getPlayer() instanceof ServerPlayer serverPlayer) {
                                sendPlayerToDimension(serverPlayer, atlantisLevel, new Vec3(atlantisLevel.getLevel().getLevelData().getXSpawn(), 100, atlantisLevel.getLevel().getLevelData().getZSpawn()));
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            ServerLevel serverLevel = serverPlayer.getLevel();
            if (DimensionAtlantis.ATLANTIS_DIMENSION != null) {
                if (previousDimension == DimensionAtlantis.ATLANTIS_WORLD) {
                    serverPlayer.setRespawnPosition(DimensionAtlantis.ATLANTIS_WORLD, serverPlayer.blockPosition(), serverPlayer.getYHeadRot(), true, false);
                    serverPlayer.getLevel().setDefaultSpawnPos(serverPlayer.blockPosition(), 16);
                    if (serverPlayer.getRespawnPosition() != null) {
                        Optional<Vec3> bedPos = Player.findRespawnPositionAndUseSpawnBlock(DimensionAtlantis.ATLANTIS_DIMENSION, serverPlayer.getRespawnPosition(), serverPlayer.getRespawnAngle(), serverPlayer.isRespawnForced(), false);
                        if (bedPos.isEmpty()) {
                            serverPlayer.setRespawnPosition(DimensionAtlantis.ATLANTIS_WORLD, serverLevel.getSharedSpawnPos(), serverPlayer.getYHeadRot(), true, false);
                            sendPlayerToDimension(serverPlayer, DimensionAtlantis.ATLANTIS_DIMENSION, new Vec3(serverPlayer.getRespawnPosition().getX(), serverPlayer.getRespawnPosition().getY(), serverPlayer.getRespawnPosition().getZ()));
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDeathEvent(LivingDeathEvent event) {
        previousDimension = event.getEntityLiving().getLevel().dimension();
    }


    public static ServerLevel getDimension(ResourceKey<Level> arg, ServerPlayer player) {
        return Objects.requireNonNull(player.getServer()).getLevel(arg);
    }

    private static void sendPlayerToDimension(ServerPlayer serverPlayer, ServerLevel targetWorld, Vec3 targetVec) {
        // ensure destination chunk is loaded before we put the player in it
        targetWorld.getChunk(new BlockPos(targetVec));
        serverPlayer.teleportTo(targetWorld, targetVec.x(), targetVec.y(), targetVec.z(), serverPlayer.getYRot(), serverPlayer.getXRot());
    }

    private static int getDamage(int i, Random random) {
        return i > 10 ? i - 10 : 1 + random.nextInt(4);
    }

    @SubscribeEvent
    public static void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerLevel serverLevel) {
            ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();

            // Skip superflat worlds to prevent issues with it. Plus, users don't want structures clogging up their superflat worlds.
            if (chunkGenerator instanceof FlatLevelSource && serverLevel.dimensionType().equals(Level.OVERWORLD)) {
                return;
            }

            StructureSettings worldStructureConfig = chunkGenerator.possibleStructureSets().getSettings();

            HashMap<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> STStructureToMultiMap = new HashMap<>();

            for (Map.Entry<ResourceKey<Biome>, Biome> biomeEntry : serverLevel.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).entrySet()) {
                // Skip all ocean, end, nether, and none category biomes.
                // You can do checks for other traits that the biome has.
                if (isAtlanteanBiome(biomeEntry.getKey())) {
                    associateBiomeToConfiguredStructure(STStructureToMultiMap, AtlantisConfiguredStructures.CONFIGURED_ATLANTEAN_FOUNTAIN, biomeEntry.getKey());
                    associateBiomeToConfiguredStructure(STStructureToMultiMap, AtlantisConfiguredStructures.CONFIGURED_ATLANTEAN_HOUSE_1, biomeEntry.getKey());
                    associateBiomeToConfiguredStructure(STStructureToMultiMap, AtlantisConfiguredStructures.CONFIGURED_ATLANTEAN_HOUSE_3, biomeEntry.getKey());
                    associateBiomeToConfiguredStructure(STStructureToMultiMap, AtlantisConfiguredStructures.CONFIGURED_ATLANTEAN_TOWER, biomeEntry.getKey());
                    associateBiomeToConfiguredStructure(STStructureToMultiMap, AtlantisConfiguredStructures.CONFIGURED_OYSTER_STRUCTURE, biomeEntry.getKey());
                }
            }

            ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> tempStructureToMultiMap = ImmutableMap.builder();
            worldStructureConfig.configuredStructures.entrySet().stream().filter(entry -> !STStructureToMultiMap.containsKey(entry.getKey())).forEach(tempStructureToMultiMap::put);

            // Add our structures to the structure map/multimap and set the world to use this combined map/multimap.
            STStructureToMultiMap.forEach((key, value) -> tempStructureToMultiMap.put(key, ImmutableMultimap.copyOf(value)));

            // Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
            worldStructureConfig.configuredStructures = tempStructureToMultiMap.build();
        }
    }
    /**
     * Helper method that handles setting up the map to multimap relationship to help prevent issues.
     */
    private static void associateBiomeToConfiguredStructure(Map<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> STStructureToMultiMap, ConfiguredStructureFeature<?, ?> configuredStructureFeature, ResourceKey<Biome> biomeRegistryKey) {
        STStructureToMultiMap.putIfAbsent(configuredStructureFeature.feature, HashMultimap.create());
        HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> configuredStructureToBiomeMultiMap = STStructureToMultiMap.get(configuredStructureFeature.feature);
        if (configuredStructureToBiomeMultiMap.containsValue(biomeRegistryKey)) {
            Atlantis.LOGGER.error(String.format("""
                                Detected 2 ConfiguredStructureFeatures that share the same base StructureFeature trying to be added to same biome. One will be prevented from spawning.
                                This issue happens with vanilla too and is why a Snowy Village and Plains Village cannot spawn in the same biome because they both use the Village base structure.
                                The two conflicting ConfiguredStructures are: %s, %s
                                The biome that is attempting to be shared: %s
                            """,
                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getKey(configuredStructureFeature).toString(),
                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getKey(configuredStructureToBiomeMultiMap.entries().stream().filter(e -> e.getValue() == biomeRegistryKey).findFirst().get().getKey()).toString(),
                    biomeRegistryKey
            ));
        } else {
            configuredStructureToBiomeMultiMap.put(configuredStructureFeature, biomeRegistryKey);
        }
    }
  
    private static boolean isAtlanteanBiome(ResourceKey<Biome> key) {

        return key.location().getNamespace().
                equals(Reference.MODID);
    }

}
