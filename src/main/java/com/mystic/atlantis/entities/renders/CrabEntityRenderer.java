package com.mystic.atlantis.entities.renders;

import com.mystic.atlantis.entities.CrabEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CrabEntityRenderer extends GeoEntityRenderer<CrabEntity> {

    public CrabEntityRenderer(EntityRendererProvider.Context renderManager, GeoModel<CrabEntity> modelProvider) {
        super(renderManager, modelProvider);
    }
}
