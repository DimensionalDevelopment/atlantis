package com.mystic.atlantis.blocks.blockentities.models;

import com.mystic.atlantis.blocks.blockentities.plants.TuberUpTileEntity;
import com.mystic.atlantis.util.Reference;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TuberUpModel extends AnimatedGeoModel<TuberUpTileEntity>
{
    @Override
    public ResourceLocation getModelLocation(TuberUpTileEntity object)
    {
        return new ResourceLocation(Reference.MODID, "geo/tall_flower.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TuberUpTileEntity object)
    {
        return new ResourceLocation(Reference.MODID, "textures/block/tuber_up.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TuberUpTileEntity object)
    {
        return new ResourceLocation(GeckoLib.ModID, "animations/jackinthebox.animation.json");
    }
}