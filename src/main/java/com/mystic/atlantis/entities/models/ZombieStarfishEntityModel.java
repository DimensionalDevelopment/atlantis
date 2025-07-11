package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.ZombieStarfishEntity;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ZombieStarfishEntityModel extends GeoModel<ZombieStarfishEntity> {
    @Override
    public ResourceLocation getModelResource(ZombieStarfishEntity object) {
        return Atlantis.id("geo/starfish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ZombieStarfishEntity entity) {
        if(entity.hasCustomName() && entity.getName().getString().equals("Star-O")) {
            return Atlantis.id("textures/entity/starrofish.png");
        } else {
            return Atlantis.id("textures/entity/starzomfish.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(ZombieStarfishEntity animatable) {
        return Atlantis.id("animations/starfish.animation.json");
    }
}