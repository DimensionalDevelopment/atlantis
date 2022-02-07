package com.mystic.atlantis.blocks.blockentities.models;

import com.mystic.atlantis.blocks.blockentities.plants.TuberUpTileEntity;
import com.mystic.atlantis.util.Reference;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TuberUpModel extends AnimatedGeoModel<TuberUpTileEntity>
{
    @Override
    public Identifier getModelLocation(TuberUpTileEntity object)
    {
        return new Identifier(Reference.MODID, "geo/tall_flower.geo.json");
    }

    @Override
    public Identifier getTextureLocation(TuberUpTileEntity object)
    {
        return new Identifier(Reference.MODID, "textures/block/tuber_up.png");
    }

    @Override
    public Identifier getAnimationFileLocation(TuberUpTileEntity object)
    {
        return new Identifier(GeckoLib.ModID, "animations/jackinthebox.animation.json");
    }
}