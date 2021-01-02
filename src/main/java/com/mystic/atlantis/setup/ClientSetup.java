package com.mystic.atlantis.setup;

import com.mystic.atlantis.init.BlockInit;
import com.mystic.atlantis.util.Reference;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup
{

    public static void init(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(BlockInit.UNDERWATER_FLOWER.get(), RenderType.getCutout());
    }
}
