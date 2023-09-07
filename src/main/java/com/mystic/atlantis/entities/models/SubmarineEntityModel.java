package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.SubmarineEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SubmarineEntityModel extends GeoModel<SubmarineEntity> {
    @Override
    public ResourceLocation getModelResource(SubmarineEntity object) {
        return Atlantis.id("geo/submarine.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SubmarineEntity entity) {
        return Atlantis.id("textures/entity/submarine.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SubmarineEntity animatable) {
        return Atlantis.id("animations/submarine.animation.json");
    }
}