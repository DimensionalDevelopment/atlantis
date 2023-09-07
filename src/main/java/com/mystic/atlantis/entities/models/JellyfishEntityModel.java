package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.JellyfishEntity;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JellyfishEntityModel extends AnimatedGeoModel<JellyfishEntity> {
    @Override
    public ResourceLocation getModelResource(JellyfishEntity entity) {
        return switch (entity.getVariant()) {
            default -> Atlantis.id("geo/jellyfish.geo.json");
            case 2 -> Atlantis.id("geo/jellyfish_2.geo.json");
        };
    }

    @Override
    public ResourceLocation getTextureResource(JellyfishEntity entity) {
        return switch (entity.getVariant()) {
            default -> Atlantis.id("textures/entity/jellyfish.png");
            case 2 -> Atlantis.id("textures/entity/jellyfish_2.png");
        };
    }

    @Override
    public ResourceLocation getAnimationResource(JellyfishEntity entity) {
        return switch (entity.getVariant()) {
            default -> Atlantis.id("animations/jellyfish.animation.json");
            case 2 -> Atlantis.id("animations/jellyfish2.animation.json");
        };
    }
}