package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.Jellyfish2Entity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Jellyfish2EntityModel extends AnimatedGeoModel<Jellyfish2Entity> {
    @Override
    public Identifier getModelLocation(Jellyfish2Entity object) {
        return Atlantis.id("geo/jellyfish_2.geo.json");
    }

    @Override
    public Identifier getTextureLocation(Jellyfish2Entity entity) {
        return Atlantis.id("textures/entity/jellyfish_2.png");
    }

    @Override
    public Identifier getAnimationFileLocation(Jellyfish2Entity animatable) {
        return Atlantis.id("animations/jellyfish2.animation.json");
    }
}