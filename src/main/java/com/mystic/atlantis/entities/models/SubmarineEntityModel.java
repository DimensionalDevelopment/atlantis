package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.SubmarineEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SubmarineEntityModel extends AnimatedGeoModel<SubmarineEntity> {
    @Override
    public ResourceLocation getModelLocation(SubmarineEntity object) {
        return Atlantis.id("geo/submarine.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SubmarineEntity entity) {
        return Atlantis.id("textures/entity/submarine.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SubmarineEntity animatable) {
        return Atlantis.id("animations/submarine.animation.json");
    }
}