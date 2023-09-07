package com.mystic.atlantis.blocks.blockentities.renderers;

import com.mystic.atlantis.blocks.blockentities.plants.BlueLilyTileEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class BlueLilyTileRenderer extends GeneralPlantRenderer<BlueLilyTileEntity> {
	
    public BlueLilyTileRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super("blue_lily");
    }
}
