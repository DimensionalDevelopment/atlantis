package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.CoconutCrabEntity;
import mod.azure.azurelib.common.api.client.model.GeoModel;
import net.minecraft.resources.ResourceLocation;

public class CoconutCrabEntityModel extends GeoModel<CoconutCrabEntity> {
    @Override
    public ResourceLocation getModelResource(CoconutCrabEntity object) {
        return Atlantis.id("geo/crab.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CoconutCrabEntity entity) {
        return Atlantis.id("textures/entity/coconut_crab.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CoconutCrabEntity animatable) {
        return Atlantis.id("animations/crab.animation.json");
    }
}