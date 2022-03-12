package com.mystic.atlantis.blocks.blockentities.models;

import com.mystic.atlantis.blocks.blockentities.plants.BurntDeepTileEntity;
import com.mystic.atlantis.util.Reference;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BurntDeepModel extends AnimatedGeoModel<BurntDeepTileEntity>
{
    @Override
    public ResourceLocation getModelLocation(BurntDeepTileEntity object)
    {
        return new ResourceLocation(Reference.MODID, "geo/flower_1.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BurntDeepTileEntity object)
    {
        return new ResourceLocation(Reference.MODID, "textures/block/burnt_deep.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BurntDeepTileEntity object)
    {
        return new ResourceLocation(GeckoLib.ModID, "animations/jackinthebox.animation.json");
    }
}