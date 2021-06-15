package com.mystic.atlantis.setup;

import com.mystic.atlantis.dimension.AltantisSkyRenderer;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.entities.AtlantisEntities;
import com.mystic.atlantis.entities.models.CrabEntityModel;
import com.mystic.atlantis.entities.renders.CrabEntityRenderer;
import com.mystic.atlantis.init.BlockInit;
import io.github.waterpicker.openworlds.OpenWorlds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class ClientSetup implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), BlockInit.UNDERWATER_FLOWER, BlockInit.ALGAE);
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
    }
}
