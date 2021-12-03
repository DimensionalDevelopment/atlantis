package com.mystic.atlantis;

import com.mystic.atlantis.blocks.blockentities.DummyDataStorage;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.configfeature.AtlantisFeature;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.entities.AtlantisEntities;
import com.mystic.atlantis.event.BreathingLibIntegration;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.itemgroup.AtlantisGroup;
import com.mystic.atlantis.structures.AtlantisConfiguredStructures;
import com.mystic.atlantis.structures.AtlantisStructures;
import com.mystic.atlantis.util.Reference;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.portal.PortalIgnitionSource;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

public class Atlantis implements ModInitializer
{

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

    private static final ConfiguredFeature<?, ?> ORE_AQUAMARINE_OVERWORLD = Feature.ORE
            .configure(new OreFeatureConfig(
                    OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                    BlockInit.AQUAMARINE_ORE.getDefaultState(),
                    9)) // vein size
            .triangleRange(YOffset.getBottom(), YOffset.getTop())
            .spreadHorizontally()
            .repeat(20); // number of veins per chunk

    @NotNull @Deprecated
    public static MinecraftServer getServer() {
        throw new UnsupportedOperationException();
    }

    public static AtlantisConfig CONFIG;

    @Override
    public void onInitialize() {
        Atlantis.CONFIG = AutoConfig.register(AtlantisConfig.class,
                PartitioningSerializer.wrap(Toml4jConfigSerializer::new)).getConfig();

        BlockInit.init();
        ItemInit.init();
        CustomPortalBuilder.beginPortal()
                .frameBlock(BlockInit.ATLANTEAN_CORE)
                .lightWithWater()
                .destDimID(new Identifier("atlantis", "atlantis"))
                .tintColor(0, 125, 255)
                .customPortalBlock(BlockInit.ATLANTIS_CLEAR_PORTAL)
                .registerPortal();
        AtlantisGroup.init();
        AtlantisEntities.initialize();
        GeckoLib.initialize();

        GeckoLibMod.DISABLE_IN_DEV = true;
        DimensionAtlantis.registerBiomeSources();
        DimensionAtlantis.setupSurfaceBuilders();
        DimensionAtlantis.init();

        AtlantisFeature.ConfiguredFeaturesAtlantis.registerConfiguredFeatures();
        AtlantisStructures.setupAndRegisterStructureFeatures();
        AtlantisConfiguredStructures.registerConfiguredStructures();

        RegistryKey<ConfiguredFeature<?, ?>> oreAquamarineOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier("atlantis", "ore_aquamarine_overworld"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, oreAquamarineOverworld.getValue(), ORE_AQUAMARINE_OVERWORLD);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, oreAquamarineOverworld);

        BreathingLibIntegration.register();
    }
    public static final BlockEntityType<DummyDataStorage> DUMMY_DATA_STORAGE;
    static {
        DUMMY_DATA_STORAGE = Registry.register(
                Registry.BLOCK_ENTITY_TYPE, "atlantis:dummydatastorage",
                FabricBlockEntityTypeBuilder.create(
                        DummyDataStorage::new, BlockInit.ATLANTIS_PORTAL).build(null));
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
