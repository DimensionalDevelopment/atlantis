package com.mystic.atlantis.blocks.blockentities.models;

import com.mystic.atlantis.blocks.blockentities.plants.BlueLilyTileEntity;
import com.mystic.atlantis.util.Reference;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlueLilyModel extends AnimatedGeoModel<BlueLilyTileEntity>
{
    @Override
    public Identifier getModelLocation(BlueLilyTileEntity object)
    {
        return new Identifier(Reference.MODID, "geo/flower_1.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BlueLilyTileEntity object)
    {
        return new Identifier(Reference.MODID, "textures/block/blue_lily.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BlueLilyTileEntity object)
    {
        return new Identifier(GeckoLib.ModID, "animations/jackinthebox.animation.json");
    }
}