package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.ShrimpEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShrimpEntityModel extends AnimatedGeoModel<ShrimpEntity> {
    @Override
    public ResourceLocation getModelResource(ShrimpEntity object) {
        return Atlantis.id("geo/shrimp.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShrimpEntity entity) {
        return Atlantis.id("textures/entity/shrimp.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShrimpEntity animatable) {
        return Atlantis.id("animations/shrimp.animation.json");
    }
}