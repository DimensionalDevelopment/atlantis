package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Main;
import com.mystic.atlantis.entities.CrabEntity;
import com.mystic.atlantis.entities.JellyfishEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JellyfishEntityModel extends AnimatedGeoModel<JellyfishEntity> {
    @Override
    public Identifier getModelLocation(JellyfishEntity object) {
        return Main.id("geo/jellyfish.geo.json");
    }

    @Override
    public Identifier getTextureLocation(JellyfishEntity entity) {
        return Main.id("textures/entity/jellyfish.png");
    }

    @Override
    public Identifier getAnimationFileLocation(JellyfishEntity animatable) {
        return Main.id("animations/jellyfish.animation.json");
    }
}