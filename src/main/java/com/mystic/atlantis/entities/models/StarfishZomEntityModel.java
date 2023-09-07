package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.StarfishZomEntity;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class StarfishZomEntityModel extends GeoModel<StarfishZomEntity> {
    @Override
    public ResourceLocation getModelResource(StarfishZomEntity object) {
        return Atlantis.id("geo/starfish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StarfishZomEntity entity) {
        if(entity.hasCustomName() && entity.getName().getString().equals("Star-O")) {
            return Atlantis.id("textures/entity/starrofish.png");
        } else {
            return Atlantis.id("textures/entity/starzomfish.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(StarfishZomEntity animatable) {
        return Atlantis.id("animations/starfish.animation.json");
    }
}