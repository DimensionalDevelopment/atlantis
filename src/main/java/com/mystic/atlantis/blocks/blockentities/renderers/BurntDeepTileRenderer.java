package com.mystic.atlantis.blocks.blockentities.renderers;

import com.mystic.atlantis.blocks.blockentities.models.BurntDeepModel;
import com.mystic.atlantis.blocks.blockentities.plants.BurntDeepTileEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class BurntDeepTileRenderer extends GeoBlockRenderer<BurntDeepTileEntity> {
    public BurntDeepTileRenderer() {
        super(new BurntDeepModel());
    }

    @Override
    public RenderLayer getRenderType(BurntDeepTileEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                     Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
