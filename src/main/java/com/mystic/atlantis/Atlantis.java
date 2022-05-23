package com.mystic.atlantis;

import com.mystic.atlantis.blocks.ExtendedBlockEntity;
import com.mystic.atlantis.blocks.LinguisticGlyph;
import com.mystic.atlantis.blocks.blockentities.registry.TileRegistry;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.configfeature.AtlantisFeature;
import com.mystic.atlantis.data.AtlantisModifer;
import com.mystic.atlantis.datagen.Providers;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.entities.AtlantisEntities;
import com.mystic.atlantis.entities.CrabEntity;
import com.mystic.atlantis.event.ACommonFEvents;
import com.mystic.atlantis.event.AtlantisSoundEvents;
import com.mystic.atlantis.init.*;
import com.mystic.atlantis.inventory.MenuTypeInit;
import com.mystic.atlantis.itemgroup.AtlantisGroup;
import com.mystic.atlantis.items.item.LinguisticGlyphScrollItem;
import com.mystic.atlantis.particles.ModParticleTypes;
import com.mystic.atlantis.screen.LinguisticScreen;
import com.mystic.atlantis.screen.WritingScreen;
import com.mystic.atlantis.structures.AtlantisConfiguredStructures;
import com.mystic.atlantis.structures.AtlantisStructures;
import com.mystic.atlantis.util.ItemStackUtil;
import com.mystic.atlantis.util.Reference;
import me.shedaniel.autoconfig.AutoConfig;
import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.ConfigGuiHandler.ConfigGuiFactory;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

import java.util.Locale;

@Mod(Reference.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Atlantis {
    public static final Logger LOGGER = LogManager.getLogger(Reference.MODID);

    public Atlantis() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AtlantisConfig.CONFIG_SPEC);
        ModParticleTypes.PARTICLES.register(bus);
        onInitialize(bus);
        AtlantisFeature.FEATURES.register(bus);
        AtlantisStructures.DEFERRED_REGISTRY_STRUCTURE.register(bus);
        BlockInit.registerColor(bus);
        Providers.init(bus);
    }

    @SubscribeEvent
    public static void registerModifierSerializers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().register(new AtlantisModifer.Serializer().setRegistryName(new ResourceLocation("atlantis:seeds_drop")));
    }

    @SubscribeEvent
    public void loadCompleted(FMLLoadCompleteEvent event) {
        ModContainer createContainer = ModList.get()
                .getModContainerById(Reference.MODID)
                .orElseThrow(() -> new IllegalStateException("Create Mod Container missing after loadCompleted"));
        createContainer.registerExtensionPoint(ConfigGuiFactory.class, () -> new ConfigGuiFactory((mc, previousScreen) -> AutoConfig.getConfigScreen(AtlantisConfig.class, previousScreen).get()));
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
        EffectsInit.init(bus);
        MenuTypeInit.init(bus);
        RecipesInit.init(bus);
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

        ((ExtendedBlockEntity) BlockEntityType.SIGN).addAdditionalValidBlock(BlockInit.ATLANTEAN_SIGNS.get(), BlockInit.ATLANTEAN_WALL_SIGN.get());

        SpawnPlacements.register(AtlantisEntities.CRAB.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrabEntity::canSpawn);
    }

}
