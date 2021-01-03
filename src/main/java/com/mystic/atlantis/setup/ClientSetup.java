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
