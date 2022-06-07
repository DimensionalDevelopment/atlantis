package com.mystic.atlantis.setup;

import com.google.common.collect.ArrayListMultimap;
import com.mystic.atlantis.blocks.blockentities.registry.TileRegistry;
import com.mystic.atlantis.blocks.blockentities.renderers.*;
import com.mystic.atlantis.dimension.AltantisSkyRenderer;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.entities.AtlantisEntities;
import com.mystic.atlantis.entities.models.*;
import com.mystic.atlantis.entities.renders.*;
import com.mystic.atlantis.fluids.SaltySeaWaterFluid;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.FluidInit;
import com.mystic.atlantis.particles.PushBubbleStreamParticle;
import com.mystic.atlantis.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ICloudRenderHandler;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Stream;

import static com.mystic.atlantis.init.BlockInit.dyedLinguistic;
import static com.mystic.atlantis.init.BlockInit.nonLinguistic;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Reference.MODID);
    public static final RegistryObject<SimpleParticleType> PUSH_BUBBLESTREAM = PARTICLES.register("push_bubblestream", () -> new SimpleParticleType(false));

    @SubscribeEvent
    public static void onInitializeClient(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(FluidInit.STILL_JETSTREAM_WATER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.FLOWING_JETSTREAM_WATER.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(FluidInit.STILL_SALTY_SEA_WATER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.FLOWING_SALTY_SEA_WATER.get(), RenderType.translucent());

        BlockEntityRenderers.register(TileRegistry.UNDERWATER_SHROOM_TILE.get(),
                UnderwaterShroomTileRenderer::new);

        BlockEntityRenderers.register(TileRegistry.TUBER_UP_TILE.get(),
                TuberUpTileRenderer::new);

        BlockEntityRenderers.register(TileRegistry.BLUE_LILY_TILE.get(),
                BlueLilyTileRenderer::new);

        BlockEntityRenderers.register(TileRegistry.BURNT_DEEP_TILE.get(),
                BurntDeepTileRenderer::new);

        BlockEntityRenderers.register(TileRegistry.ENENMOMY_TILE.get(),
                EnenmomyTileRenderer::new);

        registerBlockRenderLayers(RenderType.cutout(),
                BlockInit.ATLANTEAN_FIRE_MELON_FRUIT.get(),
                BlockInit.ATLANTEAN_FIRE_MELON_FRUIT_SPIKED.get(),
                BlockInit.ATLANTEAN_FIRE_MELON_STEM.get(),
                BlockInit.ATLANTEAN_FIRE_MELON_TOP.get(),
                BlockInit.ATLANTEAN_DOOR.get(),
                BlockInit.ATLANTEAN_TRAPDOOR.get(),
                BlockInit.ATLANTEAN_LEAVES.get(),
                BlockInit.ATLANTEAN_SAPLING.get(),
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

        DimensionSpecialEffects atlantis = new DimensionSpecialEffects(255.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false) {
            @Override
            public void setSkyRenderHandler(ISkyRenderHandler skyRenderHandler) {
                super.setSkyRenderHandler(new AltantisSkyRenderer());
            }

            @Override
            public void setCloudRenderHandler(ICloudRenderHandler cloudRenderHandler) {
                super.setCloudRenderHandler(null);
            }

            @Override
            public @NotNull Vec3 getBrightnessDependentFogColor(Vec3 vector3d, float v) {
                return vector3d;
            }

            @Override
            public boolean isFoggyAt(int i, int i1) {
                return false;
            }
        };

        DimensionSpecialEffects.EFFECTS.put(DimensionAtlantis.ATLANTIS_DIMENSION_EFFECT, atlantis);
    }

    @SubscribeEvent
    public static void entityRegisterEvent(EntityRenderersEvent.RegisterRenderers bus) {
        bus.registerEntityRenderer(AtlantisEntities.CRAB.get(), entityRenderDispatcher -> new CrabEntityRenderer(entityRenderDispatcher, new CrabEntityModel()));
        bus.registerEntityRenderer(AtlantisEntities.JELLYFISH.get(), entityRenderDispatcher -> new JellyfishEntityRenderer(entityRenderDispatcher, new JellyfishEntityModel()));
        bus.registerEntityRenderer(AtlantisEntities.JELLYFISH2.get(), entityRenderDispatcher -> new Jellyfish2EntityRenderer(entityRenderDispatcher, new Jellyfish2EntityModel()));
        bus.registerEntityRenderer(AtlantisEntities.SHRIMP.get(), entityRenderDispatcher -> new ShrimpEntityRenderer(entityRenderDispatcher, new ShrimpEntityModel()));
        bus.registerEntityRenderer(AtlantisEntities.SUBMARINE.get(), SubmarineEntityRenderer::new);
        bus.registerEntityRenderer(AtlantisEntities.ATLANTEAN_BOAT.get(), AtlanteanBoatRenderer::new);
        bus.registerEntityRenderer(AtlantisEntities.LEVIATHAN.get(), entityRenderDispatcher -> new LeviathanEntityRenderer(entityRenderDispatcher, new LeviathanEntityModel()));
    }
    @SubscribeEvent
    public static void init(ParticleFactoryRegisterEvent bus) {
        Minecraft.getInstance().particleEngine.register(PUSH_BUBBLESTREAM.get(), PushBubbleStreamParticle.Factory::new);
    }

    private static void registerBlockRenderLayers(RenderType layer, Block... blocks) {
        Stream.of(blocks).forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, layer));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerBlockColor(ColorHandlerEvent.Block event) {
        ArrayListMultimap<DyeColor, Block> map = ArrayListMultimap.<DyeColor, Block>create();

        for(Map<DyeColor, RegistryObject<Block>> colorMap : dyedLinguistic.values()) {
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

        BlockColor REGULAR = (arg, arg2, arg3, i) -> 0x8caed2; nonLinguistic.values().stream().map(RegistryObject::get).forEach(block -> blockColors.register(REGULAR, block));

        BlockColor SaltySeaWaterColor = (arg, arg2, arg3, i) -> 0x100a60;
        blockColors.register(SaltySeaWaterColor, FluidInit.SALTY_SEA_WATER.get());

        BlockColor JetstreamWaterColor = (arg, arg2, arg3, i) -> 0x52A9FF;
        blockColors.register(JetstreamWaterColor, FluidInit.JETSTREAM_WATER.get());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerItemColor(ColorHandlerEvent.Item event) {
        ArrayListMultimap<DyeColor, Block> map = ArrayListMultimap.<DyeColor, Block>create();

        for(Map<DyeColor, RegistryObject<Block>> colorMap : dyedLinguistic.values()) {
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

        ItemColor REGULAR = (arg, i) -> 0x8caed2; nonLinguistic.values().stream().map(RegistryObject::get).forEach(block -> blockColors.register(REGULAR, block));
    }
}
