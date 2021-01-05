package com.mystic.atlantis.setup;

import com.mystic.atlantis.dimension.AltantisSkyRenderer;
import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.util.Reference;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup
{

    public static void init(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(BlockInit.UNDERWATER_FLOWER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.ALGAE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BLACK_PEARL_BLOCK.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.GRAY_PEARL_BLOCK.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.WHITE_PEARL_BLOCK.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.LIGHT_GRAY_PEARL_BLOCK.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.BLUE_PEARL_BLOCK.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.LIGHT_BLUE_PEARL_BLOCK.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.RED_PEARL_BLOCK.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.ORANGE_PEARL_BLOCK.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.PINK_PEARL_BLOCK.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.YELLOW_PEARL_BLOCK.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.GREEN_PEARL_BLOCK.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.LIME_PEARL_BLOCK.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.PURPLE_PEARL_BLOCK.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.MAGENTA_PEARL_BLOCK.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.CYAN_PEARL_BLOCK.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.BROWN_PEARL_BLOCK.get(), RenderType.getTranslucent());

        DimensionRenderInfo atlantis = new DimensionRenderInfo(255.0F, true, DimensionRenderInfo.FogType.NORMAL, false, false) {
            @Override
            public Vector3d func_230494_a_(Vector3d vector3d, float v) {
                return vector3d;
            }

            @Override
            public boolean func_230493_a_(int i, int i1) {
                return false;
            }

            @Nonnull
            @Override
            public ISkyRenderHandler getSkyRenderHandler() {
                return new AltantisSkyRenderer();
            }
        };
        DimensionRenderInfo.field_239208_a_.put(new ResourceLocation(Reference.MODID, "atlantis"), atlantis);
    }
}
