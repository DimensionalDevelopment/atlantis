package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Main;
import com.mystic.atlantis.entities.CrabEntity;
import com.mystic.atlantis.entities.renders.CrabEntityRenderer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CrabEntityModel extends AnimatedGeoModel<CrabEntity> {
    @Override
    public Identifier getModelLocation(CrabEntity object) {
        return Main.id("geo/crab.geo.json");
    }

    @Override
    public Identifier getTextureLocation(CrabEntity entity) {
        return switch (entity.getVariant()) {
            default -> Main.id("textures/entity/crab_red.png");
            case 1 -> Main.id("textures/entity/crab_red.png");
            case 2 -> Main.id("textures/entity/crab_blue.png");
        };
    }

    @Override
    public Identifier getAnimationFileLocation(CrabEntity animatable) {
        return Main.id("animations/crab.animation.json");
    }
}