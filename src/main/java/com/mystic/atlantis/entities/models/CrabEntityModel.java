package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.CrabEntity;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.common.api.client.model.GeoModel;

public class CrabEntityModel extends GeoModel<CrabEntity> {
    @Override
    public ResourceLocation getModelResource(CrabEntity object) {
        return Atlantis.id("geo/crab.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CrabEntity entity) {
        return switch (entity.getVariant()) {
            default -> Atlantis.id("textures/entity/crab_red.png");
            case 2 -> Atlantis.id("textures/entity/crab_blue.png");
        };
    }

    @Override
    public ResourceLocation getAnimationResource(CrabEntity animatable) {
        return Atlantis.id("animations/crab.animation.json");
    }
}