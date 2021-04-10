package com.mystic.atlantis;

import com.mystic.atlantis.blocks.blockentities.DummyDataStorage;
import com.mystic.atlantis.configfeature.AtlantisFeature;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.event.DimensionFoodEvent;
import com.mystic.atlantis.event.PositionEvent;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.mixin.StructuresConfigAccessor;
import com.mystic.atlantis.structures.AtlantisConfiguredStructures;
import com.mystic.atlantis.structures.AtlantisStructures;
import com.mystic.atlantis.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Main implements ModInitializer
{

    private static MinecraftServer server;
    public static final Logger LOGGER = LogManager.getLogger(Reference.MODID);

    private static final ConfiguredFeature<?, ?> ORE_AQUAMARINE_OVERWORLD = Feature.ORE
            .configure(new OreFeatureConfig(
                    OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                    BlockInit.AQUAMARINE_ORE.getDefaultState(),
                    9)) // vein size
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(YOffset.getBottom(), YOffset.getTop())))
            .spreadHorizontally()
            .repeat(20); // number of veins per chunk

    public void addDimensionalSpacing(MinecraftServer server, ServerWorld world) {
        if(world != null){
            if(world.getChunkManager().getChunkGenerator() instanceof FlatChunkGenerator && world.getRegistryKey().equals(World.OVERWORLD)){
                return;
            }

            Map<StructureFeature<?>, StructureConfig> tempMap = new HashMap<>(world.getChunkManager().getChunkGenerator().getStructuresConfig().getStructures());
            tempMap.put(AtlantisStructures.OYSTER_STRUCTURE, StructuresConfig.DEFAULT_STRUCTURES.get(AtlantisStructures.OYSTER_STRUCTURE));
            tempMap.put(AtlantisStructures.ATLANTEAN_FOUNTAIN, StructuresConfig.DEFAULT_STRUCTURES.get(AtlantisStructures.ATLANTEAN_FOUNTAIN));
            ((StructuresConfigAccessor)world.getChunkManager().getChunkGenerator().getStructuresConfig()).setStructures(tempMap);
        }
    }

    @NotNull
    public static MinecraftServer getServer() {
        if (server != null) {
            return server;
        }
        throw new UnsupportedOperationException("Accessed server too early!");
    }

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register((minecraftServer) -> {
            server = minecraftServer;
        });

        UseBlockCallback.EVENT.register(new PositionEvent());
        UseItemCallback.EVENT.register(new DimensionFoodEvent());

        DimensionAtlantis.registerBiomeSources();

        DimensionAtlantis.init();

        AtlantisFeature.ConfiguredFeaturesAtlantis.registerConfiguredFeatures();
        AtlantisStructures.setupAndRegisterStructureFeatures();
        AtlantisConfiguredStructures.registerConfiguredStructures();

        RegistryKey<ConfiguredFeature<?, ?>> oreAquamarineOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier("atlantis", "ore_aquamarine_overworld"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, oreAquamarineOverworld.getValue(), ORE_AQUAMARINE_OVERWORLD);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, oreAquamarineOverworld);

        ServerWorldEvents.LOAD.register(this::addDimensionalSpacing);

        DUMMY_DATA_STORAGE = Registry.register(Registry.BLOCK_ENTITY_TYPE, "atlantis:dummydatastorage", FabricBlockEntityTypeBuilder.create(DummyDataStorage::new, BlockInit.ATLANTIS_PORTAL).build(null));
    }
    public static BlockEntityType<DummyDataStorage> DUMMY_DATA_STORAGE;
}
