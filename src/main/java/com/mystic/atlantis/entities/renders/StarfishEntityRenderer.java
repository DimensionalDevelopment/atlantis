package com.mystic.atlantis.entities.renders;

import com.mystic.atlantis.entities.StarfishEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class StarfishEntityRenderer extends GeoEntityRenderer<StarfishEntity> {

    public StarfishEntityRenderer(EntityRendererProvider.Context renderManager, GeoModel<StarfishEntity> modelProvider) {
        super(renderManager, modelProvider);
    }
}
