package com.mystic.atlantis.blocks.blockentities.models;

import com.mystic.atlantis.blocks.blockentities.plants.GeneralPlantBlockEntity;
import com.mystic.atlantis.util.Reference;
import mod.azure.azurelib.common.api.client.model.GeoModel;
import mod.azure.azurelib.common.internal.common.AzureLib;
import net.minecraft.resources.ResourceLocation;

public class GeneralPlantModel<T extends GeneralPlantBlockEntity<?>> extends GeoModel<T> {
    private final ResourceLocation model;
    private final ResourceLocation texture;

    public GeneralPlantModel(String name) {
        this.model = new ResourceLocation(Reference.MODID, "geo/" + name + ".geo.json");
        this.texture = new ResourceLocation(Reference.MODID, "textures/block/" + name + ".png");
    }


    @Override
    public ResourceLocation getModelResource(T object) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(T object) {
        return new ResourceLocation(AzureLib.MOD_ID, "animations/jackinthebox.animation.json");
    }
}