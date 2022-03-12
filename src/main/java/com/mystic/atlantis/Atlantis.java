package com.mystic.atlantis;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.mystic.atlantis.blocks.blockentities.registry.TileRegistry;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.configfeature.AtlantisFeature;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.entities.AtlantisEntities;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.FluidInit;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.init.ToolInit;
import com.mystic.atlantis.itemgroup.AtlantisGroup;
import com.mystic.atlantis.setup.ClientSetup;
import com.mystic.atlantis.structures.AtlantisConfiguredStructures;
import com.mystic.atlantis.structures.AtlantisStructures;
import com.mystic.atlantis.util.Reference;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

import java.util.HashMap;
import java.util.Map;

@Mod(Reference.MODID)
public class Atlantis {
    /**
     * @deprecated this is not a thread-safe thing.
     * Imagine that you open your single-player level and close it
     * soon, and join a dedicated server. At that time this value
     * is absolutely wrong, AND stops JVM from removing it from
     * your RAM.
     */
    @Deprecated @SuppressWarnings({"unused"})
    private static final MinecraftServer server = null;
    public static final Logger LOGGER = LogManager.getLogger(Reference.MODID);

    private static final PlacedFeature ORE_AQUAMARINE_OVERWORLD = Feature.ORE
            .configure(new OreFeatureConfig(
                    OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                    BlockInit.AQUAMARINE_ORE.getDefaultState(),
                    9)).withPlacement(
                    CountPlacementModifier.of(20), // number of veins per chunk
                    SquarePlacementModifier.of(), // spreading horizontally
                    HeightRangePlacementModifier.trapezoid(YOffset.getBottom(), YOffset.getTop())); // height
    public static RegistryKey<PlacedFeature> oreAquamarineOverworld;

    @NotNull @Deprecated
    public static MinecraftServer getServer() {
        throw new UnsupportedOperationException();
    }

    public static AtlantisConfig CONFIG;

