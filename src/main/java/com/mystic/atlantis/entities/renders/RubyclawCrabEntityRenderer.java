package com.mystic.atlantis.entities.renders;

import com.mystic.atlantis.entities.RubyclawCrabEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RubyclawCrabEntityRenderer extends GeoEntityRenderer<RubyclawCrabEntity> {

    public RubyclawCrabEntityRenderer(EntityRendererProvider.Context renderManager, GeoModel<RubyclawCrabEntity> modelProvider) {
        super(renderManager, modelProvider);
    }
}
