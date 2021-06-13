package com.mystic.atlantis.entities.renders;

import com.mystic.atlantis.Main;
import com.mystic.atlantis.entities.CrabEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CrabEntityRenderer extends GeoEntityRenderer<CrabEntity> {

    public CrabEntityRenderer(EntityRendererFactory.Context renderManager, AnimatedGeoModel<CrabEntity> modelProvider) {
        super(renderManager, modelProvider);
    }
}
