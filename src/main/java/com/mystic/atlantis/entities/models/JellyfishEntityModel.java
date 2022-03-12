package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.JellyfishEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JellyfishEntityModel extends AnimatedGeoModel<JellyfishEntity> {
    @Override
    public ResourceLocation getModelLocation(JellyfishEntity object) {
        return Atlantis.id("geo/jellyfish.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(JellyfishEntity entity) {
        return Atlantis.id("textures/entity/jellyfish.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(JellyfishEntity animatable) {
        return Atlantis.id("animations/jellyfish.animation.json");
    }
}