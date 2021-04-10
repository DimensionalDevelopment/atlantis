package com.mystic.atlantis.setup;

import io.github.waterpicker.openworlds.renderer.CloudRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

import com.mystic.atlantis.dimension.AltantisSkyRenderer;
import com.mystic.atlantis.dimension.DimensionAtlantis;
import com.mystic.atlantis.init.BlockInit;
import io.github.waterpicker.openworlds.OpenWorlds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

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
            BlockInit.BROWN_PEARL_BLOCK);

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
    }
}
