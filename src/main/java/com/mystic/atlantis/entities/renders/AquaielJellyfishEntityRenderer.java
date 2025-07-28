package com.mystic.atlantis.entities.renders;

import com.mystic.atlantis.entities.AquaielJellyfishEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AquaielJellyfishEntityRenderer extends GeoEntityRenderer<AquaielJellyfishEntity> {

    public AquaielJellyfishEntityRenderer(EntityRendererProvider.Context renderManager, GeoModel<AquaielJellyfishEntity> modelProvider) {
        super(renderManager, modelProvider);
    }
}
