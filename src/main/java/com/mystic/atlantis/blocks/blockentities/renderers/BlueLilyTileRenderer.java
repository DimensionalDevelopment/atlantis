package com.mystic.atlantis.blocks.blockentities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mystic.atlantis.blocks.blockentities.models.BlueLilyModel;
import com.mystic.atlantis.blocks.blockentities.plants.BlueLilyBlock;
import com.mystic.atlantis.blocks.blockentities.plants.BlueLilyTileEntity;
import com.mystic.atlantis.blocks.blockentities.plants.TuberUpTileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class BlueLilyTileRenderer extends GeoBlockRenderer<BlueLilyTileEntity> {
    public BlueLilyTileRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new BlueLilyModel());
    }

    @Override
    public RenderType getRenderType(BlueLilyTileEntity animatable, float partialTicks, PoseStack stack,
                                     MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                     ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
