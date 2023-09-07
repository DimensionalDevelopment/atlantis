package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.LeviathanEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class LeviathanEntityModel extends GeoModel<LeviathanEntity> {
    @Override
    public ResourceLocation getModelResource(LeviathanEntity object) {
        return Atlantis.id("geo/leviathan.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LeviathanEntity entity) {
        return Atlantis.id("textures/entity/leviathan.png");
    }

    @Override
    public ResourceLocation getAnimationResource(LeviathanEntity animatable) {
        return Atlantis.id("animations/leviathan.animation.json");
    }
}