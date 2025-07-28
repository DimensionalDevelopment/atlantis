package com.mystic.atlantis.entities.renders;

import com.mystic.atlantis.entities.LeviathanEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LeviathanEntityRenderer extends GeoEntityRenderer<LeviathanEntity> {

    public LeviathanEntityRenderer(EntityRendererProvider.Context renderManager, GeoModel<LeviathanEntity> modelProvider) {
        super(renderManager, modelProvider);
    }
}
