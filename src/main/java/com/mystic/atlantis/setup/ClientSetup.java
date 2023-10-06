package com.mystic.atlantis.setup;

import com.google.common.collect.ArrayListMultimap;
import com.mystic.atlantis.AtlantisDimensionalEffect;
import com.mystic.atlantis.blocks.blockentities.plants.BlueLilyTileEntity;
import com.mystic.atlantis.blocks.blockentities.plants.GeneralPlantBlockEntity;
import com.mystic.atlantis.blocks.blockentities.renderers.*;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.entities.models.*;
import com.mystic.atlantis.entities.renders.*;
import com.mystic.atlantis.init.*;
import com.mystic.atlantis.particles.PushBubbleStreamParticle;
import com.mystic.atlantis.screen.CrystalGeneratorScreen;
import com.mystic.atlantis.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.DimensionSpecialEffectsManager;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.stream.Stream;

import static com.mystic.atlantis.init.BlockInit.DYED_LINGUISTICS;
import static com.mystic.atlantis.init.BlockInit.NON_LINGUISTICS;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Reference.MODID);
    public static final RegistryObject<SimpleParticleType> PUSH_BUBBLESTREAM = PARTICLES.register("push_bubblestream", () -> new SimpleParticleType(false));

    @SubscribeEvent
    public static void onInitializeClient(FMLClientSetupEvent event) {
        MenuScreens.register(MenuTypeInit.CRYSTAL_GENERATOR_MENU.get(), CrystalGeneratorScreen::new);

        ItemBlockRenderTypes.setRenderLayer(FluidInit.JETSTREAM_WATER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.FLOWING_JETSTREAM_WATER.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(FluidInit.SALTY_SEA_WATER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.FLOWING_SALTY_SEA_WATER.get(), RenderType.translucent());

        registerPlantRenderer(TileEntityInit.UNDERWATER_SHROOM_TILE, "underwater_shroom");

        registerPlantRenderer(TileEntityInit.TUBER_UP_TILE, "tuber_up");

        registerPlantRenderer(TileEntityInit.BLUE_LILY_TILE, "blue_lily");

        registerPlantRenderer(TileEntityInit.BURNT_DEEP_TILE, "burnt_deep");

        registerPlantRenderer(TileEntityInit.ENENMOMY_TILE, "enenmomy");

        registerBlockRenderLayers(RenderType.cutout(),
                BlockInit.BLUE_LILY_BLOCK.get(),
                BlockInit.BURNT_DEEP_BLOCK.get(),
                BlockInit.ENENMOMY_BLOCK.get(),
                BlockInit.TUBER_UP_BLOCK.get(),
                BlockInit.UNDERWATER_SHROOM_BLOCK.get(),
                BlockInit.ATLANTEAN_FIRE_MELON_FRUIT.get(),
                BlockInit.ATLANTEAN_FIRE_MELON_FRUIT_SPIKED.get(),
                BlockInit.ATLANTEAN_FIRE_MELON_STEM.get(),
                BlockInit.ATLANTEAN_FIRE_MELON_TOP.get(),
                BlockInit.ATLANTEAN_DOOR.get(),
                BlockInit.ATLANTEAN_TRAPDOOR.get(),
                BlockInit.ATLANTEAN_SAPLING.get(),
                BlockInit.ATLANTEAN_PALM_SAPLING.get(),
                BlockInit.UNDERWATER_FLOWER.get(),
                BlockInit.ALGAE.get(),
                BlockInit.ATLANTEAN_POWER_TORCH.get(),
                BlockInit.WALL_ATLANTEAN_POWER_TORCH.get(),
                BlockInit.ATLANTEAN_POWER_DUST_WIRE.get(),
                BlockInit.ATLANTEAN_POWER_REPEATER.get(),
                BlockInit.ATLANTEAN_TRIPWIRE.get(),
                BlockInit.ATLANTEAN_TRIPWIRE_HOOK.get(),
                BlockInit.YELLOW_UNDERWATER_FLOWER.get(),
                BlockInit.RED_UNDERWATER_FLOWER.get(),
                BlockInit.ATLANTEAN_POWER_COMPARATOR.get(),
                BlockInit.ANCIENT_ACACIA_WOOD_MOSS_DOOR.get(),
                BlockInit.ANCIENT_BIRCH_WOOD_MOSS_DOOR.get(),
                BlockInit.ANCIENT_DARK_OAK_WOOD_MOSS_DOOR.get(),
                BlockInit.ANCIENT_JUNGLE_WOOD_MOSS_DOOR.get(),
                BlockInit.ANCIENT_OAK_WOOD_MOSS_DOOR.get(),
                BlockInit.ANCIENT_SPRUCE_WOOD_MOSS_DOOR.get(),
                BlockInit.ANCIENT_ACACIA_WOOD_MOSS_TRAPDOOR.get(),
                BlockInit.ANCIENT_BIRCH_WOOD_MOSS_TRAPDOOR.get(),
                BlockInit.ANCIENT_DARK_OAK_WOOD_MOSS_TRAPDOOR.get(),
                BlockInit.ANCIENT_JUNGLE_WOOD_MOSS_TRAPDOOR.get(),
                BlockInit.ANCIENT_OAK_WOOD_MOSS_TRAPDOOR.get(),
                BlockInit.ANCIENT_SPRUCE_WOOD_MOSS_TRAPDOOR.get(),
                BlockInit.PURPLE_GLOWING_MUSHROOM.get(),
                BlockInit.YELLOW_GLOWING_MUSHROOM.get());
        registerBlockRenderLayers(RenderType.translucent(),
                BlockInit.PALM_LEAVES.get(),
                BlockInit.ATLANTEAN_LEAVES.get(),
                BlockInit.BLACK_PEARL_BLOCK.get(),
                BlockInit.GRAY_PEARL_BLOCK.get(),
                BlockInit.WHITE_PEARL_BLOCK.get(),
                BlockInit.LIGHT_GRAY_PEARL_BLOCK.get(),
                BlockInit.BLUE_PEARL_BLOCK.get(),
                BlockInit.LIGHT_BLUE_PEARL_BLOCK.get(),
                BlockInit.RED_PEARL_BLOCK.get(),
                BlockInit.ORANGE_PEARL_BLOCK.get(),
                BlockInit.PINK_PEARL_BLOCK.get(),
                BlockInit.YELLOW_PEARL_BLOCK.get(),
                BlockInit.GREEN_PEARL_BLOCK.get(),
                BlockInit.LIME_PEARL_BLOCK.get(),
                BlockInit.PURPLE_PEARL_BLOCK.get(),
                BlockInit.MAGENTA_PEARL_BLOCK.get(),
                BlockInit.CYAN_PEARL_BLOCK.get(),
                BlockInit.BROWN_PEARL_BLOCK.get(),
                BlockInit.ATLANTIS_CLEAR_PORTAL.get());
    }

    private static <T extends GeneralPlantBlockEntity<T>> void registerPlantRenderer(RegistryObject<BlockEntityType<T>> registryObject, String name) {
        BlockEntityRenderers.register(registryObject.get(), pContext -> new GeneralPlantRenderer<T>(name));
    }

    @SubscribeEvent
    public static void registerDimensionEffect(RegisterDimensionSpecialEffectsEvent event) {
        event.register(DimensionAtlantis.ATLANTIS_DIMENSION_EFFECT, AtlantisDimensionalEffect.INSTANCE);
    }

    @SubscribeEvent
    public static void entityRegisterEvent(EntityRenderersEvent.RegisterRenderers bus) {
        bus.registerEntityRenderer(AtlantisEntityInit.CRAB.get(), entityRenderDispatcher -> new CrabEntityRenderer(entityRenderDispatcher, new CrabEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.COCONUT_CRAB.get(), entityRenderDispatcher -> new CoconutCrabEntityRenderer(entityRenderDispatcher, new CoconutCrabEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.JELLYFISH.get(), entityRenderDispatcher -> new JellyfishEntityRenderer(entityRenderDispatcher, new JellyfishEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.SHRIMP.get(), entityRenderDispatcher -> new ShrimpEntityRenderer(entityRenderDispatcher, new ShrimpEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.SUBMARINE.get(), SubmarineEntityRenderer::new);
        bus.registerEntityRenderer(AtlantisEntityInit.ATLANTEAN_BOAT.get(), AtlanteanBoatRenderer::new);
        bus.registerEntityRenderer(AtlantisEntityInit.LEVIATHAN.get(), entityRenderDispatcher -> new LeviathanEntityRenderer(entityRenderDispatcher, new LeviathanEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.SEAHORSE.get(), entityRenderDispatcher -> new SeahorseEntityRenderer(entityRenderDispatcher, new SeahorseEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.STARFISH.get(), entityRenderDispatcher -> new StarfishEntityRenderer(entityRenderDispatcher, new StarfishEntityModel()));
        bus.registerEntityRenderer(AtlantisEntityInit.STARFISH_ZOM.get(), entityRenderDispatcher -> new StarfishZomEntityRenderer(entityRenderDispatcher, new StarfishZomEntityModel()));

        bus.registerEntityRenderer(AtlantisEntityInit.BOMB.get(), SodiumBombRenderer::new);
    }
    @SubscribeEvent
    public static void init(RegisterParticleProvidersEvent bus) {
        Minecraft.getInstance().particleEngine.register(PUSH_BUBBLESTREAM.get(), PushBubbleStreamParticle.Factory::new);
    }

    private static void registerBlockRenderLayers(RenderType layer, Block... blocks) {
        Stream.of(blocks).forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, layer));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
        ArrayListMultimap<DyeColor, Block> map = ArrayListMultimap.create();

        for(Map<DyeColor, RegistryObject<Block>> colorMap : DYED_LINGUISTICS.values()) {
            colorMap.forEach((k,v) -> map.put(k, v.get()));
        }

        BlockColors blockColors = event.getBlockColors();

        BlockColor WHITE = (arg, arg2, arg3, i) -> 0xf9fffe;
        map.get(DyeColor.WHITE).forEach(block -> blockColors.register(WHITE, block));
        BlockColor ORANGE = (arg, arg2, arg3, i) -> 0xf9801d;
        map.get(DyeColor.ORANGE).forEach(block -> blockColors.register(ORANGE, block));
        BlockColor MAGENTA = (arg, arg2, arg3, i) -> 0xc74ebd;
        map.get(DyeColor.MAGENTA).forEach(block -> blockColors.register(MAGENTA, block));
        BlockColor LIGHT_BLUE = (arg, arg2, arg3, i) -> 0x3ab3da;
        map.get(DyeColor.LIGHT_BLUE).forEach(block -> blockColors.register(LIGHT_BLUE, block));
        BlockColor YELLOW = (arg, arg2, arg3, i) -> 0xfed83d;
        map.get(DyeColor.YELLOW).forEach(block -> blockColors.register(YELLOW, block));
        BlockColor LIME = (arg, arg2, arg3, i) -> 0x80c71f;
        map.get(DyeColor.LIME).forEach(block -> blockColors.register(LIME, block));
        BlockColor PINK = (arg, arg2, arg3, i) -> 0xf38baa;
        map.get(DyeColor.PINK).forEach(block -> blockColors.register(PINK, block));
        BlockColor GRAY = (arg, arg2, arg3, i) -> 0x474f52;
        map.get(DyeColor.GRAY).forEach(block -> blockColors.register(GRAY, block));
        BlockColor LIGHT_GRAY = (arg, arg2, arg3, i) -> 0x9d9d97;
        map.get(DyeColor.LIGHT_GRAY).forEach(block -> blockColors.register(LIGHT_GRAY, block));
        BlockColor CYAN = (arg, arg2, arg3, i) -> 0x169c9c;
        map.get(DyeColor.CYAN).forEach(block -> blockColors.register(CYAN, block));
        BlockColor PURPLE = (arg, arg2, arg3, i) -> 0x8932b8;
        map.get(DyeColor.PURPLE).forEach(block -> blockColors.register(PURPLE, block));
        BlockColor BLUE = (arg, arg2, arg3, i) -> 0x3c44aa;
        map.get(DyeColor.BLUE).forEach(block -> blockColors.register(BLUE, block));
        BlockColor BROWN = (arg, arg2, arg3, i) -> 0x835432;
        map.get(DyeColor.BROWN).forEach(block -> blockColors.register(BROWN, block));
        BlockColor GREEN = (arg, arg2, arg3, i) -> 0x5e7c16;
        map.get(DyeColor.GREEN).forEach(block -> blockColors.register(GREEN, block));
        BlockColor RED = (arg, arg2, arg3, i) -> 0xb02e26;
        map.get(DyeColor.RED).forEach(block -> blockColors.register(RED, block));
        BlockColor BLACK = (arg, arg2, arg3, i) -> 0x1d1d21;
        map.get(DyeColor.BLACK).forEach(block -> blockColors.register(BLACK, block));

        BlockColor REGULAR = (arg, arg2, arg3, i) -> 0x8caed2; NON_LINGUISTICS.values().stream().map(RegistryObject::get).forEach(block -> blockColors.register(REGULAR, block));

        BlockColor SaltySeaWaterColor = (arg, arg2, arg3, i) -> 0x100a60D0;
        blockColors.register(SaltySeaWaterColor, BlockInit.SALTY_SEA_WATER_BLOCK.get());

        BlockColor JetstreamWaterColor = (arg, arg2, arg3, i) -> 0x52A9FFD0;
        blockColors.register(JetstreamWaterColor, BlockInit.JETSTREAM_WATER_BLOCK.get());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
        ArrayListMultimap<DyeColor, Block> map = ArrayListMultimap.<DyeColor, Block>create();

        for(Map<DyeColor, RegistryObject<Block>> colorMap : DYED_LINGUISTICS.values()) {
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

        ItemColor REGULAR = (arg, i) -> 0x8caed2; NON_LINGUISTICS.values().stream().map(RegistryObject::get).forEach(block -> blockColors.register(REGULAR, block));
    }
}
