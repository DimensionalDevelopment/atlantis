package com.mystic.atlantis.blocks.blockentities.models;

import com.mystic.atlantis.blocks.blockentities.plants.TuberUpTileEntity;
import com.mystic.atlantis.util.Reference;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TuberUpModel extends AnimatedGeoModel<TuberUpTileEntity>
{
    @Override
    public ResourceLocation getModelResource(TuberUpTileEntity object)
    {
        return new ResourceLocation(Reference.MODID, "geo/tall_flower.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TuberUpTileEntity object)
    {
        return new ResourceLocation(Reference.MODID, "textures/block/tuber_up.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TuberUpTileEntity object)
    {
        return new ResourceLocation(GeckoLib.ModID, "animations/jackinthebox.animation.json");
    }
}