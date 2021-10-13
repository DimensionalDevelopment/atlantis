package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.ShrimpEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import net.minecraft.util.Identifier;

public class ShrimpEntityModel extends AnimatedGeoModel<ShrimpEntity> {
    @Override
    public Identifier getModelLocation(ShrimpEntity object) {
        return Atlantis.id("geo/shrimp.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ShrimpEntity entity) {
        return Atlantis.id("textures/entity/shrimp.png");
    }

    @Override
    public Identifier getAnimationFileLocation(ShrimpEntity animatable) {
        return Atlantis.id("animations/shrimp.animation.json");
    }
}