package com.mystic.atlantis.blocks.blockentities.models;

import com.mystic.atlantis.blocks.blockentities.plants.UnderwaterShroomTileEntity;
import com.mystic.atlantis.util.Reference;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UnderwaterShroomModel extends AnimatedGeoModel<UnderwaterShroomTileEntity>
{
    @Override
    public ResourceLocation getModelLocation(UnderwaterShroomTileEntity object)
    {
        return new ResourceLocation(Reference.MODID, "geo/underwater_shroom.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(UnderwaterShroomTileEntity object)
    {
        return new ResourceLocation(Reference.MODID, "textures/block/underwater_shroom.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(UnderwaterShroomTileEntity object)
    {
        return new ResourceLocation(GeckoLib.ModID, "animations/jackinthebox.animation.json");
    }
}