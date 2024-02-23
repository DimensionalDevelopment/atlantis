package com.mystic.atlantis;

import com.mystic.atlantis.blocks.base.ExtendedBlockEntity;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.datagen.Providers;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.entities.*;
import com.mystic.atlantis.feature.AtlantisFeature;
import com.mystic.atlantis.init.*;
import com.mystic.atlantis.particles.ModParticleTypes;
import com.mystic.atlantis.screen.LinguisticScreen;
import com.mystic.atlantis.screen.WritingScreen;
import com.mystic.atlantis.structures.AtlantisStructures;
import com.mystic.atlantis.util.Reference;
import mod.azure.azurelib.common.internal.common.AzureLib;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Atlantis {
    public static final Logger LOGGER = LogManager.getLogger(Reference.MODID);

    public Atlantis() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AtlantisConfig.CONFIG_SPEC);
        ModParticleTypes.PARTICLES.register(bus);
        onInitialize(bus);
        AtlantisFeature.init(bus);
        AtlantisStructures.DEFERRED_REGISTRY_STRUCTURE.register(bus);
        Providers.init(bus);
    }

    public static ResourceLocation id(String id) {
        return new ResourceLocation("atlantis", id);
    }

    //Don't remove needed for legacy portal block!
    public static ResourceKey<Level> getOverworldKey() {
        ResourceLocation OVERWORLD_ID = LevelStem.OVERWORLD.location();
        return ResourceKey.create(Registries.DIMENSION, OVERWORLD_ID);
    }

    public void onInitialize(IEventBus bus) {
        AzureLib.initialize();
        BlockInit.init(bus);
        ItemInit.init(bus);
        PaintingVariantsInit.init(bus);
        AtlantisModifierInit.init(bus);
        TileEntityInit.init(bus);
        FluidTypesInit.init(bus);
        FluidInit.init(bus);
        AtlantisGroupInit.init(bus);
        AtlantisEntityInit.init(bus);
        AtlantisSoundEventInit.init(bus);
        EffectsInit.init(bus);
        EnchantmentInit.init(bus);
        MenuTypeInit.init(bus);
        RecipesInit.init(bus);
        POITypesInit.init(bus);
        DimensionAtlantis.registerBiomeSources(bus);
    }

    @SubscribeEvent
    public static void onClientSet(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(MenuTypeInit.LINGUISTIC.get(), LinguisticScreen::new);
            MenuScreens.register(MenuTypeInit.WRITING.get(), WritingScreen::new);
        });
    }

    @SubscribeEvent
    public static void onCommonSet(FMLCommonSetupEvent event) {
        ToolInit.init();
        TagsInit.init();

        ((ExtendedBlockEntity) BlockEntityType.SIGN).addAdditionalValidBlock(BlockInit.ATLANTEAN_SIGNS.get(), BlockInit.ATLANTEAN_WALL_SIGN.get());

        SpawnPlacements.register(AtlantisEntityInit.CRAB.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrabEntity::canSpawn);
        SpawnPlacements.register(AtlantisEntityInit.COCONUT_CRAB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CoconutCrabEntity::canSpawn);
        SpawnPlacements.register(AtlantisEntityInit.JELLYFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, JellyfishEntity::canSpawn);
        SpawnPlacements.register(AtlantisEntityInit.STARFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, StarfishEntity::canSpawn);
        SpawnPlacements.register(AtlantisEntityInit.STARFISH_ZOM.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, StarfishZomEntity::canSpawn);
        SpawnPlacements.register(AtlantisEntityInit.SHRIMP.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ShrimpEntity::canSpawn);
        SpawnPlacements.register(AtlantisEntityInit.LEVIATHAN.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LeviathanEntity::canSpawn);
        SpawnPlacements.register(AtlantisEntityInit.SEAHORSE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SeahorseEntity::canSpawn);
    }
}
