package com.mystic.atlantis.blocks.blockentities.renderers;

import com.mystic.atlantis.blocks.blockentities.models.UnderwaterShroomModel;
import com.mystic.atlantis.blocks.blockentities.plants.UnderwaterShroomTileEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class UnderwaterShroomTileRenderer extends GeoBlockRenderer<UnderwaterShroomTileEntity> {
    public UnderwaterShroomTileRenderer() {
        super(new UnderwaterShroomModel());
    }

    @Override
    public RenderLayer getRenderType(UnderwaterShroomTileEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                     Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
