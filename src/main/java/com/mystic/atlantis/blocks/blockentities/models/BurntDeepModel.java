package com.mystic.atlantis.blocks.blockentities.models;

import com.mystic.atlantis.blocks.blockentities.plants.BurntDeepTileEntity;
import com.mystic.atlantis.util.Reference;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BurntDeepModel extends AnimatedGeoModel<BurntDeepTileEntity>
{
    @Override
    public Identifier getModelLocation(BurntDeepTileEntity object)
    {
        return new Identifier(Reference.MODID, "geo/flower_1.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BurntDeepTileEntity object)
    {
        return new Identifier(Reference.MODID, "textures/block/burnt_deep.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BurntDeepTileEntity object)
    {
        return new Identifier(GeckoLib.ModID, "animations/jackinthebox.animation.json");
    }
}