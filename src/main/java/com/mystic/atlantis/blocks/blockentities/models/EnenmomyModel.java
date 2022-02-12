package com.mystic.atlantis.blocks.blockentities.models;

import com.mystic.atlantis.blocks.blockentities.plants.EnenmomyTileEntity;
import com.mystic.atlantis.util.Reference;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EnenmomyModel extends AnimatedGeoModel<EnenmomyTileEntity>
{
    @Override
    public Identifier getModelLocation(EnenmomyTileEntity object)
    {
        return new Identifier(Reference.MODID, "geo/enenmomy.geo.json");
    }

    @Override
    public Identifier getTextureLocation(EnenmomyTileEntity object)
    {
        return new Identifier(Reference.MODID, "textures/block/anenomy.png");
    }

    @Override
    public Identifier getAnimationFileLocation(EnenmomyTileEntity object)
    {
        return new Identifier(GeckoLib.ModID, "animations/jackinthebox.animation.json");
    }
}