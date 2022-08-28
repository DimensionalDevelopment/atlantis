package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.JellyfishEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JellyfishEntityModel extends AnimatedGeoModel<JellyfishEntity> {
    @Override
    public ResourceLocation getModelResource(JellyfishEntity object) {
        return Atlantis.id("geo/jellyfish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(JellyfishEntity entity) {
        return Atlantis.id("textures/entity/jellyfish.png");
    }

    @Override
    public ResourceLocation getAnimationResource(JellyfishEntity animatable) {
        return Atlantis.id("animations/jellyfish.animation.json");
    }
}