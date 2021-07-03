package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.JellyfishEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JellyfishEntityModel extends AnimatedGeoModel<JellyfishEntity> {
    @Override
    public Identifier getModelLocation(JellyfishEntity object) {
        return Atlantis.id("geo/jellyfish.geo.json");
    }

    @Override
    public Identifier getTextureLocation(JellyfishEntity entity) {
        return Atlantis.id("textures/entity/jellyfish.png");
    }

    @Override
    public Identifier getAnimationFileLocation(JellyfishEntity animatable) {
        return Atlantis.id("animations/jellyfish.animation.json");
    }
}