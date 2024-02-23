package com.mystic.atlantis.entities.renders;

import com.mystic.atlantis.entities.CoconutCrabEntity;
import mod.azure.azurelib.common.api.client.model.GeoModel;
import mod.azure.azurelib.common.api.client.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CoconutCrabEntityRenderer extends GeoEntityRenderer<CoconutCrabEntity> {

    public CoconutCrabEntityRenderer(EntityRendererProvider.Context renderManager, GeoModel<CoconutCrabEntity> modelProvider) {
        super(renderManager, modelProvider);
    }
}
