package com.mystic.atlantis.setup;

import com.mystic.atlantis.blocks.AncientWoodDoors;
import com.mystic.atlantis.blocks.AncientWoodTrapdoor;
import com.mystic.atlantis.blocks.AtlanteanLeaves;
import com.mystic.atlantis.blocks.blockentities.registry.TileRegistry;
import com.mystic.atlantis.blocks.blockentities.renderers.*;
import com.mystic.atlantis.blocks.plants.Algae;
import com.mystic.atlantis.blocks.plants.PurpleGlowingMushroom;
import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import com.mystic.atlantis.blocks.plants.YellowGlowingMushroom;
import com.mystic.atlantis.blocks.power.*;
import com.mystic.atlantis.dimension.AltantisSkyRenderer;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.entities.AtlantisEntities;
import com.mystic.atlantis.entities.models.CrabEntityModel;
import com.mystic.atlantis.entities.models.Jellyfish2EntityModel;
import com.mystic.atlantis.entities.models.JellyfishEntityModel;
import com.mystic.atlantis.entities.models.ShrimpEntityModel;
import com.mystic.atlantis.entities.renders.*;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.FluidInit;
import com.mystic.atlantis.particles.ModParticleTypes;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Function;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class ClientSetup {
    public static void onInitializeClient(FMLClientSetupEvent event) {

        setupFluidRendering(FluidInit.STILL_JETSTREAM_WATER, FluidInit.FLOWING_JETSTREAM_WATER, new ResourceLocation("minecraft", "water"), 0x52A9FF);
        ItemBlockRenderTypes.setRenderLayer(FluidInit.STILL_JETSTREAM_WATER, RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FluidInit.FLOWING_JETSTREAM_WATER, RenderType.translucent());

        BlockEntityRendererRegistry.register(TileRegistry.UNDERWATER_SHROOM_TILE,
                (BlockEntityRendererProvider.Context rendererDispatcherIn) -> new UnderwaterShroomTileRenderer());

        BlockEntityRendererRegistry.register(TileRegistry.TUBER_UP_TILE,
                (BlockEntityRendererProvider.Context rendererDispatcherIn) -> new TuberUpTileRenderer());

        BlockEntityRendererRegistry.register(TileRegistry.BLUE_LILY_TILE,
                (BlockEntityRendererProvider.Context rendererDispatcherIn) -> new BlueLilyTileRenderer());

        BlockEntityRendererRegistry.register(TileRegistry.BURNT_DEEP_TILE,
                (BlockEntityRendererProvider.Context rendererDispatcherIn) -> new BurntDeepTileRenderer());

        BlockEntityRendererRegistry.register(TileRegistry.ENENMOMY_TILE,
                (BlockEntityRendererProvider.Context rendererDispatcherIn) -> new EnenmomyTileRenderer());

        registerBlockRenderLayers(RenderType.cutout(),
                BlockInit.ATLANTEAN_LEAVES,
                BlockInit.ATLANTEAN_SAPLING,
                BlockInit.UNDERWATER_FLOWER,
                BlockInit.ALGAE,
                BlockInit.ATLANTEAN_POWER_TORCH,
                BlockInit.WALL_ATLANTEAN_POWER_TORCH,
                BlockInit.ATLANTEAN_POWER_DUST_WIRE,
                BlockInit.ATLANTEAN_POWER_REPEATER,
                BlockInit.ATLANTEAN_TRIPWIRE,
                BlockInit.ATLANTEAN_TRIPWIRE_HOOK,
                BlockInit.YELLOW_UNDERWATER_FLOWER,
                BlockInit.RED_UNDERWATER_FLOWER,
                BlockInit.ATLANTEAN_POWER_COMPARATOR,
                BlockInit.ANCIENT_ACACIA_WOOD_MOSS_DOOR,
                BlockInit.ANCIENT_BIRCH_WOOD_MOSS_DOOR,
                BlockInit.ANCIENT_DARK_OAK_WOOD_MOSS_DOOR,
                BlockInit.ANCIENT_JUNGLE_WOOD_MOSS_DOOR,
                BlockInit.ANCIENT_OAK_WOOD_MOSS_DOOR,
                BlockInit.ANCIENT_SPRUCE_WOOD_MOSS_DOOR,
                BlockInit.ANCIENT_ACACIA_WOOD_MOSS_TRAPDOOR,
                BlockInit.ANCIENT_BIRCH_WOOD_MOSS_TRAPDOOR,
                BlockInit.ANCIENT_DARK_OAK_WOOD_MOSS_TRAPDOOR,
                BlockInit.ANCIENT_JUNGLE_WOOD_MOSS_TRAPDOOR,
                BlockInit.ANCIENT_OAK_WOOD_MOSS_TRAPDOOR,
                BlockInit.ANCIENT_SPRUCE_WOOD_MOSS_TRAPDOOR,
                BlockInit.PURPLE_GLOWING_MUSHROOM,
                BlockInit.YELLOW_GLOWING_MUSHROOM);
        registerBlockRenderLayers(RenderType.translucent(),
                BlockInit.BLACK_PEARL_BLOCK,
                BlockInit.GRAY_PEARL_BLOCK,
                BlockInit.WHITE_PEARL_BLOCK,
                BlockInit.LIGHT_GRAY_PEARL_BLOCK,
                BlockInit.BLUE_PEARL_BLOCK,
                BlockInit.LIGHT_BLUE_PEARL_BLOCK,
                BlockInit.RED_PEARL_BLOCK,
                BlockInit.ORANGE_PEARL_BLOCK,
                BlockInit.PINK_PEARL_BLOCK,
                BlockInit.YELLOW_PEARL_BLOCK,
                BlockInit.GREEN_PEARL_BLOCK,
                BlockInit.LIME_PEARL_BLOCK,
                BlockInit.PURPLE_PEARL_BLOCK,
                BlockInit.MAGENTA_PEARL_BLOCK,
                BlockInit.CYAN_PEARL_BLOCK,
                BlockInit.BROWN_PEARL_BLOCK,
                BlockInit.ATLANTIS_CLEAR_PORTAL);

        DimensionSpecialEffects atlantis = new DimensionSpecialEffects(255.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false) {
            @Override
            public Vec3 getBrightnessDependentFogColor(Vec3 vector3d, float v) {
                return vector3d;
            }

            @Override
            public boolean isFoggyAt(int i, int i1) {
                return false;
            }
        };

        DimensionRenderingRegistry.registerDimensionEffects(DimensionAtlantis.ATLANTIS_DIMENSION_EFFECT, atlantis);
        DimensionRenderingRegistry.registerSkyRenderer(DimensionAtlantis.ATLANTIS_WORLD, new AltantisSkyRenderer());
        DimensionRenderingRegistry.registerCloudRenderer(DimensionAtlantis.ATLANTIS_WORLD, (context) -> {});

        EntityRendererRegistry.INSTANCE.register(AtlantisEntities.CRAB, entityRenderDispatcher -> new CrabEntityRenderer(entityRenderDispatcher, new CrabEntityModel()));
        EntityRendererRegistry.INSTANCE.register(AtlantisEntities.JELLYFISH, entityRenderDispatcher -> new JellyfishEntityRenderer(entityRenderDispatcher, new JellyfishEntityModel()));
        EntityRendererRegistry.INSTANCE.register(AtlantisEntities.JELLYFISH2, entityRenderDispatcher -> new Jellyfish2EntityRenderer(entityRenderDispatcher, new Jellyfish2EntityModel()));
        EntityRendererRegistry.INSTANCE.register(AtlantisEntities.SHRIMP, entityRenderDispatcher -> new ShrimpEntityRenderer(entityRenderDispatcher, new ShrimpEntityModel()));
        EntityRendererRegistry.INSTANCE.register(AtlantisEntities.SUBMARINE, SubmarineEntityRenderer::new);
        ModParticleTypes.init();
    }

    private void registerBlockRenderLayers(RenderType layer, Block... blocks) {
        Stream.of(blocks).forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, layer));
    }

    public static void setupFluidRendering(final Fluid still, final Fluid flowing, final ResourceLocation textureFluidId, final int color) {
        final ResourceLocation stillSpriteId = new ResourceLocation(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_still");
        final ResourceLocation flowingSpriteId = new ResourceLocation(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_flow");

        // If they're not already present, add the sprites to the block atlas
        ClientSpriteRegistryCallback.event(TextureAtlas.LOCATION_BLOCKS).register((atlasTexture, registry) -> {
            registry.register(stillSpriteId);
            registry.register(flowingSpriteId);
        });

        final ResourceLocation fluidId = Registry.FLUID.getKey(still);
        final ResourceLocation listenerId = new ResourceLocation(fluidId.getNamespace(), fluidId.getPath() + "_reload_listener");

        final TextureAtlasSprite[] fluidSprites = { null, null };

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return listenerId;
            }

            /**
             * Get the sprites from the block atlas when resources are reloaded
             */
            @Override
            public void reload(ResourceManager resourceManager) {
                final Function<ResourceLocation, TextureAtlasSprite> atlas = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS);
                fluidSprites[0] = atlas.apply(stillSpriteId);
                fluidSprites[1] = atlas.apply(flowingSpriteId);
            }
        });

        // The FluidRenderer gets the sprites and color from a FluidRenderHandler during rendering
        final FluidRenderHandler renderHandler = new FluidRenderHandler()
        {
            @Override
            public TextureAtlasSprite[] getFluidSprites(BlockAndTintGetter view, BlockPos pos, FluidState state) {
                return fluidSprites;
            }

            @Override
            public int getFluidColor(BlockAndTintGetter view, BlockPos pos, FluidState state) {
                return color;
            }
        };

        FluidRenderHandlerRegistry.INSTANCE.register(still, renderHandler);
        FluidRenderHandlerRegistry.INSTANCE.register(flowing, renderHandler);
    }
}
