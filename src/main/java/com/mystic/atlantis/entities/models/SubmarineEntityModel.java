package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.SubmarineEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import net.minecraft.util.Identifier;

public class SubmarineEntityModel extends AnimatedGeoModel<SubmarineEntity> {
    @Override
    public Identifier getModelLocation(SubmarineEntity object) {
        return Atlantis.id("geo/submarine.geo.json");
    }

    @Override
    public Identifier getTextureLocation(SubmarineEntity entity) {
        return Atlantis.id("textures/entity/submarine.png");
    }

    @Override
    public Identifier getAnimationFileLocation(SubmarineEntity animatable) {
        return Atlantis.id("animations/submarine.animation.json");
    }
}