package com.mystic.atlantis;

import java.util.HashMap;
import java.util.Map;

import com.mystic.atlantis.blocks.blockentities.DummyDataStorage;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import com.mystic.atlantis.configfeature.AtlantisFeature;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.event.DimensionFoodEvent;
import com.mystic.atlantis.event.PositionEvent;
import com.mystic.atlantis.mixin.StructuresConfigAccessor;
import com.mystic.atlantis.structures.AtlantisConfiguredStructures;
import com.mystic.atlantis.structures.AtlantisStructures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

public class Main implements ModInitializer
{

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

    @Override
    public void onInitialize() {
        UseBlockCallback.EVENT.register(new PositionEvent());
        UseItemCallback.EVENT.register(new DimensionFoodEvent());

        DimensionAtlantis.registerBiomeSources();

        AtlantisFeature.ConfiguredFeaturesAtlantis.registerConfiguredFeatures();
        AtlantisStructures.setupAndRegisterStructureFeatures();
        AtlantisConfiguredStructures.registerConfiguredStructures();

        ServerWorldEvents.LOAD.register(this::addDimensionalSpacing);

        DUMMY_DATA_STORAGE = Registry.register(Registry.BLOCK_ENTITY_TYPE, "atlantis:dummydatastorage", BlockEntityType.Builder.create(DummyDataStorage::new, BlockInit.ATLANTIS_PORTAL).build(null));
    }
    public static BlockEntityType<DummyDataStorage> DUMMY_DATA_STORAGE;
}
