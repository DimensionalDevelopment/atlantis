package com.mystic.atlantis.blocks.blockentities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mystic.atlantis.blocks.blockentities.models.EnenmomyModel;
import com.mystic.atlantis.blocks.blockentities.plants.EnenmomyTileEntity;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class EnenmomyTileRenderer extends GeoBlockRenderer<EnenmomyTileEntity> {
	
    public EnenmomyTileRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new EnenmomyModel());
    }

    @Override
    public RenderType getRenderType(EnenmomyTileEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void render(BlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
    }
}
