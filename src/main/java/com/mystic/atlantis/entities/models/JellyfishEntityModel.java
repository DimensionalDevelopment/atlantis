package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.JellyfishEntity;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.common.api.client.model.GeoModel;

public class JellyfishEntityModel extends GeoModel<JellyfishEntity> {
    @Override
    public ResourceLocation getModelResource(JellyfishEntity entity) {
        if (entity.getVariant() == 1) {
            return Atlantis.id("geo/jellyfish.geo.json");
        } else {
            return Atlantis.id("geo/jellyfish_2.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(JellyfishEntity entity) {
        if (entity.getVariant() == 1) {
            return switch (entity.getColor()) {
                default -> Atlantis.id("textures/entity/jellyfish_white.png");
                case 1 -> Atlantis.id("textures/entity/jellyfish_black.png");
                case 2 -> Atlantis.id("textures/entity/jellyfish_blue.png");
                case 3 -> Atlantis.id("textures/entity/jellyfish_brown.png");
                case 4 -> Atlantis.id("textures/entity/jellyfish_cyan.png");
                case 5 -> Atlantis.id("textures/entity/jellyfish_gray.png");
                case 6 -> Atlantis.id("textures/entity/jellyfish_green.png");
                case 7 -> Atlantis.id("textures/entity/jellyfish_light_blue.png");
                case 8 -> Atlantis.id("textures/entity/jellyfish_light_gray.png");
                case 9 -> Atlantis.id("textures/entity/jellyfish_lime.png");
                case 10 -> Atlantis.id("textures/entity/jellyfish_magenta.png");
                case 11 -> Atlantis.id("textures/entity/jellyfish_orange.png");
                case 12 -> Atlantis.id("textures/entity/jellyfish_yellow.png");
                case 13 -> Atlantis.id("textures/entity/jellyfish_pink.png");
                case 14 -> Atlantis.id("textures/entity/jellyfish_purple.png");
                case 15 -> Atlantis.id("textures/entity/jellyfish_red.png");
            };
        } else {
            return switch (entity.getColor()) {
                default -> Atlantis.id("textures/entity/jellyfish_2_white.png");
                case 1 -> Atlantis.id("textures/entity/jellyfish_2_black.png");
                case 2 -> Atlantis.id("textures/entity/jellyfish_2_blue.png");
                case 3 -> Atlantis.id("textures/entity/jellyfish_2_brown.png");
                case 4 -> Atlantis.id("textures/entity/jellyfish_2_cyan.png");
                case 5 -> Atlantis.id("textures/entity/jellyfish_2_gray.png");
                case 6 -> Atlantis.id("textures/entity/jellyfish_2_green.png");
                case 7 -> Atlantis.id("textures/entity/jellyfish_2_light_blue.png");
                case 8 -> Atlantis.id("textures/entity/jellyfish_2_light_gray.png");
                case 9 -> Atlantis.id("textures/entity/jellyfish_2_lime.png");
                case 10 -> Atlantis.id("textures/entity/jellyfish_2_magenta.png");
                case 11 -> Atlantis.id("textures/entity/jellyfish_2_orange.png");
                case 12 -> Atlantis.id("textures/entity/jellyfish_2_yellow.png");
                case 13 -> Atlantis.id("textures/entity/jellyfish_2_pink.png");
                case 14 -> Atlantis.id("textures/entity/jellyfish_2_purple.png");
                case 15 -> Atlantis.id("textures/entity/jellyfish_2_red.png");
            };
        }
    }

    @Override
    public ResourceLocation getAnimationResource(JellyfishEntity entity) {
        if (entity.getVariant() == 1) {
            return Atlantis.id("animations/jellyfish.animation.json");
        } else {
            return Atlantis.id("animations/jellyfish2.animation.json");
        }
    }
}