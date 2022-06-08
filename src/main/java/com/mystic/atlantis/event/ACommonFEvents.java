package com.mystic.atlantis.event;

import com.mystic.atlantis.biomes.AtlantisBiomeSource;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.init.EffectsInit;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.Event;
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
    public static void onBiomeLightingRegister(BiomeLightingRegister event) {
        event.register(AtlantisBiomeSource.VOLCANIC_DARKSEA, 11);
        event.register(AtlantisBiomeSource.JELLYFISH_FIELDS, 8);
        event.register(AtlantisBiomeSource.ATLANTEAN_ISLANDS, 3);
        event.register(AtlantisBiomeSource.ATLANTIS_BIOME, 3);
        event.register(AtlantisBiomeSource.ATLANTEAN_GARDEN, 0);
    }

    public static Map<ResourceLocation, Integer> map;

    @SubscribeEvent
    public static void onServerLoad(ServerStartingEvent event) {
        BiomeLightingRegister biomeLightingRegister = new BiomeLightingRegister();
        MinecraftForge.EVENT_BUS.post(biomeLightingRegister);
        map = biomeLightingRegister.getBiomeMap();
    }

    public static class BiomeLightingRegister extends Event {
        Map<ResourceLocation, Integer> biomeMap = new HashMap<>();

        public BiomeLightingRegister() {}

        public void register(ResourceLocation resourceLocation, int lightLevel) {
            biomeMap.put(resourceLocation, lightLevel);
        }

        public Map<ResourceLocation, Integer> getBiomeMap() {
            return biomeMap;
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
  
    private static boolean isAtlanteanBiome(ResourceKey<Biome> key) {

        return key.location().getNamespace().
                equals(Reference.MODID);
    }

}
