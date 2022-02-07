package com.mystic.atlantis;

import com.mystic.atlantis.blocks.blockentities.DummyDataStorage;
import com.mystic.atlantis.blocks.blockentities.plants.BlueLilyTileEntity;
import com.mystic.atlantis.blocks.blockentities.plants.BurntDeepTileEntity;
import com.mystic.atlantis.blocks.blockentities.plants.TuberUpTileEntity;
import com.mystic.atlantis.blocks.blockentities.plants.UnderwaterShroomTileEntity;
import com.mystic.atlantis.blocks.blockentities.registry.TileRegistry;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.configfeature.AtlantisFeature;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.entities.AtlantisEntities;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.itemgroup.AtlantisGroup;
import com.mystic.atlantis.structures.AtlantisConfiguredStructures;
import com.mystic.atlantis.structures.AtlantisStructures;
import com.mystic.atlantis.util.Reference;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
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

    private static final PlacedFeature ORE_AQUAMARINE_OVERWORLD = Feature.ORE
            .configure(new OreFeatureConfig(
                    OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                    BlockInit.AQUAMARINE_ORE.getDefaultState(),
                    9)).withPlacement(
                    CountPlacementModifier.of(20), // number of veins per chunk
                    SquarePlacementModifier.of(), // spreading horizontally
                    HeightRangePlacementModifier.trapezoid(YOffset.getBottom(), YOffset.getTop())); // height

    @NotNull @Deprecated
    public static MinecraftServer getServer() {
        throw new UnsupportedOperationException();
    }

    public static AtlantisConfig CONFIG;
    public static BlockEntityType<UnderwaterShroomTileEntity> UNDERWATER_SHROOM_TILE;
    public static BlockEntityType<TuberUpTileEntity> TUBER_UP_TILE;
    public static BlockEntityType<BlueLilyTileEntity> BLUE_LILY_TILE;
    public static BlockEntityType<BurntDeepTileEntity> BURNT_DEEP_TILE;
    public static ItemInit ITEMS;

    @Override
    public void onInitialize() {
        AutoConfig.register(AtlantisConfig.class, GsonConfigSerializer::new);

        BlockInit.init();
        Registry.register(Registry.BLOCK, new Identifier(Reference.MODID, "underwater_shroom"), BlockInit.UNDERWATER_SHROOM_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Reference.MODID, "tuber_up"), BlockInit.TUBER_UP_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Reference.MODID, "blue_lily"), BlockInit.BLUE_LILY_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Reference.MODID, "burnt_deep"), BlockInit.BURNT_DEEP_BLOCK);
        ItemInit.init();
        CustomPortalBuilder.beginPortal()
                .frameBlock(BlockInit.ATLANTEAN_CORE)
                .lightWithWater()
                .destDimID(new Identifier("atlantis", "atlantis"))
                .tintColor(0, 125, 255)
                .customPortalBlock(BlockInit.ATLANTIS_CLEAR_PORTAL)
                .registerPortal();
        AtlantisGroup.init();
        ITEMS = new ItemInit();
        GeckoLib.initialize();
        AtlantisEntities.initialize();
        UNDERWATER_SHROOM_TILE = TileRegistry.UNDERWATER_SHROOM_TILE;
        TUBER_UP_TILE = TileRegistry.TUBER_UP_TILE;
        BLUE_LILY_TILE = TileRegistry.BLUE_LILY_TILE;
        BURNT_DEEP_TILE = TileRegistry.BURNT_DEEP_TILE;

        GeckoLibMod.DISABLE_IN_DEV = true;
        DimensionAtlantis.registerBiomeSources();
        DimensionAtlantis.init();

        AtlantisFeature.ConfiguredFeaturesAtlantis.registerConfiguredFeatures();

        AtlantisStructures.setupAndRegisterStructureFeatures();
        AtlantisConfiguredStructures.registerConfiguredStructures();

        BiomeModifications.addStructure(
                (biomeSelectionContext) -> biomeSelectionContext
                        .getBiomeKey().getValue().getNamespace().
                        equals(Reference.MODID),
                RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
                        BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(
                                AtlantisConfiguredStructures.CONFIGURED_ATLANTEAN_FOUNTAIN
                        )));
        BiomeModifications.addStructure(
                (biomeSelectionContext) -> biomeSelectionContext.getBiomeKey().getValue().getNamespace().equals(Reference.MODID),
                RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
                        BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(
                                AtlantisConfiguredStructures.CONFIGURED_ATLANTEAN_HOUSE_1
                        )));
        BiomeModifications.addStructure(
                (biomeSelectionContext) -> biomeSelectionContext.getBiomeKey().getValue().getNamespace().equals(Reference.MODID),
                RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
                        BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(
                                AtlantisConfiguredStructures.CONFIGURED_ATLANTEAN_HOUSE_3
                        )));
        BiomeModifications.addStructure(
                (biomeSelectionContext) -> biomeSelectionContext.getBiomeKey().getValue().getNamespace().equals(Reference.MODID),
                RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
                        BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(
                                AtlantisConfiguredStructures.CONFIGURED_ATLANTEAN_TOWER
                        )));
        BiomeModifications.addStructure(
                (biomeSelectionContext) -> biomeSelectionContext.getBiomeKey().getValue().getNamespace().equals(Reference.MODID),
                RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY,
                        BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(
                                AtlantisConfiguredStructures.CONFIGURED_OYSTER_STRUCTURE
                        )));

        RegistryKey<PlacedFeature> oreAquamarineOverworld = RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier("atlantis", "ore_aquamarine_overworld"));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, oreAquamarineOverworld.getValue(), ORE_AQUAMARINE_OVERWORLD);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, oreAquamarineOverworld);

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
