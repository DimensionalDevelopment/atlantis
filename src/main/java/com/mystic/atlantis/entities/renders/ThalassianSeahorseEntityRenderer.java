package com.mystic.atlantis.entities.renders;

import com.mystic.atlantis.entities.ThalassianSeahorseEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ThalassianSeahorseEntityRenderer extends GeoEntityRenderer<ThalassianSeahorseEntity> {

    public ThalassianSeahorseEntityRenderer(EntityRendererProvider.Context renderManager, GeoModel<ThalassianSeahorseEntity> modelProvider) {
        super(renderManager, modelProvider);
    }
}
