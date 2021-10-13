package com.mystic.atlantis.setup;

import java.util.function.Function;

import com.mystic.atlantis.dimension.AltantisSkyRenderer;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.entities.AtlantisEntities;
import com.mystic.atlantis.entities.models.CrabEntityModel;
import com.mystic.atlantis.entities.models.Jellyfish2EntityModel;
import com.mystic.atlantis.entities.models.JellyfishEntityModel;
import com.mystic.atlantis.entities.models.ShrimpEntityModel;
import com.mystic.atlantis.entities.renders.CrabEntityRenderer;
import com.mystic.atlantis.entities.renders.Jellyfish2EntityRenderer;
import com.mystic.atlantis.entities.renders.JellyfishEntityRenderer;
import com.mystic.atlantis.entities.renders.ShrimpEntityRenderer;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.init.FluidInit;
import com.mystic.atlantis.particles.ModParticleTypes;
import io.github.waterpicker.openworlds.OpenWorlds;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;

@Environment(EnvType.CLIENT)
public class ClientSetup implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        setupFluidRendering(FluidInit.STILL_JETSTREAM_WATER, FluidInit.FLOWING_JETSTREAM_WATER, new Identifier("minecraft", "water"), 0x52A9FF);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), FluidInit.STILL_JETSTREAM_WATER, FluidInit.FLOWING_JETSTREAM_WATER);


        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
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
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
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

        SkyProperties atlantis = new SkyProperties(255.0F, true, SkyProperties.SkyType.NORMAL, false, false) {
            @Override
            public Vec3d adjustFogColor(Vec3d vector3d, float v) {
                return vector3d;
            }

            @Override
            public boolean useThickFog(int i, int i1) {
                return false;
            }
        };

        OpenWorlds.registerSkyProperty(DimensionAtlantis.ATLANTIS_DIMENSION_TYPE_KEY, atlantis);
        OpenWorlds.registerSkyRenderer(DimensionAtlantis.ATLANTIS_DIMENSION_TYPE_KEY, new AltantisSkyRenderer());
        OpenWorlds.registerCloudRenderer(DimensionAtlantis.ATLANTIS_DIMENSION_TYPE_KEY, (client, matrices, matrix4f, tickDelta, cameraX, cameraY, cameraZ) -> {});

        EntityRendererRegistry.INSTANCE.register(AtlantisEntities.CRAB, entityRenderDispatcher -> new CrabEntityRenderer(entityRenderDispatcher, new CrabEntityModel()));
        EntityRendererRegistry.INSTANCE.register(AtlantisEntities.JELLYFISH, entityRenderDispatcher -> new JellyfishEntityRenderer(entityRenderDispatcher, new JellyfishEntityModel()));
        EntityRendererRegistry.INSTANCE.register(AtlantisEntities.JELLYFISH2, entityRenderDispatcher -> new Jellyfish2EntityRenderer(entityRenderDispatcher, new Jellyfish2EntityModel()));
        EntityRendererRegistry.INSTANCE.register(AtlantisEntities.SHRIMP, entityRenderDispatcher -> new ShrimpEntityRenderer(entityRenderDispatcher, new ShrimpEntityModel()));
        ModParticleTypes.init();
    }

    public static void setupFluidRendering(final Fluid still, final Fluid flowing, final Identifier textureFluidId, final int color) {
        final Identifier stillSpriteId = new Identifier(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_still");
        final Identifier flowingSpriteId = new Identifier(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_flow");

        // If they're not already present, add the sprites to the block atlas
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
            registry.register(stillSpriteId);
            registry.register(flowingSpriteId);
        });

        final Identifier fluidId = Registry.FLUID.getId(still);
        final Identifier listenerId = new Identifier(fluidId.getNamespace(), fluidId.getPath() + "_reload_listener");

        final Sprite[] fluidSprites = { null, null };

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return listenerId;
            }

            /**
             * Get the sprites from the block atlas when resources are reloaded
             */
            @Override
            public void reload(ResourceManager resourceManager) {
                final Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
                fluidSprites[0] = atlas.apply(stillSpriteId);
                fluidSprites[1] = atlas.apply(flowingSpriteId);
            }
        });

        // The FluidRenderer gets the sprites and color from a FluidRenderHandler during rendering
        final FluidRenderHandler renderHandler = new FluidRenderHandler()
        {
            @Override
            public Sprite[] getFluidSprites(BlockRenderView view, BlockPos pos, FluidState state) {
                return fluidSprites;
            }

            @Override
            public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) {
                return color;
            }
        };

        FluidRenderHandlerRegistry.INSTANCE.register(still, renderHandler);
        FluidRenderHandlerRegistry.INSTANCE.register(flowing, renderHandler);
    }
}
