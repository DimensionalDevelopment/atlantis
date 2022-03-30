package com.mystic.atlantis.entities.models;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.entities.AtlanteanBoatEntity;
import com.mystic.atlantis.entities.SubmarineEntity;
import com.mystic.atlantis.util.Reference;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import software.bernie.example.item.JackInTheBoxItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AtlanteanBoatModel extends BoatModel {
    public AtlanteanBoatModel(ModelPart bakeLayer) {
        super(bakeLayer);
    }
}