package com.mystic.atlantis;

import com.mystic.atlantis.configfeature.AtlantisFeature;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.event.*;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.setup.ClientSetup;
import com.mystic.atlantis.setup.ModSetup;
import com.mystic.atlantis.structures.AtlantisConfiguredStructures;
import com.mystic.atlantis.structures.AtlantisStructures;
import com.mystic.atlantis.util.Reference;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.HashMap;
import java.util.Map;

@Mod(Reference.MODID)
public class Main
{
    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new OreGenerationEvent());
        MinecraftForge.EVENT_BUS.register(new PositionEvent());
        MinecraftForge.EVENT_BUS.register(new ElderPortalEvent());
        MinecraftForge.EVENT_BUS.register(new DimensionEffectTimed());
        MinecraftForge.EVENT_BUS.register(new DimensionFoodEvent());

        AtlantisFeature.FEATURES.register(bus);
        AtlantisStructures.DEFERRED_REGISTRY_STRUCTURE.register(bus);
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        DeferredRegister<?>[] registers = {
                BlockInit.BLOCKS,
                ItemInit.ITEMS,
        };

        for (DeferredRegister<?> register : registers) {
            register.register(bus);
        }
    }


    private void setup(final FMLCommonSetupEvent event)
    {
        DimensionAtlantis.registerBiomeSources();
        event.enqueueWork(() -> {
            AtlantisStructures.setupStructures();
            AtlantisConfiguredStructures.registerConfiguredStructures();
        });
    }

    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();
            if(serverWorld.getChunkManager().getChunkGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.getRegistryKey().equals(World.OVERWORLD)){
                return;
            }

            Map<StructureFeature<?>, StructureConfig> tempMap = new HashMap<>(serverWorld.getChunkManager().chunkGenerator.getStructuresConfig().getStructures());
            tempMap.put(AtlantisStructures.OYSTER_STRUCTURE.get(), StructuresConfig.DEFAULT_STRUCTURES.get(AtlantisStructures.OYSTER_STRUCTURE.get()));
            serverWorld.getChunkManager().chunkGenerator.getStructuresConfig().structures = tempMap;

            Map<StructureFeature<?>, StructureConfig> tempMap2 = new HashMap<>(serverWorld.getChunkManager().chunkGenerator.getStructuresConfig().getStructures());
            tempMap2.put(AtlantisStructures.ATLANTEAN_FOUNTAIN.get(), StructuresConfig.DEFAULT_STRUCTURES.get(AtlantisStructures.ATLANTEAN_FOUNTAIN.get()));
            serverWorld.getChunkManager().chunkGenerator.getStructuresConfig().structures = tempMap2;
        }
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {

    }

    private void processIMC(final InterModProcessEvent event)
    {

    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {

    }

    public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T entry, String registryKey) {
        entry.setRegistryName(new Identifier(Reference.MODID, registryKey));
        registry.register(entry);
        return entry;
    }
}
