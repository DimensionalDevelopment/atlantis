package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.SeahorseEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SeahorseEntityModel extends GeoModel<SeahorseEntity> {
    @Override
    public ResourceLocation getModelResource(SeahorseEntity object) {
        return Atlantis.id("geo/seahorse.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SeahorseEntity entity) {
        return Atlantis.id("textures/entity/seahorse.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SeahorseEntity animatable) {
        return Atlantis.id("animations/seahorse.animation.json");
    }
}