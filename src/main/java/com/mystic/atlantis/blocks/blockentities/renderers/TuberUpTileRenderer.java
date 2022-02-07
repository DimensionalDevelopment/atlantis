package com.mystic.atlantis.blocks.blockentities.renderers;

import com.mystic.atlantis.blocks.blockentities.models.TuberUpModel;
import com.mystic.atlantis.blocks.blockentities.models.UnderwaterShroomModel;
import com.mystic.atlantis.blocks.blockentities.plants.TuberUpTileEntity;
import com.mystic.atlantis.blocks.blockentities.plants.UnderwaterShroomTileEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class TuberUpTileRenderer extends GeoBlockRenderer<TuberUpTileEntity> {
    public TuberUpTileRenderer() {
        super(new TuberUpModel());
    }

    @Override
    public RenderLayer getRenderType(TuberUpTileEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                     Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
