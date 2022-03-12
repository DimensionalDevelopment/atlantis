package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.ShrimpEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShrimpEntityModel extends AnimatedGeoModel<ShrimpEntity> {
    @Override
    public ResourceLocation getModelLocation(ShrimpEntity object) {
        return Atlantis.id("geo/shrimp.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ShrimpEntity entity) {
        return Atlantis.id("textures/entity/shrimp.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ShrimpEntity animatable) {
        return Atlantis.id("animations/shrimp.animation.json");
    }
}