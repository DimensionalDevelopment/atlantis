package com.mystic.atlantis.blocks.blockentities.renderers;

import com.mystic.atlantis.blocks.blockentities.models.BlueLilyModel;
import com.mystic.atlantis.blocks.blockentities.models.EnenmomyModel;
import com.mystic.atlantis.blocks.blockentities.plants.BlueLilyTileEntity;
import com.mystic.atlantis.blocks.blockentities.plants.EnenmomyTileEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class EnenmomyTileRenderer extends GeoBlockRenderer<EnenmomyTileEntity> {
    public EnenmomyTileRenderer() {
        super(new EnenmomyModel());
    }

    @Override
    public RenderLayer getRenderType(EnenmomyTileEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                     Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