    public Atlantis() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ClientSetup::onInitializeClient);
        onInitialize(bus);
    }

    public void onInitialize(IEventBus bus) {
        BlockInit.init(bus);
        ItemInit.init(bus);
        TileRegistry.init(bus);
        FluidInit.init(bus);

        AtlantisGroup.init();
        GeckoLib.initialize();
        AtlantisEntities.initialize(bus);

        DimensionAtlantis.init(bus);

        bus.addListener(this::onCommonSet);

        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoad);
        MinecraftForge.EVENT_BUS.addListener(this::addDimensionalSpacing);
    }

    public void onCommonSet(FMLCommonSetupEvent event) {
        AutoConfig.register(AtlantisConfig.class, GsonConfigSerializer::new);
        ToolInit.init();

        CustomPortalBuilder.beginPortal()
                .frameBlock(BlockInit.ATLANTEAN_CORE)
                .lightWithWater()
                .flatPortal()
                .destDimID(new Identifier("atlantis", "atlantis"))
                .tintColor(0, 125, 255)
                .customPortalBlock(BlockInit.ATLANTIS_CLEAR_PORTAL)
                .registerPortal();

        GeckoLibMod.DISABLE_IN_DEV = true;

        event.enqueueWork(() -> {
            DimensionAtlantis.registerBiomeSources();
            AtlantisFeature.ConfiguredFeaturesAtlantis.registerConfiguredFeatures();
            AtlantisStructures.setupAndRegisterStructureFeatures();
            AtlantisConfiguredStructures.registerConfiguredStructures();

            oreAquamarineOverworld = RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("atlantis", "ore_aquamarine_overworld"));
            Registry.register(BuiltinRegistries.PLACED_FEATURE, oreAquamarineOverworld.getValue(), ORE_AQUAMARINE_OVERWORLD);
        });
    }

    public void onBiomeLoad(BiomeLoadingEvent event) {
        //TODO: FIgure out how to get the overworld
        event.getGeneration().feature(GenerationStep.Feature.UNDERGROUND_ORES, ORE_AQUAMARINE_OVERWORLD);
    }


    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld serverLevel) {
            ChunkGenerator chunkGenerator = serverLevel.getChunkManager().getChunkGenerator();

            // Skip superflat worlds to prevent issues with it. Plus, users don't want structures clogging up their superflat worlds.
            if (chunkGenerator instanceof FlatChunkGenerator && serverLevel.getDimension().equals(World.OVERWORLD)) {
                return;
            }

            StructuresConfig worldStructureConfig = chunkGenerator.getStructuresConfig();

            HashMap<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, RegistryKey<Biome>>> STStructureToMultiMap = new HashMap<>();

            for (Map.Entry<RegistryKey<Biome>, Biome> biomeEntry : serverLevel.getRegistryManager().get(Registry.BIOME_KEY).getEntries()) {
                // Skip all ocean, end, nether, and none category biomes.
                // You can do checks for other traits that the biome has.
                if(isAlanteanBiome(biomeEntry.getKey())) {
                    associateBiomeToConfiguredStructure(STStructureToMultiMap, AtlantisConfiguredStructures.CONFIGURED_ATLANTEAN_FOUNTAIN, biomeEntry.getKey());
                    associateBiomeToConfiguredStructure(STStructureToMultiMap, AtlantisConfiguredStructures.CONFIGURED_ATLANTEAN_HOUSE_1, biomeEntry.getKey());
                    associateBiomeToConfiguredStructure(STStructureToMultiMap, AtlantisConfiguredStructures.CONFIGURED_ATLANTEAN_HOUSE_3, biomeEntry.getKey());
                    associateBiomeToConfiguredStructure(STStructureToMultiMap, AtlantisConfiguredStructures.CONFIGURED_ATLANTEAN_TOWER, biomeEntry.getKey());
                    associateBiomeToConfiguredStructure(STStructureToMultiMap, AtlantisConfiguredStructures.CONFIGURED_OYSTER_STRUCTURE, biomeEntry.getKey());
                }
            }

            ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, RegistryKey<Biome>>> tempStructureToMultiMap = ImmutableMap.builder();
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
    private static void associateBiomeToConfiguredStructure(Map<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, RegistryKey<Biome>>> STStructureToMultiMap, ConfiguredStructureFeature<?, ?> configuredStructureFeature, RegistryKey<Biome> biomeRegistryKey) {
        STStructureToMultiMap.putIfAbsent(configuredStructureFeature.feature, HashMultimap.create());
        HashMultimap<ConfiguredStructureFeature<?, ?>, RegistryKey<Biome>> configuredStructureToBiomeMultiMap = STStructureToMultiMap.get(configuredStructureFeature.feature);
        if(configuredStructureToBiomeMultiMap.containsValue(biomeRegistryKey)) {
            LOGGER.error(String.format("""
                    Detected 2 ConfiguredStructureFeatures that share the same base StructureFeature trying to be added to same biome. One will be prevented from spawning.
                    This issue happens with vanilla too and is why a Snowy Village and Plains Village cannot spawn in the same biome because they both use the Village base structure.
                    The two conflicting ConfiguredStructures are: %s, %s
                    The biome that is attempting to be shared: %s
                """,
                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureFeature).toString(),
                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureToBiomeMultiMap.entries().stream().filter(e -> e.getValue() == biomeRegistryKey).findFirst().get().getKey()).toString(),
                    biomeRegistryKey
            ));
        }
        else{
            configuredStructureToBiomeMultiMap.put(configuredStructureFeature, biomeRegistryKey);
        }
    }

    private boolean isAlanteanBiome(RegistryKey<Biome> key) {
        return key.getValue().getNamespace().
                equals(Reference.MODID);
    }

    public static Identifier id(String id) {
        return new Identifier("atlantis", id);
    }

    //Don't remove needed for legacy portal block!
    public static RegistryKey<World> getOverworldKey() {
        Identifier OVERWORLD_ID = DimensionOptions.OVERWORLD.getValue();
        return RegistryKey.of(Registry.WORLD_KEY, OVERWORLD_ID);
    }
}
