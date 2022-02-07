package com.mystic.atlantis.blocks.blockentities.renderers;

import com.mystic.atlantis.blocks.blockentities.models.BlueLilyModel;
import com.mystic.atlantis.blocks.blockentities.plants.BlueLilyBlock;
import com.mystic.atlantis.blocks.blockentities.plants.BlueLilyTileEntity;
import com.mystic.atlantis.blocks.blockentities.plants.TuberUpTileEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class BlueLilyTileRenderer extends GeoBlockRenderer<BlueLilyTileEntity> {
    public BlueLilyTileRenderer() {
        super(new BlueLilyModel());
    }

    @Override
    public RenderLayer getRenderType(BlueLilyTileEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                     Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
