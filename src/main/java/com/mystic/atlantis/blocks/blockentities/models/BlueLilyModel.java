package com.mystic.atlantis.blocks.blockentities.models;

import com.mystic.atlantis.blocks.blockentities.plants.BlueLilyTileEntity;
import com.mystic.atlantis.util.Reference;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlueLilyModel extends AnimatedGeoModel<BlueLilyTileEntity> {
    @Override
    public ResourceLocation getModelResource(BlueLilyTileEntity object) {
        return new ResourceLocation(Reference.MODID, "geo/flower_1.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlueLilyTileEntity object) {
        return new ResourceLocation(Reference.MODID, "textures/block/blue_lily.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BlueLilyTileEntity object) {
        return new ResourceLocation(GeckoLib.ModID, "animations/jackinthebox.animation.json");
    }
}