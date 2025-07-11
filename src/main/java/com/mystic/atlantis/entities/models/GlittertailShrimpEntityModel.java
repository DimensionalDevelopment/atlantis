package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.GlittertailShrimpEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GlittertailShrimpEntityModel extends GeoModel<GlittertailShrimpEntity> {
    @Override
    public ResourceLocation getModelResource(GlittertailShrimpEntity object) {
        return Atlantis.id("geo/shrimp.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GlittertailShrimpEntity entity) {
        return Atlantis.id("textures/entity/shrimp.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GlittertailShrimpEntity animatable) {
        return Atlantis.id("animations/shrimp.animation.json");
    }
}