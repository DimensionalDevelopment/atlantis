package com.mystic.atlantis.blocks.blockentities.models;

import com.mystic.atlantis.blocks.blockentities.plants.UnderwaterShroomTileEntity;
import com.mystic.atlantis.util.Reference;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UnderwaterShroomModel extends AnimatedGeoModel<UnderwaterShroomTileEntity>
{
    @Override
    public Identifier getModelLocation(UnderwaterShroomTileEntity object)
    {
        return new Identifier(Reference.MODID, "geo/underwater_shroom.geo.json");
    }

    @Override
    public Identifier getTextureLocation(UnderwaterShroomTileEntity object)
    {
        return new Identifier(Reference.MODID, "textures/block/underwater_shroom.png");
    }

    @Override
    public Identifier getAnimationFileLocation(UnderwaterShroomTileEntity object)
    {
        return new Identifier(GeckoLib.ModID, "animations/jackinthebox.animation.json");
    }
}