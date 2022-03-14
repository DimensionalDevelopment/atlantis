package com.mystic.atlantis;

import com.mystic.atlantis.blocks.blockentities.registry.TileRegistry;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.configfeature.AtlantisFeature;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.entities.AtlantisEntities;
import com.mystic.atlantis.entities.CrabEntity;
import com.mystic.atlantis.event.AtlantisSoundEvents;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.FluidInit;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.init.ToolInit;
import com.mystic.atlantis.itemgroup.AtlantisGroup;
import com.mystic.atlantis.particles.ModParticleTypes;
import com.mystic.atlantis.structures.AtlantisConfiguredStructures;
import com.mystic.atlantis.structures.AtlantisStructures;
import com.mystic.atlantis.util.Reference;
import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

@Mod(Reference.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Atlantis {
    public static final Logger LOGGER = LogManager.getLogger(Reference.MODID);
    /**
     * @deprecated this is not a thread-safe thing.
     * Imagine that you open your single-player level and close it
     * soon, and join a dedicated server. At that time this value
     * is absolutely wrong, AND stops JVM from removing it from
     * your RAM.
     */
    @Deprecated
    @SuppressWarnings({"unused"})
    private static final MinecraftServer server = null;
    public static AtlantisConfig CONFIG;

    public Atlantis() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModParticleTypes.PARTICLES.register(bus);
        onInitialize(bus);
        AtlantisFeature.FEATURES.register(bus);
        AtlantisStructures.DEFERRED_REGISTRY_STRUCTURE.register(bus);
    }

    @NotNull
    @Deprecated
    public static MinecraftServer getServer() {
        throw new UnsupportedOperationException();
    }



    public static ResourceLocation id(String id) {
        return new ResourceLocation("atlantis", id);
    }

    //Don't remove needed for legacy portal block!
    public static ResourceKey<Level> getOverworldKey() {
        ResourceLocation OVERWORLD_ID = LevelStem.OVERWORLD.location();
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, OVERWORLD_ID);
    }

    public void onInitialize(IEventBus bus) {

        BlockInit.init(bus);
        ItemInit.init(bus);
        GeckoLib.initialize();
        TileRegistry.init(bus);
        FluidInit.init(bus);

        AtlantisGroup.init();
        AtlantisEntities.initialize(bus);
        AtlantisSoundEvents.SOUNDS.register(bus);
    }

    @SubscribeEvent
    public static void onCommonSet(FMLCommonSetupEvent event) {
        ToolInit.init();

        CustomPortalBuilder.beginPortal()
                .frameBlock(BlockInit.ATLANTEAN_CORE.get())
                .lightWithWater()
                .flatPortal()
                .destDimID(new ResourceLocation("atlantis", "atlantis"))
                .tintColor(0, 125, 255)
                .customPortalBlock((CustomPortalBlock) BlockInit.ATLANTIS_CLEAR_PORTAL.get())
                .registerPortal();

        GeckoLibMod.DISABLE_IN_DEV = true;
        event.enqueueWork(() -> {
            DimensionAtlantis.registerBiomeSources();
            AtlantisFeature.ConfiguredFeaturesAtlantis.registerConfiguredFeatures();
            AtlantisStructures.setupStructures();
            AtlantisConfiguredStructures.registerConfiguredStructures();
        });

        SpawnPlacements.register(AtlantisEntities.CRAB.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrabEntity::canSpawn);
    }

}
