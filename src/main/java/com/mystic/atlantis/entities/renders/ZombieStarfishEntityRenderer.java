package com.mystic.atlantis.entities.renders;

import com.mystic.atlantis.entities.ZombieStarfishEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ZombieStarfishEntityRenderer extends GeoEntityRenderer<ZombieStarfishEntity> {

    public ZombieStarfishEntityRenderer(EntityRendererProvider.Context renderManager, GeoModel<ZombieStarfishEntity> modelProvider) {
        super(renderManager, modelProvider);
    }
}
