package com.mystic.atlantis.entities.blockbenchentities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.blockbenchentities.JellyfishEntity;
import com.mystic.atlantis.entities.blockbenchentities.StarfishEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class StarfishEntityModel extends AnimatedGeoModel<StarfishEntity> {
    @Override
    public ResourceLocation getModelResource(StarfishEntity object) {
        return Atlantis.id("geo/starfish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StarfishEntity entity) {
        if(entity.hasCustomName() && entity.getName().getString().equals("Patrick")) {
            return Atlantis.id("textures/entity/starfish_patrick.png");
        } else {
            return Atlantis.id("textures/entity/starfish.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(StarfishEntity animatable) {
        return Atlantis.id("animations/starfish.animation.json");
    }
}