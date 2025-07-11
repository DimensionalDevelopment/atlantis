package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.ThalassianSeahorseEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ThalassianSeahorseEntityModel extends GeoModel<ThalassianSeahorseEntity> {
    @Override
    public ResourceLocation getModelResource(ThalassianSeahorseEntity object) {
        return Atlantis.id("geo/seahorse.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ThalassianSeahorseEntity entity) {
        return Atlantis.id("textures/entity/seahorse.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ThalassianSeahorseEntity animatable) {
        return Atlantis.id("animations/seahorse.animation.json");
    }
}