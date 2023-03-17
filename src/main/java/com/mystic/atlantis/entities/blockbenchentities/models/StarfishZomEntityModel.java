package com.mystic.atlantis.entities.blockbenchentities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.blockbenchentities.JellyfishEntity;
import com.mystic.atlantis.entities.blockbenchentities.StarfishZomEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class StarfishZomEntityModel extends AnimatedGeoModel<StarfishZomEntity> {
    @Override
    public ResourceLocation getModelResource(StarfishZomEntity object) {
        return Atlantis.id("geo/starfish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StarfishZomEntity entity) {
        if(entity.hasCustomName() && entity.getName().getString().equals("Star-O")) {
            return Atlantis.id("textures/entity/starrofish");
        } else {
            return Atlantis.id("textures/entity/starzomfish.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(StarfishZomEntity animatable) {
        return Atlantis.id("animations/starfish.animation.json");
    }
}