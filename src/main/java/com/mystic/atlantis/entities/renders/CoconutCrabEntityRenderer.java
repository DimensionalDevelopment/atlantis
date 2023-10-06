package com.mystic.atlantis.entities.renders;

import com.mystic.atlantis.entities.CoconutCrabEntity;
import com.mystic.atlantis.entities.CrabEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CoconutCrabEntityRenderer extends GeoEntityRenderer<CoconutCrabEntity> {

    public CoconutCrabEntityRenderer(EntityRendererProvider.Context renderManager, GeoModel<CoconutCrabEntity> modelProvider) {
        super(renderManager, modelProvider);
    }
}
