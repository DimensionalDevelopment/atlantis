package com.mystic.atlantis.setup;

import com.google.common.collect.ArrayListMultimap;
import com.mystic.atlantis.AtlantisDimensionalEffect;
import com.mystic.atlantis.blocks.BlockType;
import com.mystic.atlantis.blocks.ancient_cuprum.TrailsGroup;
import com.mystic.atlantis.blocks.blockentities.plants.GeneralPlantBlockEntity;
import com.mystic.atlantis.blocks.blockentities.renderers.*;
import com.mystic.atlantis.dimension.AtlantisDimensions;
import com.mystic.atlantis.entities.*;
import com.mystic.atlantis.entities.models.*;
import com.mystic.atlantis.entities.renders.*;
import com.mystic.atlantis.init.*;
import com.mystic.atlantis.particles.*;
import com.mystic.atlantis.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Map;
import java.util.stream.Stream;

import static com.mystic.atlantis.init.BlockInit.*;

@EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onInitializeClient(FMLClientSetupEvent event) {

        ItemBlockRenderTypes.setRenderLayer(FluidInit.JETSTREAM_WATER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.FLOWING_JETSTREAM_WATER.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(FluidInit.SALTY_SEAWATER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.FLOWING_SALTY_SEAWATER.get(), RenderType.translucent());

        registerPlantRenderer(BlockEntityInit.SEASHROOM, "seashroom");
        registerPlantRenderer(BlockEntityInit.TUBER_UP, "tuber_up");
        registerPlantRenderer(BlockEntityInit.BLUE_LILY, "blue_lily");
        registerPlantRenderer(BlockEntityInit.BURNT_DEEP, "burnt_deep");
        registerPlantRenderer(BlockEntityInit.ANEMONE, "anemone");

        for (DyeColor dyeColor : DyeColor.values()) {
            registerBlockRenderLayers(RenderType.cutoutMipped(),
                    MOSSY_SHELL_BLOCKS.get(dyeColor).get(),
                    CRACKED_MOSSY_SHELL_BLOCKS.get(dyeColor).get());
        }

        for(TrailsGroup group : ANCIENT_CUPRUM.values()) {
            registerBlockRenderLayers(RenderType.cutoutMipped(), group.grate().get(), group.waxed_grate().get());
        }

        for (DyeColor dyeColor : DyeColor.values()) {
            registerBlockRenderLayers(RenderType.cutoutMipped(),
                    MOSSY_SHELL_BLOCKS.get(dyeColor).get(),
                    CRACKED_MOSSY_SHELL_BLOCKS.get(dyeColor).get());
        }

        for (TrailsGroup group : ANCIENT_CUPRUM.values()) {
            registerBlockRenderLayers(RenderType.cutoutMipped(),
                    group.grate().get());
        }

        for (BlockType seaGlass : SEA_GLASS_LIST.values()) {
            registerBlockRenderLayers(RenderType.translucent(),
                    seaGlass.block().get(),
                    seaGlass.button().get(),
                    seaGlass.wall().get(),
                    seaGlass.slab().get(),
                    seaGlass.pressurePlate().get(),
                    seaGlass.stairs().get());
        }

        for (BlockType seaGlass : SEA_GLASS_PATTERNS.values()) {
            registerBlockRenderLayers(RenderType.translucent(),
                    seaGlass.block().get(),
                    seaGlass.button().get(),
                    seaGlass.wall().get(),
                    seaGlass.slab().get(),
                    seaGlass.pressurePlate().get(),
                    seaGlass.stairs().get());
        }

        registerBlockRenderLayers(RenderType.cutout(),
                BLUE_LILY.get(),
                BURNT_DEEP.get(),
                ANEMONE.get(),
                TUBER_UP.get(),
                SEASHROOM.get(),
                FIRE_MELON_FRUIT.get(),
                FIRE_MELON_FRUIT_SPIKED.get(),
                FIRE_MELON_STEM.get(),
                FIRE_MELON_TOP.get(),
                NYMPH_PLANKS.door().get(),
                NYMPH_PLANKS.trapDoor().get(),
                NYMPH_SAPLING.get(),
                PALM_SAPLING.get(),
                SEABLOOM.get(),
                ALGAE.get(),
                AQUATIC_POWER_TORCH.get(),
                WALL_AQUATIC_POWER_TORCH.get(),
                AQUATIC_POWER_DUST_WIRE.get(),
                AQUATIC_POWER_REPEATER.get(),
                AQUATIC_POWER_TRIPWIRE.get(),
                AQUATIC_POWER_TRIPWIRE_HOOK.get(),
                YELLOW_SEABLOOM.get(),
                RED_SEABLOOM.get(),
                AQUATIC_POWER_COMPARATOR.get(),
                ANCIENT_ACACIA.door().get(),
                ANCIENT_BIRCH.door().get(),
                ANCIENT_DARK_OAK.door().get(),
                ANCIENT_JUNGLE.door().get(),
                ANCIENT_OAK.door().get(),
                ANCIENT_SPRUCE.door().get(),
                ANCIENT_BAMBOO.door().get(),
                ANCIENT_MANGROVE.door().get(),
                ANCIENT_CHERRY.door().get(),
                ANCIENT_WARPED.door().get(),
                ANCIENT_CRIMSON.door().get(),
                ANCIENT_ACACIA.trapDoor().get(),
                ANCIENT_BIRCH.trapDoor().get(),
                ANCIENT_DARK_OAK.trapDoor().get(),
                ANCIENT_JUNGLE.trapDoor().get(),
                ANCIENT_OAK.trapDoor().get(),
                ANCIENT_SPRUCE.trapDoor().get(),
                ANCIENT_BAMBOO.trapDoor().get(),
                ANCIENT_MANGROVE.trapDoor().get(),
                ANCIENT_CHERRY.trapDoor().get(),
                ANCIENT_WARPED.trapDoor().get(),
                ANCIENT_CRIMSON.trapDoor().get(),
                PURPLE_SEASHROOM.get(),
                YELLOW_SEASHROOM.get());
        registerBlockRenderLayers(RenderType.translucent(),
                BLACK_PEARL_BLOCK.get(),
                GRAY_PEARL_BLOCK.get(),
                WHITE_PEARL_BLOCK.get(),
                LIGHT_GRAY_PEARL_BLOCK.get(),
                BLUE_PEARL_BLOCK.get(),
                LIGHT_BLUE_PEARL_BLOCK.get(),
                RED_PEARL_BLOCK.get(),
                ORANGE_PEARL_BLOCK.get(),
                PINK_PEARL_BLOCK.get(),
                YELLOW_PEARL_BLOCK.get(),
                GREEN_PEARL_BLOCK.get(),
                LIME_PEARL_BLOCK.get(),
                PURPLE_PEARL_BLOCK.get(),
                MAGENTA_PEARL_BLOCK.get(),
                CYAN_PEARL_BLOCK.get(),
                BROWN_PEARL_BLOCK.get(),
                ATLANTEAN_PORTAL.get());
        registerBlockRenderLayers(RenderType.cutoutMipped(),
                PALM_LEAVES.get(),
                NYMPH_LEAVES.get());
    }

    private static <T extends GeneralPlantBlockEntity<T>> void registerPlantRenderer(DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> registryObject, String name) {
        BlockEntityRenderers.register(registryObject.get(), pContext -> new GeneralPlantRenderer<T>(name));
    }

    @SubscribeEvent
    public static void registerDimensionEffect(RegisterDimensionSpecialEffectsEvent event) {
        event.register(AtlantisDimensions.ATLANTIS_DIMENSION_EFFECT, AtlantisDimensionalEffect.INSTANCE);
    }

    @SubscribeEvent
    public static void entityRegisterEvent(EntityRenderersEvent.RegisterRenderers bus) {
        bus.registerEntityRenderer(AtlantisEntityInit.RUBYCLAW_CRAB.get(), entityRenderDispatcher -> new RubyclawCrabEntityRenderer(entityRenderDispatcher, new RubyclawCrabEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.COCONUT_CRAB.get(), entityRenderDispatcher -> new CoconutCrabEntityRenderer(entityRenderDispatcher, new CoconutCrabEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.AQUAIEL_JELLYFISH.get(), entityRenderDispatcher -> new AquaielJellyfishEntityRenderer(entityRenderDispatcher, new AquaielJellyfishEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.GLITTERTAIL_SHRIMP.get(), entityRenderDispatcher -> new GlittertailShrimpEntityRenderer(entityRenderDispatcher, new GlittertailShrimpEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.SUBMARINE.get(), SubmarineEntityRenderer::new);
        bus.registerEntityRenderer(AtlantisEntityInit.NYMPH_BOAT.get(), NymphBoatRenderer::new);
        bus.registerEntityRenderer(AtlantisEntityInit.PALM_BOAT.get(), PalmBoatRenderer::new);
        bus.registerEntityRenderer(AtlantisEntityInit.LEVIATHAN.get(), entityRenderDispatcher -> new LeviathanEntityRenderer(entityRenderDispatcher, new LeviathanEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.THALASSIAN_SEAHORSE.get(), entityRenderDispatcher -> new ThalassianSeahorseEntityRenderer(entityRenderDispatcher, new ThalassianSeahorseEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.STARFISH.get(), entityRenderDispatcher -> new StarfishEntityRenderer(entityRenderDispatcher, new StarfishEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.ZOMBIE_STARFISH.get(), entityRenderDispatcher -> new ZombieStarfishEntityRenderer(entityRenderDispatcher, new ZombieStarfishEntityModel()));

        bus.registerEntityRenderer(AtlantisEntityInit.SODIUM_BOMB.get(), SodiumBombRenderer::new);
    }

    @SubscribeEvent
    public static void spawnRules(RegisterSpawnPlacementsEvent event) {
        event.register(AtlantisEntityInit.STARFISH.get(),
                SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.OCEAN_FLOOR,
                StarfishEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(AtlantisEntityInit.ZOMBIE_STARFISH.get(),
                SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.OCEAN_FLOOR,
                ZombieStarfishEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(AtlantisEntityInit.THALASSIAN_SEAHORSE.get(),
                SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.OCEAN_FLOOR,
                ThalassianSeahorseEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(AtlantisEntityInit.AQUAIEL_JELLYFISH.get(),
                SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.OCEAN_FLOOR,
                AquaielJellyfishEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(AtlantisEntityInit.RUBYCLAW_CRAB.get(),
                SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.OCEAN_FLOOR,
                RubyclawCrabEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(AtlantisEntityInit.GLITTERTAIL_SHRIMP.get(),
                SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.OCEAN_FLOOR,
                GlittertailShrimpEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(AtlantisEntityInit.COCONUT_CRAB.get(),
                SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                CoconutCrabEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);

        event.register(AtlantisEntityInit.LEVIATHAN.get(),
                SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.OCEAN_FLOOR,
                LeviathanEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    @SubscribeEvent
    public static void init(RegisterParticleProvidersEvent bus) {
        Minecraft.getInstance().particleEngine.register(ModParticleTypes.PUSH_BUBBLESTREAM_UP.get(), PushBubbleStreamParticleUp.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticleTypes.PUSH_BUBBLESTREAM_DOWN.get(), PushBubbleStreamParticleDown.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticleTypes.PUSH_BUBBLESTREAM_NORTH.get(), PushBubbleStreamParticleNorth.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticleTypes.PUSH_BUBBLESTREAM_SOUTH.get(), PushBubbleStreamParticleSouth.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticleTypes.PUSH_BUBBLESTREAM_EAST.get(), PushBubbleStreamParticleEast.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticleTypes.PUSH_BUBBLESTREAM_WEST.get(), PushBubbleStreamParticleWest.Factory::new);
    }

    private static void registerBlockRenderLayers(RenderType layer, Block... blocks) {
        Stream.of(blocks).forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, layer));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
        ArrayListMultimap<DyeColor, Block> mapLinguistic = ArrayListMultimap.create();

        for(Map<DyeColor, DeferredBlock<Block>> colorMapLinguistics : DYED_LINGUISTICS.values()) {
            colorMapLinguistics.forEach((k,v) -> mapLinguistic.put(k, v.get()));
        }

        BlockColors blockColors = event.getBlockColors();

        BlockColor WHITE = (arg, arg2, arg3, i) -> 0xf9fffe;
        BlockColor ORANGE = (arg, arg2, arg3, i) -> 0xf9801d;
        BlockColor MAGENTA = (arg, arg2, arg3, i) -> 0xc74ebd;
        BlockColor LIGHT_BLUE = (arg, arg2, arg3, i) -> 0x3ab3da;
        BlockColor YELLOW = (arg, arg2, arg3, i) -> 0xfed83d;
        BlockColor LIME = (arg, arg2, arg3, i) -> 0x80c71f;
        BlockColor PINK = (arg, arg2, arg3, i) -> 0xf38baa;
        BlockColor GRAY = (arg, arg2, arg3, i) -> 0x474f52;
        BlockColor LIGHT_GRAY = (arg, arg2, arg3, i) -> 0x9d9d97;
        BlockColor CYAN = (arg, arg2, arg3, i) -> 0x169c9c;
        BlockColor PURPLE = (arg, arg2, arg3, i) -> 0x8932b8;
        BlockColor BLUE = (arg, arg2, arg3, i) -> 0x3c44aa;
        BlockColor BROWN = (arg, arg2, arg3, i) -> 0x835432;
        BlockColor GREEN = (arg, arg2, arg3, i) -> 0x5e7c16;
        BlockColor RED = (arg, arg2, arg3, i) -> 0xb02e26;
        BlockColor BLACK = (arg, arg2, arg3, i) -> 0x1d1d21;

        mapLinguistic.get(DyeColor.WHITE).forEach(block -> blockColors.register(WHITE, block));
        mapLinguistic.get(DyeColor.ORANGE).forEach(block -> blockColors.register(ORANGE, block));
        mapLinguistic.get(DyeColor.MAGENTA).forEach(block -> blockColors.register(MAGENTA, block));
        mapLinguistic.get(DyeColor.LIGHT_BLUE).forEach(block -> blockColors.register(LIGHT_BLUE, block));
        mapLinguistic.get(DyeColor.YELLOW).forEach(block -> blockColors.register(YELLOW, block));
        mapLinguistic.get(DyeColor.LIME).forEach(block -> blockColors.register(LIME, block));
        mapLinguistic.get(DyeColor.PINK).forEach(block -> blockColors.register(PINK, block));
        mapLinguistic.get(DyeColor.GRAY).forEach(block -> blockColors.register(GRAY, block));
        mapLinguistic.get(DyeColor.LIGHT_GRAY).forEach(block -> blockColors.register(LIGHT_GRAY, block));
        mapLinguistic.get(DyeColor.CYAN).forEach(block -> blockColors.register(CYAN, block));
        mapLinguistic.get(DyeColor.PURPLE).forEach(block -> blockColors.register(PURPLE, block));
        mapLinguistic.get(DyeColor.BLUE).forEach(block -> blockColors.register(BLUE, block));
        mapLinguistic.get(DyeColor.BROWN).forEach(block -> blockColors.register(BROWN, block));
        mapLinguistic.get(DyeColor.GREEN).forEach(block -> blockColors.register(GREEN, block));
        mapLinguistic.get(DyeColor.RED).forEach(block -> blockColors.register(RED, block));
        mapLinguistic.get(DyeColor.BLACK).forEach(block -> blockColors.register(BLACK, block));

        blockColors.register(WHITE, COLORED_SHELL_BLOCKS.get(DyeColor.WHITE).get());
        blockColors.register(ORANGE, COLORED_SHELL_BLOCKS.get(DyeColor.ORANGE).get());
        blockColors.register(MAGENTA, COLORED_SHELL_BLOCKS.get(DyeColor.MAGENTA).get());
        blockColors.register(LIGHT_BLUE, COLORED_SHELL_BLOCKS.get(DyeColor.LIGHT_BLUE).get());
        blockColors.register(YELLOW, COLORED_SHELL_BLOCKS.get(DyeColor.YELLOW).get());
        blockColors.register(LIME, COLORED_SHELL_BLOCKS.get(DyeColor.LIME).get());
        blockColors.register(PINK, COLORED_SHELL_BLOCKS.get(DyeColor.PINK).get());
        blockColors.register(GRAY, COLORED_SHELL_BLOCKS.get(DyeColor.GRAY).get());
        blockColors.register(LIGHT_GRAY, COLORED_SHELL_BLOCKS.get(DyeColor.LIGHT_GRAY).get());
        blockColors.register(CYAN, COLORED_SHELL_BLOCKS.get(DyeColor.CYAN).get());
        blockColors.register(PURPLE, COLORED_SHELL_BLOCKS.get(DyeColor.PURPLE).get());
        blockColors.register(BLUE, COLORED_SHELL_BLOCKS.get(DyeColor.BLUE).get());
        blockColors.register(BROWN, COLORED_SHELL_BLOCKS.get(DyeColor.BROWN).get());
        blockColors.register(GREEN, COLORED_SHELL_BLOCKS.get(DyeColor.GREEN).get());
        blockColors.register(RED, COLORED_SHELL_BLOCKS.get(DyeColor.RED).get());
        blockColors.register(BLACK, COLORED_SHELL_BLOCKS.get(DyeColor.BLACK).get());


        blockColors.register(WHITE, CRACKED_SHELL_BLOCKS.get(DyeColor.WHITE).get());
        blockColors.register(ORANGE, CRACKED_SHELL_BLOCKS.get(DyeColor.ORANGE).get());
        blockColors.register(MAGENTA, CRACKED_SHELL_BLOCKS.get(DyeColor.MAGENTA).get());
        blockColors.register(LIGHT_BLUE, CRACKED_SHELL_BLOCKS.get(DyeColor.LIGHT_BLUE).get());
        blockColors.register(YELLOW, CRACKED_SHELL_BLOCKS.get(DyeColor.YELLOW).get());
        blockColors.register(LIME, CRACKED_SHELL_BLOCKS.get(DyeColor.LIME).get());
        blockColors.register(PINK, CRACKED_SHELL_BLOCKS.get(DyeColor.PINK).get());
        blockColors.register(GRAY, CRACKED_SHELL_BLOCKS.get(DyeColor.GRAY).get());
        blockColors.register(LIGHT_GRAY, CRACKED_SHELL_BLOCKS.get(DyeColor.LIGHT_GRAY).get());
        blockColors.register(CYAN, CRACKED_SHELL_BLOCKS.get(DyeColor.CYAN).get());
        blockColors.register(PURPLE, CRACKED_SHELL_BLOCKS.get(DyeColor.PURPLE).get());
        blockColors.register(BLUE, CRACKED_SHELL_BLOCKS.get(DyeColor.BLUE).get());
        blockColors.register(BROWN, CRACKED_SHELL_BLOCKS.get(DyeColor.BROWN).get());
        blockColors.register(GREEN, CRACKED_SHELL_BLOCKS.get(DyeColor.GREEN).get());
        blockColors.register(RED, CRACKED_SHELL_BLOCKS.get(DyeColor.RED).get());
        blockColors.register(BLACK, CRACKED_SHELL_BLOCKS.get(DyeColor.BLACK).get());

        blockColors.register(WHITE, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.WHITE).get());
        blockColors.register(ORANGE, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.ORANGE).get());
        blockColors.register(MAGENTA, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.MAGENTA).get());
        blockColors.register(LIGHT_BLUE, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.LIGHT_BLUE).get());
        blockColors.register(YELLOW, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.YELLOW).get());
        blockColors.register(LIME, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.LIME).get());
        blockColors.register(PINK, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.PINK).get());
        blockColors.register(GRAY, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.GRAY).get());
        blockColors.register(LIGHT_GRAY, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.LIGHT_GRAY).get());
        blockColors.register(CYAN, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.CYAN).get());
        blockColors.register(PURPLE, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.PURPLE).get());
        blockColors.register(BLUE, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.BLUE).get());
        blockColors.register(BROWN, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.BROWN).get());
        blockColors.register(GREEN, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.GREEN).get());
        blockColors.register(RED, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.RED).get());
        blockColors.register(BLACK, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.BLACK).get());

        blockColors.register(WHITE, MOSSY_SHELL_BLOCKS.get(DyeColor.WHITE).get());
        blockColors.register(ORANGE, MOSSY_SHELL_BLOCKS.get(DyeColor.ORANGE).get());
        blockColors.register(MAGENTA, MOSSY_SHELL_BLOCKS.get(DyeColor.MAGENTA).get());
        blockColors.register(LIGHT_BLUE, MOSSY_SHELL_BLOCKS.get(DyeColor.LIGHT_BLUE).get());
        blockColors.register(YELLOW, MOSSY_SHELL_BLOCKS.get(DyeColor.YELLOW).get());
        blockColors.register(LIME, MOSSY_SHELL_BLOCKS.get(DyeColor.LIME).get());
        blockColors.register(PINK, MOSSY_SHELL_BLOCKS.get(DyeColor.PINK).get());
        blockColors.register(GRAY, MOSSY_SHELL_BLOCKS.get(DyeColor.GRAY).get());
        blockColors.register(LIGHT_GRAY, MOSSY_SHELL_BLOCKS.get(DyeColor.LIGHT_GRAY).get());
        blockColors.register(CYAN, MOSSY_SHELL_BLOCKS.get(DyeColor.CYAN).get());
        blockColors.register(PURPLE, MOSSY_SHELL_BLOCKS.get(DyeColor.PURPLE).get());
        blockColors.register(BLUE, MOSSY_SHELL_BLOCKS.get(DyeColor.BLUE).get());
        blockColors.register(BROWN, MOSSY_SHELL_BLOCKS.get(DyeColor.BROWN).get());
        blockColors.register(GREEN, MOSSY_SHELL_BLOCKS.get(DyeColor.GREEN).get());
        blockColors.register(RED, MOSSY_SHELL_BLOCKS.get(DyeColor.RED).get());
        blockColors.register(BLACK, MOSSY_SHELL_BLOCKS.get(DyeColor.BLACK).get());


        BlockColor REGULAR = (arg, arg2, arg3, i) -> 0x8caed2; NON_LINGUISTICS.values().stream().map(DeferredBlock::get).forEach(block -> blockColors.register(REGULAR, block));

        BlockColor JetstreamWaterColor = (arg, arg2, arg3, i) -> FastColor.ARGB32.color(255, 169, 255, 208);
        blockColors.register(JetstreamWaterColor, BlockInit.JETSTREAM_WATER.get());

        BlockColor SaltySeaWaterColor = (arg, arg2, arg3, i) -> FastColor.ARGB32.color(255, 10, 96, 208);
        blockColors.register(SaltySeaWaterColor, BlockInit.SALTY_SEAWATER.get());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        ArrayListMultimap<DyeColor, Block> map = ArrayListMultimap.<DyeColor, Block>create();

        for(Map<DyeColor, DeferredBlock<Block>> colorMap : DYED_LINGUISTICS.values()) {
            colorMap.forEach((k,v) -> map.put(k, v.get()));
        }

        ItemColors blockColors = event.getItemColors();

        ItemColor WHITE = (arg, i) -> 0xf9fffe;
        map.get(DyeColor.WHITE).forEach(block -> blockColors.register(WHITE, block));
        ItemColor ORANGE = (arg, i) -> 0xf9801d;
        map.get(DyeColor.ORANGE).forEach(block -> blockColors.register(ORANGE, block));
        ItemColor MAGENTA = (arg, i) -> 0xc74ebd;
        map.get(DyeColor.MAGENTA).forEach(block -> blockColors.register(MAGENTA, block));
        ItemColor LIGHT_BLUE = (arg, i) -> 0x3ab3da;
        map.get(DyeColor.LIGHT_BLUE).forEach(block -> blockColors.register(LIGHT_BLUE, block));
        ItemColor YELLOW = (arg, i) -> 0xfed83d;
        map.get(DyeColor.YELLOW).forEach(block -> blockColors.register(YELLOW, block));
        ItemColor LIME = (arg, i) -> 0x80c71f;
        map.get(DyeColor.LIME).forEach(block -> blockColors.register(LIME, block));
        ItemColor PINK = (arg, i) -> 0xf38baa;
        map.get(DyeColor.PINK).forEach(block -> blockColors.register(PINK, block));
        ItemColor GRAY = (arg, i) -> 0x474f52;
        map.get(DyeColor.GRAY).forEach(block -> blockColors.register(GRAY, block));
        ItemColor LIGHT_GRAY = (arg, i) -> 0x9d9d97;
        map.get(DyeColor.LIGHT_GRAY).forEach(block -> blockColors.register(LIGHT_GRAY, block));
        ItemColor CYAN = (arg, i) -> 0x169c9c;
        map.get(DyeColor.CYAN).forEach(block -> blockColors.register(CYAN, block));
        ItemColor PURPLE = (arg, i) -> 0x8932b8;
        map.get(DyeColor.PURPLE).forEach(block -> blockColors.register(PURPLE, block));
        ItemColor BLUE = (arg, i) -> 0x3c44aa;
        map.get(DyeColor.BLUE).forEach(block -> blockColors.register(BLUE, block));
        ItemColor BROWN = (arg, i) -> 0x835432;
        map.get(DyeColor.BROWN).forEach(block -> blockColors.register(BROWN, block));
        ItemColor GREEN = (arg, i) -> 0x5e7c16;
        map.get(DyeColor.GREEN).forEach(block -> blockColors.register(GREEN, block));
        ItemColor RED = (arg, i) -> 0xb02e26;
        map.get(DyeColor.RED).forEach(block -> blockColors.register(RED, block));
        ItemColor BLACK = (arg, i) -> 0x1d1d21;
        map.get(DyeColor.BLACK).forEach(block -> blockColors.register(BLACK, block));

        blockColors.register(WHITE, COLORED_SHELL_BLOCKS.get(DyeColor.WHITE).get());
        blockColors.register(ORANGE, COLORED_SHELL_BLOCKS.get(DyeColor.ORANGE).get());
        blockColors.register(MAGENTA, COLORED_SHELL_BLOCKS.get(DyeColor.MAGENTA).get());
        blockColors.register(LIGHT_BLUE, COLORED_SHELL_BLOCKS.get(DyeColor.LIGHT_BLUE).get());
        blockColors.register(YELLOW, COLORED_SHELL_BLOCKS.get(DyeColor.YELLOW).get());
        blockColors.register(LIME, COLORED_SHELL_BLOCKS.get(DyeColor.LIME).get());
        blockColors.register(PINK, COLORED_SHELL_BLOCKS.get(DyeColor.PINK).get());
        blockColors.register(GRAY, COLORED_SHELL_BLOCKS.get(DyeColor.GRAY).get());
        blockColors.register(LIGHT_GRAY, COLORED_SHELL_BLOCKS.get(DyeColor.LIGHT_GRAY).get());
        blockColors.register(CYAN, COLORED_SHELL_BLOCKS.get(DyeColor.CYAN).get());
        blockColors.register(PURPLE, COLORED_SHELL_BLOCKS.get(DyeColor.PURPLE).get());
        blockColors.register(BLUE, COLORED_SHELL_BLOCKS.get(DyeColor.BLUE).get());
        blockColors.register(BROWN, COLORED_SHELL_BLOCKS.get(DyeColor.BROWN).get());
        blockColors.register(GREEN, COLORED_SHELL_BLOCKS.get(DyeColor.GREEN).get());
        blockColors.register(RED, COLORED_SHELL_BLOCKS.get(DyeColor.RED).get());
        blockColors.register(BLACK, COLORED_SHELL_BLOCKS.get(DyeColor.BLACK).get());


        blockColors.register(WHITE, CRACKED_SHELL_BLOCKS.get(DyeColor.WHITE).get());
        blockColors.register(ORANGE, CRACKED_SHELL_BLOCKS.get(DyeColor.ORANGE).get());
        blockColors.register(MAGENTA, CRACKED_SHELL_BLOCKS.get(DyeColor.MAGENTA).get());
        blockColors.register(LIGHT_BLUE, CRACKED_SHELL_BLOCKS.get(DyeColor.LIGHT_BLUE).get());
        blockColors.register(YELLOW, CRACKED_SHELL_BLOCKS.get(DyeColor.YELLOW).get());
        blockColors.register(LIME, CRACKED_SHELL_BLOCKS.get(DyeColor.LIME).get());
        blockColors.register(PINK, CRACKED_SHELL_BLOCKS.get(DyeColor.PINK).get());
        blockColors.register(GRAY, CRACKED_SHELL_BLOCKS.get(DyeColor.GRAY).get());
        blockColors.register(LIGHT_GRAY, CRACKED_SHELL_BLOCKS.get(DyeColor.LIGHT_GRAY).get());
        blockColors.register(CYAN, CRACKED_SHELL_BLOCKS.get(DyeColor.CYAN).get());
        blockColors.register(PURPLE, CRACKED_SHELL_BLOCKS.get(DyeColor.PURPLE).get());
        blockColors.register(BLUE, CRACKED_SHELL_BLOCKS.get(DyeColor.BLUE).get());
        blockColors.register(BROWN, CRACKED_SHELL_BLOCKS.get(DyeColor.BROWN).get());
        blockColors.register(GREEN, CRACKED_SHELL_BLOCKS.get(DyeColor.GREEN).get());
        blockColors.register(RED, CRACKED_SHELL_BLOCKS.get(DyeColor.RED).get());
        blockColors.register(BLACK, CRACKED_SHELL_BLOCKS.get(DyeColor.BLACK).get());

        blockColors.register(WHITE, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.WHITE).get());
        blockColors.register(ORANGE, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.ORANGE).get());
        blockColors.register(MAGENTA, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.MAGENTA).get());
        blockColors.register(LIGHT_BLUE, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.LIGHT_BLUE).get());
        blockColors.register(YELLOW, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.YELLOW).get());
        blockColors.register(LIME, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.LIME).get());
        blockColors.register(PINK, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.PINK).get());
        blockColors.register(GRAY, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.GRAY).get());
        blockColors.register(LIGHT_GRAY, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.LIGHT_GRAY).get());
        blockColors.register(CYAN, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.CYAN).get());
        blockColors.register(PURPLE, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.PURPLE).get());
        blockColors.register(BLUE, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.BLUE).get());
        blockColors.register(BROWN, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.BROWN).get());
        blockColors.register(GREEN, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.GREEN).get());
        blockColors.register(RED, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.RED).get());
        blockColors.register(BLACK, CRACKED_MOSSY_SHELL_BLOCKS.get(DyeColor.BLACK).get());

        blockColors.register(WHITE, MOSSY_SHELL_BLOCKS.get(DyeColor.WHITE).get());
        blockColors.register(ORANGE, MOSSY_SHELL_BLOCKS.get(DyeColor.ORANGE).get());
        blockColors.register(MAGENTA, MOSSY_SHELL_BLOCKS.get(DyeColor.MAGENTA).get());
        blockColors.register(LIGHT_BLUE, MOSSY_SHELL_BLOCKS.get(DyeColor.LIGHT_BLUE).get());
        blockColors.register(YELLOW, MOSSY_SHELL_BLOCKS.get(DyeColor.YELLOW).get());
        blockColors.register(LIME, MOSSY_SHELL_BLOCKS.get(DyeColor.LIME).get());
        blockColors.register(PINK, MOSSY_SHELL_BLOCKS.get(DyeColor.PINK).get());
        blockColors.register(GRAY, MOSSY_SHELL_BLOCKS.get(DyeColor.GRAY).get());
        blockColors.register(LIGHT_GRAY, MOSSY_SHELL_BLOCKS.get(DyeColor.LIGHT_GRAY).get());
        blockColors.register(CYAN, MOSSY_SHELL_BLOCKS.get(DyeColor.CYAN).get());
        blockColors.register(PURPLE, MOSSY_SHELL_BLOCKS.get(DyeColor.PURPLE).get());
        blockColors.register(BLUE, MOSSY_SHELL_BLOCKS.get(DyeColor.BLUE).get());
        blockColors.register(BROWN, MOSSY_SHELL_BLOCKS.get(DyeColor.BROWN).get());
        blockColors.register(GREEN, MOSSY_SHELL_BLOCKS.get(DyeColor.GREEN).get());
        blockColors.register(RED, MOSSY_SHELL_BLOCKS.get(DyeColor.RED).get());
        blockColors.register(BLACK, MOSSY_SHELL_BLOCKS.get(DyeColor.BLACK).get());

        ItemColor REGULAR = (arg, i) -> 0x8caed2; NON_LINGUISTICS.values().stream().map(DeferredBlock::get).forEach(block -> blockColors.register(REGULAR, block));
    }
}
