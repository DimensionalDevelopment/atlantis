package com.mystic.atlantis.setup;

import com.mystic.atlantis.dimension.AltantisSkyRenderer;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.util.Reference;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup
{

    public static void init(final FMLClientSetupEvent event) {
        RenderLayers.setRenderLayer(BlockInit.UNDERWATER_FLOWER.get(), RenderLayer.getCutout());
        RenderLayers.setRenderLayer(BlockInit.ALGAE.get(), RenderLayer.getCutout());
        RenderLayers.setRenderLayer(BlockInit.BLACK_PEARL_BLOCK.get(), RenderLayer.getTranslucent());
        RenderLayers.setRenderLayer(BlockInit.GRAY_PEARL_BLOCK.get(), RenderLayer.getTranslucent());
        RenderLayers.setRenderLayer(BlockInit.WHITE_PEARL_BLOCK.get(), RenderLayer.getTranslucent());
        RenderLayers.setRenderLayer(BlockInit.LIGHT_GRAY_PEARL_BLOCK.get(), RenderLayer.getTranslucent());
        RenderLayers.setRenderLayer(BlockInit.BLUE_PEARL_BLOCK.get(), RenderLayer.getTranslucent());
        RenderLayers.setRenderLayer(BlockInit.LIGHT_BLUE_PEARL_BLOCK.get(), RenderLayer.getTranslucent());
        RenderLayers.setRenderLayer(BlockInit.RED_PEARL_BLOCK.get(), RenderLayer.getTranslucent());
        RenderLayers.setRenderLayer(BlockInit.ORANGE_PEARL_BLOCK.get(), RenderLayer.getTranslucent());
        RenderLayers.setRenderLayer(BlockInit.PINK_PEARL_BLOCK.get(), RenderLayer.getTranslucent());
        RenderLayers.setRenderLayer(BlockInit.YELLOW_PEARL_BLOCK.get(), RenderLayer.getTranslucent());
        RenderLayers.setRenderLayer(BlockInit.GREEN_PEARL_BLOCK.get(), RenderLayer.getTranslucent());
        RenderLayers.setRenderLayer(BlockInit.LIME_PEARL_BLOCK.get(), RenderLayer.getTranslucent());
        RenderLayers.setRenderLayer(BlockInit.PURPLE_PEARL_BLOCK.get(), RenderLayer.getTranslucent());
        RenderLayers.setRenderLayer(BlockInit.MAGENTA_PEARL_BLOCK.get(), RenderLayer.getTranslucent());
        RenderLayers.setRenderLayer(BlockInit.CYAN_PEARL_BLOCK.get(), RenderLayer.getTranslucent());
        RenderLayers.setRenderLayer(BlockInit.BROWN_PEARL_BLOCK.get(), RenderLayer.getTranslucent());

        SkyProperties atlantis = new SkyProperties(255.0F, true, SkyProperties.SkyType.NORMAL, false, false) {
            @Override
            public Vec3d adjustFogColor(Vec3d vector3d, float v) {
                return vector3d;
            }

            @Override
            public boolean useThickFog(int i, int i1) {
                return false;
            }

            @Nonnull
            @Override
            public ISkyRenderHandler getSkyRenderHandler() {
                return new AltantisSkyRenderer();
            }
        };
        SkyProperties.BY_IDENTIFIER.put(new Identifier(Reference.MODID, "atlantis"), atlantis);
    }
}
